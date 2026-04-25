import java.util.*;
public class Main {
    public static void main(String[] args) {

    }

}
//題目:整数数组 nums 和一个整数 k ，请你返回其中出现频率前 k 高的元素
//這其實是一個「Top K 模板」，以後遇到：
//                               Top K Frequent Words
//                                   K Closest Points
//                               Top K Largest
//幾乎都能套這個模板。
//Min Heap + 限制大小 = 保留最大的 k 个元素
//時間複雜度O(n x log k)，n是数组的长度
//空间复杂度O(k)
class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        //STEP1:統計每個數字出現次數
        Map<Integer, Integer> cnt = new HashMap<>();//<數字, 出現次數>
        for (int x : nums) {
            //cnt.merge(x, 1, Integer::sum);等價下方的ifelse
            if (!cnt.containsKey(x)) {
                cnt.put(x, 1);
            } else {
                cnt.put(x, cnt.get(x) + 1);
            }
        }
        //STEP2:建立最小堆 (Min Heap)
        //為什麼用「最小堆」？ ==> 因為我們只需要保留「前 k 大」==>(堆顶永远是：当前最小频率) + (删除最小的) = 保留最大的 k 个元素
        //                      <數字, 出現次數>                             依「出現次數」排序，而且是"小"的在前面（最小堆）
        PriorityQueue<Map.Entry<Integer, Integer>> pq = new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));
        //Map.Entry就是"1"個「key + value」的物件，有2個方法
        //Map.Entry<Integer, Integer> e = (1,3)
        //e.getKey();   // 1
        //e.getValue(); // 3
        //像是nums = [1,1,1,2,2,3]則pq會存(1,3),(2,2),(3,1)
        //每個元素就丟進 heap，如果 heap 超過 k ，就把最小的丟掉
        for (var e : cnt.entrySet()) {//cnt.entrySet功能：把Map內所有的東西變成「一組一組 key+value」。像是變成：(1,3)(2,2)(3,1)
            pq.offer(e);
            if (pq.size() > k) {
                pq.poll();
            }
        }
        return pq.stream().mapToInt(Map.Entry::getKey).toArray();
        //變成資料流，一個一個拿出來處理.  對每個entry::只取key
    }
}
/*
👉 Min Heap 的作用不是“找最小”
👉 而是：快速淘汰最小的
(1)PriorityQueue流程說明:
🔵 第一個 e = (1,3)
pq.offer((1,3));
heap:[(1,3)]
size = 1,沒有超過 k，故不用刪

🔵 第二個 e = (2,2)
pq.offer((2,2));
heap 會自動排序（小的在前）：
(前)[(2,2), (1,3)](後)
size = 2,沒有超過 k，故不用刪

🔵 第三個 e = (3,1)
pq.offer((3,1));
heap 變：(前)[(3,1), (1,3), (2,2)](後)
⚠️ 現在 size = 3
超過了，所以這行會執行：
pq.poll();會刪掉「出現次數最小的」
最小的是：(3,1)
刪掉後 heap：[(2,2), (1,3)]
效果是：永遠只保留「出現次數最大的 k 個」

🟢 最後這行在幹嘛？
return pq.stream().mapToInt(Map.Entry::getKey).toArray();
pq 裡現在是：[(2,2), (1,3)]

這行會：
1️⃣ 轉成 stream
2️⃣ 只拿 key（數字）
3️⃣ 變成 int[]

最後回傳：[2,1]

(2)觀念:
🔷 什麼是 Heap？
Heap（堆）是一種：
    完全二元樹（Complete Binary Tree）
    且滿足某種「大小規則」的資料結構
🔹 什麼叫完全二元樹？
    除了最後一層，全部滿
    最後一層從左到右填滿
    例如：
            5
          /   \
         3     4
        / \
       1   2
     這就是完全二元樹。

🔷 Heap 的核心特性
    Heap 只保證一件事：父節點 和 子節點之間有大小關係。但它不保證整棵樹有排序
    ⚠️ Heap ≠ 排序樹（不是 BST）
🔷 Min Heap（最小堆） 口訣:小子最大
    規則：每個子節點 ≥ 父節點
    常用場景:找最小、維護前K大
    例子
            1
          /   \
         3     4
        / \
       5   6
    根節點一定是最小值

🔷 Max Heap（最大堆）
    規則：每個父節點 ≥ 子節點
    常用場景:找最大、維護前K小
    例子
            9
          /   \
         7     6
        / \
       3   2
    根節點一定是最大值

🔷 Heap 怎麼存？
實際上 Heap 是用陣列存的，不是用 Tree Node。
陣列索引關係:
    假設index = i
    左子節點  = 2i + 1
    右子節點  = 2i + 2
    父節點    = (i - 1) / 2
    例如：[1, 3, 4, 5, 6]
    會變成：
            1
          /   \
         3     4
        / \
       5   6

🔷 Heap 的兩個核心操作
    1️⃣ 插入 (offer)
    插到最後
    然後往上調整（heapify up）
    時間：O(log n)
    2️⃣ 刪除頂端 (poll)
    把最後一個補到最上面
    往下調整（heapify down）
    時間：O(log n)

🔷 Java 裡的 Heap
Java 沒有叫 Heap 的類別==>所以用PriorityQueue

🔹 Java 預設是 Min Heap
    PriorityQueue<Integer> pq = new PriorityQueue<>();  (最小的會在最上面)
🔹 如果要 Max Heap
    PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
    或
    PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a);

🔥 為什麼 Top K 要用 Min Heap？
    假設要找前 3 大：
    我們用 Min Heap：
    裡面只存 3 個
    最小的在上面
    新元素比最小的大 → 取代它
    這樣時間是：O(n log k)
    如果用 Max Heap：要存全部 + 再取 k 次
    時間：O(n log n)

🔥 面試會考什麼？
1️⃣ 為什麼 heap 是 log n？
→ 因為樹高度是 log n
🎯 一句話理解
Heap 就是：一種可以快速拿到「最大或最小」元素的資料結構

(3)
Q:PriorityQueue也是存(1,3),(2,2),(3,1)，那幹嘛還要寫Map.Entry?
A:PriorityQueue 可以存 (1,3),(2,2),(3,1)
但：
  ❗ PriorityQueue 只負責「排序」
  ❗ Map 才負責「key → value 對應關係」

而 Map.Entry 只是把「key + value」當成一個物件丟進 PriorityQueue 而已。
故正確步驟一定是：
   (1)先用 Map 統計次數
   (2)再丟進 PriorityQueue 排序
      [PriorityQueue 只能存「元素」，所以要存(key, value)]
(4)
🧩 用例子带你走一遍
假设频率是：👉 我们要找 Top 3
元素:  a   b   c   d   e
频率:  5   3   2   8   6
Step 1：加入 a(5)
    [5]
Step 2：加入 b(3)
    [3, 5]   // 小顶堆，3在顶部
Step 3：加入 c(2)
    [2, 5, 3]
Step 4：加入 d(8)
    [2, 5, 3, 8]  // 超过 k=3
    → 删除最小的 2
    → 剩下 [3, 5, 8]
Step 5：加入 e(6)
    [3, 5, 8, 6]  // 超过 k
    → 删除最小的 3
    → 剩下 [5, 6, 8]
✅ 最终结果
    [5, 6, 8] → Top 3 最大频率
(5)
Q:為何在Step 3：加入 c(2)之後變成[2, 5, 3]而非[2, 3, 5]
A:这是个很经典的误区：👉 你把 Heap 当成“排序数组”了 
其实 PriorityQueue（堆）≠ 完全有序结构
👉结论先说:[2, 5, 3] 和 [2, 3, 5]在 Heap 里都是合法的！
*/
