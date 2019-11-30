public class A11 {

//    static int serialNumber = 18;
//    static int serialNumber = 42;
    static int serialNumber = 5177;

    static int power(int x, int y) {
        int p = ((x + 10) * y + serialNumber) * (x + 10);
        p = (p % 1000) / 100;
        return p - 5;
    }

    public static void p1() {
        int[][] p = new int[300][300];
        for (int x = 0; x < 300; ++x) {
            for (int y = 0; y < 300; y++) {
                p[x][y] = power(x + 1, y + 1);
            }
        }
        int maxX = 0, maxY = 0, maxP = -1000;
        for (int x = 0; x < 298; ++x) {
            for (int y = 0; y < 298; y++) {
                int sum = p[x][y] + p[x + 1][y] + p[x + 2][y]
                        + p[x][y + 1] + p[x + 1][y + 1] + p[x + 2][y + 1]
                        + p[x][y + 2] + p[x + 1][y + 2] + p[x + 2][y + 2];
                if (sum > maxP) {
                    maxP = sum;
                    maxX = x + 1;
                    maxY = y + 1;
                }
            }
        }
        System.out.printf("%d,%d %d", maxX, maxY, maxP);
    }

    public static void main(String[] args) {
        int[][] p = new int[300][300];
        for (int x = 0; x < 300; ++x) {
            for (int y = 0; y < 300; y++) {
                p[x][y] = power(x + 1, y + 1);
            }
        }
        int maxX = 0, maxY = 0, maxP = -1000, maxS = 0;
        for (int x = 0; x < 300; ++x) {
            for (int y = 0; y < 300; y++) {
                for (int s = 1; s <= 300 - x && s <= 300 - y; s++) {
                    int sum = 0;
                    for (int dx = 0; dx < s; dx++) {
                        for (int dy = 0; dy < s; dy++) {
                            sum += p[x + dx][y + dy];
                        }
                    }
                    if (sum > maxP) {
                        maxP = sum;
                        maxX = x + 1;
                        maxY = y + 1;
                        maxS = s;
                    }
                }
            }
        }
        System.out.printf("%d,%d,%s %d", maxX, maxY, maxS, maxP);
    }
}
