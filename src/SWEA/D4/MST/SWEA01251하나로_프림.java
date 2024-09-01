package SWEA.D4.MST;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * SWEA1251 하나로
 *
 * 1. 입력
 * 1-1. 테스트케이스의 수 T를 입력받는다.
 * 1-2. 섬의 개수 N을 입력받는다.
 * 1-3. 각 섬의 X좌표, Y좌표를 입력받는다. (정수)
 * 1-4. 환경 부담 세율을 입력받는다. (실수)
 *
 * 2. 프림 알고리즘
 * 2-1. 모든 섬들이 연결 가능하므로 모든 간선들을 구한다. (간선의 길이의 제곱을 가중치로 넣는다.)
 * 2-2. 간선들을 오름차순으로 정렬한다.
 * 2-3. 간선들을 make set하여 단위 서로소 집합으로 만든다.
 * 2-4. 간선들을 정렬된 순서로 순회하면서 사이클이 발생하지 않는 경우만 선택한다.
 * 2-5. 선택한 간선의 갯수가 N-1개가 되면 종료한다.
 *
 * 3. 출력
 * 3-1. 크루스칼 알고리즘을 사용하면서 구한 최소 결과에 세율을 곱하여 소수 첫째 자리에서 반올림한다.
 * 3-2. 결과를 출력한다.
 *
 * Utils..
 * - 서로소 집합 메서드들
 * - 간선 클래스
 */

public class SWEA01251하나로_프림 {

    static BufferedReader br;
    static StringTokenizer st;

    static int numOfIsland;
    static long[] islandXInfoArr;
    static long[] islandYInfoArr;
    static double taxRatio;

    static Edge[] edgeArr;
    static int[] parents;


    public static void main(String[] args) throws IOException {

        // 1. 입력
        br = new BufferedReader(new InputStreamReader(System.in));

        // 1-1. 테스트케이스의 수 T를 입력받는다.
        int T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {
            // 1-2. 섬의 개수 N을 입력받는다.
            numOfIsland = Integer.parseInt(br.readLine());

            // 1-3. 각 섬의 X좌표, Y좌표를 입력받는다. (정수)
            islandXInfoArr = new long[numOfIsland];
            islandYInfoArr = new long[numOfIsland];

            st = new StringTokenizer(br.readLine().trim());
            for (int islandIdx = 0; islandIdx < numOfIsland; islandIdx++) {
                islandXInfoArr[islandIdx] = Integer.parseInt(st.nextToken());
            }

            st = new StringTokenizer(br.readLine().trim());
            for (int islandIdx = 0; islandIdx < numOfIsland; islandIdx++) {
                islandYInfoArr[islandIdx] = Integer.parseInt(st.nextToken());
            }

            // 1-4. 환경 부담 세율을 입력받는다. (실수)
            taxRatio = Double.parseDouble(br.readLine());

            // 2. 크루스칼 알고리즘
            // 2-1. 모든 섬들이 연결 가능하므로 모든 간선들을 구한다. (간선의 길이의 제곱을 가중치로 넣는다.)
            edgeArr = new Edge[numOfIsland * (numOfIsland - 1) / 2];
            int cur = 0;

            for (int fromIslandIdx = 0; fromIslandIdx < numOfIsland-1; fromIslandIdx++) {
                for (int toIslandIdx = fromIslandIdx + 1; toIslandIdx < numOfIsland; toIslandIdx++) {
                    long distance = (long) (Math.pow(islandXInfoArr[fromIslandIdx] - islandXInfoArr[toIslandIdx], 2) + Math.pow(islandYInfoArr[fromIslandIdx] - islandYInfoArr[toIslandIdx], 2));
                    Edge curEdge = new Edge(fromIslandIdx, toIslandIdx, distance);
                    edgeArr[cur] = curEdge;
                    cur++;
                }
            }

            // 2-2. 간선들을 오름차순으로 정렬한다.
            Arrays.sort(edgeArr);

            parents = new int[numOfIsland];

            // 2-3. 간선들을 make set하여 단위 서로소 집합으로 만든다.
            for (int islandIdx = 0; islandIdx < numOfIsland; islandIdx++) {
                makeSet(islandIdx);
            }

            // 2-4. 간선들을 정렬된 순서로 순회하면서 사이클이 발생하지 않는 경우만 선택한다.
            int cnt = 0;
            long result = 0;

            for (Edge edge : edgeArr) {
                if (union(edge.start, edge.end)) {
                    result += edge.squaredDistance;
                    // 2-5. 선택한 간선의 갯수가 N-1개가 되면 종료한다.
                    if(++cnt == numOfIsland-1) break;
                }
            }

            // 3. 출력
            // 3-1. 크루스칼 알고리즘을 사용하면서 구한 최소 결과에 세율을 곱하여 소수 첫째 자리에서 반올림한다.
            long finalResult = (long) Math.round(result * taxRatio);
            // 3-2. 결과를 출력한다.
            System.out.println("#" + test_case + " " + finalResult);

        }

    }

    // - 서로소 집합 메서드들
    static void makeSet(int edgeIdx) {
        parents[edgeIdx] = -1;
    }

    static int findSet(int a) {
        if (parents[a] < 0) return a;

        return parents[a] = findSet(parents[a]); // 경로 압축
    }

    static boolean union(int a, int b) {
        int aRoot = findSet(a);
        int bRoot = findSet(b);
        if(aRoot == bRoot) return false;

        // 아래 2줄 순서 주의!
        parents[aRoot] += parents[bRoot]; // 집합 크기 관리 (절대값을 사용하면 집합의 크기가 됨)
        parents[bRoot] = aRoot;
        return true;
    }

    //

    // - 간선 클래스
    static class Edge implements Comparable<Edge> {

        int start, end;
        long squaredDistance;

        public Edge(int start, int end, long squaredDistance) {
            this.start = start;
            this.end = end;
            this.squaredDistance = squaredDistance;
        }

        @Override
        public int compareTo(Edge o) {
            return Long.compare(this.squaredDistance, o.squaredDistance);
        }
    }
}
