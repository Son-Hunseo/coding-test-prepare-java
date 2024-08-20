package SWEA.D4.그리디;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * SWEA4796 의석이의 우뚝 선 산
 *
 * 주의!) 이 문제는 최대 한줄에 50000개가 주어진다. -> BufferedReader로는 8000개까지가 받아지고 그 다음부터는 다시 line을 읽어야한다.
 * -> Scanner 사용
 *
 * 1. 테스트 케이스의 수 T를 입력받는다.
 * 2. 구간의 개수 N을 입력받는다.
 * 3. 각 구간의 높이를 입력받는다.
 *
 * 4. 구간을 순회하면서 봉오리을 기준으로 왼쪽 오르막의 개수, 오른쪽 내리막의 개수를 리스트에 기록한다.
 * 예) 1 2 6 5 4 3 -> 6이 봉오리 왼쪽 오르막의 개수 = 2, 오른쪽 내리막의 개수 = 3
 * 5. 리스트를 순회하면서 결과에 오르막의 개수 x 내리막의 개수를 더한다.
 * 6. 결과를 출력한다.
 */
public class SWEA04796의석이의우뚝선산 {

    static Scanner sc;

    static int numOfSection;
    static ArrayList<int[]> upDownList;
    static final boolean UP = true;
    static final boolean DOWN = false;

    public static void main(String[] args) throws IOException {

        sc = new Scanner(System.in);

        // 1. 테스트 케이스의 수 T를 입력받는다.
        int T = sc.nextInt();

        for (int test_case = 1; test_case <= T; test_case++) {
            // 2. 구간의 개수 N을 입력받는다.
            numOfSection = sc.nextInt();

            upDownList = new ArrayList<>();

            int upCnt = 0;
            int downCnt = 0;
            int befNum = -1;
            boolean status = DOWN;

            // 3. 각 구간의 높이를 입력받는다.
            // 4. 구간을 순회하면서 봉오리을 기준으로 왼쪽 오르막의 개수, 오른쪽 내리막의 개수를 리스트에 기록한다.
            for (int heightIdx = 0; heightIdx < numOfSection; heightIdx++) {
                int curHeight = sc.nextInt();

                // 내려가다가 올라가는 상태로 바뀔 경우
                if (status == DOWN && curHeight > befNum) {
                    // 지금까지 저장한 오르막의 길이와 내리막의 길이를 배열로 저장하고, 초기화한다.
                    int[] curInfo = {upCnt, downCnt};
                    upDownList.add(curInfo);
                    if (befNum == -1) { // 처음인 경우만
                        upCnt = 1;
                    } else {
                        upCnt = 2; // 이전과 현재 포함
                    }
                    downCnt = 0;
                    befNum = curHeight;
                    status = UP;
                }

                // 올라가다가 내려가는 상태로 바뀔 경우 (꼭대기)
                else if (status == UP && curHeight < befNum) {
                    // 꼭대기는 오르막의 길이에 포함하지 않고 그대로 진행한다.
                    upCnt--;
                    downCnt++;
                    befNum = curHeight;
                    status = DOWN;
                }

                // 올라가는 상태가 유지될 경우
                else if (status == UP && curHeight > befNum) {
                    upCnt++;
                    befNum = curHeight;
                }

                // 내려가는 상태가 유지될 경우
                else if (status == DOWN && curHeight < befNum) {
                    downCnt++;
                    befNum = curHeight;
                }

                if (heightIdx == numOfSection -1) {
                    int[] curInfo = {upCnt, downCnt};
                    upDownList.add(curInfo);
                }
            }

            // 5. 리스트를 순회하면서 결과에 오르막의 개수 x 내리막의 개수를 더한다.
            int result = 0;

            for (int[] info : upDownList) {
                result += info[0] * info[1];
            }

            // 6. 결과를 출력한다.
            System.out.println("#" + test_case + " " + result);
        }
    }
}
