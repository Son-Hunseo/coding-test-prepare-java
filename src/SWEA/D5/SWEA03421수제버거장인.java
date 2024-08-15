package SWEA.D5;

import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * SWEA 3421 수제버거 장인
 *
 * # n x n 배열 (0 인 재료는 없으므로)을 만들어서 서로 불가능한 조합을 1로 체킹 해놓으면 부분집합 만들면서 거를 수 있지 않을까?
 *
 * 1. 테스트 케이스의 개수 T 입력 받는다.
 * 2. 재료의 종류 (1~N)를 알 수있는 N, 서로 양립 불가능한 재료들을 나열할 횟수 M을 입력받는다.
 * 3. M번 양립하면 안되는 조합들을 입력 받는다.
 * 3-1. 이 조합들을 n x n 배열에 양립이 불가능하다는 의미인 1로 체킹한다. (양립가능은 0 (Default))
 * 4. 재귀를 돌면서 양립가능여부 배열을 확인하면서 양립 가능한 녀석들만 부분 집합으로 만든다.
 */

public class SWEA03421수제버거장인 {

    static BufferedReader br;
    static StringTokenizer st;

    static boolean[] checkArr;
    static int[][] caseMap;

    static int numOfIngre;
    static int result;

    public static void main(String[] args) throws IOException {

        br = new BufferedReader(new InputStreamReader(System.in));

        // 1. 테스트 케이스의 개수 T 입력 받는다.
        int T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {

            // 2. 재료의 종류 (1~N)를 알 수있는 N, 서로 양립 불가능한 재료들을 나열할 횟수 M을 입력받는다.
            st = new StringTokenizer(br.readLine().trim());
            numOfIngre = Integer.parseInt(st.nextToken());
            int numOfCase = Integer.parseInt(st.nextToken());

            // 양립 불가능 조건을 매핑할 배열을 생성한다.
            caseMap = new int[numOfIngre + 1][numOfIngre + 1];

            // 3. M번 양립하면 안되는 조합들을 입력 받는다.
            for (int Case = 0; Case < numOfCase; Case++) {
                st = new StringTokenizer(br.readLine().trim());
                int row = Integer.parseInt(st.nextToken());
                int col = Integer.parseInt(st.nextToken());
                // 3-1. 이 조합들을 n+1 x n+1 배열에 양립이 불가능하다는 의미인 1로 체킹한다. (양립가능은 0 (Default))
                caseMap[row][col] = 1;
                caseMap[col][row] = 1;
            }

            result = 0;

            checkArr = new boolean[numOfIngre + 1];
            checkArr[0] = true; // 0이라는 재료는 없기 때문이다.
            // 4. 재귀를 돌면서 양립가능여부 배열을 확인하면서 양립 가능한 녀석들만 부분 집합으로 만든다.
            myCombi(1);

            System.out.println("#" + test_case + " " + result);
        }

    }

    static void myCombi(int curCheckIdx) {

        // 종료 조건 1 - 다 돌경우
        if (curCheckIdx == numOfIngre + 1) {
            result++;
            return;
        }

        boolean compatible = true; // 양립 가능한지의 여부를 나타내는 flag

        // 양립가능한지 여부를 체크
        for (int befIdx = 1; befIdx < curCheckIdx; befIdx++) {

            if (checkArr[befIdx] == true && caseMap[befIdx][curCheckIdx] == 1) {
                compatible = false;
                break;
            }
        }

        // 양립 가능한 경우에는 해당 원소를 포함한 경우와 포함하지 않은 경우 모두 재귀를 들어간다.
        if (compatible) {
            checkArr[curCheckIdx] = true;
            myCombi(curCheckIdx + 1);

            // 원복
            checkArr[curCheckIdx] = false;
            myCombi(curCheckIdx + 1);

        } else { // 양립 불가능한 경우에는 포함하지 않은 경우로만 재귀를 들어간다.

            checkArr[curCheckIdx] = false;
            myCombi(curCheckIdx + 1);
        }


    }

}
