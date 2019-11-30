import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class A17 {

    static int minx, maxx, miny, maxy, realminy;

    static class R {
        final char xy;
        final int x, y1, y2;


        R(char xy, int x, int y1, int y2) {
            this.xy = xy;
            this.x = x;
            this.y1 = y1;
            this.y2 = y2;
        }
    }

    static char[][] map;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("input17.txt"));
        List<R> rows = new LinkedList<>();
        while (scanner.hasNextLine()) {
            String l = scanner.nextLine();
            char c = l.charAt(0);
            int x = Integer.parseInt(l.substring(2, l.indexOf(',')));
            int y1 = Integer.parseInt(l.substring(l.indexOf('=', l.indexOf(',')) + 1, l.indexOf("..")));
            int y2 = Integer.parseInt(l.substring(l.indexOf("..") + 2));
            rows.add(new R(c, x, y1, y2));
        }
        minx = Math.min(rows.stream().filter(r -> r.xy == 'x').mapToInt(r -> r.x).min().getAsInt(),
                rows.stream().filter(r -> r.xy == 'y').mapToInt(r -> r.y1).min().getAsInt()) - 1;
        realminy = Math.min(rows.stream().filter(r -> r.xy == 'y').mapToInt(r -> r.x).min().getAsInt(),
                rows.stream().filter(r -> r.xy == 'x').mapToInt(r -> r.y1).min().getAsInt());
        miny=0;
        maxx = Math.max(rows.stream().filter(r -> r.xy == 'x').mapToInt(r -> r.x).max().getAsInt(),
                rows.stream().filter(r -> r.xy == 'y').mapToInt(r -> r.y2).max().getAsInt()) + 1;
        maxy = Math.max(rows.stream().filter(r -> r.xy == 'y').mapToInt(r -> r.x).max().getAsInt(),
                rows.stream().filter(r -> r.xy == 'x').mapToInt(r -> r.y2).max().getAsInt()) + 1;

        map = new char[maxy - miny + 1][maxx - minx + 1];
        for (int y = miny; y <= maxy; ++y) Arrays.fill(map[y - miny], '.');

        for (R r : rows) {
            for (int i = r.y1; i <= r.y2; ++i) {
                map[r.xy == 'y' ? r.x - miny : i - miny][r.xy == 'x' ? r.x - minx : i - minx] = '#';
            }
        }

        drip(0, 500 - minx);
//        System.out.println("ready");
        p();
        System.out.println(calculate());
    }

    private static int calculate() throws FileNotFoundException {
//        Scanner s = new Scanner(new File("solution17.txt"));
//        s.nextLine();
//        int scount = 0;
        int countRetained = 0, countFlowing = 0;
        for (int i = realminy; i <maxy; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == '~' ) {
                    ++countRetained;
                } else                 if (map[i][j] == '|') {
                    ++countFlowing;
                }
            }
//            String l = s.nextLine();
//            for (int j = 0; j < l.length(); j++) {
//                if (l.charAt(j) == '~' || l.charAt(j)== '|') {
//                    ++scount;
//                }
//            }
//            if (scount != count) {
//                System.out.printf("i = %d, count = %d scount = %d\n", i, count, scount);
//                System.out.println(map[i]);
//                System.out.println(l);
//                System.exit(1);
//            }

        }
        System.out.println("retained = " + countRetained);
        return countRetained + countFlowing;
    }

    static void drip(int y, int x) {
//        System.out.printf("drip %d %d \n", y, x);
//        p();

        if (y >= map.length || x < 0 || x >= map[y].length) return;
        if (map[y][x] != '.') return;
        drip(y + 1, x);
        if (isClosed(y + 1, x)) {
//            System.out.println("yes");
            convertToSolid(y + 1, x);
        } //else System.out.println("no");
        map[y][x] = '|';
        if (isSolid(y + 1, x)) {
//            dripSideways(y, x - 1, -1);
//            dripSideways(y, x + 1, +1);
            drip(y, x - 1);
            drip(y, x + 1);
        }
    }

    static boolean isClosed(int y, int x) {
//        p();
//        System.out.printf("isClosed %d %d \n", y, x);
        if ( y >= map.length) return false;
//        if (map[y + 1][x] != '#') return false;
        for (int xx = x - 1; xx >= 0; --xx) {
            if (map[y][xx] == '#') break;
            if (map[y][xx] == '.') return false;
//            if (map[y + 1][xx] == '.') return false;
        }
        for (int xxx = x + 1; xxx < map[y].length; ++xxx) {
            if (map[y][xxx] == '#') return true;
            if (map[y][xxx] == '.') return false;
//            if (map[y + 1][xxx] == '.') return false;
        }
        return false; // unreachable
    }

    static void convertToSolid(int y, int x) {
//        p();
//        System.out.printf("flow %d %d \n", y, x);

        for (int xx = x; map[y][xx] != '#'; --xx) map[y][xx] = '~';
        for (int xxx = x + 1; map[y][xxx] != '#'; ++xxx) map[y][xxx] = '~';
    }

    private static boolean isSolid(int y, int x) {
        return y < map.length && (map[y][x] == '#' || map[y][x] == '~');
    }

    //251
    //39150 39151
    static void p() {
        System.out.println();
        for (int y = miny; y <= maxy; ++y) {
            System.out.println(String.valueOf(map[y - miny]));
        }
    }
}