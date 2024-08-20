package SWEA.D3.DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 최장 증가 수열
 * - 주어진 수열에서의 최장 증가 수열을 구한다.
 * - 전형적인 dp 문제 (LIS)
 */

public class SWEA03307최장증가부분수열 {

    static BufferedReader br;
    static StringTokenizer st;

    public static void main(String[] args) throws IOException {

        br = new BufferedReader(new InputStreamReader(System.in));

        // 테스트 케이스 숫자 입력
        int T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {

            // 주어지는 전체 수열의 크기 입력받기
            int N = Integer.parseInt(br.readLine());

            st = new StringTokenizer(br.readLine().trim());
            // 전체 수열 입력받기
            int[] sq = new int[N];
            for (int idx = 0; idx < N; idx++) {
                sq[idx] = Integer.parseInt(st.nextToken());
            }

            // 전체 수열과 사이즈가 같은 빈 배열 선언
            int[] dp = new int[N];
            dp[0] = 1;
            // dp 수행
            // 현재 인덱스에 해당하는 값보다 작은 값을 가지는 이전 값들 중 최대 dp값 + 1을 한다.
            for (int idx = 1; idx < N; idx++) {
                int bfMax = 0;
                for (int bfIdx = 0; bfIdx < idx; bfIdx++) {
                    if (sq[bfIdx] < sq[idx] && dp[bfIdx] > bfMax) {
                        bfMax = dp[bfIdx];
                    }
                }
                dp[idx] = bfMax + 1;
            }

            int max = 0;
            for (int idx = 0; idx < N; idx++) {
                if (max < dp[idx]) {
                    max = dp[idx];
                }
            }

            System.out.printf("#%d %d\n", test_case, max);
        }

    }

}
