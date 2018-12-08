import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class A8 {

    static class Node {
        final int numChildren, numMetadata;
        final List<Node> chlidren = new LinkedList<>();
        final List<Integer> metadata = new LinkedList<>();

        Node(int numChildren, int numMetadata) {
            this.numChildren = numChildren;
            this.numMetadata = numMetadata;
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(new File("input8.txt"));

        Node root = new Node(scanner.nextInt(), scanner.nextInt());
        readChildren(scanner, root);
        readMetadata(scanner, root);

        System.out.printf("%d %d", sumMetaData(root), sumMetaDataIndexed(root));
    }

    static int sumMetaDataIndexed(Node node) {
        int sum = 0;
        for (int i = 0; i < node.numMetadata; i++) {
            if (node.numChildren == 0) {
                sum += node.metadata.get(i);
            } else if (node.metadata.get(i) <= node.numChildren) {
                sum += sumMetaDataIndexed(node.chlidren.get(node.metadata.get(i) - 1));
            }
        }
        return sum;
    }

    static int sumMetaData(Node node) {
        int sum = node.metadata.stream().mapToInt(x -> x.intValue()).sum();
        for (int i = 0; i < node.numChildren; i++) {
            sum += sumMetaData(node.chlidren.get(i));
        }
        return sum;
    }

    static void readMetadata(Scanner scanner, Node node) {
        for (int i = 0; i < node.numMetadata; i++) {
            node.metadata.add(scanner.nextInt());
        }
    }

    static void readChildren(Scanner scanner, Node node) {
        for (int i = 0; i < node.numChildren; i++) {
            Node child = new Node(scanner.nextInt(), scanner.nextInt());
            readChildren(scanner, child);
            readMetadata(scanner, child);
            node.chlidren.add(child);
        }
    }
}
