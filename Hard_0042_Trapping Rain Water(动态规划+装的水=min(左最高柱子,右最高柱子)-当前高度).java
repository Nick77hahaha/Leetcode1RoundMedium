import java.util.Arrays;
import java.util.Comparator;
public class Main {
    public static void main(String[] args) {
        /*Q:給定 n個非負整數表示每個寬度為1的柱子的高度圖，計算以此排列的柱子，下雨後能接多少雨水
            0是沒有柱子，黑色是柱子，藍色是雨水*/
        //時間複雜度O(n)，空間複雜度O(n)
        //動態規劃解法
        /*核心公式：当前位置能装的水 = min(左边最高柱子, 右边最高柱子) - 当前高度
           因为水会被“左右两边较"低"的那一边”限制住。
        */
        int[] height = {4,2,0,3,2,5};
        Solution sol = new Solution();
        System.out.println(sol.trap(height));
    }
}
class Solution {
    public int trap(int[] height) {
        int n = height.length;
        int[] left = new int[n];//  left[i]：从 0 到 i 的最大高度   [从左往右]
        int[] right = new int[n];//right[i]：从 i 到 n-1 的最大高度 [从右往左]
        //边界初始化:
        left[0] = height[0];//第一个位置的左最大值就是自己
        right[n - 1] = height[n - 1];//最后一个位置的右最大值也是自己
        //提前把“左边最高”和“右边最高”算好
        for (int i = 1; i < n; i++) {
            //当前位置左边的最高柱子 = (前一个位置为止，左边最高的柱子)和(当前柱子的高度)(說明1)
            left[i] = Math.max(left[i - 1], height[i]);
            //right[n - i - 1] = Math.max(right[n - i], height[n - i - 1]);//（倒着算）
            //上列等价于：
            for (int j = n-2; j >= 0; j--) {//從最右邊開始倒着算
                //当前往右的最高柱子 = (右边已经算好的“最高柱子)和(当前柱子的高度)
                right[j] = Math.max(right[j+1], height[j]);
            }
        }
        int ans = 0;
        for (int i = 0; i < n; i++) {//公式：水量 = min(左最高, 右最高) - 当前高度
            ans += Math.min(left[i], right[i]) - height[i];
        }
        return ans;
    }
}
/*
說明1:
height = [4,2,0,3,2,5]
i = 0 ==>left[0] = 4
i = 1 ==>left[1] = max(left[0], height[1])
                 = max(4,2)
                 = 4
i = 2 ==>left[2] = max(left[1], height[2])
                 = max(4,0)
                 = 4
說明2:
初始化right[5] = 5
j = 4 ==>right[4] = max(right[5], height[4])
                  = max(5, 2)
                  = 5
j = 3 ==>right[3] = max(right[4], height[3])
                  = max(5, 3)
                  = 5
j = 0 ==>right[0] = max(5, 4)
                  = 5
說明3:为什么要倒着算？
因为：right[j] 依赖 right[j+1]
也就是说：当前位置的“右边最大值”，必须先知道右边的结果
right[4] = max(right[5], height[4])
right[3] = max(right[4], height[3])==>這裡的right[4]需要前一步先算出來，故倒著算
假设你从 0 开始：那right[0] = ?==>你怎么知道 0 右边最高是多少？
*/