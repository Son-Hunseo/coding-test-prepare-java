package SWEA.A형특훈.보충수업;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.StringTokenizer;

public class SWEA1244최대상금_강사님 {

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
            updateN();
            MAX = 0; // 전역 변수 사용 시 초기화 주의하자!!!
            // 완탐 돌리기
            dfs(0, 0);
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

    private static void updateN() {
        if (N > data.length) {
            if (N % 2 != data.length % 2) {
                N = data.length - 1;
            } else {
                N = data.length;
            }
        }
    }

}