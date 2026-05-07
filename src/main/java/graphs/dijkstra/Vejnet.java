package graphs.dijkstra;

import java.util.*;

public class Vejnet {

    public static void main(String[] args) {
        WeightedNode københavn = new WeightedNode("København H");
        WeightedNode islands   = new WeightedNode("Islands Brygge");
        WeightedNode amager    = new WeightedNode("Amager Strand");
        WeightedNode kastrup   = new WeightedNode("Kastrup");
        WeightedNode dragør    = new WeightedNode("Dragør");
        WeightedNode tårnby    = new WeightedNode("Tårnby");
        WeightedNode sundby    = new WeightedNode("Sundby");
        WeightedNode lufthavn  = new WeightedNode("CPH Lufthavn");
        WeightedNode køge      = new WeightedNode("Køge");

        københavn.addNeighbor(islands,  3);
        københavn.addNeighbor(sundby,   5);
        islands.addNeighbor(amager,     4);
        islands.addNeighbor(sundby,     2);
        sundby.addNeighbor(kastrup,     4);
        sundby.addNeighbor(tårnby,      3);
        amager.addNeighbor(kastrup,     5);
        amager.addNeighbor(tårnby,      6);
        kastrup.addNeighbor(lufthavn,   2);
        kastrup.addNeighbor(dragør,     5);
        tårnby.addNeighbor(lufthavn,    3);
        tårnby.addNeighbor(dragør,      6);
        lufthavn.addNeighbor(dragør,    6);
        lufthavn.addNeighbor(køge,     30);
        dragør.addNeighbor(køge,       25);

        findShortestPath(københavn, dragør);
    }

    public static void findShortestPath(WeightedNode source, WeightedNode destination) {

        Scanner scanner = new Scanner(System.in);

        // En node og den node vi kom fra.
        // Skal bruges til at rekonstruere stien til sidst.
        Map<WeightedNode, WeightedNode> prev = new HashMap<>();

        // Gemmer den billigste kendte afstand fra startnoden
        // til hver node vi har fundet indtil videre.
        Map<WeightedNode, Integer> dist = new HashMap<>();

        // Indeholder noder vi allerede er færdige med at undersøge.
        // Når en node er visited skal den ikke undersøges igen.
        Set<WeightedNode> visited = new HashSet<>();

        // PriorityQueue sørger automatisk for at noden med
        // den laveste distance bliver popped først.
        PriorityQueue<NodeWithDist> queue = new PriorityQueue<>();

        // Startnoden har distance 0 til sig selv.
        queue.add(new NodeWithDist(source, 0));
        dist.put(source, 0);

        int step = 0;

        printHeader(source, destination);
        printState(dist, visited, queue, prev);
        waitForEnter(scanner);

        // Så længe der stadig er noder i køen fortsætter algoritmen.
        while (!queue.isEmpty()) {

            // Hent noden med den laveste kendte distance.
            NodeWithDist current = queue.poll();

            System.out.println("─".repeat(70));
            System.out.printf("  Trin %d: Popper %-20s (dist = %d)%n",
                    ++step, current.node.getName(), current.dist);

            // Hvis vi har fundet destinationen stopper vi.
            // Dijkstra garanterer at dette er den korteste vej.
            if (current.node.equals(destination)) {
                System.out.println("  ★  Vi har nået destinationen – stopper!");
                System.out.println("─".repeat(70));
                break;
            }

            // Hvis noden allerede er visited springer vi over.
            // Dette kan ske fordi Java PriorityQueue ikke understøtter
            // effektiv decrease-key, så gamle entries kan stadig ligge i køen.
            if (visited.contains(current.node)) {
                System.out.printf("  ⚠  %s er allerede visited – springer over%n",
                        current.node.getName());
                waitForEnter(scanner);
                continue;
            }

            // Vi er nu i gang med at undersøge current,
            // så den markeres som visited.
            visited.add(current.node);

            System.out.printf("  ✓  Markerer %s som visited%n",
                    current.node.getName());

            System.out.println();
            System.out.println("  Undersøger naboer:");

            // Gennemgå alle naboer til current.
            for (Map.Entry<WeightedNode, Integer> entry :
                    current.node.getNeighbors().entrySet()) {

                WeightedNode next = entry.getKey();
                int weight = entry.getValue();

                // Hvis naboen allerede er visited skal den ikke undersøges igen.
                if (visited.contains(next)) {
                    System.out.printf(
                            "    → %-20s allerede visited – springer over%n",
                            next.getName());
                    continue;
                }

                // Ny potentiel afstand:
                // afstand til current + kanten til naboen.
                int newDist = current.dist + weight;

                // Kendt afstand til naboen.
                // Hvis vi aldrig har set noden før bruges ∞.
                int knownDist = dist.getOrDefault(next, Integer.MAX_VALUE);

                String known =
                        knownDist == Integer.MAX_VALUE
                                ? "∞"
                                : String.valueOf(knownDist);

                // Opdater kun hvis vi har fundet en billigere vej.
                if (newDist < knownDist) {

                    // Gem den nye korteste afstand.
                    dist.put(next, newDist);

                    // Gem hvilken node vi kom fra.
                    prev.put(next, current.node);

                    // Læg naboen i køen med den nye distance.
                    queue.add(new NodeWithDist(next, newDist));

                    System.out.printf(
                            "    → %-20s dist %s → %d  ✓ opdateret (via %s)%n",
                            next.getName(),
                            known,
                            newDist,
                            current.node.getName());

                } else {

                    // Hvis den eksisterende vej allerede er bedre
                    // gør vi ingenting.
                    System.out.printf(
                            "    → %-20s dist %s ≤ %d  – ingen opdatering%n",
                            next.getName(),
                            known,
                            newDist);
                }
            }

            System.out.println();

            printState(dist, visited, queue, prev);

            waitForEnter(scanner);
        }

        printResult(dist, prev, source, destination);
    }

    // ── Hjælpemetoder til output ──────────────────────────────────────────────

    private static void printHeader(WeightedNode source, WeightedNode destination) {
        System.out.println("═".repeat(70));
        System.out.printf("  Dijkstra: %s  →  %s%n",
                source.getName(), destination.getName());
        System.out.println("  Tryk Enter for at gå ét trin frem.");
        System.out.println("═".repeat(70));
        System.out.println();
    }

    private static void printState(Map<WeightedNode, Integer> dist,
                                   Set<WeightedNode> visited,
                                   PriorityQueue<NodeWithDist> queue,
                                   Map<WeightedNode, WeightedNode> prev) {

        // dist-tabel
        System.out.println("  ┌─ Kendte afstande ──────────────────────────────────┐");
        List<Map.Entry<WeightedNode, Integer>> sorted = new ArrayList<>(dist.entrySet());
        sorted.sort(Comparator.comparingInt(Map.Entry::getValue));
        for (Map.Entry<WeightedNode, Integer> e : sorted) {
            String prevName = prev.containsKey(e.getKey())
                    ? "via " + prev.get(e.getKey()).getName()
                    : "start";
            String vis = visited.contains(e.getKey()) ? "[visited]" : "";
            System.out.printf("  │  %-20s %4d km   %-26s %s%n",
                    e.getKey().getName(), e.getValue(), prevName, vis);
        }
        System.out.println("  └────────────────────────────────────────────────────┘");

        // Kø
        List<NodeWithDist> queueSnap = new ArrayList<>(queue);
        queueSnap.sort(Comparator.comparingInt(n -> n.dist));
        System.out.print("  Kø (prioritet): ");
        if (queueSnap.isEmpty()) {
            System.out.println("(tom)");
        } else {
            StringJoiner sj = new StringJoiner(", ");
            for (NodeWithDist n : queueSnap)
                sj.add(n.node.getName() + "(" + n.dist + ")");
            System.out.println(sj);
        }
        System.out.println();
    }

    private static void printResult(Map<WeightedNode, Integer> dist,
                                    Map<WeightedNode, WeightedNode> prev,
                                    WeightedNode source, WeightedNode destination) {
        List<String> path = new ArrayList<>();
        WeightedNode step = destination;
        while (step != null) {
            path.add(0, step.getName());
            step = prev.get(step);
        }

        System.out.println("═".repeat(70));
        System.out.println("  RESULTAT");
        System.out.println("─".repeat(70));
        System.out.println("  Korteste vej:  " + String.join(" → ", path));
        System.out.println("  Samlet afstand: " + dist.get(destination) + " km");
        System.out.println("═".repeat(70));
    }

    private static void waitForEnter(Scanner scanner) {
        System.out.print("  [Enter for næste trin] ");
        scanner.nextLine();
    }

    // ── NodeWithDist ─────────────────────────────────────────────────────────

    private static class NodeWithDist implements Comparable<NodeWithDist> {
        WeightedNode node;
        int dist;

        public NodeWithDist(WeightedNode node, int dist) {
            this.node = node;
            this.dist = dist;
        }

        @Override
        public int compareTo(NodeWithDist other) {
            return Integer.compare(this.dist, other.dist);
        }
    }
}