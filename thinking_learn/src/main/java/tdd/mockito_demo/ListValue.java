package tdd.mockito_demo;

import org.mockito.ArgumentMatcher;

import java.util.List;
import java.util.Random;

public class ListValue implements ArgumentMatcher<List> {
    @Override
    public boolean matches(List list) {
        return "element".equals(list.get(new Random().nextInt(10)));
    }
}
