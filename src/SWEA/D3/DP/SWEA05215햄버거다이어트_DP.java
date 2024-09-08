package SWEA.D3.DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * SWEA 햄버거다이어트 - DP로 풀기
 *
 * 1. 입력
 * 1-1. 테스트 케이스의 개수 T를 입력받는다.
 * 1-2. 재료의 갯수 N과 제한 칼로리 L을 입력받는다.
 * 1-3. N개의 줄에 걸쳐 재료들을 입력받는다.
 *
 * 2. DP
 * 2-1. 냅색 알고리즘으로 해결한다. -> 점화식 NS(n, w) = max(NS(n-1, w - w[n]) + val[n], NS(n-1, w))
 *
 * 3. 출력
 * 3-1. DP 테이블의 가장 우측하단을 출력한다.
 */

public class SWEA05215햄버거다이어트_DP {

    static BufferedReader br;
    static StringTokenizer st;

    public static void main(String[] args) throws IOException {

        // 1. 입력
        br = new BufferedReader(new InputStreamReader(System.in));

        // 1-1. 테스트 케이스의 개수 T를 입력받는다.
        int T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {
            // 1-2. 재료의 갯수 N과 제한 칼로리 L을 입력받는다.
            st = new StringTokenizer(br.readLine().trim());

            int numOfIngre = Integer.parseInt(st.nextToken());
            int calLimit = Integer.parseInt(st.nextToken());

            // 1-3. N개의 줄에 걸쳐 재료들을 입력받는다.
            int[] scoreArr = new int[numOfIngre + 1];
            int[] calArr = new int[numOfIngre + 1];

            for (int ingreIdx = 0; ingreIdx < numOfIngre; ingreIdx++) {
                st = new StringTokenizer(br.readLine().trim());

                scoreArr[ingreIdx + 1] = Integer.parseInt(st.nextToken());
                calArr[ingreIdx + 1] = Integer.parseInt(st.nextToken());
            }

            // 2. DP
            // 2-1. 냅색 알고리즘으로 해결한다. -> 점화식 NS(n, w) = max(NS(n-1, w - w[n]) + val[n], NS(n-1, w))

            int[][] dpTable = new int[numOfIngre + 1][calLimit + 1];

            for (int rowIdx = 1; rowIdx <= numOfIngre; rowIdx++) {
                for (int colIdx = 1; colIdx <= calLimit; colIdx++) {

                    int curIngrePut;

                    if (colIdx - calArr[rowIdx] < 0) {
                        curIngrePut = 0;
                    } else {
                        curIngrePut = dpTable[rowIdx - 1][colIdx - calArr[rowIdx]] + scoreArr[rowIdx];
                    }

                    int curIngreNotPut = dpTable[rowIdx - 1][colIdx];

                    dpTable[rowIdx][colIdx] = Math.max(curIngrePut, curIngreNotPut);
                }
            }

            System.out.println("#" + test_case + " " + dpTable[numOfIngre][calLimit]);
        }

    }
}
