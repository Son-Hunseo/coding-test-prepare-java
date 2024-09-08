package SWEA.D4.MST;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * SWEA 7465 창용마을 무리의 개수
 *
 * 1. 입력
 * 1-1. 테스트 케이스의 개수 T를 입력받는다.
 * 1-2. 사람의 수 N과, 관계의 개수 M을 입력받는다.
 * 1-3. M개의 줄에 걸쳐서 서로를 알고있는 두 사람의 번호를 입력받는다.
 *
 * 2. 서로소 집합
 * 2-1. 관계만큼 union연산을 진행한 후, parent 배열에서 0보다 작은 수의 개수를 세면 된다.
 *
 * 3. 출력
 *
 * Utils
 * - make
 * - find
 * - parent
 */

public class SWEA07465창용마을무리의개수 {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int[] parents;

    public static void main(String[] args) throws IOException {

        // 1. 입력
        br = new BufferedReader(new InputStreamReader(System.in));

        // 1-1. 테스트 케이스의 개수 T를 입력받는다.
        int T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {

            sb = new StringBuilder();
            sb.append("#");
            sb.append(test_case);
            sb.append(" ");

            // 1-2. 사람의 수 N과, 관계의 개수 M을 입력받는다.
            st = new StringTokenizer(br.readLine().trim());
            int numOfPeople = Integer.parseInt(st.nextToken());
            int numOfRelationship = Integer.parseInt(st.nextToken());

            parents = new int[numOfPeople + 1];
            make();

            // 1-3. M개의 줄에 걸쳐서 서로를 알고있는 두 사람의 번호를 입력받는다.
            for (int relateIdx = 0; relateIdx < numOfRelationship; relateIdx++) {
                st = new StringTokenizer(br.readLine().trim());
                int peopleA = Integer.parseInt(st.nextToken());
                int peopleB = Integer.parseInt(st.nextToken());

                // 2. 서로소 집합
                // 2-1. 관계만큼 union연산을 진행한 후, parent 배열에서 0보다 작은 수의 개수를 세면 된다.
                union(peopleA, peopleB);
            }

            int result = 0;
            for (int parent : parents) {
                if (parent < 0) {
                    result++;
                }
            }

            sb.append(result);
            System.out.println(sb);
        }
    }

    static void make() {
        Arrays.fill(parents, -1);
        parents[0] = Integer.MAX_VALUE; // 0 번째 사람은 없기 때문.
    }

    static int find(int people) {
        if (parents[people] < 0) {
            return people;
        }
        return parents[people] = find(parents[people]);
    }

    static boolean union(int peopleA, int peopleB) {
        int rootA = find(peopleA);
        int rootB = find(peopleB);

        if (rootA == rootB) {
            return false;
        }

        parents[rootA] += parents[rootB];
        parents[rootB] = rootA;
        return true;
    }
}
