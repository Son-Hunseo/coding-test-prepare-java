package 삼성기출;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 삼성기출 2023년도 하반기 오후1번 루돌프의 반란
 *
 * -------------- 특이사항 --------------
 * 주의) 좌측 상단의 위치는 (0, 0)이 아닌 (1, 1)이다.
 *
 * -문제 풀이 근거-
 * 1. 서로 다른 객체에 동적으로 영향을 받기 때문에 격자에 표시한다.
 * 2. 각 객체를 순회해야하기 때문에 리스트도 만들어서 관리한다. (그렇지 않으면 격자를 순회하면서 각 객체를 탐색해야한다.)
 * ----> 아.. 근데 이 문제는 산타의 수와 턴 수가 적기도 하고.. 그냥 리스트만 관리하는게 편할거같은데....
 * ----> 특정 객체에 번호가 매겨져있다면 리스트로 하는게 편한가?
 * -------------- 객체 --------------
 * 1. 산타 객체
 * 1-1. 산타의 번호, 산타의 위치, 산타의 점수, 산타의 상태(생존, 탈락), 기절 여부(2: 지금 턴에 기절당함, 1: 기절 풀릴 때 까지 1턴 남음, 0: 기절하지 않은 상태) 를 속성으로 가지는 객체
 * 1-2. 산타가 루돌프를 향해 움직이는 메서드
 * 1-2-1. 다른 산타가 있는 곳이나, 격자 밖으로는 움직일 수 없다.
 * 1-2-2. 움직일 수 있는 칸이 없다면 산타는 움직이지 않는다.
 * 1-2-3. 움직일 수 있는 칸이 있더라도 루돌프에게 가까워질 수 없다면 움직이지 않는다.
 * 1-2-4. 가까워질 수 있는 방향이 여러개라면 상우하좌 우선순위에 맞춰 움직인다.
 * 1-2-5. 루돌프와 충돌이 일어났을 경우 산타는 D만큼 점수를 얻고 자신이 이동해온 반대방향으로 D칸만큼 밀려남
 * 1-2-6. 밀려난 위치가 격자 밖이라면 해당 산타는 탈락
 * 1-2-7. 연쇄적인 상호작용 진행 (착지하게 되는 칸에 다른 산타가 있다면 1칸 해당 방향으로 밀려남)
 * 1-2-8. 연쇄적인 상호작용에서 격자 밖으로 밀려난 산타 또한 탈락
 *
 * 2. 루돌프 객체
 * 2-1. 루돌프의 위치를 속성으로 가지는 객체
 * 2-2. 루돌프가 산타를 향해 움직이는 메서드
 * 2-2-1. 탈락하지 않은 산타 중 가장 가까운 산타를 선택
 * 2-2-2. 가장 가까운 산타가 2명 이상이라면, 행 좌표가 큰 산타를 향해 돌진, 행이 동일할 경우, 열 좌표가 큰 산타를 향해 돌진
 * 2-2-3. 루돌프는 8방향 중 하나로 돌진할 수 있음. 가장 우선순위가 높은 산타를 향해 8방향 중 가장 가까워지는 방향으로 한 칸 돌진
 * 2-2-4. 산타와 충돌이 일어났을 경우 해당 산타는 C만큼 점수를 얻고 루돌프의 이동 방향으로 C칸만큼 밀려남
 * 2-2-5. 밀려난 위치가 격자 밖이라면 해당 산타는 탈락
 * 2-2-6. 연쇄적인 상호작용 진행 (착지하게 되는 칸에 다른 산타가 있다면 1칸 해당 방향으로 밀려남)
 * 2-2-7. 연쇄적인 상호작용에서 격자 밖으로 밀려난 산타 또한 탈락
 *
 * -------------- 메서드 --------------
 *
 * 1. 격자 밖인지 판단하는 메서드
 * 2. 거리 계산 메서드
 *
 * -------------- 알고리즘 --------------
 * 1. 입력
 * 1-1. 격자의 크기 N, 진행할 게임 턴 M, 산타의 수 P, 루돌프의 힘 C, 산타의 힘 D를 입력받는다.
 * 1-2. 루돌프의 초기 위치를 입력받는다. (단, 나는 좌상단을 0, 0으로 할 것이기 때문에 -1씩 해서 저장)
 * 1-3. P개의 줄에 걸쳐 산타의 번호, 산타의 초기 위치를 입력받는다.
 *
 * 2. 시뮬레이션 진행
 * 2-1. M 번의 턴에 걸쳐 루돌프, 산타가 순서대로 움직인 이후 게임이 종료됨
 * 2-2. P 명의 산타가 모두 게임에서 탈락하게 된다면 그 즉시 게임이 종료됨
 * 2-3. 매 턴 이후 아직 탈락하지 않은 산타들에게는 1점씩을 추가로 부여함
 *
 */

public class SCT3_2023_하_오후1_루돌프의반란 {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    // 상우하좌
    static int[] dRowSanta = {-1, 0, 1, 0};
    static int[] dColSanta = {0, 1, 0, -1};

    // 상, 우, 하, 좌, 우상, 우하, 좌하, 좌상
    static int[] dRowRudolph = {-1, 0, 1, 0, -1, 1, -1, 1};
    static int[] dColRudolph = {0, 1, 0, -1, 1, 1, -1, -1};

    static int sizeOfMap;
    static int numOfGameTurn;
    static int numOfSanta;
    static int powerOfRudolph;
    static int powerOfSanta;

    static ArrayList<Santa> santaList;

    static Rudolph rudolph;

    public static void main(String[] args) throws IOException {

        // 1. 입력
        br = new BufferedReader(new InputStreamReader(System.in));

        // 1-1. 격자의 크기 N, 진행할 게임 턴 M, 산타의 수 P, 루돌프의 힘 C, 산타의 힘 D를 입력받는다.
        st = new StringTokenizer(br.readLine().trim());
        sizeOfMap = Integer.parseInt(st.nextToken());
        numOfGameTurn = Integer.parseInt(st.nextToken());
        numOfSanta = Integer.parseInt(st.nextToken());
        powerOfRudolph = Integer.parseInt(st.nextToken());
        powerOfSanta = Integer.parseInt(st.nextToken());

        // 1-2. 루돌프의 초기 위치를 입력받는다. (단, 나는 좌상단을 0, 0으로 할 것이기 때문에 -1씩 해서 저장)
        st = new StringTokenizer(br.readLine().trim());
        int rudolphRow = Integer.parseInt(st.nextToken()) - 1;
        int rudolphCol = Integer.parseInt(st.nextToken()) - 1;
        rudolph = new Rudolph(rudolphRow, rudolphCol);

        // 1-3. P개의 줄에 걸쳐 산타의 번호, 산타의 초기 위치를 입력받는다. (단, 나는 좌상단을 0, 0으로 할 것이기 때문에 -1씩 해서 저장)
        santaList = new ArrayList<>();

        for (int cnt = 0; cnt < numOfSanta; cnt++) {
            st = new StringTokenizer(br.readLine().trim());

            int santaNum = Integer.parseInt(st.nextToken());
            int santaRow = Integer.parseInt(st.nextToken()) - 1;
            int santaCol = Integer.parseInt(st.nextToken()) - 1;

            santaList.add(new Santa(santaNum, santaRow, santaCol));
        }

        Collections.sort(santaList, new Comparator<Santa>() {
            @Override
            public int compare(Santa o1, Santa o2) {
                if (o1.santaNum < o2.santaNum) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });

        // 2. 시뮬레이션 진행
        // 2-1. M 번의 턴에 걸쳐 루돌프, 산타가 순서대로 움직인 이후 게임이 종료됨
        for (int turn = 0; turn < numOfGameTurn; turn++) {

            for (Santa santa : santaList) {
                if (santa.stunStatus != 0) {
                    santa.stunStatus--;
                }
            }

            rudolph.rudolphMove();

            boolean isAllSantaDie = true;

            // 산타를 번호순대로 움직이기 위함
            for (Santa santa : santaList) {
                if (santa.isLive) {
                    // 기절이 아닐경우 이동
                    // 기절일 경우 기절 턴 1턴 줄여줌
                    isAllSantaDie = false;
                    if (santa.stunStatus == 0) {
                        santa.santaMove();
                    }
                }
            }

            // 2-2. P 명의 산타가 모두 게임에서 탈락하게 된다면 그 즉시 게임이 종료됨
            if (isAllSantaDie) {
                break;
            }

            // 2-3. 매 턴 이후 아직 탈락하지 않은 산타들에게는 1점씩을 추가로 부여함
            for (Santa santa : santaList) {
                if (santa.isLive) {
                    santa.score++;
                }
            }
        }

        // 결과 출력
        for (Santa santa : santaList) {
            System.out.print(santa.score + " ");
        }
    }

    static class Santa {

        // 1-1. 산타의 번호, 산타의 위치, 산타의 점수, 산타의 상태(생존, 탈락), 기절 여부(2: 지금 턴에 기절당함, 1: 기절 풀릴 때 까지 1턴 남음, 0: 기절하지 않은 상태) 를 속성으로 가지는 객체
        int santaNum, curRow, curCol, score, stunStatus;
        boolean isLive;

        public Santa(int santaNum, int curRow, int curCol) {
            this.santaNum = santaNum;
            this.curRow = curRow;
            this.curCol = curCol;
            this.score = 0;
            this.isLive = true;
            this.stunStatus = 0;
        }

        // 1-2. 산타가 루돌프를 향해 움직이는 메서드
        public void santaMove() {

            int curDistanceToRudolph = getDistance(this.curRow, this.curCol, rudolph.curRow, rudolph.curCol);

            // 각 방향에 따른 루돌프와의 거리를 저장하는 배열 (MAX로 초기화)
            int[] distanceArr = new int[4];
            Arrays.fill(distanceArr, Integer.MAX_VALUE);

            for (int dr = 0; dr < 4; dr++) {

                int nextRow = this.curRow + dRowSanta[dr];
                int nextCol = this.curCol + dColSanta[dr];

                // 1-2-1. 다른 산타가 있는 곳이나, 격자 밖으로는 움직일 수 없다.
                if (isOut(nextRow, nextCol)) {
                    continue;
                }

                boolean isOtherSanta = false;

                for (Santa santa : santaList) {
                    // 탈락한 산타 제외
                    if (!santa.isLive) {
                        continue;
                    }

                    // 자기 자신 제외
                    if (santa.santaNum == this.santaNum) {
                        continue;
                    }

                    // 다른 산타가 있다면 못움직임
                    if (nextRow == santa.curRow && nextCol == santa.curCol) {
                        isOtherSanta = true;
                        break;
                    }
                }

                if (isOtherSanta) {
                    continue;
                }

                int nextLocaDistanceToRudolph = getDistance(nextRow, nextCol, rudolph.curRow, rudolph.curCol);

                // 1-2-3. 움직일 수 있는 칸이 있더라도 루돌프에게 가까워질 수 없다면 움직이지 않는다.
                if (nextLocaDistanceToRudolph >= curDistanceToRudolph) {
                    continue;
                }

                distanceArr[dr] = nextLocaDistanceToRudolph;
            }

            int minDistanceToRudolph = Integer.MAX_VALUE;
            int targetDr = -1;

            for (int dr = 0; dr < 4; dr++) {
                // 1-2-4. 가까워질 수 있는 방향이 여러개라면 상우하좌 우선순위에 맞춰 움직인다.
                // 배열 순서를 이미 상우하좌로 해놓았기 때문에 자동으로 우선순위가 선택됨
                if (distanceArr[dr] < minDistanceToRudolph) {
                    targetDr = dr;
                    minDistanceToRudolph = distanceArr[dr];
                }
            }

            // 1-2-2. 움직일 수 있는 칸이 없다면 산타는 움직이지 않는다.
            if (targetDr == -1) {
                return;
            }

            // 이동 진행
            this.curRow += dRowSanta[targetDr];
            this.curCol += dColSanta[targetDr];

            if (this.curRow == rudolph.curRow && this.curCol == rudolph.curCol) {

                // 1-2-5. 루돌프와 충돌이 일어났을 경우 산타는 D만큼 점수를 얻고 자신이 이동해온 반대방향으로 D칸만큼 밀려남
                this.score += powerOfSanta;

                this.curRow -= (powerOfSanta * dRowSanta[targetDr]);
                this.curCol -= (powerOfSanta * dColSanta[targetDr]);

                // 1-2-6. 밀려난 위치가 격자 밖이라면 해당 산타는 탈락
                if (isOut(curRow, curCol)) {
                    this.isLive = false;
                    return;
                }

                // 기절 처리
                this.stunStatus = 2;

                // 1-2-7. 연쇄적인 상호작용 진행 (착지하게 되는 칸에 다른 산타가 있다면 1칸 해당 방향으로 밀려남)
                // 1-2-8. 연쇄적인 상호작용에서 격자 밖으로 밀려난 산타 또한 탈락
                chainInteraction(targetDr, this, "SantaTurn");
            }

        }
    }

    static class Rudolph {

        // 2-1. 루돌프의 위치를 속성으로 가지는 객체
        int curRow;
        int curCol;

        public Rudolph(int curRow, int curCol) {
            this.curRow = curRow;
            this.curCol = curCol;
        }

        // 2-2. 루돌프가 산타를 향해 움직이는 메서드
        // 2-2-1. 탈락하지 않은 산타 중 가장 가까운 산타를 선택
        public void rudolphMove() {

            Santa targetSanta = null;
            int minDistance = Integer.MAX_VALUE;

            for (Santa santa: santaList) {

                if (!santa.isLive) {
                    continue;
                }

                int rudolphToSantaDistance = getDistance(this.curRow, this.curCol, santa.curRow, santa.curCol);

                if (rudolphToSantaDistance < minDistance) {
                    targetSanta = santa;
                    minDistance = rudolphToSantaDistance;
                } else if (rudolphToSantaDistance == minDistance) {
                    // 2-2-2. 가장 가까운 산타가 2명 이상이라면, 행 좌표가 큰 산타를 향해 돌진, 행이 동일할 경우, 열 좌표가 큰 산타를 향해 돌진
                    if (santa.curRow > targetSanta.curRow) {
                        targetSanta = santa;
                        minDistance = rudolphToSantaDistance;
                    } else if (santa.curRow == targetSanta.curRow) {
                        if (santa.curCol > targetSanta.curCol) {
                            targetSanta = santa;
                            minDistance = rudolphToSantaDistance;
                        }
                    }

                }

            }

            // 근데 이거 시험이었으면 못잡았을 듯
            if (targetSanta == null) {
                return;
            }

            int minDistanceDr = -1;
            int minDrDistance = Integer.MAX_VALUE;

            for (int dr = 0; dr < 8; dr++) {
                // 2-2-3. 루돌프는 8방향 중 하나로 돌진할 수 있음. 가장 우선순위가 높은 산타를 향해 8방향 중 가장 가까워지는 방향으로 한 칸 돌진
                int nextRow = this.curRow + dRowRudolph[dr];
                int nextCol = this.curCol + dColRudolph[dr];

                if (isOut(nextRow, nextCol)) {
                    continue;
                }

                int nextLocaRudolphToSantaDistance = getDistance(nextRow, nextCol, targetSanta.curRow, targetSanta.curCol);

                if (nextLocaRudolphToSantaDistance < minDrDistance) {
                    minDistanceDr = dr;
                    minDrDistance = nextLocaRudolphToSantaDistance;
                }
            }

            // 이동
            this.curRow += dRowRudolph[minDistanceDr];
            this.curCol += dColRudolph[minDistanceDr];

            boolean isCrash = false;
            Santa crashSanta = null;

            for (Santa santa : santaList) {

                if (this.curRow == santa.curRow && this.curCol == santa.curCol) {
                    isCrash = true;
                    crashSanta = santa;
                }
            }

            if (!isCrash) {
                return;
            }

            // 2-2-4. 산타와 충돌이 일어났을 경우 해당 산타는 C만큼 점수를 얻고 루돌프의 이동 방향으로 C칸만큼 밀려남
            crashSanta.score += powerOfRudolph;
            crashSanta.curRow += dRowRudolph[minDistanceDr] * powerOfRudolph;
            crashSanta.curCol += dColRudolph[minDistanceDr] * powerOfRudolph;

            // 2-2-5. 밀려난 위치가 격자 밖이라면 해당 산타는 탈락
            if (isOut(crashSanta.curRow, crashSanta.curCol)) {
                crashSanta.isLive = false;
                return;
            }

            // 기절 처리
            crashSanta.stunStatus = 2;

            // 2-2-6. 연쇄적인 상호작용 진행 (착지하게 되는 칸에 다른 산타가 있다면 1칸 해당 방향으로 밀려남)
            // 2-2-7. 연쇄적인 상호작용에서 격자 밖으로 밀려난 산타 또한 탈락
            chainInteraction(minDistanceDr, crashSanta, "RudolphTurn");
        }

    }

    static void chainInteraction(int pushDr, Santa curSanta, String type) {

        // 상호작용이 일어날지 판단하는 플래그
        boolean flag = false;
        Santa targetSanta = null;

        for (Santa santa : santaList) {

            // 탈락한 산타 제외
            if (!santa.isLive) {
                continue;
            }

            // 자기 자신 제외
            if (curSanta.santaNum == santa.santaNum) {
                continue;
            }

            if (curSanta.curRow == santa.curRow && curSanta.curCol == santa.curCol) {
                flag = true;
                targetSanta = santa;
            }

        }

        if (flag) {
            // 상우하좌 까지는 루돌프 방향이랑 같으니까 그냥 루돌프 방향 씀
            if (type.equals("SantaTurn")){
                targetSanta.curRow -= dRowRudolph[pushDr];
                targetSanta.curCol -= dColRudolph[pushDr];
            } else {
                targetSanta.curRow += dRowRudolph[pushDr];
                targetSanta.curCol += dColRudolph[pushDr];
            }

            if (isOut(targetSanta.curRow, targetSanta.curCol)) {
                targetSanta.isLive = false;
                return;
            }
            chainInteraction(pushDr, targetSanta, type);
        }
    }

    // 1. 격자 밖인지 판단하는 메서드
    static boolean isOut(int rowIdx, int colIdx) {
        return rowIdx < 0 || colIdx < 0 || rowIdx >= sizeOfMap || colIdx >= sizeOfMap;
    }

    // 2. 거리 계산 메서드
    static int getDistance(int myRowIdx, int myColIdx, int targetRowIdx, int targetColIdx) {
        return (int) (Math.pow((myRowIdx - targetRowIdx), 2) + Math.pow((myColIdx - targetColIdx), 2));
    }

    // 디버그
    static void debug() {
        for (Santa santa: santaList) {
            System.out.println("SantaNum: " + santa.santaNum + ", " + "SantaLoca: " + santa.curRow + ", " + santa.curCol + ", score: " + santa.score + ", stun: " + santa.stunStatus + ", Life: " + santa.isLive);
        }
        System.out.println("RudolphLoca: " + rudolph.curRow + ", " + rudolph.curCol);
    }
}
