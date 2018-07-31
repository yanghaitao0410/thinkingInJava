package com.yht.nowcode.tree.trie_tree;

public class TrieTreeNode {


    //当前节点字符经过数量
    public int pass;
    //以当前节点作为结尾的字符串的数量
    public int end;

    public TrieTreeNode[] nextNodes;

    public TrieTreeNode() {

        pass = 0;
        end = 0;
        /**
         * 此前缀树所保存的字符串都是由小写字母组成
         */
        nextNodes = new TrieTreeNode[26];
    }

}
