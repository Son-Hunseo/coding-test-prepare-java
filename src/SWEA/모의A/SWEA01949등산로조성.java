package SWEA.모의A;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * SWEA 1949 등산로조성
 *
 * 1. 입력
 * 1-1. 테스트 케이스의 수 T를 입력받는다.
 * 1-2. 맵의 크기 N, 최대 공사 가능 깊이 K를 입력받는다.
 * 1-3. 맵을 입력받는다.
 *
 * 2. 완전탐색 + bfs
 * 2-1. 맵을 순회하면서 0 ~ K 만큼 땅을 파본다.(N x N x (K+1))
 * 2-2. 땅을 판 상태에서 가장 높은 봉우리들의 위치를 리스트에 넣는다.
 * 2-3. 리스트에서 하나씩 꺼내서 bfs를 돌려서 가장 긴 위치를 결과에 최댓값으로 갱신한다.
 *
 * 3. 결과를 출력한다.
 */

public class SWEA01949등산로조성 {

    static BufferedReader br;
    static StringTokenizer st;

    static int sizeOfMap;
    static int maxDigDepth;

    static int[][] map;
    static int[][] roadLengthMap;

    static ArrayList<int[]> peekList;

    static int[] dRow = {-1, 1, 0, 0};
    static int[] dCol = {0, 0, -1, 1};

    static int result = 0;

    public static void main(String[] args) throws IOException {

        // 1. 입력
        br = new BufferedReader(new InputStreamReader(System.in));

        // 1-1. 테스트 케이스의 수 T를 입력받는다.
        int T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {
            // 1-2. 맵의 크기 N, 최대 공사 가능 깊이 K를 입력받는다.
            st = new StringTokenizer(br.readLine().trim());

            sizeOfMap = Integer.parseInt(st.nextToken());
            maxDigDepth = Integer.parseInt(st.nextToken());

            int maxHeight = 0;

            // 1-3. 맵을 입력받는다.
            map = new int[sizeOfMap][sizeOfMap];
            for (int rowIdx = 0; rowIdx < sizeOfMap; rowIdx++) {
                st = new StringTokenizer(br.readLine().trim());
                for (int colIdx = 0; colIdx < sizeOfMap; colIdx++) {
                    map[rowIdx][colIdx] = Integer.parseInt(st.nextToken());
                    maxHeight = Math.max(maxHeight, map[rowIdx][colIdx]);
                }
            }

            peekList = new ArrayList<>();

            for (int rowIdx = 0; rowIdx < sizeOfMap; rowIdx++) {
                for (int colIdx = 0; colIdx < sizeOfMap; colIdx++) {
                    if (map[rowIdx][colIdx] == maxHeight) {
                        peekList.add(new int[]{rowIdx, colIdx});
                    }
                }
            }

            // 2. 완전탐색 + bfs
            // 2-1. 맵을 순회하면서 0 ~ K 만큼 땅을 파본다.(N x N x (K+1))
            result = 0;

            for (int digDepth = 0; digDepth <= maxDigDepth; digDepth++) {
                for (int rowIdx = 0; rowIdx < sizeOfMap; rowIdx++) {
                    for (int colIdx = 0; colIdx < sizeOfMap; colIdx++) {
                        map[rowIdx][colIdx] -= digDepth;
                        bfs();
                        map[rowIdx][colIdx] += digDepth; // 원복
                    }
                }
            }

            System.out.println("#" + test_case + " " + result);

        }

    }

    static void bfs() {

        for (int[] peekLoca : peekList) {

            Deque<int[]> queue = new ArrayDeque<>();

            roadLengthMap = new int[sizeOfMap][sizeOfMap];
            roadLengthMap[peekLoca[0]][peekLoca[1]] = 1;
            queue.offer(peekLoca);

            while (!queue.isEmpty()) {

                int[] cur = queue.poll();
                int curRow = cur[0];
                int curCol = cur[1];

                for (int dr = 0; dr < 4; dr++) {
                    int nRow = curRow + dRow[dr];
                    int nCol = curCol + dCol[dr];

                    if (nRow < 0 || nCol < 0 || nRow >= sizeOfMap || nCol >= sizeOfMap) {
                        continue;
                    }

                    if (map[nRow][nCol] >= map[curRow][curCol]) {
                        continue;
                    }

                    roadLengthMap[nRow][nCol] = roadLengthMap[curRow][curCol] + 1;
                    result = Math.max(result, roadLengthMap[nRow][nCol]);
                    queue.offer(new int[]{nRow, nCol});
                }
            }
        }
    }
}
