import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 1. 테스트 케이스의 번호 T 와 사다리를 나타내는 100 x 100 배열을 입력받는다.
 * 2. 배열의 100번째 row를 순회하면서 목표 도착점을 찾는다. (2가 있는 곳)
 * 3. 해당 목표 도착점에서 역순으로 위로 올라가면서 출발점을 찾는다.
 * 3-1. 좌, 우를 먼저 고려하며, 아래로 내려가는 경우는 없으므로 델타 방향을 [좌, 우, 위] 이렇게 하면 된다.
 * 3-2. 좌, 우를 확인하고 갈 수 있으면 이동한다. (단, 좌, 우가 동시에 있는 경우는 문제에서 없다고 했으니 고려하지 않는다)
 */

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static final int LADDER_SIZE = 100;
    static final int MAX_IDX = LADDER_SIZE - 1;
    static int[][] ladderMap = new int[LADDER_SIZE][LADDER_SIZE];

    // 3-1. 좌, 우를 먼저 고려하며, 아래로 내려가는 경우는 없으므로 델타 방향을 [좌, 우, 위] 이렇게 하면 된다.
    static int[] dRow = {0, 0, -1};
    static int[] dCol = {-1, 1, 0};

    static int findTargetCol(int[][] ladderMap) {
        for (int targetCol = 0; targetCol < LADDER_SIZE; targetCol++) {
            if (ladderMap[MAX_IDX][targetCol] == 2) {
                return targetCol;
            }
        }
        return -1;
    }

    static int[] move(int curRow, int curCol) {
        for (int dr = 0; dr < 3; dr++) {
            // 벽이면 이동하지 못한다.
            if (curRow + dRow[dr] < 0 || curRow + dRow[dr] >= LADDER_SIZE || curCol + dCol[dr] < 0 || curCol + dCol[dr] >= LADDER_SIZE) {
                continue;
            }

            // 0 이면 이동하지 못한다.
            if (ladderMap[curRow + dRow[dr]][curCol + dCol[dr]] == 0) {
                continue;
            }

            // 그것이 아니라면 해당 방향으로 이동한다.

            // 지나온 자리를 0으로 만듦으로써 방문처리를 한다.
            ladderMap[curRow][curCol] = 0;

            curRow = curRow + dRow[dr];
            curCol = curCol + dCol[dr];
            break;
        }
        return new int[]{curRow, curCol};
    }

    public static void main(String[] args) throws IOException {

        br = new BufferedReader(new InputStreamReader(System.in));

        // 총 10개의 테스트 케이스가 주어진다.
        for (int test_case = 1; test_case <= 10; test_case++) {

            // 보통 위의 test_case 변수를 사용하는데 굳이 주니까 그냥 일단 입력 받는다.
            int TC = Integer.parseInt(br.readLine());

            // 1. 테스트 케이스의 번호 T 와 사다리를 나타내는 100 x 100 배열을 입력받는다.
            for (int row = 0; row < LADDER_SIZE; row++) {
                st = new StringTokenizer(br.readLine().trim());
                for (int col = 0; col < LADDER_SIZE; col++) {
                    ladderMap[row][col] = Integer.parseInt(st.nextToken());
                }
            }

            // 2. 배열의 100번째 row를 순회하면서 목표 도착점을 찾는다. (2가 있는 곳)
            int targetTargetCol = findTargetCol(ladderMap);

            // 3. 해당 목표 도착점에서 역순으로 위로 올라가면서 출발점을 찾는다.
            int curRow = MAX_IDX;
            int curCol = targetTargetCol;

            while (curRow != 0) {
                // 3-2. 좌, 우를 확인하고 갈 수 있으면 이동한다. (단, 좌, 우가 동시에 있는 경우는 문제에서 없다고 했으니 고려하지 않는다)
                int[] moveResult = move(curRow, curCol);
                curRow = moveResult[0];
                curCol = moveResult[1];
            }

            System.out.println("#" + test_case + " " + curCol);

        }

    }
}