package SWEA.D4.완전탐색;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

/**
 * SWEA 1486 장훈이의 높은 선반
 *
 * 모든 점원의 키를 세우는 경우를 다 구해서 문제를 풀 수 있다. 점원 수가 최대 20명이기 때문 (모든 부분집합 2^20 -> 약 100만)
 *
 * 1. 테스트 케이스의 수 T를 입력받는다.
 * 2. 점원의 수 N, 선반의 높이 B를 입력받는다. (탑의 높이가 선반의 높이보다 같거나 크면 선반의 물건을 사용할 수 있다.)
 * 3. 점원들의 키를 입력 받는다.
 * 4. 점원들의 키의 부분집합을 구해서 해당 부분집합의 합들을 탑 높이 리스트에 저장한다.
 * 5. 리스트를 오름차순으로 정렬하고 처음으로 선반의 크기보다 같가나 큰 요소를 구한다.
 * 6. 해당 요소의 높이와 선반의 높이 차이를 출력한다.
 */

public class SWEA01486장훈이의높은선반 {

    static BufferedReader br;
    static StringTokenizer st;

    static boolean[] isSelected;
    static int[] heightsOfMan;
    static ArrayList<Integer> towerHeights;
    static int numOfMan;

    public static void main(String[] args) throws IOException {

        br = new BufferedReader(new InputStreamReader(System.in));
        // 1. 테스트 케이스의 수 T를 입력받는다.
        int T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {

            // 2. 점원의 수 N, 선반의 높이 B를 입력받는다. (탑의 높이가 선반의 높이보다 같거나 크면 선반의 물건을 사용할 수 있다.)
            st = new StringTokenizer(br.readLine());
            numOfMan = Integer.parseInt(st.nextToken());
            int targetHeight = Integer.parseInt(st.nextToken());

            heightsOfMan = new int[numOfMan];
            st = new StringTokenizer(br.readLine());
            for (int manIdx = 0; manIdx < numOfMan; manIdx++) {
                // 3. 점원들의 키를 입력 받는다.
                heightsOfMan[manIdx] = Integer.parseInt(st.nextToken());
            }

            // 4. 점원들의 키의 부분집합을 구해서 해당 부분집합의 합들을 탑 높이 배열에 저장한다.
            towerHeights = new ArrayList<>();
            isSelected = new boolean[numOfMan];
            powerSet(0);

            // 5. 리스트를 오름차순으로 정렬하고 처음으로 선반의 크기보다 같가나 큰 요소를 구한다.
            Collections.sort(towerHeights);

            int target = 0;

            for (int height : towerHeights) {
                if (height >= targetHeight) {
                    target = height;
                    break;
                }
            }

            System.out.println("#" + test_case + " " + (target - targetHeight));
        }

    }

    static void powerSet(int selectIdx) {

        // 종료조건
        if (selectIdx == numOfMan) {
            // 이때 구해진 부분집합의 요소 합을 탑 높이 리스트에 저장한다.
            int sum = 0;
            for (int idx = 0; idx < numOfMan; idx++) {
                if (isSelected[idx] == true) {
                    sum += heightsOfMan[idx];
                }
            }
            towerHeights.add(sum);
            return;
        }

        // 선택
        isSelected[selectIdx] = true;
        powerSet(selectIdx + 1);

        // 선택 안함
        isSelected[selectIdx] = false;
        powerSet(selectIdx + 1);

    }
}
