/*
8
1 1 0 0 0 0 1 1
1 1 0 0 0 0 1 1
0 0 0 0 1 1 0 0
0 0 0 0 1 1 0 0
1 0 0 0 1 1 1 1
0 1 0 0 1 1 1 1
0 0 1 1 1 1 1 1
0 0 1 1 1 1 1 1
*/

import java.util.Scanner;

public class Main {

    static int N, map[][], white, green;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        N = sc.nextInt();
        map = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                map[i][j] = sc.nextInt();
            }
        }
        cut(0, 0, N);
        System.out.println(white);
        System.out.println(green);
    }

    static void cut(int r, int c, int size) {

        // 주어진 공간이 모두 같은 색으로 이루어졌는지 체크
        int sum = 0;
        for (int i = r, rEnd = r + size; i < rEnd; i++) {
            for (int j = c, cEnd = c + size; j < cEnd; j++) {
                sum += map[i][j];
            }
        }

        // 같은색이며녀 분할하지 않음
        if (sum == 0) {
            white++;
        } else if (sum == size * size) {
            green++;
        } else { // 같은색으로 이루어져 있지 않으면 4분할
            int half = size/2;
            cut(r, c, half);
            cut(r, c+half, half);
            cut(r+half, c, half);
            cut(r+half, c+half, half);
        }

    }

}