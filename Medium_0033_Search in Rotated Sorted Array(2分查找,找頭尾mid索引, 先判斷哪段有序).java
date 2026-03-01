import java.util.*;

public class Main {
    public static void main(String[] args) {
        Solution sol = new Solution();
        int[] nums={4,5,6, 7, 0,1,2};
        //index     0     mid     6
        //          左半段  |    右半段
        int target=1;
        System.out.println(sol.rotate(nums, target));
    }
}
/*
给旋转后的数组 nums和一个整数target ，如果nums中存在目标值target ，则返回它的索引值，否则返回 -1 。
你必须设计一个时间复杂度为 O(log n) 的算法解决此问题。
 ==>快速找就是用2分法!!
//此时间复杂度为 O(log n)，空间复杂度O(1)
//1️⃣ 先判断哪边有序
//2️⃣ 再判断 target 是否在有序区间里
//3️⃣ 决定移动 left 还是 right
//为什么必须先判断哪边有序？==>因為普通二分查找的前提是：区间有序
int[] nums = {4,5,6,7,0,1,2};
int n = nums.length;
*/
class Solution{
    public int rotate(int[] nums,int target){
        int left=0;
        int right= nums.length-1;
        while (left<right){//比索引值
            int mid = left+right >>1;
            if(nums[mid]>=nums[0]){//左半部分有序
                if(target>=nums[0] && target<=nums[mid]){//target在左半段!
                    right=mid;
                }else{                                  //target在右半段!
                    left=mid+1;
                }
            }else{                //右半部分有序
                if(target>nums[mid] && target<=nums[right]){
                    left=mid+1;
                }else{
                    right=mid;
                }
            }
        }
        return nums[left]==target? left:-1;
    }
}
/*
(1)现在回答你真正困惑的点
你误以为：
进入“右半边有序”就应该一直在右边找==>錯了❌ ，不是这样
逻辑是：
判断哪半边有序
→ 判断 target 是否在那半边
→ 如果不在，就丢掉那半边
关键思想
这题不是在“找有序区间”
而是在：利用有序区间来排除另一半
(2)為何不是right = mid-1??
因為取決於while (left<right)還是while (left <= right)
while (left <= right)
这种写法里：
mid 已经检查过
可以安全排除
所以可以 right = mid - 1
这是另一套模板。
(3)
Q1:怎麼判斷right 跟left到底要不要mid+1還是mid
A1:用while (left < right)，所以 right 是可能答案的位置(不能隨便跳過它)

Q2:左半邊等同包含了 mid 嗎？
A2:對

Q3:為何第36列的target>nums[mid]不需要等號?
A3:等號只能屬於一邊。左半是：target <= nums[mid]
                 右半是：target > nums[mid]
                 剛好
Q4:為何return是用nums[left]==target
           而不用nums[right]==target?
A4:這裡用left還是right都一樣，因為迴圈條件是while (left < right)。所以會執行到return這行的狀況一定是在left=right。
*/
