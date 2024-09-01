package SWEA.D4.MST;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * SWEA 3124 최소스패닝트리 - 프림으로 풀기
 *
 * 1. 입력
 * 1-1. 테스트케이스의 개수 T를 입력받는다.
 * 1-2. 정점의 개수 V와 간선의 개수 E를 입력받는다.
 * 1-3. 간선의 정보를 입력받는다. (출발노드 도착노드 가중치)
 *
 * 2. 프림 알고리즘
 *
 * 3. 출력
 * 3-1. 결과를 출력한다.
 *
 */

public class SWEA03124최소스패닝트리_프림 {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static boolean[] isVisited;

    public static void main(String[] args) throws IOException {

        // 1. 입력
        br = new BufferedReader(new InputStreamReader(System.in));

        // 1-1. 테스트케이스의 개수 T를 입력받는다.
        int T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {

            sb = new StringBuilder();
            sb.append("#");
            sb.append(test_case);
            sb.append(" ");

            // 1-2. 정점의 개수 V와 간선의 개수 E를 입력받는다.
            st = new StringTokenizer(br.readLine().trim());

            int numOfNodes = Integer.parseInt(st.nextToken());
            int numOfEdges = Integer.parseInt(st.nextToken());

            // 1-3. 간선의 정보를 입력받는다. (출발노드 도착노드 가중치)

            ArrayList<Edge>[] edgeListArr = new ArrayList[numOfNodes + 1];

            for (int idx = 1; idx <= numOfNodes; idx++) {
                edgeListArr[idx] = new ArrayList<>();
            }

            for (int edgeIdx = 0; edgeIdx < numOfEdges; edgeIdx++) {
                st = new StringTokenizer(br.readLine().trim());
                int startNodeIdx = Integer.parseInt(st.nextToken());
                int endNodeIdx = Integer.parseInt(st.nextToken());
                int weight = Integer.parseInt(st.nextToken());

                edgeListArr[startNodeIdx].add(new Edge(startNodeIdx, endNodeIdx, weight));
                edgeListArr[endNodeIdx].add(new Edge(startNodeIdx, endNodeIdx, weight));
            }

            long sumOfWeights = 0;

            isVisited = new boolean[numOfNodes + 1];
            isVisited[0] = true;

            PriorityQueue<Edge> pq = new PriorityQueue<>();

            pq.add(new Edge(1, 1, 0));

            while (!pq.isEmpty()) {
                Edge curEdge = pq.poll();

                if (isVisited[curEdge.endNodeIdx]) {
                    continue;
                }

                isVisited[curEdge.endNodeIdx] = true;
                sumOfWeights += curEdge.weight;

                for (Edge toEdge : edgeListArr[curEdge.endNodeIdx]) {
                    if (isVisited[toEdge.endNodeIdx]) {
                        return;
                    }
                    pq.add(toEdge);
                }
            }

            // 3. 출력
            // 3-1. 결과를 출력한다.
            sb.append(sumOfWeights);
            System.out.println(sb);
        }

    }

    static class Edge implements Comparable<Edge> {
        int startNodeIdx;
        int endNodeIdx;
        int weight;

        public Edge(int startNodeIdx, int endNodeIdx, int weight) {
            this.startNodeIdx = startNodeIdx;
            this.endNodeIdx = endNodeIdx;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge targetEdge) {
            if (this.weight < targetEdge.weight) {
                return -1;
            } else {
                return 1;
            }
        }
    }
}
