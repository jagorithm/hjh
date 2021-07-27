/**
 * 문제 출처 : https://www.acmicpc.net/problem/17396
 */

import java.util.*;

class Node implements Comparable<Node>{
    long weight;
    int v;

    public Node(long weight, int v) {
        this.weight = weight;
        this.v = v;
    }

    @Override
    public int compareTo(Node o){
        // 오름차순 (min heap 기반 우선순위 큐)
        if(this.weight > o.weight) return 1;
        else if(this.weight < o.weight) return -1;
        else return 0;
    }

    @Override
    public String toString() {
        return "Node{" +
                "weight='" + weight + '\'' +
                ", to=" + v +
                '}';
    }
}

public class Main {
    static int nodeNum, lineNum;
    static ArrayList<Node>[] adjInfo; // 인접리스트

    // 다익스트라 알고리즘에서 사용
    // 최대 edge개수가 300,000개 이고, cost가 최대 100,000이므로
    // 3*10^10 은 int로 표현 불가. 따라서 dist는 long(8바이트) 사용
    static long dist[];
    static boolean visited[];

    public static void main(String[] args) {
        getInput();

        doDijkstra(0);
        long result = dist[nodeNum-1] == Long.MAX_VALUE ? -1 : dist[nodeNum-1];
        System.out.println(result);
    }

    private static void doDijkstra(int start) {
        Arrays.fill(dist, Long.MAX_VALUE);

        dist[start] = 0;
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(0, start));

        while(!pq.isEmpty()){
            Node now = pq.poll();

            if(visited[now.v]) continue;

            for(Node next : adjInfo[now.v]){
                if(visited[next.v]) continue;

                if(dist[next.v] > dist[now.v] + next.weight){
                    dist[next.v] = dist[now.v] + next.weight;
                    pq.add(new Node(dist[next.v], next.v));
                }
            }
            visited[now.v] = true;
        }
    }

    private static void getInput() {
        Scanner sc = new Scanner(System.in);

        nodeNum = sc.nextInt();
        lineNum = sc.nextInt();

        visited = new boolean[nodeNum];
        adjInfo = new ArrayList[nodeNum];
        dist = new long[nodeNum];

        for(int i=0; i<nodeNum; i++) {
            int cango = sc.nextInt();
            visited[i] = cango == 1 ? true : false;
        }
        visited[nodeNum-1] = false; // 마지막은 갈 수 있는 점으로 마킹

        for (int i = 0; i<nodeNum; i++) {
            adjInfo[i] = new ArrayList<Node>();
        }

        int from, to, time;
        for(int i=0; i<lineNum; i++){
            from = sc.nextInt();
            to = sc.nextInt();
            time = sc.nextInt();

            adjInfo[from].add(new Node(time, to));
            adjInfo[to].add(new Node(time, from));
        }
    }
}