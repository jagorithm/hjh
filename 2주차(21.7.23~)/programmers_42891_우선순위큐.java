/**
 * 문제 출처 : https://programmers.co.kr/learn/courses/30/lessons/42891
 */

// import for testing (junit 5.3.1)
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

//import for problem solving
import java.util.*;

class Element implements Comparable<Element>{
    int food_time;
    int num;

    Element(int food_time, int num){
        this.food_time = food_time;
        this.num = num;
    }

    @Override
    public int compareTo(Element o){
        return this.food_time - o.food_time; // 최소 힙 기반
    }
}

public class Solution {
    public int solution(int[] food_times, long k) {
        PriorityQueue<Element> pq = new PriorityQueue<>();

        long sum = 0;
        for(int i=0; i<food_times.length; i++){
            pq.add(new Element(food_times[i], i + 1));
            sum += food_times[i];
        }

        // k 만큼 돌릴 수 없는 경우
        if(sum <= k)
            return -1;

        // 한 바퀴 단위로 회전 가능한 만큼 최대한 돌리기
        long totalTime = 0;
        long prevFoodTime = 0;
        long restFoodCnt = food_times.length;

        while(totalTime + (pq.peek().food_time - prevFoodTime) * restFoodCnt <= k){
            long curFoodTime = pq.poll().food_time;
            totalTime += (curFoodTime - prevFoodTime) * restFoodCnt;
            restFoodCnt--;
            prevFoodTime = curFoodTime;
        }

        List<Element> restResult = new ArrayList<>();
        while(!pq.isEmpty()){
            restResult.add(pq.poll());
        }

        Collections.sort(restResult,
                new Comparator<Element>() {
                    @Override
                    public int compare(Element o1, Element o2) {
                        return o1.num - o2.num;
                    }
                });

        int restIdx = (int)((k-totalTime) % restFoodCnt);

        return restResult.get(restIdx).num;
    }

    @Test
    public void 정답확인() {
        assertEquals(1, solution(new int[] {3, 1, 2}, 5));
        assertEquals(2, solution(new int[] {8, 6, 4}, 15));
        assertEquals(1, solution(new int[] {946, 314, 757, 322, 559, 647, 983, 482, 145}, 1833));
    }
}