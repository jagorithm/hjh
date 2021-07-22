/**
 * 문제 출처 : https://www.acmicpc.net/problem/12845
 */

import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int inputSize = sc.nextInt();

        int curLevel, maxLevel = 0, sumExclusiveMaxLevel = 0;
        for(int i=0; i<inputSize; i++){
            curLevel = sc.nextInt();

            sumExclusiveMaxLevel += curLevel;
            maxLevel = Math.max(curLevel, maxLevel);
        }
        sumExclusiveMaxLevel -= maxLevel;

        // maxLevel은 그 이하의 n-1개의 level과 더해지기 때문에 maxLevel * (inputSize - 1)이 필요하고
        // 그 이하의 n-1개의 level의 합은 sumExclusiveMaxLevel으로 계산되어 있음.
        int answer = sumExclusiveMaxLevel + maxLevel * (inputSize - 1);
        System.out.println(answer);
    }
}