package SWEA.모의A;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * SWEA 2117 홈 방범 서비스
 *
 * 1. 입력
 * 1-1. 테스트 케이스의 수 T를 입력받는다.
 * 1-2. 격자의 크기 N과 한 가구에서 낼 수 있는 최대의 비용 M을 입력받는다. (공백 O)
 * 1-3. 격자가 가구가 없는 곳은 0, 가구가 있는 곳은 1로 맵을 입력받는다. (공백 O)
 * 1-3-1. 입력받으면서 가구가 있는 위치만 리스트에 기록한다.
 * 1-3-2. 맵 자체를 입력받을 필요는 없다.
 *
 * 2. 완전탐색
 * 2-1. 격자 첫 줄부터 거리 기준으로 마름모 모양의 범위에 들어가는 가구 수를 구하면서 (가구 수 * M) - 운영 비용 을 계산하여 손해보지 않을경우, 최대 집 수를 갱신한다.
 * 2-2. 2-1 과정을 격자의 크기 K=1에서부터 K=N+1까지 구한다.
 *
 * 3. 출력
 */

public class SWEA02117홈방범서비스 {

    static BufferedReader br;
    static StringTokenizer st;

    static int sizeOfMap;
    static int maxPayPerHouse;

    static final int IS_HOUSE = 1;
    static ArrayList<int[]> houstLocaList;

    public static void main(String[] args) throws IOException {
        // 1. 입력
        br = new BufferedReader(new InputStreamReader(System.in));

        // 1-1. 테스트 케이스의 수 T를 입력받는다.
        int T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {
            // 1-2. 격자의 크기 N과 한 가구에서 낼 수 있는 최대의 비용 M을 입력받는다. (공백 O)
            st = new StringTokenizer(br.readLine().trim());
            sizeOfMap = Integer.parseInt(st.nextToken());
            maxPayPerHouse = Integer.parseInt(st.nextToken());

            // 1-3. 격자가 가구가 없는 곳은 0, 가구가 있는 곳은 1로 맵을 입력받는다. (공백 O)
            // 1-3-1. 입력받으면서 가구가 있는 위치만 리스트에 기록한다.
            // 1-3-2. 맵 자체를 입력받을 필요는 없다.
            houstLocaList = new ArrayList<>();

            for (int rowIdx = 0; rowIdx < sizeOfMap; rowIdx++) {
                st = new StringTokenizer(br.readLine().trim());
                for (int colIdx = 0; colIdx < sizeOfMap; colIdx++) {
                    int curLocaInfo = Integer.parseInt(st.nextToken());
                    if (curLocaInfo == IS_HOUSE) {
                        houstLocaList.add(new int[]{rowIdx, colIdx});
                    }
                }
            }

            // 2. 완전탐색
            // 2-1. 격자 첫 줄부터 거리 기준으로 마름모 모양의 범위에 들어가는 가구 수를 구하면서 (가구 수 * M) - 운영 비용 을 계산하여 손해보지 않을경우, 최대 집 수를 갱신한다.

            int maxHouse = 0;

            // 2-2. 2-1 과정을 격자의 크기 K=1에서부터 K=N+1까지 구한다.
            for (int range = 1; range <= sizeOfMap+1; range++) {
                for (int rowIdx = 0; rowIdx < sizeOfMap; rowIdx++) {
                    for (int colIdx = 0; colIdx < sizeOfMap; colIdx++) {
                        int inRangeHoustCnt = 0;
                        for (int[] house : houstLocaList) {
                            if (isInRange(rowIdx, colIdx, house[0], house[1], range)) {
                                inRangeHoustCnt++;
                            }
                        }
                        if ((maxPayPerHouse * inRangeHoustCnt) - ((range * range) + (range - 1) * (range - 1)) >= 0) {
                            maxHouse = Math.max(maxHouse, inRangeHoustCnt);
                        }
                    }
                }
            }

            System.out.println("#" + test_case + " " + maxHouse);
        }
    }

    static boolean isInRange(int curRow, int curCol, int houseRow, int houseCol, int range) {

        range = range - 1; // 범위 1은 자기 자신만 포함하므로 이렇게 전처리

        int distance = Math.abs(curRow - houseRow) + Math.abs(curCol - houseCol);

        return distance <= range ? true : false;
    }
}
