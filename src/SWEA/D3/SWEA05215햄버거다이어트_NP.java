package SWEA.D3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

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
 * 4. Next Permutation을 이용하여 구한 조합 중 제한 칼로리 이하이면서 가장 맛에 대한 점수가 높은 햄버거의 점수를 출력
 */

public class SWEA05215햄버거다이어트_NP {

    static BufferedReader br;
    static StringTokenizer st;

    static int maxCal;
    static int result;
    static int numOfIngre;
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


            // 4. Next Permutation을 이용하여 구한 조합 중 제한 칼로리 이하이면서 가장 맛에 대한 점수가 높은 햄버거의 점수를 출력
            result = 0;
            // 4-1. 조합을 이용하여 구하려면, nC1 ~ nCn 까지 다 해봐야한다. (r은 nCr의 r이다.)
            for (int r = 1; r <= numOfIngre; r++) {
                selectedIngreArr = new Ingredient[r];
                findRecipe(r);
            }

            System.out.printf("#%d %d\n", test_case, result);
        }

    }

    static void findRecipe(int r) {

        // nextPermutation을 응용하여 조합을 찾기위한 배열
        int[] arrayToCombi = new int[numOfIngre];
        for (int cnt = 1; cnt <= r; cnt++) {
            arrayToCombi[numOfIngre-cnt] = 1;
        }

        do {
            int cnt = 0;
            for (int ingreIdx = 0; ingreIdx < numOfIngre; ingreIdx++) {
                if (arrayToCombi[ingreIdx] == 1) {
                    selectedIngreArr[cnt] = allIngreArr[ingreIdx];
                    cnt++;
                }
            }

            int curCal = 0;
            int curScore = 0;

            for (int ingreIdx = 0; ingreIdx < selectedIngreArr.length; ingreIdx++) {

                curCal += selectedIngreArr[ingreIdx].cal;
                curScore += selectedIngreArr[ingreIdx].score;

                // 제한 칼로리가 넘으면 고려할 필요 없다.
                if (curCal > maxCal) {
                    break;
                }

            }

            // 제한 칼로리가 이하일 때
            if (curCal <= maxCal) {
                if (curScore > result) {
                    result = curScore;
                }
            }

        } while (np(arrayToCombi));

    }

    static boolean np(int[] inputArray) {

        int inputSize = inputArray.length;

        // 꼭대기 i 찾기
        int top = inputSize - 1;
        while (top > 0 && inputArray[top-1] >= inputArray[top]) {
            top--;
        }

        if (top == 0) {
            return false;
        }

        int target = inputSize - 1;
        while(inputArray[top-1] >= inputArray[target]) {
            target--;
        }

        swap(inputArray, top - 1, target);

        int k = inputSize - 1;
        while (top < k) {
            swap(inputArray, top++, k--);
        }
        return true;
    }

    static void swap(int[] inputArray, int i, int j) {
        int temp = inputArray[i];
        inputArray[i] = inputArray[j];
        inputArray[j] = temp;
    }

}

class Ingredient {
    int score;
    int cal;
    Ingredient(int score, int cal) {
        this.score = score;
        this.cal = cal;
    }
}
