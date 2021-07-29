/**
 * 문제 출처 : https://www.acmicpc.net/problem/1303
 */

import java.util.*;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static boolean isOurTeam[][];
    static boolean visited[][];
    static int hight, width;

    static int dx[] = {0, 1, 0, -1};
    static int dy[] = {-1, 0, 1, 0};

    public static void main(String[] args) {
        getInputAndInitialize();

        int areaSize;
        int ourTeamResult = 0, enemyResult = 0;

        for(int i=0; i<hight; i++){
            for(int j=0; j<width; j++){
                if(visited[i][j]) continue;

                areaSize = bfs(i, j);

                if(isOurTeam[i][j]) // 시작 점이 우리팀인지 아닌지
                    ourTeamResult += (areaSize * areaSize);
                else
                    enemyResult += (areaSize * areaSize);
            }
        }

        System.out.print(ourTeamResult + " " + enemyResult);
    }

    private static int bfs(int startY, int startX) {
        int areaCnt = 0;
        boolean isOur = isOurTeam[startY][startX];
        LinkedList<int[]> Q = new LinkedList<>();
        Q.add(new int[] {startY, startX});
        visited[startY][startX] = true;

        while(!Q.isEmpty()){
            int curY = Q.get(0)[0];
            int curX = Q.get(0)[1];
            Q.remove(0);
            areaCnt++;

            for(int i=0; i<4; i++){
                int ny = curY + dy[i];
                int nx = curX + dx[i];

                if(ny < 0 || ny >= hight || nx < 0 || nx >= width) continue;
                if(visited[ny][nx]) continue;
                if(isOur != isOurTeam[ny][nx]) continue;

                visited[ny][nx] = true;
                Q.add(new int[] {ny, nx});
            }
        }

        return areaCnt;
    }


    private static void getInputAndInitialize() {
        String line = sc.nextLine();
        String[] sizes = line.split(" ");

        width = Integer.parseInt(sizes[0]);
        hight = Integer.parseInt(sizes[1]);

        isOurTeam = new boolean[hight][width];
        visited = new boolean[hight][width];

        for(int i=0; i<hight; i++) {
            line = sc.nextLine();
            for(int j=0; j<width; j++){
                if(line.charAt(j) == 'W')
                    isOurTeam[i][j] = true;
            }
        }
    }
}