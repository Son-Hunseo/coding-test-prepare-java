package SWEA.D3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * SWEA 6808 규영이와 인영이의 카드게임
 *
 * [알고리즘]
 * - 모든 카드는 1 ~ 18 이 적힌 18가지의 카드이다.
 * - 규영이가 받은 카드를 제외한 카드가 인영이가 받은 카드이다.
 * - 규영이는 입력받은 카드 순서대로 카드를 낸다. (규영이 카드 내는 순서 고정)
 * - 인영이가 내는 카드의 순서에 따라 9! 의 승패의 가짓수가 정해질 것이다.
 * - 9!은 약 30만 정도이므로 충분히 완전탐색이 가능하다.
 * ## 1 ~ 18을 모두 더한 값이 171 이므로 과반인 86 이상의 득점을 하면 그 다음 게임은 진행할 필요가 없다. (가지치기!)
 *
 * [구현 순서]
 * 1. 테스트 케이스의 수 입력 받음 (T)
 * 2. 규영이가 받은 카드들을 입력받는다. (규영이의 카드를 내는 순서는 입력 받은 그대로 고정)
 * 2-1. 나머지 카드들은 인영이 카드들이다. (인영이 카드는 순서 고정 아니다)
 * 3. 나머지 카드인 인영이의 카드들의 순서 경우의 수를 순열을 통해서 구한다.
 * 3-1. 인영이의 카드와 규영이의 카드들을 대결시키면서 점수가 처음 86을 넘어가는 사람이 이긴걸로 처리한다.
 */
public class SWEA06808규영이와 {

    static BufferedReader br;
    static StringTokenizer st;
    static int[] GYcard = new int[9];
    static int[] IYcard = new int[9];
    static boolean[] IYcardUsedArray;
    static int[] IYcardSelectedArray;
    static int GYwinCnt;
    static int IYwinCnt;
    static int GYscore;
    static int IYscore;

    // 3. 나머지 카드인 인영이의 카드들의 순서 경우의 수를 순열을 통해서 구한다.
    static void permutation(int selectIYcardIdx) {

        // 종료 조건
        if (selectIYcardIdx == 9) {
            // 여기서 게임 진행하고 종료
            for (int turn = 0; turn < 9; turn++) {

                // 게임 진행
                if (GYcard[turn] >= IYcardSelectedArray[turn]) {
                    GYscore += GYcard[turn] + IYcardSelectedArray[turn];
                } else {
                    IYscore += GYcard[turn] + IYcardSelectedArray[turn];
                }

                // 3-1. 인영이의 카드와 규영이의 카드들을 대결시키면서 점수가 처음 86을 넘어가는 사람이 이긴걸로 처리한다.
                if (GYscore >= 86) {
                    GYwinCnt++;
                    break;
                } else if (IYscore >= 86) {
                    IYwinCnt++;
                    break;
                }

            }

            // 게임이 끝났으니 점수 원복
            GYscore = 0;
            IYscore = 0;
            return;
        }

        for (int AllIYcardIdx = 0; AllIYcardIdx < 9; AllIYcardIdx++) {

            // 전처리
            if (IYcardUsedArray[AllIYcardIdx]) {
                continue;
            }

            IYcardSelectedArray[selectIYcardIdx] = IYcard[AllIYcardIdx];
            IYcardUsedArray[AllIYcardIdx] = true;

            // 재귀 진행
            permutation(selectIYcardIdx + 1);

            // 후처리
            IYcardUsedArray[AllIYcardIdx] = false;

        }

    }

    public static void main(String[] args) throws IOException {

        // 1. 테스트 케이스의 수 입력 받음 (T)
        br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {

            // 2. 규영이가 받은 카드들을 입력받는다. (규영이의 카드를 내는 순서는 입력 받은 그대로 고정)
            st = new StringTokenizer(br.readLine());
            boolean[] allCardList = new boolean[19]; // 인덱스가 18까지 있게 하기 위해
            allCardList[0] = true; // 0이라는 카드는 없으므로 true 처리

            for (int cardCnt = 0; cardCnt < 9; cardCnt++) {
                int nextCard = Integer.parseInt(st.nextToken());
                GYcard[cardCnt] = nextCard;
                allCardList[nextCard] = true;
            }

            // 2-1. 나머지 카드들은 인영이 카드들이다. (인영이 카드는 순서 고정 아니다)
            int IYcardIdx = 0;
            for (int cardCnt = 0; cardCnt < 19; cardCnt++) {
                if (allCardList[cardCnt] == false) {
                    IYcard[IYcardIdx] = cardCnt;
                    IYcardIdx++;
                }
            }

            GYwinCnt = 0;
            IYwinCnt = 0;
            IYcardUsedArray = new boolean[9];
            IYcardSelectedArray = new int[9];

            permutation(0);

            System.out.println("#" + test_case + " " + GYwinCnt + " " + IYwinCnt);
        }

    }
}
