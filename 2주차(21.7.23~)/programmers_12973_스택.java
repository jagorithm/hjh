/**
 * 문제 링크 : https://programmers.co.kr/learn/courses/30/lessons/12973
 */

import java.util.Stack;

class Solution{
    public int solution(String s){
        Stack<Character> stack = new Stack<>();
        char[] inputs = s.toCharArray();

        for(char input  : inputs){
            if(!stack.empty() && stack.peek() == input){
                stack.pop();
                continue;
            }
            stack.push(input);
        }

        int answer = stack.empty() == true ? 1 : 0;
        return answer;
    }
}

public class Main {
    public static void main(String args[]){
        int ans = new Solution().solution("baabaaa");
        System.out.println(ans);
    }
}