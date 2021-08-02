/**
 * 문제 출처 : https://www.acmicpc.net/problem/11726
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int size = Integer.parseInt(br.readLine());

        int[] dp = new int[1001];
        dp[1] = 1;
        dp[2] = 2;

        if (size == 1 || size == 2) {
            System.out.println(dp[size]);
            return;
        }

        for (int i = 3; i <= size; i++) {
            dp[i] = (dp[i - 1] + dp[i - 2]) % 10007;
        }

        System.out.println(dp[size]);
    }
}