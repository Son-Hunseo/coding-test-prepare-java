package SWEA.D4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * SWEA20552 XOR과 합
 *
 * - 문제에 설명이 너무 부족하다.
 * - 비트 XOR 연산에 대한 사전 지식이 있어야 한다.
 * - https://dojang.io/mod/page/view.php?id=1638
 *
 * [문제 예시 분석]
 * 1. [8, 5]
 * -> 8 = 1000, XOR 연산으로 1000을 만드려면 최소한 2진수 4번째 자리에 1이 하나 이상 있어야하는데, 5이하의 숫자로 만들 수 없다.
 * -> 따라서 만들 수 없다.
 * -> 출력: -1
 *
 * 2. [0, 0]
 * -> 0 = 0, 0개 사용하면 0으로 쳐주는 판정인가보다. 그리고 원소가 없으면 아예 출력하지 않는 것 같다.
 * -> 출력: 0
 *
 * 3. [2, 4]
 * -> 2 = 10, 합이 4가 되게끔 만드는 방법
 * -> [2, 1, 1] = [10, 1, 1] XOR 연산을하면 된다.
 * -> [3, 1] = [11, 1] XOR 연산을하면 된다.
 * -> 둘중에 [3, 1]이 더 짧다.
 * -> 출력: 2 / 3 1
 *
 * 4. [1, 3]
 * -> [1, 1, 1] 경우의 수 밖에 없다.
 * -> 출력: 3 / 1 1 1
 *
 * [로직]
 * 0. 2진수를 구하는 로직짜기
 * - 0이 될 때까지 2로 계속 나눈 뒤 나머지를 역순으로 읽는다.
 * - 예: 13: 13 -> 6 .. 1 -> 3 .. 0 -> 1 .. 1 -> 0 .. 1 : 1101
 *
 * 1. 일단 X의 2진수 최대 자릿수보다 S의 2인수 최대 자릿수의 자리수 자체가 X가 크면 불가능하다. -> -1 출력
 * 2. 
 *
 */

public class SWEA20552XOR과합 {

    static BufferedReader br;
    static StringTokenizer st;

    public static void main(String[] args) throws IOException {

        br = new BufferedReader(new InputStreamReader(System.in));

        int T = Integer.parseInt(br.readLine());

        for (int test_case = 0; test_case <= T; test_case++) {

            st = new StringTokenizer(br.readLine().trim());
            int X = Integer.parseInt(st.nextToken());
            int S = Integer.parseInt(st.nextToken());



        }

    }
}
