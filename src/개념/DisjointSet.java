package 개념;

import java.util.Arrays;

public class DisjointSet {

    static int N = 5;
    static int[] parents; // 부모 배열

    static void make() {
        for (int i = 0; i < N; i++) {
            parents[i] = i; // make-set i
        }
    }

    static int findSet(int a) {

        if (a == parents[a]) return a; // 자신이 자시느이 부모라면 루트노드이고 집합의 대표자가 됨
        return findSet(parents[a]);
    }

    static boolean Union(int a, int b) {
        int aRoot = findSet(a);
        int bRoot = findSet(b);

        if (aRoot == bRoot) return false; // 두 집합의 대표자가 같으면 이미 같은 집합이므로 합집합 연산 불가
        // aRoot 에 bRoot를 흡수
        parents[bRoot] = aRoot;
        return true;
    }

    public static void main(String[] args) {
        parents = new int[N];

        // 모든 원소에 대해 단위 서로소집합 생성
        make();

        System.out.println(Arrays.toString(parents));

        Union(0, 1);
        Union(2, 3);

        System.out.println(Arrays.toString(parents));

        Union(0, 3);
        System.out.println(Arrays.toString(parents));

        System.out.println(findSet(3));

    }
}
