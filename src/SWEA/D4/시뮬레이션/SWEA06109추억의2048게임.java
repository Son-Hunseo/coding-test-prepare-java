package SWEA.D4.시뮬레이션;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * SWEA 06109 추억의 2048게임
 *
 * 1. 테스트 케이스 개수 T를 입력받는다.
 * 2. 격자의 크기 N과 명령어 S를 입력받는다.
 * 3. 격자의 정보를 입력받는다.
 * 3-1. 이 격자에는 {값, 합쳐졌는지의 여부} 의 크기가 2인 배열이 들어간다. {0 합쳐지지 않음, 1 합쳐짐}
 * 4. 모든 칸의 수를 아래로 내리는 로직을 만든다.
 * 4-1. 주의!) 한번 합쳐진 칸은 다시 합쳐지지 않는다. 2 2 4 -> 4 4 0
 *
 * 5. 명령에 따른 로직을 수행해준다.
 * 5-1. 명령이 up일 경우 행을 반전시켜서 내리는 로직 수행 후 원복한다.
 * 5-2. 명령이 down일 경우 그냥 내리는 로직을 수행해준다.
 * 5-3. 명령이 left일 경우 원래 배열의 열을 반전시키고 전치한 후 내리는 로직 수행 후 원복한다.
 * 5-4. 명령이 right일 경우 원래 배열을 전치하고 열을 반전시키고 내리는 로직 수행 후 원복한다.
 *
 * 999. 명령을 수행한 결과를 출력한다.
 */
public class SWEA06109추억의2048게임 {

    // 입력
    static BufferedReader br;
    static StringTokenizer st;

    // 맵 정보
    static int mapSize;
    static int[][][] map;

    // 명령 정보
    static final String UP = "up";
    static final String DOWN = "down";
    static final String LEFT = "left";
    static final String RIGHT = "right";

    // 합쳐진 여부를 나타내는 상태 정보
    static final boolean MERGED = true;


    public static void main(String[] args) throws IOException {

        // 1. 테스트 케이스 개수 T를 입력받는다.
        br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {
            // 2. 격자의 크기 N과 명령어 S를 입력받는다.
            st = new StringTokenizer(br.readLine().trim());
            mapSize = Integer.parseInt(st.nextToken());
            String order = st.nextToken();

            map = new int[mapSize][mapSize][2];
            // 3. 격자의 정보를 입력받는다.
            for (int rowIdx = 0; rowIdx < mapSize; rowIdx++) {
                st = new StringTokenizer(br.readLine().trim());
                for (int colIdx = 0; colIdx < mapSize; colIdx++) {
                    //3-1. 이 격자에는 {값, 합쳐졌는지의 여부} 의 크기가 2인 배열이 들어간다. {0 합쳐지지 않음, 1 합쳐짐}
                    map[rowIdx][colIdx][0] = Integer.parseInt(st.nextToken());
                }
            }

            // 5. 명령에 따른 로직을 수행해준다.
            if (order.equals(UP)) { // 5-1. 명령이 up일 경우 행을 반전시켜서 내리는 로직 수행 후 원복한다.
                reverseRow();
                gravity();
                reverseRow();
            } else if (order.equals(DOWN)) { // 5-2. 명령이 down일 경우 그냥 내리는 로직을 수행해준다.
                gravity();
            } else if (order.equals(LEFT)) { // 5-3. 명령이 left일 경우 원래 배열의 열을 반전시키고 전치한 후 내리는 로직 수행 후 원복한다.
                reverseCol();
                Transpose();
                gravity();
                Transpose();
                reverseCol();
            } else if (order.equals(RIGHT)) { // 5-4. 명령이 right일 경우 원래 배열을 전치하고 열을 반전시키고 내리는 로직 수행 후 원복한다.
                Transpose();
                reverseCol();
                gravity();
                reverseCol();
                Transpose();
            }

            //999. 명령을 수행한 결과를 출력한다.
            System.out.println("#" + test_case);
            for (int rowIdx = 0; rowIdx < mapSize; rowIdx++) {
                for (int colIdx = 0; colIdx < mapSize; colIdx++) {
                    System.out.print(map[rowIdx][colIdx][0] + " ");
                }
                System.out.println();
            }

        }

    }

    // 4. 모든 칸의 수를 아래로 내리는 로직을 만든다.
    static void gravity() {

        for (int colIdx = 0; colIdx < mapSize; colIdx++) {
            // 맨 아래에서 한칸 위 행 부터
            for (int rowIdx = mapSize - 2; rowIdx >= 0; rowIdx--) {

                // 내 위치의 요소의 값이 0이면 넘어간다.
                if (map[rowIdx][colIdx][0] == 0) {
                    continue;
                }

                boolean flag = false; // 합쳐야할 지 여부를 알려주는 플래그
                int targetRowIdx = -1;

                for (int nextIdx = rowIdx + 1; nextIdx < mapSize; nextIdx++) {

                    // 다음 위치의 값이 0이면 다음 위치의 값을 올려준다. (그냥 진행한다는 뜻)
                    if (map[nextIdx][colIdx][0] == 0) {
                        // 다음 위치가 마지막 자리일 경우 그냥 그자리가 가야할 자리다.
                        if (nextIdx == mapSize - 1) {
                            targetRowIdx = nextIdx;
                            break;
                        }
                        continue;
                    }

                    // 다음 위치의 값이 현재 위치의 값과 다르다면 멈춘다. (이동해야할 위치는 다음 위치 -1 이다.)
                    if (map[nextIdx][colIdx][0] != map[rowIdx][colIdx][0]) {
                        targetRowIdx = nextIdx - 1;
                        break;
                    }

                    // 다음 위치의 값이 현재 위치의 값과 같다면,
                    else {
                        // 다음 위치가 합쳐진 적이 있다면 멈춘다. (이동해야할 위치는 다음 위치 -1 이다.)
                        if (map[nextIdx][colIdx][1] == 1) {
                            targetRowIdx = nextIdx - 1;
                            break;
                        } else { // 다음 위치가 합쳐진 적이 없다면 멈춘다. (합쳐주기 위해)
                            flag = true;
                            targetRowIdx = nextIdx;
                            break;
                        }
                    }
                }

                if (flag) { // 합칠 수 있다면
                    // 목표 위치의 요소를 {현재 값 2배, 1} 으로 바꿔주고
                    map[targetRowIdx][colIdx][0] = map[rowIdx][colIdx][0] * 2;
                    map[targetRowIdx][colIdx][1] = 1;

                    // 현재 위치의 요소를 (0, 0) 으로 바꾼다.
                    map[rowIdx][colIdx][0] = 0;
                    map[rowIdx][colIdx][1] = 0;
                } else { // 합치지 못한다면!
                    // 현재 위치와 같다면
                    if (targetRowIdx == rowIdx) {
                        continue;
                    }

                    // 목표 위치의 요소를 {내 위치의 값, 내 위치의 합쳐진 여부} 으로 바꿔주고
                    map[targetRowIdx][colIdx][0] = map[rowIdx][colIdx][0];
                    map[targetRowIdx][colIdx][1] = map[rowIdx][colIdx][1];

                    // 현재 위치의 요소를 (0, 0) 으로 바꾼다.
                    map[rowIdx][colIdx][0] = 0;
                    map[rowIdx][colIdx][1] = 0;
                }

            }
        }

    }

    // 행과 열을 반전시키는 로직 (전치)
    static void Transpose() {

        int[][][] tempArr = new int[mapSize][mapSize][2];

        // 행과 열의 위치를 바꿈
        for (int rowIdx = 0; rowIdx < mapSize; rowIdx++) {
            for (int colIdx = 0; colIdx < mapSize; colIdx++) {
                tempArr[colIdx][rowIdx] = map[rowIdx][colIdx];
            }
        }
        map = tempArr;
    }

    // 열의 순서를 바꾸는 로직
    static void reverseCol() {

        int[][][] tempArr = new int[mapSize][mapSize][2];

        // 열의 순서를 거꾸로 뒤집음
        for (int rowIdx = 0; rowIdx < mapSize; rowIdx++) {
            for (int colIdx = 0; colIdx < mapSize; colIdx++) {
                tempArr[rowIdx][colIdx] = map[rowIdx][mapSize - 1 - colIdx];
            }
        }
        map = tempArr;
    }

    // 행의 순서를 바꾸는 로직
    static void reverseRow() {

        int[][][] tempArr = new int[mapSize][mapSize][2];

        // 행의 순서를 거꾸로 뒤집음
        for (int colIdx = 0; colIdx < mapSize; colIdx++) {
            for (int rowIdx = 0; rowIdx < mapSize; rowIdx++) {
                tempArr[rowIdx][colIdx] = map[mapSize - 1 - rowIdx][colIdx];
            }
        }
        map = tempArr;
    }

}
