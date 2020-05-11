package tdd;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import tdd.mockito_demo.ListOfTwoElements;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class MockitoDemo1 {
    public List<String> mockedList;

    @Before
    public void mockTestBefore() {
        mockedList = mock(List.class);
    }

    /**
     * 校验对象的增加和清除
     */
    @Test
    public void mockObjectTest() {

        mockedList.add("one");
        mockedList.clear();

        Mockito.verify(mockedList).add("one");
        Mockito.verify(mockedList).clear();
    }

    @Test
    public void verifyMethodTest() {
        Mockito.when(mockedList.get(0)).thenReturn("first");
        Mockito.when(mockedList.get(1)).thenThrow(new RuntimeException());

        System.out.println(mockedList.get(0));

        //System.out.println(mockList.get(1));

        System.out.println(mockedList.get(999));

        /**
         * 验证某一方法在前面调用过一次
         */
        Mockito.verify(mockedList).get(0);
        Mockito.verify(mockedList).get(999);
    }

    /**
     * 自定义校验规则
     */
    @Test
    public void matcherArgsTest() {
        Mockito.when(mockedList.get(Mockito.anyInt())).thenReturn("element");

        Mockito.when(mockedList.addAll(Mockito.argThat(new ListOfTwoElements()))).thenReturn(true);

        mockedList.addAll(Arrays.asList("one", "two"));

        System.out.println(mockedList.get(0));
        Assert.assertEquals(mockedList.get(0), "element");

        Mockito.verify(mockedList).addAll(Mockito.argThat(new ListOfTwoElements()));


    }

    /**
     * 对方法的调用次数进行校验
     */
    @Test
    public void timesTest() {
        //using mock
        mockedList.add("once");

        mockedList.add("twice");
        mockedList.add("twice");

        mockedList.add("three times");
        mockedList.add("three times");
        mockedList.add("three times");

        //following two verifications work exactly the same - times(1) is used by default
        Mockito.verify(mockedList).add("once");
        Mockito.verify(mockedList, Mockito.times(1)).add("once");

        //exact number of invocations verification
        Mockito.verify(mockedList, Mockito.times(2)).add("twice");
        Mockito.verify(mockedList, Mockito.times(3)).add("three times");

        //verification using never(). never() is an alias to times(0)
        Mockito.verify(mockedList, Mockito.never()).add("never happened");

        //verification using atLeast()/atMost()
        Mockito.verify(mockedList, Mockito.atLeastOnce()).add("three times");
        Mockito.verify(mockedList, Mockito.atLeast(2)).add("three times");
        Mockito.verify(mockedList, Mockito.atMost(5)).add("three times");
    }

    /**
     * 指定调用某方法后抛出异常
     */
    @Test
    public void doThrowTest() {
        Mockito.doThrow(new RuntimeException()).when(mockedList).clear();
        mockedList.clear();
    }

    /**
     * 校验某些方法必须按照顺序调用
     */
    @Test
    public void inOrderTest() {
        // A. Single mock whose methods must be invoked in a particular order
        List singleMock = mock(List.class);

        //using a single mock
        singleMock.add("was added first");
        singleMock.add("was added second");

        //create an inOrder verifier for a single mock
        InOrder inOrder = inOrder(singleMock);

        //following will make sure that add is first called with "was added first, then with "was added second"
        inOrder.verify(singleMock).add("was added first");
        inOrder.verify(singleMock).add("was added second");

        // B. Multiple mocks that must be used in a particular order
        List firstMock = mock(List.class);
        List secondMock = mock(List.class);

        //using mocks
        firstMock.add("was called first");
        secondMock.add("was called second");

        //create inOrder object passing any mocks that need to be verified in order
        inOrder = inOrder(firstMock, secondMock);

        //following will make sure that firstMock was called before secondMock
        inOrder.verify(firstMock).add("was called first");
        inOrder.verify(secondMock).add("was called second");

        // Oh, and A + B can be mixed together at will
    }

    /**
     * 校验方法没有被调用 verify(mockOne, never()).add("two");
     * 两个对象在前面没有调用 verifyZeroInteractions(mockTwo, mockThree);
     */
    @Test
    public void interactionTest() {
        List mockOne = Mockito.mock(List.class);
        List mockTwo = Mockito.mock(List.class);
        List mockThree = Mockito.mock(List.class);

        mockOne.add("one");
        verify(mockOne).add("one");
        verify(mockOne, never()).add("two");

        //mockTwo.add("Two");
        //mockOne.addAll(mockTwo);

        verifyZeroInteractions(mockTwo, mockThree);
    }

    @Test
    public void interactionTest2() {
        List mockedList = Mockito.mock(List.class);
        mockedList.add("one");
        mockedList.add("two");

        verify(mockedList).add("one");
        verify(mockedList).add("two");

        //断言该实例除了上面的验证之外没有其他的调用了
        verifyNoMoreInteractions(mockedList);
    }

}
