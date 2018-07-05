package tdd.mockito_demo;

import org.mockito.ArgumentMatcher;

import java.util.List;

/**
 * 自定义校验规则需要实现ArgumentMatcher<T> 接口
 */
public class ListOfTwoElements implements ArgumentMatcher<List>{

    @Override
    public boolean matches(List list) {
        return list.size() == 2;
    }

    @Override
    public String toString() {
        return "list of 2 elements";
    }
}
