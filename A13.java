import java.io.File;
import java.io.IOException;
import java.util.*;

public class A13 {

    static class C {
        int x, y, turns = 0;
        char c;

        public C(int x, int y, char c) {
            this.x = x;
            this.y = y;
            this.c = c;
        }
    }

    static final List<String> map = new LinkedList<>();
    static final List<C> carts = new LinkedList<>();
    static int tick = 0;

    public static void main1(String[] args) throws IOException {
        Scanner scanner = new Scanner(new File("input13.txt"));

        while (scanner.hasNextLine()) {
            String l = scanner.nextLine();
            for (int i = 0; i < l.length(); i++) {
                if (l.charAt(i) == '>' || l.charAt(i) == '<') {
                    carts.add(new C(i, map.size(), l.charAt(i)));
                    l = l.substring(0, i) + "-" + l.substring(i + 1);
                }
                if (l.charAt(i) == '^' || l.charAt(i) == 'v') {
                    carts.add(new C(i, map.size(), l.charAt(i)));
                    l = l.substring(0, i) + "|" + l.substring(i + 1);
                }
            }
            map.add(l);
        }
        while (true) {
            Collections.sort(carts, (c1, c2) -> {
                if (c1.y != c2.y)
                    return Integer.compare(c1.y, c2.y);
                return Integer.compare(c1.x, c2.x);
            });
            Set<String> positions = new HashSet<>();
            for (C c : carts) {
                int x = 0, y = 0, turns = 0;
                char newc = c.c;
                char nextc = ' ';
                final boolean left = (c.turns % 3) == 0;
                final boolean straight = (c.turns % 3) == 1;
                if (c.c == '>') {
                    x = 1;
                    nextc = map.get(c.y).charAt(c.x + 1);
                    newc = nextc == '/' ? '^' : (nextc == '-' ? newc : (nextc == '\\' ? 'v' : (left ? '^' : (straight ? newc : 'v'))));
                } else if (c.c == '<') {
                    x = -1;
                    nextc = map.get(c.y).charAt(c.x - 1);
                    newc = nextc == '/' ? 'v' : (nextc == '-' ? newc : (nextc == '\\' ? '^' : (left ? 'v' : (straight ? newc : '^'))));
                } else if (c.c == '^') {
                    y = -1;
                    nextc = map.get(c.y - 1).charAt(c.x);
                    newc = nextc == '/' ? '>' : (nextc == '|' ? newc : (nextc == '\\' ? '<' : (left ? '<' : (straight ? newc : '>'))));
                } else if (c.c == 'v') {
                    y = 1;
                    nextc = map.get(c.y + 1).charAt(c.x);
                    newc = nextc == '/' ? '<' : (nextc == '|' ? newc : (nextc == '\\' ? '>' : (left ? '>' : (straight ? newc : '<'))));
                }
                    turns = nextc ==    '+'? 1 : 0;
                c.x += x;
                c.y += y;
                c.c = newc;
                c.turns += turns;
                String poskey = c.x + "," + c.y;
                if (positions.contains(poskey)) {
                    System.out.println(poskey);
                    System.exit(0);
                }
                positions.add(poskey);
            }
            ++tick;
//            p();
        }
    }
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(new File("input13.txt"));

        while (scanner.hasNextLine()) {
            String l = scanner.nextLine();
            for (int i = 0; i < l.length(); i++) {
                if (l.charAt(i) == '>' || l.charAt(i) == '<') {
                    carts.add(new C(i, map.size(), l.charAt(i)));
                    l = l.substring(0, i) + "-" + l.substring(i + 1);
                }
                if (l.charAt(i) == '^' || l.charAt(i) == 'v') {
                    carts.add(new C(i, map.size(), l.charAt(i)));
                    l = l.substring(0, i) + "|" + l.substring(i + 1);
                }
            }
            map.add(l);
        }
        while (carts.size() > 1) {
            Collections.sort(carts, (c1, c2) -> {
                if (c1.y != c2.y)
                    return Integer.compare(c1.y, c2.y);
                return Integer.compare(c1.x, c2.x);
            });
            Map<String, C> positions = new HashMap<>();
            for (C c : carts) {
                positions.put(c.x + "," + c.y, c);
            }
            Set<C> toRemove = new HashSet<>();
            for (C c : carts) {
                int x = 0, y = 0, turns = 0;
                char newc = c.c;
                char nextc = ' ';
                final boolean left = (c.turns % 3) == 0;
                final boolean straight = (c.turns % 3) == 1;
                if (c.c == '>') {
                    x = 1;
                    nextc = map.get(c.y).charAt(c.x + 1);
                    newc = nextc == '/' ? '^' : (nextc == '-' ? newc : (nextc == '\\' ? 'v' : (left ? '^' : (straight ? newc : 'v'))));
                } else if (c.c == '<') {
                    x = -1;
                    nextc = map.get(c.y).charAt(c.x - 1);
                    newc = nextc == '/' ? 'v' : (nextc == '-' ? newc : (nextc == '\\' ? '^' : (left ? 'v' : (straight ? newc : '^'))));
                } else if (c.c == '^') {
                    y = -1;
                    nextc = map.get(c.y - 1).charAt(c.x);
                    newc = nextc == '/' ? '>' : (nextc == '|' ? newc : (nextc == '\\' ? '<' : (left ? '<' : (straight ? newc : '>'))));
                } else if (c.c == 'v') {
                    y = 1;
                    nextc = map.get(c.y + 1).charAt(c.x);
                    newc = nextc == '/' ? '<' : (nextc == '|' ? newc : (nextc == '\\' ? '>' : (left ? '>' : (straight ? newc : '<'))));
                }
                    turns = nextc ==    '+'? 1 : 0;
                String oldposkey = c.x + "," + c.y;
                c.x += x;
                c.y += y;
                c.c = newc;
                c.turns += turns;
                String poskey = c.x + "," + c.y;
                if (positions.containsKey(poskey)) {
                    toRemove.add(c);
                    toRemove.add(positions.get(poskey));
                    positions.remove(poskey);
                } else {
                    positions.remove(oldposkey);
                    positions.put(poskey, c);
                }
            }
            carts.removeAll(toRemove);
            ++tick;
//            p();
        }
        System.out.println(carts.get(0).x + "," + carts.get(0).y);
    }

    private static void p() {
        List<String> m = new LinkedList<>(map);
        for (C c : carts) {
            String l = m.get(c.y);
            l = l.substring(0, c.x) + c.c + l.substring(c.x + 1);
            m.set(c.y, l);
        }
        for (String s : m) {
            System.out.println(s);
        }
        System.out.println();
    }
}