package 개념;


/**
 * 순열
 *
 * !! 순서가 의미가 있다. [1, 2] <-> [2, 1]
 *
 */
public class Permutation {

    // 알고리즘 할때는 웬만하면 다 static 으로 빼자.
    // 왜? -> 메모리

    static int[] elementArray = {1, 2, 3};
    static int[] selectArray = new int[2];

    static final int ELEMENT_COUNT = 3; // 원소의 개수
    static final int SELECT_COUNT = 2;  // 선택할 원소의 개수

    static boolean[] elementUsedArray = {false, false, false};

    // selectIndex: 선택해서 담을 위치.
    // 재귀 함수로 구현 -> 재귀함수는 무조건 기저조건이 들어가야 한다. -> 없으면 스택오버플로우 생긴다.
    public static void permutation(int selectIndex) {

        // 1. 기저 조건 (종료 조건)
        // 내가 선택할 수 있는 만큼 다 선택했으면 더 이상 진행할 필요가 없다.
        if (selectIndex == SELECT_COUNT) {
            // 다 선택했으면 출력
//            for () {
//                // 담아놓은 배열을 출력.
//            }
            return;
        }

        // 2. 진행 로직
        // 아직 다 선택하지 못한 상황.
        // 원소들을 살펴봐서 아직 선택하지 않은 원소가 있으면 선택하고 다음으로 진행.

        // 2-1. 전처리 로직
        for(int elementIndex = 0; elementIndex < ELEMENT_COUNT; elementIndex++) {
            // 이미 선택이 된 원소라면 선택할 수 없다.
            if (elementUsedArray[elementIndex]) {
                continue;
            }

            // 아직이 선택이 안된 원소라면 선택한다.
            elementUsedArray[elementIndex] = true;
            selectArray[selectIndex] = elementArray[elementIndex];

            // 2-2. 다시 재귀함수 호출
            permutation(selectIndex + 1);

            // 2-3. 후처리 로직 (복원 작업)
            elementUsedArray[elementIndex] = false;
            selectArray[selectIndex] = -1;
        }
    }

    public static void main(String[] args) {

        // T
        // N: elementCount, R: selectCount
        // 순, 조, 부 - 1, 2, 1 -> 파라미터의 개수 = 기저조건 개수
        permutation(0);



    }

}
