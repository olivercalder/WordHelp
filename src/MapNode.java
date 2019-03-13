public class MapNode {

    private String wordData;
    private boolean visited;
    private MapNode parent;

    public MapNode(String word) {
        wordData = word;
        visited = false;
        parent = null;
    }

    public void visit(MapNode N) {
        visited = true;
        parent = N;
    }

    public String getWord() {
        return wordData;
    }

    public boolean isVisited() {
        return visited;
    }

    public MapNode getParent() {
        return parent;
    }
}
