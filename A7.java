import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class A6 {

    static void p2() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input6.txt"));
        List<Integer> x = new LinkedList<>();
        List<Integer> y = new LinkedList<>();
        String line = br.readLine();
        while (line != null) {
            String[] xy = line.split(" ");
            x.add(Integer.valueOf(xy[0].substring(0, xy[0].length() - 1)));
            y.add(Integer.valueOf(xy[1]));
            line = br.readLine();
        }

        int maxX = x.stream().mapToInt(Integer::intValue).max().getAsInt() + 1;
        int maxY = y.stream().mapToInt(Integer::intValue).max().getAsInt() + 1;

        final int limit = 10_000;
        int count = 0;

        for (int xx = 0; xx < maxX; xx++) {
            for (int yy = 0; yy < maxY; yy++) {
                int distance = 0;
                for (int i = 0; i < x.size(); i++) {
                    int x0 = x.get(i);
                    int y0 = y.get(i);
                    int manhattan = Math.abs(xx - x0) + Math.abs(yy - y0);
                    distance += manhattan;
                }
                if (distance <= limit) {
                    ++count;
                }
            }
        }
        System.out.println(count);
    }

    static void p1() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input6.txt"));
        List<Integer> x = new LinkedList<>();
        List<Integer> y = new LinkedList<>();
        String line = br.readLine();
        while (line != null) {
            String[] xy = line.split(" ");
            x.add(Integer.valueOf(xy[0].substring(0, xy[0].length() - 1)));
            y.add(Integer.valueOf(xy[1]));
            line = br.readLine();
        }
        int maxX = x.stream().mapToInt(Integer::intValue).max().getAsInt() + 1;
        int maxY = y.stream().mapToInt(Integer::intValue).max().getAsInt() + 1;
        int[][][] distance = new int[maxX][maxY][2];
        for (int xx = 0; xx < maxX; xx++) {
            for (int yy = 0; yy < maxY; yy++) {
                distance[xx][yy][0] = -1;
            }
        }
        for (int i = 0; i < x.size(); i++) {
            int x0 = x.get(i);
            int y0 = y.get(i);
            for (int xx = 0; xx < maxX; xx++) {
                for (int yy = 0; yy < maxY; yy++) {
                    int manhattan = Math.abs(xx - x0) + Math.abs(yy - y0);
                    if (distance[xx][yy][0] < 0 || distance[xx][yy][1] >= manhattan) {
                        if (distance[xx][yy][1] == manhattan) {
                            distance[xx][yy][0] = x.size();
                        } else {
                            distance[xx][yy][0] = i;
                            distance[xx][yy][1] = manhattan;
                        }
                    }
                }
            }
        }
        int[] count = new int[x.size() + 1];
        for (int xx = 0; xx < maxX; xx++) {
            for (int yy = 0; yy < maxY; yy++) {
                ++count[distance[xx][yy][0]];
            }
        }
        for (int xx = 0; xx < maxX; xx++) {
            count[distance[xx][0][0]] = 0;
            count[distance[xx][maxY - 1][0]] = 0;
        }
        for (int yy = 0; yy < maxY; yy++) {
            count[distance[0][yy][0]] = 0;
            count[distance[maxX - 1][yy][0]] = 0;
        }
        System.out.println(IntStream.of(count).max().getAsInt());
    }

    public static void main(String[] args) throws IOException {
        p1();
        p2();
    }
}
