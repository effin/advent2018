import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class A10 {


    static class Point {
        int x, y;
        final int vx, vy;

        Point(int vx, int vy, int x, int y) {
            this.vx = vx;
            this.vy = vy;
            this.x = x;
            this.y = y;
        }

        void move() {
            x += vx;
            y += vy;
            minX = Math.min(x, minX);
            minY = Math.min(y, minY);
            maxX = Math.max(x, maxX);
            maxY = Math.max(y, maxY);
        }

        void back() {
            x -= vx;
            y -= vy;
            minX = Math.min(x, minX);
            minY = Math.min(y, minY);
            maxX = Math.max(x, maxX);
            maxY = Math.max(y, maxY);
        }
    }

    static long minX, maxX, minY, maxY;
//    static int minX, minY, maxX, maxY, xoff, yoff;
//    static int[] countInRow;

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(new File("input10.txt"));

        List<Point> points = new LinkedList<>();
        while (scanner.hasNextLine()) {
            String l = scanner.nextLine();
            if (l.length() > 42) {
                int x = Integer.parseInt(l.substring(10, 16).trim());
                int y = Integer.parseInt(l.substring(18, 24).trim());
                int vx = Integer.parseInt(l.substring(36, 38).trim());
                int vy = Integer.parseInt(l.substring(40, 42).trim());
                points.add(new Point(vx, vy, x, y));
            } else {
                int x = Integer.parseInt(l.substring(10, 12).trim());
                int y = Integer.parseInt(l.substring(14, 16).trim());
                int vx = Integer.parseInt(l.substring(28, 30).trim());
                int vy = Integer.parseInt(l.substring(32, 34).trim());
                points.add(new Point(vx, vy, x, y));
            }
        }

//        minX = points.stream().mapToInt(p -> p.x).min().getAsInt();
//        maxX = points.stream().mapToInt(p -> p.x).max().getAsInt();
//        minY = points.stream().mapToInt(p -> p.y).min().getAsInt();
//        maxY = points.stream().mapToInt(p -> p.y).max().getAsInt();
//        xoff = minX < 0 ? Math.abs(minX) : -1 * minX;
//        yoff = minY < 0 ? Math.abs(minY) : -1 * minY;
//        c = new char[maxX - minX + 1][maxY - minY + 1];
//        for (int i = 0; i < c.length; i++) {
//            Arrays.fill(c[i], ' ');
//        }

//        countInRow = new int[maxY - minY + 1 + 2 * pad];
//        ++countInRow[pad];
        area = Long.MAX_VALUE;
        points.forEach(p -> p.move());
        int count = 0;
        while (!message(area)) {
            System.out.print(".");
//        printMessage(points);
//            countInRow = new int[maxY - minY + 1 + 2 * pad];
            points.forEach(p -> p.move());
//            printMessage(points);
            ++count;
        }
        points.forEach(p -> p.back());
        System.out.println(count);
        printMessage(points);

    }

    //static int skipRows = 10000;
    static void printMessage(List<Point> points) {
        System.out.println("ok");
        for (long y = minY; y <= maxY; y++) {
//            if (countInRow[y  + yoff + pad] == 0) continue;
            final long yy = y;
//            System.out.print((y - minY) % 10);
            StringBuilder sbuff = new StringBuilder();
//            sbuff.append((y - minY) % 10);
            for (long x = minX; x <= maxX; x++) {
                final long xx = x;
                boolean exist = points.stream().filter(p -> p.x == xx && p.y == yy).count() > 0;
//                System.out.print(exist ? "#" : ' ');
                sbuff.append(exist ? "#" : " ");
            }
            System.out.println(sbuff.toString());
        }
    }

    static long area;

    static boolean message(long previousArea) {
        long newArea = Math.abs((maxX - minX + 1) * (maxY - minY + 1));
        System.out.println(newArea);
        boolean message = newArea > previousArea;
        area = newArea;
        minX = Long.MAX_VALUE;
        minY = Long.MAX_VALUE;
        maxX = Long.MIN_VALUE;
        maxY = Long.MIN_VALUE;
        return message;
    }

//    static boolean message(List<Point> points) {
//        final int pointsInLineMin = 5;
//        return existLineOf(pointsInLineMin, points);
//        return !anyLonely(points);
//        return emptyRows(skipRows);
//    }

//    private static boolean emptyRows(int i) {
//        for (int j = 0; j < i; j++) {
//            if (countInRow[j + pad] > 0)
//                return false;
//        }
//        return true;
//    }
//
//    static boolean anyLonely(List<Point> points) {
//        List<Point> withNeighbors = new LinkedList<>();
//        for (Point p : points) {
//            if (withNeighbors.contains(p)) {
//                continue;
//            }
//            List<Point> neighbors = points.stream().filter(pp -> pp != p
//                            && (
//                            (pp.x == p.x && (pp.y == p.y + 1 || pp.y == p.y - 1))
//                                    || (pp.y == p.y && (pp.x == p.x + 1 || pp.x == p.x - 1))
//                    )
//            ).collect(toList());
//            if (neighbors.isEmpty()) {
//                return true;
//            }
//            withNeighbors.addAll(neighbors);
//        }
//        return false;
//    }
//
//    static boolean existLineOf(int lineSize, List<Point> points) {
//        int minX = points.stream().mapToInt(p -> p.x).min().getAsInt();
//        int maxX = points.stream().mapToInt(p -> p.x).max().getAsInt();
//        for (int x = minX; x <= maxX; x++) {
//            final int xx = x;
//            List<Point> onThisRow = points.stream().filter(p -> p.x == xx).sorted(Comparator.comparingInt(p -> p.y)).collect(toList());
//            if (onThisRow.size() >= lineSize) {
//                boolean noGaps = (onThisRow.get(onThisRow.size() - 1).y - onThisRow.get(0).y + 1) == onThisRow.size();
//                if (noGaps) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
}
