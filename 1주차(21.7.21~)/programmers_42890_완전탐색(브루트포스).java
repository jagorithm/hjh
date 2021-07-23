/**
 * 문제 출처 : https://programmers.co.kr/learn/courses/30/lessons/42890
 */

import java.util.*;

class Solution {
    static List<int[]> idxsOfCanBeCandiate;
    static int selectedColSize;
    List<Set<Integer>> candidateKeyResult = new LinkedList<Set<Integer>>();

    public int solution(String[][] relation) {
        int attriSize = relation[0].length;
        int tupleSize = relation.length;

        // 0~7 컬럼 인덱스 배열 만들기
        int[] colIdxs = new int[attriSize];
        for(int i=0; i<attriSize; i++) colIdxs[i] = i;

        // nC1 부터 nC7 까지 상향식으로 검사
        selectedColSize = 0;
        while(selectedColSize++ < attriSize){
            // 1. nCr 구하기
            idxsOfCanBeCandiate = new ArrayList<int[]>(); // nCr들이 담길 리스트
            setIdxCombination(colIdxs, new boolean[8], 0, attriSize, selectedColSize); // nCr 결과 반환

            // 2. 구한 nCr에서 후보키가 될 수 있는 것들 고르기
            HashSet<String> canBeCandidate;
            for(int i=0; i<idxsOfCanBeCandiate.size(); i++){
                int[] partIdxs = idxsOfCanBeCandiate.get(i);
                canBeCandidate = getJoinColumnValSet(relation, partIdxs);

                if(canBeCandidate.size() == tupleSize){ // 유일성 만족
                    updateResult(partIdxs); // 최소성이 만족된다면 추가, 아니면 추가 안함
                }
            }
        }

        return candidateKeyResult.size();
    }

    private void updateResult(int[] partIdxs) {
        HashSet<Integer> curKeySet = new HashSet<>();
        for(int partIdx : partIdxs) curKeySet.add(partIdx);

        for(var candiKey : candidateKeyResult){
            if(curKeySet.containsAll(candiKey))
                return;
        }

        // 새로운 후보키 추가
        candidateKeyResult.add(curKeySet);
    }

    private HashSet<String> getJoinColumnValSet(String[][] relation, int[] partIdxs) {
        HashSet<String> resultSet = new HashSet<>();

        for(String[] tuple : relation){
            String joinedVal = "";

            for(int partIdx : partIdxs){
                joinedVal += (tuple[partIdx] + "-");
            }
            resultSet.add(joinedVal);
        }

        return resultSet;
    }

    static void setIdxCombination(int[] arr, boolean[] visited, int start, int n, int r) {
        if (r == 0) {
            makeResultAndAddToList(arr, visited, n);
            return;
        }

        for (int i = start; i < n; i++) {
            visited[i] = true;
            setIdxCombination(arr, visited, i + 1, n, r - 1);
            visited[i] = false;
        }
    }

    private static void makeResultAndAddToList(int[] arr, boolean[] visited, int n) {
        int[] result = new int[selectedColSize];
        int tmpIdx = 0;
        for (int i = 0; i < n; i++) {
            if (visited[i]) {
                result[tmpIdx++] = arr[i];
            }
        }
        idxsOfCanBeCandiate.add(result);
    }
}

public class Main {
    public static void main(String[] args){
        // input example
        String[][] relation = {  // 정답 2
                {"100", "ryan", "music", "2"},
                {"200", "apeach", "math", "2"},
                {"300", "tube", "computer", "3"},
                {"400", "con", "computer", "4"},
                {"500", "muzi", "music", "3"},
                {"600", "apeach", "music", "2"}
        };

        String[][] relation2 = {  // 정답 1
                {"100"},
                {"200"},
                {"300"},
                {"400"},
                {"500"},
                {"600"}
        };

        String[][] args1 = new String[][] { // 정답 5
                {"4", "4", "사랑", "2020-07-03 오전 12:00:00", "love"}
                , {"5", "4", "같은, 좋은, 와 비슷한", "2020-07-03 오전 12:00:00", "like"}
                , {"6", "4", "사과,대도시", "2020-07-03 오전 12:00:00", "apple"}
                , {"7", "4", "빌다,기도하다,에게 간청하다", "2020-07-03 오전 12:00:00", "pray"}
                , {"8", "3", "빌다,기도하다,에게 간청하다", "2020-06-27 오전 12:00:00", "pray"}
                , {"9", "4", "안녕", "2020-07-03 오전 12:00:00", "hi"}
                , {"10", "3", "같은, 좋은", "2020-06-29 오전 12:00:00", "like"}
                , {"11", "2", "나는 모자를 벗는다", "2020-06-27 오전 12:00:00", "I take off my hat"}};

        String[][] args2 = new String[][] { // 정답 3
                {"4", "4", "사랑", "2020-07-03 오전 12:00:00", "love"}
                , {"5", "4", "같은, 좋은, 와 비슷한", "2020-07-03 오전 12:00:00", "like"}
                , {"6", "4", "사과,대도시", "2020-07-03 오전 12:00:00", "apple"}
                , {"7", "4", "빌다,기도하다,에게 간청하다", "2020-07-03 오전 12:00:00", "pray"}
                , {"8", "4", "빌다,기도하다,에게 간청하다", "2020-06-27 오전 12:00:00", "pray"}
                , {"9", "4", "안녕", "2020-07-03 오전 12:00:00", "hi"}
                , {"10", "3", "같은, 좋은", "2020-06-29 오전 12:00:00", "like"}
                , {"11", "2", "나는 모자를 벗는다", "2020-06-27 오전 12:00:00", "I take off my hat"}};

        int ans = new Solution().solution(relation2);
        System.out.println(ans);
    }
}