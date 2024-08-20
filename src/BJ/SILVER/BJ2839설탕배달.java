package BJ.SILVER;

import java.util.Scanner;

/**
 * BJ2839 설탕배달
 *
 * 1. 입력받음
 * 2. 5킬로그램짜리를 목표 무게가 초과하지 않는 선에서 최대한 넣어본다.
 * 3. 5킬로그램짜리를 하나씩 빼면서 진행 (맨 처음에는 하나도 안 뺀 상태)
 * 3-1. 남은 무게가 0일 경우 5킬로그램으로만 채우면 된다.
 * 3-2. 남은 무게가 3의 배수일 경우 남은 무게를 3킬로그램으로 채운다.
 * 3-3. 남은 무게가 3의 배수가 아닐경우 5킬로그램짜리를 하나 더 빼서 반복한다.
 * 4. 모든 5킬로그램짜리를 다 빼도 3의 배수가 아닐경우 -1을 출력한다.
 */
public class BJ2839설탕배달 {

    static Scanner sc;

    public static void main(String[] args) {

        int result = 0;
        // 1. 입력받음
        sc = new Scanner(System.in);
        int targetW = sc.nextInt();

        int numOf5Bag = 0;
        int curW = 0;

        // 2. 5킬로그램짜리를 목표 무게가 초과하지 않는 선에서 최대한 넣어본다.
        while (targetW >= curW + 5) {
            numOf5Bag++;
            curW = curW + 5;
        }

        // 3. 5킬로그램짜리를 하나씩 빼면서 진행 (맨 처음에는 하나도 안 뺀 상태)
        while (numOf5Bag != 0) {
            // 3-1. 남은 무게가 0일 경우 5킬로그램으로만 채우면 된다.
            if ((targetW - curW) == 0) {
                result = numOf5Bag;
                break;
            }
            // 3-2. 남은 무게가 3의 배수일 경우 남은 무게를 3킬로그램으로 채운다.
            if ((targetW - curW) % 3 == 0) {
                result = numOf5Bag + ((targetW - curW) / 3);
                break;
            }
            // 3-3. 남은 무게가 3의 배수가 아닐경우 5킬로그램짜리를 하나 더 빼서 반복한다.
            numOf5Bag--;
            curW = curW - 5;
        }

        if (numOf5Bag == 0) {
            if (targetW % 3 == 0) {
                result = targetW / 3;
            } else { // 4. 모든 5킬로그램짜리를 다 빼도 3의 배수가 아닐경우 -1을 출력한다.
                result = -1;
            }
        }

        System.out.println(result);
    }
}
