import java.util.*;

public class Main {
    public static void main(String[] args) {
        int[] nums = {2,7,11,15};
        int target = 9;
        //System.out.println(sol.targetSol(nums, target));會印出地址
        //System.out.println(Arrays.toString(sol.targetSol(nums, target)));
        //第2種解法(暴力解):
        int i=0;
        int j=0;
        boolean flag = false;
        for (i = 0; i < nums.length; i++) {
            for (j = i+1; j < nums.length; j++) {
                if((nums[i]+nums[j]) == target){
                    flag = true;
                    break;
                }
            }
            if(flag){
                break;
            }
        }
        int[] ans = {i,j};
        System.out.println(Arrays.toString(ans));
    }
}
//题目:
//给定一个整数数组 nums 和一个整数目标值 target，
// 请你在该数组中找出和为目标值target的那两个整数，并返回它们陣列的"索引值"
// [return indices of the two numbers such that they add up to target]
//你可以假设每种输入只会对应一个答案，并且你不能使用两次相同的元素。你可以按任意顺序返回答案
//提示：只会存在一个有效答案

//进阶：你可以想出一个时间复杂度小于 O(n平方) 的算法吗？

class Easy_0001_TwoSum {
    //法1:Hashmap
    //时间复杂度O(n)，n是nums的长度
    //空间复杂度O(n)
    public int[] twoSum(int[] nums, int target) {//回傳一個整數陣列
        Map<Integer, Integer> d = new HashMap<>();//Map<數值,該數值在nums陣列中的索引>
        for(int i = 0;; ++i) {//無條件的for迴圈（沒有設定終止條件，因為題目保證會有解）
            int x = nums[i];
            int y = target - x;//還剩下多少才會湊出target
            //如果y出現過，表示我們找到兩個數字了:[说明target值已经找到]
            if (d.containsKey(y)) {
                return new int[] {d.get(y), i};//把這兩個索引作為陣列回傳
                //上列是建立陣列的標準語法，拆開寫其實就是下面這樣
                //int[] result = new int[2];
                //result[0] = d.get(y);
                //result[1] = i;
                //return result;
            }
            //如果還沒找到，就把現在的數值x和它的索引i加到HashMap裡，让后面的数字可以用它来配对
            d.put(x, i);//這列可不是在if-else裡面
        }
    }
}

/*
1.
Q:return new int[] {d.get(y), i} 。 為何可用new int[] {}來傳值?
A:
因為：
    方法的回傳型別是 int[]（整數陣列）
    你需要回傳一個陣列，但你當下沒有事先宣告一個變數儲存它
    所以你直接使用 Java 提供的「陣列初始化語法」來建構這個陣列並直接回傳它
簡單例子來說明：
public static void main(String[] args) {
    int[] result = new int[] {3, 5};
    System.out.println(result[0]);  // 輸出3
    System.out.println(result[1]);  // 輸出5
}
如果不這樣寫會怎樣？
以下這種寫法是錯的（因為你不能用 {} 來初始化陣列，除非你是在宣告時）：
    // ❌ 錯誤寫法：
    return {d.get(y), i};  // 編譯錯誤
    正確的寫法只有這樣：
    return new int[] {d.get(y), i}; // 正確

額外補充：語法限制:
你只能在宣告變數時使用簡化語法（如下）：
    int[] a = {1, 2}; // OK
但如果是在方法中要「臨時建立陣列」，就必須明確用 new 關鍵字：
    return new int[] {1, 2}; // ✅ 這是唯一可行方式

2.
將
if (d.containsKey(y)) {
    return new int[] {d.get(y), i};
}
d.put(x, i);
調換順序寫成:
d.put(x, i);
if (d.containsKey(y)) {
    return new int[] {d.get(y), i};
}
==>會出錯
在這種情況下，當你掃描到第一個 x，你就先把它放進 map 中，然後馬上檢查 map 裡有沒有 target - x。
問題來了：
如果 x == y（例如:target 是 6，nums[i] 是 3），那你剛剛自己把3放進 map，接著馬上又問map裡有沒有 3，當然會有，但這是你剛剛放進去的自己。

*/


