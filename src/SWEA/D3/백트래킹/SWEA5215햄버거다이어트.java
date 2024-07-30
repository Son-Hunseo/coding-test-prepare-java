package SWEA.D3.백트래킹;

import java.io.*;
import java.util.*;

/**
 * [조건]
 * 1. 주어진 제한 칼로리 이하
 * 2. 가장 맛에 대한 점수(선호도 합)가 높은 햄버거
 *
 * [내 생각]
 * 1. 모든 조합을 계산하는 코드를 짜봤는데 시간 터진다.
 * 2. 칼로리를 계산하면서 탐색하면서 제한 칼로리를 넘으면 탐색을 멈추는 백트래킹을 사용해보자.
 * 3. 점수만 알면 되므로 무슨 재료를 넣었는지 트래킹할 필요가 없다.
 */
public class SWEA5215햄버거다이어트 {

    static BufferedReader br;
    static StringTokenizer st;

    static int maxCal;
    static int result;

    public static void main(String[] args) throws IOException {

        br = new BufferedReader(new InputStreamReader(System.in));

        // 테스트 케이스 숫자 입력받기
        int T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {

            st = new StringTokenizer(br.readLine());

            // 재료 수와 최대 칼로리 입력받기
            int numOfIgre = Integer.parseInt(st.nextToken());
            maxCal = Integer.parseInt(st.nextToken());

            // 재료의 점수와 칼로리 입력받기
            Ingredient[] ingreArr = new Ingredient[numOfIgre];
            for (int idx = 0; idx < numOfIgre; idx++) {
                st = new StringTokenizer(br.readLine());
                int score = Integer.parseInt(st.nextToken());
                int cal = Integer.parseInt(st.nextToken());
                Ingredient ingre = new Ingredient(score, cal);
                ingreArr[idx] = ingre;
            }

            result = 0;
            findRecipe(ingreArr, 0, 0, 0);

            System.out.printf("#%d %d\n", test_case, result);
        }

    }

    // 백트래킹
    static void findRecipe(Ingredient[] ingreArr, int idx, int curCal, int curScore) {
        // 기록
        if (curScore > result) {
            result = curScore;
        }

        // 종료 조건
        if (idx == ingreArr.length) {
            return;
        }

        // 현재 재료를 넣는 경우 (현재 재료를 넣었을 때, 제한 칼로리를 넘지 않아야 함
        if (curCal + ingreArr[idx].cal <= maxCal) {
            findRecipe(ingreArr, idx+1, curCal + ingreArr[idx].cal, curScore + ingreArr[idx].score);
        }
        // 현재 재료를 넣지 않는 경우
        findRecipe(ingreArr, idx+1, curCal, curScore);
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
