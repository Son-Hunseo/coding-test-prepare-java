package SWEA.D4;

import java.io.*;
import java.util.*;

/**
 * SWEA20936 상자 정렬하기
 *
 * - 뾰족한 수가 안떠올라서 그냥 떠오르는대로 그리디로 접근해보자.
 * - 일단 X 자리는 -1로 표시하자.
 *
 * 1. 모두 정렬될 떄 까지 반복
 * 1-1. X가 마지막 인덱스에 있을 경우 (모두 정렬되지 않았을 때)
 * -> 제자리에 있지 않은 수 중 가장 큰 수를 X와 자리 바꿈
 * 1-2. X가 마지막 인덱스가 아닐 경우
 * -> X가 마지막 인덱스로 갈 때까지 X를 해당 인덱스가 제자리인 숫자와 자리를 바꿈
 */
public class SWEA20936상자정렬하기 {

    static BufferedReader br;
    static StringTokenizer st;

    public static void main(String[] args) throws IOException {

        br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        int T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {

            // 입력
            int numOfBox = Integer.parseInt(br.readLine());
            int[] BoxList = new int[numOfBox + 1];

            st = new StringTokenizer(br.readLine().trim());
            for (int idx = 0; idx < numOfBox; idx++) {
                BoxList[idx] = Integer.parseInt(st.nextToken());
            }
            BoxList[numOfBox] = -1;

            int changeCnt = 0;

            // 여기다가 히스토리 기록
            List<Integer> changeHistory = new ArrayList<>();
            int XIdx = numOfBox; // X인덱스
            boolean check = true; // 처음부터 정렬되어있었는지 아닌지 여부

            // 잘못된 자리 만큼 반복
            while (!isSorted(numOfBox, BoxList)) {
                changeCnt++;
                // X가 마지막 인덱스에 있는 경우
                if (XIdx == numOfBox) {
                    int maxIdx = -1;
                    int maxValue = 0;

                    // 제자리에 없는 요소 중 가장 큰 요소 찾기
                    for (int idx = 0; idx < numOfBox; idx++) {
                        // 제자리에 있는 경우 넘어감
                        if (BoxList[idx] == idx + 1) {
                            continue;
                        }
                        if (maxValue < BoxList[idx]) {
                            maxValue = BoxList[idx];
                            maxIdx = idx;
                        }
                    }

                    // 처음부터 모든 요소가 제자리에 있는 경우
                    if (maxIdx == -1) {
                        check = false;
                        break;
                    }

                    BoxList[XIdx] = BoxList[maxIdx];
                    BoxList[maxIdx] = -1;
                    XIdx = maxIdx;
                    changeHistory.add(XIdx + 1);

                } else { // X가 마지막 인덱스가 아닌 경우

                    int targetIdx = -1;

                    // X자리에 알맞는 요소가 있는 자리 찾기
                    for (int idx = 0; idx <= numOfBox; idx++) {
                        if (BoxList[idx] == XIdx + 1) {
                            targetIdx = idx;
                            break;
                        }
                    }

                    BoxList[XIdx] = BoxList[targetIdx];
                    BoxList[targetIdx] = -1;
                    XIdx = targetIdx;
                    changeHistory.add(XIdx + 1);
                }
            }

            if (check == false) {
                System.out.println(0);
                System.out.println();
            } else {
                System.out.println(changeCnt);
                for (int cnt = 0; cnt < changeHistory.size(); cnt++) {
                    System.out.print(changeHistory.get(cnt) + " ");
                }
                System.out.println();
            }
        }
    }

    static boolean isSorted(int numOfBox, int[] BoxList) {
        for (int idx = 0; idx < numOfBox; idx++) {
            if (BoxList[idx] != idx + 1) {
                return false;
            }
        }
        return true;
    }

}
