package SWEA.D3.완전탐색;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * SWEA2805 농작물 수확하기
 *
 * 1. 테스트 케이스의 수 T를 입력받는다.
 * 2. 농장의 크기 N을 입력받는다.
 * 3. 농작물의 가치를 입력받는다.
 * 4. 농작물 가치와 크기가 같은 2차원 더미 배열을 생성한다.
 * 5. 중앙 위치(N/2) 에서 마름모의 칸 수 (NxN /2 + 1) 횟수만큼 bfs를 돌면서 2차원 배열을 1씩 늘린다.
 * 6. 더미 배열을 순회하면서 0이 아닌 위치의 농작물의 가치를 결과에 더한다.
 */
public class SWEA02805농작물수확하기 {

    static BufferedReader br;

    static int sizeOfFarm;
    static int[][] farmValueMap;
    static int[][] dummyValueMap;
    static int[] dRow = {-1, 1, 0, 0};
    static int[] dCol = {0, 0, -1, 1};
    static Deque<int[]> queue;

    public static void main(String[] args) throws IOException {

        br = new BufferedReader(new InputStreamReader(System.in));

        // 1. 테스트 케이스의 수 T를 입력받는다.
        int T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {
            // 2. 농장의 크기 N을 입력받는다.
            sizeOfFarm = Integer.parseInt(br.readLine());

            // 3. 농작물의 가치를 입력받는다.
            farmValueMap = new int[sizeOfFarm][sizeOfFarm];

            for (int row = 0; row < sizeOfFarm; row++) {
                String farmRow = br.readLine().trim();
                for (int col = 0; col < sizeOfFarm; col++) {
                    farmValueMap[row][col] = farmRow.charAt(col) - '0';
                }
            }

            // 4. 농작물 가치와 크기가 같은 2차원 더미 배열을 생성한다.
            dummyValueMap = new int[sizeOfFarm][sizeOfFarm];

            // 5. 중앙 위치(N/2) 에서 깊이(N/2) 만큼 bfs를 돌면서 2차원 배열을 1씩 늘린다.
            queue = new ArrayDeque();

            int[] startLoca = {sizeOfFarm/2, sizeOfFarm/2};
            queue.add(startLoca);

            int cnt = 0;

            while (!queue.isEmpty()) {

                // 종료 조건
                if (cnt == (sizeOfFarm * sizeOfFarm) / 2 + 1) {
                    break;
                }

                int[] curLoca = queue.pop();
                // 이미 방문한 곳이면 안감
                if (dummyValueMap[curLoca[0]][curLoca[1]] != 0) {
                    continue;
                }

                dummyValueMap[curLoca[0]][curLoca[1]]++;
                cnt++;

                for (int dr = 0; dr < 4; dr++) {
                    int[] nLoca = {curLoca[0] + dRow[dr], curLoca[1] + dCol[dr]};
                    queue.add(nLoca);
                }

            }

            // 6. 더미 배열을 순회하면서 0이 아닌 위치의 농작물의 가치를 결과에 더한다.
            int result = 0;
            for (int row = 0; row < sizeOfFarm; row++) {
                for (int col = 0; col < sizeOfFarm; col++) {
                    if (dummyValueMap[row][col] != 0) {
                        result += farmValueMap[row][col];
                    }
                }
            }

            System.out.println("#" + test_case + " " + result);
        }

    }
}
