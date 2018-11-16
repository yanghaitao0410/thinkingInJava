package code_java.generic;

import java.time.LocalDate;

public class Employee {
    private String name;
    private LocalDate date;
    private int bonus;

    public Employee(String name, int bonus, int year, int month, int day) {
        this.name = name;
        date = LocalDate.of(year, month, day);
        this.bonus = bonus;
    }

    public int getBonus() {
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
}
