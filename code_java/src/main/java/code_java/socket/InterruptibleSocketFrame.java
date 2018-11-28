package code_java.socket;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author yht
 * @create 2018/11/24
 */
public class InterruptibleSocketFrame extends JFrame {
    private Scanner in;
    private JButton interruptibleButton;
    private JButton blockingButton;
    private JButton cancelButton;
    private JTextArea message;
    private TestServer server;
    private Thread connectThread;

    public InterruptibleSocketFrame() throws HeadlessException {
        JPanel northPanel = new JPanel();
        add(northPanel, BorderLayout.NORTH);

        final int TEXT_ROWS = 20;
        final int TEXT_COLUMNS = 60;
        message = new JTextArea(TEXT_ROWS, TEXT_COLUMNS);
        add(new JScrollPane(message));

        interruptibleButton = new JButton("Interruptible");
        blockingButton = new JButton("Blocking");
        northPanel.add(interruptibleButton);
        northPanel.add(blockingButton);

        interruptibleButton.addActionListener(event ->{
            interruptibleButton.setEnabled(false);
            blockingButton.setEnabled(false);
            cancelButton.setEnabled(true);
            connectThread = new Thread(() -> {
                try{
                    connectInterruptibly();
                } catch (IOException e) {
                    message.append("\nInterruptibleSocketTest.connectInterruptibly\n");
                }
            });
            connectThread.start();
        });

        blockingButton.addActionListener(event -> {
            interruptibleButton.setEnabled(false);
            blockingButton.setEnabled(false);
            cancelButton.setEnabled(true);
            connectThread = new Thread(() -> {
                try{
                    connectBlocking();
                } catch (IOException e) {
                    message.append("\nInterruptibleSocketTest.connectBlocking\n");
                }
            });
            connectThread.start();
        });

        cancelButton = new JButton("Cancel");
        cancelButton.setEnabled(false);
        northPanel.add(cancelButton);
        cancelButton.addActionListener(event -> {
            connectThread.interrupt();
            cancelButton.setEnabled(false);
        });
        server = new TestServer();
        new Thread(server).start();
        pack();
    }

    private void connectBlocking() throws IOException {
        message.append("Blocking:\n");
        try(Socket socket = new Socket("localhost", 8189)) {
            in = new Scanner(socket.getInputStream(), StandardCharsets.UTF_8.name());
            while (!Thread.currentThread().isInterrupted()) {
                message.append("Reading ");
                if(in.hasNextLine()) {
                    String line = in.nextLine();
                    message.append(line);
                    message.append("\n");
                }
            }
        } finally {
            EventQueue.invokeLater(() -> {
                message.append("Socket closed\n");
                interruptibleButton.setEnabled(true);
                blockingButton.setEnabled(true);
            });
        }
    }

    private void connectInterruptibly() throws IOException {
        message.append("Interruptible:\n");
        try(SocketChannel channel = SocketChannel.open(new InetSocketAddress("localhost", 8189))) {
            in = new Scanner(channel, StandardCharsets.UTF_8.name());
            while (!Thread.currentThread().isInterrupted()) {
                message.append("Reading ");
                if(in.hasNextLine()) {
                    String line = in.nextLine();
                    message.append(line);
                    message.append("\n");
                }
            }
        } finally {
            EventQueue.invokeLater(() -> {
                message.append("Channel closed\n");
                interruptibleButton.setEnabled(true);
                blockingButton.setEnabled(true);
            });
        }
    }


    class TestServer implements Runnable {
        @Override
        public void run() {
            try (ServerSocket server = new ServerSocket(8189)) {
                while (true) {
                    Socket incoming = server.accept();
                    Runnable runnable = new TestServerHandler(incoming);
                    Thread thread = new Thread(runnable);
                    thread.start();
                }
            } catch (IOException e) {
                message.append("\nTestServer.run: " + e);
            }
        }
    }

    class TestServerHandler implements Runnable {
        private Socket incoming;
        private int counter;
        public TestServerHandler(Socket incoming) {
            this.incoming = incoming;
        }

        @Override
        public void run() {
            try{
                try{
                    OutputStream outputStream = incoming.getOutputStream();
                    PrintWriter out = new PrintWriter(
                            new OutputStreamWriter(outputStream, StandardCharsets.UTF_8.name()), true);
                    while (counter < 100) {
                        counter ++;
                        if(counter < 10) {
                            out.println(counter);
                            Thread.sleep(100);
                        }
                    }
                } finally {
                    incoming.close();
                    message.append("Closing server\n");
                }
            } catch (Exception e) {
                message.append("\nThreadServerHandler.run: " + e);
            }
        }
    }
}


