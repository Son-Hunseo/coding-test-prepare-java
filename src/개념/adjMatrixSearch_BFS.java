package 개념;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

/**
 7
 8
 0 1
 0 2
 1 3
 1 4
 2 4
 3 5
 4 5
 5 6
 */
public class adjMatrixSearch_BFS {
    static int V;
    static int adjMatrix[][];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        V = sc.nextInt();
        int E = sc.nextInt();
        adjMatrix = new int[V][V];
        for (int i = 0; i < E; ++i) {
            int from = sc.nextInt();
            int to = sc.nextInt();
            adjMatrix[to][from] = adjMatrix[from][to] = 1;
        }
        bfs1(0);
        System.out.println("===========================");
        //bfs2(0);
        bfs3(0);
    }

    // 방문 처리하고 넣는 방법
    private static void bfs1(int start) {
        Queue<Integer> queue = new ArrayDeque<>();
        boolean[] visited = new boolean[V];

        visited[start] = true;
        queue.offer(start);

        while(!queue.isEmpty()) {
            int cur = queue.poll();
            //방문했을 때 해야할 작업들...
            System.out.println((char)(cur + 65));
            //자신의 인접정점들 다음 탐색위한 준비!
            for(int i = 0; i < V; i++) {
                if(adjMatrix[cur][i] == 0 || visited[i]) continue;
                visited[i] = true;
                queue.offer(i);
            }
        }
    }

    // 빼고 방문처리하는 예시인데 이렇게 하면 안된다.
    private static void bfs2(int start) {
        Queue<Integer> queue = new ArrayDeque<>();
        boolean[] visited = new boolean[V];

        queue.offer(start);

        while(!queue.isEmpty()) {
            int cur = queue.poll();
            visited[cur] = true;
            //방문했을 때 해야할 작업들...
            System.out.println((char)(cur + 65));
            //자신의 인접정점들 다음 탐색위한 준비!
            for(int i = 0; i < V; i++) {
                if(adjMatrix[cur][i] == 0 || visited[i]) continue;
                queue.offer(i);
            }
        }
    }

    private static void bfs3(int start) {
        Queue<Integer> queue = new ArrayDeque<>();
        boolean[] visited = new boolean[V];

        visited[start] = true;
        queue.offer(start);
        int breadth = 0;

        while(!queue.isEmpty()) {

            int size = queue.size(); // 큐의 크기 체크
            System.out.print("너비 "+breadth + " : ");
            while(--size >= 0) {
                int cur = queue.poll();
                //방문했을 때 해야할 작업들...
                System.out.print((char)(cur + 65) + " ");
                //자신의 인접정점들 다음 탐색위한 준비!
                for(int i = 0; i < V; i++) {
                    if(adjMatrix[cur][i] == 0 || visited[i]) continue;
                    visited[i] = true;
                    queue.offer(i);
                }
            }    // 동일 너비 처리
            System.out.println();
            ++breadth; // 너비 증가
        }
    }
}
