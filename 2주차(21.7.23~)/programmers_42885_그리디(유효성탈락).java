/**
 * 문제 출처 : https://programmers.co.kr/learn/courses/30/lessons/42885
 */

// import for testing (junit 5.3.1)
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

//import for problem solving
import java.util.Arrays;

public class Solution {
    public int solution(int[] people, int limit) {
        Arrays.sort(people);
        int answer = 0;
        boolean[] selected = new boolean[people.length];

        for(int i=people.length-1; i>=0; i--){
            if(selected[i]) continue;

            int pivot = people[i];

            // 같이 배에 탈 짝 찾기
            for(int j=i-1; j>=0; j--){
                if(selected[j]) continue;
                int pair = people[j];

                if(pivot + pair > limit) continue;

                selected[i] = selected[j] = true;
                answer++;
                break;
            }

            // 짝을 찾지 못한 경우 1개만 보냄
            if(selected[i] == false){
                selected[i] = true;
                answer++;
            }
        }

        return answer;
    }

    @Test
    public void 정답확인() {
        assertEquals(1, solution(new int[]{50}, 50));
        assertEquals(2, solution(new int[]{20, 50, 50, 80}, 100));
        assertEquals(3, solution(new int[]{70, 50, 80, 50}, 100));
        assertEquals(3, solution(new int[]{50, 30, 20, 70, 10}, 100));
        assertEquals(3, solution(new int[]{70, 80, 50}, 100));
        assertEquals(5, solution(new int[]{10, 20, 30, 40, 50, 60, 70, 80, 90}, 100));
    }
}