import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class A7 {

    static List<Character> before = new LinkedList<>();
    static List<Character> after = new LinkedList<>();

    static List<Character> findAvailable(int firstChar, boolean[] taken, boolean[] ready) {
        List<Character> avail = new LinkedList<>();
        for (int i = 0; i < taken.length; i++) {
            if (!taken[i] && !ready[i]) {
                char c = (char) (firstChar + i);
                if (isAvailable(c, firstChar, ready)) {
                    avail.add(c);
                }
            }
        }
        return avail;
    }

    private static boolean isAvailable(char c, int firstChar, boolean[] taken) {
        for (int i = 0; i < before.size(); i++) {
            if (after.get(i).charValue() == c && !taken[before.get(i).charValue() - firstChar]) {
                return false;
            }
        }
        return true;
    }

    static void p1() {
        final Set<Character> all = new HashSet<>(before);
        all.addAll(after);
        final int numChars = new HashSet<>(all).size();
        final int firstChar = all.stream().sorted().findFirst().get();// fixme hard code 'A'
        boolean[] taken = new boolean[numChars];
        StringBuffer res = new StringBuffer();
        while (res.length() < numChars) {
            List<Character> ava = findAvailable(firstChar, taken, taken);
            Collections.sort(ava);
            res.append(ava.get(0));
            taken[ava.get(0).charValue() - firstChar] = true;
        }
        System.out.println(res);
    }

    static void p2() {
        final Set<Character> all = new HashSet<>(before); // fixme move this out
        all.addAll(after);
        final int numChars = new HashSet<>(all).size();
        final int firstChar = all.stream().sorted().findFirst().get();// fixme hard code 'A'
        boolean[] taken = new boolean[numChars];
        boolean[] ready = new boolean[numChars];
        int[] worked = new int[numChars];
        int time = 0;
        StringBuffer res = new StringBuffer();
        Character[] workers = new Character[5];
        while (res.length() < numChars) {
            for (int i = 0; i < workers.length; i++) {
                if (workers[i] == null) {
                    List<Character> ava = findAvailable(firstChar, taken, ready);
                    if (!ava.isEmpty()) {
                        Collections.sort(ava);
                        taken[ava.get(0).charValue() - firstChar] = true;
                        workers[i] = ava.get(0);
                        worked[workers[i] - firstChar] = 0;
                    }
                }
            }
            ++time;
            for (int i = 0; i < workers.length; i++) {
                if (workers[i] != null) {
                    ++worked[workers[i] - firstChar];
                    if (worked[workers[i] - firstChar] == timeToFinish(workers[i] - firstChar)) {
                        res.append(workers[i]);
                        ready[workers[i] - firstChar] = true;
                        workers[i] = null;
                    }
                }
            }
        }
        System.out.println(time);
    }

    private static int timeToFinish(int i) {
        return i + 61;
    }

    private static void read() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("input7.txt"));
        String line = br.readLine();
        while (line != null) {
            String[] xy = line.split(" ");
            before.add(Character.valueOf(xy[1].toCharArray()[0]));
            after.add(Character.valueOf(xy[7].toCharArray()[0]));
            line = br.readLine();
        }
    }

    public static void main(String[] args) throws IOException {
        read();
        p1();
        p2();
    }
}
