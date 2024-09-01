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
 * 2-1. 모든 섬들이 연결 가능하므로 모든 간선들의 가중치를 구해서 인접 행렬에 넣는다. (간선의 길이의 제곱을 가중치로 넣는다.)
 * 2-2. 최소 간선 배열의 0번째 인덱스 요소를 0으로 만들어서 출발 섬을 0번째 섬으로 지정한다.
 * 2-3. 최소 간선 배열에서 방문하지 않은 섬 중 가장 짧은 길이의 간선을 가진 섬을 현재 방문할 섬으로 지정한다.
 * 2-4. 더이상 방문할 섬이 없을 경우 종료한다.
 * 2-5. 현재 방문한 섬을 방문처리를하고, 현재 섬으로 들어오는 가장 짧은 길이의 간선을 결과에 더한다.
 * 2-6. 현재 섬에서 갈 수 있는 (방문하지 않은) 섬들의 간선의 길이를 최소 간선 배열에 저장된 간선보다 작다면 갱신한다.
 *
 * 3. 출력
 * 3-1. 프림 알고리즘을 사용하면서 구한 최소 결과에 세율을 곱하여 소수 첫째 자리에서 반올림한다.
 * 3-2. 결과를 출력한다.
 *
 */

public class SWEA01251하나로_프림 {

    static BufferedReader br;
    static StringTokenizer st;

    static int numOfIsland;
    static long[] islandXInfoArr;
    static long[] islandYInfoArr;
    static double taxRatio;
    static long[][] islandMatrix;

    static long[] minEdgeArr;
    static boolean[] isVisited;
    static long result;

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
            islandMatrix = new long[numOfIsland][numOfIsland];

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

            // 2. 프림 알고리즘
            // 2-1. 모든 섬들이 연결 가능하므로 모든 간선들의 가중치를 구해서 인접 행렬에 넣는다. (간선의 길이의 제곱을 가중치로 넣는다)
            for (int fromIslandIdx = 0; fromIslandIdx < numOfIsland; fromIslandIdx++) {
                for (int toIslandIdx = fromIslandIdx + 1; toIslandIdx < numOfIsland; toIslandIdx++) {
                    long value = (long) (Math.pow(islandXInfoArr[fromIslandIdx] - islandXInfoArr[toIslandIdx], 2) + Math.pow(islandYInfoArr[fromIslandIdx] - islandYInfoArr[toIslandIdx], 2));
                    islandMatrix[fromIslandIdx][toIslandIdx] = value;
                    islandMatrix[toIslandIdx][fromIslandIdx] = value;
                }
            }

            minEdgeArr = new long[numOfIsland];
            Arrays.fill(minEdgeArr, Long.MAX_VALUE);

            // 2-2. 최소 간선 배열의 0번째 인덱스 요소를 0으로 만들어서 출발 섬을 0번째 섬으로 지정한다.
            minEdgeArr[0] = 0;

            isVisited = new boolean[numOfIsland];

            result = 0;

            // 첫 출발은 0번 섬 부터
            Prim();

            // 3. 출력
            // 3-1. 프림 알고리즘을 사용하면서 구한 최소 결과에 세율을 곱하여 소수 첫째 자리에서 반올림한다.
            long finalResult = (long) Math.round(result * taxRatio);
            // 3-2. 결과를 출력한다.
            System.out.println("#" + test_case + " " + finalResult);

        }

    }

    static void Prim() {

        int minFromIslandIdx = -1;
        long minDistance = Long.MAX_VALUE;

        // 2-3. 최소 간선 배열에서 방문하지 않은 섬 중 가장 짧은 길이의 간선을 가진 섬을 현재 방문할 섬으로 지정한다.
        for (int curFromIslandIdx = 0; curFromIslandIdx < numOfIsland; curFromIslandIdx++) {
            if (isVisited[curFromIslandIdx]) {
                continue;
            }

            if (minEdgeArr[curFromIslandIdx] < minDistance) {
                minDistance = minEdgeArr[curFromIslandIdx];
                minFromIslandIdx = curFromIslandIdx;
            }
        }

        // 2-4. 더이상 방문할 섬이 없을 경우 종료한다.
        if (minFromIslandIdx == -1) {
            return;
        }

        // 2-5. 현재 방문한 섬을 방문처리를하고, 현재 섬으로 들어오는 가장 짧은 길이의 간선을 결과에 더한다.
        isVisited[minFromIslandIdx] = true;
        result += minDistance;

        // 2-6. 현재 섬에서 갈 수 있는 (방문하지 않은) 섬들의 간선의 길이를 최소 간선 배열에 저장된 간선보다 작다면 갱신한다.
        for (int curToIslandIdx = 0; curToIslandIdx < numOfIsland; curToIslandIdx++) {

            if (isVisited[curToIslandIdx]) { // 방문한거 스킵
                continue;
            }

            if (minFromIslandIdx == curToIslandIdx) { // 시작지점과 도착지점이 같은 점 스킵
                continue;
            }

            if (islandMatrix[minFromIslandIdx][curToIslandIdx] < minEdgeArr[curToIslandIdx]) {
                minEdgeArr[curToIslandIdx] = islandMatrix[minFromIslandIdx][curToIslandIdx];
            }
        }

        Prim();
    }
}
