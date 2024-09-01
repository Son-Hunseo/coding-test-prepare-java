package SWEA.D4.MST;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * SWEA 3289 서로소 집합
 *
 * 1. 입력
 * 1-1. 테스트케이스의 수 T를 입력받는다.
 * 1-2. 원소의 갯수 N과 연산의 갯수 M을 입력받는다. (단, N이 주어질 경우, 각 원소는 1, 2, ... , N이다.)
 * 1-3. 연산들을 입력받는다.
 *
 * 2. 서로소 집합 연산
 * 2-1. 각 원소를 단위 서로소 집합으로 초기화한다.
 * 2-2. 연산이 0이라면 합집합 연산을 수행한다.
 * 2-3. 연산이 1이라면 두 원소가 같은 집합에 포함되어있는지 확인하고 같은 집합이라면 결과에 1을 아니라면 0을 붙인다.
 *
 * 3. 출력
 * 3-1. 같은집합인지 확인하는 연산의 결과들을 공백없이 출력한다.
 *
 * Utils
 * - make
 * - find
 * - union
 */

public class SWEA03289서로소집합 {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    // 연산
    static final int UNION = 0;
    static final int IS_SAME_SET = 1;

    // 결과
    static final int SAME_SET = 1;
    static final int NOT_SAME_SET = 0;

    static int[] parents;

    public static void main(String[] args) throws IOException {

        // 1. 입력
        br = new BufferedReader(new InputStreamReader(System.in));

        // 1-1. 테스트케이스의 수 T를 입력받는다.
        int T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {

            // 3. 출력
            sb = new StringBuilder();
            sb.append("#");
            sb.append(test_case);
            sb.append(" ");

            // 1-2. 원소의 갯수 N과 연산의 갯수 M을 입력받는다. (단, N이 주어질 경우, 각 원소는 1, 2, ... , N이다.)
            st = new StringTokenizer(br.readLine().trim());

            int numOfElement = Integer.parseInt(st.nextToken());
            int numOfCalculation = Integer.parseInt(st.nextToken());

            // 부모 배열 초기화
            parents = new int[numOfElement + 1];

            // 2-1. 각 원소를 단위 서로소 집합으로 초기화한다.
            for (int elementIdx = 1; elementIdx <= numOfElement; elementIdx++) {
                make(elementIdx);
            }

            // 1-3. 연산들을 입력받는다.
            for (int calIdx = 0; calIdx < numOfCalculation; calIdx++) {

                st = new StringTokenizer(br.readLine().trim());

                int calType = Integer.parseInt(st.nextToken());
                int elementAIdx = Integer.parseInt(st.nextToken());
                int elementBIdx = Integer.parseInt(st.nextToken());

                // 2-2. 연산이 0이라면 합집합 연산을 수행한다.
                if (calType == UNION) {
                    union(elementAIdx, elementBIdx);
                } else if (calType == IS_SAME_SET) { // 2-3. 연산이 1이라면 두 원소가 같은 집합에 포함되어있는지 확인하고 같은 집합이라면 결과에 1을 아니라면 0을 붙인다.
                    if (find(elementAIdx) == find(elementBIdx)) {
                        sb.append(SAME_SET);
                    } else {
                        sb.append(NOT_SAME_SET);
                    }
                }

            }

            // 3-1. 같은집합인지 확인하는 연산의 결과들을 공백없이 출력한다.
            System.out.println(sb);

        }

    }

    static void make(int elementIdx) {
        parents[elementIdx] = -1;
    }

    static int find(int elementIdx) {
        if (parents[elementIdx] < 0) {
            return elementIdx;
        }

        return parents[elementIdx] = find(parents[elementIdx]);
    }

    static boolean union(int elementAIdx, int elementBIdx) {

        int aRoot = find(elementAIdx);
        int bRoot = find(elementBIdx);

        if (aRoot == bRoot) {
            return false;
        }

        parents[aRoot] += parents[bRoot];
        parents[bRoot] = aRoot;
        return true;
    }
}
