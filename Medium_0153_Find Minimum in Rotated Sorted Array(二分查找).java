import java.util.*;

public class Main {
    public static void main(String[] args) {
        /*
        1.
        2.旋转 = 把数组前面一段 剪下来接到数组后面==>数组變成局部有序 + 局部有序==>可用二分查找法
        3.不是普通二分查找。它是在找：第一个 < nums[0] 的位置，也就是：找断点
        4.時間複雜度O(log n)，
          空間複雜度O()
        */
    }
}
class Solution {//二分查找法:
    public int findMin(int[] nums) {
        int n = nums.length;
        if (nums[0] <= nums[n - 1]) {//首 ≤ 尾元素，代表数组没有旋转
            return nums[0];
        }
        int left = 0, right = n - 1;
        while (left < right) {//超白话总结:找：第一个 < nums[0] 的位置
            int mid = (left + right) >> 1;//索引值除以 2
            if (nums[0] <= nums[mid]) {//最小值一定在右边這段
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return nums[left];
    }
}
/*
假设数组：[4,5,6,7,1,2,3]
1 round: left = 0
         right = 6
         nums[0] = 4
         mid = (0+6)/2=3
         nums[3]=7 ==>要看成陣列被分段:[4 5 6 7 | 1 2 3] (是數值而非索引值，不要想成3是在最後1個位置)
         因為nums[0] <= nums[mid]
               4    <     7     ==>最小值一定在右边這段=>故left = mid + 1
2 round: left = 4  這個變了!!!
         right = 6
         nums[0] = 4  固定
         mid = (4+6)/2=5
         nums[5]=2
         nums[0] <= nums[mid]有嗎?
            4    >     2         ==>false ==>故 right = mid = 5
3 round: left = 4
         right = 5
         mid =4
         nums[4]=1
         nums[0] <= nums[mid]有嗎?
            4    >     1         ==> right = mid = 4
4 round: left = 4
         right = 4
         循环条件：left < right不成立了。循环结束。
*/