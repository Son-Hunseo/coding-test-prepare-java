package SWEA.A형특훈;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 로봇청소기
 *
 * - 현재 로봇의 위치와 현재 진행 방향을 기준으로 좌상단, 우상단, 좌하단, 우하단 / 오른쪽, 아래, 왼쪽, 위쪽 -> 총 16가지의 경우의 수를 처리해주면된다.
 * - 위 처럼 풀 수 있게 하려고 외곽에 먼지가 없는 조건과 먼지가 청소되었을 경우 같은 행, 열에는 다른 먼지가 나타나지 않게한 것이다.
 *
 * 1. 입력
 * 1-1. 테스트케이스의 수 T를 입력받는다.
 * 1-2. 맵의 크기 N을 입력받는다.
 * 1-3. 청소하는 먼지 칸을 입력받는다.
 * 1-3-2. 입력 받으면서 각 먼지가 있는 칸을 2차원 배열에 저장한다.
 *
 * 2. 16가지 경우의 수로 나누어서 푼다.
 * - 주의, 각 먼지를 청소할때 더하는 회전 수 만큼 현재 바라보는 방향도 업데이트 시켜줘야한다.
 */

public class 로봇청소기 {

    static BufferedReader br;
    static StringTokenizer st;

    static int[][] dustArr;

    // 방향은 우하좌상 순서대로 0123
    static int curRow, curCol, curDr;

    public static void main(String[] args) throws IOException {
        // 1. 입력
        br = new BufferedReader(new InputStreamReader(System.in));

        // 1-1. 테스트케이스의 수 T를 입력받는다.
        int T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {
            // 1-2. 맵의 크기 N을 입력받는다.
            int sizeOfMap = Integer.parseInt(br.readLine());

            dustArr = new int[11][2]; // 먼지는 최대 10개이므로

            for (int rowIdx = 0; rowIdx < 11; rowIdx++) { // -1로 초기화
                dustArr[rowIdx][0] = -1;
                dustArr[rowIdx][1] = -1;
            }

            // 1-3. 청소하는 먼지 칸을 입력받는다.
            // 1-3-2. 입력 받으면서 각 먼지가 있는 칸을 리스트에 저장한다.
            for (int rowIdx = 0; rowIdx < sizeOfMap; rowIdx++) {
                st = new StringTokenizer(br.readLine().trim());
                for (int colIdx = 0; colIdx < sizeOfMap; colIdx++) {
                    int curLocaInfo = Integer.parseInt(st.nextToken());
                    if (curLocaInfo != 0) {
                        dustArr[curLocaInfo][0] = rowIdx;
                        dustArr[curLocaInfo][1] = colIdx;
                    }
                }
            }

            int dustNum = 1;
            int turnCnt = 0;
            curRow = 0;
            curCol = 0;
            curDr = 0;

            while (true) {
                if (dustNum == 11 || dustArr[dustNum][0] == -1) {
                    break;
                }

                int dustRow = dustArr[dustNum][0];
                int dustCol = dustArr[dustNum][1];

                int dustDr = getDustDr(curRow, curCol, dustRow, dustCol);
                int dustTurnCnt = getTurnCnt(dustDr, curDr);

                // 이동
                curRow = dustRow;
                curCol = dustCol;
                curDr = (curDr + dustTurnCnt) % 4;

                turnCnt += dustTurnCnt;

                dustNum++;
            }

            System.out.println("#" + test_case + " " + turnCnt);
        }
    }

    /**
     * Utils
     */

    // 현재 위치 기준으로 좌상단, 우상단, 좌하단, 우하단 순서로 0123
    static int getDustDr(int curRow, int curCol, int dustRow, int dustCol) {
        if (dustRow < curRow && dustCol < curCol) { // 좌상단
            return 0;
        }
        if (dustRow < curRow && dustCol > curCol) { // 우상단
            return 1;
        }
        if (dustRow > curRow && dustCol < curCol) { // 좌하단
            return 2;
        }
        return 3;
    }

    // 먼지의 위치와 현재 진행 방향에 따른 회전 횟수 결정
    static int getTurnCnt(int dustDr, int curDr) {
        if (dustDr == 0) { // 먼지 위치가 좌상단
            if (curDr == 0) {
                return 3;
            }
            if (curDr == 1) {
                return 2;
            }
            if (curDr == 2) {
                return 1;
            }
            return 3;
        }

        if (dustDr == 1) { // 먼지 위치가 우상단
            if (curDr == 0) {
                return 3;
            }
            if (curDr == 1) {
                return 3;
            }
            if (curDr == 2) {
                return 2;
            }
            return 1;
        }

        if (dustDr == 2) { // 먼지 위치가 좌하단
            if (curDr == 0) {
                return 2;
            }
            if (curDr == 1) {
                return 1;
            }
            if (curDr == 2) {
                return 3;
            }
            return 3;
        }

        // 먼지 위치 우하단
        if (curDr == 0) {
            return 1;
        }
        if (curDr == 1) {
            return 3;
        }
        if (curDr == 2) {
            return 3;
        }
        return 2;
    }
}
