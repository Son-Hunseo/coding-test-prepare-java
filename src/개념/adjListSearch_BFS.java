package 개념;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

public class adjListSearch_BFS {

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

        bfs1(0);

    }

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
            for(Node temp = adjList[cur]; temp != null; temp = temp.next) {
                if(visited[temp.to]) continue;

                visited[temp.to] = true;
                queue.offer(temp.to);
            }
        }
    }
}

class Node {
    int to;
    Node next;

    public Node(int to, Node next) {
        this.to = to;
        this.next = next;
    }

    @Override
    public String toString() {
        return "Node [to=" + to + ", next=" + next + "]";
    }
}
