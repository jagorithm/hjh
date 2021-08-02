/**
 * 문제 출처 : https://www.acmicpc.net/problem/1541
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static String input;
    static int[] absNumbers = new int[26]; // 최대 5자리이므로 int로 가능
    static boolean[] isPuls = new boolean[26];
    static int numOfNumber;

    public static void main(String[] args) throws IOException {
        getInputAndParse();

        int i, sum = 0;
        for (i = 0; i < numOfNumber; i++) { // - 숫자가 나올 때까지 모두 더합니다.
            if(isPuls[i])
                sum += absNumbers[i];
            else
                break;
        }
        if(numOfNumber == i) { // - 숫자가 나오지 않았다면 합계를 반환
            System.out.println(sum);
            return;
        }

        int tmpSum = 0; // 남은 숫자를 모두 더할 변수
        for (; i < numOfNumber; i++) { // - 가 한번 등장한다면 그 뒤의 모든 수를 더해 sum에서 뺀다.
            tmpSum += absNumbers[i];
        }
        sum -= tmpSum;
        System.out.println(sum);
    }

    private static void getInputAndParse() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        input = br.readLine();
        if (Character.isDigit(input.charAt(0)))
            input = "+" + input;

        int tokensIdx = 0;
        int numberStartIdx = 1;
        for (int i = 1; i < input.length(); i++) {
            char curChar = input.charAt(i);
            if (Character.isDigit(curChar)) continue;

            if (input.charAt(numberStartIdx - 1) == '+')
                isPuls[tokensIdx] = true;
            else
                isPuls[tokensIdx] = false;

            absNumbers[tokensIdx] = Integer.parseInt(input.substring(numberStartIdx, i));

            numberStartIdx = i + 1;
            tokensIdx++;
        }
        // 마지막 원소 넣기
        if (input.charAt(numberStartIdx - 1) == '+')
            isPuls[tokensIdx] = true;
        else
            isPuls[tokensIdx] = false;
        
        absNumbers[tokensIdx] = Integer.parseInt(input.substring(numberStartIdx, input.length()));

        numOfNumber = tokensIdx + 1;
    }
}