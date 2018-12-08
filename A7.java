import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class A7 {

    static List<Character> before = new LinkedList<>();
    static List<Character> after = new LinkedList<>();

    static List<Character> findAvailable(boolean[] taken, boolean[] ready) {
        List<Character> avail = new LinkedList<>();
        for (int i = 0; i < taken.length; i++) {
            if (!taken[i] && !ready[i]) {
                char c = (char) ('A' + i);
                if (isAvailable(c, ready)) {
                    avail.add(c);
                }
            }
        }
        return avail;
    }

    static boolean isAvailable(char c, boolean[] taken) {
        for (int i = 0; i < before.size(); i++) {
            if (after.get(i).charValue() == c && !taken[before.get(i).charValue() - 'A']) {
                return false;
            }
        }
        return true;
    }

    static String p1(List<Character> before, List<Character> after) {
        final Set<Character> all = new HashSet<>(before);
        all.addAll(after);
        final int numChars = new HashSet<>(all).size();
        boolean[] taken = new boolean[numChars];
        StringBuffer res = new StringBuffer();
        while (res.length() < numChars) {
            List<Character> ava = findAvailable(taken, taken);
            Collections.sort(ava);
            res.append(ava.get(0));
            taken[ava.get(0).charValue() - 'A'] = true;
        }
        return res.toString();
    }

    static int p2(List<Character> before, List<Character> after) {
        final Set<Character> all = new HashSet<>(before); // fixme move this out
        all.addAll(after);
        final int numChars = new HashSet<>(all).size();
        boolean[] taken = new boolean[numChars];
        boolean[] ready = new boolean[numChars];
        int[] worked = new int[numChars];
        int time = 0;
        StringBuffer res = new StringBuffer();
        Character[] workers = new Character[5];
        while (res.length() < numChars) {
            for (int i = 0; i < workers.length; i++) {
                if (workers[i] == null) {
                    List<Character> ava = findAvailable(taken, ready);
                    if (!ava.isEmpty()) {
                        Collections.sort(ava);
                        taken[ava.get(0).charValue() - 'A'] = true;
                        workers[i] = ava.get(0);
                        worked[workers[i] - 'A'] = 0;
                    }
                }
            }
            ++time;
            for (int i = 0; i < workers.length; i++) {
                if (workers[i] != null) {
                    ++worked[workers[i] - 'A'];
                    if (worked[workers[i] - 'A'] == timeToFinish(workers[i] - 'A')) {
                        res.append(workers[i]);
                        ready[workers[i] - 'A'] = true;
                        workers[i] = null;
                    }
                }
            }
        }
        return time;
    }

    static int timeToFinish(int i) {
        return i + 61;
    }

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader("input7.txt"));
        String line = br.readLine();
        while (line != null) {
            String[] xy = line.split(" ");
            before.add(Character.valueOf(xy[1].toCharArray()[0]));
            after.add(Character.valueOf(xy[7].toCharArray()[0]));
            line = br.readLine();
        }

        System.out.printf("%s %d", p1(before, after), p2(before, after));
    }
}
