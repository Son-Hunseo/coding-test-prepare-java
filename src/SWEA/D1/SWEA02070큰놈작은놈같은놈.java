package SWEA.D1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * SWEA 2070 큰놈 작은놈 같은놈
 *
 * 1. 테스트케이스의 수 T를 입력받는다.
 * 2. 2개의 수를 입력받는다.
 * 3. 2개의 수의 대소를 비교해 결과를 출력한다.
 */

public class SWEA02070큰놈작은놈같은놈 {

    static BufferedReader br;
    static StringTokenizer st;

    public static void main(String[] args) throws IOException {

        br = new BufferedReader(new InputStreamReader(System.in));

        // 1. 테스트케이스의 수 T를 입력받는다.
        int T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {
            st = new StringTokenizer(br.readLine().trim());

            // 2. 2개의 수를 입력받는다.
            int curNum = Integer.parseInt(st.nextToken());
            int targetNum = Integer.parseInt(st.nextToken());

            char result = ' ';

            // 3. 2개의 수의 대소를 비교해 결과를 출력한다.
            if (curNum > targetNum) {
                result = '>';
            } else if (curNum < targetNum) {
                result = '<';
            } else {
                result = '=';
            }

            System.out.println("#" + test_case + " " + result);
        }
    }
}
