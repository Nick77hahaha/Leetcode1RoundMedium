import java.util.*;

public class Main {
    public static void main(String[] args) {
        /*
        1.[1,8,6,2,5,4,8,3,7]數字就是代表高度，題目講得太雜甚麼两个端点是 (i, 0) 和 (i, height[i])...
        2.
        3.時間複雜度O(n)，n為数组height的长度
          空間複雜度O(1)
        */
    }
}
class Solution {//雙指針法:
    public int maxArea(int[] height) {
        int l = 0, r = height.length - 1;//索引值
        int ans = 0;
        while (l < r) {
            int t = Math.min(height[l], height[r]) * (r - l);//容量=高*底，容器的高度由较短的那根线决定故用min。
            ans = Math.max(ans, t);//存最大值到ans內
            if (height[l] < height[r]) {//哪條高度較高就保留此高度線才能有最大容量==>保留此線的指針(索引值)==>移動另外一個指針
                l++;
            } else {
                r--;
            }
        }
        return ans;
    }
}
