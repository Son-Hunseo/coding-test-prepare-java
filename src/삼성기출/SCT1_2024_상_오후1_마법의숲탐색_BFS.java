package 삼성기출;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

/**
 * 삼성기출 2024년도 상반기 오후1번 마법의 숲 탐색
 *
 * 1. 입력
 * 1-1. 숲의 크기 R(행 크기), C(열 크기), 정령의 수 K를 입력받음
 * 1-2. K줄에 걸쳐 골렘이 출발하는 열의 위치, 골렘의 출구방향 정보를 입력받음
 * - 여기서 열의 위치는 2 ~ C-1의 값으로 주어지며 0번째 인덱스 = 1번째 열 임에 주의
 * - 골렘의 출구방향은 0 ~ 3 / 북, 동, 남, 서
 *
 * 2. 골렘 이동시키기
 * 2-1. 남쪽으로 이동시키기: 골렘의 동, 남, 서 위치의 아랫방향이 비어있을 경우에만 아래로 이동이 가능하다.
 * 2-2-1. 서쪽으로 이동시키기: 골렘의 북, 남, 서 위치의 서쪽방향이 비어있으며, 골렘의 남쪽 기준 서쪽으로 2칸 위치, 남서쪽 방향또한 비어있어야 이동이 가능하다.
 * 2-2-2. 서쪽으로 이동했을 경우 출구가 반시계 방향으로 이동한다.
 * 2-3-1. 동쪽으로 이동시키기: 골렘의 동, 남, 북 위치의 동쪽방향이 비어있으며, 골렘이 남쪽 기준 동쪽으로 2칸 위치, 남동쪽 방향또한 비어있어야 이동이 가능하다.
 * 2-3-2. 동쪽으로 이동했을 경우 출구가 시계 방향으로 이동한다.
 * 2-4-1. 만약 골렘을 이동시켰는데도, 숲 밖에 위치한 부분이 있을 경우, 숲 자체를 다 비우고 다음 골렘을 이동시킨다.
 * 2-4-2. 골렘의 이동 종료 후 숲 밖에 위치한 부분이 없을 경우 맵에 골렘의 정보를 표시한다. (출구는 2, 중앙은 3, 나머지 부분은 1)
 * 2-4-3. 정령을 이동시킨다.
 *
 * 3. 정령 이동시키기
 * 3-1. 정령은 각 골렘을 타고 내려갈 수 있는 가장 아래까지 내려간다.
 * 3-2-1. 현재 위치가 골렘 중앙인 경우, 상하좌우로 이동 가능 (방문한 곳 제외)
 * 3-2-2. 현재 위치가 출구인 경우, 주변 골렘으로 이동 가능 (방문한 곳 제외)
 * 3-2-3. 현재 위치가 일반 골렘인 경우, 골렘 중앙으로만 이동 가능 (방문한 곳 제외)
 * 3-3. 가장 아래까지 내려간 행의 번호를 결과에 누적시킨다. (주의! 0번째 인덱스 행은 1번째 번호의 행이다.)
 *
 * 4. 출력
 * 4-1. 누적된 행의 번호를 출력한다.
 *
 */

public class SCT1_2024_상_오후1_마법의숲탐색_BFS {

    static BufferedReader br;
    static StringTokenizer st;

    static int sizeOfRow;
    static int sizeOfCol;
    static int numOfGolem;

    static int[][] map;
    static boolean[][] visited;

    static Golem[] golemArr;

    static final int START_ROW = -2;

    static final int NOTHING = 0;
    static final int NORMAL_GOLEM = 1;
    static final int GOLEM_EXIT = 2;
    static final int GOLEM_CENTOR = 3;

    static final int NORTH = 0;
    static final int EAST = 1;
    static final int SOUTH = 2;
    static final int WEST = 3;

    // 북 동 남 서
    static final int[] dRow = {-1, 0, 1, 0};
    static final int[] dCol = {0, 1, 0, -1};

    static Deque<int[]> queue;
    static int depth;
    static int result;

    public static void main(String[] args) throws IOException {

        // 1. 입력
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine().trim());

        // 1-1. 숲의 크기 R(행 크기), C(열 크기), 정령의 수 K를 입력받음
        sizeOfRow = Integer.parseInt(st.nextToken());
        sizeOfCol = Integer.parseInt(st.nextToken());
        numOfGolem = Integer.parseInt(st.nextToken());

        golemArr = new Golem[numOfGolem];
        map = new int[sizeOfRow][sizeOfCol];

        // 1-2. K줄에 걸쳐 골렘이 출발하는 열의 위치, 골렘의 출구방향 정보를 입력받음
        for (int golemIdx = 0; golemIdx < numOfGolem; golemIdx++) {

            st = new StringTokenizer(br.readLine().trim());

            // - 여기서 열의 위치는 2 ~ C-1의 값으로 주어지며 0번째 인덱스 = 1번째 열 임에 주의
            int centorColLoca = Integer.parseInt(st.nextToken()) - 1;

            // - 골렘의 출구방향은 0 ~ 3 / 북, 동, 남, 서
            int exitLoca = Integer.parseInt(st.nextToken());

            golemArr[golemIdx] = new Golem(centorColLoca, exitLoca);
        }

        result = 0;

        for (int golemIdx = 0; golemIdx < numOfGolem; golemIdx++) {

            boolean flag = true;

            while (flag) { // 골렘이 더이상 이동하지 못할 때 까지 이동시킴
                flag = moveGolem(golemIdx);
            }

            // 2-4-1. 만약 골렘을 이동시켰는데도, 숲 밖에 위치한 부분이 있을 경우, 숲 자체를 다 비우고 다음 골렘을 이동시킨다.
            if (golemArr[golemIdx].centorRowLoca < 1) {
                map = new int[sizeOfRow][sizeOfCol];
                continue;
            }

            // 2-4-2. 골렘의 이동 종료 후 숲 밖에 위치한 부분이 없을 경우 맵에 골렘의 정보를 표시한다. (출구는 2, 나머지 부분은 1)
            Golem curGolem = golemArr[golemIdx];

            int[] NorthLoca = new int[]{curGolem.centorRowLoca + dRow[NORTH], curGolem.centorColLoca + dCol[NORTH]};
            int[] EastLoca = new int[]{curGolem.centorRowLoca + dRow[EAST], curGolem.centorColLoca + dCol[EAST]};
            int[] SouthLoca = new int[]{curGolem.centorRowLoca + dRow[SOUTH], curGolem.centorColLoca + dCol[SOUTH]};
            int[] WestLoca = new int[]{curGolem.centorRowLoca + dRow[WEST], curGolem.centorColLoca + dCol[WEST]};

            map[curGolem.centorRowLoca][curGolem.centorColLoca] = GOLEM_CENTOR;
            map[NorthLoca[0]][NorthLoca[1]] = NORMAL_GOLEM;
            map[EastLoca[0]][EastLoca[1]] = NORMAL_GOLEM;
            map[SouthLoca[0]][SouthLoca[1]] = NORMAL_GOLEM;
            map[WestLoca[0]][WestLoca[1]] = NORMAL_GOLEM;

            map[curGolem.centorRowLoca + dRow[curGolem.exitLoca]][curGolem.centorColLoca + dCol[curGolem.exitLoca]] = GOLEM_EXIT;
            queue = new ArrayDeque<>();

            visited = new boolean[sizeOfRow][sizeOfCol];
            depth = curGolem.centorRowLoca;

            // 2-4-3. 정령을 이동시킨다.
            visited[golemArr[golemIdx].centorRowLoca][golemArr[golemIdx].centorColLoca] = true;
            queue.offer(new int[]{golemArr[golemIdx].centorRowLoca, golemArr[golemIdx].centorColLoca});
            moveElement();

            // 3-3. 가장 아래까지 내려간 행의 번호를 결과에 누적시킨다. (주의! 0번째 인덱스 행은 1번째 번호의 행이다.)
            result += (depth + 1);
        }

        // 4. 출력
        // 4-1. 누적된 행의 번호를 출력한다.
        System.out.println(result);

    }

    static class Golem {

        // 인덱스 기준
        int centorRowLoca;
        int centorColLoca;

        int exitLoca;

        public Golem(int centorColLoca, int exitLoca) {
            this.centorRowLoca = START_ROW; // 맵 밖에서 시작해야하므로 골렘의 중간 위치의 행 값을 -2로 초기화한다.
            this.centorColLoca = centorColLoca;
            this.exitLoca = exitLoca;
        }
    }

    // 2. 골렘 이동시키기
    static boolean moveGolem(int golemIdx) {

        Golem curGolem = golemArr[golemIdx];

        int[] NorthLoca = new int[]{curGolem.centorRowLoca + dRow[NORTH], curGolem.centorColLoca + dCol[NORTH]};
        int[] EastLoca = new int[]{curGolem.centorRowLoca + dRow[EAST], curGolem.centorColLoca + dCol[EAST]};
        int[] SouthLoca = new int[]{curGolem.centorRowLoca + dRow[SOUTH], curGolem.centorColLoca + dCol[SOUTH]};
        int[] WestLoca = new int[]{curGolem.centorRowLoca + dRow[WEST], curGolem.centorColLoca + dCol[WEST]};

        // 2-1. 남쪽으로 이동시키기: 골렘의 동, 남, 서 위치의 아랫방향이 비어있을 경우에만 아래로 이동이 가능하다.
        int[] EastSouthLoca = new int[]{EastLoca[0] + dRow[SOUTH], EastLoca[1] + dCol[SOUTH]};
        int[] SouthSouthLoca = new int[]{SouthLoca[0] + dRow[SOUTH], SouthLoca[1] + dCol[SOUTH]};
        int[] WestSouthLoca = new int[]{WestLoca[0] + dRow[SOUTH], WestLoca[1] + dCol[SOUTH]};

        if (SouthSouthLoca[0] < sizeOfRow && // 숲 바닥 밑으로 갈 수 없다.
                (EastSouthLoca[0] < 0 || map[EastSouthLoca[0]][EastSouthLoca[1]] == NOTHING) &&
                (WestSouthLoca[0] < 0 || map[WestSouthLoca[0]][WestSouthLoca[1]] == NOTHING) &&
                map[SouthSouthLoca[0]][SouthSouthLoca[1]] == NOTHING) {
            curGolem.centorRowLoca = curGolem.centorRowLoca + dRow[SOUTH];
            curGolem.centorColLoca = curGolem.centorColLoca + dCol[SOUTH];
            return true;
        }

        // 2-2-1. 서쪽으로 이동시키기: 골렘의 북, 남, 서 위치의 서쪽방향이 비어있으며, 골렘의 남쪽 기준 서쪽으로 2칸 위치, 남서쪽 방향또한 비어있어야 이동이 가능하다.
        int[] NorthWestLoca = new int[]{NorthLoca[0] + dRow[WEST], NorthLoca[1] + dCol[WEST]};
        int[] SouthWestLoca = new int[]{SouthLoca[0] + dRow[WEST], SouthLoca[1] + dCol[WEST]};
        int[] WestWestLoca = new int[]{WestLoca[0] + dRow[WEST], WestLoca[1] + dCol[WEST]};

        int[] SouthWestWestLoca = new int[]{SouthWestLoca[0] + dRow[WEST], SouthWestLoca[1] + dCol[WEST]};
        int[] SouthWestSouthLoca = new int[]{SouthWestLoca[0] + dRow[SOUTH], SouthWestLoca[1] + dCol[SOUTH]};

        if (curGolem.centorColLoca > 1 &&
            curGolem.centorRowLoca < sizeOfRow - 2 &&
                (NorthWestLoca[0] < 0 || map[NorthWestLoca[0]][NorthWestLoca[1]] == NOTHING) &&
                (SouthWestLoca[0] < 0 || map[SouthWestLoca[0]][SouthWestLoca[1]] == NOTHING) &&
                (WestWestLoca[0] < 0 || map[WestWestLoca[0]][WestWestLoca[1]] == NOTHING) &&
                (SouthWestWestLoca[0] < 0 || map[SouthWestWestLoca[0]][SouthWestWestLoca[1]] == NOTHING) &&
                map[SouthWestSouthLoca[0]][SouthWestSouthLoca[1]] == NOTHING) {
            curGolem.centorRowLoca = curGolem.centorRowLoca + dRow[SOUTH] + dRow[WEST];
            curGolem.centorColLoca = curGolem.centorColLoca + dCol[SOUTH] + dCol[WEST];
            // 2-2-2. 서쪽으로 이동했을 경우 출구가 반시계 방향으로 이동한다.
            curGolem.exitLoca = (curGolem.exitLoca + 3) % 4;
            return true;
        }

        // 동쪽으로 이동시키기: 골렘의 동, 남, 북 위치의 동쪽방향이 비어있으며, 골렘이 남쪽 기준 동쪽으로 2칸 위치, 남동쪽 방향또한 비어있어야 이동이 가능하다.
        int[] EastEastLoca = new int[]{EastLoca[0] + dRow[EAST], EastLoca[1] + dCol[EAST]};
        int[] SouthEastLoca = new int[]{SouthLoca[0] + dRow[EAST], SouthLoca[1] + dCol[EAST]};
        int[] NorthEastLoca = new int[]{NorthLoca[0] + dRow[EAST], NorthLoca[1] + dCol[EAST]};

        int[] SouthEastEastLoca = new int[]{SouthEastLoca[0] + dRow[EAST], SouthEastLoca[1] + dCol[EAST]};
        int[] SouthEastSouthLoca = new int[]{SouthEastLoca[0] + dRow[SOUTH], SouthEastLoca[1] + dCol[SOUTH]};

        if (curGolem.centorColLoca < sizeOfCol - 2 &&
                curGolem.centorRowLoca < sizeOfRow - 2 &&
                (EastEastLoca[0] < 0 || map[EastEastLoca[0]][EastEastLoca[1]] == NOTHING) &&
                (SouthEastEastLoca[0] < 0 || map[SouthEastLoca[0]][SouthEastLoca[1]] == NOTHING) &&
                (NorthEastLoca[0] < 0 || map[NorthEastLoca[0]][NorthEastLoca[1]] == NOTHING) &&
                (SouthEastEastLoca[0] < 0 || map[SouthEastEastLoca[0]][SouthEastEastLoca[1]] == NOTHING) &&
                map[SouthEastSouthLoca[0]][SouthEastSouthLoca[1]] == NOTHING) {
            curGolem.centorRowLoca = curGolem.centorRowLoca + dRow[SOUTH] + dRow[EAST];
            curGolem.centorColLoca = curGolem.centorColLoca + dCol[SOUTH] + dCol[EAST];
            // 2-3-2. 동쪽으로 이동했을 경우 출구가 시계 방향으로 이동한다.
            curGolem.exitLoca = (curGolem.exitLoca + 1) % 4;
            return true;
        }

        return false;
    }

    static void moveElement() {

        while (!queue.isEmpty()) {

            int[] curElementLoca = queue.poll();
            int curElementRowIdx = curElementLoca[0];
            int curElementColIdx = curElementLoca[1];

            if (curElementRowIdx > depth) {
                depth = curElementRowIdx;
            }

            // 3-1. 정령은 각 골렘을 타고 내려갈 수 있는 가장 아래까지 내려간다.
            // 3-2-1. 현재 위치가 골렘 중앙인 경우, 상하좌우로 이동 가능 (방문한 곳 제외)
            if (map[curElementRowIdx][curElementColIdx] == GOLEM_CENTOR) {
                for (int dr = 0; dr < 4; dr++) {

                    int nextRowIdx = curElementRowIdx + dRow[dr];
                    int nextColIdx = curElementColIdx + dCol[dr];

                    if (visited[nextRowIdx][nextColIdx]) {
                        continue;
                    }

                    visited[nextRowIdx][nextColIdx] = true;
                    queue.offer(new int[]{nextRowIdx, nextColIdx});
                }
            }

            // 3-2-2. 현재 위치가 출구인 경우, 주변 골렘으로 이동 가능 (방문한 곳 제외)
            else if (map[curElementRowIdx][curElementColIdx] == GOLEM_EXIT) {
                for (int dr = 0; dr < 4; dr++) {

                    int nextRowIdx = curElementRowIdx + dRow[dr];
                    int nextColIdx = curElementColIdx + dCol[dr];

                    if (nextRowIdx < 0 || nextColIdx < 0 || nextRowIdx >= sizeOfRow || nextColIdx >= sizeOfCol) {
                        continue;
                    }

                    if (map[nextRowIdx][nextColIdx] == NOTHING) {
                        continue;
                    }

                    if (visited[nextRowIdx][nextColIdx]) {
                        continue;
                    }

                    visited[nextRowIdx][nextColIdx] = true;
                    queue.offer(new int[]{nextRowIdx, nextColIdx});
                }
            }
            // 3-2-3. 현재 위치가 일반 골렘인 경우, 골렘 중앙으로만 이동 가능 (방문한 곳 제외)
            else {
                for (int dr = 0; dr < 4; dr++) {

                    int nextRowIdx = curElementRowIdx + dRow[dr];
                    int nextColIdx = curElementColIdx + dCol[dr];

                    if (nextRowIdx < 0 || nextColIdx < 0 || nextRowIdx >= sizeOfRow || nextColIdx >= sizeOfCol) {
                        continue;
                    }

                    if (map[nextRowIdx][nextColIdx] != GOLEM_CENTOR) {
                        continue;
                    }

                    if (visited[nextRowIdx][nextColIdx]) {
                        continue;
                    }

                    visited[nextRowIdx][nextColIdx] = true;
                    queue.offer(new int[]{nextRowIdx, nextColIdx});
                }
            }

        }

    }

}
