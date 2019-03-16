public class MapNode {

    private String wordData;
    private boolean visited;
    private MapNode parent;

    /**
     * Constructor for the MapNode class.
     * Initializes a new MapNode with:
     * String wordData = word;
     * boolean visited = false;
     * MapNode parent = null;
     * @param word the word which will be stored in the String wordData
     */
    public MapNode(String word) {
        wordData = word;
        visited = false;
        parent = null;
    }

    /**
     * Sets the node as visited, and records the parent MapNode from which it was first visited.
     * @param N the parent MapNode from which the current MapNode was visited
     */
    public void visit(MapNode N) {
        visited = true;
        parent = N;
    }

    /**
     * Returns the word stored in the MapNode.
     * @return the String wordData of the MapNode
     */
    public String getWord() {
        return wordData;
    }

    /**
     * Checks whether the MapNode has been visited.
     * @return the boolean value for visited
     */
    public boolean isVisited() {
        return visited;
    }

    /**
     * Returns the parent node of the current node
     * @return the MapNode parent of the MapNode
     */
    public MapNode getParent() {
        return parent;
    }
}
