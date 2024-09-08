package SWEA.모의A;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * SWEA1952 수영장
 *
 * 1. 입력
 * 1-1. 테스트 케이스의 개수 T를 입력받는다.
 * 1-2. 이용권의 가격들을 입력받는다. (일권, 1개월권, 3개월권, 1년권)
 * 1-3. 이용 계획을 입력받는다.
 *
 * 2. DP
 * 2-1. 4 x 12의 DP 테이블을 만든다.
 * 2-2. 4번째 행은 1년 이용권 테이블 이므로 모두 1년권의 비용으로 초기화한다.
 * 2-3. 3번째 행은 3개월 이용권 테이블 이므로 1월 ~ 3월 열을 3개월 이용권의 비용으로 초기화한다.
 * 2-4. 1번째 2번째 행의 1월은 일권의 가격, 월권의 가격으로 초기화한다.
 * 2-5. 일권과 월권 - 2번째 열부터는 이전 열의 최솟값 + 현재 열의 해당 값으로 DP 테이블에 넣는다.
 * 2-6. 3개월권 - 4번째 열부터 3칸만큼 전의 열의 최솟값 + 3개월권의 값으로 DP 테이블에 넣는다.
 *
 * 3. 출력
 * 3-1. DP 테이블의 마지막 열 중에서 최솟값을 출력한다.
 */

public class SWEA01952수영장_DP {

    static BufferedReader br;
    static StringTokenizer st;

    static int dayCharge;
    static int monthCharge;
    static int threeMonthCharge;
    static int yearCharge;

    static int[] useInfo;
    static int[][] dpTable;

    public static void main(String[] args) throws IOException {
        // 1. 입력
        br = new BufferedReader(new InputStreamReader(System.in));

        // 1-1. 테스트 케이스의 개수 T를 입력받는다.
        int T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {
            st = new StringTokenizer(br.readLine().trim());

            dayCharge = Integer.parseInt(st.nextToken());
            monthCharge = Integer.parseInt(st.nextToken());
            threeMonthCharge = Integer.parseInt(st.nextToken());
            yearCharge = Integer.parseInt(st.nextToken());

            // 1-3. 이용 계획을 입력받는다.
            useInfo = new int[12];

            st = new StringTokenizer(br.readLine().trim());
            for (int month = 0; month < 12; month++) {
                useInfo[month] = Integer.parseInt(st.nextToken());
            }

            // 2. DP
            // 2-1. 4 x 12의 DP 테이블을 만든다.
            dpTable = new int[4][12];

            // 2-2. 4번째 행은 1년 이용권 테이블 이므로 모두 1년권의 비용으로 초기화한다.
            Arrays.fill(dpTable[3], yearCharge);

            // 2-3. 3번째 행은 3개월 이용권 테이블 이므로 1월 ~ 3월 열을 3개월 이용권의 비용으로 초기화한다.
            for (int month = 0; month < 3; month++) {
                dpTable[2][month] = threeMonthCharge;
            }

            // 2-4. 1번째 2번째 행의 1월은 일권의 가격, 월권의 가격으로 초기화한다.
            dpTable[0][0] = dayCharge * useInfo[0];
            dpTable[1][0] = monthCharge;

            // 2-5. 일권과 월권 - 2번째 열부터는 이전 열의 최솟값 + 현재 열의 해당 값으로 DP 테이블에 넣는다.
            for (int month = 1; month < 12; month++) {
                int minA = Integer.MAX_VALUE;

                for (int rowIdx = 0; rowIdx < 4; rowIdx++) {
                    minA = Math.min(minA, dpTable[rowIdx][month - 1]);
                }

                if (month >= 3) { // 2-6. 3개월권 - 4번째 열부터 3칸만큼 전의 열의 최솟값 + 3개월권의 값으로 DP 테이블에 넣는다.
                    int minB = Integer.MAX_VALUE;
                    for (int rowIdx = 0; rowIdx < 4; rowIdx++) {
                        minB = Math.min(minB, dpTable[rowIdx][month - 3]);
                    }
                    dpTable[2][month] = minB + threeMonthCharge;
                }

                dpTable[0][month] = minA + (dayCharge * useInfo[month]);
                dpTable[1][month] = minA + monthCharge;
            }

            // 3-1. DP 테이블의 마지막 열 중에서 최솟값을 출력한다.
            int result = Integer.MAX_VALUE;

            for (int rowIdx = 0; rowIdx < 4; rowIdx++) {
                result = Math.min(result, dpTable[rowIdx][11]);
            }

            System.out.println("#" + test_case + " " + result);
        }
    }
}