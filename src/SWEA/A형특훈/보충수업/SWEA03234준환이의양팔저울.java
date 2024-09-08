package SWEA.A형특훈.보충수업;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * SWEA 3234 준환이의 양팔저울
 *
 * 1. 입력
 * 1-1. 테스트케이스의 수 T를 입력받는다.
 * 1-2. 추의 개수 N을 입력받는다.
 * 1-3. 추의 무게를 입력받는다.
 *
 * 2. 순열 응용
 *
 *
 */

public class SWEA03234준환이의양팔저울 {

    static BufferedReader br;
    static StringTokenizer st;

    static int result;

    static int sum;

    public static void main(String[] args) throws IOException {

        // 1. 입력
        br = new BufferedReader(new InputStreamReader(System.in));

        // 1-1. 테스트케이스의 수 T를 입력받는다.
        int T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {
            // 1-2. 추의 개수 N을 입력받는다.
            int numOfIron = Integer.parseInt(br.readLine());
            int[] ironWeightArr = new int[numOfIron];
            boolean[] isVisited = new boolean[numOfIron];

            // 1-3. 추의 무게를 입력받는다.
            st = new StringTokenizer(br.readLine().trim());

            sum = 0;

            for (int ironIdx = 0; ironIdx < numOfIron; ironIdx++) {
                int cur = Integer.parseInt(st.nextToken());
                ironWeightArr[ironIdx] = cur;
                sum += cur;
            }

            int leftSum = 0;
            int rightSum = 0;
            result = 0;

            getPermu(0, 0, numOfIron, isVisited, ironWeightArr, rightSum, leftSum);

            System.out.println("#" + test_case + " " + result);
        }
    }

    // 2. 순열 응용
    static void getPermu(int leftSelectedIdx, int rightSelectedIdx, int numOfIron, boolean[] isVisited, int[] ironWeightArr, int rightSum, int leftSum) {

        if (leftSum > (sum / 2)) {
            int num = numOfIron - leftSelectedIdx - rightSelectedIdx; // 남은 갯수
            result += (int) Math.pow(2, num) * factorial(num);
            return;
        }

        if (leftSelectedIdx + rightSelectedIdx == numOfIron) {
            result++;
            return;
        }

        for (int ironIdx = 0; ironIdx < numOfIron; ironIdx++) {
            if (isVisited[ironIdx]) {
                continue;
            }

            // 오른쪽 저울에 놓을 경우
            if (rightSum + ironWeightArr[ironIdx] <= leftSum) {
                rightSum += ironWeightArr[ironIdx];
                isVisited[ironIdx] = true;
                getPermu(leftSelectedIdx, rightSelectedIdx + 1, numOfIron, isVisited, ironWeightArr, rightSum, leftSum);
                rightSum -= ironWeightArr[ironIdx];
                isVisited[ironIdx] = false;
            }

            // 왼쪽 저울에 놓을 경우
            leftSum += ironWeightArr[ironIdx];
            isVisited[ironIdx] = true;
            getPermu(leftSelectedIdx + 1, rightSelectedIdx, numOfIron, isVisited, ironWeightArr, rightSum, leftSum);
            leftSum -= ironWeightArr[ironIdx];
            isVisited[ironIdx] = false;
        }

    }

    static int factorial(int n) {
        if (n <= 1) {
            return 1;
        }
        return n * factorial(n - 1);
    }

}
