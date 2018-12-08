import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class A1 {

    static int p1(List<Integer> c) throws IOException {
        int f = 0;
        for (int ch : c) {
            f += ch;
        }
        return f;
    }

    static int p2(List<Integer> c) {
        int f = 0;
        final Set<Integer> hits = new HashSet<>();
        hits.add(0);
        while (true) {
            for (int ch : c) {
                f += ch;
                if (!hits.add(f)) {
                    return f;
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        final List<Integer> c = new LinkedList<>();
        BufferedReader br = new BufferedReader(new FileReader("input1.txt"));
        String line;
        while ((line = br.readLine()) != null) {
            c.add(Integer.parseInt(line));
        }

        System.out.printf("%d %d", p1(c), p2(c));
    }
}
