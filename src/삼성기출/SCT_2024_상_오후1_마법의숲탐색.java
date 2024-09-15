package 삼성기출;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
 * 2-3. 동쪽으로 이동시키기: 골렘의 동, 남, 서 위치의 동쪽방향이 비어있으며, 골렘이 남쪽 기준 동쪽으로 2칸 위치, 남동쪽 방향또한 비어있어야 이동이 가능하다.
 * 2-4-1. 만약 골렘을 이동시켰는데도, 숲 밖에 위치한 부분이 있을 경우, 숲 자체를 다 비우고 다음 골렘을 이동시킨다.
 * 2-4-2. 골렘의 이동 종료 후 숲 밖에 위치한 부분이 없을 경우 맵에 골렘의 정보를 표시한다. (출구는 2, 나머지 부분은 1)
 * 2-4-3. 위 과정을 통해 더이상 이동할 수 없을 경우 정령을 이동시킨다.
 *
 * 3. 정령 이동시키기
 * 3-1. 정령은 각 골렘을 타고 내려갈 수 있는 가장 아래까지 내려간다. (출구가 있어야 다른 골렘으로 이동할 수 있음)
 * 3-2. 가장 아래까지 내려간 행의 번호를 결과에 누적시킨다. (주의! 0번째 인덱스 행은 1번째 번호의 행이다.)
 *
 * 4. 출력
 * 4-1. 누적된 행의 번호를 출력한다.
 *
 */

public class SCT_2024_상_오후1_마법의숲탐색 {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int sizeOfRow;
    static int sizeOfCol;
    static int numOfGolem;

    static int[][] map;

    static Golem[] golemArr;

    static final int START_ROW = -2;
    static final int NOTHING = 0;
    static final int NORMAL_GOLEM = 1;
    static final int GOLEM_EXIT = 2;

    static final int NORTH = 0;
    static final int EAST = 1;
    static final int SOUTH = 2;
    static final int WEST = 3;

    // 북 동 남 서
    static final int[] dRow = {-1, 0, 1, 0};
    static final int[] dCol = {0, 1, 0, -1};

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

        int[] curGolemNorthLoca = new int[]{curGolem.centorRowLoca + dRow[NORTH], curGolem.centorColLoca + dCol[NORTH]};
        int[] curGolemEastLoca = new int[]{curGolem.centorRowLoca + dRow[EAST], curGolem.centorColLoca + dCol[EAST]};
        int[] curGolemSouthLoca = new int[]{curGolem.centorRowLoca + dRow[SOUTH], curGolem.centorColLoca + dCol[SOUTH]};
        int[] curGolemWestLoca = new int[]{curGolem.centorRowLoca + dRow[WEST], curGolem.centorColLoca + dCol[WEST]};

        // 2-1. 남쪽으로 이동시키기: 골렘의 동, 남, 서 위치의 아랫방향이 비어있을 경우에만 아래로 이동이 가능하다.
        int[] curGolemEastSouthLoca = new int[]{curGolemEastLoca[0] + dRow[SOUTH], curGolemEastLoca[1] + dCol[SOUTH]};
        int[] curGolemSouthSouthLoca = new int[]{curGolemSouthLoca[0] + dRow[SOUTH], curGolemSouthLoca[1] + dCol[SOUTH]};
        int[] curGolemWestSouthLoca = new int[]{curGolemWestLoca[0] + dRow[SOUTH], curGolemWestLoca[1] + dCol[SOUTH]};

        // 처음에는 격자 밖이므로
        if (curGolem.centorRowLoca == START_ROW) {
            // 아랫방향의 아랫방향만 확인
            if (map[curGolemSouthSouthLoca[0]][curGolemSouthSouthLoca[1]] == NOTHING) {
                curGolem.centorRowLoca = curGolem.centorRowLoca + dRow[SOUTH];
                curGolem.centorColLoca = curGolem.centorColLoca + dCol[SOUTH];
                return true;
            }
        } else {
            if (!isOut(curGolemSouthSouthLoca[0], curGolemSouthSouthLoca[1]) &&
                    map[curGolemEastSouthLoca[0]][curGolemEastSouthLoca[1]] == NOTHING &&
                    map[curGolemSouthSouthLoca[0]][curGolemSouthSouthLoca[1]] == NOTHING &&
                    map[curGolemWestSouthLoca[0]][curGolemWestSouthLoca[1]] == NOTHING) {
                curGolem.centorRowLoca = curGolem.centorRowLoca + dRow[SOUTH];
                curGolem.centorColLoca = curGolem.centorColLoca + dCol[SOUTH];
                return true;
            }
        }

        // 2-2-1. 서쪽으로 이동시키기: 골렘의 북, 남, 서 위치의 서쪽방향이 비어있으며, 골렘의 남쪽 기준 서쪽으로 2칸 위치, 남서쪽 방향또한 비어있어야 이동이 가능하다.
        int[] curGolemNorthWestLoca = new int[]{curGolemNorthLoca[0] + dRow[WEST], curGolemNorthLoca[1] + dCol[WEST]};
        int[] curGolemSouthWestLoca = new int[]{curGolemSouthLoca[0] + dRow[WEST], curGolemSouthLoca[1] + dCol[WEST]};
        int[] curGolemWestWestLoca = new int[]{curGolemWestLoca[0] + dRow[WEST], curGolemWestLoca[1] + dCol[WEST]};

        int[] curGolemSouthWestWestLoca = new int[]{curGolemSouthWestLoca[0] + dRow[WEST], curGolemSouthWestLoca[1] + dCol[WEST]};
        int[] curGolemSouthWestSouthLoca = new int[]{curGolemSouthWestLoca[0] + dRow[SOUTH], curGolemSouthWestLoca[1] + dCol[SOUTH]};

        // 처음에는 격자 밖이므로
        if (curGolem.centorRowLoca == START_ROW) {
            // 아랫방향의 좌하단만 확인, 근데 어떤 위치가
        }




        return false;
    }

    static boolean isOut(int curRowIdx, int curColIdx) {
        // 골렘은 항상 위에서 아래로 내려오기때문에 격자 위로 벗어난 경우는 벗어나지 않았다고 생각
        return (curRowIdx >= sizeOfRow || curColIdx < 0 || curColIdx >= sizeOfCol);
    }
}
