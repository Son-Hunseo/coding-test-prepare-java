package SWEA.모의A;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * SWEA 2105 디저트카페
 *
 * [제약조건]
 * - 사각형(평행사변형)이 완성되어야한다.
 * - 같은 디저트(같은 숫자)를 먹을 수 없다.
 *
 * 1. 입력
 * 1-1. 테스트 케이스의 개수 T를 입력받는다.
 * 1-2. 디저트 맵의 크기 N을 입력받는다.
 * 1-3. 디저트 맵을 입력 받는다.
 *
 * 2. 탐색
 * - 우상향 방향을 시작으로 시계방향으로 돈다. (한 방향으로만 돌아도 모든 칸을 탐색하기 때문에 모든 경우의 수 카운팅이 보장된다)
 * - 중요! ->> 어짜피 처음 위치로 돌아오지 못하는 경우들은 격자 밖으로 나가서 사라질 것이다. (2-7번 조건 덕분에 제대로 셀 수 있다.)
 * 2-1. 격자 밖으로 나가면 재귀 종료
 * 2-2. 진행 방향이 좌상단이며 처음 위치로 돌아올 경우 이때까지 먹은 디저트의 개수를 세고 result를 갱신한다.
 * 2-3. 먹었던 디저트를 만나면 재귀 종료
 * 2-4. 위 조건이 안걸리면 디저트 먹은 것으로 하고 방문 처리
 * 2-5. 현재 진행 방향으로 가는 경우로 재귀 들어감
 * 2-6. 방향을 시계방향으로 바꾸고 가는 경우로 재귀 들어감
 * 2-7. 현재 들어온 위치에서 더이상 갈 곳이 없는 경우가 생길 수 있다. 이 경우 현재 위치 자체가 쓸모 없는 위치이므로, 마지막에 먹었던 디저트의 방문처리를 취소한다.
 *
 * 3. 출력
 * 3-1. result를 출력한다.
 */

public class SWEA02105디저트카페 {

    static BufferedReader br;
    static StringTokenizer st;

    static int sizeOfMap;
    static int[][] map;
    static int result;

    // 우상, 우하, 좌하, 좌상
    static final int[] dRow = {-1, 1, 1, -1};
    static final int[] dCol = {1, 1, -1, -1};

    static int startRowIdx;
    static int startColIdx;
    static boolean[] isVisited;

    public static void main(String[] args) throws IOException {

        // 1. 입력
        br = new BufferedReader(new InputStreamReader(System.in));

        // 1-1. 테스트 케이스의 개수 T를 입력받는다.
        int T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {

            // 1-2. 디저트 맵의 크기 N을 입력받는다.
            sizeOfMap = Integer.parseInt(br.readLine());

            // 1-3. 디저트 맵을 입력 받는다.
            map = new int[sizeOfMap][sizeOfMap];

            for (int rowIdx = 0; rowIdx < sizeOfMap; rowIdx++) {
                st = new StringTokenizer(br.readLine().trim());
                for (int colIdx = 0; colIdx < sizeOfMap; colIdx++) {
                    map[rowIdx][colIdx] = Integer.parseInt(st.nextToken());
                }
            }

            // 2.
            // 2-0. 반드시 result 초기화!
            result = -1;
            for (int rowIdx = 0; rowIdx < sizeOfMap; rowIdx++) {
                for (int colIdx = 0; colIdx < sizeOfMap; colIdx++) {
                    startRowIdx = rowIdx;
                    startColIdx = colIdx;
                    isVisited = new boolean[101];
                    eatDesert(rowIdx, colIdx, 0);
                }
            }

            // 3. 출력
            // 3-1. result를 출력한다.
            System.out.println("#" + test_case + " " + result);
        }

    }

    static void eatDesert(int rowIdx, int colIdx, int dr) {

        // 2-1. 격자 밖으로 나가면 재귀 종료
        if (isOut(rowIdx, colIdx)) {
            return;
        }

        // 2-2. 진행 방향이 좌상단이며 처음 위치로 돌아올 경우 이때까지 먹은 디저트의 개수를 세고 result를 갱신한다.
        if (dr == 3 && rowIdx == startRowIdx && colIdx == startColIdx) {
            int numOfEated = countDesert();
            result = Math.max(result, numOfEated);
            return;
        }

        // 2-3. 먹었던 디저트를 만나면 재귀 종료
        if (isVisited[map[rowIdx][colIdx]]) {
            return;
        }

        // 2-4. 위 조건이 안걸리면 디저트 먹은 것으로 하고 방문 처리
        isVisited[map[rowIdx][colIdx]] = true;

        if (dr != 3) {
            // 2-5. 현재 진행 방향으로 가는 경우로 재귀 들어감
            eatDesert(rowIdx + dRow[dr], colIdx + dCol[dr], dr);
            // 2-6. 방향을 시계방향으로 바꾸고 가는 경우로 재귀 들어감
            eatDesert(rowIdx + dRow[dr + 1], colIdx + dCol[dr + 1], dr + 1);
        } else {
            // 2-5. 현재 진행 방향으로 가는 경우로 재귀 들어감
            eatDesert(rowIdx + dRow[dr], colIdx + dCol[dr], dr);
        }

        // 2-7. 현재 들어온 위치에서 더이상 갈 곳이 없는 경우가 생길 수 있다. 이 경우 현재 위치 자체가 쓸모 없는 위치이므로, 마지막에 먹었던 디저트의 방문처리를 취소한다.
        isVisited[map[rowIdx][colIdx]] = false;
    }

    /**
     * Utils
     */

    static boolean isOut(int rowIdx, int colIdx) {
        return rowIdx < 0 || colIdx < 0 || rowIdx >= sizeOfMap || colIdx >= sizeOfMap;
    }

    static int countDesert() {
        int cnt = 0;
        for (boolean isEated : isVisited) {
            if (isEated) {
                cnt++;
            }
        }
        return cnt;
    }

}
