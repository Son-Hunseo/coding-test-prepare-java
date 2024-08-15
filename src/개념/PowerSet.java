package 개념;


public class PowerSet {

    static int[] elementArray = {1, 2, 3};
    static int[] selectArray = new int[2];

    static final int ELEMENT_COUNT = 3; // 원소의 개수

    static boolean[] elementUsedArray = {false, false, false};

    public static void powerSet(int elementIndex) {

        if (elementIndex == ELEMENT_COUNT) {
            return;
        }

        elementUsedArray[elementIndex] = true;
        powerSet(elementIndex + 1);

        elementUsedArray[elementIndex] = false;
        powerSet(elementIndex + 1);
    }

    public static void main(String[] args) {

        // T
        // N: elementCount, R: selectCount
        // 순, 조, 부 - 1, 2, 1 -> 파라미터의 개수 = 기저조건 개수
        powerSet(0);

    }

}
