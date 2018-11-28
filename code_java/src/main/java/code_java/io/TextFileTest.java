package code_java.io;

import code_java.generic.Employee;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * 以文本格式存储对象
 *
 * @author yht
 * @create 2018年11月22日
 */
public class TextFileTest {
    public static void main(String[] args) {
        Employee[] staff = new Employee[3];

        staff[0] = new Employee("Carl Cracker", 75000, 1987, 12, 15);
        staff[1] = new Employee("Harry Hacker", 50000, 1989, 10, 1);
        staff[2] = new Employee("Carl Cracker", 40000, 1990, 3, 5);

        try (PrintWriter out = new PrintWriter("code_java/employee.dat", StandardCharsets.UTF_8.name())) {
            writeData(staff, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try (Scanner in = new Scanner(new FileInputStream("code_java/employee.dat"), StandardCharsets.UTF_8.name())) {
            Employee[] newStaff = readData(in);

            for (Employee employee : newStaff) {
                System.out.println(employee);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void writeData(Employee[] employees, PrintWriter out) {
        out.println(employees.length);
        for (Employee employee : employees) {
            writeEmployee(out, employee);
        }
    }

    private static void writeEmployee(PrintWriter out, Employee employee) {
        out.println(employee.getName() + "|" + employee.getBonus() + "|" + employee.getDate());
    }

    private static Employee[] readData(Scanner in) {
        int n = in.nextInt();
        //nextInt的调用读入的是数组的长度，但不包括行尾的换行符
        //需要主动调用nextLine()将光标移动到下一行的开头
        in.nextLine();

        Employee[] employees = new Employee[n];
        for (int i = 0; i < n; i++) {
            employees[i] = readEmployee(in);
        }
        return employees;
    }

    private static Employee readEmployee(Scanner in) {
        String line = in.nextLine();
        String[] tokens = line.split("\\|");
        String name = tokens[0];
        double salary = Double.parseDouble(tokens[1]);
        LocalDate hireData = LocalDate.parse(tokens[2]);
        int year = hireData.getYear();
        int month = hireData.getMonthValue();
        int day = hireData.getDayOfMonth();

        return new Employee(name, salary, year, month, day);
    }
}
