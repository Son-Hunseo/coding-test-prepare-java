package SWEA.모의A;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.StringTokenizer;

/**
 * SWEA 1953 탈주범 검거
 *
 * 1. 입력
 * 1-1. 테스트 케이스의 개수 T를 입력받는다.
 * 1-2. 맵의 열의 크기 N, 맵의 행의 크기 M, 시작 행 R, 시작 열 C, 경과된 시간 L을 입력받는다. (공백 포함)
 * 1-3. 맵의 정보를 입력받는다. (공백 포함)
 *
 * 2. BFS
 * 2-1. 맵과 똑같은 크기의 새로운 배열을 만든다.
 * 2-2. 기존 맵의 정보를 바탕으로 BFS안에서 진행할 수 있는지 없는지 판단하고 진행할 수 있으면 1을 표시한다.
 *
 * 3. 출력
 * 3-1. 새로운 배열을 순회하면서 1이 기록된 갯수를 센다.
 * 3-2. 결과를 출력한다.
 *
 */

public class SWEA01953탈주범검거 {

    static BufferedReader br;
    static StringTokenizer st;

    static int sizeOfRow;
    static int sizeOfCol;
    static int startRow;
    static int startCol;
    static int spentTime;

    static int[] dRow = {-1, 1, 0, 0};
    static int[] dCol = {0, 0, -1, 1};

    static int[][] map;
    static boolean[][] visited;
    static int[][] newMap;

    static final int DR_UP = 0;
    static final int DR_DOWN = 1;
    static final int DR_LEFT = 2;
    static final int DR_RIGHT = 3;

    static final int FOUR_WAY = 1;
    static final int UP_DOWN = 2;
    static final int LEFT_RIGHT = 3;
    static final int UP_RIGHT = 4;
    static final int DOWN_RIGHT = 5;
    static final int DOWN_LEFT = 6;
    static final int UP_LEFT = 7;

    public static void main(String[] args) throws IOException {
        // 1. 입력
        br = new BufferedReader(new InputStreamReader(System.in));

        //1-1. 테스트 케이스의 개수 T를 입력받는다.
        int T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {

            // 1-2. 맵의 열의 크기 N, 맵의 행의 크기 M, 시작 행 R, 시작 열 C, 경과된 시간 L을 입력받는다. (공백 포함)
            st = new StringTokenizer(br.readLine().trim());
            sizeOfRow = Integer.parseInt(st.nextToken());
            sizeOfCol = Integer.parseInt(st.nextToken());
            startRow = Integer.parseInt(st.nextToken());
            startCol = Integer.parseInt(st.nextToken());
            spentTime = Integer.parseInt(st.nextToken());

            map = new int[sizeOfRow][sizeOfCol];

            // 1-3. 맵의 정보를 입력받는다. (공백 포함)
            for (int rowIdx = 0; rowIdx < sizeOfRow; rowIdx++) {
                st = new StringTokenizer(br.readLine().trim());
                for (int colIdx = 0; colIdx < sizeOfCol; colIdx++) {
                    map[rowIdx][colIdx] = Integer.parseInt(st.nextToken());
                }
            }

            // 2. BFS
            // 2-1. 맵과 똑같은 크기의 새로운 배열을 만든다.
            newMap = new int[sizeOfRow][sizeOfCol];
            visited = new boolean[sizeOfRow][sizeOfCol];

            Deque<int[]> queue = new ArrayDeque<>();

            visited[startRow][startCol] = true;
            queue.offer(new int[]{startRow, startCol});

            int breath = 0;

            while (!queue.isEmpty()) {

                if (breath == spentTime) {
                    break;
                }

                int size = queue.size();

                while (size != 0) {

                    int[] cur = queue.poll();
                    int curRow = cur[0];
                    int curCol = cur[1];
                    // 2-2. 기존 맵의 정보를 바탕으로 BFS안에서 진행할 수 있는지 없는지 판단하고 진행할 수 있으면 1을 표시한다.
                    newMap[curRow][curCol] = 1;

                    for (int dr = 0; dr < 4; dr++) {
                        int nextRow = curRow + dRow[dr];
                        int nextCol = curCol + dCol[dr];

                        if (nextRow < 0 || nextCol < 0 || nextRow >= sizeOfRow || nextCol >= sizeOfCol) {
                            continue;
                        }

                        if (visited[nextRow][nextCol]) {
                            continue;
                        }

                        if (!isCanGo(map[curRow][curCol], map[nextRow][nextCol], dr)) {
                            continue;
                        }

                        visited[nextRow][nextCol] = true;
                        queue.offer(new int[]{nextRow, nextCol});
                    }

                    size--;
                }
                breath++;

            }

            // 3. 출력
            // 3-1. 새로운 배열을 순회하면서 1이 기록된 갯수를 센다.
            int result = 0;

            for (int rowIdx = 0; rowIdx < sizeOfRow; rowIdx++) {
                for (int colIdx = 0; colIdx <sizeOfCol; colIdx++) {
                    if (newMap[rowIdx][colIdx] == 1) {
                        result++;
                    }
                }
            }

            // 3-2. 결과를 출력한다.
            System.out.println("#" + test_case + " " + result);
        }
    }

    static boolean isCanGo(int curType, int nextType, int curDr) {

        if (curType == FOUR_WAY) {

            if (curDr == DR_UP) {
                return (nextType == FOUR_WAY || nextType == UP_DOWN || nextType == DOWN_RIGHT || nextType == DOWN_LEFT);
            } else if (curDr == DR_DOWN) {
                return (nextType == FOUR_WAY || nextType == UP_DOWN || nextType == UP_RIGHT || nextType == UP_LEFT);
            } else if (curDr == DR_LEFT) {
                return (nextType == FOUR_WAY || nextType == LEFT_RIGHT || nextType == UP_RIGHT || nextType == DOWN_RIGHT);
            } else if (curDr == DR_RIGHT) {
                return (nextType == FOUR_WAY || nextType == LEFT_RIGHT || nextType == DOWN_LEFT || nextType == UP_LEFT);
            }

        } else if (curType == UP_DOWN) {

            if (curDr == DR_UP) {
                return (nextType == FOUR_WAY || nextType == UP_DOWN || nextType == DOWN_RIGHT || nextType == DOWN_LEFT);
            } else if (curDr == DR_DOWN) {
                return (nextType == FOUR_WAY || nextType == UP_DOWN || nextType == UP_RIGHT || nextType == UP_LEFT);
            }

        } else if (curType == LEFT_RIGHT) {

            if (curDr == DR_LEFT) {
                return (nextType == FOUR_WAY || nextType == LEFT_RIGHT || nextType == UP_RIGHT || nextType == DOWN_RIGHT);
            } else if (curDr == DR_RIGHT) {
                return (nextType == FOUR_WAY || nextType == LEFT_RIGHT || nextType == DOWN_LEFT || nextType == UP_LEFT);
            }

        } else if (curType == UP_RIGHT) {

            if (curDr == DR_UP) {
                return (nextType == FOUR_WAY || nextType == UP_DOWN || nextType == DOWN_RIGHT || nextType == DOWN_LEFT);
            } else if (curDr == DR_RIGHT) {
                return (nextType == FOUR_WAY || nextType == LEFT_RIGHT || nextType == DOWN_LEFT || nextType == UP_LEFT);
            }

        } else if (curType == DOWN_RIGHT) {

            if (curDr == DR_DOWN) {
                return (nextType == FOUR_WAY || nextType == UP_DOWN || nextType == UP_RIGHT || nextType == UP_LEFT);
            } else if (curDr == DR_RIGHT) {
                return (nextType == FOUR_WAY || nextType == LEFT_RIGHT || nextType == DOWN_LEFT || nextType == UP_LEFT);
            }

        } else if (curType == DOWN_LEFT) {

            if (curDr == DR_DOWN) {
                return (nextType == FOUR_WAY || nextType == UP_DOWN || nextType == UP_RIGHT || nextType == UP_LEFT);
            } else if (curDr == DR_LEFT) {
                return (nextType == FOUR_WAY || nextType == LEFT_RIGHT || nextType == UP_RIGHT || nextType == DOWN_RIGHT);
            }

        } else if (curType == UP_LEFT) {

            if (curDr == DR_UP) {
                return (nextType == FOUR_WAY || nextType == UP_DOWN || nextType == DOWN_RIGHT || nextType == DOWN_LEFT);
            } else if (curDr == DR_LEFT) {
                return (nextType == FOUR_WAY || nextType == LEFT_RIGHT || nextType == UP_RIGHT || nextType == DOWN_RIGHT);
            }
        }

        return false;
    }

}
