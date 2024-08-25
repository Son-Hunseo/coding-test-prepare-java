package SWEA.D5.그리디;

import java.util.Scanner;

/**
 * SWEA 6782 현주가 좋아하는 제곱근 놀이
 *
 * 1. 테스트 케이스의 개수 T를 입력받는다.
 * 2. 2로 만들어야하는 N을 입력받는다.
 * 3. 게임 수행
 * 3-1. N이 2일 경우 게임을 종료한다.
 * 3-2. 제곱근을 할 수 있다면 제곱근을 하고 연산횟수에 1 더하고, 하지 못할경우 제곱근을 할 수 있는 수만큼 더하고 연산횟수를 그만큼 올려준다.
 * 4. 연산 횟수를 출력한다.
 */

public class SWEA06782현주가좋아하는 {

    static Scanner sc;

    public static void main(String[] args) {

        sc = new Scanner(System.in);
        // 1. 테스트 케이스의 개수 T를 입력받는다.
        int T = sc.nextInt();

        for (int test_case = 1; test_case <= T; test_case++) {
            // 2. 2로 만들어야하는 N을 입력받는다.
            long num = sc.nextLong();
            long calCnt = 0;

            // 3. 게임 수행
            while (true) {
                // 3-1. N이 2일 경우 게임을 종료한다.
                if (num == 2) {
                    break;
                }

                // 3-2. 제곱근을 할 수 있다면, 제곱근을 하고 하지 못할경우 1을 더하고 연산 횟수를 1 올린다.
                if (Math.sqrt(num) == (long) Math.sqrt(num)) {
                    num = (long) Math.sqrt(num);
                    calCnt++;
                } else {
                    long befnum = num;
                    num = (long) Math.pow(((long) Math.sqrt(num)) + 1, 2);
                    calCnt += num - befnum;
                }
            }
            // 4. 연산 횟수를 출력한다.
            System.out.println("#" + test_case + " " + calCnt);
        }
    }
}
