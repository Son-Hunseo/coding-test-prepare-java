package 개념;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

public class Dijkstra_PQ_SWEA1249보급로 {

    static int sizeOfMap, map[][];

    static int[] dRow = {-1, 1, 0, 0};
    static int[] dCol = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {

            sizeOfMap = Integer.parseInt(br.readLine());
            map = new int[sizeOfMap][sizeOfMap];

            for (int rowIdx = 0; rowIdx < sizeOfMap; rowIdx++) {

                String row = br.readLine();
                for (int colIdx = 0; colIdx < sizeOfMap; colIdx++) {
                    map[rowIdx][colIdx] = row.charAt(colIdx) - '0';
                }

            }
            System.out.println("#" + test_case + " " + getMinTime(0, 0, sizeOfMap - 1, sizeOfMap - 1));

        }
    }

    static int getMinTime(int startRow, int startCol, int endRow, int endCol) {

        final int INF = Integer.MAX_VALUE;

        boolean[][] visited = new boolean[sizeOfMap][sizeOfMap];
        int[][] minTime = new int[sizeOfMap][sizeOfMap];

        PriorityQueue<int[]> pQ = new PriorityQueue<>((a, b) -> Integer.compare(a[2], b[2]));

        for (int rowIdx = 0; rowIdx < sizeOfMap; rowIdx++) {
            for (int colIdx = 0; colIdx <sizeOfMap; colIdx++) {
                minTime[rowIdx][colIdx] = INF;
            }
        }

        minTime[startRow][startCol] = 0;
        pQ.offer(new int[]{startRow, startCol, minTime[startRow][startCol]});

        while (!pQ.isEmpty()) {

            int[] stopOver = pQ.poll();
            int curRow = stopOver[0];
            int curCol = stopOver[1];
            int time = stopOver[2];

            if (visited[curRow][curCol]) continue;
            visited[curRow][curCol] = true;
            if (curRow == endRow && curCol == endCol) return time;

            for (int dr = 0; dr < 4; dr++) {
                int nr = curRow + dRow[dr];
                int nc = curCol + dCol[dr];
                if (nr >= 0 && nr < sizeOfMap && nc >= 0 && nc < sizeOfMap && !visited[nr][nc] && minTime[nr][nc] > time + map[nr][nc]) {
                    minTime[nr][nc] = time + map[nr][nc];
                    pQ.offer(new int[]{nr, nc, minTime[nr][nc]});
                }
            }

        }
        return -1;
    }
}
