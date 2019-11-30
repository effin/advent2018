import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;

public class A19 {

    static class C {
        final Consumer<int[]> op;
        final int[] abc;

        C(Consumer<int[]> op, int a, int b, int c) {
            this.op = op;
            this.abc = new int[]{0, a, b, c};
        }
    }

    static final List<C> program = new LinkedList<>();
    static int[] r = new int[6];

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

    static final Map<String, Consumer<int[]>> ops = new HashMap();

    static {
        ops.put("addr", faddr);
        ops.put("addi", faddi);
        ops.put("mulr", fmulr);
        ops.put("muli", fmuli);
        ops.put("banr", fbanr);
        ops.put("bani", fbani);
        ops.put("borr", fborr);
        ops.put("bori", fbori);
        ops.put("setr", fsetr);
        ops.put("seti", fseti);
        ops.put("gtir", fgtir);
        ops.put("gtri", fgtri);
        ops.put("gtrr", fgtrr);
        ops.put("eqir", feqir);
        ops.put("eqri", feqri);
        ops.put("eqrr", feqrr);
    }

    public static void p1(String[] args) throws IOException {
        Scanner scanner = new Scanner(new File("input19.txt"));

        final int ipr = Integer.parseInt(scanner.nextLine().substring(4));
        while (scanner.hasNextLine()) {
            String l = scanner.nextLine();
            C c = new C(ops.get(l.substring(0, 4)),
                    Integer.parseInt(l.substring(5, 6)),
                    Integer.parseInt(l.substring(7, l.indexOf(' ', 7))),
                    Integer.parseInt(l.substring(l.lastIndexOf(' ') + 1)));
            program.add(c);
        }

        int ip = 0;
        while (true) {
            r[ipr] = ip;
            C op = program.get(ip);
            op.op.accept(op.abc);
            ip = r[ipr] + 1;
            if (ip < 0 || ip >= program.size()) {
                System.out.println(r[0]);
                System.exit(0);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(new File("input19.txt"));

        final int ipr = Integer.parseInt(scanner.nextLine().substring(4));
        while (scanner.hasNextLine()) {
            String l = scanner.nextLine();
            C c = new C(ops.get(l.substring(0, 4)),
                    Integer.parseInt(l.substring(5, 6)),
                    Integer.parseInt(l.substring(7, l.indexOf(' ', 7))),
                    Integer.parseInt(l.substring(l.lastIndexOf(' ') + 1)));
            program.add(c);
        }

        r = new int[]{1, 10551314, 2, 3, 5275657, 10551314};

        int ip = 4;
        int i = 0;
        while (true) {
            r[ipr] = ip;
            C op = program.get(ip);
            op.op.accept(op.abc);
            if (i++ % 100000 == 0)
            System.out.printf("%d -- ip=%d --[%d, %d, %d, %d, %d, %d]\n", i, ip, r[0], r[1], r[2], r[3], r[4], r[5]);
            ip = r[ipr] + 1;
            if (ip < 0 || ip >= program.size()) {
                System.out.println(r[0]);
                System.exit(0);
            }
//            if (++i > 1000) break;
        }
    }

}
