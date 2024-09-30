import java.util.Arrays;

public class Main {

    static int[][] map;

    public static void main(String[] args) {

        map = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};

        rotate();

        print();

    }

    static void rotate() {

        int[][] newMap = new int[3][3];

        for (int rowIdx = 0; rowIdx < map.length; rowIdx++) {
            for (int colIdx = 0; colIdx < map.length; colIdx++) {
                newMap[colIdx][map.length - 1 - rowIdx] = map[rowIdx][colIdx];
            }
        }

        map = newMap;
    }

    static void print() {
        for (int rowIdx = 0; rowIdx < map.length; rowIdx++) {
            System.out.println(Arrays.toString(map[rowIdx]));
        }
    }

}