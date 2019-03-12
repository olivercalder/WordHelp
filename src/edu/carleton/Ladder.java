package edu.carleton;

import java.util.ArrayList;
import java.util.HashMap;

public class Ladder {

    private String startWord;
    private String endWord;
    private HashMap<String, MapNode> M;
    private LadderQueue Q;
    private String currentWord;
    private ArrayList<MapNode> path;

    private class MapNode {

        private String wordData;
        private boolean visited;
        private MapNode parent;

        private MapNode(String word) {
            wordData = word;
            visited = false;
            parent = null;
        }

        private void visit(MapNode N) {
            visited = true;
            parent = N;
        }

        private String getWord() {
            return wordData;
        }

        private boolean isVisited() {
            return visited;
        }

        private MapNode getParent() {
            return parent;
        }
    }

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
            if (head != null) {
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
        M = new HashMap<>();
        Q = new LadderQueue();
        currentWord = null;
        path = null;
    }

    public void loadDictionary(String fileName) {
        Loader loader = new Loader();
        ArrayList<String> realWords = loader.load(fileName);
        for (String word : realWords) {
            MapNode newNode = new MapNode(word);
            M.put(word, newNode);
        }
    }

    public void visit(String word) {
        MapNode parent = M.get(currentWord);
        M.get(word).visit(parent);
    }

    public void climb(String start, String end) {
        startWord = start;
        endWord = end;
        currentWord = null;
        Q.clear();
        path.clear();

        visit(startWord);
        Q.enqueue(startWord);
        currentWord = startWord;
        while (!Q.isEmpty() && currentWord.compareTo(endWord) != 0) {
            currentWord = Q.dequeue();
            ArrayList<String> neighbors = Generator.neighbors(currentWord);
            for (String w : neighbors) {
                if (M.containsKey(w) && !M.get(w).isVisited()) {
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
        System.out.format("Start Word: %s\n", startWord);
        System.out.format("End word: %s\n\n", endWord);
        for (MapNode N : path) {
            System.out.println(N.getWord());
        }
        System.out.println();
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java Ladder startWord endWord");
            System.exit(1);
        }
        String start = args[0];
        String end = args[1];
        if (start.length() != end.length()) {
            System.out.println("Error: Words must be of the same length");
            System.exit(1);
        }
        Ladder L = new Ladder();
        L.loadDictionary("sowpods.txt");
        L.climb(start, end);
        L.printPath();
    }
}
