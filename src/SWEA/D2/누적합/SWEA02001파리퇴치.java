package SWEA.D2.누적합;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * SWEA 2001 파리 퇴치
 * # 과제 조건: 누적합으로 풀기
 *
 * 1. 테스트 케이스의 수 T를 입력받는다.
 * 2. 전체 배열의 크기 N과 파리채의 크기 M을 입력받는다.
 * 3. N 줄에 걸쳐 배열을 입력 받는다.
 * 3-1. 입력을 받으면서 배열에 누적합을 저장한다.
 * 4. 파리채가 (row, col)를 좌측 상단의 끝 좌표로하고 때린다고 했을 때,
 *    (row, col + M - 1)의 누적합 - (row, col - 1)의 누적합 +
 *    (row + 1, col + M - 1)의 누적합 - (row + 1, col - 1)의 누적합 +
 *    ...
 *    (row + M -1, col + M -1)의 누적합 - (row + M -1, col -1)의 누적합
 *    의 값이 죽일 수 있는 파리의 개수가 된다.
 *    + row, col는 0부터 (k + M -1) 이 N - 1 이하인 범위 까지 이다. -> row, col <= N - M
 *
 * # 하나의 위치에서 죽일 수 있는 파리의 수를 구하는데 연산이 O(M^2)에서 O(M)으로 줄어들었다.
 * # 이를 (N-M) 만큼 구해야한다.
 * # 누적합을 사용하지 않고 계산해야할 경우 -> O((N-M)*M^2) -> O(N*M^2)
 * # 누적합을 사용한 경우              -> O((N-M)*(M) -> O(N*M)
 *
 */

public class SWEA02001파리퇴치 {

    static BufferedReader br;
    static StringTokenizer st;
    static int[][] flyMap;

    public static void main(String[] args) throws IOException {

        br = new BufferedReader(new InputStreamReader(System.in));

        // 1. 테스트 케이스의 수 T를 입력받는다.
        int T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {

            st = new StringTokenizer(br.readLine().trim());
            // 2. 전체 배열의 크기 N과 파리채의 크기 M을 입력받는다.
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());

            flyMap = new int[N][N];

            // 3. N 줄에 걸쳐 배열을 입력 받는다.
            // 3-1. 입력을 받으면서 배열에 누적합을 저장한다.
            for (int row = 0; row < N; row++) {
                st = new StringTokenizer(br.readLine().trim());
                for (int col = 0; col < N; col++) {
                    int num = Integer.parseInt(st.nextToken());
                    int befSum;

                    // row 가 0이 아닌데, col이 0일 경우 이전 row의 마지막 col의 누적합에서 더한다.
                    if (row != 0 && col == 0) {
                        befSum = flyMap[row-1][N-1];
                    } else if (row == 0 && col == 0) { // 맨 처음 요소는 이전 누적합이 없다.
                        befSum = 0;
                    } else {
                        befSum = flyMap[row][col-1];
                    }

                    flyMap[row][col] = befSum + num;
                }

            }

            /*
             *  4. 파리채가 (row, col)를 좌측 상단의 끝 좌표로하고 때린다고 했을 때,
             *     (row, col + M - 1)의 누적합 - (row, col - 1)의 누적합 +
             *     (row + 1, col + M - 1)의 누적합 - (row + 1, col - 1)의 누적합 +
             *     ...
             *     (row + M -1, col + M -1)의 누적합 - (row + M -1, col -1)의 누적합
             *     의 값이 죽일 수 있는 파리의 개수가 된다.
             *     + row, col는 0부터 (k + M -1) 이 N - 1 이하인 범위 까지 이다. -> row, col <= N - M
             */

            int maxFly = 0;
            int curFly;

            // attackRow, attackCol: 파리채가 때리는 기준 좌측 상단 좌표
            // row: 파리채의 row

            for (int attackRow = 0; attackRow <= N - M; attackRow++) {

                for (int attackCol = 0; attackCol <= N - M; attackCol++) {

                    curFly = 0;

                    for (int row = attackRow; row < attackRow + M; row++) {

                        // row 가 0이 아닌데, attackCol이 0일 경우 이전 row의 마지막 attackCol의 누적합사용.
                        if (row != 0 && attackCol == 0) {
                            curFly += (flyMap[row][attackCol + M - 1] - flyMap[row - 1][N - 1]);
                        } else if (row == 0 && attackCol == 0) { // 맨 처음 요소는 이전 누적합이 없다.
                            curFly += flyMap[row][attackCol + M -1];
                        } else {
                            curFly += (flyMap[row][attackCol + M -1] - flyMap[row][attackCol -1]);
                        }
                    }

                    if (curFly > maxFly) {
                        maxFly = curFly;
                    }

                }


            }

            System.out.println("#" + test_case + " " + maxFly);

        }

    }
}
