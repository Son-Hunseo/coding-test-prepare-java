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

public class adjListSearch_DFS {

    static int V;
    static Node[] adjList;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        V = sc.nextInt(); // 정점 개수
        int E = sc.nextInt(); // 간선 개수

        adjList = new Node[V]; // 각 노드의 연결 리스트의 헤드 리스트

        for (int i = 0; i < E; ++i) {
            int from = sc.nextInt();
            int to = sc.nextInt();
            //첫노드로 삽입하는 알고리즘
            adjList[from] = new Node(to, adjList[from]);
            adjList[to] = new Node(from, adjList[to]);
        }

        dfs(0, new boolean[V]);
    }

    private static void dfs(int cur, boolean[] visited) {

        visited[cur] = true;
        System.out.println((char) (cur + 65));

        for (Node temp = adjList[cur]; temp != null; temp = temp.next) {
            if(visited[temp.to]) continue;
            dfs(temp.to, visited);
        }
    }
}
