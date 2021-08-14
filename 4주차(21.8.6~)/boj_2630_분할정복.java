/**
 * 문제 출처 : https://www.acmicpc.net/problem/2630
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    private static boolean[][] map;
    private static int whiteBoxNumber, blueBoxNumber;
    private static int mapSize;

    public static void main(String[] args) throws IOException {
        getInputAndInitialize();

        solve(0, 0, mapSize);

        System.out.println(whiteBoxNumber);
        System.out.println(blueBoxNumber);
    }

    private static void solve(int y, int x, int size) { // start y, x
        if (size == 1) {
            if (map[y][x] == true) blueBoxNumber++;
            else whiteBoxNumber++;
            return;
        }
        boolean pivot = map[y][x];
        int areaCnt = 0;
        int newSize = 0;

        for (int i = y; i < y + size; i++) {
            for (int j = x; j < x + size; j++) {
                if (map[i][j] == pivot) {
                    areaCnt++;
                    continue;
                }

                newSize = size / 2;

                solve(y, x, newSize);
                solve(y, x + newSize, newSize);
                solve(y + newSize, x, newSize);
                solve(y + newSize, x + newSize, newSize);
                break;
            }
            if(newSize != 0) break;
        }

        if (size * size == areaCnt) { // 구역이 모두 같은 값일 경우
            if (pivot == true) blueBoxNumber++;
            else whiteBoxNumber++;
        }
    }

    private static void getInputAndInitialize() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        mapSize = Integer.parseInt(br.readLine());

        map = new boolean[mapSize][mapSize];

        int j = 0;
        for (int i = 0; i < mapSize; i++) {
            String line = br.readLine();

            for (int t = 0; t < line.length(); t++) {
                if (line.charAt(t) == ' ') continue;
                map[i][j++] = (line.charAt(t) == '1' ? true : false);
            }
            j = 0;
        }
    }
}