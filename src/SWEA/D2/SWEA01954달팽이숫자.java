package SWEA.D2;

import javax.imageio.IIOException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/** SWEA 1954 달팽이 숫자
 *
 * 1. 테스트 케이스 숫자 T 를 입력받는다.
 * 2. 달팽이의 크기 (2차원 배열의 크기) N을 입력받는다.
 * 3. 0으로 채워진 NxN 크기의 2차원 배열을 생성한다. (달팽이를 그리기 위한 배열)
 * 4. 달팽이를 그린다.
 * 4-1. 달팽이는 방향이 정해져있으므로 델타 배열을 (우, 하, 좌, 상) 순서로 선언한다.
 * 4-1-1. 추가적으로, 이전 방향을 유지하면서 4방향을 순회해야하므로 이전 (이전방향 + drCnt) % 4 로 방향을 순회한다.
 * 4-2. 배열 밖으로 나가면 방향을 전환한다.
 * 4-3. 0이 아닌 숫자를 만나면 (이미 달팽이를 그린 부분) 방향을 전환한다.
 * 4-4. 이동한다.
 * 5. 출력
 */

public class SWEA01954달팽이숫자 {

    static BufferedReader br;

    // 4-1. 달팽이는 방향이 정해져있으므로 델타 배열을 (우, 하, 좌, 상) 순서로 선언한다.
    static int[] dRow = {0, 1, 0, -1};
    static int[] dCol = {1, 0, -1, 0};

    static int[][] snailArray;

    static void drawSnail(int N) {

        int curRow = 0;
        int curCol = 0;
        int befDir = 0;

        for (int snailCnt = 1; snailCnt <= N*N; snailCnt++) {

            snailArray[curRow][curCol] = snailCnt;

            for (int drCnt = 0; drCnt < 4; drCnt++) {

                int dr = (befDir+drCnt)%4;

                int nextRow = curRow + dRow[dr];
                int nextCol = curCol + dCol[dr];

                // 4-2. 배열 밖으로 나가면 방향을 전환한다.
                if (nextRow < 0 || nextRow >= N || nextCol < 0 || nextCol >= N) {
                    continue;
                }
                // 4-3. 0이 아닌 숫자를 만나면 (이미 달팽이를 그린 부분) 방향을 전환한다.
                if (snailArray[nextRow][nextCol] != 0) {
                    continue;
                }
                // 4-4. 이동한다.
                curRow = nextRow;
                curCol = nextCol;
                befDir = dr;
                break;
            }

        }

    }

    static void printResult(int test_case, int N) {

        System.out.println("#" + test_case);
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                System.out.print(snailArray[row][col] + " ");
            }
            System.out.println();
        }

    }

    public static void main(String[] args) throws IOException {

        // 1. 테스트 케이스 숫자 T 를 입력받는다.
        br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        // 2. 달팽이의 크기 (2차원 배열의 크기) N을 입력받는다.
        for (int test_case = 1; test_case <= T; test_case++) {
            int N = Integer.parseInt(br.readLine());

            // 3. 0으로 채워진 NxN 크기의 2차원 배열을 생성한다. (달팽이를 그리기 위한 배열)
            snailArray = new int[N][N];

            // 4. 달팽이를 그린다.
            drawSnail(N);

            // 5. 출력
            printResult(test_case, N);

        }

    }
}
