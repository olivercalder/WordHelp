import java.util.ArrayList;
import java.util.HashMap;

public class Ladder {

    private String startWord;
    private String endWord;
    private ArrayList<String> ignoredWords;
    private HashMap<String, MapNode> M;
    private LadderQueue Q;
    private Generator G;
    private Loader loader;
    private String currentWord;
    private ArrayList<MapNode> path;

    private class LadderQueue {

        private Node head;
        private Node tail;

        private class Node {

            public MapNode data;
            public Node next;

            private Node (MapNode N) {
                data = N;
                next = null;
            }
        }

        private boolean isEmpty() {
            if (head == null) {
                return true;
            } else {
                return false;
            }
        }

        private void enqueue(String word) {
            MapNode N = M.get(word);
            Node newNode = new Node(N);
            if (this.isEmpty()) {
                head = newNode;
                tail = newNode;
            } else {
                tail.next = newNode;
                tail = tail.next;
            }
        }

        private String dequeue() {
            MapNode N = dequeueNode();
            return N.getWord();
        }

        private MapNode dequeueNode() {
            if (this.isEmpty()) {
                return null;
            } else {
                MapNode N = head.data;
                head = head.next;
                return N;
            }
        }

        private void clear() {
            head = null;
            tail = null;
        }

    }

    public Ladder() {
        startWord = null;
        endWord = null;
        ignoredWords = new ArrayList<>();
        M = new HashMap<>();
        Q = new LadderQueue();
        G = new Generator();
        loader = new Loader();
        currentWord = null;
        path = new ArrayList<>();
    }

    public void loadDictionary(String fileName) {
        loader = new Loader();
        M = loader.loadHashMap(fileName);
    }

    public void visit(String word) {
        MapNode parent = M.get(currentWord);
        M.get(word).visit(parent);
    }

    public void ignore(ArrayList<String> ignored) {
        for (String wordChunk : ignored) {
            String w = wordChunk.toUpperCase();
            String[] words = w.split(",");
            for (String word : words) {
                ignoredWords.add(word);
            }
        }
    }

    public boolean ignores(String word) {
        boolean isIgnored = false;
        for (String w : ignoredWords) {
            if (word.equals(w)) {
                isIgnored = true;
            }
        }
        return isIgnored;
    }

    public void clearIgnored() {
        ignoredWords.clear();
    }

    public void climb(String start, String end) {
        startWord = start.toUpperCase();
        endWord = end.toUpperCase();
        currentWord = null;
        Q.clear();
        path.clear();

        if (!M.containsKey(startWord)) {
            System.out.format("\n%s is not a word\n", startWord);
            MapNode newStartNode = new MapNode(startWord);
            M.put(startWord, newStartNode);
        }
        if (!M.containsKey((endWord))) {
            System.out.format("\n%s is not a word\n", endWord);
            MapNode newEndNode = new MapNode(endWord);
            M.put(endWord, newEndNode);
        }

        visit(startWord);
        Q.enqueue(startWord);
        currentWord = startWord;
        while (!Q.isEmpty() && !currentWord.equals(endWord)) {
            currentWord = Q.dequeue();
            ArrayList<String> neighbors = G.neighbors(currentWord);
            for (String w : neighbors) {
                if (M.containsKey(w) && !this.ignores(w) && !M.get(w).isVisited()) {
                    visit(w);
                    Q.enqueue(w);
                }
            }
        }

        if (!Q.isEmpty()) {
            MapNode currentNode = M.get(currentWord);
            while (currentNode != null) {
                path.add(0, currentNode);
                currentNode = currentNode.getParent();
            }
        }
    }

    public void printPath() {
        System.out.format("\nStart word: %s\n", startWord);
        System.out.format("End word: %s\n", endWord);

        if (!ignoredWords.isEmpty()) {
            System.out.print("Ignored words:");
            for (String word : ignoredWords) {
                System.out.format(" %s", word);
            }
            System.out.print("\n");
        }
        System.out.println();

        if (path.size() != 0) {
            for (MapNode N : path) {
                System.out.println(N.getWord());
            }
            System.out.println();
        } else {
            System.out.format("No path between %s and %s\n", startWord, endWord);
        }
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java Ladder startWord endWord [--dictFile=fileName] [--ignore=word]");
            System.exit(1);
        }
        String dictFile = null;
        ArrayList<String> ignored = new ArrayList<>();
        if (args.length >= 3) {
            dictFile = null;
            int count = 0;
            for (String arg : args) {
                if (arg.length() > 11 && arg.substring(0, 11).equals("--dictFile=")) {
                    dictFile = arg.substring(11);
                } else if (arg.length() > 9 && arg.substring(0, 9).equals("--ignore=")) {
                    ignored.add(arg.substring(9));
                } else if (count >= 2) {
                    System.out.format("Argument %s not recognized\n", arg);
                    System.exit(1);
                }
                count++;
            }
        }
        if (dictFile == null) {
            dictFile = "sowpods.txt";
        }
        String start = args[0];
        String end = args[1];
        if (start.length() != end.length()) {
            System.out.println("Error: Words must be of the same length");
            System.exit(1);
        }
        Ladder L = new Ladder();
        L.loadDictionary(dictFile);
        L.ignore(ignored);
        L.climb(start, end);
        L.printPath();
    }
}
