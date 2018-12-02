import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class A1 {

    static void p1() throws IOException {
        final List<Integer> c = getIntegers();
        int f = 0;
        for (int ch : c) {
            f += ch;
        }
        System.out.println(f);
    }

    static void p2() throws IOException {
        final List<Integer> c = getIntegers();
        int f = 0;
        final Set<Integer> hits = new HashSet<>();
        hits.add(0);
        while (true) {
            for (int ch : c) {
                f += ch;
                if (!hits.add(f)) {
                    System.out.println(f);
                    System.exit(0);
                }
            }
        }
    }

    private static List<Integer> getIntegers() throws IOException {
        final List<Integer> c = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("input1.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                c.add(Integer.parseInt(line));
            }
        }
        return c;
    }

    public static void main(String[] args) throws IOException {
        p1();
        p2();
    }
}
