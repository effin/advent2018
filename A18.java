import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class A18 {

    static char[][] map;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("input18.txt"));
        List<String> rows = new LinkedList<>();
        while (scanner.hasNextLine()) {
            rows.add(scanner.nextLine());
        }

        map = new char[rows.size()][rows.get(0).length()];
        for (int y = 0; y < map.length; ++y) map[y] = rows.get(y).toCharArray();

//        p();
        int m = 0;
        for (; m < 10; ++m) tick();
        System.out.println(calculate());
        for (; m < 100; ++m) tick();
        System.out.println(calculate());

        // part 2

        for (; m < 1000; ++m) tick();

        p();
            System.out.printf("%d %d\n", m, calculate());

//        for (int n = 0; n < 100; ++n) {
//            System.out.printf("%d %d\n", m++, calculate());
//            tick();
//        }
        System.out.println(1_000L % 28L);
        System.out.println(1_000_000_000L % 28L);
    }

    private static void tick() {
//        p();
        char[][] newmap = new char[map.length][];
        for (int i = 0; i < map.length; i++) {
            newmap[i] = new char[map[i].length];
            for (int j = 0; j < map[i].length; j++) {
                newmap[i][j] = getNext(i, j);
            }
        }
        map = newmap;
    }

    private static char getNext(int i, int j) {
        switch (map[i][j]) {
            case '.':
                return getNextFromOpen(i, j);
            case '|':
                return getNextFromTree(i, j);
        }
        return getNextFromLumberyard(i, j);
    }

    private static char getNextFromLumberyard(int i, int j) {
        int lumberyard = 0, tree = 0;
        for (int y = i - 1; y <= i + 1; ++y) {
            if (y < 0 || y >= map.length) continue;
            for (int x = j - 1; x <= j + 1; ++x) {
                if (x < 0 || x >= map[y].length) continue;
                lumberyard += map[y][x] == '#' ? 1 : 0;
                tree += map[y][x] == '|' ? 1 : 0;
            }
        }
        return lumberyard >= 2 && tree >= 1 ? '#' : '.';
    }

    private static char getNextFromTree(int i, int j) {
        int count = 0;
        for (int y = i - 1; y <= i + 1 && count < 3; ++y) {
            if (y < 0 || y >= map.length) continue;
            for (int x = j - 1; x <= j + 1 && count < 3; ++x) {
                if (x < 0 || x >= map[y].length) continue;
                count += map[y][x] == '#' ? 1 : 0;
            }
        }
        return count >= 3 ? '#' : '|';
    }

    private static char getNextFromOpen(int i, int j) {
        int count = 0;
        for (int y = i - 1; y <= i + 1 && count < 3; ++y) {
            if (y < 0 || y >= map.length) continue;
            for (int x = j - 1; x <= j + 1 && count < 3; ++x) {
                if (x < 0 || x >= map[y].length) continue;
                count += map[y][x] == '|' ? 1 : 0;
            }
        }
        return count >= 3 ? '|' : '.';
    }

    private static int calculate() {
        int lumberyard = 0, tree = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == '|') {
                    ++lumberyard;
                } else if (map[i][j] == '#') {
                    ++tree;
                }
            }
        }
        return lumberyard * tree;
    }

    static void p() {
        System.out.println();
        for (int y = 0; y < map.length; ++y) {
            System.out.println(String.valueOf(map[y]));
        }
    }
}