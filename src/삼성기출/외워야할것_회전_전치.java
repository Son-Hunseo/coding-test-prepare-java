package 삼성기출;

/*
Sample Input

5 6
1 2 3 4 5 6
7 8 9 10 11 12
13 14 15 16 17 18
19 20 21 22 23 24
25 26 27 28 29 30
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class 외워야할것_회전_전치 {

    /**
     * 시계 방향으로 90도 회전
     * - 전치를하고, 좌우 대칭을 시킨다고 생각하면 외우기 쉽다.
     */

     static int[][] rotateMatrix1(int[][] matrix) {

         int rowSize = matrix.length;
         int colSize = matrix[0].length; // 직사각형 일 경우도 고려
         int[][] newMatrix = new int[colSize][rowSize];

         for(int i = 0 ; i < colSize; i++) {
             for(int j = 0 ; j < rowSize ; j++) {
                 newMatrix[i][j] = matrix[rowSize - 1 - j][i];
             }
         }

         return newMatrix;
     }

    /**
     * 반시계 방향으로 90도 회전
     * - 이 경우, 시계 방향으로 90도 회전을 3번해도 된다.
     */

    static int[][] rotateMatrix2(int[][] matrix) {

        int rowSize = matrix.length;
        int colSize = matrix[0].length; // 직사각형 일 경우도 고려
        int[][] newMatrix = new int[colSize][rowSize];

        for (int i = 0; i < colSize; i++) {
            for (int j = 0; j < rowSize; j++) {
                newMatrix[i][j] = matrix[j][colSize - 1 - i];
            }
        }

        return newMatrix;
    }

    /**
     * 전치
     */

    static int[][] transpose(int[][] matrix) {

        int rowSize = matrix.length;
        int colSize = matrix[0].length; // 직사각형 일 경우도 고려
        int[][] newMatrix = new int[colSize][rowSize];

        for (int i = 0; i < colSize; i++) {
            for (int j = 0; j < rowSize; j++) {
                newMatrix[i][j] = matrix[j][i];
            }
        }

        return newMatrix;
    }

    /**
     * 상하 뒤집기
     */

    static int[][] upDown(int[][] matrix) {

        int rowSize = matrix.length;
        int colSize = matrix[0].length; // 직사각형 일 경우도 고려
        int[][] newMatrix = new int[rowSize][colSize];

        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                newMatrix[i][j] = matrix[rowSize - 1 - i][j];
            }
        }

        return newMatrix;
    }

    /**
     * 좌우 뒤집기
     */

    static int[][] leftRight(int[][] matrix) {

        int rowSize = matrix.length;
        int colSize = matrix[0].length; // 직사각형 일 경우도 고려
        int[][] newMatrix = new int[rowSize][colSize];

        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                newMatrix[i][j] = matrix[i][colSize - 1 - j];
            }
        }

        return newMatrix;
    }

    /**
     * 한 칸씩 회전하는 경우 (달팽이) - temp 사용
     *
     * 전체 격자 중 부분만 회전(전치)하는 경우 - 해당 부분만 회전(전치)한 격자를 원래 격자에 붙여넣기
     *
     */

    // -----------------------------------------------------------------------------------

    /**
     * 인풋 테스트
     */

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int[][] Matrix = new int[n][m];

        for(int i = 0 ; i < n ; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0 ; j < m ; j++) {
                Matrix[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 여기서 테스트
        int[][] newMatrix = rotateMatrix1(Matrix);
        // 여기서 테스트

        for(int i = 0 ; i < newMatrix.length; i++) {
            for(int j = 0 ; j < newMatrix[0].length; j++ ) {
                System.out.print(newMatrix[i][j]+" ");
            }
            System.out.println();
        }

    }

}
