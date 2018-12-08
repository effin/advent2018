import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class A5 {

    static int diff = Math.abs('A' - 'a');
    static String line;

    static boolean reduce(int offset) {
        StringBuffer newLine = new StringBuffer();
        if (offset > 0 && offset <= line.length())
            newLine.append(line.substring(0, offset));
        char[] c = line.toCharArray();
        for (int i = 1 + offset; i < c.length; i = i + 2) {
            if (Math.abs(c[i - 1] - c[i]) == diff) {
                continue;
            }
            newLine.append(c[i - 1]);
            newLine.append(c[i]);
        }
        if ((line.length() - offset) % 2 > 0) {
            newLine.append(c[c.length - 1]);
        }
        boolean result = newLine.length() < line.length();
        line = newLine.toString();
        return result;
    }

    static void p1() {
        int changes = 0;
        do {
            changes = 0;
            changes += reduce(0) ? 1 : 0;
            changes += reduce(1) ? 1 : 0;
        } while (changes > 0);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input5.txt"));
        final String original = br.readLine();
        line = original;
        p1();
        System.out.println(line.length());

        int best = line.length();
        for (char c = 'a'; c <= 'z'; ++c) {
            line = original.replaceAll(String.valueOf(c), "").replaceAll(String.valueOf((char) (c - diff)), "");
            p1();
            if (line.length() < best) {
                best = line.length();
            }
        }
        System.out.println(best);
    }
}
