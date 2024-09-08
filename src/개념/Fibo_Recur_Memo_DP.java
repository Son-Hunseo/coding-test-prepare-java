package 개념;

import java.util.Arrays;
import java.util.Scanner;

public class Fibo_Recur_Memo_DP {

    static long callCnt1, callCnt2, callCnt3;
    static long[] memo;

    // 순수 재귀
    static long fibo1(int n) {
        callCnt1++;

        if (n <= 2) {
            return 1;
        }

        return fibo1(n - 1) + fibo1(n - 2);
    }

    static long fibo2(int n) {
        callCnt2++;
        if (memo[n] != -1) {
            return memo[n];
        }
        return memo[n] = fibo2(n - 1) + fibo2(n - 2);
    }

    static long fibo3(int n) {
        long[] D = new long[n + 1];
        D[0] = 0;
        D[1] = 1;
        for (int i = 2; i <= n; i++) {
            callCnt3++;
            D[i] = D[i - 1] + D[i - 2];
        }
        return D[n];
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        memo = new long[N + 1];
        Arrays.fill(memo, -1);
        memo[0] = 0;
        memo[1] = 1;

        System.out.println(fibo3(N));
        System.out.println(callCnt3);

        System.out.println(fibo2(N));
        System.out.println(callCnt2);

        System.out.println(fibo1(N));
        System.out.println(callCnt1);
    }
}
