package SWEA.D1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * SWEA 2072 홀수만 더하기
 * 1. 테스트 케이스 수 T를 입력받는다.
 * 2. 10개의 수를 입력 받아서 배열에 저장한다.
 * 3. 배열을 순회하면서 2로 나누었을 때, 나머지가 1이 나오는 수만 결과값에 더한다.
 * 4. 결과를 출력한다.
 */
public class SWEA02072홀수만더하기 {

    static BufferedReader br;
    static StringTokenizer st;
    static int[] data;

    public static void main(String[] args) throws IOException {

        br = new BufferedReader(new InputStreamReader(System.in));

        // 1. 테스트 케이스 수 T를 입력받는다.
        int T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {
            data = new int[10];

            // 2. 10개의 수를 입력 받아서 배열에 저장한다.
            st = new StringTokenizer(br.readLine().trim());

            for (int idx = 0; idx < 10; idx++) {
                data[idx] = Integer.parseInt(st.nextToken());
            }

            // 3. 배열을 순회하면서 2로 나누었을 때, 나머지가 1이 나오는 수만 결과값에 더한다.
            int result = 0;
            for (int idx = 0; idx < 10; idx++) {
                if (data[idx] % 2 == 1) {
                    result += data[idx];
                }
            }

            System.out.println("#" + test_case + " " + result);
        }


    }

}
