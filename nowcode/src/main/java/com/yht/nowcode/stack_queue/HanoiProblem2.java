package com.yht.nowcode.stack_queue;

import java.util.Stack;

/**
 * 汉诺塔 使用栈实现打印最优步骤和总步数
 * @author yht
 * @create 2020/5/24
 */
public class HanoiProblem2 {

    public static void main(String[] args) {
        System.out.printf("It will move %s steps.", hanoiProblem2(3, "left", "mid", "right"));
    }

    public static int hanoiProblem2(int num, String left, String mid, String right) {
        Stack<Integer> ls = new Stack<>();
        Stack<Integer> ms = new Stack<>();
        Stack<Integer> rs = new Stack<>();

        //ls设置汉诺塔元素
        for(int i = num; i > 0; i--) {
            ls.push(i);
        }

        //一开始
        Action[] preRecord = {Action.No};

        int step = 0;
        //循环，直到元素全部挪动到rs栈中
        while (rs.size() != num) {
            //下面4次操作就是全部的可操作步骤，每次只会有一个满足要求（小压大，相邻不可逆）
            step += fromStackToStack(preRecord, Action.LToM, Action.MToL, ls, ms, left, mid);
            step += fromStackToStack(preRecord, Action.MToL, Action.LToM, ms, ls, mid, left);
            step += fromStackToStack(preRecord, Action.MToR, Action.RToM, ms, rs, mid, right);
            step += fromStackToStack(preRecord, Action.RToM, Action.MToR, rs, ms, right, mid);
        }
        return step;
    }

    /**
     * fStack栈顶元素弹出，压入到tStack
     * @param preAct 前一个动作 这里设置为数组的原因是为了修改数组中的元素，使之对方法调用处的preAct生效，起到了全局变量的作用
     * @param curAct 当前要做的操作
     * @param preNoAct 若当前操作成立，前一个动作不可能的操作
     * @param fStack from塔
     * @param tStack to塔
     * @param from 打印的from
     * @param to 打印的to
     * @return 操作的步数 若没有操作，返回0
     */
    public static int fromStackToStack(
            Action[] preAct, Action curAct,  Action preNoAct,
            Stack<Integer> fStack, Stack<Integer> tStack, String from, String to) {

        //fStack为空 直接返回0步
        if(fStack.isEmpty()) {
            return 0;
        }
        /*
         * 判断前一个操作是否是preNoAct
         * 不是的话，当前curAct才能操作
         */
        if(preAct[0] != preNoAct) {
            //判断fStack栈顶元素是否比tStack栈顶元素小（汉诺塔规则：只能小压大）  tStack为空不需要判断，可以压入
            if(tStack.isEmpty() || fStack.peek() < tStack.peek()) {
                System.out.println("Move " + fStack.peek() + " from " + from + " to " + to);
                tStack.push(fStack.pop());
                preAct[0] = curAct;
                return 1;
            }
        }
        return 0;
    }

}

enum Action {
    No, LToM, MToL, MToR, RToM;
}
