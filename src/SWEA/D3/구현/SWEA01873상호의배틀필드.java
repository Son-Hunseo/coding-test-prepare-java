package SWEA.D3.구현;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * SWEA 1873 상호의 배틀필드
 *
 * [맵]
 * '.' : 평지 (포탄과 전차 모두 지나갈 수 있다.)
 * '*', '#' : 벽돌 벽, 강철 벽 (벽돌 벽은 포탄으로 부숴지지만, 강철 벽은 부숴지지 않는다.)
 * '-' : 물 (포탄은 지나갈 수 있지만, 전차는 지나갈 수 없다.)
 * '^', 'v', '<', '>' : 전차 (전차 아래는 당연히 평지이다.)
 *
 * [명령]
 * U : 전차가 보는 방향을 위쪽으로 바꾸고, 한 칸 위의 칸이 평지라면 그 칸으로 이동한다.
 * D : 전차가 보는 방향을 아래쪽으로 바꾸고, 한 칸 아래의 칸이 평지라면 그 칸으로 이동한다.
 * L : 전차가 보는 방향을 왼쪽으로 바꾸고, 한 칸 왼쪽의 칸이 평지라면 그 칸으로 이동한다.
 * R : 전차가 보는 방향을 오른쪽으로 바꾸고, 한 칸 오른쪽의 칸이 평지라면 그 칸으로 이동한다.
 * S : 전차가 현재 바라보는 방향으로 포탄을 발사한다.
 *
 * 1. 입력받기.
 * 1-1. 테스트 케이스의 수 T를 입력받는다.
 * 1-2. 게임 맵의 높이 H, 게임 맵의 너비 W를 입력받는다. (공백 O)
 * 1-3. 맵의 정보를 '공백 없이' 입력받는다.
 * 1-4. 명령의 개수 N을 입력받는다.
 * 1-5. 명령을 '공백 없이' 입력받는다.
 * 2. 명령 수행하기
 * 2-1. 명령이 UDLR 중 하나인 명령이라면 명령에 따라 전차를 이동시킨다. (전차는 평지 '만' 지나갈 수 있으며, 다음 방향이 지나갈 수 없는 자리라고 하더라도 방향 전환을 먼저 하기 때문에, 방향은 전환한다)
 * 2-2. 명령이 S라면 기본적으로 전차의 위치와 같게 저장된 포탄의 위치를 전차가 바라보는 방향으로 발사한다. (벽돌로 만들어진 벽을 부술 수 있는 점에 유의)
 * 3. 모든 명령을 수행한 후의 맵을 출력한다.
 *
 */

public class SWEA01873상호의배틀필드 {

    static BufferedReader br;
    static StringTokenizer st;

    // 맵의 상태 정의
    static final char LAND = '.';
    static final char BRICKWALL = '*';
    static final char IRONWALL = '#';
    static final char WATER = '-';
    static final char UPTANK = '^';
    static final char DOWNTANK = 'v';
    static final char LEFTTANK = '<';
    static final char RIGHTTANK = '>';

    // 델타 방향 정의 (상, 하, 좌 우) 순서
    static final int[] dRow = {-1, 1, 0, 0};
    static final int[] dCol = {0, 0, -1, 1};

    static final int UP = 0;
    static final int DOWN = 1;
    static final int LEFT = 2;
    static final int RIGHT = 3;

    // 명령 정의
    static final char SHOOTORDER = 'S';
    static final char UPORDER = 'U';
    static final char DOWNORDER = 'D';
    static final char LEFTORDER = 'L';
    static final char RIGHTORDER = 'R';


    // 게임 수행에 필요한 변수들
    static int sizeOfRow;
    static int sizeOfCol;
    static int numOfOrder;

    static char[][] gameMap;
    static char[] orderArr;
    static int[] tankLoca;
    static int tankStatus;
    static int[] bombLoca;


    public static void main(String[] args) throws IOException {

        // 1. 입력받기.
        br = new BufferedReader(new InputStreamReader(System.in));

        // 1-1. 테스트 케이스의 수 T를 입력받는다.
        int T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {

            // 1-2. 게임 맵의 높이 H, 게임 맵의 너비 W를 입력받는다. (공백 O)
            st = new StringTokenizer(br.readLine().trim());
            sizeOfRow = Integer.parseInt(st.nextToken());
            sizeOfCol = Integer.parseInt(st.nextToken());
            gameMap = new char[sizeOfRow][sizeOfCol];

            tankLoca = new int[2];
            bombLoca = new int[2];

            // 1-3. 맵의 정보를 '공백 없이' 입력받는다.
            for (int rowIdx = 0; rowIdx < sizeOfRow; rowIdx++) {

                String curRow = br.readLine();

                for (int colIdx = 0; colIdx < sizeOfCol; colIdx++) {
                    char field = curRow.charAt(colIdx);
                    gameMap[rowIdx][colIdx] = field;

                    if (field == UPTANK) {
                        tankLoca[0] = rowIdx;
                        tankLoca[1] = colIdx;
                        tankStatus = UP;
                    } else if (field == DOWNTANK) {
                        tankLoca[0] = rowIdx;
                        tankLoca[1] = colIdx;
                        tankStatus = DOWN;
                    } else if (field == LEFTTANK) {
                        tankLoca[0] = rowIdx;
                        tankLoca[1] = colIdx;
                        tankStatus = LEFT;
                    } else if (field == RIGHTTANK) {
                        tankLoca[0] = rowIdx;
                        tankLoca[1] = colIdx;
                        tankStatus = RIGHT;
                    }
                }
            }

            // 1-4. 명령의 개수 N을 입력받는다.
            numOfOrder = Integer.parseInt(br.readLine());
            orderArr = new char[numOfOrder];

            // 1-5. 명령을 '공백 없이' 입력받는다.
            String orderString = br.readLine();
            for (int orderIdx = 0; orderIdx < numOfOrder; orderIdx++) {
                orderArr[orderIdx] = orderString.charAt(orderIdx);
            }

            // 2. 명령 수행하기
            playGame();

            // 3. 모든 명령을 수행한 후의 맵을 출력한다.
            System.out.print("#" + test_case + " ");
            for (char[] row : gameMap) {
                for (char element : row) {
                    System.out.print(element);
                }
                System.out.println();
            }
        }

    }

    // 게임 수행하기
    static void playGame() {

        for (char order : orderArr) {

            if (order == SHOOTORDER) {
                shoot();
            } else {
                moveTank(order);
            }
        }
    }

    // 2-1. 명령이 UDLR 중 하나인 명령이라면 명령에 따라 전차를 이동시킨다. (전차는 평지 '만' 지나갈 수 있으며, 다음 방향이 지나갈 수 없는 자리라고 하더라도 방향 전환을 먼저 하기 때문에, 방향은 전환한다)
    static void moveTank(char order) {

        // 일단 방향 돌리기
        if (order == UPORDER) {
            tankStatus = UP;
            gameMap[tankLoca[0]][tankLoca[1]] = UPTANK;
        } else if (order == DOWNORDER) {
            tankStatus = DOWN;
            gameMap[tankLoca[0]][tankLoca[1]] = DOWNTANK;
        } else if (order == LEFTORDER) {
            tankStatus = LEFT;
            gameMap[tankLoca[0]][tankLoca[1]] = LEFTTANK;
        } else if (order == RIGHTORDER) {
            tankStatus = RIGHT;
            gameMap[tankLoca[0]][tankLoca[1]] = RIGHTTANK;
        }

        // 바라보고 있는 방향의 다음 칸이 평지라면 이동하고 평지가 아니라면 가만히
        int[] nextLoca = {tankLoca[0] + dRow[tankStatus], tankLoca[1] + dCol[tankStatus]};

        if (0 <= nextLoca[0] && nextLoca[0] < sizeOfRow &&
                0 <= nextLoca[1] && nextLoca[1] < sizeOfCol &&
                gameMap[nextLoca[0]][nextLoca[1]] == LAND) {
            // 다음 위치를 탱크로 바꾸고
            gameMap[nextLoca[0]][nextLoca[1]] = gameMap[tankLoca[0]][tankLoca[1]];

            // 이전 위치를 평지로 바꾸고
            gameMap[tankLoca[0]][tankLoca[1]] = LAND;

            // 탱크 위치 업데이트
            tankLoca[0] = nextLoca[0];
            tankLoca[1] = nextLoca[1];
        }

    }

    // 2-2. 명령이 S라면 기본적으로 전차의 위치와 같게 저장된 포탄의 위치를 전차가 바라보는 방향으로 발사한다. (벽돌로 만들어진 벽을 부술 수 있는 점에 유의)
    static void shoot() {

        // 초기 포탄 위치는 탱크 위치와 같다.
        bombLoca[0] = tankLoca[0];
        bombLoca[1] = tankLoca[1];

        while (true) {
            // 포탄 위치 이동
            bombLoca[0] += dRow[tankStatus];
            bombLoca[1] += dCol[tankStatus];

            // 포탄은 맵 밖으로 나가면 사라진다.
            if (bombLoca[0] < 0 || bombLoca[1] < 0 || bombLoca[0] >= sizeOfRow || bombLoca[1] >= sizeOfCol) {
                // 포탄 위치 초기화 시키고
                bombLoca[0] = tankLoca[0];
                bombLoca[1] = tankLoca[1];
                // 포탄은 사라진다.
                break;
            }

            // 포탄이 강철벽에 만난다면
            if (gameMap[bombLoca[0]][bombLoca[1]] == IRONWALL) {
                // 포탄 위치 초기화 시키고
                bombLoca[0] = tankLoca[0];
                bombLoca[1] = tankLoca[1];
                // 포탄은 사라진다.
                break;
            }

            // 포탄이 벽돌벽을 만난다면 벽을 부수고 포탄은 사라진다.
            if (gameMap[bombLoca[0]][bombLoca[1]] == BRICKWALL) {
                // 벽을 부숴서 평지로 만듬
                gameMap[bombLoca[0]][bombLoca[1]] = LAND;

                // 포탄 위치 초기화 시키고
                bombLoca[0] = tankLoca[0];
                bombLoca[1] = tankLoca[1];
                // 포탄은 사라진다.
                break;
            }
        }

    }
}
