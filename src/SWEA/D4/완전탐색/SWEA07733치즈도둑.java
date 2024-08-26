package SWEA.D4.완전탐색;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

/**
 * SWEA 7733 치즈 도둑
 *
 * - 100일 동안 BFS를 돌리면 된다.
 *
 * 1. 입력
 * 1-1. 테스트 케이스의 수 T를 입력받는다.
 * 1-2. 치즈의 크기 N을 입력받는다.
 * 1-3. 치즈의 각 칸을 입력받는다.
 *
 * 2. 시뮬레이션
 * 2-1. X번째 날에 각 칸을 순회하면서 맛있는 정도가 X인 칸을 0으로 만든다.
 * 2-2. 해당 치즈 배열을 복사해서 새로운 배열을 만든다.
 * 2-3. 새로운 배열에서 각 칸을 순회하면서 BFS를 돌리면서 카운트를 센다. (방문 처리 하면서)
 * 2-4. 카운트로 최댓값을 갱신한다.
 *
 * 3. 출력
 * 3-1. 100일 중에서 가장 덩어리가 많을 때의 덩어리의 개수를 출력한다.
 */

public class SWEA07733치즈도둑 {

    static BufferedReader br;
    static StringTokenizer st;

    // 상하좌우
    static int[] dRow = {-1, 1, 0, 0};
    static int[] dCol = {0, 0, -1, 1};

    static int[][] cheezeMap;
    static int[][] newCheezeMap;
    static Deque<int[]> queue;

    static int sizeOfCheeze;
    static int result;

    public static void main(String[] args) throws IOException {
        // 1. 입력
        br = new BufferedReader(new InputStreamReader(System.in));

        // 1-1. 테스트 케이스의 수 T를 입력받는다.
        int T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {

            result = 0;

            // 1-2. 치즈의 크기 N을 입력받는다.
            sizeOfCheeze = Integer.parseInt(br.readLine());

            cheezeMap = new int[sizeOfCheeze][sizeOfCheeze];

            // 1-3. 치즈의 각 칸을 입력받는다.
            for (int rowIdx = 0; rowIdx < sizeOfCheeze; rowIdx++) {
                st = new StringTokenizer(br.readLine().trim());
                for (int colIdx = 0; colIdx < sizeOfCheeze; colIdx++) {
                    cheezeMap[rowIdx][colIdx] = Integer.parseInt(st.nextToken());
                }
            }

            // 2. 시뮬레이션
            for (int day = 0; day <= 100; day++) {
                // 2-1. X번째 날에 요정이 각 칸을 순회하면서 맛있는 정도가 X인 칸을 0로 만든다.
                fairy(day);

                // 2-2. 해당 치즈 배열을 복사해서 새로운 배열을 만든다.
                newCheezeMap = new int[sizeOfCheeze][sizeOfCheeze];
                for (int rowIdx = 0; rowIdx < sizeOfCheeze; rowIdx++) {
                    for (int colIdx = 0; colIdx < sizeOfCheeze; colIdx++) {
                        newCheezeMap[rowIdx][colIdx] = cheezeMap[rowIdx][colIdx];
                    }
                }

                // 2-3. 새로운 배열에서 각 칸을 순회하면서 BFS를 돌리면서 카운트를 센다. (방문 처리 하면서)
                queue = new ArrayDeque<>();
                int chunkCnt = countingChunk();
                // 2-4. 카운트로 최댓값을 갱신한다.
                result = Math.max(result, chunkCnt);
                if (chunkCnt == 0) {
                    break;
                }
            }

            // 3-1. 100일 중에서 가장 덩어리가 많을 때의 덩어리의 개수를 출력한다.
            System.out.println("#" + test_case + " " + result);
        }


    }

    static void fairy(int day) {
        for (int rowIdx = 0; rowIdx < sizeOfCheeze; rowIdx++) {
            for (int colIdx = 0; colIdx < sizeOfCheeze; colIdx++) {
                if (cheezeMap[rowIdx][colIdx] == day) {
                    cheezeMap[rowIdx][colIdx] = 0;
                }
            }
        }
    }

    // BFS
    static int countingChunk() {

        int chunkCnt = 0;

        for (int rowIdx = 0; rowIdx < sizeOfCheeze; rowIdx++) {
            for (int colIdx = 0; colIdx < sizeOfCheeze; colIdx++) {
                if (newCheezeMap[rowIdx][colIdx] != 0) {
                    queue.add(new int[]{rowIdx, colIdx});
                    newCheezeMap[rowIdx][colIdx] = 0;
                    chunkCnt++;

                    while (!queue.isEmpty()) {
                        int[] curLoca = queue.pollLast();
                        int curRow = curLoca[0];
                        int curCol = curLoca[1];

                        for (int dr = 0; dr < 4; dr++) {

                            int nextRow = curRow + dRow[dr];
                            int nextCol = curCol + dCol[dr];

                            if (nextRow < 0 || nextRow >= sizeOfCheeze || nextCol < 0 || nextCol >= sizeOfCheeze) {
                                continue;
                            }

                            if (newCheezeMap[nextRow][nextCol] == 0) {
                                continue;
                            }

                            queue.add(new int[]{nextRow, nextCol});
                            newCheezeMap[nextRow][nextCol] = 0;
                        }
                    }
                }
            }
        }
        return chunkCnt;
    }

}
