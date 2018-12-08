import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class A4 {

    static class F {
        final String start;
        final int n;
        final int[] sleepMinutes = new int[60];
        int totalSleep = 0;
        int mostSleptMinute = -1;

        F(String start, int n) {
            this.start = start;
            this.n = n;
        }

        void addSleep(String fallAsleep, String wakeUp) {
            int start = Integer.parseInt(fallAsleep.split(" ")[1].substring(3, 5));
            int end = Integer.parseInt(wakeUp.split(" ")[1].substring(3, 5));
            for (int i = start; i < end; i++) {
                ++sleepMinutes[i];
            }
            totalSleep += end - start;
            int maxSleptMinutes = sleepMinutes[0];
            for (int i = 1; i < 60; i++) {
                if (sleepMinutes[i] > maxSleptMinutes) {
                    maxSleptMinutes = sleepMinutes[i];
                    mostSleptMinute = i;
                }
            }
        }

        @Override
        public String toString() {
            return "F{" +
                    "start='" + start + '\'' +
                    ", n=" + n +
                    ", sleepMinutes=" + sleepMinutes +
                    ", totalSleep=" + totalSleep +
                    ", mostSleptMinute=" + mostSleptMinute +
                    '}';
        }

        static F parse(String line) {
            if (line.contains("Guard")) {
                return new F(line.substring(1, 18), Integer.parseInt(line.split(" ")[3].substring(1)));
            }
            return null;
        }
    }

    static List<F> getSquares() throws IOException {
        final List<String> c = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("input4.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                c.add(line);
            }
        }
        Collections.sort(c);
        final Map<Integer, F> ff = new HashMap<>();
        int currentGuard = -1;
        for (int i = 0; i < c.size(); ++i) {
            String line = c.get(i);
            F f = F.parse(line);
            if (f != null) {
                System.out.println(line);
                if (!ff.containsKey(f.n)) {
                    ff.put(f.n, f);
                }
                currentGuard = f.n;
            } else {
                System.out.println(line + " " + c.get(i + 1));
                ff.get(currentGuard).addSleep(line, c.get(++i));
            }
        }
        return new LinkedList<>(ff.values());
    }

    public static void main(String[] args) throws IOException {
        final List<F> input = getSquares();
        F f = input.stream().sorted((f1, f2) -> Integer.compare(f2.totalSleep, f1.totalSleep)).findFirst().get();
        System.out.println(f);
        System.out.println(f.n * f.mostSleptMinute);
        F p2 = input.stream().sorted((f1, f2) -> Integer.compare(f2.mostSleptMinute < 0? 0 :f2.sleepMinutes[f2.mostSleptMinute],f1.mostSleptMinute <0? 0 : f1.sleepMinutes[f1.mostSleptMinute])).findFirst().get();
        System.out.println(p2);
        System.out.println(p2.n * p2.mostSleptMinute);
    }
}
