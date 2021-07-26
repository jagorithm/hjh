/**
 * 문제 링크 : https://programmers.co.kr/learn/courses/30/lessons/60057
 */

class Solution {
    public int solution(String s) {
        if(s.length() == 1) return 1;

        int answer = 1001;

        for(int unitLen=1; unitLen<=s.length()/2; unitLen++){
            String curStr, nextStr = "";
            String resultStr = "";

            int hit = 1;
            for(int offset=0; offset<=s.length()/unitLen; offset++){
                int startIdx = unitLen*offset;
                int endIdx = unitLen*(offset+1) > s.length() ? s.length() : unitLen*(offset+1);

                curStr = nextStr;
                nextStr = s.substring(startIdx, endIdx);

                if(curStr.equals(nextStr)){
                    hit++;
                }
                else{
                    if(hit>1) resultStr += String.valueOf(hit);
                    resultStr += curStr;
                    hit = 1;
                }
            }
            if(hit>1) resultStr += String.valueOf(hit);
            resultStr += nextStr;

            answer = Math.min(answer, resultStr.length());
        }
        return answer;
    }
}

public class Main {
    public static void main(String args[]){
        int ans = new Solution().solution("aabbaccc");
        System.out.println(ans);
    }
}