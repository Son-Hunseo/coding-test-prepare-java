package SWEA.모의A;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * SWEA 2112 보호 필름
 *
 * 각 Row(막)에 가할 수 있는 행동의 경우의 수는 아무것도 하지 않음, A약품, B약품 으로 3가지이다.
 * 최악의 경우의 수 3^13 = 약 160만 & 각 경우의 수 일 때, 합격 여부를 테스트 하는 경우는 최악의 경우 13 x 20 = 260 이다.
 * 따라서 160만 x 260 = 약 4억이다. -> 이렇게 모든 완전탐색을하면 시간 복잡도 조건을 맞추지 못한다.
 * 하지만, 우리가 구하는 것은 "최소값"이다. 따라서 각 경우의 수에따른 재귀를 돌다가 검사에 통과하게 된다면, 해당 경우의 수에서는 더이상 재귀를 들어가지 않아도 된다.
 * 이러면 얼추 맞출 수 있지 않을까?
 *
 * 1. 테스트 케이스의 개수 T를 입력받는다.
 * 2. 막의 수 (row의 수) D, 셀의 수 (col의 수) W, 합격 기준 (몇개 연속하여 같은 특성을 가져야 통과하는가) K를 입력받는다.
 * 3. 셀의 정보들을 입력받는다.
 * 4. 각 row의 상태 (약품X, A약품, B약품)에 따라 재귀 분기점을 나누어서 재귀적으로 탐색을한다.
 * 5. 각 재귀 단계마다 성능 검사를 하고 만약 성능 검사에 통과하게된다면, 최솟값을 갱신하고 해당 방향으로는 더이상 재귀를 들어가지 않는다.
 * 6. 성능검사를 통과할 수 있는 약품의 최소 투입 횟수를 출력한다.
 */

public class SWEA02112보호필름 {

    // 입력
    static BufferedReader br;
    static StringTokenizer st;

    // 테스트 케이스 조건
    static int T, rowNum, colNum, testCondition;

    // 각 막의 상태 맵
    static int[][] cellMap;

    // 재귀에 사용할 상태값, 아무것도 사용 안함 -1, 약품A 사용 0, 약품B 사용 1
    static int[] statusArr;

    // 결과값
    static int result;

    public static void main(String[] args) throws IOException {

        br = new BufferedReader(new InputStreamReader(System.in));
        // 1. 테스트 케이스의 개수 T를 입력받는다.
        T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {
            // 2. 막의 수 (row의 수) D, 셀의 수 (col의 수) W, 합격 기준 (몇개 연속하여 같은 특성을 가져야 통과하는가) K를 입력받는다.
            st = new StringTokenizer(br.readLine().trim());
            rowNum = Integer.parseInt(st.nextToken());
            colNum = Integer.parseInt(st.nextToken());
            testCondition = Integer.parseInt(st.nextToken());

            cellMap = new int[rowNum][colNum];

            for (int rowIdx = 0; rowIdx < rowNum; rowIdx++) {
                // 3. 셀의 정보들을 입력받는다.
                st = new StringTokenizer(br.readLine().trim());
                for (int colIdx = 0; colIdx < colNum; colIdx++) {
                    cellMap[rowIdx][colIdx] = Integer.parseInt(st.nextToken());
                }
            }

            result = 99999999;

            statusArr = new int[rowNum];
            // -1로 초기화
            for (int statusIdx = 0; statusIdx < rowNum; statusIdx++) {
                statusArr[statusIdx] = -1;
            }

            search(0);

            if (result == 99999999) {
                result = 0;
            }

            System.out.println("#" + test_case + " " + result);

        }

    }

    // 재귀적으로 탐색
    static void search(int curRow) {

        // 성능 검사를 통과한다면
        if (getTestResult()) {
            // 약품 사용 개수를 체크하고
            int cnt = 0;
            for (int status : statusArr) {
                if (status != -1) {
                    cnt++;
                }
            }


            // 최소 결과값 갱신 이후 종료
            result = Math.min(result, cnt);
            return;

        }

        // 종료 조건
        if (curRow == rowNum) {
            return;
        }

        // 현재 row 원 상태 기록
        int[] rowBefStatus = new int[colNum];
        for (int colIdx = 0; colIdx < colNum; colIdx++) {
            rowBefStatus[colIdx] = cellMap[curRow][colIdx];
        }

        // 성능 검사를 통과하지 못했다면

        // 1. 아무것도 하지 않고 재귀
        search(curRow + 1);

        // 2. A 약품을 사용하고 재귀
        statusArr[curRow] = 0;
        for (int colIdx = 0; colIdx < colNum; colIdx++) {
            cellMap[curRow][colIdx] = 0;
        }
        search(curRow + 1);
        // 원복
        statusArr[curRow] = -1;
        for (int colIdx = 0; colIdx < colNum; colIdx++) {
            cellMap[curRow][colIdx] = rowBefStatus[colIdx];
        }

        // 3. B 약품을 사용하고 재귀
        statusArr[curRow] = 1;
        for (int colIdx = 0; colIdx < colNum; colIdx++) {
            cellMap[curRow][colIdx] = 1;
        }
        search(curRow + 1);
        // 원복
        statusArr[curRow] = -1;
        for (int colIdx = 0; colIdx < colNum; colIdx++) {
            cellMap[curRow][colIdx] = rowBefStatus[colIdx];
        }
    }


    // 검사를 통과했는지 확인하는 메서드
    static boolean getTestResult() {

        boolean result = true;
        int befNum = -1;

        for (int colIdx = 0; colIdx < colNum; colIdx++) {
            int conditionCnt = 0;
            for (int rowIdx = 0; rowIdx < rowNum; rowIdx++) {
                // 이전셀의 상태와 같다면
                if (cellMap[rowIdx][colIdx] == befNum) {
                    conditionCnt++;
                    befNum = cellMap[rowIdx][colIdx];
                } else { // 같지 않다면
                    conditionCnt = 1;
                    befNum = cellMap[rowIdx][colIdx];
                }
                // 조건을 만족한다면 즉시 종료
                if (conditionCnt >= testCondition) {
                    break;
                }
            }
            // 하나의 열이라도 조건을 만족하지 않는다면 false 리턴
            if (conditionCnt < testCondition) {
                return false;
            }
        }

        return result;
    }

}