import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static int[] dy = {-1, 0, 1, 0};
    static int[] dx = {0, 1, 0, -1};
    static int[] hps;
    static int[][] map;
    static int[][] knight_map;
    static int[][] knight_positions;
    static int n;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        int knight_count = Integer.parseInt(st.nextToken());
        int query_count = Integer.parseInt(st.nextToken());
        map = new int[n + 2][n + 2];
        knight_map = new int[n + 2][n + 2];
        knight_positions = new int[knight_count + 1][2];
        hps = new int[knight_count + 1];
        int[] original_hps = new int[knight_count + 1];

        // 맵 초기 설정
        for (int i = 0; i < n + 2; i++) {
            map[i][0] = 2;
            map[i][n + 1] = 2;
            map[0][i] = 2;
            map[n + 1][i] = 2;
        }

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) map[i + 1][j + 1] = Integer.parseInt(st.nextToken());
        }

        for (int knight_index = 1; knight_index < knight_count + 1; knight_index++) {
            st = new StringTokenizer(br.readLine());
            int y = Integer.parseInt(st.nextToken());
            int x = Integer.parseInt(st.nextToken());
            int height = Integer.parseInt(st.nextToken());
            int width = Integer.parseInt(st.nextToken());
            int hp = Integer.parseInt(st.nextToken());

            knight_positions[knight_index] = new int[]{y, x};
            hps[knight_index] = hp;
            original_hps[knight_index] = hp;

            for (int cur_y = y; cur_y < y + height; cur_y++) {
                for (int cur_x = x; cur_x < x + width; cur_x++) {
                    knight_map[cur_y][cur_x] = knight_index;
                }
            }
        }

        for (int query = 0; query < query_count; query++) {
            st = new StringTokenizer(br.readLine());
            int knight_index = Integer.parseInt(st.nextToken());
            int dir = Integer.parseInt(st.nextToken());

            SimulationKnight(knight_index, dir);
        }

        int result = 0;
        for (int knight_index = 1; knight_index < knight_count + 1; knight_index++) {
            if (hps[knight_index] < 1) continue;
            System.out.print(original_hps[knight_index] - hps[knight_index] + " ");
            result += original_hps[knight_index] - hps[knight_index];
        }

        System.out.println();
        System.out.println(result);
    }

    public static void SimulationKnight(int knight_index, int dir) {
        if (hps[knight_index] < 1) return; // 삭제된 기사인 경우 무조건 중단

        // knight_index 와 이어진 모든 기사 위치 추출
        int y = knight_positions[knight_index][0];
        int x = knight_positions[knight_index][1];
        boolean[][] visited = new boolean[n + 2][n + 2];
        Map<Integer, ArrayList<int[]>> knight_info = new HashMap<>();

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{y, x, knight_index});
        visited[y][x] = true;

        while (!queue.isEmpty()) {
            int[] q = queue.poll();
            int qy = q[0];
            int qx = q[1];
            int qindex = q[2];

            // 이동할 위치에 벽이 있다면 어차피 움직이지 못하므로 중단한다.
            int moved_y = qy + dy[dir];
            int moved_x = qx + dx[dir];
            if (map[moved_y][moved_x] == 2) return;
            // 이동 가능한 기사의 다음 위치를 저장해둔다.
            knight_info.computeIfAbsent(qindex, k -> new ArrayList<>()).add(new int[]{moved_y, moved_x});

            // 다른 블럭 구하기
            if (knight_map[moved_y][moved_x] != 0 && knight_map[moved_y][moved_x] != qindex && !visited[moved_y][moved_x]){
                visited[moved_y][moved_x] = true;
                queue.offer(new int[] {moved_y, moved_x, knight_map[moved_y][moved_x]});
            }

            // 같은 블럭 구하기
            for (int i = 0; i < 4; i++) {
                int ny = qy + dy[i];
                int nx = qx + dx[i];

                if (visited[ny][nx] || map[ny][nx] == 2 || knight_map[ny][nx] != qindex) continue;

                visited[ny][nx] = true;
                queue.offer(new int[]{ny, nx, knight_map[ny][nx]});
            }
        }

        // 기사들 이동
        for (int cur_knight_index : knight_info.keySet()) {
            RemoveKnight(cur_knight_index);

            int damage = 0;
            for (int[] position : knight_info.get(cur_knight_index)) {
                int nex_y = position[0];
                int nex_x = position[1];

                if (map[nex_y][nex_x] == 1) damage++;
                knight_map[nex_y][nex_x] = cur_knight_index;
            }

            // 기사의 중심 위치 이동
            int[] prev_knight_info = knight_positions[cur_knight_index];
            int[] new_knight_info = new int[] {prev_knight_info[0] + dy[dir], prev_knight_info[1] + dx[dir]};
            knight_positions[cur_knight_index] = new_knight_info.clone();

            // 체력감소
            if (cur_knight_index == knight_index) continue;
            hps[cur_knight_index] -= damage;

            // 기사 삭제
            if (hps[cur_knight_index] > 0) continue;
            RemoveKnight(cur_knight_index);
        }
    }

    public static void RemoveKnight(int knight_index) {
        for (int i=0; i<n+2; i++) for (int j=0; j<n+2; j++) if (knight_map[i][j] == knight_index) knight_map[i][j] = 0;
    }
}