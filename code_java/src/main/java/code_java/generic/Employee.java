package code_java.generic;

import java.io.Serializable;
import java.time.LocalDate;

public class Employee implements Serializable {

    private static final long serialVersionUID = -3331088854939486760L;

    private String name;
    private LocalDate date;
    private double bonus;

    public Employee(String name, double bonus, int year, int month, int day) {
        this.name = name;
        date = LocalDate.of(year, month, day);
        this.bonus = bonus;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", date=" + date +
                ", bonus=" + bonus +
                '}';
    }
}
