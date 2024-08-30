package SWEA.모의A;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.StringTokenizer;

/**
 * SWEA1238 Contact
 *
 * - 인접 행렬을 만들어서 각 개개인이 누구에게 연락을 '걸'수 있는지 매핑한다. (2번이 3번에게 연락이 가능하다고 하면 2행3열에 true를 매핑한다.)
 *
 * 1. 입력
 * 1-1. 데이터의 길이 N과 시작점 S가 주어진다.
 * 1-2. N개의 데이터를 입력받아서 인접 행렬에 매핑한다.
 *
 * 2. 인접 행렬
 * 2-1. [x][시작점] 모두 false 방문처리하고 시작점을 큐에 넣는다.
 * 2-2. queue.poll -> 행에서 열 방향으로 한 칸씩 순회하면서 true가 나온 행을 방문처리를 하고 큐에 넣는다. (만약 1 -> 7을 갓다면, [x][7]을 모두 방문처리로 false로 만들어준다.)
 * 2-3. 큐에서 뺀 요소들을 리스트에 보관한다.
 * 2-4. 큐가 빌때까지 반복.
 * 2-4-1. 큐가 비어있지 않다면, 리스트를 초기화하고 반복을 다시 시작한다.
 *
 * 3. 출력
 * 3-1. 리스트 중 가장 번호가 큰 것을 출력한다.
 *
 */

public class SWEA01238Contact {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static boolean[][] adjacentMatrix;
    static ArrayList<Integer> LastVisitedList;

    public static void main(String[] args) throws IOException {

        // 1. 입력
        br = new BufferedReader(new InputStreamReader(System.in));

        for (int test_case = 1; test_case <= 10; test_case++) {
            // 1-1. 데이터의 길이 N과 시작점 S가 주어진다.
            st = new StringTokenizer(br.readLine().trim());
            int sizeOfData = Integer.parseInt(st.nextToken());
            int startPoint = Integer.parseInt(st.nextToken());

            // 1-2. N개의 데이터를 입력받아서 인접 행렬에 매핑한다.
            adjacentMatrix = new boolean[101][101];
            st = new StringTokenizer(br.readLine().trim());
            for (int dataIdx = 0; dataIdx < sizeOfData/2; dataIdx++) {
                int fromIdx = Integer.parseInt(st.nextToken());
                int toIdx = Integer.parseInt(st.nextToken());
                adjacentMatrix[fromIdx][toIdx] = true;
            }

            // 2. 인접 행렬
            Deque<Integer> queue = new ArrayDeque();
            // 2-1. [x][시작점] 모두 false 방문처리하고 시작점을 큐에 넣는다.
            for (int toIdx = 0; toIdx < 101; toIdx++) {
                adjacentMatrix[toIdx][startPoint] = false;
            }
            queue.add(startPoint);

            // 2-4. 큐가 빌때까지 반복.
            while(!queue.isEmpty()) {

                int size = queue.size();
                // 2-4-1. 큐가 비어있지 않다면, 리스트를 초기화하고 반복을 다시 시작한다.
                LastVisitedList = new ArrayList<>();

                while (size != 0) {
                    // 2-2. queue.poll -> 행에서 열 방향으로 한 칸씩 순회하면서 true가 나온 열을 방문처리를 하고 큐에 넣는다. (만약 1 -> 7을 갓다면, [x][7]을 모두 방문처리로 false로 만들어준다.)
                    int cur = queue.pop();
                    LastVisitedList.add(cur);

                    for (int toIdx = 0; toIdx < 101; toIdx++) {
                        if (adjacentMatrix[cur][toIdx] == true) {
                            for (int fromIdx = 0; fromIdx < 101; fromIdx++) {
                                adjacentMatrix[fromIdx][toIdx] = false;
                            }
                            queue.add(toIdx);
                        }
                    }
                    size--;
                }
            }

            int max = 0;
            for (int element : LastVisitedList) {
                max = Math.max(max, element);
            }

            System.out.println("#" + test_case + " " + max);
        }

    }
}
