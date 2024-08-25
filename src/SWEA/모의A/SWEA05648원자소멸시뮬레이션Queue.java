package SWEA.모의A;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Queue 로 풀어야하나?
 *
 */

public class SWEA05648원자소멸시뮬레이션Queue {

    static BufferedReader br;
    static StringTokenizer st;
    static Deque<int[]> atomQueue;
    static StringBuilder sb;

    // 주의!) 보통 알고리즘 문제에서 dx를 row 즉, 세로축, dy를 가로축으로하지만,
    // 이 문제에서는 우리가 아는 일반적인 좌표평면과 같이 x축이 가로축, y축이 세로축이다.
    static int[] dx = {0, 0, -1, 1};
    static int[] dy = {1, -1, 0, 0};

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));

        // 1. 테스트 케이스의 개수 T를 입력받는다.
        int T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {

            int result = 0;

            // 2. 원자들의 수 N을 입력받는다.
            int numOfAtom = Integer.parseInt(br.readLine());
            atomQueue = new LinkedList<>();

            for (int atomCnt = 1; atomCnt <= numOfAtom; atomCnt++) {
                // 3. 원자들의 x위치, y위치 이동방향, 보유 에너지 k를 입력받는다. (이동 방향 상하좌우 0123)
                st = new StringTokenizer(br.readLine().trim());
                // 3-1. x위치와 y위치는 2배로 저장한다.
                int xLoca = Integer.parseInt(st.nextToken()) * 2;
                int yLoca = Integer.parseInt(st.nextToken()) * 2;

                int dir = Integer.parseInt(st.nextToken());
                int energy = Integer.parseInt(st.nextToken());

                int[] atomInfo = {xLoca, yLoca, dir, energy};

                // 3-2. 입력 받은 원자들을 {x위치, y위치, 이동방향, 에너지} 의 형태로 ArrayList에 순서대로 넣는다.
                atomQueue.add(atomInfo);
            }

            while (atomQueue.size() > 1) { // 7. 원자가 1개 이하로 남는 경우 종료한다.

                int atomQueueSize = atomQueue.size();

                // 4. ArrayList을 순회하면서 다음 위치로 옮겨준다.
                for (int listIdx = 0; listIdx < atomQueueSize; listIdx++) {
                    int[] curAtomInfo = atomQueue.pop();
                    curAtomInfo[0] = curAtomInfo[0] + dx[curAtomInfo[2]]; // x좌표 이동
                    curAtomInfo[1] = curAtomInfo[1] + dy[curAtomInfo[2]]; // y좌표 이동

                    // 6. (위치를 2배로 한 것 기준) x위치 혹은 y위치가 -2000 ~ 2000 범위를 초과하면 원자를 없애준다. (이 범위 밖에서 만나는 경우는 없다)
                    if (curAtomInfo[0] > 2000 || curAtomInfo[1] > 2000 || curAtomInfo[0] < -2000 || curAtomInfo[1] < -2000) {
                        continue;
                    } else {
                        atomQueue.add(curAtomInfo);
                    }
                }

                Deque<int[]> newQueue = new LinkedList<>();

                for (int curAtomIdx = 0; curAtomIdx < atomQueue.size(); curAtomIdx++) {

                    int[] curAtom = atomQueue.pop();
                    newQueue.add(curAtom);

                    // 5. 같은 x위치와 y위치를 가질 경우 새로운 큐에 넣고, 그렇지 않을 경우 원래 큐에 다시 넣는다.
                    for (int targetAtomIdx = curAtomIdx + 1; targetAtomIdx < atomQueue.size(); targetAtomIdx++) {

                        int[] targetAtom = atomQueue.pop();

                        if (targetAtom[0] == curAtom[0] && targetAtom[1] == curAtom[1]) {
                            newQueue.add(targetAtom);
                        } else {
                            atomQueue.add(targetAtom);
                        }

                    }

                    while (newQueue.size() != 0) {
                        result += newQueue.pop()[3];
                    }
                }

            }

            sb = new StringBuilder();
            sb.append("#");
            sb.append(test_case);
            sb.append(" ");
            sb.append(result);

            System.out.println(sb);

        }
    }
}
