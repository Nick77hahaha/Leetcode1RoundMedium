import java.util.*;
public class Main {
    public static void main(String[] args) {

    }
}
//題目:设计當容量滿了，刪掉「最久沒被用」的資料的数据结构
//核心設計思想: HashMap負責快速查找，LinkedList負責維護使用順序
//時間複雜度O(1)，
//空間複雜度O(capacity)，最多存 capacity 個節點
class Node {
    int key, val;
    Node prev, next;
    Node() {    }
    Node(int key, int val) {
        this.key = key;
        this.val = val;
    }
}

class LRUCache {
    private int size;
    private int capacity;
    //dummy node（2個虛擬節點）:
    private Node head = new Node();
    private Node tail = new Node();
    private Map<Integer, Node> cache = new HashMap<>();//用key快速找到對應的「節點本體」
    //HashMap長這樣：  1 → A節點
    //               2 → B節點
    //               3 → C節點

    public LRUCache(int capacity) {//這是Constructor，當你new一個物件時，會自動執行的特殊方法。
        this.capacity = capacity;
        head.next = tail;
        tail.prev = head;
        //初始化時：head.next = tail;
        //        tail.prev = head;
        //變成：head <-> tail
    }

    //流程：
    //1️⃣ 如果 key 不在 → 回 -1
    //2️⃣ 找到 node
    //3️⃣ 把它移到 head 後面（表示最新使用）
    //4️⃣ 回傳 value
    //為什麼要移到前面？ ==>因為它剛被用。
    //搞清楚現在是從cache還是node內，get東西出來!!
    public int get(int key) {
        if (!cache.containsKey(key)) {
            return -1;
        }
        Node node = cache.get(key);//就是把那個節點抓出來。
        removeNode(node);
        addToHead(node);
        return node.val;
    }

    //情況 1️⃣ key 已存在==>更新value，然後把它移到最前面
    //情況 2️⃣ key 不存在==>向缓存中插入新節點到最前面，然後檢查容量。如果超過容量：刪掉最久沒用的(tail.prev)
    public void put(int key, int value) {
        if (cache.containsKey(key)) {
            Node node = cache.get(key);
            removeNode(node);
            node.val = value;
            addToHead(node);
        } else {//假設目前是head <-> A <-> B <-> tail
            Node node = new Node(key, value);//建立新節點:Node(3, 300)
            cache.put(key, node);            //放進HashMap，只是建立key到node的對應，還沒處理順序
            addToHead(node);                 //linked list變成：head <-> 3 <-> A <-> B <-> tail
            size++;
            if (size > capacity) {           //3>2==>超過容量
                node = tail.prev;            //最久沒用的節點
                cache.remove(node.key);      //並非刪掉"整個"B節點，而是只有從HashMap刪掉
                removeNode(node);            //還要再從linked list刪掉B節點
                size--;                      //size回到2
            }
        }
    }

    //等於把這個節點從鏈結串列中拔掉。因為是雙項鍊表所以一定要處裡前後2項指標
    //假設head <-> A <-> B <-> C <-> tail
    private void removeNode(Node node) {//以移除B點為例 [一定要針對某個點去想必較容易懂]
        node.prev.next = node.next;     //A.next = C   下一個指向前一個
        node.next.prev = node.prev;     //C.prev = A   前一個指向下一個
    }
    //把node插到head後面。 [node 插到 head 和 原本第一個節點 中間]
    //以B點插到head後面為例，目標變成：head <-> B <-> A <-> C <-> tail
    //==>注意:不能直接當成把B改動位置而已==>關鍵:在雙向linked list裡：插入前一定要確保node已經從原位置拔掉!!!!
    //「移動節點」= remove + insert。
    // 所以標準 LRU 寫法永遠是：
    //                      removeNode(node);
    //                      addToHead(node);
    private void addToHead(Node node) {//想成是重建 (head跟B)&(B跟A) 之間的雙向連結
        node.next = head.next;         //B.next = A
        node.prev = head;              //B.prev = head
        head.next = node;              //head.next = B
        node.next.prev = node;         //A.prev = B
    }
}
/*
Q1:為什麼不能用"單向"LinkedList？
A1:刪除節點時需要：node.prev
   沒有prev就要遍歷 → O(n)
Q2:
cache.remove(node.key)不是已經刪掉整個node了嗎?
為何還需要removeNode(node)?
A2:
👉 HashMap 刪掉的是「索引」
👉 removeNode 刪掉的是「鏈結關係」

Q3:題目的說明裡提到lRUCache.put(3, 3); // 该操作会使得关键字 2 作废，缓存是 {1=1, 3=3}
為何使得关键字 2 作废?2不是比較慢進來的嗎?應該要刪除1吧
A3:它淘汰的不是“最早放进去的”，而是最近最久没有被访问的。关键点来了：访问也算“使用”！
使用顺序变为: [2, 1]
（1变成最近使用，2变成最久未使用==>所以會踢掉2）

*/

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
