/**
 * 문제 출처 : https://www.acmicpc.net/problem/7562
 */

import java.util.*;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static int step[][];
    static int len;
    static int startX, startY;
    static int endX, endY;

    static int dx[] = {-2, -1, 1, 2, 2, 1, -1, -2};
    static int dy[] = {-1, -2, -2, -1, 1, 2, 2, 1};

    public static void main(String[] args) {
        int tcNum = sc.nextInt();
        while(tcNum-- > 0){
            getInput();
            bfs();
            System.out.println(step[endY][endX]);
        }
    }

    private static void bfs() {
        boolean[][] visited = new boolean[len][len];

        List<int[]> Q = new LinkedList<>(); // y, x
        Q.add(new int[] {startY, startX});
        visited[startY][startX] = true;

        while(!Q.isEmpty()){
            int curY = Q.get(0)[0];
            int curX = Q.get(0)[1];

            Q.remove(0);

            for(int i=0; i<8; i++){
                int ny = curY + dy[i];
                int nx = curX + dx[i];

                if(ny >= len || ny < 0 || nx >= len || nx < 0) continue;
                if(visited[ny][nx]) continue;

                visited[ny][nx] = true;
                step[ny][nx] = step[curY][curX] + 1; // bfs단계 + 1

                if(ny == endY && nx == endX){
                    return;
                }

                Q.add(new int[] {ny, nx});
            }
        }
    }

    private static void getInput() {
        len = sc.nextInt();
        startY = sc.nextInt();
        startX = sc.nextInt();
        endY = sc.nextInt();
        endX = sc.nextInt();
        step = new int[len][len];
    }
}