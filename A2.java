import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class A2 {

    static void p1() throws IOException {
        final List<String> c = getStrings();
        int two = 0, three = 0;
        for (String ch : c) {
            two += countAny(ch, 2);
            three += countAny(ch, 3);
        }
        System.out.println(two * three);
    }

    static int countAny(String ch, final int count) {
        final int[] counters = new int[30];
        for (char c : ch.toCharArray()) {
            ++counters[c - 'a'];
        }
        return Arrays.stream(counters).filter(i -> i == count).findAny().isPresent() ? 1 : 0;
    }

    static void p2() throws IOException {
        final List<String> c = getStrings();
        for (String ch1 : c) {
            for (String ch2 : c) {
                if (differsInOne(ch1, ch2)) {
                    System.out.println(removeDiffChar(ch1, ch2));
                    return;
                }
            }
        }
    }

    static String removeDiffChar(String ch1, String ch2) {
        final StringBuilder s = new StringBuilder();
        for (int i = 0; i < ch1.length(); ++i) {
            if (ch1.toCharArray()[i] == ch2.toCharArray()[i]) {
                s.append(ch1.toCharArray()[i]);
            }
        }
        return s.toString();
    }

    static boolean differsInOne(String ch1, String ch2) {
        boolean foundDiff = false;
        for (int i = 0; i < ch1.length(); ++i) {
            if (ch1.toCharArray()[i] != ch2.toCharArray()[i]) {
                if (foundDiff) {
                    return false;
                }
                foundDiff = true;
            }
        }
        return foundDiff;
    }

    static List<String> getStrings() throws IOException {
        final List<String> c = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("input2.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                c.add(line);
            }
        }
        return c;
    }

    public static void main(String[] args) throws IOException {
        p1();
        p2();
    }
}
