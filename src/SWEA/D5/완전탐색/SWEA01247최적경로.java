package SWEA.D5.완전탐색;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * SWEA 1247 최적 경로
 *
 * - 고객을 방문하는 순서를 나열하는 순열을 구하고, 그 순열대로 방문했을 때의 거리를 구해서 최소를 구한다.
 * - 10! = 약 363만 은 충분히 완전 탐색이 가능한 경우의 수이다.
 *
 * 1. 입력
 * 1-1. 테스트 케이스의 수 T를 입력받는다.
 * 1-2. 고객의 수 N을 입력받는다.
 * 1-3. 회사의 좌표, 집 좌표, 고객의 좌표를 입력받는다. (총 N+2개)
 * 2. 경우의 수 구하기
 * 2-1. 고객을 방문하는 순열을 구한다.
 * 2-2. 순열에 따라 회사 -> 고객 ... -> 집의 거리를 구하면서 최솟값을 갱신한다.
 * 3. 최솟값을 출력한다.
 */

public class SWEA01247최적경로 {

    static BufferedReader br;
    static StringTokenizer st;

    static int[] companyLoca = new int[2];
    static int[] homeLoca = new int[2];
    static int[][] customerLocaInfo;

    // for permutation
    static int[] curArr;
    static boolean[] isVisited;
    static int[] customerNumArr;
    static int numOfCustomer;

    // result
    static int result;

    public static void main(String[] args) throws IOException {
        // 1. 입력
        br = new BufferedReader(new InputStreamReader(System.in));

        // 1-1. 테스트 케이스의 수 T를 입력받는다.
        int T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {
            // 1-2. 고객의 수 N을 입력받는다.
            numOfCustomer = Integer.parseInt(br.readLine());

            customerLocaInfo = new int[numOfCustomer][2];

            // 1-3. 회사의 좌표, 집 좌표, 고객의 좌표를 입력받는다. (총 N+2개)
            st = new StringTokenizer(br.readLine().trim());
            companyLoca[0] = Integer.parseInt(st.nextToken());
            companyLoca[1] = Integer.parseInt(st.nextToken());
            homeLoca[0] = Integer.parseInt(st.nextToken());
            homeLoca[1] = Integer.parseInt(st.nextToken());

            for (int customerIdx = 0; customerIdx < numOfCustomer; customerIdx++) {
                customerLocaInfo[customerIdx][0] = Integer.parseInt(st.nextToken());
                customerLocaInfo[customerIdx][1] = Integer.parseInt(st.nextToken());
            }

            // 2. 경우의 수 구하기
            result = 99999999;
            curArr = new int[numOfCustomer];
            isVisited = new boolean[numOfCustomer];
            // 손님의 순서를 정하기위한 순열
            customerNumArr = new int[numOfCustomer];
            for (int idx = 0; idx < numOfCustomer; idx++) {
                customerNumArr[idx] = idx;
            }
            getPermuResult(0);

            System.out.println("#" + test_case + " " + result);
        }

    }

    // 2-1. 고객을 방문하는 순열을 구한다.
    static void getPermuResult(int curIdx) {
        // 기저 조건
        if (curIdx == numOfCustomer) {

            // 회사 -> 고객 ... -> 집 거리를 구하고 최솟값을 갱신한다.
            int sum = 0;

            // 회사 -> 고객 1
            sum += getDistance(companyLoca, customerLocaInfo[curArr[0]]);

            // 고객 2 -> .... -> 마지막 고객
            for (int customerIdx = 0; customerIdx < numOfCustomer - 1; customerIdx++) {
                sum += getDistance(customerLocaInfo[curArr[customerIdx]], customerLocaInfo[curArr[customerIdx+1]]);
            }

            // 마지막 고객 -> 집
            sum += getDistance(customerLocaInfo[curArr[numOfCustomer - 1]], homeLoca);

            // 최솟값 갱신
            result = Math.min(result, sum);
            return;
        }

        for (int customerIdx = 0; customerIdx < numOfCustomer; customerIdx++) {
            if (isVisited[customerIdx]) {
                continue;
            }
            curArr[curIdx] = customerNumArr[customerIdx];
            isVisited[customerIdx] = true;
            getPermuResult(curIdx + 1);
            isVisited[customerIdx] = false;
        }
    }

    static int getDistance(int[] loca1, int[] loca2) {
        return (Math.abs(loca1[0] - loca2[0]) + Math.abs(loca1[1] - loca2[1]));
    }
}
