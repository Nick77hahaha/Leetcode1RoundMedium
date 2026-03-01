import java.util.*;
public class Main {
    public static void main(String[] args) {

    }
}
//題目:给你二叉树的根节点root，返回其节点值的层序遍历 (return the level order traversal of its nodes' values.)
//            3
//          /   \
//         9     20
//              /  \
//             15   7
//输入：root = [3,9,20,null,null,15,7]
//输出：[[3],[9,20],[15,7]]
//時間複雜度O(n)，n:二叉树的节点个数
//空間複雜度O(n)

//BFS
class Solution {
    public List<List<Integer>> levelOrder(TreeNode root) {//一層一層走，每一層是一個 list
        List<List<Integer>> ans = new ArrayList<>();
        if (root == null) {
            return ans;
        }
        Deque<TreeNode> q = new ArrayDeque<>();
        q.offer(root);//把節點 3 這個TreeNode物件放進queue，此時 queue 裡是Queue: [3] [不是傳陣列進去]
        while (!q.isEmpty()) {//只要 queue 還有節點就繼續。
            List<Integer> t = new ArrayList<>();//t:代表當前這一層的所有節點值
            //為何一定要用q.size()開始然後慢慢扣(好像是鎖定當前層大小)?
            //因為queue會一直加子節點，如果不固定size，你會把下一層也算進來
            for (int n = q.size(); n > 0; --n) {//觀念跑1次:queue裡是Queue: [3], ==>故n = 1
                TreeNode node = q.poll();       //取出 3
                t.add(node.val);                //3是這層的其中1個節點值 [這裡是加入t裡，下面是加入q裡!!]
                if (node.left != null) {
                    q.offer(node.left);         //加入 9, 20
                }
                if (node.right != null) {
                    q.offer(node.right);        //加入 9, 20
                }
            }
            ans.add(t);
        }
        return ans;
    }
}
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode() {    }
    TreeNode(int val) {
        this.val = val;
    }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}
/*
1.Deque 同時支援：[這就是為什麼方法很多]
1️⃣ Queue（佇列,隊）:[先進先出（FIFO）] [會加入尾端&前端拿出] （BFS 常用）
    加入	offer()
    取出	poll()              口訣:Q阿剌
2️⃣ Stack（堆疊）:   [後進先出（LIFO）] [會加入前端&前端拿出]（DFS 常用）
    加入	push()
    取出	pop()
3️⃣ Double-ended queue（雙端操作）:
    前端加入	addFirst()
    後端加入	addLast()
2.這其實是 BFS 標準模板:
while (!q.isEmpty()) {
    int size = q.size();
    for (int i = 0; i < size; i++) {
        TreeNode node = q.poll();
        ...
    }
}
3.

*/