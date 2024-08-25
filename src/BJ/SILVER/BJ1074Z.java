package BJ.SILVER;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * BJ1074 Z
 * - 문제에 힌트가 있다. "재귀적"
 *
 * 1. 2^N x 2^N 배열의 크기를 정의하는 N, 출력해야하는 방문 정보를 담은 r, c를 공백을 사이에 두고 입력받는다.
 * 2. 재귀함수
 * 2-1. N이 1일 경우 더이상 재귀를 들어가지 않고 2 x 2 배열을 Z 모양으로 방문한다.
 * 2-2. N이 2이상일 경우 4등분으로 쪼개서 재귀를 들어간다. (해당 범위안에 목표 위치가 있는 경우)
 */

public class BJ1074Z {

    static BufferedReader br;
    static StringTokenizer st;

    static long targetRow;
    static long targetCol;

    static long result;

    public static void main(String[] args) throws IOException {

        br = new BufferedReader(new InputStreamReader(System.in));

        // 1. 2^N x 2^N 배열의 크기를 정의하는 N, 출력해야하는 방문 정보를 담은 r, c를 공백을 사이에 두고 입력받는다.
        st = new StringTokenizer(br.readLine().trim());
        long N = Long.parseLong(st.nextToken());
        targetRow = Long.parseLong(st.nextToken());
        targetCol = Long.parseLong(st.nextToken());

        result = -1;
        recursiveSearch(N, 0, 0);
    }

    static void recursiveSearch(long N, long curRow, long curCol) {

        // 2-1. N이 1일 경우 더이상 재귀를 들어가지 않고 2 x 2 배열을 Z 모양으로 방문한다.
        if (N == 1) {
            for (int cnt = 0; cnt < 4; cnt++) {
                // 좌상단에 있을 경우
                if (targetRow == curRow && targetCol == curCol) {
                    System.out.println(result+1);
                } else if (targetRow == curRow && targetCol == curCol+1) { // 우상단에 있을 경우
                    System.out.println(result+2);
                } else if (targetRow == curRow + 1 && targetCol == curCol) { // 좌하단에 있을 경우
                    System.out.println(result+3);
                } else { // 우하단에 있을 경우
                    System.out.println(result+4);
                }
                return;
            }
        }

        // 2-2. N이 2이상일 경우 4등분으로 쪼개서 재귀를 들어간다. (해당 범위안에 목표 위치가 있는 경우)
        if (targetRow < curRow + (long) Math.pow(2, N-1) && targetCol < curCol + (long) Math.pow(2, N-1)) { // 1사분면인 경우
            recursiveSearch(N-1, curRow, curCol);
        }
        else if (targetRow < curRow + (long) Math.pow(2, N-1) && targetCol >= curCol + (long) Math.pow(2, N-1)) { // 2사분면인 경우
            result += ((long) Math.pow(Math.pow(2, N - 1), 2));
            recursiveSearch(N-1, curRow, curCol + (long) Math.pow(2, N-1));
        }
        else if (targetRow >= curRow + (long) Math.pow(2, N-1) && targetCol < curCol + (long) Math.pow(2, N-1)) { // 3사분면인 경우
            result += ((long) Math.pow(Math.pow(2, N - 1), 2)) * 2;
            recursiveSearch(N-1, curRow + (long) Math.pow(2, N-1), curCol);
        }
        else { // 4사분면인 경우
            result += ((long) Math.pow(Math.pow(2, N - 1), 2)) * 3;
            recursiveSearch(N-1, curRow + (long) Math.pow(2, N-1), curCol + (long) Math.pow(2, N-1));
        }
    }
}
