/**
 * 문제 출처 : https://www.acmicpc.net/problem/2504
 */

import java.io.*;
import java.util.Stack;

public class Main {
    private static String input;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        input = br.readLine();

        bw.write(Integer.toString(solve()));

        bw.flush();
        bw.close();
        br.close();
    }

    private static int solve() {
        if (input.length() % 2 == 1) return 0;

        Stack<Character> stack = new Stack<>();
        int areaVal = 1;
        int sum = 0;

        for (int i = 0; i < input.length(); i++) {
            char cur = input.charAt(i);

            if (cur == '(') {
                areaVal *= 2;
                stack.push('(');
            } else if (cur == '[') {
                areaVal *= 3;
                stack.push('[');
            } else if (cur == ')') {
                if (stack.isEmpty() || stack.peek() != '(')
                    return 0;
                if (input.charAt(i - 1) == '(')
                    sum += areaVal;

                stack.pop();
                areaVal /= 2;
            } else if (cur == ']') {
                if (stack.isEmpty() || stack.peek() != '[')
                    return 0;
                if (input.charAt(i - 1) == '[')
                    sum += areaVal;

                stack.pop();
                areaVal /= 3;
            }
        }

        return sum;
    }
}