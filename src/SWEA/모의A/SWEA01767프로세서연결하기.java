package SWEA.모의A;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * SWEA 1767 프로세서 연결하기
 *
 * 1. 입력
 * 1-1. 테스트 케이스의 개수 T를 입력받는다.
 * 1-2. 배열의 크기 N을 입력받는다.
 * 1-3. 멕시노스 맵을 입력받는다.
 *
 * 2. 완전탐색 (조합)
 * 2-1. 각 멕시노스가 전원이 연결이 되는 경우의 수의 조합을 구해서 전선 길이의 합의 최소를 구한다.
 * 2-2. 각 전선은 겹쳐서는 안된다.
 *
 * 3. 출력
 */

public class SWEA01767프로세서연결하기 {

    // 1. 입력
    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    // 상하좌우
    static final int[] dRow = {-1, 1, 0, 0};
    static final int[] dCol = {0, 0, -1, 1};

    static int T;
    static int sizeOfMap;
    static int[][] map;

    static ArrayList<int[]> processorList;
    static int numOfConnect;
    static int lengthOfLine;
    static int[] resultCandiList;
    static int result;

    static final int PROCESSOR = 1;

    public static void main(String[] args) throws IOException {

        br = new BufferedReader(new InputStreamReader(System.in));

        // 1-1. 테스트 케이스의 개수 T를 입력받는다.
        T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {

            // 1-2. 배열의 크기 N을 입력받는다.
            sizeOfMap = Integer.parseInt(br.readLine());

            // 1-3. 멕시노스 맵을 입력받는다.
            map = new int[sizeOfMap][sizeOfMap];
            processorList = new ArrayList<>();

            for (int rowIdx = 0; rowIdx < sizeOfMap; rowIdx++) {
                st = new StringTokenizer(br.readLine().trim());
                for (int colIdx = 0; colIdx < sizeOfMap; colIdx++) {
                    int curNum = Integer.parseInt(st.nextToken());
                    map[rowIdx][colIdx] = curNum;

                    if (curNum == PROCESSOR) {

                        // 멕시노스가 가장자리에 위치할 경우 이미 전원이 연결된 것으로 간주하고 전선을 연결할 후보에 넣지 않는다.
                        if (rowIdx == 0 || colIdx == 0 || rowIdx == sizeOfMap - 1 || colIdx == sizeOfMap - 1) {
                            continue;
                        }

                        processorList.add(new int[]{rowIdx, colIdx});
                    }
                }
            }

            resultCandiList = new int[processorList.size() + 1]; // 배열의 값: idx개의 프로세서가 연결되었을 때의 전선 길이의 합 최소 배열
            Arrays.fill(resultCandiList, Integer.MAX_VALUE);

            // 2. 완전탐색 (조합)
            lengthOfLine = 0;
            numOfConnect = 0;
            connectElec(0, 0);

            int maxConIdx = -1;

            for (int resultCandiIdx = 1; resultCandiIdx < processorList.size() + 1; resultCandiIdx++) {
                if (resultCandiList[resultCandiIdx] != Integer.MAX_VALUE) {
                    maxConIdx = resultCandiIdx;
                }
            }

            result = resultCandiList[maxConIdx];

            sb = new StringBuilder();
            sb.append("#");
            sb.append(test_case);
            sb.append(" ");
            sb.append(result);

            System.out.println(sb);
        }

    }

    // 2. 완전탐색 (조합)
    // 2-1. 각 멕시노스가 전원이 연결이 되는 경우의 수의 조합을 구해서 전선 길이의 합의 최소를 구한다.
    static void connectElec(int selectIdx, int processorIdx) {

        resultCandiList[numOfConnect] = Math.min(resultCandiList[numOfConnect], lengthOfLine);

        // 모든 경우를 다 봤을 경우 종료
        if (processorIdx == processorList.size()) {
            return;
        }

        // 각방향에 전선을 연결
        for (int dr = 0; dr < 4; dr++) {
            if (canConnect(processorIdx, dr)) { // 해당 방향에 전선을 연결할 수 있는 경우

                connectLine(processorIdx, dr); // 전선 연결
                numOfConnect++;
                lengthOfLine += getLineLength(processorIdx, dr); // 전체 전선 길이 늘려줌

                // 재귀 들어감
                connectElec(selectIdx + 1, processorIdx + 1);

                // 원복
                cancelConnectLine(processorIdx, dr);
                numOfConnect--;
                lengthOfLine -= getLineLength(processorIdx, dr);

            } else { // 전선을 연결할 수 없는 경우

                // 재귀 들어감
                connectElec(selectIdx, processorIdx + 1);

            }
        }

    }

    static boolean canConnect(int processorIdx, int lineDr) {

        int[] curProcessor = processorList.get(processorIdx);
        int processorRowIdx = curProcessor[0];
        int processorColIdx = curProcessor[1];

        // 전선이 가는 길목에 다른 전선이 있거나, 다른 프로세서가 있을 경우 연결하지 못한다.
        while (true) {
            int nextRowIdx = processorRowIdx + dRow[lineDr];
            int nextColIdx = processorColIdx + dCol[lineDr];

            // 격자 밖으로 나간 경우 (전원이 연결된 경우)
            if (isOut(nextRowIdx, nextColIdx)) {
                return true;
            }

            // 2-2. 각 전선은 겹쳐서는 안된다.
            // 만약 다른 전선이나 프로세서를 만난 경우
            if (map[nextRowIdx][nextColIdx] == PROCESSOR) {
                return false;
            }

            processorRowIdx = nextRowIdx;
            processorColIdx = nextColIdx;
        }
    }

    static int getLineLength(int processorIdx, int lineDr) {

        int[] curProcessor = processorList.get(processorIdx);
        int processorRowIdx = curProcessor[0];
        int processorColIdx = curProcessor[1];

        if (lineDr == 0) {
            return processorRowIdx;
        } else if (lineDr == 1) {
            return (sizeOfMap - 1 - processorRowIdx);
        } else if (lineDr == 2) {
            return processorColIdx;
        } else {
            return (sizeOfMap - 1 - processorColIdx);
        }
    }

    static void connectLine(int processorIdx, int lineDr) {

        int[] curProcessor = processorList.get(processorIdx);
        int processorRowIdx = curProcessor[0];
        int processorColIdx = curProcessor[1];

        while (true) {
            int nextRowIdx = processorRowIdx + dRow[lineDr];
            int nextColIdx = processorColIdx + dCol[lineDr];

            // 격자 밖으로 나간 경우 멈춤
            if (isOut(nextRowIdx, nextColIdx)) {
                return;
            }

            // 전선 연결
            map[nextRowIdx][nextColIdx] = PROCESSOR;

            processorRowIdx = nextRowIdx;
            processorColIdx = nextColIdx;
        }
    }

    static void cancelConnectLine(int processorIdx, int lineDr) {
        int[] curProcessor = processorList.get(processorIdx);
        int processorRowIdx = curProcessor[0];
        int processorColIdx = curProcessor[1];

        while (true) {
            int nextRowIdx = processorRowIdx + dRow[lineDr];
            int nextColIdx = processorColIdx + dCol[lineDr];

            // 격자 밖으로 나간 경우 멈춤
            if (isOut(nextRowIdx, nextColIdx)) {
                return;
            }

            // 전선 끊기
            map[nextRowIdx][nextColIdx] = 0;

            processorRowIdx = nextRowIdx;
            processorColIdx = nextColIdx;
        }
    }

    static boolean isOut(int rowIdx, int colIdx) {
        return rowIdx < 0 || colIdx < 0 || rowIdx >= sizeOfMap || colIdx >= sizeOfMap;
    }

}
