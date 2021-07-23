/**
 * 문제 출처 : https://www.acmicpc.net/problem/2806
 */

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        
        // 주의: nextLine으로 받아야 입력문자열을 공백string으로 안받을 수 있음
        int inputSize = Integer.parseInt(sc.nextLine());
        String inputStr = sc.nextLine();
        char[] inputs = inputStr.toCharArray();

        // dp[i][0] : 0부터 i번째까지 모두 A로 바꾸는 최소 가지수
        // dp[i][1] : 0부터 i번째까지 모두 B로 바꾸는 최소 가지수
        int[][] dp = new int[inputSize][2];

        if(inputs[0] == 'A'){ // 초항 설정
            dp[0][0] = 0;
            dp[0][1] = 1;
        }
        else{
            dp[0][0] = 1;
            dp[0][1] = 0;
        }

        for(int i=1; i<inputSize; i++){
            if(inputs[i] == 'A'){
                dp[i][0] = Math.min(dp[i-1][0], dp[i-1][1] + 1);
                dp[i][1] = Math.min(dp[i-1][0] + 1, dp[i-1][1] + 1);
            }
            else{ // 'B'
                dp[i][0] = Math.min(dp[i-1][0] + 1, dp[i-1][1] + 1);
                dp[i][1] = Math.min(dp[i-1][0] + 1, dp[i-1][1]);
            }
        }

        System.out.println(dp[inputSize-1][0]);
    }
}