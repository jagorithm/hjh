/**
 * 문제 출처 : https://www.acmicpc.net/problem/13418
 */

import java.io.*;
import java.util.*;

abstract class Edge {
    int from, to, weight;

    Edge(int from, int to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }
}

class minEdge extends Edge implements Comparable<minEdge> {
    minEdge(int from, int to, int weight) {
        super(from, to, weight);
    }

    @Override
    public int compareTo(minEdge o) {
        return this.weight - o.weight; // 오름차순
    }
}

class maxEdge extends Edge implements Comparable<maxEdge> {
    maxEdge(int from, int to, int weight) {
        super(from, to, weight);
    }

    @Override
    public int compareTo(maxEdge o) {
        return o.weight - this.weight; // 내림차순
    }
}

public class Main {
    static int buildingNum, roadNum;
    static int[] cycleTable; // root
    static PriorityQueue<Edge> minHeapEdgePQ = new PriorityQueue<>();
    static PriorityQueue<Edge> maxHeapEdgePQ = new PriorityQueue<>();

    public static void main(String[] args) throws IOException {
        getInputAndInitalize();

        int bestCost = solve(true);
        int worstCost = solve(false);

        System.out.println((worstCost * worstCost) - (bestCost * bestCost));
    }

    private static int solve(boolean isMaxCost) {
        var pq = isMaxCost ? maxHeapEdgePQ : minHeapEdgePQ;
        int selectedEdgeNum = 0;
        int cnt = 0;

        cycleTable = new int[buildingNum];
        for (int node = 0; node < buildingNum; node++) { // cycleTable초기화
            cycleTable[node] = node;
        }

        while (!pq.isEmpty()) {
            var curEdge = pq.poll();
            int from = curEdge.from;
            int to = curEdge.to;
            int weight = curEdge.weight;

            if (find(from) == find(to)) continue; // 부모가 같은경우 => cycle이 형성될수 있는 경우

            union(from, to); // 같은 부모를 두고 연결

            if (weight == 0) cnt++;

            selectedEdgeNum++;
            if (selectedEdgeNum == buildingNum - 1) break;
        }

        return cnt;
    }

    /**
     * Union-Find 알고리즘 구현(트리구조 기반으로 해야 성능이 좋음.)
     *
     * @param x : 자식노드
     * @return : 부모노드
     */
    static int find(int x) {
        if (cycleTable[x] == x)
            return x;

        return find(cycleTable[x]); // 최대 트리의 깊이만큼 재귀호출
    }

    static void union(int x, int y) {
        x = find(x);
        y = find(y);
        cycleTable[y] = x; // y의 부모를 x의 부모로 설정
    }

    private static void getInputAndInitalize() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        buildingNum = Integer.parseInt(st.nextToken()) + 1; // 입구포함
        roadNum = Integer.parseInt(st.nextToken());

        for (int i = 1; i <= roadNum + 1; i++) {
            st = new StringTokenizer(br.readLine());

            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());

            minHeapEdgePQ.add(new minEdge(from, to, weight));
            maxHeapEdgePQ.add(new maxEdge(from, to, weight));
            // 참고: 우선순위 큐를 안쓰고 구현하는 방법
            // Collections.sort(edgeList, (e1, e2) -> e1.weight - e2.weight); // 오름차순
        }
    }
}