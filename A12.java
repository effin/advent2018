import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class A12 {

    static int offset = 500;
    static int end = 520;

    final static int generations = 150;

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(new File("input12.txt"));

        int size = 1000;
        char[] state = new char[size];
        Arrays.fill(state, '.');
        char[] init = scanner.nextLine().substring(15).toCharArray();
        System.arraycopy(init, 0, state, offset, init.length);
        end = 500 + init.length;

        scanner.nextLine();
        Map<String, Boolean> rules = new HashMap<>();
        while (scanner.hasNextLine()) {
            String l = scanner.nextLine();
            rules.put(l.substring(0, 5), l.charAt(9) == '#' ? true : false);
        }

        int s = 0, prevSum;
        for (int i = 0; i < generations; i++) {
            prevSum = s;
            s = sum(state);
            System.out.println(p(state) + s + " " + (s - prevSum) + " " + i);
            state = generate(state, rules);
        }
        prevSum = s;
        s = sum(state);
        System.out.println(p(state) + s + " " + (s - prevSum));
        System.out.println(s);
        BigInteger bigInteger = new BigInteger("50000000000");
        BigInteger x = bigInteger.subtract(BigInteger.valueOf(140)).multiply(BigInteger.valueOf(78)).add(BigInteger.valueOf(13132));
        System.out.println(x);
    }

    private static int sum(char[] state) {
        int sum = 0;
        for (int i = offset - 2; i < end + 2; i++) {
            if (state[i] == '#') {
                sum += i - 500;
            }
        }
        return sum;
    }

    private static String p(char[] state) {
        char[] newState = new char[state.length];
        Arrays.fill(newState, '.');
        System.arraycopy(state, offset, newState, offset, end - offset);
        return new String(newState);

    }

    private static char[] generate(char[] state, Map<String, Boolean> rules) {
        char[] newState = new char[state.length];
        Arrays.fill(newState, '.');
        offset -= 2;
        end += 2;
        for (int i = offset; i < end; i++) {
            String stateString = new String(Arrays.copyOfRange(state, i - 2, i + 3));
            boolean pot = rules.getOrDefault(stateString, false);
            newState[i] = pot ? '#' : '.';
        }
        return newState;
    }
}
