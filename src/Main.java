import java.util.Arrays;
import java.util.Collections;

public class Main {

    public static void main(String[] args) {

        Method[] methodArr = new Method[3];

        methodArr[0] = new Method(4, 4, 0, 100);
        methodArr[1] = new Method(5, 5, 1, 200);
        methodArr[2] = new Method(2, 2, 0, 100);

        Arrays.sort(methodArr);

        System.out.println(methodArr[2].centorRowIdx);

    }

    static class Method implements Comparable<Method> {

        int centorRowIdx;
        int centorColIdx;
        int rotateDegree;
        int sumOfValue;

        public Method(int centorRowIdx, int centorColIdx, int rotateDegree, int sumOfValue) {
            this.centorRowIdx = centorRowIdx;
            this.centorColIdx = centorColIdx;
            this.rotateDegree = rotateDegree;
            this.sumOfValue = sumOfValue;
        }

        // 2-2-1. (우선순위) 1. 유물 1차 획득가치 최대 / 2. 회전 각도 가장 작은 / 3. 회전 중심 좌표의 열이 가장 작은 구간 / 4. 회전 중심 좌표의 행이 가장 작은 구간
        @Override
        public int compareTo(Method m) {

            if (this.sumOfValue > m.sumOfValue) {
                return -1;
            } else if (this.sumOfValue < m.sumOfValue) {
                return 1;
            } else {

                if (this.rotateDegree > m.rotateDegree) {
                    return 1;
                } else if (this.rotateDegree < m.rotateDegree) {
                    return -1;
                } else {

                    if (this.centorColIdx > m.centorColIdx) {
                        return 1;
                    } else if (this.centorColIdx < m.centorColIdx) {
                        return -1;
                    } else {

                        if (this.centorRowIdx > m.centorRowIdx) {
                            return 1;
                        } else if(this.centorRowIdx < m.centorRowIdx) {
                            return -1;
                        } else {
                            return 0;
                        }

                    }

                }

            }
        }

    }
}