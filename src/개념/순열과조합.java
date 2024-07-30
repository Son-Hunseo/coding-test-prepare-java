package 개념;

import java.util.*;

/**
 * 기존의 레퍼런스들은 순열이나 조합을 출력하는 로직이었음
 * 실제로 사용할 때는 특정 리스트에 순열이나 조합의 로직을 저장하는 식이 많을 것 같아서
 * or(Original r) 이라는 변수를 추가해서 구현하였음
 *
 * [조합 - resultnation]
 * 1. 조합의 경우의 수 구하기
 * - nCr = {n-1}C{r-1} + {n-1}C{r} 이라는 공식을 통해서 재귀적으로 구현한다.
 *
 * 2. 조합 구하기 (진짜 뽑아진 애들)
 * 2-1. 백트래킹을 이용한 구현
 * 2-2. 재귀를 이용한 구현
 *
 *
 * [순열 - permutation]
 * 3. 순열의 경우의 수 구하기
 * - 조합의 경우의 수에서 r!을 곱하면 될것 같아서 딱히 추가적으로 구현할 필요 없을듯
 *
 * 4. 순열 구하기 (진짜 뽑아진 애들)
 * 4-1. Swap을 이용한 순열 (https://bcp0109.tistory.com/14)
 * - depth가 r(뽑는 숫자의 개수)만큼만 진행되고 해당 인덱스까지만 읽으면 됨
 * - 간편하고 코드도 간결하지만, 순열들의 순서가 보장되지 않음
 */

public class 순열과조합 {

    static List<int[]> result = new ArrayList<>();

    public static void main(String[] args) {

        // 1. 조합의 경우의 수 구하기
        // System.out.println(resultnation(4, 2));

        int[] target = {1, 2, 3, 4};
        
        // 2-1. 백트래킹을 이용해 조합 구하기
        // comb1(target, new boolean[4], 0, 4, 2, 2);

        // 2-2. 재귀를 이용해 조합 구하기
        // comb2(target, new boolean[4], 0, 4, 2, 2);

        // 4-1. swap을 이용해 순열 구하기
        //per1(target, 0, 4, 2);

        // 4-2. visited를 이용해 순열 구하기
        per2(target, new int[2], new boolean[4], 0, 4, 2);

        for (int idx = 0; idx < result.size(); idx++) {
            print(result.get(idx));
        }

    }

    // 1. 조합의 경우의 수 구하기
    static int resultnation(int n, int r) {
        if (n == r || r == 0) {
            return 1;
        } else {
            return resultnation(n-1, r-1) + resultnation(n-1, r);
        }
    }

    // 2-1. 백트래킹을 이용한 구현
    // or 은 original r 이라는 뜻
    static void comb1(int[] arr, boolean[] visited, int start, int n, int r, int or) {
        if (r == 0) {
            int[] temp = new int[or];
            int nidx = 0;

            for (int idx = 0; idx < n; idx++) {
                if (visited[idx] == true) {
                    temp[nidx] = arr[idx];
                    nidx++;
                }
            }

            result.add(temp);
            return;
        } else {
            for (int i = start; i < n; i++) {
                visited[i] = true;
                comb1(arr, visited, i + 1, n, r - 1, or);
                visited[i] = false;
            }
        }
    }

    // 2-2. 재귀를 이용한 구현
    // depth는 현재 인덱스, 0부터 시작
    static void comb2(int[] arr, boolean[] visited, int depth, int n, int r, int or) {
        if (r == 0) {
            int[] temp = new int[or];
            int nidx = 0;

            for (int idx = 0; idx < n; idx++) {
                if (visited[idx] == true) {
                    temp[nidx] = arr[idx];
                    nidx++;
                }
            }

            result.add(temp);
            return;
        }

        if (depth == n) {
            return;
        } else {
            visited[depth] = true;
            comb2(arr, visited, depth + 1, n, r-1, or);

            visited[depth] = false;
            comb2(arr, visited, depth + 1, n, r, or);
        }
    }

    // 4-1. Swap을 이용한 순열
    static void per1(int[] arr, int depth, int n, int r) {
        if (depth == r) {
            int[] temp = new int[r];
            for (int idx = 0; idx < r; idx++) {
                temp[idx] = arr[idx];
            }

            result.add(temp);
            return;
        }

        for (int i = depth; i < n; i++) {
            swap(arr, depth, i);
            per1(arr, depth + 1, n, r);
            swap(arr, depth, i);
        }
    }

    static void per2(int[] arr, int[] output, boolean[] visited, int depth, int n, int r) {
        if (depth == r) {
            int[] temp = output.clone(); // output을 add 하면 결국에 다 같은 배열 가리킴
            result.add(temp);
            return;
        }

        for (int i = 0; i < n; i++) {
            if (visited[i] != true) {
                visited[i] = true;
                output[depth] = arr[i];
                per2(arr, output, visited, depth+1, n, r);
                output[depth] = 0; // 없어도 되긴 함
                visited[i] = false;
            }
        }
    }
    
    // 배열 출력
    static void print(int[] arr) {
        for (int idx = 0; idx < arr.length; idx++) {
            System.out.print(arr[idx] + " ");
        }
        System.out.println();
    }

    static void swap(int[] arr, int depth, int i) {
        int temp = arr[depth];
        arr[depth] = arr[i];
        arr[i] = temp;
    }

}
