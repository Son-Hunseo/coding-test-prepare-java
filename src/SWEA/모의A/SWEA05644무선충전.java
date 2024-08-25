package SWEA.모의A;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * SWEA 5644 무선 충전
 *
 * 1. 입력
 * 1-1. 테스트 케이스의 개수 T를 입력받는다.
 * 1-2. 이동 시간 (이동 횟수) M과 BC(Batter Charger)의 개수 A를 입력받는다.
 * 1-3. 사용자 A의 이동 정보를 입력 받는다.
 * 1-4. 사용자 B의 이동 정보를 입력 받는다.
 * 1-5. AP의 정보들을 A번 입력 받는다.
 *
 * 2. 시뮬레이션
 * 2-1. 2차원 배열 안에 어떤 BC의 영향을 받는지를 기록하여 10 x 10 x A 의 3차원 배열을 만든다.
 * 2-2. BC의 정보가 기록된 배열을 순회하면서, 3차원 배열에 어떤 BC의 영향을 받는지 기록한다. (0, 0, 1) 은 0열 0행에서 2번째 BC의 영향을 받는지의 여부 (0은 영향 안받음, 1은 영향받음)
 * 2-3. 각 초마다 (0초도 포함해야하는 것 유의) 현재 BC 영향을 고려하여 가장 많이 충전할 수 있는 경우의 수로 충전을 하고 다음 이동을 한다.
 * 2-4. 각 사용자가 선택할 수 있는 경우의 수 중에 각 사용자의 충전량 합이 가장 높은 경우의 수를 선택해 충전을 한다. (단, 같은 충전기에서 충전을 할 경우 효과 절반)
 * - 결국, 각 초마다의 최선의 선택을 하면 그것이 최적의 결과이다.
 *
 * 3. 결과를 출력한다.
 */

public class SWEA05644무선충전 {

    static BufferedReader br;
    static StringTokenizer st;

    // 델타 배열 (이동X, 상, 우, 하, 좌)
    static int[] dCol = {0, 0, 1, 0, -1};
    static int[] dRow = {0, -1, 0, 1, 0};

    static int[] userAmoveInfo;
    static int[] userBmoveInfo;
    static int[][] BCInfoArr;
    static int[][][] map;

    static int numOfTime;
    static int numOfBC;

    public static void main(String[] args) throws IOException {

        // 1. 입력
        br = new BufferedReader(new InputStreamReader(System.in));

        // 1-1. 테스트 케이스의 개수 T를 입력받는다.
        int T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {
            // 1-2. 이동 시간 (이동 횟수) M과 BC(Batter Charger)의 개수 A를 입력받는다.
            st = new StringTokenizer(br.readLine().trim());

            numOfTime = Integer.parseInt(st.nextToken());
            numOfBC = Integer.parseInt(st.nextToken());

            // 0초일 때를 처리해주기 위해 1개 크게 선언함
            userAmoveInfo = new int[numOfTime + 1];
            userBmoveInfo = new int[numOfTime + 1];

            // 1-3. 사용자 A의 이동 정보를 입력 받는다.
            st = new StringTokenizer(br.readLine().trim());

            // 0초일 떄를 고려하여 1크게 선언한 배열에 1인덱스부터 이동 정보를 집어넣음
            for (int userAmoveIdx = 1; userAmoveIdx < numOfTime + 1; userAmoveIdx++) {
                userAmoveInfo[userAmoveIdx] = Integer.parseInt(st.nextToken());
            }

            // 1-4. 사용자 B의 이동 정보를 입력 받는다.
            st = new StringTokenizer(br.readLine().trim());

            for (int userBmoveIdx = 1; userBmoveIdx < numOfTime + 1; userBmoveIdx++) {
                userBmoveInfo[userBmoveIdx] = Integer.parseInt(st.nextToken());
            }

            BCInfoArr = new int[numOfBC][4];

            // 1-5. AP의 정보들을 A번 입력 받는다.
            for (int BCIdx = 0; BCIdx < numOfBC; BCIdx++) {
                st = new StringTokenizer(br.readLine().trim());

                BCInfoArr[BCIdx][0] = Integer.parseInt(st.nextToken()) - 1; // 열 좌표
                BCInfoArr[BCIdx][1] = Integer.parseInt(st.nextToken()) - 1; // 행 좌표
                BCInfoArr[BCIdx][2] = Integer.parseInt(st.nextToken()); // 영향 범위
                BCInfoArr[BCIdx][3] = Integer.parseInt(st.nextToken()); // 충전 파워
            }

            // 2. 시뮬레이션
            // 2-1. 2차원 배열 안에 어떤 BC의 영향을 받는지를 기록하여 10 x 10 x A 의 3차원 배열을 만든다.
            map = new int[10][10][numOfBC];

            // 2-2. BC의 정보가 기록된 배열을 순회하면서, 3차원 배열에 어떤 BC의 영향을 받는지 기록한다. (0, 0, 1) 은 0열 0행에서 2번째 BC의 영향을 받는지의 여부 (0은 영향 안받음, 1은 영향받음)
            for (int BCidx = 0; BCidx < numOfBC; BCidx++) {
                mappingBC(BCidx, BCInfoArr[BCidx]);
            }

            // 2-3. 각 초마다 (0초도 포함해야하는 것 유의) 현재 BC 영향을 고려하여 가장 많이 충전할 수 있는 경우의 수로 충전을 하고 다음 이동을 한다.

            int result = 0;

            int[] curALoca = {0, 0};
            int[] curBLoca = {9, 9};

            for (int time = 0; time < numOfTime + 1; time++) {

                // 2-4. 각 사용자가 선택할 수 있는 경우의 수 중에 각 사용자의 충전량 합이 가장 높은 경우의 수를 선택해 충전을 한다. (단, 같은 충전기에서 충전을 할 경우 효과 절반)
                curALoca[0] += dCol[userAmoveInfo[time]];
                curALoca[1] += dRow[userAmoveInfo[time]];

                curBLoca[0] += dCol[userBmoveInfo[time]];
                curBLoca[1] += dRow[userBmoveInfo[time]];

                ArrayList<Integer> BC1Candi = new ArrayList<>();
                ArrayList<Integer> BC2Candi = new ArrayList<>();

                for (int BCIdx = 0; BCIdx < numOfBC; BCIdx++) {
                    // A를 충전할 수 있을 경우 BC1의 후보이다.
                    if (map[curALoca[0]][curALoca[1]][BCIdx] == 1) {
                        BC1Candi.add(BCIdx);
                    }
                    // B를 충전할 수 있을 경우 BC2의 후보이다.
                    if (map[curBLoca[0]][curBLoca[1]][BCIdx] == 1) {
                        BC2Candi.add(BCIdx);
                    }
                }

                int MAX = 0;

                // 둘다 아무 충전도 받지 않는 경우
                if (BC1Candi.isEmpty() && BC2Candi.isEmpty()) {
                    continue;
                } else if (BC1Candi.isEmpty()) { // A가 아무 충전도 받지 않는 경우
                    for (int BC2Idx : BC2Candi) {
                        MAX = Math.max(MAX, BCInfoArr[BC2Idx][3]);
                    }
                } else if (BC2Candi.isEmpty()) { // B가 아무 충전도 받지 않는 경우
                    for (int BC1Idx : BC1Candi) {
                        MAX = Math.max(MAX, BCInfoArr[BC1Idx][3]);
                    }
                } else { // 둘다 충전을 받는 경우
                    for (int BC1Idx : BC1Candi) {
                        for (int BC2Idx : BC2Candi) {
                            if (BC1Idx == BC2Idx) { // 같은 경우
                                MAX = Math.max(MAX, BCInfoArr[BC1Idx][3]);
                            } else { // 다른 경우
                                MAX = Math.max(MAX, BCInfoArr[BC1Idx][3] + BCInfoArr[BC2Idx][3]);
                            }
                        }
                    }
                }
                result += MAX;
            }

            System.out.println("#" + test_case + " " + result);
        }
    }

    static void mappingBC(int numOfBC, int[] BCinfo) {

        int targetCol = BCinfo[0];
        int targetRow = BCinfo[1];
        int powerSpan = BCinfo[2];

        // bfs를 이용해서 마름모 형태의 범위에 매핑
        Deque<int[]> deque = new LinkedList<>();
        deque.add(new int[]{targetCol, targetRow});

        while (!deque.isEmpty()) {

            int[] element = deque.pop();

            // 영향권 표시
            map[element[0]][element[1]][numOfBC] = 1;

            for (int dr = 1; dr < 5; dr++) {

                int nextCol = element[0] + dCol[dr];
                int nextRow = element[1] + dRow[dr];

                // 범위 밖이 면 안넣음
                if (nextCol < 0 || nextRow < 0 || nextCol > 9 || nextRow > 9) {
                    continue;
                }

                // 영향 범위 밖이면 안넣음
                if (Math.abs(targetCol - nextCol) + Math.abs(targetRow - nextRow) > powerSpan) {
                    continue;
                }

                // 해당 자리가 방문하지 않았다면
                if (map[nextCol][nextRow][numOfBC] == 0){
                    deque.add(new int[]{element[0] + dCol[dr], element[1] + dRow[dr]});
                }
            }
        }

    }

}
