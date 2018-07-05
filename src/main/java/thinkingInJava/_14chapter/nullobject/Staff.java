package thinkingInJava._14chapter.nullobject;

import java.util.ArrayList;

public class Staff extends ArrayList<Position> {
    public void add(String title, Person person) {
        add(new Position(title, person));
    }
    public void add(String... titles) {
        for(String title : titles) {
            add(new Position(title));
        }
    }
    public Staff(String... titles){
        add(titles);
    }

    /**
     * 判断标题为title的position是否为空   true:空  false :非空或集合中不存在
     * @param title
     * @return
     */
    public boolean positionAvailable(String title) {
        for(Position position : this) {
            if(position.getTitle().equals(title) && position.getPerson() == Person.NULL) {
                return true;
            }
        }
        return false;
    }
    public void fillPosition(String title, Person hire) {
        for(Position position : this) {
            if(position.getTitle().equals(title) && position.getPerson() == Person.NULL) {
                position.setPerson(hire);
                return;
            }
        }
        throw new RuntimeException("Position " + title + " not avaliable");
    }

    public static void main(String [] args) {
        Staff staff = new Staff("President", "CTO", "Marketing Manager",
                "Product Manager", "Project Lead", "Software Engineer", "Software Engineer", "Software Engineer",
                "Test Engineer", "Technical Writer");
        staff.fillPosition("President", new Person("Me", "last", "Earch"));
        staff.fillPosition("Project Lead", new Person("Ivan", "Planner", "lalaAddress"));
        if(staff.positionAvailable("Software Engineer")) {
            staff.fillPosition("Software Engineer", new Person("Bob", "Bright", "Bright Light City"));
        }
        System.out.println(staff);
    }

}
