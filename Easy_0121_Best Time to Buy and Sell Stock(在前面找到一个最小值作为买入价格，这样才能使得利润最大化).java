import java.util.*;
public class Main {
    public static void main(String[] args) {

    }

}

//题目:
//数组的第 i 个元素 prices[i] 表示股票第 i 天的价格。
//你只能选择某一天买入这只股票，并选择在未来的某一个"不同的日子卖出"股票。设计一个算法来计算你所能获取的最大利润。
//返回最大利润。如果你不能获取任何利润，返回 0 。
//結論:從數組中挑出任2數的差值要最大，並返回該數值

//時間複雜度O(n)，n是数组nums的长度
//空間複雜度O(1)
class Easy_0121_BestTimeToBuyAndSellStock {
    public int maxProfit(int[] prices) {
        int ans = 0, mi = prices[0];//需要在前面找到一个最小值作为买入价格，这样才能使得利润最大化
        for (int v : prices) {
            ans = Math.max(ans, v - mi);//把今天的價格v當作"賣出價"-之前最便宜的那天買價
            mi = Math.min(mi, v);//如果今天價格比目前最低價還低，就更新 mi
        }
        return ans;
    }
}
/*
範例說明:假設 prices = [7, 1, 5, 3, 6, 4]
| 天數  | 價格  | mi (最低價)  | v - mi (潛在利潤)  | ans (最大利潤) |
|------|------|-------------|-------------------|----------------|
| 0    |  7   | 7           | 0                 | 0              |
| 1    |  1   | "1"已變化    | 0                 | 0              |
| 2    |  5   | 1           | 4                 | 4              |
| 3    |  3   | 1           | 2                 | 4              |
| 4    |  6   | 1           | 5                 | 5 ✅           |
| 5    |  4   | 1           | 3                 | 5              |
最後回傳 ans = 5

*/