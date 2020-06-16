package code_java.collection;

import code_java.model.Person;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;


/**
 * @Desc
 * @Author water
 * @date 2020/6/9
 **/
public class HashMapLearn {

    @Test
    public void test1(){
        //对象的hashcode作为key 模拟HashMap内部存储，在key相同的情况下，各个value是通过链表的方式连接在一起的
        Map<Integer, List<Person>> bucketMap = new HashMap<>();

        for (int i = 20; i <= 30; i++) {
            Person person = new Person("Tina", i);
            int hashCode = person.hashCode();
            List<Person> persons = bucketMap.get(hashCode);
            if (persons == null) {
                persons = new ArrayList<>();
                bucketMap.put(hashCode, persons);
            }
            persons.add(person);
        }
        bucketMap.forEach((k, v) -> {
            System.out.println("hashcode: " + k);
            v.forEach(x -> System.out.println("        " + x));
        });
    }

    /**
     * HashMap在内部将每个hashCode组映射到一个bucket索引，如下代码所示:
     */
    @Test
    public void test2() {
        List<Person> firstList = new ArrayList<>();
        int hashCode = 0;
        for (int i = 20; i <= 30; i++) {
            Person person = new Person("Tina", i);
            //相同的hash只添加一次
            int hash = hash(person.hashCode());
            if (hashCode != hash) {
                firstList.add(person);
                hashCode = hash;
            }
        }

        for (Person person : firstList) {
            int hash = hash(person.hashCode());
            //HashMap的初始大小：16
            int n = 1 << 4;
            //HashMap's hash to bucket index formula
            //HashMap的hash到bucket的索引公式
            int index = (n - 1) & hash;
            //运行结果说明从20~30 只索引了3个桶
            System.out.printf("index: %s, hash: %s%n", index, hash);
        }
    }

    /**
     * internally used by HashMap
     */
    final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    /**
     * 让我们使用反射来查看HashMap的bucket细节。
     */
    @Test
    public void test3() throws Exception {
        Map<Person, Integer> personSalaryMap = new HashMap<>();
        for (int i = 20; i <= 30; i++) {
            Person person = new Person("Tina", i);
            personSalaryMap.put(person, ThreadLocalRandom.current().nextInt(100, 1000));
        }
        printHashMapBuckets(personSalaryMap);
    }

    private void printHashMapBuckets(Map<Person, Integer> personSalaryMap) throws Exception {
        //HashMap#table field = array of bucket
        Field bucketArrayField = HashMap.class.getDeclaredField("table");
        bucketArrayField.setAccessible(true);
        Object[] buckets = (Object[]) bucketArrayField.get(personSalaryMap);
        System.out.println("Number of buckets: " + buckets.length);
        for (int i = 0; i < buckets.length; i++) {
            Object node = buckets[i];
            if (node == null) {
                continue;
            }
            System.out.printf("\n-- bucket index: %s --%n", i);
            print(1, "Node: " + node);
            printNode(node, 1);
        }
    }

    private void printNode(Object node, int level) throws IllegalAccessException {

        for (Field declaredField : node.getClass().getDeclaredFields()) {
            declaredField.setAccessible(true);
            String fieldName = declaredField.getName();
            Object value = declaredField.get(node);
            if (fieldName.equals("next")) {
                if (value == null) {
                    continue;
                }
                print(level, "Next Node:" + value);
                printNode(value, level + 1);
                continue;
            }
            print(level, fieldName + " = " + value);
        }
    }

    private void print(int level, String string) {
        System.out.printf("%" + (level * 4 - 3) + "s|- %s%n", "", string);
    }
}
