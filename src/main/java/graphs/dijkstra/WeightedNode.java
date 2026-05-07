package graphs.dijkstra;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class WeightedNode {
    private String name;
    // Kanterne er vægtede, så vi gemmer afstanden til naboerne i et map
    private Map<WeightedNode, Integer> neighbors;

    public WeightedNode(String name) {
        this.name = name;
        this.neighbors = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public Map<WeightedNode, Integer> getNeighbors() {
        return neighbors;
    }

    public void addNeighbor(WeightedNode neighbor, int weight) {
        neighbors.put(neighbor, weight);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WeightedNode)) return false;
        WeightedNode that = (WeightedNode) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}