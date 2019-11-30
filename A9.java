import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class A9 {

//    static int numPlayers = 9;
//    static int numMarbles = 25;
    static int numPlayers = 476;
    static int numMarbles = 71431*100;

    static class N {
        N previous, next;
        final int value;

        N(int value) {
            this.value = value;
        }
    }

    public static void main(String[] args) {
        long[] points = new long[numPlayers];

        N current = new N(0);
        current.next = current;
        current.previous = current;
        for (int i = 1; i <= numMarbles; i++) {
            if (i % 23 == 0) {
                int currentPlayer = (i - 1) % numPlayers;
                N toRemove = current.previous.previous.previous.previous.previous.previous.previous;
                toRemove.previous.next = toRemove.next;
                toRemove.next.previous = toRemove.previous;
                current = toRemove.next;
                points[currentPlayer] += i + toRemove.value;
//                if (currentPlayer == 237)
//                    System.out.printf("player no %d gets %d + %d = %d points, has now total of %d\n", currentPlayer, i, marbleToRemove, i + marbleToRemove, points[currentPlayer]);
//                currentMarble = circle.get(currentMarbleIndex);
            } else {
                N newNode = new N(i);
                newNode.previous = current.next;
                newNode.next = current.next.next;
                current.next.next.previous = newNode;
                current.next.next = newNode;
                current = newNode;
            }
        }
        System.out.println(LongStream.of(points).max().getAsLong());
        System.out.println(points[237]);
//        // 237
//        // 5474 + 10948n
//        // 2364 + 4735n
//        int n = 0;
//        int p = 0;
//        for (int i = 1; i <= numMarbles; ++i) {
//            if ((i - 5474) % 10948 == 0) {
//                int marbleToRemove = (2364 + 4735 * n++);
//                p += i + marbleToRemove;
//                System.out.printf("player no %d gets %d + %d = %d points, has now total of %d\n", 237, i, marbleToRemove, i + marbleToRemove, p);
//            }
//        }
//        System.out.println(p);
    }
}
