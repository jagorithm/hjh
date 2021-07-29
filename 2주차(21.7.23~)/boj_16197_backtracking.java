/**
 * 문제 출처 : https://www.acmicpc.net/problem/16197
 */

import java.util.*;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static char board[][];
    static boolean visited_a[][];
    static boolean visited_b[][];
    static int hight, width;
    static int startX_a, startY_a, startX_b, startY_b;
    static int resultCnt = Integer.MAX_VALUE;

    static int dx[] = {0, 1, 0, -1};
    static int dy[] = {-1, 0, 1, 0};

    public static void main(String[] args) {
        getInputAndInitialize();

        visited_a[startY_a][startX_a] = true;
        visited_b[startY_b][startX_b] = true;
        backtrack(startY_a, startX_a, startY_b, startX_b, 0);

        int ans = (resultCnt == Integer.MAX_VALUE ? -1 : resultCnt);
        System.out.println(ans);
    }

    private static void backtrack(int curY_a, int curX_a, int curY_b, int curX_b, int count) {
        if(count >= 10) return;

        for(int i=0; i<4; i++){
            int ny_a = curY_a + dy[i];
            int nx_a = curX_a + dx[i];
            int ny_b = curY_b + dy[i];
            int nx_b = curX_b + dx[i];

            boolean isOut_a = isOutPosition(ny_a, nx_a);
            boolean isOut_b = isOutPosition(ny_b, nx_b);

            if((isOut_a && !isOut_b) || (!isOut_a && isOut_b)){ //재귀 탈출 조건
                resultCnt = Math.min(resultCnt, count + 1);
                return;
            }

            // 두 점이 동시에 빠져나간 경우
            if(isOut_a && isOut_b) continue;
            // 방문했던 길은 되돌아 가지 않음
            if(visited_a[ny_a][nx_a] || visited_b[ny_b][nx_b]) continue;

            // 다음 위치로 이동할 수 없는 경우 이동하지 않음
            if(board[ny_a][nx_a] == '#'){
                ny_a = curY_a;
                nx_a = curX_a;
            }
            if(board[ny_b][nx_b] == '#'){
                ny_b = curY_b;
                nx_b = curX_b;
            }

            visited_a[ny_a][nx_a] = true;
            visited_b[ny_b][nx_b] = true;
            backtrack(ny_a, nx_a, ny_b, nx_b, count + 1);
            visited_a[ny_a][nx_a] = false;
            visited_b[ny_b][nx_b] = false;
        }
    }

    private static boolean isOutPosition(int curY, int curX) {
        return (curY < 0 || curY >= hight || curX < 0 || curX >= width);
    }

    private static void getInputAndInitialize() {
        String line = sc.nextLine();
        String[] sizes = line.split(" ");

        hight = Integer.parseInt(sizes[0]);
        width = Integer.parseInt(sizes[1]);

        board = new char[hight][width];
        visited_a = new boolean[hight][width];
        visited_b = new boolean[hight][width];

        boolean isFirst = false;
        for(int i=0; i<hight; i++) {
            line = sc.nextLine();

            for(int j=0; j<width; j++){
                char curChar = line.charAt(j);
                board[i][j] = curChar;

                if(isFirst == false && curChar == 'o'){
                    isFirst = true;
                    startY_a = i;
                    startX_a = j;
                }
                else if(isFirst == true && curChar == 'o'){
                    startY_b = i;
                    startX_b = j;
                }
            }
        }
    }
}