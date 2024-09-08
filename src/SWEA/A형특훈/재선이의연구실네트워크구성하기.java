package SWEA.A형특훈;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * 재선이의 연구실 네트워크 구성하기
 * - 포트가 AP를 설치할 수 있는 위치이지만, 그냥 편의상 다 AP라고 하겟다.
 *
 * 1. 입력
 * 1-1. 테스트 케이스의 갯수 T를 입력받는다.
 * 1-2. 연구실의 크기와 AP의 범위를 입력받는다.
 * 1-3. 연구실 맵의 정보를 입력받는다.
 * 1-3-1. AP의 위치 정보(9)를 리스트에 넣는다.
 * 1-3-2. 공유기의 위치 정보와 연결가능 범위를 리스트에 넣는다.
 *
 * 2. 알고리즘
 * 2-1. 부분 집합으로 AP를 설치할 포트를 선택한다.
 * 2-2. 해당 AP들을 선택했을 때, 모든 공유기가 연결이 되는지 확인한다.
 * 2-2-1. 모든 공유기가 연결이 될 경우, 해당 재귀를 더 파고들 필요는 없다. (이 순간이 최소이기 때문)
 * 2-2-2. 현재 선택한 부분집합의 크기가 현재 연결을 위한 최소 AP갯수보다 크다면 수행할 필요가 없다. (어짜피 최소가 아니기 때문)
 *
 * 3. 출력
 * 3-1. 모든 공유기 연결을 위한 최소 AP 갯수를 출력한다.
 *
 * Utils
 * - AP 클래스
 * - 공유기 클래스
 *
 * - 부분집합을 구하는 메서드
 * - 모든 공유기가 연결되어있는지 확인하는 메서드
 */

public class 재선이의연구실네트워크구성하기 {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int sizeOfLab, rangeOfAP;
    static ArrayList<AP> APList;
    static ArrayList<shareHub> shareHubList;

    static boolean[] APSelectedInfoArr;

    static int result;

    public static void main(String[] args) throws IOException {

        // 1. 입력
        br = new BufferedReader(new InputStreamReader(System.in));

        // 1-1. 테스트 케이스의 갯수 T를 입력받는다.
        int T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {

            // 1-2. 연구실의 크기와 AP의 범위를 입력받는다.
            st = new StringTokenizer(br.readLine().trim());
            sizeOfLab = Integer.parseInt(st.nextToken());
            rangeOfAP = Integer.parseInt(st.nextToken());

            APList = new ArrayList<>();
            shareHubList = new ArrayList<>();

            // 1-3. 연구실 맵의 정보를 입력받는다.
            for (int rowIdx = 0; rowIdx < sizeOfLab; rowIdx++) {
                st = new StringTokenizer(br.readLine().trim());
                for (int colIdx = 0; colIdx < sizeOfLab; colIdx++) {
                    int curLocaInfo = Integer.parseInt(st.nextToken());
                    if (curLocaInfo != 0) {
                        // 1-3-1. AP의 위치 정보(9)를 리스트에 넣는다.
                        if (curLocaInfo == 9) {
                            APList.add(new AP(rowIdx, colIdx));
                        } else { // 1-3-2. 공유기의 위치 정보와 연결가능 범위를 리스트에 넣는다.
                            shareHubList.add(new shareHub(rowIdx, colIdx, curLocaInfo));
                        }
                    }
                }
            }

            // 2. 알고리즘
            APSelectedInfoArr = new boolean[APList.size()];
            result = Integer.MAX_VALUE;

            getPowerSetAndCheckConnect(0, 0);

            if (result == Integer.MAX_VALUE) {
                result = -1;
            }

            sb = new StringBuilder().append("#").append(test_case).append(" ").append(result);

            System.out.println(sb);
        }

    }

    static void getPowerSetAndCheckConnect(int elementIdx, int selectedCnt) {

        // 문제좀 잘보자!!
        if (selectedCnt > 5) {
            return;
        }

        // 2-2-2. 현재 선택한 부분집합의 크기가 현재 연결을 위한 최소 AP갯수보다 크다면 수행할 필요가 없다. (어짜피 최소가 아니기 때문)
        if (selectedCnt >= result) {
            return;
        }

        // 2-1. 부분 집합으로 AP를 설치할 포트를 선택한다.
        // // 2-2. 해당 AP들을 선택했을 때, 모든 공유기가 연결이 되는지 확인한다.
        if (elementIdx == APList.size()) {

            if (isAllshareHubConnected(selectedCnt)) {
                result = Math.min(result, selectedCnt);
            }
            return;
        }
        APSelectedInfoArr[elementIdx] = true;
        getPowerSetAndCheckConnect(elementIdx + 1, selectedCnt + 1);

        APSelectedInfoArr[elementIdx] = false;
        getPowerSetAndCheckConnect(elementIdx + 1, selectedCnt);

    }

    static boolean isAllshareHubConnected(int selectedCnt) {

        if (selectedCnt == 0) {
            return false;
        }

        // 초기화
        for (shareHub hub : shareHubList) {
            hub.isConnected = false;
        }

        for (int APIdx = 0; APIdx < APList.size(); APIdx++) {
            if (APSelectedInfoArr[APIdx]) { // 선택한 AP일 경우만
                for (int shareHubIdx = 0; shareHubIdx < shareHubList.size(); shareHubIdx++) {
                    if (Math.abs(shareHubList.get(shareHubIdx).rowIdx - APList.get(APIdx).rowIdx)
                            + Math.abs(shareHubList.get(shareHubIdx).colIdx - APList.get(APIdx).colIdx)
                            <= rangeOfAP + shareHubList.get(shareHubIdx).range) {
                        shareHubList.get(shareHubIdx).isConnected = true;
                    }
                }
            }
        }

        for (shareHub hub : shareHubList) {
            if (!hub.isConnected) {
                return false;
            }
        }

        return true;
    }

    static class AP {
        int rowIdx, colIdx;

        public AP(int rowIdx, int colIdx) {
            this.rowIdx = rowIdx;
            this.colIdx = colIdx;
        }
    }

    static class shareHub {
        int rowIdx, colIdx, range;
        boolean isConnected;

        public shareHub(int rowIdx, int colIdx, int range) {
            this.rowIdx = rowIdx;
            this.colIdx = colIdx;
            this.range = range;
        }
    }

}
