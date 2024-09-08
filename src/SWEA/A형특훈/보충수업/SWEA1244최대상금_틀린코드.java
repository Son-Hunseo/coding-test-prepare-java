package SWEA.A형특훈.보충수업;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


public class SWEA1244최대상금_틀린코드 {

    /**
     * 자릿수-1 횟수에 가장 큰 수를 만들 수 있음이 보장되기 때문에
     * 1. 교환횟수 > 자릿수 - 1
     * 1-1. 교환횟수 - (자릿수 - 1) 이 짝수일 경우 -> 내림차순으로 sort한 결과가 답
     * 1-2. 교환횟수 - (자릿수 - 1) 이 홀수일 경우 -> 내림차순으로 sort한 결과에서 마지막 2개 자리를 교환한 것이 답
     *
     * 2. 교환횟수 <= 자릿수 - 1
     * 2-1. 완전탐색
     */

    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer tokens;

    static int T;
    static char[] data;
    static int N;
    static int MAX;

    public static void main(String[] args) throws IOException {

        T = Integer.parseInt(input.readLine());
        for (int t = 1; t <= T; t++) {
            tokens = new StringTokenizer(input.readLine());
            data = tokens.nextToken().toCharArray();
            N = Integer.parseInt(tokens.nextToken()); // N을 다 돌 필요는 없다!!!

            MAX = 0; // 전역 변수 사용 시 초기화 주의하자!!!

            if (N > data.length-1) {
                Arrays.sort(data);
                if ((N - (data.length-1)) % 2 == 1) {
                    swap(0, 1);
                }

                int num = 0;
                for (int i = data.length-1; i >= 0; i--) {
                    num = num * 10 + (data[i] - '0');
                }
                MAX = Math.max(MAX, num);
            } else {
                // 완탐 돌리기
                dfs(0, 0);
            }

            output.append("#").append(t).append(" ").append(MAX).append("\n");
        }
        System.out.println(output);
    }

    private static void dfs(int nth, int k) {
        if (nth == N) {
            checkMax();
            return;
        }

        for (int i = k; i < data.length; i++) {
            for (int j = i + 1; j < data.length; j++) {
                swap(i, j);
                dfs(nth + 1, i);// 자신부터 변경 가능
                swap(i, j);
            }
        }
    }

    private static void checkMax() {
        int num = 0;
        for (int i = 0; i < data.length; i++) {
            num = num * 10 + (data[i] - '0');
        }
        MAX = Math.max(MAX, num);
    }

    private static void swap(int i, int j) {
        char temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

}