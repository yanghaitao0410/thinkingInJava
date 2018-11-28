package code_java.io;

import code_java.generic.Employee;
import code_java.generic.Manager;

import java.io.*;
import java.util.stream.Stream;

/**
 * 将对象写出到文件 再读取
 * 对象包含对象
 * @author yht
 * @create 2018/11/23
 */
public class ObjectStreamTest {

    public static void main(String[] args) {
        Employee harry = new Employee("Harry Hacker", 50000, 1989, 10, 1);
        Manager carl = new Manager("Carl Cracker", 80000, 1987, 12, 15);
        carl.setSecretary(harry);
        Manager tony = new Manager("Tony Tester", 40000, 1990, 3, 15);
        tony.setSecretary(harry);

        Employee[] staff = new Employee[3];
        staff[0] = harry;
        staff[1] = carl;
        staff[2] = tony;

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("code_java/employee.dat"))) {
            out.writeObject(staff);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("code_java/employee.dat"))) {
            Employee[] newStaff = (Employee[]) in.readObject();
            Stream.of(newStaff).forEach(System.out::println);
            System.out.println();
            newStaff[0].setBonus(10);
            Stream.of(newStaff).forEach(System.out::println);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
