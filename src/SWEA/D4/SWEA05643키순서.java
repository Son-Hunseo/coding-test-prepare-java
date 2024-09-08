package SWEA.D4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * SWEA 5643 키순서
 *
 * 1. 입력
 * 1-1. 테스트케이스의 개수 T를 입력받는다.
 * 1-2. 학생들의 수 N을 입력받는다.
 * 1-3. 키를 비교한 결과의 수 M을 입력받는다.
 * 1-4. 학생들의 키 비교 점수를 입력받는다.
 * 1-4-1. 입력받은 키 비교 결과를 인접 행렬에 넣는다.
 *
 * 2. 알고리즘
 * 2-1. n번째 학생이 본인의 키 순서를 알 수 있는지 판단하는 로직
 * 2-2. n번째 학생보다 키가 작다고 판단할 수 있는 학생의 수를 센다.
 * 2-3. n번째 학생보다 키가 크다고 판단할 수 있는 학생의 수를 센다.
 * 2-4. 키가 작다고 판단할 수 있는 학생의 수 + 키가 크다고 판단할 수 있는 학생의 수 = N-1이 된다면, 해당 학생은 본인의 키 순서를 알 수 있다.
 *
 * 3. 출력
 * 3-1. 결과를 출력한다.
 *
 */

public class SWEA05643키순서 {

    static BufferedReader br;
    static StringTokenizer st;

    static int numOfStudent;
    static int[][] adjacentMatrix;

    static int lowerStudentCnt;
    static int higherStudentCnt;
    static boolean[] isVisited;

    static int result;

    public static void main(String[] args) throws IOException {
        // 1. 입력
        br = new BufferedReader(new InputStreamReader(System.in));

        // 1-1. 테스트케이스의 개수 T를 입력받는다.
        int T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {

            // 1-2. 학생들의 수 N을 입력받는다.
            numOfStudent = Integer.parseInt(br.readLine());

            // 1-3. 키를 비교한 결과의 수 M을 입력받는다.
            int numOfEdge = Integer.parseInt(br.readLine());

            adjacentMatrix = new int[numOfStudent+1][numOfStudent + 1];

            // 1-4. 학생들의 키 비교 점수를 입력받는다.
            for (int edgeIdx = 0; edgeIdx < numOfEdge; edgeIdx++) {
                st = new StringTokenizer(br.readLine().trim());

                int start = Integer.parseInt(st.nextToken());
                int end = Integer.parseInt(st.nextToken());

                // 1-4-1. 입력받은 키 비교 결과를 인접 행렬에 넣는다.
                adjacentMatrix[start][end] = 1;

            }

            // 2. 알고리즘
            // 2-1. n번째 학생이 본인의 키 순서를 알 수 있는지 판단하는 로직

            result = 0;

            for (int curStudentIdx = 1; curStudentIdx <= numOfStudent; curStudentIdx++) {
                // 2-2. n번째 학생보다 키가 작다고 판단할 수 있는 학생의 수를 센다.
                lowerStudentCnt = 0;
                isVisited = new boolean[numOfStudent + 1];
                cntLowerStudent(curStudentIdx);

                // 2-3. n번째 학생보다 키가 크다고 판단할 수 있는 학생의 수를 센다.
                higherStudentCnt = 0;
                isVisited = new boolean[numOfStudent + 1];
                cntHigherStudent(curStudentIdx);

                // 2-4. 키가 작다고 판단할 수 있는 학생의 수 + 키가 크다고 판단할 수 있는 학생의 수 = N-1이 된다면, 해당 학생은 본인의 키 순서를 알 수 있다.
                if (lowerStudentCnt + higherStudentCnt == numOfStudent - 1) {
                    result++;
                }
            }

            // 3. 출력
            // 3-1. 결과를 출력한다.
            System.out.println("#" + test_case + " " + result);

        }


    }

    static void cntHigherStudent(int standardStudentIdx) {
        for (int compareStudentIdx = 1; compareStudentIdx <= numOfStudent; compareStudentIdx++) {
            if (adjacentMatrix[standardStudentIdx][compareStudentIdx] == 1 && !isVisited[compareStudentIdx]) {
                isVisited[compareStudentIdx] = true;
                higherStudentCnt++;
                cntHigherStudent(compareStudentIdx);
            }
        }
    }

    static void cntLowerStudent(int standardStudentIdx) {
        for (int compareStudentIdx = 1; compareStudentIdx <= numOfStudent; compareStudentIdx++) {
            if (adjacentMatrix[compareStudentIdx][standardStudentIdx] == 1 && !isVisited[compareStudentIdx]) {
                isVisited[compareStudentIdx] = true;
                lowerStudentCnt++;
                cntLowerStudent(compareStudentIdx);
            }
        }
    }

}
