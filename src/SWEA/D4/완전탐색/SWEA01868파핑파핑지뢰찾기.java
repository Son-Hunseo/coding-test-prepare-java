package SWEA.D4.완전탐색;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * SWEA 1868 파핑파핑 지뢰찾기
 *
 *
 * 1. 입력
 * 1-1. 테스트 케이스의 수 T를 입력받는다.
 * 1-2. 지뢰찾기를하는 표 사이즈 N을 입력받는다.
 * 1-3. 지뢰찾기를 하는 표를 입력받는다.
 * 1-3-2. 지뢰가 있는 칸은 -1로 매핑하고, 다른 칸은 0으로 일단 매핑해둔다.
 *
 * 2. 표에 숫자 적기
 * 2-1. 각 칸을 순회하면서 지뢰가 있는 칸이 아닐 경우, 8방향을 확인하고 숫자를 해당 칸에 매핑한다.
 *
 * 3. BFS
 * 3-1. 각 칸을 순회하면서 0인 경우 큐에 넣고 BFS를 돌리면서 BFS가 닿은 칸은 싹다 9999로 방문처리를 한다. (BFS 들어갈때 마다 결과 1 증가)
 * 3-2. 이후 지뢰(-1)이 아니면서, 방문(9999)하지도 않은 칸의 수를 세서 결과값에 더한다.
 *
 * 4. 결과 출력
 *
 */

public class SWEA01868파핑파핑지뢰찾기 {

    static BufferedReader br;

    // 좌상단부터 시계방향
    static int[] dRow = {-1, -1, -1, 0, 1, 1, 1, 0};
    static int[] dCol = {-1, 0, 1, 1, 1, 0, -1, -1};

    static int sizeOfMap;
    static int[][] map;
    static int[][] newMap;

    static int result;

    public static void main(String[] args) throws IOException {

        // 1. 입력
        br = new BufferedReader(new InputStreamReader(System.in));

        // 1-1. 테스트 케이스의 수 T를 입력받는다.
        int T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {
            // 1-2. 지뢰찾기를하는 표 사이즈 N을 입력받는다.
            sizeOfMap = Integer.parseInt(br.readLine());

            map = new int[sizeOfMap][sizeOfMap];

            // 1-3. 지뢰찾기를 하는 표를 입력받는다.
            for (int rowIdx = 0; rowIdx < sizeOfMap; rowIdx++) {
                String row = br.readLine();
                for (int colIdx = 0; colIdx < sizeOfMap; colIdx++) {
                    char data = row.charAt(colIdx);
                    // 1-3-2. 지뢰가 있는 칸은 -1로 매핑하고, 다른 칸은 0으로 일단 매핑해둔다.
                    if (data == '.') {
                        map[rowIdx][colIdx] = 0;
                    } else {
                        map[rowIdx][colIdx] = -1;
                    }
                }
            }

            // 2. 표에 숫자 적기
            for (int rowIdx = 0; rowIdx < sizeOfMap; rowIdx++) {
                for (int colIdx = 0; colIdx < sizeOfMap; colIdx++) {
                    // 2-1. 각 칸을 순회하면서 지뢰가 있는 칸이 아닐 경우, 8방향을 확인하고 숫자를 해당 칸에 매핑한다.
                    if (map[rowIdx][colIdx] != -1) {
                        int cnt = 0;
                        for (int dr = 0; dr < 8; dr++) {
                            int nextRow = rowIdx + dRow[dr];
                            int nextCol = colIdx + dCol[dr];

                            if (nextRow < 0 || nextRow >= sizeOfMap || nextCol < 0 || nextCol >= sizeOfMap) {
                                continue;
                            }

                            if (map[nextRow][nextCol] == -1) {
                                cnt++;
                            }
                        }
                        map[rowIdx][colIdx] = cnt;
                    }
                }
            }

            result = 0;

            // 방문처리를 할 똑같은 맵 복사
            newMap = new int[sizeOfMap][sizeOfMap];
            for (int rowIdx = 0; rowIdx < sizeOfMap; rowIdx++) {
                for (int colIdx = 0; colIdx < sizeOfMap; colIdx++) {
                    newMap[rowIdx][colIdx] = map[rowIdx][colIdx];
                }
            }

            // 3. BFS
            // 3-1. 각 칸을 순회하면서 0인 경우 큐에 넣고 BFS를 돌리면서 BFS가 닿은 칸은 싹다 9999로 방문처리를 한다. (BFS 들어갈때 마다 결과 1 증가)
            for (int rowIdx = 0; rowIdx < sizeOfMap; rowIdx++) {
                for (int colIdx = 0; colIdx < sizeOfMap; colIdx++) {
                    if (map[rowIdx][colIdx] == 0 && newMap[rowIdx][colIdx] != 9999) {
                        result++;
                        bfs(rowIdx, colIdx);
                    }
                }
            }

            // 3-2. 이후 지뢰(-1)이 아니면서, 방문(9999)하지도 않은 칸의 수를 세서 결과값에 더한다.
            for (int rowIdx = 0; rowIdx < sizeOfMap; rowIdx++) {
                for (int colIdx = 0; colIdx < sizeOfMap; colIdx++) {
                    if (map[rowIdx][colIdx] != -1 && newMap[rowIdx][colIdx] != 9999) {
                        result++;
                    }
                }
            }

            // 4. 결과 출력
            System.out.println("#" + test_case + " " + result);
        }
    }

    static void bfs(int rowIdx, int colIdx) {
        Deque<int[]> queue = new ArrayDeque<>();

        queue.add(new int[]{rowIdx, colIdx});
        // 방문 처리
        newMap[rowIdx][colIdx] = 9999;

        while (!queue.isEmpty()) {
            int[] curElement = queue.pop();
            int curRow = curElement[0];
            int curCol = curElement[1];
            // 만약 꺼낸 요소가 0 이라면
            if (map[curRow][curCol] == 0) {
                // 주변 요소 다 넣음
                for (int dr = 0; dr < 8; dr++) {
                    int nextRow = curRow + dRow[dr];
                    int nextCol = curCol + dCol[dr];

                    if (nextRow < 0 || nextRow >= sizeOfMap || nextCol < 0 || nextCol >= sizeOfMap) {
                        continue;
                    }

                    if (newMap[nextRow][nextCol] == 9999) {
                        continue;
                    }

                    queue.add(new int[]{nextRow, nextCol});
                    // 방문 처리
                    newMap[nextRow][nextCol] = 9999;
                }
            }
            // 0이 아니라면 아무것도 하지 않음
        }
    }
}
