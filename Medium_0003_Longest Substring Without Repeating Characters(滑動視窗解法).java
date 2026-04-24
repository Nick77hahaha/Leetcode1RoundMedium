import java.util.Arrays;
import java.util.Comparator;
public class Main {
    public static void main(String[] args) {
        /*
        1.找不含有重複字元的 最長子字串的長度
        "滑動視窗解法"
        時間複雜度O(n)，n為字串的長度
        空間複雜度O(|Σ|)，其中Σ表示字符集
        2.我们用两个指针：l = 左边界
                       r = 右边界
        形成一个窗口：[l .... r]
        窗口里永远保持：没有重复字符
        r 一直往右扩展。 l 负责在必要时收缩
        3.cnt[x] 表示的是：当前窗口内，字符 x 出现了几次 [ 注意：是“窗口内”，不是整个字符串]
        */
    }
}
class Solution {
    public int lengthOfLongestSubstring(String s) {
        //ASC，'h' 的 ASCII 是 104。字元英文:character
        //ASC包含符號+數字+大小寫英文
        int[] cnt = new int[128];//ASC
        int ans = 0;
        for (int l = 0, r = 0; r < s.length(); ++r) {
            char c = s.charAt(r);
            cnt[c]++;
            while (cnt[c] > 1) {
                //--cnt[s.charAt(l++)];
                //上列是簡寫，可以改成下方:
                char leftChar = s.charAt(l);
                cnt[leftChar]--;//把左边字符的计数减 1
                l++;//左指针往右移动
            }
            ans = Math.max(ans, r-l+1);//+1是因為多扣1個 (r-l+1是指窗口長度)
        }
        return ans;
    }
}
//Q1:為何要用While而不是if，不識一但有重複地就會馬上處理掉，那何須用迴圈? 為何max(ans, r - l + 1)
//A:舉例：s = "abba"
//      當 r=3，c='a'：
//          cnt['a'] = 2
//          左指針 l=0，窗口 = "abb"
//          用 if：只減掉一次 cnt['a']，l 移到 1，窗口是"bb"，cnt['b'] = 2 → 仍不合法！
//          用 while：會一直移動 l，直到窗口變成 "ba"，合法。
//Q2:為什麼用 ans = Math.max(ans, r - l + 1)
//你每次 r 移動時得到的 當前窗口長度 = r - l + 1
//但是這個窗口 是動態變化的，可能比之前的窗口短。
//像是s = "abcba"==>最長是3，
//但是當窗口滑動到abcb=>l移動2次之後，窗口變成cb==>長度變成2(變短==>前面的3還比較大，所以要用之前的ans才是正解)
//
//Q3:為何窗口有重複就要從l指標開始往右增?為什麼不能動 r？
//用例子秒懂:s = "abcba"
//當r = 3 → 'b' ❗重複了
//        [a b c b]
//           ↑ 重複，已經不合法
//一步一步縮：
//l=0 → 移掉 'a'
//[b c b]   ❌ 還重複
//
//l=1 → 移掉 'b'
//[c b]     ✔ 不重複了！
//👉 r 是「往前探索」
//👉 如果你往回退：就會錯過可能的更長答案 






