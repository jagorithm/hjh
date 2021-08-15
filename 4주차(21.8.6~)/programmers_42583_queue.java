/**
 * 문제 출처 : https://programmers.co.kr/learn/courses/30/lessons/42583
 */

// import for testing (junit 5.3.1)

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//import for problem solving
import java.util.*;

public class Solution {
    private static Queue<int[]> bridgeQueue;
    private static int truckNum;

    public int solution(int bridge_length, int weight, int[] truck_weights) {
        truckNum = truck_weights.length;
        bridgeQueue = new LinkedList<>();

        int rangeWeightSum = 0;
        int time = 1;
        int truckIdx = 0;
        int completedNum = 0;
        while (completedNum < truckNum) {
            int[] headTruck = null;

            // 1. 다리에서 뺄 수 있으면 빼고
            if (((headTruck = bridgeQueue.peek()) != null) && (time - headTruck[1] >= bridge_length)) {
                rangeWeightSum -= truck_weights[headTruck[0]];
                bridgeQueue.poll();
                completedNum++;
            }
            // 2. 다리에 넣얼 수 있으면 넣는다
            if ((truckIdx < truckNum) && rangeWeightSum + truck_weights[truckIdx] <= weight) {
                rangeWeightSum += truck_weights[truckIdx];
                bridgeQueue.add(new int[]{truckIdx, time}); // 현재 트럭의 idx와 들어간 시간을 마킹
                truckIdx++;
            }
            time++;
        }

        return time - 1;
    }

    @Test
    public void 정답확인() {
        assertEquals(8, solution(2, 10, new int[]{7, 4, 5, 6}));
        assertEquals(101, solution(100, 100, new int[]{10}));
        assertEquals(110, solution(100, 100, new int[]{10, 10, 10, 10, 10, 10, 10, 10, 10, 10}));
    }
}