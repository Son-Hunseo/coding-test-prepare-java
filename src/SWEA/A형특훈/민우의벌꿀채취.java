package SWEA.A형특훈;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 민우의 벌꿀 채취
 *
 * 1. 입력
 * 1-1. 테스트케이스의 수 T를 입력받는다.
 * 1-2. 벌꿀집의 너비 W 높이 H를 입력받늗나.
 * 1-3. 벌꿀에 들어있는 벌꿀들의 양을 입력받는다.
 *
 * 2. [인접 판단 기준]
 * 2-1. 벌꿀집의 홀수 열(인덱스로 생각하면 짝수 인덱스)는 상, 하, 좌, 우 이렇게 4방향이 인접한다.
 * 2-2. 벌꿀집의 짝수 열(인덱스로 생각하면 홀수 인덱스)는 상, 하, 좌, 우, 좌하, 우하 이렇게 6방향이 인접한다.
 *
 * 3. 완전 탐색
 * 3-0. 벌꿀집과 같은 크기의 최댓값 2차원 배열을 생성한다.
 * 3-1. 깊이 4의 DFS로 탐색하면서 해당 벌꿀 위치에서의 최대 수집 벌꿀 양을 배열에 기록한다.
 * 3-2. 최대 벌꿀을 기록한 2차원 배열을 순회하면서 최댓값을 기록한다.
 *
 * 4. 예외적인 모양들
 * 4-1. dfs로 만들 수 없는 예외적인 벌꿀 채취 모양들을 추가적으로 탐색하여 최대 수집 벌꿀 양을 배열에 기록한다.
 *
 * 5. 출력
 * 5-1. 최대 벌꿀값의 제곱을 출력한다.
 */

public class 민우의벌꿀채취 {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int sizeOfRow;
    static int sizeOfCol;

    static int[][] honeyMap;

    // 2. [인접 판단 기준]
    // 2-1. 벌꿀집의 홀수 열(인덱스로 생각하면 짝수 인덱스)는 상, 하, 좌, 우, 좌상, 우상 이렇게 6방향이 인접한다.
    // 상하좌우
    static int[] dRowForEvenIdx = {-1, 1, 0, 0, -1, -1};
    static int[] dColForEvenIdx = {0, 0, -1, 1, -1, 1};

    // 2-2. 벌꿀집의 짝수 열(인덱스로 생각하면 홀수 인덱스)는 상, 하, 좌, 우, 좌하, 우하 이렇게 6방향이 인접한다.
    // 상하좌우 좌하 우하
    static int[] dRowForOddIdx = {-1, 1, 0, 0, 1, 1};
    static int[] dColForOddIdx = {0, 0, -1, 1, -1, 1};

    static int[][] maxHoneyMap;
    static boolean[][] isVisited;

    static int curRow;
    static int curCol;
    static int curSum;

    public static void main(String[] args) throws IOException {

        // 1. 입력
        br = new BufferedReader(new InputStreamReader(System.in));

        // 1-1. 테스트케이스의 수 T를 입력받는다.
        int T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {
            inputTC();

            // 3-0. 벌꿀집과 같은 크기의 최댓값 2차원 배열을 생성한다.
            maxHoneyMap = new int[sizeOfRow][sizeOfCol];
            isVisited = new boolean[sizeOfRow][sizeOfCol];

            int result = 0;

            // 3-1. 각 벌꿀집의 위치를 순회하면서 깊이 4의 DFS로 탐색하면서 해당 벌꿀 위치에서의 최대 수집 벌꿀 양을 배열에 기록한다.
            for (int rowIdx = 0; rowIdx < sizeOfRow; rowIdx++) {
                for (int colIdx = 0; colIdx < sizeOfCol; colIdx++) {
                    curRow = rowIdx;
                    curCol = colIdx;
                    curSum = 0;
                    getMaxHoney(curRow, curCol, 0);
                    getExtraOrdinaryShapeMaxHoney(rowIdx, colIdx);
                    result = Math.max(result, maxHoneyMap[curRow][curCol]);
                }
            }

            result = (int) Math.pow(result, 2);

            sb = new StringBuilder().append("#").append(test_case).append(" ").append(result);
            System.out.println(sb);

        }

    }

    static void inputTC() throws IOException {

        // 1-2. 벌꿀집의 너비 W 높이 H를 입력받늗나.
        st = new StringTokenizer(br.readLine().trim());

        sizeOfCol = Integer.parseInt(st.nextToken());
        sizeOfRow = Integer.parseInt(st.nextToken());

        // 1-3. 벌꿀에 들어있는 벌꿀들의 양을 입력받는다.
        honeyMap = new int[sizeOfRow][sizeOfCol];

        for (int rowIdx = 0; rowIdx < sizeOfRow; rowIdx++) {
            st = new StringTokenizer(br.readLine().trim());
            for (int colIdx = 0; colIdx < sizeOfCol; colIdx++) {
                honeyMap[rowIdx][colIdx] = Integer.parseInt(st.nextToken());
            }
        }
    }

    // 3. 완전 탐색
    static void getMaxHoney(int rowIdx, int colIdx, int cnt) {

        if (cnt == 4) {
            maxHoneyMap[curRow][curCol] = Math.max(maxHoneyMap[curRow][curCol], curSum);
            return;
        }

        // 3-1. 깊이 4의 DFS로 탐색하면서 해당 벌꿀 위치에서의 최대 수집 벌꿀 양을 배열에 기록한다.
        if (colIdx % 2 == 0) {
            for (int dr = 0; dr < 6; dr++) {

                int nRow = rowIdx + dRowForEvenIdx[dr];
                int nCol = colIdx + dColForEvenIdx[dr];

                if (isOut(nRow, nCol)) {
                    continue;
                }

                if (isVisited[nRow][nCol]) {
                    continue;
                }

                curSum += honeyMap[nRow][nCol];
                isVisited[nRow][nCol] = true;
                getMaxHoney(nRow, nCol, cnt + 1);
                curSum -= honeyMap[nRow][nCol];
                isVisited[nRow][nCol] = false;
            }
        } else {
            for (int dr = 0; dr < 6; dr++) {

                int nRow = rowIdx + dRowForOddIdx[dr];
                int nCol = colIdx + dColForOddIdx[dr];

                if (isOut(nRow, nCol)) {
                    continue;
                }

                if (isVisited[nRow][nCol]) {
                    continue;
                }

                curSum += honeyMap[nRow][nCol];
                isVisited[nRow][nCol] = true;
                getMaxHoney(nRow, nCol, cnt + 1);
                curSum -= honeyMap[nRow][nCol];
                isVisited[nRow][nCol] = false;
            }
        }

    }

    static void getExtraOrdinaryShapeMaxHoney(int rowIdx, int colIdx) {

        // ㅗ 모양
        if (curRow >= 1 && sizeOfCol - colIdx >= 3) {
            int sum = 0;
            sum += honeyMap[rowIdx][colIdx];
            sum += honeyMap[rowIdx][colIdx + 1];
            sum += honeyMap[rowIdx][colIdx + 2];
            sum += honeyMap[rowIdx - 1][colIdx + 1];
            maxHoneyMap[rowIdx][colIdx] = Math.max(maxHoneyMap[rowIdx][colIdx], sum);
        }
        // ㅜ 모양
        if (sizeOfRow - curRow >= 2 && sizeOfCol - colIdx >= 3) {
            int sum = 0;
            sum += honeyMap[rowIdx][colIdx];
            sum += honeyMap[rowIdx][colIdx + 1];
            sum += honeyMap[rowIdx][colIdx + 2];
            sum += honeyMap[rowIdx + 1][colIdx + 1];
            maxHoneyMap[rowIdx][colIdx] = Math.max(maxHoneyMap[rowIdx][colIdx], sum);
        }

        // 인덱스가 짝수일 때의 Y와 홀수일 때의 거꾸로된 Y 모양도 고려 가능하다.
        if (colIdx % 2 == 0) {

            if (curRow >= 2 && sizeOfCol - curCol >= 3) { // 거꾸로된 Y
                int sum = 0;
                sum += honeyMap[rowIdx][colIdx];
                sum += honeyMap[rowIdx - 2][colIdx + 1];
                sum += honeyMap[rowIdx - 1][colIdx + 1];
                sum += honeyMap[rowIdx][colIdx + 2];
                maxHoneyMap[rowIdx][colIdx] = Math.max(maxHoneyMap[rowIdx][colIdx], sum);
            }
        } else {
            if (sizeOfRow - curRow >= 3 && sizeOfCol - curCol >= 3) {
                int sum = 0;
                sum += honeyMap[rowIdx][colIdx];
                sum += honeyMap[rowIdx + 2][colIdx + 1];
                sum += honeyMap[rowIdx + 1][colIdx + 1];
                sum += honeyMap[rowIdx][colIdx + 2];
                maxHoneyMap[rowIdx][colIdx] = Math.max(maxHoneyMap[rowIdx][colIdx], sum);
            }
        }
    }

    static boolean isOut(int rowIdx, int colIdx) {
        return rowIdx < 0 || colIdx < 0 || rowIdx >= sizeOfRow || colIdx >= sizeOfCol;
    }
}
