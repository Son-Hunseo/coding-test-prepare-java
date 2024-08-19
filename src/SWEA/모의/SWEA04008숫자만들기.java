package SWEA.모의;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * SWEA4008 숫자만들기
 *
 * 1. 테스트 케이스의 수 T 입력 받는다.
 * 2. 숫자의 개수 N을 입력받는다.
 * 3. 각 연산자의 개수를 +-* / 순서로 4개 입력받는다.
 * 4. 수식에 사용되는 숫자를 입력받는다.
 * 5. 연산자의 경우의 수를 모두 구한다.
 * 6. 최대와 최소를 뺀다.
 */

public class SWEA04008숫자만들기 {

    static BufferedReader br;
    static StringTokenizer st;

    static int numOfnumbers;
    static int[] calCntArray;
    static int[] numArray;

    static int MAX;
    static int MIN;
    static int result;


    public static void main(String[] args) throws IOException {

        br = new BufferedReader(new InputStreamReader(System.in));

        // 1. 테스트 케이스의 수 T 입력 받는다.
        int T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {
            // 2. 숫자의 개수 N을 입력받는다.
            numOfnumbers = Integer.parseInt(br.readLine());

            calCntArray = new int[4];
            numArray = new int[numOfnumbers];

            // 3. 각 연산자의 개수를 +-*\ 순서로 4개 입력받는다.
            st = new StringTokenizer(br.readLine());
            for (int calIdx = 0; calIdx < 4; calIdx++) {
                calCntArray[calIdx] = Integer.parseInt(st.nextToken());
            }

            // 4. 수식에 사용되는 숫자를 입력받는다.
            st = new StringTokenizer(br.readLine());
            for (int numIdx = 0; numIdx < numOfnumbers; numIdx++) {
                numArray[numIdx] = Integer.parseInt(st.nextToken());
            }

            MAX = -100000001;
            MIN = 100000001;

            // 5. 연산자의 경우의 수를 모두 구한다.
            cal(numArray[0], 1);

            // 6. 최대와 최소를 뺀다.
            System.out.println("#" + test_case + " " + (MAX - MIN));
        }
    }

    // 재귀를 이용하여 각 위치에 각 연산자 중 하나를 넣었을 경우를 재귀적으로 탐색
    static void cal(int curResult, int curNumIdx) {

        if (curNumIdx == numOfnumbers) {
            MAX = Math.max(MAX, curResult);
            MIN = Math.min(MIN, curResult);
            return;
        }

        // + 연산자가 있다면
        if (calCntArray[0] != 0) {
            int befResult = curResult;
            curResult += numArray[curNumIdx];
            calCntArray[0]--;
            cal(curResult, curNumIdx + 1);
            curResult = befResult;
            calCntArray[0]++;
        }

        // - 연산자가 있다면
        if (calCntArray[1] != 0) {
            int befResult = curResult;
            curResult -= numArray[curNumIdx];
            calCntArray[1]--;
            cal(curResult, curNumIdx + 1);
            curResult = befResult;
            calCntArray[1]++;
        }

        // * 연산자가 있다면
        if (calCntArray[2] != 0) {
            int befResult = curResult;
            curResult *= numArray[curNumIdx];
            calCntArray[2]--;
            cal(curResult, curNumIdx + 1);
            curResult = befResult;
            calCntArray[2]++;
        }

        // / 연산자가 있다면
        if (calCntArray[3] != 0) {
            int befResult = curResult;
            curResult /= numArray[curNumIdx];
            calCntArray[3]--;
            cal(curResult, curNumIdx + 1);
            curResult = befResult;
            calCntArray[3]++;
        }

    }

}
