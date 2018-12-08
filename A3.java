import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static java.lang.Integer.parseInt;
import static java.util.stream.Collectors.toSet;

public class A3 {

    static class F {
        public final int x, y, w, h;
        public final String id;

        F(int x, int y, int w, int h, String id) {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
            this.id = id;
        }

        static F parse(String line) {
            final String[] parts = line.split(" ");
            final String[] xy = parts[2].split(",");
            final String[] wh = parts[3].split("x");
            return new F(parseInt(xy[0]), parseInt(xy[1].substring(0, xy[1].length() - 1)), parseInt(wh[0]), parseInt(wh[1]), parts[0]);
        }
    }

    public static void main(String[] args) throws IOException {
        final List<F> input = new LinkedList<>();
        BufferedReader br = new BufferedReader(new FileReader("input3.txt"));
        String line;
        while ((line = br.readLine()) != null) {
            input.add(F.parse(line));
        }
        final int maxX = input.stream().mapToInt(f -> f.x + f.w).max().getAsInt();
        final int maxY = input.stream().mapToInt(f -> f.y + f.h).max().getAsInt();
        int count = 0;
        final Set<F> overlapping = new HashSet<>();
        for (int x = 0; x <= maxX; ++x) {
            final int xx = x;
            for (int y = 0; y <= maxY; ++y) {
                final int yy = y;
                final Set<F> fs = input.stream().filter(f -> xx >= f.x && xx < f.x + f.w && yy >= f.y && yy < f.y + f.h).collect(toSet());
                if (fs.size() > 1) {
                    ++count;
                    overlapping.addAll(fs);
                }
            }
        }
        System.out.println(count);
        System.out.println(input.stream().filter(f -> !overlapping.contains(f)).findFirst().get().id);
    }
}
