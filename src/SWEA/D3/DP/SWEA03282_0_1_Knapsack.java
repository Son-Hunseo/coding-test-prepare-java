package SWEA.D3.DP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * SWEA 3282 0/1 Knaksack
 *
 *
 *
 * [알고리즘]
 * 1. 입력
 * 1-1. 테스트 케이스의 수 T를 입력받음
 * 1-2. 물건의 개수 N과 가방의 부피 K를 입력받음
 * 1-3. N줄에 걸쳐서 i번의 물건 정보를 나타내는 부피 V_i와 가치 C_i가 주어진다.
 *
 * 2.냅색
 * - 세로축 (i) : 1번 물건부터 i번 물건까지 넣을 수 있다는 뜻
 * - 가로축 (v) : 부피
 * 2-1. 각 칸에 들어가는 수치 (C(i, v))
 * 2-2. i = 0 or v = 0 : C(i, v) = 0
 * 2-3. i > 0 and v < v_i : C(i, v) = C(i-1, v)
 * 2-4. i > 0 and v >= v_i : C(i, v) = MAX(c_i + C(i-1, v-v_i), C(i-1, v))
 *
 *
 */

public class SWEA03282_0_1_Knapsack {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;
    static int numOfStuff;
    static int volumeOfBag;
    static Stuff[] stuffInfoArr;

    static int[][] dpTable;

    public static void main(String[] args) throws IOException {

        // 1. 입력
        br = new BufferedReader(new InputStreamReader(System.in));

        // 1-1. 테스트 케이스의 수 T를 입력받음
        int T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {

            // 1-2. 물건의 개수 N과 가방의 부피 K를 입력받음
            st = new StringTokenizer(br.readLine().trim());

            numOfStuff = Integer.parseInt(st.nextToken());
            volumeOfBag = Integer.parseInt(st.nextToken());

            stuffInfoArr = new Stuff[numOfStuff + 1];

            // 1-3. N줄에 걸쳐서 i번의 물건 정보를 나타내는 부피 V_i와 가치 C_i가 주어진다.
            // 0번 물건은 없음
            for (int stuffNum = 1; stuffNum <= numOfStuff; stuffNum++) {

                st = new StringTokenizer(br.readLine().trim());

                int curVolume = Integer.parseInt(st.nextToken());
                int curCost = Integer.parseInt(st.nextToken());

                stuffInfoArr[stuffNum] = new Stuff(curVolume, curCost);
            }

            // 2.냅색
            // dp 테이블에 0도 있으므로 1씩 크게
            // 세로축 (i) : 1번 물건부터 i번 물건까지 넣을 수 있다는 뜻
            // 가로축 (v) : 부피
            dpTable = new int[numOfStuff + 1][volumeOfBag + 1];

            // 2-1. 각 칸에 들어가는 수치 (C(i, v))
            // 2-2. i = 0 or v = 0 : C(i, v) = 0

            for (int stuffNum = 1; stuffNum <= numOfStuff; stuffNum++) {
                for (int curVolume = 0; curVolume <= volumeOfBag; curVolume++) {

                    // 2-3. i > 0 and v < v_i : C(i, v) = C(i-1, v)
                    if (curVolume < stuffInfoArr[stuffNum].volume) {
                        dpTable[stuffNum][curVolume] = dpTable[stuffNum - 1][curVolume];
                    }
                    // 2-4. i > 0 and v >= v_i : C(i, v) = MAX(c_i + C(i-1, v-v_i), C(i-1, v))
                    else if (curVolume >= stuffInfoArr[stuffNum].volume) {
                        dpTable[stuffNum][curVolume] = Math.max(stuffInfoArr[stuffNum].cost + dpTable[stuffNum - 1][curVolume - stuffInfoArr[stuffNum].volume],
                                dpTable[stuffNum - 1][curVolume]);
                    }
                }
            }

            sb = new StringBuilder("#").append(test_case).append(" ").append(dpTable[numOfStuff][volumeOfBag]);

            System.out.println(sb);
        }

    }

    static class Stuff {
        int volume;
        int cost;

        public Stuff(int volume, int cost) {
            this.volume = volume;
            this.cost = cost;
        }
    }


}
