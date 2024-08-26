import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

/*
7
8
0 1
0 2
0 5
0 6
4 3
5 3
5 4
6 4
 */

public class Main {

    static int V;
    static int[][] adjMatrix;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        V = sc.nextInt(); // 정점의 수
        int E = sc.nextInt(); // 간선의 수

        // 무향 그래프
        adjMatrix = new int[V][V]; // 기본 초기화값 0: 인접하지 않는 상태

        for (int i = 0; i < E; ++i) {
            int from = sc.nextInt();
            int to = sc.nextInt();
            adjMatrix[to][from] = adjMatrix[from][to] = 1;
        }
        bfs1(0);
    }

    private static void bfs1(int start) {
        Queue<Integer> queue = new ArrayDeque<>();
        boolean[] visited = new boolean[V];

        visited[start] = true;
        queue.offer(start);

        while(!queue.isEmpty()) {
            int cur = queue.poll();
            System.out.println((char)(cur+65));

            for(int i=0; i<V; i++) {
                if(adjMatrix[cur][i] == 0 || visited[i]) continue;

                visited[i] = true;
                queue.offer(i);
            }
        }
    }

    private static void bfs2(int start) {
        Queue<Integer> queue = new ArrayDeque<>();
        boolean[] visited = new boolean[V];

        queue.offer(start);

        while(!queue.isEmpty()) {
            int cur = queue.poll();
            System.out.println((char)(cur+65));

            for(int i=0; i<V; i++) {
                if(adjMatrix[cur][i] == 0 || visited[i]) continue;

                queue.offer(i);
            }
        }
    }

}