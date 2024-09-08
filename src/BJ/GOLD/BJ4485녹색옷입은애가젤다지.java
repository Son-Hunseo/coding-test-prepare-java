package BJ.GOLD;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * BJ4485 녹색옷 입은애가 젤다지
 *
 * 1. 입력
 * 1-1. 맵의 사이즈 수 N을 입력받는다.
 * 1-1-2. N이 0이라면 프로그램을 종료한다.
 * 1-2. 맵을 입력받는다.
 *
 * 2. BFS + PQ -> 이게 결국 따지고보면 다익스트라다.
 * 2-0. 새로운 맵을 생성해서 모두 INF로 초기화한다.
 * 2-1. PQ에 출발지점을 방문처리를 하고 넣는다. (새로운 맵에 출발지점을 출발지점 값으로 초기화한다)
 * 2-2. PQ에서 요소를 꺼낸다.
 * 2-2-2. 꺼낸 요소 우측하단이라면 종료한다.
 * 2-3. 해당 요소를 기준으로 4방향을 순회한다.
 * 2-4. 다음 위치의 요소가 방문하지 않았으며, 격자 안이면서, (새로운 맵의 현재 위치의 값 + 기존 맵의 다음 위치의 값)이 새로운 맵의 다음 위치의 값보다 작을경우 방문처리를 하고, 값을 갱신하고, PQ에 넣는다.
 *
 * 3. 새로운 맵의 우측하단 값을 출력한다.
 */
public class BJ4485녹색옷입은애가젤다지 {

    static BufferedReader br;
    static StringTokenizer st;

    static final int[] dRow = {-1, 1, 0, 0};
    static final int[] dCol = {0, 0, -1, 1};

    static int sizeOfMap;
    static int[][] map;
    static int[][] newMap;
    static boolean[][] visited;

    public static void main(String[] args) throws IOException {

        // 1. 입력
        br = new BufferedReader(new InputStreamReader(System.in));
        int cnt = 0;

        while (true) {
            cnt++;

            // 1-1. 맵의 사이즈 수 N을 입력받는다.
            sizeOfMap = Integer.parseInt(br.readLine());

            // 1-1-2. N이 0이라면 프로그램을 종료한다.
            if (sizeOfMap == 0) {
                break;
            }

            // 1-2. 맵을 입력받는다.
            map = new int[sizeOfMap][sizeOfMap];

            for (int rowIdx = 0; rowIdx < sizeOfMap; rowIdx++) {
                st = new StringTokenizer(br.readLine().trim());
                for (int colIdx = 0; colIdx < sizeOfMap; colIdx++) {
                    map[rowIdx][colIdx] = Integer.parseInt(st.nextToken());
                }
            }

            // 2. BFS + PQ
            // 2-0. 새로운 맵을 생성해서 모두 INF로 초기화한다.
            newMap = new int[sizeOfMap][sizeOfMap];
            for (int rowIdx = 0; rowIdx < sizeOfMap; rowIdx++) {
                Arrays.fill(newMap[rowIdx], Integer.MAX_VALUE);
            }

            // 2-1. PQ에 출발지점을 방문처리를 하고 넣는다. (새로운 맵에 출발지점을 출발지점 값으로 초기화한다)
            visited = new boolean[sizeOfMap][sizeOfMap];
            PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[2], b[2]));

            int startRow = 0;
            int startCol = 0;
            newMap[startRow][startCol] = map[startRow][startCol];
            visited[startRow][startCol] = true;
            pq.offer(new int[]{startRow, startCol, map[startRow][startCol]});

            while(!pq.isEmpty()) {
                // 2-2. PQ에서 요소를 꺼낸다.
                int[] curInfo = pq.poll();
                int curRow = curInfo[0];
                int curCol = curInfo[1];
                int curMinDistance = curInfo[2];

                // 2-2-2. 꺼낸 요소 우측하단이라면 종료한다.
                if (curRow == sizeOfMap - 1 && curCol == sizeOfMap -1) {
                    break;
                }

                // 2-3. 해당 요소를 기준으로 4방향을 순회한다.
                for (int dr = 0; dr < 4; dr++) {
                    int nextRow = curRow + dRow[dr];
                    int nextCol = curCol + dCol[dr];

                    // 2-4. 다음 위치의 요소가 방문하지 않았으며, 격자 안이면서, (새로운 맵의 현재 위치의 값 + 기존 맵의 다음 위치의 값)이 새로운 맵의 다음 위치의 값보다 작을경우 방문처리를 하고, 값을 갱신하고, PQ에 넣는다.
                    if (nextRow < 0 || nextCol < 0 || nextRow >= sizeOfMap || nextCol >= sizeOfMap) {
                        continue;
                    }
                    if (visited[nextRow][nextCol]) {
                        continue;
                    }

                    if ((newMap[curRow][curCol] + map[nextRow][nextCol]) < newMap[nextRow][nextCol]) {
                        visited[nextRow][nextCol] = true;
                        newMap[nextRow][nextCol] = newMap[curRow][curCol] + map[nextRow][nextCol];
                        pq.offer(new int[]{nextRow, nextCol, newMap[nextRow][nextCol]});
                    }
                }
            }

            // 3. 새로운 맵의 우측하단 값을 출력한다.
            System.out.println("Problem " + cnt + ": " + newMap[sizeOfMap-1][sizeOfMap-1]);
        }

    }
}
