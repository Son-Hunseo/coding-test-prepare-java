package BJ.SILVER;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * BJ 1149 RGB거리
 *
 * 1. 입력
 * 1-1. 집의 수 N을 입력받는다.
 * 1-2. 이후 N줄에 걸쳐 N번째 줄에 집을 빨강, 초록, 파랑으로 칠하는 비용이 주어진다.
 *
 * 2. DP
 * 2-1. N x 3 DP 테이블을 만든다.
 * 2-1. DP 테이블의 첫 줄에는 첫번째 집을 빨강, 초록, 파랑으로 칠하는 비용을 넣는다.
 * 2-2. DP 테이블의 두번째 줄부터는 첫 열에는 (비용의 N행 0열의 요소 + min(DP테이블의 N-1행 1열의 요소, N-1행 2열의 요소))를 저장한다. (두번째 세번째 칸도 마찬가지)
 *
 * 3. 출력
 * 3-1. DP 테이블의 마지막 행 중에서 가장 작은 요소를 출력한다.
 */

public class BJ01149RGB거리 {

    static BufferedReader br;
    static StringTokenizer st;


    public static void main(String[] args) throws IOException {
        // 1. 입력
        br = new BufferedReader(new InputStreamReader(System.in));

        // 1-1. 집의 수 N을 입력받는다.
        int numOfHouse = Integer.parseInt(br.readLine());

        // 1-2.이후 N줄에 걸쳐 N번째 줄에 집을 빨강, 초록, 파랑으로 칠하는 비용이 주어진다.
        int[][] houseInfo = new int[numOfHouse][3];

        for (int rowIdx = 0; rowIdx < numOfHouse; rowIdx++) {
            st = new StringTokenizer(br.readLine().trim());

            houseInfo[rowIdx][0] = Integer.parseInt(st.nextToken());
            houseInfo[rowIdx][1] = Integer.parseInt(st.nextToken());
            houseInfo[rowIdx][2] = Integer.parseInt(st.nextToken());
        }

        // 2. DP
        // 2-1. N x 3 DP 테이블을 만든다.
        int[][] dpTable = new int[numOfHouse][3];

        // 2-1. DP 테이블의 첫 줄에는 첫번째 집을 빨강, 초록, 파랑으로 칠하는 비용을 넣는다.
        dpTable[0][0] = houseInfo[0][0];
        dpTable[0][1] = houseInfo[0][1];
        dpTable[0][2] = houseInfo[0][2];

        // 2-2. DP 테이블의 두번째 줄부터는 첫 열에는 (비용의 N행 0열의 요소 + min(DP테이블의 N-1행 1열의 요소, N-1행 2열의 요소))를 저장한다. (두번째 세번째 칸도 마찬가지)
        for (int rowIdx = 1; rowIdx < numOfHouse; rowIdx++) {
            dpTable[rowIdx][0] = houseInfo[rowIdx][0] + Math.min(dpTable[rowIdx - 1][1], dpTable[rowIdx - 1][2]);
            dpTable[rowIdx][1] = houseInfo[rowIdx][1] + Math.min(dpTable[rowIdx - 1][0], dpTable[rowIdx - 1][2]);
            dpTable[rowIdx][2] = houseInfo[rowIdx][2] + Math.min(dpTable[rowIdx - 1][0], dpTable[rowIdx - 1][1]);
        }

        // 3-1. DP 테이블의 마지막 행 중에서 가장 작은 요소를 출력한다.
        int min = Integer.MAX_VALUE;

        for (int element : dpTable[numOfHouse-1]) {
            min = Math.min(min, element);
        }

        System.out.println(min);
    }

}
