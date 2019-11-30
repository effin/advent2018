import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class A14 {

    static int input = 765071;
    //    static final int[] solution = {0, 1, 2, 4, 5};
//    static final int[] solution = {5, 9, 4, 1, 4};
    static final int[] solution = {7, 6, 5, 0, 7, 1};
    //    static int input = 59414;
    static List<Integer> r = new ArrayList<>(Arrays.asList(3, 7));
    static int[] c = new int[]{0, 1};

    public static void p1() {
        p();
        while (r.size() < input + 10) {
            int v = r.get(c[0]) + r.get(c[1]);
            if (v > 9) {
                r.add(v / 10);
            }
            r.add(v % 10);
            c[0] = (c[0] + r.get(c[0]) + 1) % r.size();
            c[1] = (c[1] + r.get(c[1]) + 1) % r.size();
            p();
        }
        System.out.println(String.join("", r.subList(input, input + 10).stream().map(i -> i.toString()).collect(Collectors.toList())));
    }

    public static void main(String[] args) {
        p();
        while (true) {
            int v = r.get(c[0]) + r.get(c[1]);
            if (v > 9) {
                r.add(v / 10);
                if (isSolved()) {
                    break;
                }
            }
            r.add(v % 10);
            if (isSolved()) {
                break;
            }
            c[0] = (c[0] + r.get(c[0]) + 1) % r.size();
            c[1] = (c[1] + r.get(c[1]) + 1) % r.size();
            p();
        }
        System.out.println(r.size() - solution.length);
    }

    private static boolean isSolved() {
        if (r.size() < solution.length) {
            return false;
        }
        for (int i = 0; i < solution.length; ++i) {
            if (solution[solution.length - i - 1] != r.get(r.size() - 1 - i)) {
                return false;
            }
        }
        return true;
    }

    static boolean compare(int[] a1, int[] a2, int a2offset) {
        for (int i = 0; i < a1.length; ++i) {
            if (a1[i] != a2[(i + a2offset) % a2.length]) {
                return false;
            }
        }
        return true;
    }

    private static void p() {
//        for (int i = 0; i < r.size(); ++i) {
//            if (i == c[0]) {
//                System.out.print("(" + r.get(i) + ")");
//            } else if (i == c[1]) {
//                System.out.print("[" + r.get(i) + "]");
//            } else {
//                System.out.print(" " + r.get(i) + " ");
//            }
//        }
//        System.out.println();
    }
}