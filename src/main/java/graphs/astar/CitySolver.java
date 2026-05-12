package graphs.astar;

import java.util.*;

public class CitySolver {

    static int[][] grid = {
            {0,0,1,0,0,1,0,0},
            {0,1,0,0,1,0,0,0},
            {0,0,0,1,0,0,1,0},
            {1,0,0,0,0,1,0,0},
            {0,0,1,0,0,0,0,1},
            {0,1,0,0,1,0,0,0},
            {0,0,0,1,0,0,1,0},
            {0,0,0,0,0,1,0,0},
    };

    static final int ROWS = 8, COLS = 8;

    static CityNode[][] nodes = new CityNode[ROWS][COLS];

    static Map<String, int[]> cities = Map.of(
            "Nordby", new int[]{0, 0},
            "Østby",  new int[]{0, 7},
            "Sydby",  new int[]{7, 7},
            "Vestby", new int[]{7, 0},
            "Midtby", new int[]{3, 3}
    );

    public static void main(String[] args) {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (grid[r][c] == 0) {
                    nodes[r][c] = new CityNode(r, c, getCityName(r, c));
                }
            }
        }

        int[][] directions = {{-1,0},{1,0},{0,-1},{0,1}};
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (nodes[r][c] == null) continue;
                for (int[] d : directions) {
                    int nr = r + d[0], nc = c + d[1];
                    if (nr >= 0 && nr < ROWS && nc >= 0 && nc < COLS && nodes[nr][nc] != null) {
                        nodes[r][c].addNeighbor(nodes[nr][nc]);
                    }
                }
            }
        }

        CityNode source = nodes[0][0];
        CityNode destination = nodes[7][7];

        findShortestPath(source, destination);
    }

    public static void findShortestPath(CityNode source, CityNode destination) {
        Scanner scanner = new Scanner(System.in);

        Map<CityNode, CityNode> prev = new HashMap<>();
        Map<CityNode, Integer>  dist = new HashMap<>();
        Set<CityNode>           visited = new HashSet<>();
        PriorityQueue<NodeWithCost> queue = new PriorityQueue<>();

        queue.add(new NodeWithCost(source, 0, heuristic(source, destination)));
        dist.put(source, 0);

        int step = 0;

        printHeader(source, destination);
        printState(dist, visited, queue, prev);
        waitForEnter(scanner);

        while (!queue.isEmpty()) {
            NodeWithCost current = queue.poll();

            System.out.println("─".repeat(70));
            printCurrentNode(current, ++step);

            if (current.node.equals(destination)) {
                System.out.println("  ★  Mål nået – stopper!");
                System.out.println("─".repeat(70));
                break;
            }

            if (visited.contains(current.node)) {
                printAlreadyVisited(current.node);
                continue;
            }

            visited.add(current.node);
            printMarkVisited(current.node);

            System.out.println();
            System.out.println("  Undersøger naboer:");

            for (CityNode next : current.node.getNeighbors()) {
                if (visited.contains(next)) {
                    printNeighborSkipped(next);
                    continue;
                }

                int newDist = current.gCost + 1;
                int h = heuristic(next, destination);
                int f = newDist + h;
                int known = dist.getOrDefault(next, Integer.MAX_VALUE);

                if (newDist < known) {
                    dist.put(next, newDist);
                    prev.put(next, current.node);
                    queue.add(new NodeWithCost(next, newDist, h));
                    printNeighborUpdated(next, known, newDist, h, f, current.node);
                } else {
                    printNeighborNoUpdate(next, known, newDist);
                }
            }

            System.out.println();
            printState(dist, visited, queue, prev);
            waitForEnter(scanner);
        }

        printResult(dist, prev, visited, source, destination);
    }


    // Manhattan-afstand som heuristik
    private static int heuristic(CityNode node, CityNode destination) {
        return Math.abs(destination.getRow() - node.getRow())
                + Math.abs(destination.getCol() - node.getCol());
    }

    private static class NodeWithCost implements Comparable<NodeWithCost> {
        CityNode node;
        int gCost;
        int fCost;

        public NodeWithCost(CityNode node, int gCost, int hCost) {
            this.node  = node;
            this.gCost = gCost;
            this.fCost = gCost + hCost;
        }

        @Override
        public int compareTo(NodeWithCost other) {
            return Integer.compare(this.fCost, other.fCost);
        }
    }

    private static String getCityName(int row, int col) {
        for (Map.Entry<String, int[]> entry : cities.entrySet()) {
            int[] coords = entry.getValue();
            if (coords[0] == row && coords[1] == col) return entry.getKey();
        }
        return null;
    }

    private static String label(CityNode node) {
        return node.isCity()
                ? node.getName()
                : "(" + node.getRow() + "," + node.getCol() + ")";
    }


    // Hjælpe metoder til print

    private static void waitForEnter(Scanner scanner) {
        System.out.print("  [Enter for næste trin] ");
        scanner.nextLine();
    }

    private static void printHeader(CityNode source, CityNode destination) {
        System.out.println("═".repeat(70));
        System.out.printf("  A*: %s  →  %s%n", label(source), label(destination));
        System.out.println("  Tryk Enter for at gå ét trin frem.");
        System.out.println("─".repeat(70));
        System.out.println("  Forklaring:");
        System.out.println("    g = faktisk afstand fra start til denne node");
        System.out.println("    h = estimeret afstand fra denne node til mål (heuristik)");
        System.out.println("    f = g + h  →  jo lavere f, jo højere prioritet i køen");
        System.out.println("═".repeat(70));
        System.out.println();
    }

    private static void printCurrentNode(NodeWithCost current, int step) {
        int h = current.fCost - current.gCost;
        System.out.printf("  Trin %d: Popper %-20s%n", step, label(current.node));
        System.out.printf("    g (afstand fra start):  %d%n", current.gCost);
        System.out.printf("    h (estimat til mål):    %d%n", h);
        System.out.printf("    f (prioritet = g + h):  %d%n", current.fCost);
    }

    private static void printMarkVisited(CityNode node) {
        System.out.printf("  ✓  Markerer %s som visited%n", label(node));
    }

    private static void printAlreadyVisited(CityNode node) {
        System.out.printf("  ⚠  %s er allerede visited – springer over%n", label(node));
    }

    private static void printNeighborSkipped(CityNode next) {
        System.out.printf("    → %-20s allerede visited – springer over%n", label(next));
    }

    private static void printNeighborUpdated(CityNode next, int known, int newDist,
                                             int h, int f, CityNode via) {
        String knownStr = known == Integer.MAX_VALUE ? "∞" : String.valueOf(known);
        System.out.printf("    → %-20s g: %s → %d  h=%d  f=%d  ✓ opdateret (via %s)%n",
                label(next), knownStr, newDist, h, f, label(via));
    }

    private static void printNeighborNoUpdate(CityNode next, int known, int newDist) {
        System.out.printf("    → %-20s g: %d ≤ %d  – ingen opdatering%n",
                label(next), known, newDist);
    }

    private static void printState(Map<CityNode, Integer> dist,
                                   Set<CityNode> visited,
                                   PriorityQueue<NodeWithCost> queue,
                                   Map<CityNode, CityNode> prev) {

        System.out.println("  ┌─ Kendte afstande ──────────────────────────────────┐");
        List<Map.Entry<CityNode, Integer>> sorted = new ArrayList<>(dist.entrySet());
        sorted.sort(Comparator.comparingInt(Map.Entry::getValue));
        for (Map.Entry<CityNode, Integer> e : sorted) {
            String prevName = prev.containsKey(e.getKey())
                    ? "via " + label(prev.get(e.getKey()))
                    : "start";
            String vis = visited.contains(e.getKey()) ? "[visited]" : "";
            System.out.printf("  │  %-20s g=%-4d %-26s %s%n",
                    label(e.getKey()), e.getValue(), prevName, vis);
        }
        System.out.println("  └────────────────────────────────────────────────────┘");

        List<NodeWithCost> queueSnap = new ArrayList<>(queue);
        queueSnap.sort(Comparator.comparingInt(n -> n.fCost));
        System.out.println("  Kø (sorteret efter f):");
        if (queueSnap.isEmpty()) {
            System.out.println("    (tom)");
        } else {
            for (NodeWithCost n : queueSnap) {
                int h = n.fCost - n.gCost;
                System.out.printf("    %-20s g=%-4d h=%-4d f=%-4d%n",
                        label(n.node), n.gCost, h, n.fCost);
            }
        }
        System.out.println();
    }

    private static void printGrid(Map<CityNode, CityNode> prev,
                                  Set<CityNode> visited,
                                  CityNode source,
                                  CityNode destination) {

        Set<CityNode> path = new HashSet<>();
        CityNode step = destination;
        while (step != null) {
            path.add(step);
            step = prev.get(step);
        }

        System.out.println("  Spatial visualisering:");
        System.out.println("  ┌" + "───┬".repeat(COLS - 1) + "───┐");

        for (int r = 0; r < ROWS; r++) {
            System.out.print("  │");
            for (int c = 0; c < COLS; c++) {
                if (grid[r][c] == 1) {
                    System.out.print("███│");
                } else if (nodes[r][c] != null && path.contains(nodes[r][c])) {
                    if (nodes[r][c].equals(source) || nodes[r][c].equals(destination)) {
                        System.out.print(" ★ │");
                    } else {
                        System.out.print(" · │");
                    }
                } else if (nodes[r][c] != null && visited.contains(nodes[r][c])) {
                    System.out.print(" ░ │");
                } else {
                    System.out.print("   │");
                }
            }
            System.out.println();

            if (r < ROWS - 1) {
                System.out.println("  ├" + "───┼".repeat(COLS - 1) + "───┤");
            }
        }

        System.out.println("  └" + "───┴".repeat(COLS - 1) + "───┘");
        System.out.println();
        System.out.println("  Forklaring:  ★ = start/mål   · = sti   ░ = besøgt   ███ = mur");
    }

    private static void printResult(Map<CityNode, Integer> dist,
                                    Map<CityNode, CityNode> prev,
                                    Set<CityNode> visited,
                                    CityNode source,
                                    CityNode destination) {
        List<String> path = new ArrayList<>();
        CityNode step = destination;
        while (step != null) {
            path.add(0, label(step));
            step = prev.get(step);
        }

        System.out.println("═".repeat(70));
        System.out.println("  RESULTAT");
        System.out.println("─".repeat(70));
        System.out.println("  Korteste vej:   " + String.join(" → ", path));
        System.out.println("  Antal skridt:   " + (path.size() - 1));
        System.out.println("─".repeat(70));
        System.out.println();
        printGrid(prev, visited, source, destination);
        System.out.println("═".repeat(70));
    }

}