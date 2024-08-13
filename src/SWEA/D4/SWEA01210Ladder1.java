package SWEA.D4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * SWEA 1210 - Ladder 1
 *
 * 월말 평가때 쳤던 숫자를 달팽이 모양으로 찍는 것이 역순으로하면 구현이 훨씬 쉬웠다.
 * 이에 아이디어를 얻었다.
 * 결국에 사다리타기란, 위에서 내려오는 시작점과 아래의 도착점이 1 대 1 대응이 되기 때문에,
 * 특정 도착점에 도착하는 시작점을 알기 위해서는 해당 도착점에서 역순으로 올라가면 찾을 수 있다.
 *
 * [알고리즘]
 * 1. 테스트 케이스의 번호 T 와 사다리를 나타내는 100 x 100 배열을 입력받는다.
 * 2. 배열의 100번째 row를 순회하면서 목표 도착점을 찾는다. (2가 있는 곳)
 * 3. 해당 목표 도착점에서 역순으로 위로 올라가면선 출발점을 찾는다.
 * 3-1. 좌, 우를 먼저 고려하며, 아래로 내려가는 경우는 없으므로 델타 방향을 [좌, 우, 위] 이렇게 하면 된다.
 * 3-2. 좌, 우를 확인하고 갈 수 있으면 이동한다. (단, 좌, 우가 동시에 있는 경우는 문제에서 없다고 했으니 고려하지 않는다)
 * 4. 도착한 출발점의 col을 반환한다.
 *
 */

public class SWEA01210Ladder1 {

    static BufferedReader br;
    static StringTokenizer st;

    public static void main(String[] args) throws IOException {

        // 총 10개의 테스트 케이스가 주어진다.
        for (int test_case = 1; test_case <= 10; test_case++) {

            // 보통 위의 test_case 변수를 사용하는데 굳이 주니까 그냥 일단 입력 받는다.
            br = new BufferedReader(new InputStreamReader(System.in));
            int TC = Integer.parseInt(br.readLine());

            // 1. 테스트 케이스의 번호 T 와 사다리를 나타내는 100 x 100 배열을 입력받는다.
            int[][] ladderMap = new int[100][100];
            int targetBottomCol = -1;

            for (int row = 0; row < 100; row++) {
                st = new StringTokenizer(br.readLine());
                for (int col = 0; col < 100; col++) {
                    int isLadder = Integer.parseInt(st.nextToken());
                    // 2. 배열의 100번째 row를 순회하면서 목표 도착점을 찾는다. (2가 있는 곳)
                    if (isLadder == 2) {
                        targetBottomCol = col;
                    }
                    ladderMap[row][col] = isLadder;
                }

            }

            // 3. 해당 목표 도착점에서 역순으로 위로 올라가면선 출발점을 찾는다.
            // 3-1. 좌, 우를 먼저 고려하며, 아래로 내려가는 경우는 없으므로 델타 방향을 [좌, 우, 위] 이렇게 하면 된다.
            int[] dRow = {0, 0, -1};
            int[] dCol = {-1, 1, 0};

            int curRow = 100;
            int curCol = targetBottomCol;

            while (curRow != 0) {
                // 3-2. 좌, 우를 확인하고 갈 수 있으면 이동한다. (단, 좌, 우가 동시에 있는 경우는 문제에서 없다고 했으니 고려하지 않는다)
                for (int dr = 0; dr < 3; dr++) {
                    // 0 이면 이동하지 못한다.
                    if (ladderMap[curRow + dRow[dr]][curCol + dCol[dr]] == 0) {
                        continue;
                    }

                    // 벽이어도 이동하지 못한다.
                    if (curRow + dRow[dr] < 0 || curRow + dRow[dr] >= 100 || curCol + dCol[dr] < 0 || curCol + dCol[dr] >= 100) {
                        continue;
                    }

                    // 그것이 아니라면 해당 방향으로 이동한다.
                    curRow = curRow + dRow[dr];
                    curCol = curCol + dCol[dr];
                    break;
                }

            }

            System.out.println("#" + test_case + " " + curCol);

        }
    }
}