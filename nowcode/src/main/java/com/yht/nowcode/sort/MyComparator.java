package com.yht.nowcode.sort;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MyComparator implements Comparator<Student> {

    @Override
    public int compare(Student o1, Student o2) {
        return o2.getId() - o1.getId();
    }

    @Test
    public void test() {
        Student s1 = new Student(1, "student1", 50);
        Student s2 = new Student(2, "student2", 21);
        Student s3 = new Student(3, "student3", 20);
        List<Student> students = new ArrayList<> ();
        students.add(s1);
        students.add(s2);
        students.add(s3);
        Collections.sort(students, new MyComparator());
        System.out.println(students);
    }
}
