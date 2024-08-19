package SWEA.모의;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * SWEA0412 요리사
 *
 * 1. 테스트 케이스의 수 T를 입력받는다.
 * 2. 음식 재료의 개수 N을 입력받는다.
 * 3. N x N 크기의 시너지 표를 입력받는다.
 * 4. (0 ~ N-1) 의 요소로 N/2개를 뽑는 조합을 만든다.
 * 5. 해당 조합으로 만들 수 있는 음식들의 맛을 모두 구한다.
 * 6. 각 음식마다 맛이 기록되어있는 리스트를 순회하면서 절댓값이 가장 낮은 값을 기록한다.
 * 6-1. 2차원 리스트에 2(선택) 1(아무것도 아님) 0(다른 음식에 선택) 으로 마킹하여 음식 A와 음식 B의 맛을 구한다.
 * 7. 결과 출력
 */

public class SWEA0412요리사 {

    static BufferedReader br;
    static StringTokenizer st;

    static int[][] synergyMap;
    static int numOfIngre;
    static int[] selectedArr;
    static ArrayList<Integer> absList;

    public static void main(String[] args) throws IOException {

        br = new BufferedReader(new InputStreamReader(System.in));
        // 1. 테스트 케이스의 수 T를 입력받는다.
        int T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {
            // 2. 음식 재료의 개수 N을 입력받는다.
            numOfIngre = Integer.parseInt(br.readLine());
            synergyMap = new int[numOfIngre][numOfIngre];

            // 3. N x N 크기의 시너지 표를 입력받는다.
            for (int row = 0; row < numOfIngre; row++) {

                st = new StringTokenizer(br.readLine().trim());

                for (int col = 0; col < numOfIngre; col++) {
                    synergyMap[row][col] = Integer.parseInt(st.nextToken());
                }
            }

            // (0 ~ N-1) 의 요소로 N/2개를 뽑는 조합을 만든다.
            selectedArr = new int[numOfIngre/2];
            // 5. 해당 조합으로 만들 수 있는 음식들의 맛의 차이를 모두 구한다.
            absList = new ArrayList<>();

            combi(0, 0);

            // 6. 각 음식마다 맛이 기록되어있는 리스트를 순회하면서 절댓값이 가장 낮은 값을 기록한다.
            int minAbs = 99999999;

            for(int abs : absList) {
                minAbs = Math.min(abs, minAbs);
            }

            System.out.println("#" + test_case + " " + minAbs);

        }
    }

    static void combi(int selectedIdx, int elementIdx) {
        // 종료
        if (selectedIdx == numOfIngre/2) {

            // 6-1. 2차원 리스트에 2(선택) 1(아무것도 아님) 0(다른 음식에 선택) 으로 마킹하여 음식 A와 음식 B의 맛을 구한다.
            int[][] dummyMap = new int[numOfIngre][numOfIngre];
            for (int row : selectedArr) {
                for (int col = 0; col < numOfIngre; col++) {
                    dummyMap[row][col]++;
                }
            }

            for (int col : selectedArr) {
                for (int row = 0; row < numOfIngre; row++) {
                    dummyMap[row][col]++;
                }
            }

            int sumOfFoodSynergy = 0;
            int anotherSum = 0;
            for (int row = 0; row < numOfIngre; row++) {
                for (int col = 0; col < numOfIngre; col++) {
                    if (dummyMap[row][col] == 2) {
                        sumOfFoodSynergy += synergyMap[row][col];
                    } else if (dummyMap[row][col] == 0) {
                        anotherSum += synergyMap[row][col];
                    }
                }
            }

            absList.add(Math.abs(sumOfFoodSynergy - anotherSum));
            return;
        }

        if (elementIdx == numOfIngre) {
            return;
        }

        // 현재 음식 선택
        selectedArr[selectedIdx] = elementIdx;
        combi(selectedIdx + 1, elementIdx + 1);

        // 현재 음식 선택 안함
        combi(selectedIdx, elementIdx + 1);
    }
}
