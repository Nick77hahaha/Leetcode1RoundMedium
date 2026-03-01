import java.util.*;

public class Main {
    public static void main(String[] args) {
        /*
        1.合并所有重叠的区间，return恰好覆盖输入的所有区间[每個區間沒有順序]
        2.時間複雜度O( n×log n)，空間複雜度O(log n)，n为区间个数
        3.
        STEP1:将区间按照"左端点"升序排列 [讓起点越来越大]
        STEP2:当前区间的"左端点">答案数组中最后一个区间的"右端点"=>不会重合=>将当前区间加入答案数组末尾
                             <                           =>重合=>当前区间的右端点跟答案数组中最后一个区间的右端点取其MAX
                                                因為可能會是[[1,6],[4,5]]==>重合成[[1,MAX(6,5)]]
        4.intervals = [[1,3],
                       [2,6],
                       [8,10]]
        ==>代號順序是[[0][0],[0][1]]
                   [[1][0],[1][1]]
                   [[2][0],[2][1]]
        */
    }
}
class Solution {
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));//取每个数组的第0个值，用这个值比较
        //st跟ed都是子陣列內的數字，st是1，ed是3
        int st = intervals[0][0], ed = intervals[0][1];//把第一个区间当成“当前正在合并的区间” [初始化当前区间]
        List<int[]> ans = new ArrayList<>();
        for (int i = 1; i < intervals.length; ++i) {
            int s = intervals[i][0], e = intervals[i][1];
            if (ed < s) {//当前区间的结束 < 下一个区间的开始 (沒重叠到)
                ans.add(new int[] {st, ed});//把当前区间加入答案
                st = s;//开始新的区间[將此回合原本要run的數組更新到st跟ed]
                ed = e;
            } else {
                ed = Math.max(ed, e);
            }
        }
        //這裡一定要再加入1次==>防止像是全部子陣列都重疊=>永不進入else區塊=>ans會是空陣列 [舉例[[1,4],[2,5],[3,6]]]
        ans.add(new int[] {st, ed});//new int[]{st, ed}意思是：造一个区间盒子，里面放 st 和 ed
        return ans.toArray(new int[ans.size()][]);//因為題目要求回傳 int[][]，而不是 List
    }
}
/*
(1)intervals = [[1,3],[2,6],[8,10],[15,18]]
1------3
   2-------6
=>重叠
我们只需要：ed = max(3,6) = 6
(2)流程:
初始：
    st=1
    ed=3
看 [2,6]
    3 >= 2 → 重叠
更新：
    ed = 6

看 [8,10]
    6 < 8 → 不重叠
加入：
    [1,6]
开始新区间：
    st=8
    ed=10
(3)語法說明:
new int[] {st, ed}
等价于：
int[] arr = new int[2];
arr[0] = st;
arr[1] = ed;
--------------------------
a -> a[0] 是 lambda 表达式。
等价于：
new Comparator<int[]>() {
    public int compare(int[] a, int[] b) {
        return a[0] - b[0];
    }
}
Q:怎麼簡化的?連b[0]都不見了??
A:Java 提供了一个现成工具方法：Comparator.comparingInt(...)
它的意思是：帮你生成一个“比较 int 的比较器”

Comparator.comparingInt(a -> a[0])
等价于：(a, b) -> Integer.compare(a[0], b[0])
也就是说：comparingInt 自动帮你写了 b
你只需要告诉它：“每个元素用什么值来比较”
所以 b 没消失只是变成：隐藏在 comparingInt 里面


Q:為什麼要 new int[ans.size()][]？
A:因為Java 的 toArray() 需要你給一個「型別模板」。
  這行代表：
    我要一個：
    外層長度 = ans.size()
    內層是 int[]

Q:如果要按照「第 2 個元素」排序?
也就是按照：a[1]
那就改成：
Arrays.sort(nums, Comparator.comparingInt(a -> a[1]));
🔎 為什麼是 a[1]？
因為：
a -> a[0]   // 取每個子陣列的第 1 個數
a -> a[1]   // 取每個子陣列的第 2 個數
a 代表每一個 int[]
*/
