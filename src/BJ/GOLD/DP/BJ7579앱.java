package BJ.GOLD.DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * BJ 7579 앱
 *
 * 결국 냅색 문제이다.
 * M바이트를 확보하기 위한 앱 비활성화의 최소 비용을 출력
 * -> 냅색 문제에서 max를 min으로 바꾸면 된다.
 *
 * [문제 풀이]
 * 1. 입력
 * 1-1. 현재 활성화 되어 있는 앱의 수 N과 확보해야하는 메모리의 용량 M을 입력받는다.
 * 1-2. 현재 활성화 되어 있는 앱의 메모리 바이트 수 m_1 .. m_n을 입력받는다.
 * 1-3. 현재 활성화 되어 있는 앱을 종료했을 때의 비용 c_1 .. c_n을 입력받는다.
 *
 * 2. dp
 * 2-1. dp 테이블의 dp[i][j]: 1 ~ i번째 앱까지 고려하면서 j이상의 메모리를 확보할 수 있는 최소 비용
 * 2-2. if i == 0 and j == 0 -> 0
 * 2-3. if i == 0 and j != 0 -> INF
 * 2-4. if i != 0 and j == 0 -> 0
 * 2-5. if i > 0 and j > 0
 * 2-5-1. if m_i >= j -> min(dp[i-1][j], c_i)
 * 2-5-2. if m_i < j -> min(dp[i-1][j-m_i] + c_i, dp[i-1][j])
 */

public class BJ7579앱 {

    static BufferedReader br;
    static StringTokenizer st;

    static int numOfActiveApp;
    static int objectMemory;

    static int[] appMemoryArr;
    static int[] appCostArr;

    static int[][] dpTable;

    public static void main(String[] args) throws IOException {

        // 1. 입력
        br = new BufferedReader(new InputStreamReader(System.in));

        // 1-1. 현재 활성화 되어 있는 앱의 수 N과 확보해야하는 메모리의 용량 M을 입력받는다.
        st = new StringTokenizer(br.readLine().trim());
        numOfActiveApp = Integer.parseInt(st.nextToken());
        objectMemory = Integer.parseInt(st.nextToken());

        appMemoryArr = new int[numOfActiveApp + 1];
        appCostArr = new int[numOfActiveApp + 1];

        // 1-2. 현재 활성화 되어 있는 앱의 메모리 바이트 수 m_1 .. m_n을 입력받는다.
        st = new StringTokenizer(br.readLine().trim());

        for (int appIdx = 1; appIdx <= numOfActiveApp; appIdx++) {
            appMemoryArr[appIdx] = Integer.parseInt(st.nextToken());
        }

        // 1-3. 현재 활성화 되어 있는 앱을 종료했을 때의 비용 c_1 .. c_n을 입력받는다.
        st = new StringTokenizer(br.readLine().trim());

        for (int appIdx = 1; appIdx <= numOfActiveApp; appIdx++) {
            appCostArr[appIdx] = Integer.parseInt(st.nextToken());
        }

        // 2. dp
        dpTable = new int[numOfActiveApp + 1][objectMemory + 1];

        // 2-3. if i == 0 and j != 0 -> INF
        // INF 쓰니까 overflow 생겨서 모든 비용 합으로 대체
        int costSum = 0;
        for (int appIdx = 1; appIdx <= numOfActiveApp; appIdx++) {
            costSum += appCostArr[appIdx];
        }

        for (int memory = 1; memory <= objectMemory; memory++) {
            dpTable[0][memory] = costSum;
        }

        // 2-5. if i > 0 and j > 0
        for (int appIdx = 1; appIdx <= numOfActiveApp; appIdx++) {
            for (int memory = 1; memory <= objectMemory; memory++) {
                // 2-5-1. if m_i >= j -> min(dp[i-1][j], c_i)
                if (appMemoryArr[appIdx] >= memory) {
                    dpTable[appIdx][memory] = Math.min(dpTable[appIdx - 1][memory], appCostArr[appIdx]);
                }
                // 2-5-2. if m_i < j -> min(dp[i-1][j-m_i] + c_i, dp[i-1][j])
                else {
                    dpTable[appIdx][memory] = Math.min(dpTable[appIdx - 1][memory - appMemoryArr[appIdx]] + appCostArr[appIdx], dpTable[appIdx - 1][memory]);
                }

            }
        }

        System.out.println(dpTable[numOfActiveApp][objectMemory]);
    }

}
