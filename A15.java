import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Integer.MAX_VALUE;

public class A15 {

    static class C {
        int x, y, turns = 0;
        char c;

        public C(int x, int y, char c) {
            this.x = x;
            this.y = y;
            this.c = c;
        }
    }

    static char[][] map;
    static int[][] hitpoints;
    static int rounds = 0;
    static int[][] distances, dist2;
    static boolean[][] hasMoved;

    public static void main(String[] args) throws IOException {
//        Scanner scanner = new Scanner(new File("input15_t4.txt"));
//        Scanner scanner = new Scanner(new File("input15_t3.txt"));
//        Scanner scanner = new Scanner(new File("input15_t2.txt"));
//        Scanner scanner = new Scanner(new File("input15_t5.txt"));
//        Scanner scanner = new Scanner(new File("input15_t1.txt"));
        Scanner scanner = new Scanner(new File("input15.txt"));

        final List<String> input = new LinkedList<>();
        while (scanner.hasNextLine()) {
            input.add(scanner.nextLine());
        }
        map = new char[input.size()][];
        hitpoints = new int[input.size()][input.get(0).length()];
        distances = new int[input.size()][input.get(0).length()];
        dist2 = new int[input.size()][input.get(0).length()];
        IntStream.range(0, input.size()).forEach(i -> map[i] = input.get(i).toCharArray());

        while (true) {
            hasMoved = new boolean[input.size()][input.get(0).length()];
            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[i].length; j++) {
                    if (hasMoved[i][j])
                        continue;
                    char c = map[i][j];
                    switch (c) {
                        case '#':
                        case '.':
                            continue;
                        case 'E':
                        case 'G':
                            System.out.printf("take turn: %s at %d,%d\n", Character.toString(c), i, j);
                            if (!doTurn(i, j) && noTargets(i, j)) {
                                pmap();
                                System.out.println(calculate());
                                System.exit(0);
                            }

                            pmap();
                    }
                }
            }
            ++rounds;
            System.out.println("after " + rounds);
            pmap();
        }
        //212812
        //209040
        //35235
    }

    private static boolean noTargets(int i, int j) {
        char t = map[i][j] == 'G' ? 'E' : 'G';
        for (int k = 0; k < map.length; k++) {
            if (String.valueOf(map[k]).contains(Character.toString(t)))
                return false;
        }
        return true;
    }

    private static int calculate() {
        int p = 0;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == 'G' || map[i][j] == 'E') {
                    System.out.print(map[i][j]);
                    p += 200 - hitpoints[i][j];
                }
            }
        }
        System.out.println();
        System.out.println(p + " * " + rounds + " = ");
        return p * rounds;
    }

    private static boolean doTurn(int i, int j) {
        getDistances(i, j, distances);
        pdistances(distances);
        final List<int[]> targetPos = findNearest(i, j);
        if (targetPos.isEmpty()) {
            return false;
        }
//        System.out.println(targetPos[0] + " " + targetPos[1]);
        int[] newPos = new int[]{i, j};
        if (targetPos.size() > 1 || (targetPos.size() == 1 && targetPos.get(0)[0] != i || targetPos.get(0)[1] != j)) {
//        return false;
            newPos = move(i, j, targetPos);
            System.out.println(i + "," + j + " -> " + newPos[0] + "," + newPos[1]);
        }
//        return false;
        hasMoved[newPos[0]][newPos[1]] = true;
        attack(newPos[0], newPos[1]);
        return true;
    }

    private static void pdistances(int[][] dd) {
        for (int i = 0; i < dd.length; i++) {
            int[] d = dd[i];
            System.out.println(String.join("", Arrays.stream(d).mapToObj(j -> j == MAX_VALUE ? " " : Integer.toString(j % 10)).collect(Collectors.toList())));
        }
    }

    private static void pmap() {
        for (int i = 0; i < map.length; i++) {
            char[] cc = map[i];
            System.out.print(String.valueOf(cc));
            for (int j = 0; j < cc.length; j++) {
                if (map[i][j] == 'G' || map[i][j] == 'E')
                    System.out.print("  " + map[i][j] + "(" + (200 - hitpoints[i][j]) + ") ");
            }
            System.out.println();
        }
    }

    private static int[] move(int i, int j, List<int[]> target) {
        int[] moveTo = target.stream().sorted((a, b) -> a[0] == b[0] ? Integer.compare(a[1], b[1]) : Integer.compare(a[0], b[0])).findFirst().get();
        moveTo = move(i, j, moveTo[0], moveTo[1]);
//        List<int[]> moveToList = target.stream().map(a -> move(i, j, a[0], a[1])).collect(Collectors.toList());
//        if (moveToList.size() > 1) {
//            moveToList.forEach(a -> System.out.println("moveTo? " + a[0] + " " + a[1]));
//        }
//        int[] moveTo = moveToList.stream().sorted((a, b) -> a[0] == b[0] ? Integer.compare(a[1], b[1]) : Integer.compare(a[0], b[0])).findFirst().get();
        int gotoI = moveTo[0], gotoJ = moveTo[1];
        map[gotoI][gotoJ] = map[i][j];
        map[i][j] = '.';
        hitpoints[gotoI][gotoJ] = hitpoints[i][j];
        return new int[]{gotoI, gotoJ};
    }

    static int[] move(int i, int j, int targetI, int targetJ) {
        getDistances(targetI, targetJ, dist2);
        pdistances(dist2);
        int lowest = Math.min(i > 0 ? dist2[i - 1][j] : MAX_VALUE,
                Math.min(j > 0 ? dist2[i][j - 1] : MAX_VALUE,
                        Math.min(i < dist2.length - 1 ? dist2[i + 1][j] : MAX_VALUE,
                                j < dist2[i].length - 1 ? dist2[i][j + 1] : MAX_VALUE)));
        if (i > 0 && dist2[i - 1][j] == lowest)
            return new int[]{i - 1, j};
        if (j > 0 && dist2[i][j - 1] == lowest)
            return new int[]{i, j - 1};
        if (j < dist2[i].length - 1 && dist2[i][j + 1] == lowest)
            return new int[]{i, j + 1};
        return new int[]{i + 1, j};
    }


    static void getDistances(int i, int j, int[][] distances) {
        for (int k = 0; k < distances.length; k++) Arrays.fill(distances[k], MAX_VALUE);
        distances[i][j] = 0;
        walk(i, j, distances);
    }

    static void walk(int i, int j, int[][] dd) {
        int distance = dd[i][j] + 1;
        if (i > 0 && map[i - 1][j] == '.' && dd[i - 1][j] > distance) {
            dd[i - 1][j] = distance;
            walk(i - 1, j, dd);
        }
        if (i < map.length - 1 && map[i + 1][j] == '.' && dd[i + 1][j] > distance) {
            dd[i + 1][j] = distance;
            walk(i + 1, j, dd);
        }
        if (j > 0 && map[i][j - 1] == '.' && dd[i][j - 1] > distance) {
            dd[i][j - 1] = distance;
            walk(i, j - 1, dd);
        }
        if (j < map[i].length - 1 && map[i][j + 1] == '.' && dd[i][j + 1] > distance) {
            dd[i][j + 1] = distance;
            walk(i, j + 1, dd);
        }
    }

    private static void attack(int i, int j) {
//        if (distances[i][j] == 1) {
        char t = map[i][j] == 'G' ? 'E' : 'G';
        int targeti = i, targetj = j, hp = -1;
        if (i > 0 && map[i - 1][j] == t) {
            targeti = i - 1;
            targetj = j;
            hp = hitpoints[targeti][targetj];
        }
        if (j > 0 && map[i][j - 1] == t && hitpoints[i][j - 1] > hp) {
            targeti = i;
            targetj = j - 1;
            hp = hitpoints[targeti][targetj];
        }
        if (j < map[i].length - 1 && map[i][j + 1] == t && hitpoints[i][j + 1] > hp) {
            targeti = i;
            targetj = j + 1;
            hp = hitpoints[targeti][targetj];
        }
        if (i < map.length - 1 && map[i + 1][j] == t && hitpoints[i + 1][j] > hp) {
            targeti = i + 1;
            targetj = j;
            hp = hitpoints[targeti][targetj];
        }
        if (hp < 0) {
            return;
        }
        hitpoints[targeti][targetj] += 3;
        System.out.println(targeti + "," + targetj + " = " + (200 - hitpoints[targeti][targetj]));
        if (hitpoints[targeti][targetj] >= 200) {
            map[targeti][targetj] = '.';
        }
//        }
    }

    static List<int[]> findNearest(int i, int j) {
        char t = map[i][j] == 'G' ? 'E' : 'G';
        List<int[]> result = new LinkedList<>();
        int minD = MAX_VALUE;
        for (int k = 0; k < map.length; k++) {
            for (int l = 0; l < map[k].length; l++) {
                if (distances[k][l] < MAX_VALUE
                        && distances[k][l] <= minD
                        && (
                        (k > 0 && map[k - 1][l] == t)
                                || (l > 0 && map[k][l - 1] == t)
                                || (k < map.length - 1 && map[k + 1][l] == t)
                                || (l < map[k].length - 1 && map[k][l + 1] == t)
                )) {
                    if (distances[k][l] < minD) {
                        result.clear();
                    }
                    result.add(new int[]{k, l});
                    minD = distances[k][l];
                }
            }
        }
        return result;
    }
}