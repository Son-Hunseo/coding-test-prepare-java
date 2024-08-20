package SWEA.D4.스택;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * SWEA 괄호 짝짓기
 *
 * 1. 문자열의 길이를 입력 받는다.
 * 2. 문자열을 입력 받는다.
 * 3. 문자열을 하나씩 체크하면서 (, [, {, < 이 입력되면 스택에 넣고 ), ], }, > 이 입력되면 스택에서 짝을 뺀다.
 * 3-1. 중간에 짝이 맞지 않으면 플래그에 0 기록 (최초에 플래그 1로 초기화)
 * 4. 플래그 출력
 */
public class SWEA01218괄호짝짓기 {

    static BufferedReader br;

    public static void main(String[] args) throws IOException {

        br = new BufferedReader(new InputStreamReader(System.in));

        for (int test_case = 1; test_case <= 10; test_case++) {

            // 1. 문자열의 길이를 입력 받는다.
            int stringLength = Integer.parseInt(br.readLine());

            // 2. 문자열을 입력 받는다.
            String charArr = br.readLine();

            MyStack stack = new MyStack();

            // 짝이 맞는지 체크하기위한 flag
            int flag = 1;

            // 3. 문자열을 하나씩 체크하면서 (, [, {, < 이 입력되면 스택에 넣고 ), ], }, > 이 입력되면 스택에서 짝을 뺀다.
            for (int idx = 0; idx < charArr.length(); idx++) {

                char target = charArr.charAt(idx);

                if (target == '(' || target == '[' || target == '{' || target == '<') {
                    stack.push(target);
                } else if (target == ')') {
                    if (stack.peek() == '(') {
                        stack.pop();
                    } else {
                        flag = 0;
                        break;
                    }
                } else if (target == ']') {
                    if (stack.peek() == '[') {
                        stack.pop();
                    } else {
                        flag = 0;
                        break;
                    }
                } else if (target == '}') {
                    if (stack.peek() == '{') {
                        stack.pop();
                    } else {
                        flag = 0;
                        break;
                    }
                } else if (target == '>') {
                    if (stack.peek() == '<') {
                        stack.pop();
                    } else {
                        flag = 0;
                        break;
                    }
                }

            }

            System.out.println("#" + test_case + " " + flag);
        }
    }
}

class MyStack<Character> {

    private int DEFAULT_SIZE = 1000;
    int top;
    int size;
    char[] storage;


    MyStack() {
        this.top = 0;
        this.size = this.DEFAULT_SIZE;
        this.storage = new char[size];
    }

    void push(char item) {

        this.top++;

        if (this.top == this.size) {
            resize();
        }

        this.storage[this.top] = item;
    }

    char pop() {

        char target = this.storage[this.top];
        this.top--;

        return target;
    }

    char peek() {
        return this.storage[this.top];
    }


    void resize() {
        this.size = size * 2;

        char[] newStorage = new char[this.size];

        for(int idx = 0; idx <= top; idx++) {
            newStorage[idx] = this.storage[idx];
        }

        this.storage = newStorage;
    }
}
