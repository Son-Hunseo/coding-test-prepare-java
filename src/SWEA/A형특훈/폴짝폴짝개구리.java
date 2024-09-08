package SWEA.A형특훈;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 폴짝 폴짝 개구리
 *
 * 1. 입력
 * 1-1. 테스트케이스의 수 T를 입력받는다.
 * 1-2. 맵의 크기 N을 입력받는다.
 * 1-3. 맵의 정보를 입력받는다.
 * 1-3-1. 개구리가 있는 칸은 2로 주어진다. (개구리는 1마리이다)
 *
 * 2. 조합을 이용한 완전탑색
 * 2-0. 먹힌 파리를 기록하기 위한 맵과 같은 사이즈의 eatedArr 2차원배열을 생성한다.
 * 2-1. 개구리가 이동할 수 있는 모든 경우의 수를 조합을 통해 구한다.
 * 2-1-1. 개구리 위치를 기준으로 상하좌우를 한칸씩 탐색한다.
 * 2-1-2. 파리가 존재하면 그 다음칸으로 점프할 수 있지만, 2마리 이상 뛰어넘을 수는 없다.
 * 2-1-3. 해당 위치에 파리가 존재하면 해당 파리 eatedArr에 true를 기록한다.
 * 2-2. 개구리가 3회 이동하면 멈추는 기저조건을 건다.
 *
 * 3. 출력
 * 3-1. eatedFlyArr 배열의 true 개수를 센다.
 *
 */

public class 폴짝폴짝개구리 {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int sizeOfMap;
    static int[][] map;

    static boolean[][] eatedArr;
    static int[] frogLoca;

    static final int NOTHING = 0;
    static final int FLY = 1;
    static final int FROG = 2;

    // 상하좌우
    static int[] dRow = {-1, 1, 0, 0};
    static int[] dCol = {0, 0, -1, 1};


    public static void main(String[] args) throws IOException {

        // 1. 입력
        br = new BufferedReader(new InputStreamReader(System.in));

        // 1-1. 테스트케이스의 수 T를 입력받는다.
        int T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {
            // 1-2. 맵의 크기 N을 입력받는다.
            sizeOfMap = Integer.parseInt(br.readLine());

            // 1-3. 맵의 정보를 입력받는다.
            map = new int[sizeOfMap][sizeOfMap];

            for (int rowIdx = 0; rowIdx < sizeOfMap; rowIdx++) {
                st = new StringTokenizer(br.readLine().trim());
                for (int colIdx = 0; colIdx < sizeOfMap; colIdx++) {
                    int curInfo = Integer.parseInt(st.nextToken());
                    map[rowIdx][colIdx] = curInfo;
                    if (curInfo == FROG) {
                        // 1-3-1. 개구리가 있는 칸은 2로 주어진다. (개구리는 1마리이다)
                        frogLoca = new int[]{rowIdx, colIdx};
                    }
                }
            }

            // 2. 조합을 이용한 완전탑색
            // 2-0. 먹힌 파리를 기록하기 위한 맵과 같은 사이즈의 eatedArr 2차원배열을 생성한다.
            eatedArr = new boolean[sizeOfMap][sizeOfMap];

            // 2-1. 개구리가 이동할 수 있는 모든 경우의 수를 조합을 통해 구한다.
            jumpFrog(0);

            int result = 0;

            for (int rowIdx = 0; rowIdx < sizeOfMap; rowIdx++) {
                for (int colIdx = 0; colIdx < sizeOfMap; colIdx++) {
                    if (eatedArr[rowIdx][colIdx]) {
                        result++;
                    }
                }
            }

            sb = new StringBuilder().append("#").append(test_case).append(" ").append(result);
            System.out.println(sb);
        }

    }

    static void jumpFrog(int moveCnt) {

        // 2-2. 개구리가 3회 이동하면 멈추는 기저조건을 건다.
        if (moveCnt == 3) {
            return;
        }

        for (int dr = 0; dr < 4; dr++) {

            int curRow = frogLoca[0];
            int curCol = frogLoca[1];

            int jumpableFlyRow = -1;
            int jumpableFlyCol = -1;

            while (true) { // 해당방향에서 넘을 수 있는 파리를 찾자

                int nRow = curRow + dRow[dr];
                int nCol = curCol + dCol[dr];

                if (isOut(nRow, nCol)) {
                    break;
                }

                if (map[nRow][nCol] == FLY) {
                    jumpableFlyRow = nRow;
                    jumpableFlyCol = nCol;
                    break;
                }

                curRow = nRow;
                curCol = nCol;

            }

            if (jumpableFlyRow == -1 && jumpableFlyCol == -1) { // 넘을 수 있는 파리가 없을 경우
                continue;
            }

            if (dr == 0) { // 상
                for (int targetRow = jumpableFlyRow - 1; targetRow >= 0; targetRow--) {
                    map[frogLoca[0]][frogLoca[1]] = NOTHING;
                    int befRow = frogLoca[0];

                    frogLoca[0] = targetRow;
                    boolean isTargetFly = false;

                    if (map[frogLoca[0]][frogLoca[1]] == FLY) {
                        eatedArr[frogLoca[0]][frogLoca[1]] = true;
                        isTargetFly = true;
                    }

                    map[frogLoca[0]][frogLoca[1]] = FROG;

                    jumpFrog(moveCnt + 1);

                    // 원복
                    if (isTargetFly) {
                        map[frogLoca[0]][frogLoca[1]] = FLY;
                    } else {
                        map[frogLoca[0]][frogLoca[1]] = NOTHING;
                    }
                    frogLoca[0] = befRow;
                    map[frogLoca[0]][frogLoca[1]] = FROG;

                    // 2마리를 뛰어넘을 수는 없다.
                    if (isTargetFly) {
                        break;
                    }
                }
            } else if (dr == 1) { // 하
                for (int targetRow = jumpableFlyRow + 1; targetRow < sizeOfMap; targetRow++) {
                    map[frogLoca[0]][frogLoca[1]] = NOTHING;
                    int befRow = frogLoca[0];

                    frogLoca[0] = targetRow;
                    boolean isTargetFly = false;

                    if (map[frogLoca[0]][frogLoca[1]] == FLY) {
                        eatedArr[frogLoca[0]][frogLoca[1]] = true;
                        isTargetFly = true;
                    }

                    map[frogLoca[0]][frogLoca[1]] = FROG;

                    jumpFrog(moveCnt + 1);

                    // 원복
                    if (isTargetFly) {
                        map[frogLoca[0]][frogLoca[1]] = FLY;
                    } else {
                        map[frogLoca[0]][frogLoca[1]] = NOTHING;
                    }
                    frogLoca[0] = befRow;
                    map[frogLoca[0]][frogLoca[1]] = FROG;

                    // 2마리를 뛰어넘을 수는 없다.
                    if (isTargetFly) {
                        break;
                    }
                }
            } else if (dr == 2) { // 좌
                for (int targetCol = jumpableFlyCol - 1; targetCol >= 0; targetCol--) {
                    map[frogLoca[0]][frogLoca[1]] = NOTHING;
                    int befCol = frogLoca[1];

                    frogLoca[1] = targetCol;
                    boolean isTargetFly = false;

                    if (map[frogLoca[0]][frogLoca[1]] == FLY) {
                        eatedArr[frogLoca[0]][frogLoca[1]] = true;
                        isTargetFly = true;
                    }

                    map[frogLoca[0]][frogLoca[1]] = FROG;

                    jumpFrog(moveCnt + 1);

                    // 원복
                    if (isTargetFly) {
                        map[frogLoca[0]][frogLoca[1]] = FLY;
                    } else {
                        map[frogLoca[0]][frogLoca[1]] = NOTHING;
                    }
                    frogLoca[1] = befCol;
                    map[frogLoca[0]][frogLoca[1]] = FROG;

                    // 2마리를 뛰어넘을 수는 없다.
                    if (isTargetFly) {
                        break;
                    }
                }
            } else { // 우
                for (int targetCol = jumpableFlyCol + 1; targetCol < sizeOfMap; targetCol++) {
                    map[frogLoca[0]][frogLoca[1]] = NOTHING;
                    int befCol = frogLoca[1];

                    frogLoca[1] = targetCol;
                    boolean isTargetFly = false;

                    if (map[frogLoca[0]][frogLoca[1]] == FLY) {
                        eatedArr[frogLoca[0]][frogLoca[1]] = true;
                        isTargetFly = true;
                    }

                    map[frogLoca[0]][frogLoca[1]] = FROG;

                    jumpFrog(moveCnt + 1);

                    // 원복
                    if (isTargetFly) {
                        map[frogLoca[0]][frogLoca[1]] = FLY;
                    } else {
                        map[frogLoca[0]][frogLoca[1]] = NOTHING;
                    }
                    frogLoca[1] = befCol;
                    map[frogLoca[0]][frogLoca[1]] = FROG;

                    // 2마리를 뛰어넘을 수는 없다.
                    if (isTargetFly) {
                        break;
                    }
                }
            }
        }

    }

    static boolean isOut(int rowIdx, int colIdx) {
        return rowIdx < 0 || colIdx < 0 || rowIdx >= sizeOfMap || colIdx >= sizeOfMap;
    }

}
