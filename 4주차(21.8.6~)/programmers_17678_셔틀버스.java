/**
 * 문제 출처 : https://programmers.co.kr/learn/courses/30/lessons/17678
 */

// import for testing (junit 5.3.1)
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

//import for problem solving
import java.util.*;

public class Solution {
    static Map<String, Integer> crewArriveCountMap; // <도착시간, 개수>
    static List<String> crewArriveOrders;     // 도착 시간 순서를 오름차순으로 정렬, crewArriveCountMap에서 순차적으로 접근하기 위함
    static Map<String, Integer> departCountMap;   // <출발 시간, 태운 개수>
    static List<String> departOrders; // departCountMap에서 순차적 접근을 위함

    static int crewNum;

    public String solution(int n, int t, int m, String[] timetable) {
        createCountMap(timetable);
        createdepartOrders(timetable);
        createDepartTerms(n, t);
        crewNum = crewArriveOrders.size();

        int lastCrewArriveIdx = 0;
        int departedNum = 0;
        int takenCrewNum = 0;
        String lastDepartTime = "";
        for (String departTime : departOrders) {
            for (int crewArriveIdx = lastCrewArriveIdx; crewArriveIdx < crewNum; crewArriveIdx++) {
                lastDepartTime = departTime;
                lastCrewArriveIdx = crewArriveIdx;

                String crewArriveTime = crewArriveOrders.get(crewArriveIdx);
                if (departTime.compareTo(crewArriveTime) < 0) { // 태우고 갈 수 없는 경우 -> 다음 타임으로
                    break;
                }

                // 아래는 태울 수 있는 경우임
                departCountMap.put(departTime, departCountMap.get(departTime) + 1);
                takenCrewNum++;
                lastCrewArriveIdx = crewArriveIdx + 1;
                if (departCountMap.get(departTime) == m) break;
            }

            departedNum++;

            if (crewArriveOrders.size() == takenCrewNum) break;
        }

        String answer = null;
        if (departCountMap.get(lastDepartTime) == m) {
            if (departedNum == n)
                answer = minusOneMinus(crewArriveOrders.get(lastCrewArriveIdx - 1)); // 여기서 1분 뺀 값
            else
                answer = departOrders.get(n - 1);
        } else {
            answer = departOrders.get(n - 1);
        }

        return answer;
    }

    private String minusOneMinus(String timeStr) {
        String[] tokens = timeStr.split(":");

        String result = "";
        int hour = Integer.parseInt(tokens[0]);
        int min = Integer.parseInt(tokens[1]);

        if (min == 0) {
            min = 59;
            hour -= 1;
        } else {
            min -= 1;
        }
        String hourStr = hour <= 9 ? "0" + Integer.toString(hour) : Integer.toString(hour);
        String minStr = min <= 9 ? "0" + Integer.toString(min) : Integer.toString(min);

        return hourStr + ":" + minStr;
    }

    private void createdepartOrders(String[] timetable) {
        crewArriveOrders = new LinkedList<>();
        for (String a : timetable) crewArriveOrders.add(a);
        Collections.sort(crewArriveOrders);
    }

    private void createCountMap(String[] timetable) {
        crewArriveCountMap = new HashMap<>();
        for (String time : timetable) { // map : <도착시간, 개수> 저장
            crewArriveCountMap.put(time, crewArriveCountMap.getOrDefault(time, 0) + 1);
        }
    }

    private void createDepartTerms(int n, int t) {
        departCountMap = new HashMap<>();
        departCountMap.put("09:00", 0);
        int hour = 9;
        int min = 0;
        n = n - 1;
        while (n-- > 0) {
            min += t;
            if (min >= 60) {
                min -= 60;
                hour += 1;
            }
            String hourStr = hour <= 9 ? "0" + Integer.toString(hour) : Integer.toString(hour);
            String minStr = min <= 9 ? "0" + Integer.toString(min) : Integer.toString(min);

            departCountMap.put(hourStr + ":" + minStr, 0);
        }
        departOrders = new LinkedList<>(departCountMap.keySet());
        Collections.sort(departOrders);
    }

    @Test
    public void 정답확인() {
        assertEquals("09:00", solution(1, 1, 5, new String[]{"08:00", "08:01", "08:02", "08:03"}));
        assertEquals("09:09", solution(2, 10, 2, new String[]{"09:10", "09:09", "08:00"}));
        assertEquals("08:59", solution(2, 1, 2, new String[]{"09:00", "09:00", "09:00", "09:00"}));
        assertEquals("00:00", solution(1, 1, 5, new String[]{"00:01", "00:01", "00:01", "00:01", "00:01"}));
        assertEquals("09:00", solution(1, 1, 1, new String[]{"23:59"}));
        assertEquals("18:00", solution(10, 60, 45, new String[]{"23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59", "23:59"}));

        assertEquals("09:01", solution(2, 1, 2, new String[]{"00:01", "00:01", "00:01"}));
        assertEquals("00:58", solution(1, 1, 1, new String[]{"00:59"}));
        assertEquals("09:01", solution(2, 1, 1, new String[]{"00:59"}));
        assertEquals("08:59", solution(2, 1, 1, new String[]{"00:59", "09:00", "09:01"}));
    }
}