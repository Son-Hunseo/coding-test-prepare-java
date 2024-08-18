package SWEA.D3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Deque;
import java.util.LinkedList;
import java.util.StringTokenizer;

/**
 * SWEA 1225 암호 생성기
 *
 * 1. 테스트 케이스를 입력 받는다. (의미 없는 입력)
 * 2. 8개의 데이터를 입력 받는다.
 * 3. 규칙에 따라 암호를 생성한다. (Deque 이용)
 * 3-1. (amount % 6) 만큼 감소시킨다. 이후 amount++;
 * 3-1-1. 감소시켰을 때 1보다 크거나 같다면, 해당 숫자를 감소시킨 상태로 맨 뒤로 보낸다.
 * 3-1-2. 감소시켰을 때 1보다 작다면, 해당 숫자를 0으로 만들고 맨뒤로 보낸다. 이후 종료한다 (종료 조건)
 * 4. 암호를 출력한다. (공백 있음)
 *
 */

public class SWEA01225암호생성기 {

    static BufferedReader br;
    static StringTokenizer st;
    static Deque<Integer> password;
    static StringBuilder sb;

    public static void main(String[] args) throws IOException {

        br = new BufferedReader(new InputStreamReader(System.in));

        for (int test_case = 1; test_case <= 10; test_case++) {

            // 1. 테스트 케이스를 입력 받는다. (의미 없는 입력)
            int no_mean = Integer.parseInt(br.readLine());

            password = new LinkedList<>();
            st = new StringTokenizer(br.readLine());

            // 2. 8개의 데이터를 입력 받는다.
            for (int passwordIdx = 0; passwordIdx < 8; passwordIdx++) {
                password.addLast(Integer.parseInt(st.nextToken()));
            }

            // 3. 규칙에 따라 암호를 생성한다. (Deque 이용)
            int curNum;
            int minusAmount = 0;

            while (true) {
                // 3-1. (amount % 6) 만큼 감소시킨다. 이후 amount++; 0은 안됨
                curNum = password.pollFirst();
                curNum -= (minusAmount % 5) + 1;
                minusAmount++;

                // 3-1-1. 감소시켰을 때 1보다 크거나 같다면, 해당 숫자를 감소시킨 상태로 맨 뒤로 보낸다.
                if (curNum >= 1) {
                    password.addLast(curNum);
                } else { // 3-1-2. 감소시켰을 때 1보다 작다면, 해당 숫자를 0으로 만들고 맨뒤로 보낸다. 이후 종료한다 (종료 조건)
                    curNum = 0;
                    password.addLast(curNum);
                    break;
                }
            }

            // 4. 암호를 출력한다. (공백 있음)
            sb = new StringBuilder();
            sb.append("#");
            sb.append(test_case);
            sb.append(" ");

            for (int passwordCnt = 0; passwordCnt < 8; passwordCnt++) {
                sb.append(password.pollFirst());
                sb.append(" ");
            }

            System.out.println(sb.toString());
        }

    }
}
