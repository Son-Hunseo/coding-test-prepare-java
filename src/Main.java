import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    static class Meeting implements Comparable<Meeting> {
        int start, end;

        public Meeting (int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public int compareTo(Meeting o) { // 종료시간이 빠른 순, 같다면 시작시간이 빠른 순
            return this.end != o.end ? this.end - o.end : this.start - o.start;
        }

    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        Meeting[] meetings = new Meeting[N];
        for (int i = 0; i < N; i++) {
            meetings[i] = new Meeting(sc.nextInt(), sc.nextInt());
        }

        Arrays.sort(meetings);
        ArrayList<Meeting> result = new ArrayList<>();
        result.add(meetings[0]);

        for (int i = 1; i < N; i++) {
            if(result.get(result.size()-1).end <= meetings[i].start) {
                result.add(meetings[i]);
            }
        }

        System.out.println(result.size());
        for (Meeting meeting : result) {
            System.out.println(meeting.start + "-" + meeting.end);
        }

    }
}