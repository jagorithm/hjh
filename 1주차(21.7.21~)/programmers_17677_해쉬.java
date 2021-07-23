/**
 * 문제 출처 : https://programmers.co.kr/learn/courses/30/lessons/17677
 */

import java.util.HashMap;

class Solution {
    public int solution(String str1, String str2) {
        HashMap<String, Integer> strMap1 = new HashMap<>(); // <집합원소, 개수>
        HashMap<String, Integer> strMap2 = new HashMap<>();

        str1 = str1.toUpperCase();
        str2 = str2.toUpperCase();

        // str1에 대해 strMap1에 넣기 <집합 원소, 개수>
        for(int i=0; i<str1.length()-1; i++){
            if(!Character.isAlphabetic(str1.charAt(i))) continue;
            if(!Character.isAlphabetic(str1.charAt(i+1))) continue;

            String tmpKey = str1.substring(i, i+2);
            strMap1.put(tmpKey, strMap1.getOrDefault(tmpKey,0) + 1);
        }

        // str2에 대해 strMap2에 넣기
        for(int i=0; i<str2.length()-1; i++){
            if(!Character.isAlphabetic(str2.charAt(i))) continue;
            if(!Character.isAlphabetic(str2.charAt(i+1))) continue;

            String tmpKey = str2.substring(i, i+2);
            strMap2.put(tmpKey, strMap2.getOrDefault(tmpKey,0) + 1);
        }

        // min을 이용해 교집합 개수 구하기
        int numIntersection = 0;
        int numUnion = 0;
        for(String key : strMap1.keySet()){
            if(strMap2.containsKey(key) == false) continue;

            int tmpMin = Math.min(strMap1.get(key), strMap2.get(key));
            numIntersection += tmpMin;
        }

        // 교집합 개수를 이용해 합집합 개수 구하기
        for(int val : strMap1.values()) numUnion += val;
        for(int val : strMap2.values()) numUnion += val;
        numUnion = numUnion - numIntersection;

        // 정답 계산하기
        int ans = 0;
        if(numUnion != 0)
            ans = (int)((double)numIntersection / numUnion * 65536.0); // 소수점 아래 버림

        if(strMap1.size() == 0 && strMap2.size() == 0) // 공집합
            ans = 65536;  // = 1 * 65526

        return ans;
    }
}

public class Main {
    public static void main(String[] args){
        int ans = new Solution().solution("FRANCE", "french");
        System.out.println(ans);
    }
}