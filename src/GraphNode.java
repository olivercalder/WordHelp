public class GraphNode {

    private String wordData;
    private boolean visited;
    private GraphNode parent;

    /**
     * Constructor for the GraphNode class.
     * Initializes a new GraphNode with:
     * String wordData = word;
     * boolean visited = false;
     * GraphNode parent = null;
     * @param word the word which will be stored in the String wordData
     */
    public GraphNode(String word) {
        wordData = word;
        visited = false;
        parent = null;
    }

    /**
     * Sets the node as visited, and records the parent GraphNode from which it was first visited.
     * @param N the parent GraphNode from which the current GraphNode was visited
     */
    public void visit(GraphNode N) {
        visited = true;
        parent = N;
    }

    /**
     * Returns the word stored in the GraphNode.
     * @return the String wordData of the GraphNode
     */
    public String getWord() {
        return wordData;
    }

    /**
     * Checks whether the GraphNode has been visited.
     * @return the boolean value for visited
     */
    public boolean isVisited() {
        return visited;
    }

    /**
     * Returns the parent node of the current node
     * @return the GraphNode parent of the GraphNode
     */
    public GraphNode getParent() {
        return parent;
    }
}
