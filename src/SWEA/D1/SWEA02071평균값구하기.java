package SWEA.D1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * SWEA 2071 평균값 구하기
 * 1. 테스트 케이스 수 T를 입력받는다.
 * 2. 10개의 수를 입력 받아서 배열에 저장한다.
 * 3. 배열을 순회하면서 결과에 더한다.
 * 4. 결과를 double로 캐스팅하고 10으로 나눈 수를 반올림해서 출력한다.
 */
public class SWEA02071평균값구하기 {

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

            // 3. 배열을 순회하면서 결과에 더한다.
            int result = 0;
            for (int idx = 0; idx < 10; idx++) {
                result += data[idx];
            }

            // 4. 결과를 double로 캐스팅하고 10으로 나눈 수를 반올림해서 출력한다.
            double result2 = Math.round((double) result / 10);

            System.out.println("#" + test_case + " " + (int) result2);
        }


    }

}
