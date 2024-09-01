package SWEA.D4.MST;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.StringTokenizer;

/**
 * SWEA 3124 최소스패닝트리 - 크루스칼로 풀기
 *
 * 1. 입력
 * 1-1. 테스트케이스의 개수 T를 입력받는다.
 * 1-2. 정점의 개수 V와 간선의 개수 E를 입력받는다.
 * 1-3. 간선의 정보를 입력받는다. (출발노드 도착노드 가중치)
 *
 * 2. 크루스칼 알고리즘
 * 2-1. 간전들을 가중치가 작은 순서대로 정렬한다.
 * 2-2. 간선을 가중치가 작은 것 부터 순회하면서 V-1개가 선택될 때까지 선택한다. (단, 해당 간선을 연결했을 때, 이미 같은 서로소 집합이라면 넘어간다.)
 * 2-3. 선택하는 동시에 가중치를 결과에 더한다.
 *
 * 3. 출력
 * 3-1. 결과를 출력한다.
 *
 * Utils
 * - make
 * - find
 * - union
 */

public class SWEA03124최소스패닝트리_크루스칼 {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int[] parent;

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

            ArrayList<Edge> edgeList = new ArrayList<>();

            for (int edgeIdx = 0; edgeIdx < numOfEdges; edgeIdx++) {
                st = new StringTokenizer(br.readLine().trim());
                int startNodeIdx = Integer.parseInt(st.nextToken());
                int endNodeIdx = Integer.parseInt(st.nextToken());
                int weight = Integer.parseInt(st.nextToken());

                edgeList.add(new Edge(startNodeIdx, endNodeIdx, weight));
            }

            parent = new int[numOfNodes + 1];

            // 단위 서로소 집합으로 초기화
            for (int NodeIdx = 1; NodeIdx <= numOfNodes; NodeIdx++) {
                make(NodeIdx);
            }

            // 2. 크루스칼 알고리즘
            // 2-1. 간전들을 가중치가 작은 순서대로 정렬한다.
            Collections.sort(edgeList);

            // 2-2. 간선을 가중치가 작은 것 부터 순회하면서 V-1개가 선택될 때까지 선택한다. (단, 해당 간선을 연결했을 때, 이미 같은 서로소 집합이라면 넘어간다.)

            int cnt = 0;
            long sumOfWeights = 0;

            for (Edge edge : edgeList) {

                if (cnt == numOfNodes -1) {
                    break;
                }

                if (!union(edge.startNodeIdx, edge.endNodeIdx)) {
                    continue;
                }

                // 2-3. 선택하는 동시에 가중치를 결과에 더한다.
                cnt++;
                sumOfWeights += edge.weight;
            }

            // 3. 출력
            // 3-1. 결과를 출력한다.
            sb.append(sumOfWeights);
            System.out.println(sb);
        }

    }

    static void make(int NodeIdx) {
        parent[NodeIdx] = -1;
    }

    static int find(int NodeIdx) {

        if (parent[NodeIdx] < 0) {
            return NodeIdx;
        }

        return parent[NodeIdx] = find(parent[NodeIdx]); // 경로 압축
    }

    static boolean union(int NodeAIdx, int NodeBIdx) {

        int rootA = find(NodeAIdx);
        int rootB = find(NodeBIdx);

        if (rootA == rootB) {
            return false;
        }

        parent[rootA] += parent[rootB];
        parent[rootB] = rootA;
        return true;
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
