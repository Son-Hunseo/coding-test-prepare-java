package 개념;


public class Combination {

    static int[] elementArray = {1, 2, 3};
    static int[] selectArray = new int[2];

    static final int ELEMENT_COUNT = 3; // 원소의 개수
    static final int SELECT_COUNT = 2;  // 선택할 원소의 개수

    public static void combination(int elementIndex, int selectIndex) {

        // 기저조건 1
        // 선택할 수 있는 만큼 다 선택했으면
        if (selectIndex == SELECT_COUNT) {
            return;
        }

        // 기저조건 2
        // 선택하면서 모든 원소들을 다 확인 했으면
        if (elementIndex == ELEMENT_COUNT) {
            return;
        }

        // 원소를 선택해서 담아줌
        selectArray[selectIndex] = elementArray[elementIndex];
        combination(elementIndex + 1, selectIndex + 1);

        // 원소를 선택하지 않고 넘어감
        selectArray[selectIndex] = -1;
        combination(elementIndex + 1, selectIndex);

    }

    public static void main(String[] args) {

        // T
        // N: elementCount, R: selectCount
        // 순, 조, 부 - 1, 2, 1 -> 파라미터의 개수 = 기저조건 개수
        combination(0, 0);

    }

}
