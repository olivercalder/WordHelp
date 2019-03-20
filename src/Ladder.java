import java.util.ArrayList;
import java.util.HashMap;

public class Ladder {

    private String startWord;
    private String endWord;
    private ArrayList<String> ignoredWords;
    private HashMap<String, GraphNode> graph;
    private LadderQueue queue;
    private Generator generator;
    private Loader loader;
    private String currentWord;
    private ArrayList<GraphNode> path;

    private class LadderQueue {

        private Node head;
        private Node tail;

        private class Node {

            public GraphNode data;
            public Node next;

            private Node (GraphNode graphNode) {
                data = graphNode;
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
            GraphNode graphNode = graph.get(word);
            Node newNode = new Node(graphNode);
            if (this.isEmpty()) {
                head = newNode;
                tail = newNode;
            } else {
                tail.next = newNode;
                tail = tail.next;
            }
        }

        private String dequeue() {
            GraphNode node = dequeueNode();
            return node.getWord();
        }

        private GraphNode dequeueNode() {
            if (this.isEmpty()) {
                return null;
            } else {
                GraphNode node = head.data;
                head = head.next;
                return node;
            }
        }

        private void clear() {
            head = null;
            tail = null;
        }

    }

    /**
     * Constructor for the Ladder class.
     * Initializes empty values for all instance variables.
     */
    public Ladder() {
        startWord = null;
        endWord = null;
        ignoredWords = new ArrayList<>();
        graph = new HashMap<>();
        queue = new LadderQueue();
        generator = new Generator();
        loader = new Loader();
        currentWord = null;
        path = new ArrayList<>();
    }

    /**
     * Changes the alphabet used for word generation to a new alphabet. Each character in the inputted
     * String will be treated as a letter of the new alphabet.
     * @param a a String containing every character which will be considered part of the alphabet
     */
    public void setAlphabet(String a) {
        generator.setAlphabet(a);
    }

    /**
     * Loads words from a file, then creates a HashMap containing a GraphNode object for each word
     * @param fileName the name of the file from which the words are read
     */
    public void loadDictionary(String fileName) {
        loader = new Loader();
        graph = loader.loadHashMap(fileName);
    }

    /**
     * Sets the status of a GraphNode to visited, and assigns the parent GraphNode from which it was visited.
     * @param word the GraphNode which is to be visited
     */
    public void visit(String word) {
        GraphNode parent = graph.get(currentWord);
        graph.get(word).visit(parent);
    }

    /**
     * Adds words to the list of ignored words which will be excluded from the dictionary
     * when checking the validity of generated words.
     * @param ignored an ArrayList of words to be added to the ArrayList ignoredWords
     */
    public void ignore(ArrayList<String> ignored) {
        for (String wordChunk : ignored) {
            String w = wordChunk.toUpperCase();
            String[] words = w.split(",");
            for (String word : words) {
                ignoredWords.add(word);
            }
        }
    }

    /**
     * Checks whether the word is on the ignoredWords list, and thus will be treated as
     * not a part of the list of valid words.
     * @param word the word to be checked
     * @return a boolean value for whether the word is ignored
     */
    public boolean ignores(String word) {
        boolean isIgnored = false;
        for (String w : ignoredWords) {
            if (word.equals(w)) {
                isIgnored = true;
            }
        }
        return isIgnored;
    }

    /**
     * Clears the list of ignored words, leaving the original dictionary intact.
     */
    public void clearIgnored() {
        ignoredWords.clear();
    }

    /**
     * Builds a word ladder between two words.
     * Starting at String start, the method generates neighboring words which differ
     * from the starting word by one letter, and these generated words are checked
     * against a list of valid words. If they are valid, their corresponding GraphNodes
     * are added to the LadderQueue.
     * Then, the first GraphNode is taken from the queue and visited, and the neighbors
     * of it are generated. This process is repeated until either the end word is reached,
     * or the LadderQueue is empty.
     * @param start the word from which to begin the word ladder
     * @param end the word to which the ladder is built
     */
    public void climb(String start, String end) {
        startWord = start.toUpperCase();
        endWord = end.toUpperCase();
        currentWord = null;
        queue.clear();
        path.clear();

        if (!graph.containsKey(startWord)) {
            System.out.format("\n%s is not a word\n", startWord);
            GraphNode newStartNode = new GraphNode(startWord);
            graph.put(startWord, newStartNode);
        }
        if (!graph.containsKey((endWord))) {
            System.out.format("\n%s is not a word\n", endWord);
            GraphNode newEndNode = new GraphNode(endWord);
            graph.put(endWord, newEndNode);
        }

        visit(startWord);
        queue.enqueue(startWord);
        currentWord = startWord;
        while (!queue.isEmpty() && !currentWord.equals(endWord)) {
            currentWord = queue.dequeue();
            ArrayList<String> neighbors = generator.genNeighbors(currentWord);
            for (String w : neighbors) {
                if (graph.containsKey(w) && !this.ignores(w) && !graph.get(w).isVisited()) {
                    visit(w);
                    queue.enqueue(w);
                }
            }
        }

        if (!queue.isEmpty()) {
            GraphNode currentNode = graph.get(currentWord);
            while (currentNode != null) {
                path.add(0, currentNode);
                currentNode = currentNode.getParent();
            }
        }
    }

    /**
     * Prints the path generated by the most recent call of the climb() method.
     */
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
            for (GraphNode N : path) {
                System.out.println(N.getWord());
            }
            System.out.println();
        } else {
            System.out.format("No path between %s and %s\n", startWord, endWord);
        }
    }

    /**
     * Main method for the Ladder class. Constructs and prints a word ladder between the given words.
     * Ignores words specified with --ignore=word
     * Draws dictionary of valid words from file specified with --dictFile=fileName
     * Generates words using the alphabet specified with --alphabet=ABC....
     * @param args the arguments used to generate the word ladder:
     *             args[0] = startWord;
     *             args[1] = endWord;
     *             Optional: args[i] for i > 1 can include --ignore=word --dictFile=fileName --alphabet=ABC....;
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java Ladder startWord endWord [--ignore=word] [--dictFile=fileName] [--alphabet=ABCDEFGHIJKLMNOPQRSTUVWXYZ]");
            System.exit(1);
        }
        String dictFile = null;
        String alphabet = null;
        ArrayList<String> ignored = new ArrayList<>();
        if (args.length > 2) {
            for (int i = 0; i < args.length; i++) {
                if (args[i].length() > 11 && args[i].substring(0, 11).equals("--dictFile=")) {
                    dictFile = args[i].substring(11);
                } else if (args[i].length() > 9 && args[i].substring(0, 9).equals("--ignore=")) {
                    ignored.add(args[i].substring(9));
                } else if (args[i].length() > 11 && args[i].substring(0, 11).equals("--alphabet=")){
                    alphabet = args[i].substring(11);
                } else if (i >= 2) {
                    System.out.format("Argument %s not recognized\n", args[i]);
                    System.exit(1);
                }
            }
        }
        String start = args[0];
        String end = args[1];
        if (start.length() != end.length()) {
            System.out.println("Error: Words must be of the same length");
            System.exit(1);
        }
        Ladder ladder = new Ladder();
        if (alphabet != null) {
            ladder.setAlphabet(alphabet.toUpperCase());
        }
        if (dictFile == null) {
            dictFile = "Dictionaries/english_words.txt";
        }
        ladder.loadDictionary(dictFile);
        ladder.ignore(ignored);
        ladder.climb(start, end);
        ladder.printPath();
    }
}
