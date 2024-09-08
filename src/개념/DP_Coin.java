package 개념;

import java.util.Arrays;
import java.util.Scanner;

// 1원 4원 6원 단위의 동전으로 최소의 동전수를 사용해서 거슬러주는 동전의 수 계산 (동전의 갯수 무제한)

public class DP_Coin {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int money = sc.nextInt(); // 거슬러줘야하는 금액

        int[] D = new int[money + 1]; // 각 금액에 따른 최소 동전수를 저장한 dp table
        D[0] = 0; // base case 초기화 명시

        for (int i = 1; i <= money; i++) {
            int min = D[i - 1] + 1; // 1원짜리 동전은 항상 사용할 수 있으므로, 이 값으로 초기화한다.

            if (i >= 4) {
                min = Math.min(min, D[i - 4] + 1);
            }

            if (i >= 6) {
                min = Math.min(min, D[i - 6] + 1);
            }

            D[i] = min;
        }

        System.out.println(Arrays.toString(D));
        System.out.println(D[money]);
    }
}
