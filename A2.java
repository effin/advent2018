import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class A2 {

    static int p1(List<String> c) {
        int two = 0, three = 0;
        for (String ch : c) {
            two += countAny(ch, 2);
            three += countAny(ch, 3);
        }
        return two * three;
    }

    static int countAny(String ch, final int count) {
        final int[] counters = new int[30];
        for (char c : ch.toCharArray()) {
            ++counters[c - 'a'];
        }
        return Arrays.stream(counters).filter(i -> i == count).findAny().isPresent() ? 1 : 0;
    }

    static String p2(List<String> c) {
        for (String ch1 : c) {
            for (String ch2 : c) {
                if (differsInOne(ch1, ch2)) {
                    return removeDiffChar(ch1, ch2);
                }
            }
        }
        return null;
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

    public static void main(String[] args) throws IOException {
        final List<String> c = new LinkedList<>();
        BufferedReader br = new BufferedReader(new FileReader("input2.txt"));
        String line;
        while ((line = br.readLine()) != null) {
            c.add(line);
        }
        System.out.printf("%d %s", p1(c), p2(c));
    }
}
