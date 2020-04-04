package com.yht.leetcode;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 将一个给定字符串根据给定的行数，以从上往下、从左到右进行 Z 字形排列。
 * <p>
 * 比如输入字符串为 "LEETCODEISHIRING" 行数为 3 时，排列如下：
 * <p>
 * L   C   I   R
 * E T O E S I I G
 * E   D   H   N
 * 之后，你的输出需要从左往右逐行读取，产生出一个新的字符串，比如："LCIRETOESIIGEDHN"。
 * <p>
 * 请你实现这个将字符串进行指定行数变换的函数：
 * <p>
 * string convert(string s, int numRows);
 * 示例 1:
 * <p>
 * 输入: s = "LEETCODEISHIRING", numRows = 3
 * 输出: "LCIRETOESIIGEDHN"
 * 示例 2:
 * <p>
 * 输入: s = "LEETCODEISHIRING", numRows = 4
 * 输出: "LDREOEIIECIHNTSG"
 * 解释:
 * <p>
 * L     D     R
 * E   O E   I I
 * E C   I H   N
 * T     S     G
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/zigzag-conversion
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author yht
 * @create 2020/3/8
 */
public class Z_stringConvert {

    @Test
    public void test() {
        Assert.assertEquals("LCIRETOESIIGEDHN", convert1("LEETCODEISHIRING", 3));
        Assert.assertEquals("LDREOEIIECIHNTSG", convert1("LEETCODEISHIRING", 4));
        Assert.assertEquals("ABC", convert1("ABC", 1));
    }

    /**
     * 方法1 创建二维数组 然后控制row和col的下标，将string中char放到数组中 然后按行遍历拼接
     * @param s
     * @param numRows
     * @return
     */
    public String convert(String s, int numRows) {
        if(s == null || s.equals(""))
            return "";
        if(numRows == 1) {
            return s;
        }
        int length = s.length();
        Character[][] charArr = new Character[numRows][length];
        for(int i = 0; i < numRows; i++) {
            charArr[i] = new Character[length];
        }
        boolean up = true;
        int row = 0,  //行
                col = 0; //列
        char[] chars = s.toCharArray();
        for (int i = 0; i < length; i++) {
            charArr[row][col] = chars[i];

            if (up && row < numRows) { //从上往下排列
                if (row == numRows - 1) { //到达了最下一层
                    up = false;
                    if(row > 0) {
                        row--;
                    }
                    col++;
                } else { //未到达最下一层
                    row++;
                }
            } else if (!up && row >= 0) { //从左往右排列
                if (row == 0) { //到达了最上一层
                    up = true;
                    row++;
                } else {
                    if(row > 0) {
                        row--;
                    }
                    col++;
                }
            }
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < numRows; i++) {
            if(up && row <= numRows) { //结束位置还处于向下移动 并且没有到最低层 需要遍历到col列
                for (int j = 0; j <= col; j++) {
                    if (charArr[i][j] != null) {
                        builder.append(charArr[i][j]);
                    }
                }
            } else { //结束位置处于向右移动 不需要遍历到col列
                for (int j = 0; j < col; j++) {
                    if (charArr[i][j] != null) {
                        builder.append(charArr[i][j]);
                    }
                }
            }

        }
        return builder.toString();
    }

    /**
     * 官方按行排序算法
     * 思路
     *
     * 通过从左向右迭代字符串，我们可以轻松地确定字符位于 Z 字形图案中的哪一行。
     *
     * 算法
     *
     * 我们可以使用 Math.min(numRows,s.length) 个列表来表示 Z 字形图案中的非空行。
     *
     * 从左到右迭代 s，将每个字符添加到合适的行。可以使用当前行和当前方向这两个变量对合适的行进行跟踪。
     *
     * 只有当我们向上移动到最上面的行或向下移动到最下面的行时，当前方向才会发生改变。
     *
     * @param s
     * @param numRows
     * @return
     */
    public String convert1(String s, int numRows) {
        if(numRows == 1) {
            return s;
        }
        List<StringBuilder> rows = new ArrayList<>();
        for(int i = 0; i < Math.min(numRows, s.length()); i++) {
            rows.add(new StringBuilder());
        }

        int curRow = 0;
        boolean goingDown = false;

        for(char c : s.toCharArray()) {
            rows.get(curRow).append(c);
            if(curRow == 0 ||curRow == numRows - 1) { //当前行在[0, Math.min(numRows, s.length() - 1] 中间来回移动
                goingDown = !goingDown;
            }
            curRow += goingDown ? 1 : -1;
        }
        StringBuilder ret = new StringBuilder();
        for (StringBuilder row : rows) ret.append(row);
        return ret.toString();
    }
}
