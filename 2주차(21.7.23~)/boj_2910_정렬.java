/**
 * 문제 출처 : https://www.acmicpc.net/problem/2910
 */

import java.util.*;

public class Main {
    static Map<Integer, Integer> orderMap = new HashMap<>(); // 원소, 인덱스

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        // 주의: nextLine으로 받아야 입력문자열을 공백string으로 안받을 수 있음
        int inputSize = sc.nextInt();
        int maxNum = sc.nextInt();

        Map<Integer, Integer> map = new HashMap<>(); // <숫자, 그 숫자의 개수>

        int orderIdx = 0;
        for(int i=0; i<inputSize; i++){
            int inputNum = sc.nextInt();

            if(map.containsKey(inputNum)){
                map.put(inputNum, map.get(inputNum) + 1);
            }
            else{
                map.put(inputNum, 1);
                orderMap.put(inputNum, orderIdx++);
            }
        }

        int maxCount = 0;
        Map<Integer, List<Integer>> countMap = new HashMap<>();
        for(var curKey : map.keySet()){
            int curCount = map.get(curKey);
            maxCount = Math.max(maxCount, curCount);

            if(countMap.containsKey(curCount)){
                var valueList = countMap.get(curCount);
                var newList = insertToValidPos(valueList, curKey);
                countMap.put(curCount, newList);
            }
            else{
                var tmpValueList = new LinkedList<Integer>();
                tmpValueList.add(curKey);

                countMap.put(curCount, tmpValueList);
            }
        }

        for(int cnt=maxCount; cnt>=1; cnt--){
            if(countMap.containsKey(cnt) == false) continue;

            for(var val : countMap.get(cnt)){
                for(int i=0; i<cnt; i++)
                    System.out.print(val + " ");
            }
        }
    }

    private static List<Integer> insertToValidPos(List<Integer> valueList, Integer curKey) {
        for(int i=0; i<valueList.size(); i++){
            int compTarget = valueList.get(i);

            // 인덱스비교(등장한 순서 비교)
            if(orderMap.get(compTarget) < orderMap.get(curKey)) {
                if(valueList.size() - 1 == i){
                    valueList.add(curKey);
                    break;
                }

                continue;
            }

            valueList.add(i, curKey);
            break;
        }

        return valueList;
    }
}