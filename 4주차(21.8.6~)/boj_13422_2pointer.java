/**
 * 문제 출처 : https://www.acmicpc.net/problem/13422
 */

import java.io.*;
import java.util.*;

public class Main {
    private static int tcNum;
    private static List<Long> inputs;
    private static int homeNum, availHomeNum, maxMoney; // N, M, K

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer tokenizer;
        tcNum = Integer.parseInt(br.readLine());

        for (int i = 0; i < tcNum; i++) {
            tokenizer = new StringTokenizer(br.readLine(), " ");
            homeNum = Integer.parseInt(tokenizer.nextToken());
            availHomeNum = Integer.parseInt(tokenizer.nextToken());
            maxMoney = Integer.parseInt(tokenizer.nextToken());

            tokenizer = new StringTokenizer(br.readLine(), " ");
            inputs = new ArrayList<>();
            for (int h = 1; h <= homeNum; h++) {
                inputs.add(Long.parseLong(tokenizer.nextToken()));
            }

            if(homeNum == availHomeNum){
                int ans = solve();

                if(ans == 0) bw.write("0\n");
                else bw.write("1\n");

                continue;
            }
            bw.write(solve() + "\n");
        }

        bw.flush();
        br.close();
        bw.close();
    }

    private static int solve() {
        int answer = 0;
        long sumOfRegion = getSumOfInitial();

        if(homeNum == 1){
            if (sumOfRegion < (long)maxMoney) answer++;
            return answer;
        }

        if (sumOfRegion < maxMoney) answer++; // 가장 첫번째 구간 검사

        // 두번째 구간부터 배열의 끝 구간까지 검사
        for (int startHomIdx = 1; startHomIdx < homeNum - availHomeNum + 1; startHomIdx++) {
            sumOfRegion -= inputs.get(startHomIdx - 1);
            sumOfRegion += inputs.get(startHomIdx + availHomeNum - 1);

            if (sumOfRegion < (long)maxMoney) answer++;
        }

        // 마지막으로 (availHomeNum + 1) 개의 구간 더 검사
        int toBeAddedIdx = 0;
        for (int startHomIdx = homeNum - availHomeNum + 1; startHomIdx < homeNum; startHomIdx++) {
            sumOfRegion -= inputs.get(startHomIdx - 1);
            sumOfRegion += inputs.get(toBeAddedIdx++);

            if (sumOfRegion < (long)maxMoney) answer++;
        }

        return answer;
    }

    private static long getSumOfInitial() {
        long sum = 0;
        for (int i = 0; i < availHomeNum; i++) {
            sum += inputs.get(i);
        }
        return sum;
    }
}

