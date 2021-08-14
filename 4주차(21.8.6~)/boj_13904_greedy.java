/**
 * 문제 출처 : https://www.acmicpc.net/problem/13904
 */

import java.io.*;
import java.util.*;

class Assignment implements Comparable<Assignment> {
    int deadLine;
    int score;

    Assignment(int deadLine, int score) {
        this.deadLine = deadLine;
        this.score = score;
    }

    @Override
    public int compareTo(Assignment o) {
        // score기준 내림 차순
        return o.score - this.score;
//        if(o.score > this.score) return 1;
//        else if(this.score > o.score) return -1;
//        else return 0;
    }
}

public class Main {
    private static int inputSize;
    private static PriorityQueue<Assignment> pq;
    private static Map<Integer, Optional<Assignment>> planMap;
    private static int sum = 0;

    public static void main(String[] args) throws IOException {
        getInputAndInitialize();
        solve();

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        bw.write(sum + "");
        bw.flush();
        bw.close();
    }

    private static void solve() {
        int today = 1;

        while (!pq.isEmpty()) {
            var curAssign = pq.poll();

            if (!planMap.containsKey(curAssign.deadLine)) { // 해당 day에 할 숙제가 없을 경우
                planMap.put(curAssign.deadLine, Optional.of(curAssign));
                sum += curAssign.score;
                continue;
            }

            // 아래는 해당 날에 할 일이 있는 경우
            checkPrevAvailDay(curAssign);
        }
    }

    private static void checkPrevAvailDay(Assignment assig) {
        for (int prevDay = assig.deadLine - 1; prevDay >= 1; prevDay--) {
            if (!planMap.containsKey(prevDay)) { // 들어갈 자리가 비어있으면 넣고 끝
                planMap.put(prevDay, Optional.of(assig));
                sum += assig.score;
                return;
            }
        }
    }

    private static void getInputAndInitialize() throws IOException {
        pq = new PriorityQueue<>();
        planMap = new HashMap<>();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tokenizer;
        inputSize = Integer.parseInt(br.readLine());

        for (int i = 1; i <= inputSize; i++) {
            tokenizer = new StringTokenizer(br.readLine(), " ");

            var tmpAssign = new Assignment(
                    Integer.parseInt(tokenizer.nextToken()),
                    Integer.parseInt(tokenizer.nextToken())
            );

            pq.add(tmpAssign);
        }

        br.close();
    }
}