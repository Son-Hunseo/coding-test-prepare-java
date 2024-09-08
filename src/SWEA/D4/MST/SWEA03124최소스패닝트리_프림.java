package SWEA.D4.MST;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
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
    static int[] minWeights;

    static long sumOfWeights;

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

            Node[] adjacentList = new Node[numOfNodes + 1];

            for (int edgeIdx = 0; edgeIdx < numOfEdges; edgeIdx++) {
                st = new StringTokenizer(br.readLine().trim());
                int startNodeIdx = Integer.parseInt(st.nextToken());
                int endNodeIdx = Integer.parseInt(st.nextToken());
                int weight = Integer.parseInt(st.nextToken());

                adjacentList[startNodeIdx] = new Node(endNodeIdx, weight, adjacentList[startNodeIdx]);
                adjacentList[endNodeIdx] = new Node(startNodeIdx, weight, adjacentList[endNodeIdx]);
            }

            sumOfWeights = 0;

            isVisited = new boolean[numOfNodes + 1];
            minWeights = new int[numOfNodes + 1];
            Arrays.fill(minWeights, Integer.MAX_VALUE);

            PriorityQueue<Node> pq = new PriorityQueue<>();

            // 1번 노드를 시작점으로 하기 위해
            // 인접 리스트의 0번 자리에 가중치가 0이며 1번 노드를 가리키는 노드를 하나 넣어둔다.
            adjacentList[0] = new Node(1, 0, adjacentList[0]);
            pq.offer(adjacentList[0]);

            while(!pq.isEmpty()) {

                Node cur = pq.poll();

                if (isVisited[cur.nodeNum]) {
                    continue;
                }

                isVisited[cur.nodeNum] = true;
                sumOfWeights += cur.weight;
                System.out.println(cur.nodeNum);
                System.out.println(Arrays.toString(minWeights));

                for (Node curNode = cur; curNode != null; curNode = curNode.nextNode) {

                    if (isVisited[curNode.nodeNum]) {
                        continue;
                    }
                    if(curNode.weight < minWeights[curNode.nodeNum]) {
                        minWeights[curNode.nodeNum] = curNode.weight;
                        pq.offer(adjacentList[curNode.nodeNum]);
                    }
                }
            }

        }

        // 3. 출력
        // 3-1. 결과를 출력한다.
        sb.append(sumOfWeights);
        System.out.println(sb);
    }

    static class Node implements Comparable<Node> {
        int nodeNum; // 현재 노드의 번호
        int weight; // 현재 노드로 들어오는 간선의 가중치
        Node nextNode; // 다음 노드의 주소값

        public Node(int nodeNum, int weight, Node nextNode) {
            this.nodeNum = nodeNum;
            this.weight = weight;
            this.nextNode = nextNode;
        }

        @Override
        public int compareTo(Node target) {
            return Integer.compare(this.weight, target.weight);
        }
    }
}
