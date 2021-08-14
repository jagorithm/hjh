/**
 * 문제 출처 : https://www.acmicpc.net/problem/15961
 */

import java.io.*;
import java.util.*;

public class Main {
    static int dishNum, susiNum, contiguousDishNum, couponNum;
    static int rotation[];
    static Map<Integer, Integer> areaCntMap = new HashMap<>();  // 구역의 원소, 개수
    static int maxCnt = 0;

    public static void main(String[] args) throws IOException {
        getInput();

        for (int i = 0; i < contiguousDishNum; i++) {  // 초기상태(첫번째 구역) 입력
            if (rotation[i] == couponNum) continue;  // 쿠폰은 구역에 추가하지 않는다.
            areaCntMap.put(rotation[i], areaCntMap.getOrDefault(rotation[i], 0) + 1);
        }

        for (int i = 1; i < dishNum; i++) {
            if (areaCntMap.containsKey(rotation[i - 1])) {
                if (areaCntMap.get(rotation[i - 1]) == 1)
                    areaCntMap.remove(rotation[i - 1]);
                else
                    areaCntMap.put(rotation[i - 1], areaCntMap.get(rotation[i - 1]) - 1);
            }

            int endIdx = i + contiguousDishNum - 1;
            if (rotation[endIdx] == couponNum) continue;

            areaCntMap.put(rotation[endIdx], areaCntMap.getOrDefault(rotation[endIdx], 0) + 1);

            maxCnt = Math.max(maxCnt, areaCntMap.size());
        }

        System.out.println(maxCnt + 1); // 쿠폰까지 포함하므로 +1
    }

    private static void getInput() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        dishNum = Integer.parseInt(st.nextToken());
        susiNum = Integer.parseInt(st.nextToken());
        contiguousDishNum = Integer.parseInt(st.nextToken());
        couponNum = Integer.parseInt(st.nextToken());

        rotation = new int[dishNum + contiguousDishNum - 1];

        for (int i = 0; i < dishNum; i++) {
            st = new StringTokenizer(br.readLine());
            rotation[i] = Integer.parseInt(st.nextToken());
        }
        int tmpIdx = 0;
        for (int i = dishNum; i < dishNum + contiguousDishNum - 1; i++) {
            rotation[i] = rotation[tmpIdx++];
        }
    }
}