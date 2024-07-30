package BJ.Gold;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;

public class BJ1038감소하는수 {
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        ArrayList<ArrayList<Long>> data = new ArrayList<>();
        ArrayList<Long> row = new ArrayList<>();
        for (long i = 0; i < 10; i++) {
            row.add(i);
        }
        data.add(row);

        for (int unused = 0; unused < 9; unused++) {
            ArrayList<Long> inner_data = new ArrayList<>();
            for (int i = 0; i < data.get(data.size()-1).size(); i++) {
                String targetString = data.get(data.size()-1).get(i).toString();
                Long lastIndexNumber = Long.parseLong(String.valueOf(targetString.charAt(targetString.length() - 1)));
                if (lastIndexNumber != 0) {
                    for (long j = lastIndexNumber - 1; j > -1; j--) {
                        inner_data.add(Long.parseLong(targetString + String.valueOf(j)));
                    }
                }
            }
            data.add(inner_data);
        }

        ArrayList<Long> result = new ArrayList<>();

        for (int i = 0; i < data.size(); i ++) {
            for (int j = 0; j < data.get(i).size(); j ++) {
                result.add(data.get(i).get(j));
            }
        }

        result.sort(Comparator.naturalOrder());

        if (n > result.size() - 1) {
            System.out.println(-1);
        } else {
            System.out.println(result.get(n));
        }
    }
}
