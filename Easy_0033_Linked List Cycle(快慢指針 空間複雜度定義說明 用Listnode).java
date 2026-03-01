import java.util.*;
public class Main {
    public static void main(String[] args) {


    }

    //雙指針解法，当快慢指针相遇时，说明链表存在环
    //時間複雜度O(n)，n是链表中的节点数 [因為最多走完整個 linked list 一次。大約 n 步]
    //空間複雜度O(1)                 [因為只用了固定 2 個指標]
    public static boolean hasCycle(ListNode head) {//雙指針解法，当快慢指针相遇时，说明链表存在环
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {//確保(fast) & (fast.next)也不是null。否則會發生NullPointerException
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {//注意:這邊的節點判斷不是比較節點裡的"值"，是否指向同一個ListNode物件。比較的是值就會寫成slow.val
                return true;
            }
        }
        return false;
    }


}

class ListNode {
    int val;
    ListNode next;
    ListNode(int x) {
        val = x;
        next = null;
    }
}
/*
1.
Q:slow == fast，slow跟fast不是都是結點物件嗎，怎麼又可比較數值?
A:不是比較節點裡的值（val），而是比較記憶體位置（reference）!!
這裡的意思是：這兩個變數是否指向同一個 ListNode 物件（在記憶體中是同一個）？
PS:
如果你比較的是值那就會寫成這樣：if (slow.val == fast.val)
這是比較 ListNode 裡面的整數值，只是比內容，不是比物件本身是否相同。
2.
Q:為何要fast != null && fast.next != null
A:
來看錯誤例子，假設 linked list 是：1 → 2 → 3 → null  (沒cycle)
    第一次迴圈
    slow = 2
    fast = 3

    第二次迴圈
    此時：fast = 3
    但：3.next = null
    如果你只檢查：while (fast != null)
    就會進入迴圈。
    然後：
    fast = fast.next.next;
    變成：null.next
    💥 直接爆 NullPointerException
3.
🔵 什麼是「空間複雜度」？
空間複雜度看的是：額外使用的記憶體，會不會隨著 n 變大而變大？
重點是：
⚠️ 不是看總記憶體
⚠️ 是看「額外」記憶體
🔵 看你的程式
ListNode slow = head;
ListNode fast = head;
你只宣告了：
1 個 slow
1 個 fast
不管 linked list 有：
10 個節點
100 個節點
你還是只有：2 個變數
這叫：固定數量的空間
所以是：O(1)
🔵 什麼情況才會變 O(n)？
如果你這樣寫：
Set<ListNode> seen = new HashSet<>();
然後每個節點都存進去：seen.add(node);
如果有 n 個節點：就會存：n 個節點
這樣空間會隨著 n 成長，所以是：O(n)

*/