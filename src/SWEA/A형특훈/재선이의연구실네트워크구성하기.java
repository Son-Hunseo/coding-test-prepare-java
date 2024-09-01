package SWEA.A형특훈;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 재선이의 연구실네트워크구성하기
 *
 * 1. 입력
 * 1-1. 테스트케이스의 개수 T를 입력받는다.
 * 1-2. 연구실의 크기와 AP의 범위를 입력받는다.
 * 1-3. 연구실 평면도를 입력받는다. (공유기는 1~3으로 주어지고 포트는 9로 주어진다.)
 *
 * 2. 완전탐색 (백트래킹)
 * 2-1. 모든 포트에서 AP를 r개 선택하는 경우의 수를 뽑는다. (1 <= r <= 포트의 수)
 * 2-2. 만약 k개의 포트를 선택한 조합에서 모든 와이파이가 연결된다면, k+1 ~ 포트의 수 개수의 조합은 구할 필요가 없다.
 * 2-3. 모든 조합을 해보았는데, 와이파이가 연결되지 않는다면 -1 이다.
 *
 * 3. 공유기와 연결된 여부 체크
 *
 * 4. 출력
 * 4-1. 모든 공유기를 연결할 수 있는 최소의 AP 개수를 출력한다.
 *
 */

public class 재선이의연구실네트워크구성하기 {

    static BufferedReader br;
    static StringTokenizer st;

    static int sizeOfLab;
    static int rangeOfAP;

    static ArrayList<int[]> hubInfoList;
    static ArrayList<int[]> APInfoList;
    static ArrayList<int[]> CombiList;

    static int result;

    public static void main(String[] args) throws IOException {

        // 1. 입력
        br = new BufferedReader(new InputStreamReader(System.in));

        // 1-1. 테스트케이스의 개수 T를 입력받는다.
        int T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {
            // 1-2. 연구실의 크기와 AP의 범위를 입력받는다.
            st = new StringTokenizer(br.readLine().trim());
            sizeOfLab = Integer.parseInt(st.nextToken());
            rangeOfAP = Integer.parseInt(st.nextToken());

            APInfoList = new ArrayList<>();
            hubInfoList = new ArrayList<>();

            // 1-3. 연구실 평면도를 입력받는다. (공유기는 1~3으로 주어지고 포트는 9로 주어진다.)
            for (int rowIdx = 0; rowIdx < sizeOfLab; rowIdx++) {
                st = new StringTokenizer(br.readLine().trim());
                for (int colIdx = 0; colIdx < sizeOfLab; colIdx++) {
                    int curInfo = Integer.parseInt(st.nextToken());

                    if (curInfo == 9) {
                        APInfoList.add(new int[]{rowIdx, colIdx});
                    }
                    else if (curInfo != 0) {
                        hubInfoList.add(new int[]{rowIdx, colIdx, curInfo});
                    }
                }
            }

            result = Integer.MAX_VALUE;

            CombiList = new ArrayList<>();
            getCombinationAndCheckConnect(0, 0);

            if (result == Integer.MAX_VALUE) {
                result = -1;
            }

            System.out.println("#" + test_case + " " + result);
        }
    }

    static void getCombinationAndCheckConnect(int selectedIdx, int APListIdx) {

        // 연결되었을 경우, 해당 방향으로 더이상 재귀를 들어갈 필요가 없다.
        if (isConnnected(CombiList)) {
            result = Math.min(result, CombiList.size());
            return;
        }

        // 기저
        if (APListIdx == APInfoList.size()) {
            return;
        }

        CombiList.add(APInfoList.get(APListIdx));
        getCombinationAndCheckConnect(selectedIdx + 1, APListIdx + 1);

        CombiList.remove(APInfoList.get(APListIdx));
        getCombinationAndCheckConnect(selectedIdx, APListIdx + 1);
    }

    // 3. 공유기와 연결된 여부 체크
    static boolean isConnnected(ArrayList<int[]> selectedAPInfoList) {

        // 3-1. 공유기와 AP의 거리로 연결여부를 체크할 수 있다.
        boolean result = true;

        if (selectedAPInfoList.size() == 0) {
            return false;
        }

        for (int hubIdx = 0; hubIdx < hubInfoList.size(); hubIdx++) {
            for (int[] AP : selectedAPInfoList) {
                if (Math.abs(hubInfoList.get(hubIdx)[0] - AP[0]) + Math.abs(hubInfoList.get(hubIdx)[1] - AP[1]) > rangeOfAP + hubInfoList.get(hubIdx)[2]) {
                    return false;
                }
            }
        }

        return result;
    }

}
