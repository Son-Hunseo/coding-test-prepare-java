package SWEA.A형특훈;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 산악행군
 *
 * 1. 입력
 * 1-1. 테스트 케이스의 수 T를 입력받는다.
 * 1-2. 산의 높이 H와 산의 너비 W를 입력받는다.
 * 1-3. 맵 정보가 주어진다. (막사의 위치 2, 도착 지점 3, 길 1, 아무것도 없음 0)
 *
 * 2. 알고리즘
 * 2-1. 도착지점을 기준으로 출발한다.
 * 2-2. 좌우가 1이라면 이동할 수 있다.
 * 2-3. 상하로는 다른 길이 나올때까지 탐색하며 있다면 이동하며, 이동한 칸수만큼 난이도를 최댓값으로 갱신한다.
 * 2-4. 방문한 곳으로는 다시 갈 수 없으며, 막사 위치에 도착하면 현재 난이도 최댓값과 결과값을 비교하여 더 작은 것으로 갱신한다.
 *
 * 3. 출력
 * 3-1. 막사까지 도착하는 결과의 난이도 중 최소로 갱신된 결과값을 출력한다.
 *
 */

public class 산악행군 {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int sizeOfRow;
    static int sizeOfCol;

    static int[][] map;
    static boolean[][] isVisited;

    static final int[] dRow = {-1, 1, 0, 0};
    static final int[] dCol = {0, 0, -1, 1};

    static final int UP = 0;
    static final int DOWN = 1;
    static final int LEFT = 2;
    static final int RIGHT = 3;

    static int[] startLoca;
    static int[] endLoca;

    static final int START_POINT = 2;
    static final int END_POINT = 3;
    static final int ROAD = 1;
    static final int NOTHING = 0;

    static int curLevel;
    static int result;

    public static void main(String[] args) throws IOException {

        // 1. 입력
        br = new BufferedReader(new InputStreamReader(System.in));

        // 1-1. 테스트 케이스의 수 T를 입력받는다.
        int T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {

            // 1-2. 산의 높이 H와 산의 너비 W를 입력받는다.
            st = new StringTokenizer(br.readLine().trim());

            sizeOfRow = Integer.parseInt(st.nextToken());
            sizeOfCol = Integer.parseInt(st.nextToken());

            // 1-3. 맵 정보가 주어진다. (막사의 위치 2, 도착 지점 3, 길 1, 아무것도 없음 0)
            map = new int[sizeOfRow][sizeOfCol];

            for (int rowIdx = 0; rowIdx < sizeOfRow; rowIdx++) {

                st = new StringTokenizer(br.readLine().trim());

                for (int colIdx = 0; colIdx < sizeOfCol; colIdx++) {
                    int curInfo = Integer.parseInt(st.nextToken());

                    if (curInfo == START_POINT) {
                        startLoca = new int[]{rowIdx, colIdx};
                    } else if (curInfo == END_POINT) {
                        endLoca = new int[]{rowIdx, colIdx};
                    }

                    map[rowIdx][colIdx] = curInfo;
                }
            }

            result = Integer.MAX_VALUE;
            curLevel = 0;
            isVisited = new boolean[sizeOfRow][sizeOfCol];

            // 2. 알고리즘
            // 2-1. 도착지점을 기준으로 출발한다.
            findStart(endLoca[0], endLoca[1]);

            sb = new StringBuilder().append("#").append(test_case).append(" ").append(result);
            System.out.println(sb);

        }

    }

    static void findStart(int curRow, int curCol) {

        if (curLevel >= result) {
            return;
        }

        if (curRow == startLoca[0] && curCol == startLoca[1]) {
            result = Math.min(result, curLevel);
            return;
        }

        for (int dr = 0; dr < 4; dr++) {

            // 2-2. 좌우가 1이라면 이동할 수 있다.
            if (dr == LEFT || dr == RIGHT) {

                int nRow = curRow + dRow[dr];
                int nCol = curCol + dCol[dr];

                if (isOut(nRow, nCol)) {
                    continue;
                }

                if (isVisited[nRow][nCol]) {
                    continue;
                }

                if (map[nRow][nCol] == NOTHING) {
                    continue;
                }

                isVisited[nRow][nCol] = true;
                findStart(nRow, nCol);
                isVisited[nRow][nCol] = false;

            } else if (dr == UP || dr == DOWN) { // 2-3. 상하로는 다른 길이 나올때까지 탐색하며 있다면 이동하며, 이동한 칸수만큼 난이도를 최댓값으로 갱신한다.

                int targetRow = curRow + dRow[dr], targetCol = curCol + dCol[dr];
                int nRow = -1, nCol = -1;

                while (true) {
                    if (isOut(targetRow, targetCol)) {
                        break;
                    }
                    if (map[targetRow][targetCol] != NOTHING) {
                        nRow = targetRow;
                        nCol = targetCol;
                        break;
                    }

                    targetRow = targetRow + dRow[dr];
                    targetCol = targetCol + dCol[dr];
                }

                if (nRow == -1 && nCol == -1) { // 해당 방향에 이동할 수 있는 칸이 없다면 멈춤
                    continue;
                }

                if (isVisited[nRow][nCol]) {
                    continue;
                }

                int temp = curLevel;
                curLevel = Math.max(curLevel, Math.abs(curRow - nRow));
                isVisited[nRow][nCol] = true;

                findStart(nRow, nCol);

                isVisited[nRow][nCol] = false;
                curLevel = temp;

            }
        }

    }

    static boolean isOut(int rowIdx, int colIdx) {
        return rowIdx < 0 || colIdx < 0 || rowIdx >= sizeOfRow || colIdx >= sizeOfCol;
    }
}
