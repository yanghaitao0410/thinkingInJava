package com.yht.nowcode.tree.trie_tree;

/**
 * 前缀树
 * trieTree
 * prefixTree
 */
public class TrieTree {

    private TrieTreeNode root;

    public TrieTree () {
        root = new TrieTreeNode();
    }

    /**
     * 向前缀树中添加字符串
     */
    public void insert(String word) {

        if(word == null) {
            return;
        }
        TrieTreeNode node = root;
        char[] charArr = word.toCharArray();
        int index = 0;
        for(int i = 0; i < charArr.length; i++) {
            index = charArr[i] - 'a';
            //树中没有当前节点，创建一个新节点
            if(node.nextNodes[index] == null) {
                node.nextNodes[index] = new TrieTreeNode();
            }
            //从前缀树中取出相应的节点
            node = node.nextNodes[index];
            node.pass++; //匹配上的节点的pass++
        }
        node.end++; //此时字符串已经到了结尾，当前节点的end++
    }

    /**
     * 查询前缀树中是否包含某字符串
     * @param word
     * @return
     */
    public boolean search(String word) {

        if(word == null) {
            return false;
        }
        TrieTreeNode node = root;
        char[] charArr = word.toCharArray();
        int index;
        for(int i = 0; i < charArr.length; i++) {
            index = charArr[i] - 'a';
            if(node.nextNodes[index] == null) {
                return false;
            }
            node = node.nextNodes[index];
        }
        return node.end >= 0;
    }

    /**
     * 从前缀树中删除字符串
     * @param word
     */
    public void delete(String word) {
        if(search(word)) {
            TrieTreeNode node = root;
            char[] charArr = word.toCharArray();
            int index;
            for(int i = 0; i < charArr.length; i++) {
                index = charArr[i] - 'a';
                if(--node.nextNodes[index].pass == 0) {
                    node.nextNodes[index] = null;
                    return;
                }
                node = node.nextNodes[index];
            }
            node.end--;
        }
    }

    /**
     * 查找前缀树种以pre开头的字符串的个数
     * @param pre
     * @return
     */
    public int preCount(String pre) {

        if(pre == null) {
            return 0;
        }
        TrieTreeNode node = root;
        char[] charArr = pre.toCharArray();
        int index;
        for(int i = 0; i < charArr.length; i++) {
            index = charArr[i] - 'a';
            if(node.nextNodes[index] == null) {
                return 0;
            }
            node = node.nextNodes[index];
        }
        return node.pass;
    }


    public static void main(String[] args) {
        TrieTree trie = new TrieTree();
        System.out.println(trie.search("zuo"));
        trie.insert("zuo");
        System.out.println(trie.search("zuo"));
        trie.delete("zuo");
        System.out.println(trie.search("zuo"));
        trie.insert("zuo");
        trie.insert("zuo");
        trie.delete("zuo");
        System.out.println(trie.search("zuo"));
        trie.delete("zuo");
        System.out.println(trie.search("zuo"));
        trie.insert("zuoa");
        trie.insert("zuoac");
        trie.insert("zuoab");
        trie.insert("zuoad");
        trie.delete("zuoa");
        System.out.println(trie.search("zuoa"));
        System.out.println(trie.preCount("zuo"));

    }








}
