/**
 * 문제 출처 : https://programmers.co.kr/learn/courses/30/lessons/67258
 */

// import for testing (junit 5.3.1)
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

// import for problem solving
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Solution {
    static String[] inputs;
    static int uniqueElementNum;
    static int maxLen;
    static HashMap<String, Integer> lenMap;

    public int[] solution(String[] gems) {
        if(gems.length == 1){
            return new int[] {1, 1};
        }
        inputs = gems;
        Set<String> uniqueSet = new HashSet<>();
        for(String val : gems) uniqueSet.add(val);

        uniqueElementNum = uniqueSet.size();
        maxLen = gems.length;

        // 구간 길이에 대해 이진탐색
        int firstLen = uniqueElementNum;
        int lastLen = maxLen;
        int midLen = firstLen, availStartIdx = 0;

        while(firstLen <= lastLen){
            midLen = (firstLen + lastLen) / 2;

            initializeMapOfLen(midLen); // 현재 계산된 길이에 대해 Map을 생성
            availStartIdx = getValidStartIndex(midLen);

            if(availStartIdx == -1){ // 1. 범위를 늘려야 하는 상황
                firstLen = midLen + 1;
            }
            else{  // 2. 해당 길이에 모든 키들이 하나씩 들어가는 상황(길이를 줄여볼 수 있는 상황)
                lastLen = midLen;
            }

            // 정답이 될 수 있는 상황인지 확인
            if((availStartIdx > -1) && (lastLen == firstLen))
                break;
        }

        int[] answer = new int[2];
        answer[0] = availStartIdx+1; // 진열대 번호는 1부터 시작
        answer[1] = availStartIdx+midLen;

        return answer;
    }

    private int getValidStartIndex(int len) {
        for(int startIdx=0; startIdx<=maxLen-len; startIdx++){
            if(startIdx != 0){
                // 1. 이전 범위의 첫번째 요소 제외
                String prevKey = inputs[startIdx-1];
                lenMap.put(prevKey, lenMap.get(prevKey)-1);
                if(lenMap.get(prevKey) <= 0) lenMap.remove(prevKey);

                // 2. 현재 범위가 만들어짐에 따라 새로 포함된 원소(마지막 원소) 포함
                String newKey = inputs[startIdx+len-1];
                lenMap.put(newKey, lenMap.getOrDefault(newKey, 0) + 1);
            }

            if(lenMap.size() == uniqueElementNum)
                return startIdx;
        }

        return -1; // 해당하는 길이로는 모든 키들을 포함시킬 수 없음.(해당 범위가 부족한 범위임)
    }

    private void initializeMapOfLen(int midLen) {
        lenMap = new HashMap<>(); // 길이가 업데이트 될 때마다 map을 새로 생성

        for(int i=0; i<midLen; i++){
            lenMap.put(inputs[i], lenMap.getOrDefault(inputs[i], 0) + 1);
        }
    }

    @Test
    public void 정답확인() {
        int[] result;

        result = solution(new String[] {"DIA", "RUBY", "RUBY", "DIA", "DIA", "EMERALD", "SAPPHIRE", "DIA"});
        assertArrayEquals(new int[] {3, 7}, result);

        result = solution(new String[] {"AA", "AB", "AC", "AA", "AC"});
        assertArrayEquals(new int[] {1, 3}, result);

        result = solution(new String[] {"XYZ", "XYZ", "XYZ"});
        assertArrayEquals(new int[] {1, 1}, result);

        result = solution(new String[] {"ZZZ", "YYY", "NNNN", "YYY", "BBB"});
        assertArrayEquals(new int[] {1, 5}, result);

    }
}