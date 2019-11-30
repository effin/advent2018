import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class A16 {

    static class C {
        int[] before, op, after;

        boolean allEqButC() {
            return (op[3] == 0 || before[0] == after[0])
                    && (op[3] == 1 || before[1] == after[1])
                    && (op[3] == 2 || before[2] == after[2])
                    && (op[3] == 3 || before[3] == after[3]);
        }
    }

    static Predicate<C> addr = c -> c.after[c.op[3]] == c.before[c.op[1]] + c.before[c.op[2]] && c.allEqButC();
    static Predicate<C> addi = c -> c.after[c.op[3]] == c.before[c.op[1]] + c.op[2] && c.allEqButC();
    static Predicate<C> mulr = c -> c.after[c.op[3]] == c.before[c.op[1]] * c.before[c.op[2]] && c.allEqButC();
    static Predicate<C> muli = c -> c.after[c.op[3]] == c.before[c.op[1]] * c.op[2] && c.allEqButC();
    static Predicate<C> banr = c -> c.after[c.op[3]] == (c.before[c.op[1]] & c.before[c.op[2]]) && c.allEqButC();
    static Predicate<C> bani = c -> c.after[c.op[3]] == (c.before[c.op[1]] & c.op[2]) && c.allEqButC();
    static Predicate<C> borr = c -> c.after[c.op[3]] == (c.before[c.op[1]] | c.before[c.op[2]]) && c.allEqButC();
    static Predicate<C> bori = c -> c.after[c.op[3]] == (c.before[c.op[1]] | c.op[2]) && c.allEqButC();
    static Predicate<C> setr = c -> c.after[c.op[3]] == c.before[c.op[1]] && c.allEqButC();
    static Predicate<C> seti = c -> c.after[c.op[3]] == c.op[1] && c.allEqButC();
    static Predicate<C> gtir = c -> c.after[c.op[3]] == (c.op[1] > c.before[c.op[2]] ? 1 : 0) && c.allEqButC();
    static Predicate<C> gtri = c -> c.after[c.op[3]] == (c.before[c.op[1]] > c.op[2] ? 1 : 0) && c.allEqButC();
    static Predicate<C> gtrr = c -> c.after[c.op[3]] == (c.before[c.op[1]] > c.before[c.op[2]] ? 1 : 0) && c.allEqButC();
    static Predicate<C> eqir = c -> c.after[c.op[3]] == (c.op[1] == c.before[c.op[2]] ? 1 : 0) && c.allEqButC();
    static Predicate<C> eqri = c -> c.after[c.op[3]] == (c.before[c.op[1]] == c.op[2] ? 1 : 0) && c.allEqButC();
    static Predicate<C> eqrr = c -> c.after[c.op[3]] == (c.before[c.op[1]] == c.before[c.op[2]] ? 1 : 0) && c.allEqButC();

    static final List<C> obs = new LinkedList<>();
    static final List<Predicate<C>> opsPredicates = Arrays.asList(
            addr, addi, mulr, muli, banr, bani, borr, bori, setr, seti, gtir, gtri, gtrr, eqir, eqri, eqrr
    );

    public static void p1(String[] args) throws IOException {
        Scanner scanner = new Scanner(new File("input16_1.txt"));

        while (scanner.hasNextLine()) {
            String l1 = scanner.nextLine();
            if (l1.trim().length() == 0) {
                break;
            }
            String l2 = scanner.nextLine();
            String l3 = scanner.nextLine();
            scanner.nextLine();
            C c = new C();
            obs.add(c);
            c.before = Arrays.stream(l1.substring(9, l1.length() - 1).split(", ")).mapToInt(Integer::parseInt).toArray();
            c.op = Arrays.stream(l2.split(" ")).mapToInt(Integer::parseInt).toArray();
            c.after = Arrays.stream(l3.substring(9, l1.length() - 1).split(", ")).mapToInt(Integer::parseInt).toArray();
        }
        int count = 0;
        obses:
        for (C c : obs) {
            int opsCount = 0;
            for (Predicate<C> p : opsPredicates) {
                if (p.test(c)) {
                    ++opsCount;
//                    System.out.println(opsCount);
                    if (opsCount >= 3) {
                        ++count;
                        continue obses;
                    }
                }
            }
        }
        System.out.println(count);
    }

    //-----------------------

    static int[] r = new int[4];

    static Consumer<int[]> faddr = c -> r[c[3]] = r[c[1]] + r[c[2]];
    static Consumer<int[]> faddi = c -> r[c[3]] = r[c[1]] + c[2];
    static Consumer<int[]> fmulr = c -> r[c[3]] = r[c[1]] * r[c[2]];
    static Consumer<int[]> fmuli = c -> r[c[3]] = r[c[1]] * c[2];
    static Consumer<int[]> fbanr = c -> r[c[3]] = (r[c[1]] & r[c[2]]);
    static Consumer<int[]> fbani = c -> r[c[3]] = (r[c[1]] & c[2]);
    static Consumer<int[]> fborr = c -> r[c[3]] = (r[c[1]] | r[c[2]]);
    static Consumer<int[]> fbori = c -> r[c[3]] = (r[c[1]] | c[2]);
    static Consumer<int[]> fsetr = c -> r[c[3]] = r[c[1]];
    static Consumer<int[]> fseti = c -> r[c[3]] = c[1];
    static Consumer<int[]> fgtir = c -> r[c[3]] = (c[1] > r[c[2]] ? 1 : 0);
    static Consumer<int[]> fgtri = c -> r[c[3]] = (r[c[1]] > c[2] ? 1 : 0);
    static Consumer<int[]> fgtrr = c -> r[c[3]] = (r[c[1]] > r[c[2]] ? 1 : 0);
    static Consumer<int[]> feqir = c -> r[c[3]] = (c[1] == r[c[2]] ? 1 : 0);
    static Consumer<int[]> feqri = c -> r[c[3]] = (r[c[1]] == c[2] ? 1 : 0);
    static Consumer<int[]> feqrr = c -> r[c[3]] = (r[c[1]] == r[c[2]] ? 1 : 0);

    static final Consumer<int[]>[] ops = new Consumer[]{
            faddr, faddi, fmulr, fmuli, fbanr, fbani, fborr, fbori, fsetr, fseti, fgtir, fgtri, fgtrr, feqir, feqri, feqrr
    };

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(new File("input16_1.txt"));

        while (scanner.hasNextLine()) {
            String l1 = scanner.nextLine();
            if (l1.trim().length() == 0) {
                break;
            }
            String l2 = scanner.nextLine();
            String l3 = scanner.nextLine();
            scanner.nextLine();
            C c = new C();
            obs.add(c);
            c.before = Arrays.stream(l1.substring(9, l1.length() - 1).split(", ")).mapToInt(Integer::parseInt).toArray();
            c.op = Arrays.stream(l2.split(" ")).mapToInt(Integer::parseInt).toArray();
            c.after = Arrays.stream(l3.substring(9, l1.length() - 1).split(", ")).mapToInt(Integer::parseInt).toArray();
        }

        Set<Integer>[] opsindex = new Set[16];
        IntStream.range(0, 16).forEach(i -> opsindex[i] = IntStream.range(0, 16).mapToObj(Integer::valueOf).collect(Collectors.toSet()));

        for (C c : obs) {
            List<Integer> remove = new LinkedList<>();
            for (int i : opsindex[c.op[0]]) {
                if (!opsPredicates.get(i).test(c)) {
                    remove.add(i);
                }
            }
            opsindex[c.op[0]].removeAll(remove);
        }

        while (removed) {
            removed = false;
            IntStream.range(0, 16).forEach(i -> {
                if (opsindex[i].size() == 1) {
                    int op = opsindex[i].iterator().next();
                    for (int j = 0; j < 16; j++) {
                        if (i == j) continue;
                        removed = removed || opsindex[j].remove(op);
                    }
                }
            });
        }

        IntStream.range(0, 16).forEach(i -> {
            System.out.println(i + " " + opsindex[i]);
        });

        scanner.nextLine();
        while (scanner.hasNextLine()) {
            int[] op = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
            ops[opsindex[op[0]].iterator().next()].accept(op);
        }
        System.out.println(r[0]);
    }

    static boolean removed = true;
}
