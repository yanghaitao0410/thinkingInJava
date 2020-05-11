package com.yht.leetcode;

/**
 * 数独问题
 */
public class Sudoku {

    /**
     *
     * 判断一个 9x9 的填入部分数字的数独是否有效。只需要根据以下规则，验证已经填入的数字是否有效即可。
     *
     * 数字 1-9 在每一行只能出现一次。
     * 数字 1-9 在每一列只能出现一次。
     * 数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。
     *
     * 数独部分空格内已填入了数字，空白格用 '.' 表示。
     * 示例 1:
     * 输入:
     * [
     *   ["5","3",".",".","7",".",".",".","."],
     *   ["6",".",".","1","9","5",".",".","."],
     *   [".","9","8",".",".",".",".","6","."],
     *   ["8",".",".",".","6",".",".",".","3"],
     *   ["4",".",".","8",".","3",".",".","1"],
     *   ["7",".",".",".","2",".",".",".","6"],
     *   [".","6",".",".",".",".","2","8","."],
     *   [".",".",".","4","1","9",".",".","5"],
     *   [".",".",".",".","8",".",".","7","9"]
     * ]
     * 输出: true
     *
     * 示例 2:
     * 输入:
     * [
     *   ["8","3",".",".","7",".",".",".","."],
     *   ["6",".",".","1","9","5",".",".","."],
     *   [".","9","8",".",".",".",".","6","."],
     *   ["8",".",".",".","6",".",".",".","3"],
     *   ["4",".",".","8",".","3",".",".","1"],
     *   ["7",".",".",".","2",".",".",".","6"],
     *   [".","6",".",".",".",".","2","8","."],
     *   [".",".",".","4","1","9",".",".","5"],
     *   [".",".",".",".","8",".",".","7","9"]
     * ]
     * 输出: false
     * 解释: 除了第一行的第一个数字从 5 改为 8 以外，空格内其他数字均与 示例1 相同。
     *      但由于位于左上角的 3x3 宫内有两个 8 存在, 因此这个数独是无效的。
     * 说明:
     *      一个有效的数独（部分已被填充）不一定是可解的。
     *      只需要根据以上规则，验证已经填入的数字是否有效即可。
     *      给定数独序列只包含数字 1-9 和字符 '.' 。
     *      给定数独永远是 9x9 形式的。
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/valid-sudoku
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * @author yht
     * @create 2020/4/13
     */
    public boolean isValidSudoku(char[][] board) {
        char[][] row = new char[9][];
        char[][] col = new char[9][];
        char[][] singlePalace = new char[9][];
        for(int i = 0; i < 9; i++) {
            row[i] = new char[9];
            col[i] = new char[9];
            singlePalace[i] = new char[9];
        }

        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                char c = board[i][j];
                if(c == '.') {
                    continue;
                }
                int num = c - '1';
                if(row[i][num] == c) {
                    return false;
                } else {
                    row[i][num] = c;
                }

                if(col[j][num] == c) {
                    return false;
                } else {
                    col[j][num] = c;
                }

                int palaceIndex = (i / 3) * 3 + j / 3;
                if(singlePalace[palaceIndex][num] == c) {
                    return false;
                } else {
                    singlePalace[palaceIndex][num] = c;
                }
            }
        }
        return true;
    }

    /**
     * 编写一个程序，通过已填充的空格来解决数独问题。
     *
     * 一个数独的解法需遵循如下规则：
         * 数字 1-9 在每一行只能出现一次。
         * 数字 1-9 在每一列只能出现一次。
         * 数字 1-9 在每一个以粗实线分隔的 3x3 宫内只能出现一次。
         * 空白格用 '.' 表示。
     *
     * 要求：
         * 给定的数独序列只包含数字 1-9 和字符 '.' 。
         * 你可以假设给定的数独只有唯一解。
         * 给定数独永远是 9x9 形式的。
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/sudoku-solver
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    public void solveSudoku(char[][] board) {
        backtrack(board, 0, 0);
    }

    private boolean backtrack(char[][] board, int row, int col) {
        if(col == 9) { //列出超过第9列（从0开始） 跳转到下一行的开头
            return backtrack(board, row + 1, 0);
        }
        if(row == 9) { //row 等于9表示 已经全部遍历完了 说明找到了唯一解 直接停止递归
            return true;
        }

        if(board[row][col] != '.') { //当前位置有预填数 跳过
            return backtrack(board, row, col + 1);
        }

        for(char i = '1'; i <= '9'; i++) {
            if (!isVerify(board, row, col, i)) {
                continue;
            }
            board[row][col] = i;
            if(backtrack(board, row, col + 1)) { // 如果找到一个可行解，立即结束
                return true;
            }
            board[row][col] = '.';
        }
        // 穷举完 1~9，依然没有找到可行解，此路不通
        return false;
    }

    private boolean isVerify(char[][] board, int row, int col, char ch) {
        for(int i = 0; i < 9; i++) {
            if(board[row][i] == ch) { //判断row行没有ch
                return false;
            }
            if(board[i][col] == ch) { //判断col列没有ch
                return false;
            }

            //判断小方格中没有ch  行为(row/3)*3 + i/3  列为(col/3)*3 + i%3
            if(board[(row/3)*3 + i/3][(col/3)*3 + i%3] == ch) {
                return false;
            }
        }
        return true;
    }

}
