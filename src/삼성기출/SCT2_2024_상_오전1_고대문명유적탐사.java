package 삼성기출;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 삼성기출 2024년도 상반기 오전1번 고대문명 유적탐사
 *
 * 1. 입력
 * 1-1. 탐사의 반복횟수 K와 벽면에 적힌 유물 조각의 개수 M을 공백을 사이에 두고 입력받는다.
 * 1-2. 5개의 줄(고정)에 걸쳐 유물의 각 행에 있는 유물 조각에 적혀 있는 숫자들을 공백을 사이에 두고 입력받는다.
 * 1-3. 벽면에 적힌 M개의 유물 조각 번호를 공백을 사이에 두고 입력받는다.
 *
 * 2. 탐사진행
 * 2-1. (인덱스 기준) 행 인덱스 1 ~ 3, 열 인덱스 1 ~ 3을 기준으로 90도, 180도, 270도 돌렸을 때의 유물 1차가치를 회전 방법 배열에 저장한다.
 * 2-2-1. (우선순위) 1. 유물 1차 획득가치 최대 / 2. 회전 각도 가장 작은 / 3. 회전 중심 좌표의 열이 가장 작은 구간 / 4. 회전 중심 좌표의 행이 가장 작은 구간
 * 2-2-2. 우선순위대로 정렬하여 가장 앞에있는 방법을 선택한다.
 * 2-3. 유물 획득을 진행한다.
 * 2-4. 탐사 진행 과정에서 어떠한 방법을 사용하더라도 유물을 획득할 수 없다면, 탐사는 종료됨
 * 2-5. 각 턴마다 획득한 유물의 가치의 총 합을 결과 리스트에 저장한다.
 * - 해당 탐사에서 어떠한 유물도 존재하지 않는다면 아무 값도 출력하지 않는다.
 * - 단, 초기에 주어지는 유적지에서는 탐사 진행 이전에 유물이 발견되지 않으며, 첫 번째 턴에서 탐사를 진행한 이후에는 항상 유물이 발견됨을 가정해도 좋다.
 *
 * 3. 유물 획득 (방법 선택 과정)
 * 3-1. 주어진 위치에서 회전하면서, BFS로 현재 위치의 유물 가치와 같은 유물이 3개이상 연결된다면 이들의 가치를 합하여 리턴한다.
 *
 * 4. 유물 획득 (방법 이미 선택된 후 진짜 유물 획득 진행)
 * 4-1. 정해진 방법에 맞게 맵을 회전하고, 유물을 획득한다.
 * 4-2. 벽에 적힌 물은 1. 열번호가 작은 순, 2. 행번호가 큰 순으로 채워짐
 * 4-3. 연쇄적으로 얻을 유물이 있을 경우, 유물을 더이상 획들할 수 없을 때 까지 반복한다.
 *
 * 5. 출력
 * 5-1. 결과 리스트를 출력한다.
 *
 */

public class SCT2_2024_상_오전1_고대문명유적탐사 {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int numOfExplore;
    static int numOfWallTreasure;
    static Deque<Integer> wallTreasureQueue;
    static int[][] treasureMap;

    static final int NINETY_DEGREE = 1;
    static final int H_EIGHTY_DEGREE = 2;
    static final int TH_SEVENTY_DEGREE = 3;

    static Method[] methodArr;

    // 상하좌우
    static final int[] dRow = {-1, 1, 0, 0};
    static final int[] dCol = {0, 0, -1, 1};



    public static void main(String[] args) throws IOException {

        // 1. 입력
        br = new BufferedReader(new InputStreamReader(System.in));

        // 1-1. 탐사의 반복횟수 K와 벽면에 적힌 유물 조각의 개수 M을 공백을 사이에 두고 입력받는다.
        st = new StringTokenizer(br.readLine().trim());

        numOfExplore = Integer.parseInt(st.nextToken());
        numOfWallTreasure = Integer.parseInt(st.nextToken());

        // 1-2. 5개의 줄(고정)에 걸쳐 유물의 각 행에 있는 유물 조각에 적혀 있는 숫자들을 공백을 사이에 두고 입력받는다.
        treasureMap = new int[5][5];

        for (int rowIdx = 0; rowIdx < 5; rowIdx++) {
            st = new StringTokenizer(br.readLine().trim());

            for (int colIdx = 0; colIdx < 5; colIdx++) {
                treasureMap[rowIdx][colIdx] = Integer.parseInt(st.nextToken());
            }
        }

        // 1-3. 벽면에 적힌 M개의 유물 조각 번호를 공백을 사이에 두고 입력받는다.
        wallTreasureQueue = new ArrayDeque<>();

        st = new StringTokenizer(br.readLine().trim());

        for (int wallTreasureIdx = 0; wallTreasureIdx < numOfWallTreasure; wallTreasureIdx++) {
            wallTreasureQueue.offer(Integer.parseInt(st.nextToken()));
        }

        ArrayList<Integer> resultList = new ArrayList<>();

        for (int turn = 0; turn < numOfExplore; turn++) {

            // 2. 탐사진행
            // 2-1. (인덱스 기준) 행 인덱스 1 ~ 3, 열 인덱스 1 ~ 3을 기준으로 90도, 180도, 270도 돌렸을 때의 유물 1차가치를 회전 방법 배열에 저장한다.
            methodArr = new Method[27];
            int methodArrIdx = 0;

            for (int rowIdx = 1; rowIdx <= 3; rowIdx++) {
                for (int colIdx = 1; colIdx <= 3; colIdx++) {
                    for (int degree = NINETY_DEGREE; degree <= TH_SEVENTY_DEGREE; degree++) {
                        methodArr[methodArrIdx] = getMethod(rowIdx, colIdx, degree);
                        methodArrIdx++;
                    }
                }
            }

            // 2-2-2. 우선순위대로 정렬하여 가장 앞에있는 방법을 선택한다.
            Arrays.sort(methodArr);
            Method targetMethod = methodArr[0];

            // 2-3. 유물 획득을 진행한다.
            int curTurnTreasureValue = rotateMapAndgetTreasure(targetMethod);
            // 2-4. 탐사 진행 과정에서 어떠한 방법을 사용하더라도 유물을 획득할 수 없다면, 탐사는 종료됨
            if (curTurnTreasureValue == 0) {
                break;
            }
            // 2-5. 각 턴마다 획득한 유물의 가치의 총 합을 결과 리스트에 저장한다.
            resultList.add(curTurnTreasureValue);
        }

        // 5. 출력
        // 5-1. 결과 리스트를 출력한다.
        sb = new StringBuilder();
        for (int result : resultList) {
            sb.append(result).append(" ");
        }

        System.out.println(sb);
    }

    // 3. 유물 획득 (방법 선택 과정)
    static Method getMethod(int curRowIdx, int curColIdx, int degree) {

        int[][] newMap = new int[5][5];

        for (int rowIdx = 0; rowIdx < 5; rowIdx++) {
            newMap[rowIdx] = treasureMap[rowIdx].clone();
        }

        // 3-1. 주어진 위치에서 회전하면서, BFS로 현재 위치의 유물 가치와 같은 유물이 3개이상 연결된다면 이들의 가치를 합하여 리턴한다.
        for (int cnt = 0; cnt < degree; cnt++) {
            rotateMap(newMap, curRowIdx, curColIdx);
        }

        int sumOfValue = bfs(newMap);

        return new Method(curRowIdx, curColIdx, degree, sumOfValue);
    }

    // 4. 유물 획득 (방법 이미 선택된 후 진짜 유물 획득 진행)
    static int rotateMapAndgetTreasure(Method targetMethod) {

        int sumOfTreasureValue = 0;

        // 4-1. 정해진 방법에 맞게 맵을 회전하고, 유물을 획득한다.
        for (int cnt = 0; cnt < targetMethod.rotateDegree; cnt++) {
            rotateMap(treasureMap, targetMethod.centorRowIdx, targetMethod.centorColIdx);
        }

        // 4-3. 연쇄적으로 얻을 유물이 있을 경우, 유물을 더이상 획들할 수 없을 때 까지 반복한다.
        boolean flag = true;

        while (flag) {
            int curValue = getTreasure();
            if (curValue == 0) {
                flag = false;
            }
            sumOfTreasureValue += curValue;
        }
        return sumOfTreasureValue;
    }

    static int getTreasure() {

        int curTreasureValue = bfs(treasureMap);

        // 4-2. 벽에 적힌 물은 1. 열번호가 작은 순, 2. 행번호가 큰 순으로 채워짐
        Collections.sort(canGetTreasureLocaList, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[1] < o2[1]) {
                    return -1;
                } else if (o1[1] > o2[1]) {
                    return 1;
                } else {

                    if (o1[0] < o2[0]) {
                        return 1;
                    } else if (o1[0] > o2[0]) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            }
        });

        for (int[] treasureLoca : canGetTreasureLocaList) {
            treasureMap[treasureLoca[0]][treasureLoca[1]] = wallTreasureQueue.poll();
        }

        return curTreasureValue;
    }

    static ArrayList<int[]> canGetTreasureLocaList;

    static int bfs(int[][] map) {

        boolean[][] visited = new boolean[5][5];
        canGetTreasureLocaList = new ArrayList<>();

        for (int rowIdx = 0; rowIdx < 5; rowIdx++) {
            for (int colIdx = 0; colIdx < 5; colIdx++) {

                if (visited[rowIdx][colIdx]) {
                    continue;
                }

                // 3개 이상이 되면 적용하기위한 임시 방문 배열
                boolean[][] tempVisited = new boolean[5][5];

                for (int tempRowIdx = 0; tempRowIdx < 5; tempRowIdx++) {
                    tempVisited[tempRowIdx] = visited[tempRowIdx].clone();
                }

                Deque<int[]> queue = new ArrayDeque<>();
                tempVisited[rowIdx][colIdx] = true;
                queue.offer(new int[]{rowIdx, colIdx});

                int cnt = 1;

                while (!queue.isEmpty()) {

                    int[] curLoca = queue.poll();
                    int curRow = curLoca[0];
                    int curCol = curLoca[1];
                    int treasureValue = map[curRow][curCol];

                    for (int dr = 0; dr < 4; dr++) {

                        int nextRow = curRow + dRow[dr];
                        int nextCol = curCol + dCol[dr];

                        if (nextRow < 0 || nextCol < 0 || nextRow >= 5 || nextCol >= 5) {
                            continue;
                        }

                        if (tempVisited[nextRow][nextCol]) {
                            continue;
                        }

                        if (map[nextRow][nextCol] != treasureValue) { // 현재 위치와 같은 보물만 연결가능
                            continue;
                        }

                        tempVisited[nextRow][nextCol] = true;
                        queue.offer(new int[]{nextRow, nextCol});
                        cnt++;

                    }

                }

                if (cnt >= 3) { // 3개 이상일 경우에만 반영
                    for (int tempRowIdx = 0; tempRowIdx < 5; tempRowIdx++) {
                        visited[tempRowIdx] = tempVisited[tempRowIdx].clone();
                    }
                }

            }

        }

        int sumOfValue = 0;

        for (int rowIdx = 0; rowIdx < 5; rowIdx++) {
            for (int colIdx = 0; colIdx < 5; colIdx++) {
                if (visited[rowIdx][colIdx]) {
                    sumOfValue ++;
                    canGetTreasureLocaList.add(new int[]{rowIdx, colIdx});
                }
            }
        }

        return sumOfValue;
    }

    static void rotateMap (int[][] map, int curRowIdx, int curColIdx) {

        int[][] miniMap = new int[3][3];

        for (int rowIdx = 0; rowIdx < 3; rowIdx++) {
            for (int colIdx = 0; colIdx < 3; colIdx++) {
                miniMap[rowIdx][colIdx] = map[curRowIdx - 1 + rowIdx][curColIdx - 1 + colIdx];
            }
        }

        int[][] tempMiniMap = new int[3][3];

        for (int rowIdx = 0; rowIdx < 3; rowIdx++) {
            for (int colIdx = 0; colIdx < 3; colIdx++) {
                tempMiniMap[rowIdx][colIdx] = miniMap[3 - 1 - colIdx][rowIdx];
            }
        }

        for (int rowIdx = 0; rowIdx < 3; rowIdx++) {
            for (int colIdx = 0; colIdx < 3; colIdx++) {
                map[curRowIdx - 1 + rowIdx][curColIdx - 1 + colIdx] = tempMiniMap[rowIdx][colIdx];
            }
        }
    }

    static class Method implements Comparable<Method> {

        int centorRowIdx;
        int centorColIdx;
        int rotateDegree;
        int sumOfValue;

        public Method(int centorRowIdx, int centorColIdx, int rotateDegree, int sumOfValue) {
            this.centorRowIdx = centorRowIdx;
            this.centorColIdx = centorColIdx;
            this.rotateDegree = rotateDegree;
            this.sumOfValue = sumOfValue;
        }

        // 2-2-1. (우선순위) 1. 유물 1차 획득가치 최대 / 2. 회전 각도 가장 작은 / 3. 회전 중심 좌표의 열이 가장 작은 구간 / 4. 회전 중심 좌표의 행이 가장 작은 구간
        @Override
        public int compareTo(Method m) {

            if (this.sumOfValue > m.sumOfValue) {
                return -1;
            } else if (this.sumOfValue < m.sumOfValue) {
                return 1;
            } else {

                if (this.rotateDegree > m.rotateDegree) {
                    return 1;
                } else if (this.rotateDegree < m.rotateDegree) {
                    return -1;
                } else {

                    if (this.centorColIdx > m.centorColIdx) {
                        return 1;
                    } else if (this.centorColIdx < m.centorColIdx) {
                        return -1;
                    } else {

                        if (this.centorRowIdx > m.centorRowIdx) {
                            return 1;
                        } else if(this.centorRowIdx < m.centorRowIdx) {
                            return -1;
                        } else {
                            return 0;
                        }

                    }

                }

            }
        }

    }
}
