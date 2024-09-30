package 삼성기출;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

// 7 0 0 1 0 0 5 0 0 0 4 40 0 0 0 8 0 0 0 0 0 0 0 8 0 0 0 0
// 7 0 0 1 2 0 5 0 0 0 4 80 0 0 0 8 0 0 0 0 0 0 0 8 0 0 0 0

/**
 * 삼성기출 2023년도 하반기 오전1번 왕실의 기사 대결
 *
 * [주의할 점]
 * 1. 좌측 상단의 좌표는 (1, 1)이다. - 인덱스로 하려면 1씩 줄여줘야한다.
 * 2. 마지막 출력의 결과는 기사들이 받은 총 데미지의 합이 아니라. 마지막애 "생존한" 기사들이 받은 총 데미지의 합이다.
 * 3. 명령을 받은 기사는 데미지를 입지 않는다. - 밀려난 기사들만 데미지를 입는다.
 * 4. 밀쳐진다고 해도, 밀쳐진 곳에 함정이 없다면 데미지를 입지 않는다.
 * 5. 연쇄적으로 밀쳐지는 과정 중 하나의 기사라도 맵 밖으로 이동하게 되거나, 벽을 만난다면 모든 기사들의 이동은 무효처리된다.
 * --------------------------------------------------------------------------------------
 * [문제 풀이 근거]
 * 1. 기사의 수가 30이다. 명령의 수 또한 100이다. -> 기사들의 관리는 맵에 굳이 찍지 않고 리스트로 관리해도 시간이 안터진다.
 * 2. 기사의 수가 많을 경우 맵에 찍어서 관리를 해줘야할 수도 있지만 안그래도 될거같다. - 만약 한다면 1번 기사 위치는 -1 2번 기사 위치는 -2 이런식으로 찍을 듯
 * 3. 기존 맵의 함정과 벽 위치만 맵에 찍고, 기사들은 리스트로 관리한다.
 * 4. 연쇄적 이동 -> 재귀로 구현할 수 있다. -> 파라미터를 현재 밀쳐진(혹은 명령을 받은) 기사로 하면 될듯
 * --------------------------------------------------------------------------------------
 * [알고리즘]
 * 1. 입력
 * 1-1. 체스판의 크기 L, 기사들의 수 N, 명령의 수 Q가 주어진다.
 * 1-2. L개의 줄에 걸쳐서 체스판의 정보가 주어진다. (0 빈칸, 1 함정, 2 벽)
 * 1-3. N개의 줄에 걸쳐서 초기 기사들의 정보가 주어진다. (r, c, h, w, k) (기사들의 정보는 번호순서대로 주어진다)
 * 1-3-1. (r, c) 기사의 좌측 상단 좌표 (1씩 빼줘야함)
 * 1-3-2. (h, w) 세로 길이 h 가로길이 w
 * 1-3-3. 초기 체력 k
 * 1-4. Q개의 줄에 걸쳐 명령이 주어진다.
 * 1-4-1. (i, d): i번 기사에게 d 방향으로 이동하라는 명령 (d = 0, 1, 2, 3 -> 위쪽, 오른쪽, 아래쪽, 왼쪽 (시계방향))
 *
 * 2. 시뮬레이션 진행
 * --------------------------------------------------------------------------------------
 * [기사 객체]
 * 1. knightNum: 기사의 번호
 * 2. knightLocaList: 기사가 차지하는 공간을 담은 위치 리스트
 * 3. isPushed
 * 4. isLive: 기사의 생존 여부를 나타냄 (5-6 >= 0 이면 사망)
 * 5. hp: 기사의 체력
 * 6. damageCnt: 받은 데미지
 *
 */

public class SCT4_2023_하_오전1_왕실의기사대결 {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    // 위쪽, 오른쪽, 아래쪽, 왼쪽
    static final int[] dRow = {-1, 0, 1, 0};
    static final int[] dCol = {0, 1, 0, -1};

    static final int NOTHING = 0;
    static final int TRAP = 1;
    static final int WALL = 2;

    static int sizeOfMap, numOfKnight, numOfOrder;
    static int[][] map;
    static ArrayList<Knight> KnightList;
    static ArrayList<int[]> OrderList;

    // 마지막 이동까지 성공했을 때만 이동을 시키기위한 임시 기사 리스트
    static ArrayList<Knight> tempKnightList;

    static int result;

    public static void main(String[] args) throws IOException {

        // 1. 입력
        br = new BufferedReader(new InputStreamReader(System.in));

        // 1-1. 체스판의 크기 L, 기사들의 수 N, 명령의 수 Q가 주어진다.
        st = new StringTokenizer(br.readLine().trim());

        sizeOfMap = Integer.parseInt(st.nextToken());
        numOfKnight = Integer.parseInt(st.nextToken());
        numOfOrder = Integer.parseInt(st.nextToken());

        // 1-2. L개의 줄에 걸쳐서 체스판의 정보가 주어진다. (0 빈칸, 1 함정, 2 벽)
        map = new int[sizeOfMap][sizeOfMap];

        for (int rowIdx = 0; rowIdx < sizeOfMap; rowIdx++) {
            st = new StringTokenizer(br.readLine().trim());

            for (int colIdx = 0; colIdx < sizeOfMap; colIdx++) {
                map[rowIdx][colIdx] = Integer.parseInt(st.nextToken());
            }
        }

        // 1-3. N개의 줄에 걸쳐서 초기 기사들의 정보가 주어진다. (r, c, h, w, k) (기사들의 정보는 번호순서대로 주어진다)
        // !!!! 나는 편의를 위해서 1번 나이트를 0번 나이트로 표시했다. -1씩
        KnightList = new ArrayList<>();

        for (int knightNum = 0; knightNum < numOfKnight; knightNum++) {
            st = new StringTokenizer(br.readLine().trim());

            // 1-3-1. (r, c) 기사의 좌측 상단 좌표 (1씩 빼줘야함)
            int kightLeftUpRowIdx = Integer.parseInt(st.nextToken()) - 1;
            int kightLeftUpColIdx = Integer.parseInt(st.nextToken()) - 1;

            // 1-3-2. (h, w) 세로 길이 h 가로길이 w
            int kightRowSize = Integer.parseInt(st.nextToken());
            int kightColSize = Integer.parseInt(st.nextToken());

            // 1-3-3. 초기 체력 k
            int hp = Integer.parseInt(st.nextToken());

            KnightList.add(new Knight(knightNum, kightLeftUpRowIdx, kightLeftUpColIdx, kightRowSize, kightColSize, hp));
        }

        // 1-4. Q개의 줄에 걸쳐 명령이 주어진다.
        // 1-4-1. (i, d): i번 기사에게 d 방향으로 이동하라는 명령 (d = 0, 1, 2, 3 -> 위쪽, 오른쪽, 아래쪽, 왼쪽 (시계방향))

        OrderList = new ArrayList<>();

        for (int orderIdx = 0; orderIdx < numOfOrder; orderIdx++) {
            st = new StringTokenizer(br.readLine().trim());

            int knightIdx = Integer.parseInt(st.nextToken()) - 1;
            int orderDr = Integer.parseInt(st.nextToken());

            OrderList.add(new int[]{knightIdx, orderDr});

        }

        // 2. 시뮬레이션 진행
        for (int orderIdx = 0; orderIdx < numOfOrder; orderIdx++) {

            int[] curOrder = OrderList.get(orderIdx);

            int targetKnightIdx = curOrder[0];
            int knightMoveDr = curOrder[1];

            // 이동이 가능 했을 경우만 이동시키기 위한 임시 기사 리스트
            tempKnightList = new ArrayList<>();

            for (int kightIdx = 0; kightIdx < numOfKnight; kightIdx++) {
                tempKnightList.add(KnightList.get(kightIdx).clone());
            }

            // 이동이 가능할 경우에만 이동 반영함
            if (chainMove(tempKnightList.get(targetKnightIdx), knightMoveDr)) {

                KnightList = tempKnightList;

                // 이동이 끝난 뒤 게임 로직 (체력 감소 및 여러가지) 반영
                for (Knight knight : KnightList) {

                    if (!knight.isPushed) {
                        continue;
                    }

                    if (!knight.isLive) {
                        continue;
                    }

                    for (int[] knightLoca : knight.knightLocaList) {
                        if (map[knightLoca[0]][knightLoca[1]] == TRAP) {
                            knight.damageCnt++;
                        }
                    }

                    if (knight.damageCnt >= knight.hp) {
                        knight.isLive = false;
                    }

                    knight.isPushed = false;
                }
            }
        }

        result = 0;

        for (Knight knight : KnightList) {

            if (!knight.isLive) {
                continue;
            }
            result += knight.damageCnt;
        }

        System.out.println(result);
    }

    static boolean chainMove(Knight curMoveKight, int dr) {

        if (!curMoveKight.isLive) {
            return false;
        }

        ArrayList<int[]> curMoveKightLocaList = curMoveKight.knightLocaList;

        curMoveKight.knightLeftUpRowIdx += dRow[dr];
        curMoveKight.knightLeftUpColIdx += dCol[dr];

        // 현재 움직이는 기사
        for (int[] kightLoca : curMoveKightLocaList) {

            kightLoca[0] += dRow[dr];
            kightLoca[1] += dCol[dr];

            if (isOut(kightLoca[0], kightLoca[1])) {
                return false;
            }

            if (map[kightLoca[0]][kightLoca[1]] == WALL) {
                return false;
            }

        }

        boolean isSuccess = true;

        // 영향을 받아 밀려나는 기사
        for (Knight targetKnight : tempKnightList) {

            if (!targetKnight.isLive) {
                continue;
            }

            // 자기 자신 x
            if (curMoveKight.knightNum == targetKnight.knightNum) {
                continue;
            }

            // 영향을 받는 기사인지 아닌지 판단
            boolean isTarget = false;

            for (int[] knightLoca : curMoveKightLocaList) {
                for (int[] targetKightLoca : targetKnight.knightLocaList) {
                    if (knightLoca[0] == targetKightLoca[0] && knightLoca[1] == targetKightLoca[1]) {
                        isTarget = true;
                        targetKnight.isPushed = true;
                    }
                }
            }

            if (isTarget) {
                isSuccess = isSuccess && chainMove(targetKnight, dr);
            }

        }
        return isSuccess;
    }

    static class Knight {

        int knightNum;
        ArrayList<int[]> knightLocaList;
        boolean isPushed;
        boolean isLive;
        int hp;
        int damageCnt;

        int knightLeftUpRowIdx;
        int knightLeftUpColIdx;
        int knightRowSize;
        int knightColSize;

        public Knight(int knightNum, int knightLeftUpRowIdx, int knightLeftUpColIdx, int knightRowSize, int knightColSize, int hp) {
            this.knightNum = knightNum;
            this.knightLeftUpRowIdx = knightLeftUpRowIdx;
            this.knightLeftUpColIdx = knightLeftUpColIdx;
            this.knightRowSize = knightRowSize;
            this.knightColSize = knightColSize;
            this.isPushed = false;
            this.isLive = true;
            this.hp = hp;
            this.damageCnt = 0;

            this.knightLocaList = new ArrayList<>();
            for (int knightRowIdx = 0; knightRowIdx < knightRowSize; knightRowIdx++) {
                for (int knightColIdx = 0; knightColIdx < knightColSize; knightColIdx++) {
                    knightLocaList.add(new int[]{knightLeftUpRowIdx + knightRowIdx, knightLeftUpColIdx + knightColIdx});
                }
            }
        }

        public Knight clone() {
            Knight cloneKight = new Knight(this.knightNum, this.knightLeftUpRowIdx, this.knightLeftUpColIdx, this.knightRowSize, this.knightColSize, this.hp);
            cloneKight.isPushed = this.isPushed;
            cloneKight.isLive = this.isLive;
            cloneKight.damageCnt = this.damageCnt;
            return cloneKight;
        }
    }

    static boolean isOut(int rowIdx, int colIdx) {
        return rowIdx < 0 || colIdx < 0 || rowIdx >= sizeOfMap || colIdx >= sizeOfMap;
    }
}
