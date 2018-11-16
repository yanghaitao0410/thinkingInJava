package code_java.generic;

public class PairTest {

    public static void main(String[] args) {
        Manager ceo = new Manager("Gus Greedy", 800000, 2003, 12, 15);
        Manager cfo = new Manager("Sid Sneaky", 600000, 2003, 12, 15);
        Pair<Manager> buddies = new Pair<>(ceo, cfo);
        printBuddies(buddies);

        ceo.setBonus(1000000);
        cfo.setBonus(500000);

        Manager[] managers = {ceo, cfo};
        Pair<Employee> result = new Pair<>();
        minmaxBonus(managers, result);
//        需要强转
//        Pair<Employee> result2 = (Pair<Employee>) minmaxBonus2(managers);
//        System.out.println("first: " + result2.getFirst().getName() + ", second: " + result2.getSecond().getName());

        System.out.println("first: " + result.getFirst().getName() + ", second: " + result.getSecond().getName());
        maxminBonus(managers, result);
        System.out.println("first: " + result.getFirst().getName() + ", second: " + result.getSecond().getName());
    }

    private static void maxminBonus(Manager[] a, Pair<? super Manager> result) {
        minmaxBonus(a, result);
        PairAlg.swapHelper(result);
    }

    /**
     * 不能返回Pair
     * @param a
     * @param result
     */
    private static void minmaxBonus(Manager[] a, Pair<? super Manager> result) {
        if(a.length == 0)
            return;
        Manager min = a[0];
        Manager max = a[0];
        for(int i = 0; i < a.length; i++) {
            if(min.getBonus() > a[i].getBonus())
                min = a[i];
            if(max.getBonus() < a[i].getBonus())
                max = a[i];
        }
        result.setFirst(min);
        result.setSecond(max);
    }

    private static Pair<? super Manager> minmaxBonus2(Manager[] a) {
        Pair<? super Manager> result = new Pair<>();
        if(a.length == 0)
            return null;
        Manager min = a[0];
        Manager max = a[0];
        for(int i = 0; i < a.length; i++) {
            if(min.getBonus() > a[i].getBonus())
                min = a[i];
            if(max.getBonus() < a[i].getBonus())
                max = a[i];
        }
        result.setFirst(min);
        result.setSecond(max);
        return result;
    }


    /**
     * 可传入泛型为Employee或继承Employee的p
     * @param p
     */
    private static void printBuddies(Pair<? extends Employee> p) {
        Employee first = p.getFirst();
        Employee second = p.getSecond();
        System.out.println(first.getName() + " and " + second.getName() + " are buddies.");
    }
}
