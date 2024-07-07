package RandomWalk;

import java.util.*;

public class Graph {
    private Map<String, List<String>> adjList;  // 인접 리스트

    public Graph() {
        this.adjList = new HashMap<>();
    }

    // 노드 추가
    public void addNode(String node) {
        adjList.putIfAbsent(node, new ArrayList<>());
    }

    // 엣지 추가 (양방향)
    public void addEdge(String node1, String node2) {
        adjList.get(node1).add(node2);
        adjList.get(node2).add(node1);
    }

    // 랜덤 워크 메서드
    public List<String> randomWalk(String startNode, int steps) {
        List<String> walk = new ArrayList<>();
        Random random = new Random();
        String currentNode = startNode;

        for (int i = 0; i < steps; i++) {
            walk.add(currentNode);
            List<String> neighbors = adjList.get(currentNode);
            if (neighbors.isEmpty()) break;  // 더 이상 이동할 수 없으면 멈춤
            currentNode = neighbors.get(random.nextInt(neighbors.size()));
        }

        return walk;
    }

    public static void main(String[] args) {
        Graph graph = new Graph();

        // 노드 추가
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");

        // 엣지 추가
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("B", "D");
        graph.addEdge("C", "D");

        // 랜덤 워크 수행
        List<String> walk = graph.randomWalk("A", 10);
        System.out.println("Random Walk: " + walk);
    }
}