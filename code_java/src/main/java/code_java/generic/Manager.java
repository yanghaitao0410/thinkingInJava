package code_java.generic;

import java.io.Serializable;

public class Manager extends Employee implements Serializable {

    private static final long serialVersionUID = -7101341220910557623L;
    //秘书
    private Employee secretary;

    public Manager(String userName, int bonus, int year, int month, int day) {
        super(userName, bonus, year, month, day);
    }

    public Employee getSecretary() {
        return secretary;
    }

    public void setSecretary(Employee secretary) {
        this.secretary = secretary;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "secretary=" + secretary +
                "} " + super.toString();
    }
}
