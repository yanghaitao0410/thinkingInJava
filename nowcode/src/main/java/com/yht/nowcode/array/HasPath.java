package com.yht.nowcode.array;

/**
 * dfs问题
 * 请设计一个函数，用来判断在一个矩阵中是否存在一条包含某字符串所有字符的路径。
 * 路径可以从矩阵中的任意一个格子开始，每一步可以在矩阵中向左，向右，向上，向下移动一个格子。
 * 如果一条路径经过了矩阵中的某一个格子，则该路径不能再进入该格子。
 * a   b   t   g
 * c   f   c   s
 * j   d   e   h
 * </br>
 * bfce在数组中可以找到
 */
public class HasPath {


    /**
     * 用递归来实现DFS
     * 1.确定出口：
     * false:1.边界条件不满足，2.当前字符不匹配，3.已经遍历过
     * true：字符串str已经遍历结束
     * 2.递：设置访问过
     * 递归方式，按照上下左右递归
     * @param matrix
     * @param rows
     * @param cols
     * @param str
     * @return
     */
    public boolean hasPath(char[] matrix, int rows, int cols, char[] str) {
        if(matrix == null || matrix.length != rows * cols
                || str == null || str.length == 0
                || str.length > matrix.length) return false;
        boolean[] visited = new boolean[matrix.length];
        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < cols; i++) {//每个节点都有可能是起点
                if(dfs(matrix,rows,cols,str,i,j,0,visited)) {
                    return true;
                }
            }//这里多了个curIndex=0来充当str的索引
        }
        return false;
    }

    //递归开始，真是短啊
    /**
     *
     * @param matrix 矩阵
     * @param rows 矩阵行数
     * @param cols 矩阵列数
     * @param str 要匹配的字符串
     * @param curRow 当前遍历到的行号
     * @param curCol 当前遍历到的列号
     * @param curIndex 当前匹配到字符串的位置，从0开始
     * @param visited
     * @return
     */
    private boolean dfs(char[] matrix, int rows, int cols, char[] str, int curRow, int curCol, int curIndex,
                        boolean[] visited) {
        //若当前位置已经超出边界或已经遍历过或和str中这个位置不相等 返回false
        if(curRow < 0 || curRow >= cols || curCol < 0 || curCol >= rows
                || visited[curRow + curCol * cols] || matrix[curRow + curCol * cols] != str[curIndex])
            return false;
        //程序运行到这里表示当前位置的字符匹配上了，若当前字符已经是最后一位，返回true
        if(curIndex == str.length - 1) return true;
        /*
            二维数组在一维中对应的位置
            例如[1][1]位置 1 + 1 * 4  curRow表示当前行遍历到第几位（从0开始）  curCol * cols 表示上面遍历过行的总数
         */
        visited[curRow + curCol * cols] = true;//递

        //如果当前上下左右有任意位置作为起点遍历满足，返回true
        if(dfs(matrix, rows, cols, str, curRow, curCol - 1, curIndex + 1, visited)
                || dfs(matrix, rows, cols, str, curRow + 1, curCol, curIndex + 1, visited)
                || dfs(matrix, rows, cols, str, curRow, curCol + 1, curIndex + 1, visited)
                || dfs(matrix, rows, cols, str, curRow - 1, curCol, curIndex + 1, visited)) {
            return true;
        }
        //程序运行到这里表示当前位置不满足条件，将当前位置设置为未遍历过
        visited[curRow + curCol * cols] = false;//归
        return false;
    }













}
