package SWEA.D3.완전탐색;

import java.io.*;
import java.util.*;

/**
 * SWEA5216 햄버거다이어트
 * [조건]
 * 1. 주어진 제한 칼로리 이하
 * 2. 가장 맛에 대한 점수(선호도 합)가 높은 햄버거
 *
 * [알고리즘]
 * 1. 테스트 케이스 수 T 입력받음
 * 2. 재료의 수 N, 제한 칼로리 L을 입력 받는다.
 * 3. 다음 N개의 줄에는 재료의 맛에 대한 점수와 칼로리를 나타내는 T_i, K_i를 입력 받는다.
 * 4. 조합을 이용하여 모든 재료의 조합 중 제한 칼로리 이하이면서 가장 맛에 대한 점수가 높은 햄버거의 점수를 출력
 * 4-1. 조합을 이용하여 구하려면, nC1 ~ nCn 까지 다 해봐야한다.
 */

public class SWEA05215햄버거다이어트_조합 {

    static BufferedReader br;
    static StringTokenizer st;

    static int maxCal;
    static int result;
    static int numOfIngre;
    static int numOfSelect;
    static Ingredient[] allIngreArr;
    static Ingredient[] selectedIngreArr;

    public static void main(String[] args) throws IOException {

        br = new BufferedReader(new InputStreamReader(System.in));

        // 1. 테스트 케이스 수 T 입력받음
        int T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {

            st = new StringTokenizer(br.readLine());

            // 2. 재료의 수 N, 제한 칼로리 L을 입력 받는다.
            numOfIngre = Integer.parseInt(st.nextToken());
            maxCal = Integer.parseInt(st.nextToken());

            // 3. 다음 N개의 줄에는 재료의 맛에 대한 점수와 칼로리를 나타내는 T_i, K_i를 입력 받는다.
            allIngreArr = new Ingredient[numOfIngre];
            for (int idx = 0; idx < numOfIngre; idx++) {
                st = new StringTokenizer(br.readLine());
                int score = Integer.parseInt(st.nextToken());
                int cal = Integer.parseInt(st.nextToken());
                Ingredient ingre = new Ingredient(score, cal);
                allIngreArr[idx] = ingre;
            }


            // 4. 조합을 이용하여 모든 재료의 조합 중 제한 칼로리 이하이면서 가장 맛에 대한 점수가 높은 햄버거의 점수를 출력
            result = 0;
            // 4-1. 조합을 이용하여 구하려면, nC1 ~ nCn 까지 다 해봐야한다. (r은 nCr의 r이다.)
            for (int r = 1; r <= numOfIngre; r++) {
                selectedIngreArr = new Ingredient[r];
                numOfSelect = r;
                findRecipe(0, 0);
            }

            System.out.printf("#%d %d\n", test_case, result);
        }

    }

    static void findRecipe(int allIngreCnt, int selectedIngreCnt) {

        // 조합이 완성 되었을 때, 해당 조합의 칼로리가 제한 칼로리 이하인지 확인하고, 이하라면
        // 최고 점수와 비교하고 최고 점수 이상이라면 갱신한다.
        if (selectedIngreCnt == numOfSelect) {
            int curCal = 0;
            int curScore = 0;

            for (int ingreIdx = 0; ingreIdx < selectedIngreArr.length; ingreIdx++) {

                curCal += selectedIngreArr[ingreIdx].cal;
                curScore += selectedIngreArr[ingreIdx].score;

                // 제한 칼로리가 넘으면 고려할 필요 없다.
                if (curCal > maxCal) {
                    return;
                }

            }

            // 제한 칼로리가 이하일 때
            if (curCal <= maxCal) {
                if (curScore > result) {
                    result = curScore;
                }
            }
            return;
        }

        // 중단 조건 - 정해진 개수의 조합이 완성되지 않았을 때
        if (allIngreCnt == numOfIngre) {
            return;
        }

        // 현재 재료 선택
        selectedIngreArr[selectedIngreCnt] = allIngreArr[allIngreCnt];
        findRecipe(allIngreCnt + 1, selectedIngreCnt + 1);

        // 현재 재료를 선택 X
        selectedIngreArr[selectedIngreCnt] = new Ingredient(0, 0);
        findRecipe(allIngreCnt + 1, selectedIngreCnt);

    }

}

//class Ingredient {
//    int score;
//    int cal;
//    Ingredient(int score, int cal) {
//        this.score = score;
//        this.cal = cal;
//    }
//}
