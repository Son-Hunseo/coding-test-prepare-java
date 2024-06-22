package 자바언어적응용;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BJ2309 {
    public static void main(String[] args) throws IOException {

        int[] data = new int[9];
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        for (int i = 0; i < 9; i++) {
            data[i] = Integer.parseInt(br.readLine());
        }

        Arrays.sort(data);

        // 7개의 조합을 구하지말고 2개의 조합을 뺀걸 구해도 됨

        int nineSum = 0;
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < data.length; i++) {
            nineSum += data[i];
            result.add(data[i]);
        }

        boolean check = false;
        for (int i = 0; i < 9; i++) {
            for (int j = i + 1; j < 9; j++) {
                if (nineSum - (data[i] + data[j]) == 100) {
                    check = true;
                    result.remove(result.indexOf(data[i]));
                    result.remove(result.indexOf(data[j]));
                }
                if (check == true) {
                    break;
                }
            }
            if (check == true) {
                break;
            }
        }

        for (int i = 0; i < result.size(); i++) {
            System.out.println(result.get(i));
        }
    }
}
