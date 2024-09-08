package SWEA.모의A;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * SWEA 02115 벌꿀채취
 *
 * 1. 입력
 * 1-1. 테스트 케이스의 개수 T를 입력받는다.
 * 1-2. 벌통 맵의 크기 N, 선택할 수 있는 벌통의 개수(가로로만 가능) M, 한 사람이 꿀을 채취할 수 있는 최대 양 C가 공백을 기준으로 입력받는다.
 * 1-3. 벌통 맵의 정보를 입력 받는다.
 *
 * 2. 완전 탐색
 * 2-1. 어떤 행, 열 위치를 선택한다. (가로로 M 만큼 꿀통을 선택할 수 있는)
 * 2-2. 해당 꿀통 배열에서, 채취할 수 있는 최대 양을 넘지 않는 조합들을 찾으면서 최대 수익을 갱신한다.
 * 2-3. 각 행, 열의 최대 수익 중 가장 큰 수익 2개를 뽑아서 더한 값을 결과에 저장한다. (단, 꿀통 배열은 겹쳐서는 안된다)
 *
 * 3. 출력
 * 3-1. 결과를 출력한다.
 */

public class SWEA02115벌꿀채취 {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int sizeOfMap;
    static int maxHoneyChunk;
    static int maxHoneyAmount;

    static int[][] map;
    static int[][] maxProfitMap;

    static int MaxProfit;
    static int curProfit;
    static int curHoneyAmount;
    static int[] curHoneyChunksArr;

    public static void main(String[] args) throws IOException {

        // 1. 입력
        br = new BufferedReader(new InputStreamReader(System.in));
        // 1-1. 테스트 케이스의 개수 T를 입력받는다.
        int T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {
            sb = new StringBuilder();
            sb.append("#");
            sb.append(test_case);
            sb.append(" ");

            // 1-2. 벌통 맵의 크기 N, 선택할 수 있는 벌통의 개수(가로로만 가능) M, 한 사람이 꿀을 채취할 수 있는 최대 양 C가 공백을 기준으로 입력받는다.
            st = new StringTokenizer(br.readLine().trim());
            sizeOfMap = Integer.parseInt(st.nextToken());
            maxHoneyChunk = Integer.parseInt(st.nextToken());
            maxHoneyAmount = Integer.parseInt(st.nextToken());

            // 1-3. 벌통 맵의 정보를 입력 받는다.
            map = new int[sizeOfMap][sizeOfMap];

            for (int rowIdx = 0; rowIdx < sizeOfMap; rowIdx++) {
                st = new StringTokenizer(br.readLine().trim());
                for (int colIdx = 0; colIdx < sizeOfMap; colIdx++) {
                    map[rowIdx][colIdx] = Integer.parseInt(st.nextToken());
                }
            }

            maxProfitMap = new int[sizeOfMap][sizeOfMap];

            // 2. 완전 탐색
            // 2-1. 어떤 행, 열 위치를 선택한다. (가로로 M 만큼 꿀통을 선택할 수 있는)
            for (int rowIdx = 0; rowIdx < sizeOfMap; rowIdx++) {
                for (int colIdx = 0; colIdx <= sizeOfMap - maxHoneyChunk; colIdx++) {
                    MaxProfit = 0;
                    curProfit = 0;
                    curHoneyAmount = 0;
                    curHoneyChunksArr = new int[maxHoneyChunk];
                    int idx = 0;
                    while (true) {
                        if (idx == maxHoneyChunk) {
                            break;
                        }
                        curHoneyChunksArr[idx] = map[rowIdx][colIdx + idx];
                        idx++;
                    }

                    getMaxProfit(0);
                    maxProfitMap[rowIdx][colIdx] = MaxProfit;
                }
            }

            int result = 0;

            // 2-3. 각 행, 열의 최대 수익 중 가장 큰 수익 2개를 뽑아서 더한 값을 결과에 저장한다. (단, 꿀통 배열은 겹쳐서는 안된다)
            for (int rowIdxA = 0; rowIdxA < sizeOfMap; rowIdxA++) {
                for (int colIdxA = 0; colIdxA <= sizeOfMap - maxHoneyChunk; colIdxA++) {
                    for (int rowIdxB = rowIdxA; rowIdxB < sizeOfMap; rowIdxB++) {
                        if (rowIdxA == rowIdxB) {
                            for (int colIdxB = colIdxA; colIdxB <= sizeOfMap - maxHoneyChunk; colIdxB++) {
                                if (rowIdxA == rowIdxB && colIdxB - colIdxA < maxHoneyChunk) {
                                    continue;
                                }

                                result = Math.max(result, maxProfitMap[rowIdxA][colIdxA] + maxProfitMap[rowIdxB][colIdxB]);
                            }
                        } else {
                            for (int colIdxB = 0; colIdxB <= sizeOfMap - maxHoneyChunk; colIdxB++) {
                                if (rowIdxA == rowIdxB && colIdxB - colIdxA < maxHoneyChunk) {
                                    continue;
                                }

                                result = Math.max(result, maxProfitMap[rowIdxA][colIdxA] + maxProfitMap[rowIdxB][colIdxB]);
                            }
                        }
                    }
                }
            }

            sb.append(result);

            System.out.println(sb);
        }
    }

    static void getMaxProfit(int curHoneyChunckIdx) {

        if (curHoneyChunckIdx == maxHoneyChunk) {
            return;
        }

        // 현재 꿀 채취
        curHoneyAmount += curHoneyChunksArr[curHoneyChunckIdx];
        curProfit += (int) Math.pow(curHoneyChunksArr[curHoneyChunckIdx], 2);

        // 정해진 최대 꿀 채취량을 넘으면 안됨
        if (curHoneyAmount > maxHoneyAmount) {
            curHoneyAmount -= curHoneyChunksArr[curHoneyChunckIdx];
            curProfit -= (int) Math.pow(curHoneyChunksArr[curHoneyChunckIdx], 2);
            getMaxProfit(curHoneyChunckIdx+1);
            return;
        }

        // 2-2. 해당 꿀통 배열에서, 채취할 수 있는 최대 양을 넘지 않는 조합들을 찾으면서 최대 수익을 갱신한다.
        MaxProfit = Math.max(MaxProfit, curProfit);
        getMaxProfit(curHoneyChunckIdx+1);

        // 안넘어도 선택하지 않는 경우
        curHoneyAmount -= curHoneyChunksArr[curHoneyChunckIdx];
        curProfit -= (int) Math.pow(curHoneyChunksArr[curHoneyChunckIdx], 2);
        getMaxProfit(curHoneyChunckIdx+1);
    }
}
