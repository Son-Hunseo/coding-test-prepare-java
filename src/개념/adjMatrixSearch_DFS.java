package 개념;

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

public class adjMatrixSearch_DFS {
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
        dfs(0, new boolean[V]);
    }

    private static void dfs(int cur, boolean[] visited) {

        visited[cur] = true;
        System.out.println((char) (cur + 65));

        for (int i = 0; i < V; i++) {
            if(adjMatrix[cur][i] == 0 || visited[i]) continue;
            dfs(i, visited);
        }
    }

}