import java.util.*;

public class Main {
    public static void main(String[] args) {

    }
}
//题目:
//给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
//有效字符串需满足：
//左括号必须用相同类型的右括号闭合。
//左括号必须以正确的顺序闭合。
//每个右括号都有一个对应的相同类型的左括号。

//時間複雜度O(n)，空間複雜度O(n)。n为括号字符串s的长度
class Easy_0020_ValidParentheses {
    public boolean isValid(String s) {
        Deque<Character> stk = new ArrayDeque<>();//用來暫存左括號
        for (char c : s.toCharArray()) {
            if (c == '(' || c == '{' || c == '[') {//char要用單引號!!!
                stk.push(c);//stk.add(c)也可將元素加入stk，但是意義不同。
            } else if (stk.isEmpty() || !match(stk.pop(), c)) {
                //stk.isEmpty() → 目前只找到右括號，代表stack內沒有對應的左括號。像是s=")))"
                //!match(stk.pop(), c) → 從堆疊拿出左括號後，跟目前這個右括號不匹配
                return false;
            }
        }
        //整個字串處理完後：
        //如果堆疊是空的 → 表示所有左括號都被正確配對過了 → 回傳 true
        //如果堆疊還有剩 → 有沒配對到的左括號 → 回傳 false
        //不可以用return true;舉例:s="((("，整個 for 迴圈跑完了，但stack裡還有3個 '('==>應是沒配對成功
        return stk.isEmpty();
    }

    private boolean match(char l, char r) {
        return (l == '(' && r == ')') || (l == '{' && r == '}') || (l == '[' && r == ']');
        //上列等同下方code
        //if(l=='('&&r==')' || l=='['&&r==']' || l=='{' && r=='}'){
        //    return true;
        //}else{
        //    return false;
        //}
    }
}

/*
Q1:stk.push()堆疊是向stack一樣first in last out嗎?
A1:是標準堆疊的行為:把元素推進去是「壓入 stack 的"最上方"」
Q2:stk.pop()是將元素取出然後會回傳true跟false嗎?
A2:不會回傳true/false，它會：(1)移除堆疊頂端的元素
                          (2)回傳該元素的值
Q3:match(stk.pop(), c))比較的2者都要放甚麼型別的?
A3:去看match的自定義方法

Q4:
| 方法            | 意圖                          | 加在何處         | 常用場景               |
| ------------- | -------------------------     | ------------    | ------------          |
| `stk.add(c)`  | 將 `Deque` 當作**Queue（佇列）** | 尾端（addLast）   | 廣度優先搜尋、排隊處理   |
| `stk.push(c)` | 將 `Deque` 當作**Stack（堆疊）** | 頭端（addFirst）  | 括號配對、回溯、DFS等   |

以s = "([{}])"為例
STACK:
stack開口:  [    =>當進行到要處理}符號時，pop()出來的順序就是上一個的符號{
           {
stack底部:  (

*/

