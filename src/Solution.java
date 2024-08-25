import java.util.Arrays;
import java.util.Scanner;

public class Solution {

    static class Node {

        int nodeNum;
        Node next;

        public Node(int nodeNum, Node next) {
            super();
            this.nodeNum = nodeNum;
            this.next = next;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "to=" + nodeNum +
                    ", next=" + next +
                    '}';
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int V = sc.nextInt(); // 정점의 수
        int E = sc.nextInt(); // 간선의 수

        Node[] adjList = new Node[V]; // 각 노드의 연결리스트의 헤드 리스트

        /**
         * 각 노드에 인접한 노드를 삽입해줄 때 굳이 tail을 사용해서 맨 뒤에 삽입할 필요가 없다.
         * A에 B, C가 연결되어있는 것을 나타내려고 할 때, B, C 간에는 연관관계가 없기 때문이다.
         * 즉, head만 관리하면 된다.
         */

        for (int i = 0; i < E; ++i) {
            int from = sc.nextInt();
            int to = sc.nextInt();
            // 맨 앞에 삽입
            adjList[from] = new Node(to, adjList[from]);
            adjList[to] = new Node(from, adjList[from]);
        }

        for (int i = 0; i < V; i++) {
            System.out.println(adjList[i]);
        }
    }

}