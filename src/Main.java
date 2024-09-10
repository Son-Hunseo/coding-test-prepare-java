import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder sb;

    static int[][] adjacentMatrix;
    static boolean[] isVisited;
    static int numOfSubjects;

    static int order;
    static int[] resultArr;

    public static void main(String[] args) throws Exception {

        br = new BufferedReader(new InputStreamReader(System.in));

        st = new StringTokenizer(br.readLine().trim());

        numOfSubjects = Integer.parseInt(st.nextToken());
        int numOfEdge = Integer.parseInt(st.nextToken());

        adjacentMatrix = new int[numOfSubjects + 1][numOfSubjects + 1];

        for (int cnt = 0; cnt < numOfEdge; cnt++) {
            st = new StringTokenizer(br.readLine().trim());
            int befSubject = Integer.parseInt(st.nextToken());
            int targetSubject = Integer.parseInt(st.nextToken());

            adjacentMatrix[targetSubject][befSubject] = 1;
        }

        order = 0;
        resultArr = new int[numOfSubjects + 1];
        isVisited = new boolean[numOfSubjects + 1];

        while (true) {
            if (!logic()) {
                break;
            }
        }

        for (int idx = 1; idx <= numOfSubjects; idx++) {
            System.out.print(resultArr[idx] + " ");
        }
        System.out.println();

    }

    static boolean logic() {

        Deque<Integer> queue = new ArrayDeque<>();

        for (int rowIdx = 1; rowIdx <= numOfSubjects; rowIdx++) {
            boolean flag = false;
            for (int colIdx = 1; colIdx <= numOfSubjects; colIdx++) {
                if (adjacentMatrix[rowIdx][colIdx] == 1) {
                    flag = true;
                }
            }
            if (!flag && !isVisited[rowIdx]) {
                isVisited[rowIdx] = true;
                queue.add(rowIdx);
            }
        }

        if (queue.isEmpty()) {
            return false;
        }

        order++;

        while (!queue.isEmpty()) {
            int target = queue.poll();

            resultArr[target] = order;

            for (int rowIdx = 1; rowIdx <= numOfSubjects; rowIdx++) {
                adjacentMatrix[rowIdx][target] = 0;
            }
        }

        return true;
    }
}