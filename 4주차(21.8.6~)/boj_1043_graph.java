/**
 * 문제 출처 : https://www.acmicpc.net/problem/1043
 */

import java.io.*;
import java.util.*;

public class Main {
    private static int numOfPerson, numOfParty;
    private static int numOfPersonOfKnow;
    private static Map<Integer, Set<Integer>> personToPartyMap;
    private static List<Set<Integer>> adjList;
    private static Queue<Integer> peopleWhoKnow; // 비밀 퍼트리기용(BFS)
    private static boolean[] isKnown; // 비밀 퍼트리기용(BFS)
    private static Map<Integer, Integer> partyToCntMap; // 참가자가 0인 파티에 대응하기 위함

    public static void main(String[] args) throws IOException {
        getInputAndInitalize();
        setUnkwownPeople();

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        bw.write(solve() + "");

        bw.flush();
        bw.close();
    }

    private static int solve() {
        Set<Integer> ansParty = new HashSet<>();

        for (int party = 1; party <= numOfParty; party++) {
            if (partyToCntMap.get(party) == 0)
                ansParty.add(party);
        }

        for (int person = 1; person <= numOfPerson; person++) {
            if (isKnown[person]) continue;

            for (int party : personToPartyMap.get(person)) { // ansParty.addAll()과 같음
                ansParty.add(party);
            }
        }

        return ansParty.size();
    }

    private static void setUnkwownPeople() {
        // queue를 사용해서 비밀을 퍼트리기 (BFS)

        while (!peopleWhoKnow.isEmpty()) {
            int person = peopleWhoKnow.poll();
            isKnown[person] = true;

            for (int adj : adjList.get(person)) {
                if (isKnown[adj]) continue;
                peopleWhoKnow.add(adj);
            }
        }
    }

    private static void getInputAndInitalize() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tokenizer = new StringTokenizer(br.readLine(), " ");

        // 사람 수, 파티 수 입력 받기
        numOfPerson = Integer.parseInt(tokenizer.nextToken());
        numOfParty = Integer.parseInt(tokenizer.nextToken());

        // 진실을 알고 있는 사람 입력받기
        tokenizer = new StringTokenizer(br.readLine(), " ");
        numOfPersonOfKnow = Integer.parseInt(tokenizer.nextToken());
        peopleWhoKnow = new LinkedList<>();
        if (numOfPersonOfKnow != 0) {
            for (int i = 0; i < numOfPersonOfKnow; i++) {
                peopleWhoKnow.add(Integer.parseInt(tokenizer.nextToken()));
            }
        }

        // 사람 수 만큼 인접리스트 및 사람:파티 맵 생성
        adjList = new ArrayList<>();
        personToPartyMap = new HashMap<>();
        adjList.add(null); // 더미
        for (int personNum = 1; personNum <= numOfPerson; personNum++) {
            Set<Integer> adjSet = new HashSet<>();
            adjSet.add(personNum);
            adjList.add(adjSet);

            personToPartyMap.put(personNum, new HashSet<>());
        }

        // 파티 정보 입력 받기 (양방향 인접리스트)
        partyToCntMap = new HashMap<>();
        for (int curPartyNum = 1; curPartyNum <= numOfParty; curPartyNum++) {
            tokenizer = new StringTokenizer(br.readLine(), " ");

            int participatedPersonNum = Integer.parseInt(tokenizer.nextToken());\
            partyToCntMap.put(curPartyNum, participatedPersonNum);

            // 인접정보를 임시 셋에 담기
            Set<Integer> tmpAdjPerson = new HashSet<>();
            for (int t = 0; t < participatedPersonNum; t++) {
                tmpAdjPerson.add(Integer.parseInt(tokenizer.nextToken()));
            }

            // 자기자신 포함해서 인접리스트에 넣기
            for (int adjPerson : tmpAdjPerson) {
                adjList.get(adjPerson).addAll(tmpAdjPerson);
                personToPartyMap.get(adjPerson).add(curPartyNum);
            }
        }

        isKnown = new boolean[numOfPerson + 1]; // 인덱스 0 사용 안함
        br.close();
    }
}