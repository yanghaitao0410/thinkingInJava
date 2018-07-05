package jxls;

import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Excel {

    public static void exportExcel1() {
//        Map content = new HashMap();
//        content.put("name", "张三");
//        content.put("age", 30);
//        content.put("addr", "shenzhen,chegongmiao,eastATower33");
//        content.put("title", "报价单");
        List<Person> persons = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            persons.add(
                    new Person("person" + i, 20 + i, "shenzhen,chegongmiao,eastATower3" + i));
        }


        try(InputStream is = new FileInputStream("E:\\idea\\thinkingInJava\\object_collection_template1.xls")) {
            try(OutputStream os = new FileOutputStream("E:\\idea\\thinkingInJava\\object_collection_output.xls")) {
                Context context = new Context();
                context.putVar("persons", persons);
                JxlsHelper.getInstance().processTemplate(is, os, context);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[]args) throws ParseException, IOException {
        List<Employee> employees = generateSampleEmployeeData();
        List<Person> persons = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            persons.add(new Person("person" + i, 20 + i, "shenzhen,chegongmiao,eastATower3" + i));
        }
        try(InputStream is = new FileInputStream("object_collection_template.xls")) {
            try (OutputStream os = new FileOutputStream("object_collection_output.xls")) {
                Context context = new Context();
                context.putVar("employees", employees);
                context.putVar("persons", persons);
                JxlsHelper.getInstance().processTemplate(is, os, context);
            }
        }
    }
    public static List<Employee> generateSampleEmployeeData() throws ParseException {
        List<Employee> employees = new ArrayList<Employee>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MMM-dd", Locale.US);
        employees.add( new Employee("Elsa", dateFormat.parse("1970-Jul-10"), 1500, 0.15) );
        employees.add( new Employee("Oleg", dateFormat.parse("1973-Apr-30"), 2300, 0.25) );
        employees.add( new Employee("Neil", dateFormat.parse("1975-Oct-05"), 2500, 0.00) );
        employees.add( new Employee("Maria", dateFormat.parse("1978-Jan-07"), 1700, 0.15) );
        employees.add( new Employee("John", dateFormat.parse("1969-May-30"), 2800, 0.20) );
        return employees;
    }
}
