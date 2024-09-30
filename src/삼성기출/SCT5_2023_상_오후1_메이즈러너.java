package 삼성기출;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 삼성기출 2023년도 상반기 오후1번 메이즈러너
 *
 * [주의할 점]
 * - 좌상단은 (1, 1)임 인덱스로 처리하려면 1씩 빼줘야함
 * - 참가자가 출구에 도착하면 "즉시!!!" 탈출한다.
 * - 모든 참가자는 "동시에!!!" 이동한다.
 * - 한 칸에 2명 이상의 참가자기 있을 수 "있다!!"
 *
 * [특이사항]
 * - 현재 위치보다 출구에 더 가깝지 않은 상황이면 움직이지 않는다 -> 비슷한 개념 루돌프의 반란에서 재출재됨
 * - 최근 기출 눈여겨 볼 필요가 있을지도?
 * - 같은 조건일 때 우선 순위를 정하는 개념 계속 계속 반복되고 있다. -> Comparator, Comparable
 *
 * [풀이 근거]
 * - 참가자들이 번호로 구별되지 않는다. BUT!! 참가자는 한 공간에 2명이상 존재할 수 있으므로,
 * - 다른 동적인 객체의 영향을 받는다고 할 수 없다.
 * - 따라서 리스트로 관리
 * 
 * [풀이 로직]
 * 1. 입력
 * 1-1. 미로의 크기 N, 참가자의 수 M, 게임 시간 K 를 입력받는다.
 * 1-2. N개의 줄에 걸쳐서 미로에 대한 정보를 입력받는다. (0: 빈칸, 1 ~ 9: 벽의 내구도)
 * 1-3. 참가자의 좌표가 주어진다. (1씩 뺄 것!)
 * 1-4. 마지막에 출구의 좌표가 주어진다. (1씩 뺄 것!)
 *
 * 2. 시뮬레이션
 *
 * 2-1. 참가자의 이동
 * 2-1-1. 출구까지의 최단거리(행, 열의 절댓값의 합)가 가까운 방향으로 이동
 * 2-1-2. 현재 칸보다 최단거리가 가까운 칸으로 이동할 수 없을 경우 이동하지 않는다.
 * 2-1-3. 움직일 수 있는 칸이 2개 이상이라면 상하로 움직이는 것을 우선시한다.
 * 2-1-4. 모든 참가자가 탈출하면 그 즉시 게임 종료
 *
 * 2-2. 미로의 회전
 * 2-2-1. 직사각형의 우하단 -> (참가자/출구 중 큰 행의 좌표, 참가자/출구 중 큰 열의 좌표)
 * 2-2-2. 직사각형의 좌상단 -> (참가자/출구 중 작은 행의 좌표, 참가자/출구 중 작은 열의 좌표)
 * 추가) 정사각형으로 바꾸기!!!! (어짜피 좌상단일수록 우선순위를 얻으므로 아래처럼 로직을 짯다)
 * -> (가로 > 세로) -> 좌상단 행좌표를 줄일 수 있을 만큼 줄인다 -> 부족하면 우하단 행좌표를 늘릴 수 있을만큼 늘린다.
 * -> (가로 < 세로) -> 좌상단 열좌표를 줄일 수 있을 만큼 줄인다. -> 브적히먄 우하단 열좌표를 늘릴 수 있을만큼 늘린다.
 * 2-2-3. 위 규칙으로 각 참가자마다의 정사각형을 사각형 리스트에 저장함
 * 2-2-4. 해당 사각형들 중 가장 작은 크기 -> 좌상단 행이 작은것 -> 좌상단 열 좌표가 작은 것
 * 2-2-5. 선택된 사각형을 90도로 회전
 * 2-2-6. 회전한 부분 벽의 내구도 1씩 차감
 * 
 * 3. 출력
 * 3-1. 모든 참가자들의 이동 거리 합과 출구 좌표를 출력
 */

/**
 * ################# 디버깅 기록 #################
 * 1. 일단 회전하는데 너무 버벅인다. (반드시 외우자)
 * 2. 사람을 맵에 안찍고 리스트로 관리하는 만큼, 해당 사람이 포함된 격자를 돌릴 때, 해당 격자에 포함이 되는지, 돌렸을 때 어디 위치로 이동하는지를 유의해야한다.
 * 3. NPE 날 여지 주지 않기 마지막에 ArrayList.get(0) 에서 NPE가 낫다. 이런거 시험이라면 못잡을 수도 있다.
 */

public class SCT5_2023_상_오후1_메이즈러너 {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    // 상하좌우 -> 방향을 이런 순서로 설정함으로써 상하 우선순위 자동 적용
    static int[] dRow = {-1, 1, 0, 0};
    static int[] dCol = {0, 0, -1, 1};

    static int sizeOfMap;
    static int numOfPerson;
    static int gameTime;

    static int[][] map;
    static final int NOTHING = 0;
    static final int EXIT = -1;

    static Person[] personArr;
    static ArrayList<Square> squareList;

    static int sumOfmoveDistance;

    public static void main(String[] args) throws IOException {

        // 1. 입력
        br = new BufferedReader(new InputStreamReader(System.in));

        // 1-1. 미로의 크기 N, 참가자의 수 M, 게임 시간 K 를 입력받는다.
        st = new StringTokenizer(br.readLine().trim());

        sizeOfMap = Integer.parseInt(st.nextToken());
        numOfPerson = Integer.parseInt(st.nextToken());
        gameTime = Integer.parseInt(st.nextToken());

        // 1-2. N개의 줄에 걸쳐서 미로에 대한 정보를 입력받는다. (0: 빈칸, 1 ~ 9: 벽의 내구도)
        map = new int[sizeOfMap][sizeOfMap];

        for (int rowIdx = 0; rowIdx < sizeOfMap; rowIdx++) {

            st = new StringTokenizer(br.readLine().trim());

            for (int colIdx = 0; colIdx < sizeOfMap; colIdx++) {
                map[rowIdx][colIdx] = Integer.parseInt(st.nextToken());
            }
        }

        // 1-3. 참가자의 좌표가 주어진다. (1씩 뺄 것!)
        personArr = new Person[numOfPerson];

        for (int personNum = 0; personNum < numOfPerson; personNum++) {
            st = new StringTokenizer(br.readLine().trim());

            int rowIdx = Integer.parseInt(st.nextToken()) - 1;
            int colIdx = Integer.parseInt(st.nextToken()) - 1;

            personArr[personNum] = new Person(rowIdx, colIdx);
        }

        // 1-4. 마지막에 출구의 좌표가 주어진다. (1씩 뺄 것!)
        st = new StringTokenizer(br.readLine().trim());
        int exitRowIdx = Integer.parseInt(st.nextToken()) - 1;
        int exitColIdx = Integer.parseInt(st.nextToken()) - 1;

        map[exitRowIdx][exitColIdx] = EXIT;

        // ---------------------------------------------------------------
        // 2. 시뮬레이션

        sumOfmoveDistance = 0;

        for (int time = 1; time <= gameTime; time++) {

            boolean isALlEscape = true;

            // 참가자 이동
            for (Person person : personArr) {
                if (!person.isEscape) { // 탈출하지 않은 사람
                    person.move();
                    isALlEscape = false;
                }
            }

            // 2-1-4. 모든 참가자가 탈출하면 그 즉시 게임 종료
            if (isALlEscape) {
                break;
            }

            // 미로의 회전
            squareList = new ArrayList<>();
            int[] exitLoca = getExitLoca();

            for (Person person : personArr) {
                if (!person.isEscape) {
                    int[] leftUpLoca = getLeftUpLoca(person.rowIdx, person.colIdx, exitLoca[0], exitLoca[1]);
                    int[] rightDownLoca = getRightDownLoca(person.rowIdx, person.colIdx, exitLoca[0], exitLoca[1]);
                    // 2-2-3. 위 규칙으로 각 참가자마다의 사각형을 사각형 리스트에 저장함
                    squareList.add(new Square(leftUpLoca[0], leftUpLoca[1], rightDownLoca[0], rightDownLoca[1]));
                }
            }

            // 2-2-4. 해당 사각형들 중 가장 작은 크기 -> 좌상단 행이 작은것 -> 좌상단 열 좌표가 작은 것
            Collections.sort(squareList, new Comparator<Square>() {
                @Override
                public int compare(Square o1, Square o2) {
                    int o1Size = getSizeOfSquare(o1);
                    int o2Size = getSizeOfSquare(o2);

                    if (o1Size < o2Size) {
                        return -1;
                    } else if (o1Size > o2Size) {
                        return 0;
                    } else {
                        if (o1.leftUpRowIdx < o2.leftUpRowIdx) {
                            return -1;
                        } else if (o1.leftUpRowIdx > o2.leftUpRowIdx) {
                            return 1;
                        } else {
                            if (o1.leftUpColIdx < o2.leftUpColIdx) {
                                return -1;
                            } else if (o1.leftUpColIdx > o2.leftUpColIdx) {
                                return 1;
                            } else {
                                return 0;
                            }
                        }
                    }
                }
            });

            // 2-2-5. 선택된 정사각형을 90도로 회전
            if (squareList.size() != 0) {
                Square targetSquare = squareList.get(0);
                rotateSquareAndBreakWall(targetSquare);
            }
        }

        int[] exitLoca = getExitLoca();
        sb = new StringBuilder().append(sumOfmoveDistance).append("\n").append(exitLoca[0] + 1).append(" ").append(exitLoca[1] + 1);
        System.out.println(sb);
    }

    static void rotateSquareAndBreakWall(Square square) {

        int leftUpRowIdx = square.leftUpRowIdx;
        int leftUpColIdx = square.leftUpColIdx;
        int rightDownRowIdx = square.rightDownRowIdx;
        int rightDownColIdx = square.rightDownColIdx;

        int rowSize = rightDownRowIdx - leftUpRowIdx + 1;
        int colSize = rightDownColIdx - leftUpColIdx + 1;

        int[][] miniMap = new int[rowSize][colSize];

        for (int rowIdx = leftUpRowIdx; rowIdx <= rightDownRowIdx; rowIdx++) {
            for (int colIdx = leftUpColIdx; colIdx <= rightDownColIdx; colIdx++) {
                miniMap[rowIdx - leftUpRowIdx][colIdx - leftUpColIdx] = map[rowIdx][colIdx];
            }
        }

        // 시계 방향 회전
        int[][] newMiniMap = new int[rowSize][colSize];

        for (int rowIdx = 0; rowIdx < rowSize; rowIdx++) {
            for (int colIdx = 0; colIdx < colSize; colIdx++) {
                newMiniMap[colIdx][rowSize - 1 - rowIdx] = miniMap[rowIdx][colIdx];
            }
        }

        // 벽 1씩 감소
        for (int rowIdx = 0; rowIdx < rowSize; rowIdx++) {
            for (int colIdx = 0; colIdx < colSize; colIdx++) {
                if (newMiniMap[rowIdx][colIdx] > NOTHING) {
                    newMiniMap[rowIdx][colIdx]--;
                }
            }
        }

        // 반영
        for (int rowIdx = leftUpRowIdx; rowIdx <= rightDownRowIdx; rowIdx++) {
            for (int colIdx = leftUpColIdx; colIdx <= rightDownColIdx; colIdx++) {
                map[rowIdx][colIdx] = newMiniMap[rowIdx - leftUpRowIdx][colIdx - leftUpColIdx];
            }
        }

        // 범위 안에 있는 사람도 돌려야 함
        for (Person person : personArr) {
            if (person.rowIdx >= leftUpRowIdx && person.rowIdx <= rightDownRowIdx
                    && person.colIdx >= leftUpColIdx && person.colIdx <= rightDownColIdx) {
                int befRowIdx = person.rowIdx - leftUpRowIdx;
                int befColIdx = person.colIdx - leftUpColIdx;

                person.rowIdx = befColIdx + leftUpRowIdx;
                person.colIdx = rowSize - 1 - befRowIdx + leftUpColIdx;
            }
        }
    }

    static int getSizeOfSquare(Square square) {
        int leftUpRowIdx = square.leftUpRowIdx;
        int rightDownRowIdx = square.rightDownRowIdx;
        return rightDownRowIdx - leftUpRowIdx + 1;
    }

    static int[] getLeftUpLoca(int personRowIdx, int personColIdx, int exitRowIdx, int exitColIdx) {
        return new int[]{Math.min(personRowIdx, exitRowIdx), Math.min(personColIdx, exitColIdx)};
    }

    // 2-2-1. 사각형의 우하단 -> (참가자/출구 중 큰 행의 좌표, 참가자/출구 중 큰 열의 좌표)
    static int[] getRightDownLoca(int personRowIdx, int personColIdx, int exitRowIdx, int exitColIdx) {
        return new int[]{Math.max(personRowIdx, exitRowIdx), Math.max(personColIdx, exitColIdx)};
    }

    // 2-2-2. 사각형의 좌상단 -> (참가자/출구 중 작은 행의 좌표, 참가자/출구 중 작은 열의 좌표)
    static int getDistance(int myRowIdx, int myColIdx, int targetRowIdx, int targetColIdx) {
        return Math.abs(myRowIdx - targetRowIdx) + Math.abs(myColIdx - targetColIdx);
    }

    static int[] getExitLoca() {

        int exitRowIdx = 0;
        int exitColIdx = 0;

        for (int rowIdx = 0; rowIdx < sizeOfMap; rowIdx++) {
            for (int colIdx = 0; colIdx < sizeOfMap; colIdx++) {
                if (map[rowIdx][colIdx] == EXIT) {
                    exitRowIdx = rowIdx;
                    exitColIdx = colIdx;
                }
            }
        }
        return new int[]{exitRowIdx, exitColIdx};
    }

    static boolean isOut(int rowIdx, int colIdx) {
        return rowIdx < 0 || colIdx < 0 || rowIdx >= sizeOfMap || colIdx >= sizeOfMap;
    }

    static class Person {

        int rowIdx;
        int colIdx;
        boolean isEscape;

        public Person(int rowIdx, int colIdx) {
            this.rowIdx = rowIdx;
            this.colIdx = colIdx;
            this.isEscape = false;
        }

        // 2-1. 참가자의 이동
        public void move() {

            int[] exitLoca = getExitLoca();
            int curDistanceToExit = getDistance(rowIdx, colIdx, exitLoca[0], exitLoca[1]);

            // -1 은 가만히 있기
            int shortestDistanceDr = -1;
            int shortestDistance = curDistanceToExit;

            for (int dr = 0; dr < 4; dr++) {

                int nextRowIdx = rowIdx + dRow[dr];
                int nextColIdx = colIdx + dCol[dr];

                if (isOut(nextRowIdx, nextColIdx)) {
                    continue;
                }

                // 벽이면 갈 수 없음
                if (map[nextRowIdx][nextColIdx] > 0) {
                    continue;
                }

                int nextDistance = getDistance(nextRowIdx, nextColIdx, exitLoca[0], exitLoca[1]);

                if (nextDistance < shortestDistance) {
                    shortestDistanceDr = dr;
                    shortestDistance = nextDistance;
                }

            }

            // 2-1-1. 출구까지의 최단거리(행, 열의 절댓값의 합)가 가까운 방향으로 이동
            // 2-1-2. 현재 칸보다 최단거리가 가까운 칸으로 이동할 수 없을 경우 이동하지 않는다.
            if (shortestDistanceDr == -1) {
                return;
            }

            // 2-1-3. 움직일 수 있는 칸이 2개 이상이라면 상하로 움직이는 것을 우선시한다.
            // 이건 방향 정의 순서때문에 자동으로 적용됨

            this.rowIdx = rowIdx + dRow[shortestDistanceDr];
            this.colIdx = colIdx + dCol[shortestDistanceDr];
            sumOfmoveDistance++;

            // 만약 출구라면 상태 바꿔줌 (탈출)
            if (rowIdx == exitLoca[0] && colIdx == exitLoca[1]) {
                isEscape = true;
            }
        }
    }

    static class Square {

        int leftUpRowIdx;
        int leftUpColIdx;
        int rightDownRowIdx;
        int rightDownColIdx;

        public Square(int leftUpRowIdx, int leftUpColIdx, int rightDownRowIdx, int rightDownColIdx) {

            // 추가) 정사각형으로 바꾸기!!!! (어짜피 좌상단일수록 우선순위를 얻으므로 아래처럼 로직을 짯다)
            int rowSize = rightDownRowIdx - leftUpRowIdx;
            int colSize = rightDownColIdx - leftUpColIdx;

            // -> (가로 > 세로) -> 좌상단 행좌표를 줄일 수 있을 만큼 줄인다 -> 부족하면 우하단 행좌표를 늘릴 수 있을만큼 늘린다.
            if (colSize > rowSize) {
                int amount = colSize - rowSize;
                if (leftUpRowIdx >= amount) {
                    this.leftUpRowIdx = leftUpRowIdx - amount;
                    this.rightDownRowIdx = rightDownRowIdx;
                } else {
                    amount -= leftUpRowIdx;
                    this.leftUpRowIdx = 0;
                    this.rightDownRowIdx = rightDownRowIdx + amount;
                }
                this.leftUpColIdx = leftUpColIdx;
                this.rightDownColIdx = rightDownColIdx;
            }
            // -> (가로 < 세로) -> 좌상단 열좌표를 줄일 수 있을 만큼 줄인다. -> 브적히먄 우하단 열좌표를 늘릴 수 있을만큼 늘린다.
            else if (colSize < rowSize) {
                int amount = rowSize - colSize;
                if (leftUpColIdx >= amount) {
                    this.leftUpColIdx = leftUpColIdx - amount;
                    this.rightDownColIdx = rightDownColIdx;
                } else {
                    amount -= leftUpColIdx;
                    this.leftUpColIdx = 0;
                    this.rightDownColIdx = rightDownColIdx + amount;
                }
                this.leftUpRowIdx = leftUpRowIdx;
                this.rightDownRowIdx = rightDownRowIdx;
            }
            else {
                this.leftUpRowIdx = leftUpRowIdx;
                this.leftUpColIdx = leftUpColIdx;
                this.rightDownRowIdx = rightDownRowIdx;
                this.rightDownColIdx = rightDownColIdx;
            }
        }
    }
}
