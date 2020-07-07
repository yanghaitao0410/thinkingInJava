package com.yht.nowcode.stack_queue;

/**
 * 用栈来求解汉诺塔问题
 * 修改规则：不能从最左侧塔直接移动到最右侧塔，也不能从最右侧塔移动到最左侧塔，必须经过中间塔
 * <p>
 * 求：当塔有N层的时候，打印最优过程和最优移动步数
 * 例如：当有2层时，最上层记为1，最下层记为2，则打印：
 * Move 1 from left to mid
 * Move 1 from mid to right
 * Move 2 from left to mid
 * Move 1 from right to mid
 * Move 1 from mid to left
 * Move 2 from mid to right
 * Move 1 from left to mid
 * Move 1 from mid to right
 * It will move 8 steps
 *
 * @author yht
 * @create 2020/5/17
 */
public class HanoiProblem {

    public static void main(String[] args) {
        System.out.println("It will move " + hanoiProblem1(2, "left", "mid", "right") + "steps.");
    }

    public static int hanoiProblem1(int num, String left, String mid, String right) {
        if (num < 1) {
            return 0;
        }
        return process(num, left, mid, right, left, right);
    }

    private static int process(int num, String left, String mid, String right, String from, String to) {
        //递归终止处理
        if (num == 1) {
            //from to中有在mid的元素，一步走完
            if (from.equals(mid) || to.equals(mid)) {
                System.out.println("Move 1 from " + from + " to " + to);
                return 1;
            } else { //没有mid元素，需要2步
                System.out.println("Move 1 from " + from + " to " + mid);
                System.out.println("Move 1 from " + mid + " to " + to);
                return 2;
            }
        }

        //from to中有在mid的元素
        if (from.equals(mid) || to.equals(mid)) {
            //获取到此时的辅助塔
            String help = (from.equals(left) || to.equals(left)) ? right : left;

            //先把上面num - 1个元素移动到辅助塔
            int part1 = process(num - 1, left, mid, right, from, help);
            //把num的移动到指定位置
            System.out.println("Move " + num + " from " + from + " to " + mid);
            int part2 = 1;
            //把num-1个元素从辅助塔移动到指定位置
            int part3 = process(num - 1, left, mid, right, help, to);
            //返回所有移动步数
            return part1 + part2 + part3;
        } else { //把num个元素从left移动到right 或相反 没有mid，下面的注释以从left到right举例
            //先把num-1个元素从left移动到right
            int part1 = process(num - 1, left, mid, right, from, to);
            //num从left移动到mid
            System.out.println("Move " + num + " from " + from + " to " + mid);
            int part2 = 1;
            //num-1个元素从right移动到left
            int part3 = process(num - 1, left, mid, right, to, from);
            //num从mid移动到right
            System.out.println("Move " + num + " from " + mid + " to " + to);
            int part4 = 1;
            //num-1个元素从left移动到right
            int part5 = process(num - 1, left, mid, right, from, to);
            //返回所有移动步数
            return part1 + part2 + part3 + part4 + part5;
        }
    }

}
