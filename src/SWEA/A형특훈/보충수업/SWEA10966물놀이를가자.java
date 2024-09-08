package SWEA.A형특훈.보충수업;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * SWEA 10966 물놀이를가자
 *
 * 1. 입력
 * 1-1. 테스트케이스의 개수 T를 입력받는다.
 * 1-2. 행과 열의 크기 N과 M을 입력받는다.
 * 1-3. N개 줄에 걸쳐 맵을 입력받아서 물은 따로 리스트로 좌표를 저장한다. (맵은 모두 MAX로 초기화한다.)
 *
 * 2. bfs
 * 2-0.물위치를 싹다 큐에 넣고 깊이 기준의 bfs를 돌린다.
 *
 * 3. 출력
 * 3-1. 맵을 돌면서 0이 아닌위치들의 값을 모두 더해서 출력한다.
 *
 */

public class SWEA10966물놀이를가자 {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static char[][] map;
    static int[][] distanceMap;
    static boolean[][] isVisited;

    static int sizeOfRow;
    static int sizeOfCol;

    static int[] dRow = {-1, 1, 0, 0};
    static int[] dCol = {0, 0, -1, 1};
    static Deque<int[]> queue;

    public static void main(String[] args) throws IOException {
        // 1. 입력
        br = new BufferedReader(new InputStreamReader(System.in));

        // 1-1. 테스트케이스의 개수 T를 입력받는다.
        int T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {

            sb = new StringBuilder();
            sb.append("#");
            sb.append(test_case);
            sb.append(" ");

            st = new StringTokenizer(br.readLine().trim());

            // 1-2. 행과 열의 크기 N과 M을 입력받는다.
            sizeOfRow = Integer.parseInt(st.nextToken());
            sizeOfCol = Integer.parseInt(st.nextToken());

            // 1-3. N개 줄에 걸쳐 맵을 입력받아서 땅과 물을 구분하여 각각 리스트에 저장한다.
            map = new char[sizeOfRow][sizeOfCol];
            distanceMap = new int[sizeOfRow][sizeOfCol];

            queue = new ArrayDeque<>();
            isVisited = new boolean[sizeOfRow][sizeOfCol];

            for (int rowIdx = 0; rowIdx < sizeOfRow; rowIdx++) {
                String row = br.readLine();
                for (int colIdx = 0; colIdx < sizeOfCol; colIdx++) {
                    char element = row.charAt(colIdx);
                    map[rowIdx][colIdx] = element;
                    if (element == 'W') {
                        isVisited[rowIdx][colIdx] = true;
                        queue.offer(new int[]{rowIdx, colIdx});
                    }
                }
            }

            // 2. bfs
            bfs();

            int result = 0;

            for (int rowIdx = 0; rowIdx < sizeOfRow; rowIdx++) {
                for (int colIdx = 0; colIdx < sizeOfCol; colIdx++) {
                    result += distanceMap[rowIdx][colIdx];
                }
            }

            sb.append(result);
            System.out.println(sb);
        }
    }

    static void bfs() {

        int cnt = 0;

        while (!queue.isEmpty()) {

            int size = queue.size();
            cnt++;

            while (size-- != 0) {
                int[] cur = queue.poll();

                for (int dr = 0; dr < 4; dr++) {
                    int nRow = cur[0] + dRow[dr];
                    int nCol = cur[1] + dCol[dr];

                    if (nRow < 0 || nCol < 0 || nRow >= sizeOfRow || nCol >= sizeOfCol) {
                        continue;
                    }

                    if (isVisited[nRow][nCol]) {
                        continue;
                    }

                    if (map[nRow][nCol] == 'W') {
                        continue;
                    }

                    distanceMap[nRow][nCol] = cnt;
                    isVisited[nRow][nCol] = true;
                    queue.add(new int[]{nRow, nCol});
                }
            }
        }

    }
}
