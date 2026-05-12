package graphs.astar;

import java.util.ArrayList;
import java.util.List;

public class CityNode {
    private int row;
    private int col;
    private String name;
    private List<CityNode> neighbors;

    public CityNode(int row, int col) {
        this.row = row;
        this.col = col;
        this.neighbors = new ArrayList<>();
    }

    public CityNode(int row, int col, String name) {
        this(row, col);
        this.name = name;
    }

    public int getRow() { return row; }
    public int getCol() { return col; }
    public String getName() { return name; }
    public boolean isCity() { return name != null; }
    public List<CityNode> getNeighbors() { return neighbors; }

    public void addNeighbor(CityNode neighbor) {
        neighbors.add(neighbor);
    }
}