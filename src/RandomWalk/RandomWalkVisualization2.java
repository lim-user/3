package RandomWalk;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomWalkVisualization2 {

    public static void main(String[] args) {
        System.setProperty("org.graphstream.ui", "swing");

        Graph graph = new SingleGraph("Random Walk");

        // 트리 노드 추가
        for (int i = 0; i < 10; i++) {
            graph.addNode(String.valueOf(i));
        }

        // 트리 엣지 추가
        for (int i = 0; i < 10; i++) {
            if (2 * i + 1 < 10) graph.addEdge(i + "" + (2 * i + 1), i + "", (2 * i + 1) + "");
            if (2 * i + 2 < 10) graph.addEdge(i + "" + (2 * i + 2), i + "", (2 * i + 2) + "");
        }

        // 그래프 시각화 스타일 설정
        for (Node node : graph) {
            node.addAttribute("ui.label", node.getId());
        }

        graph.display();

        // 랜덤 워크 수행 및 시각화
        randomWalk(graph, "0", 10);  // 0번 노드에서 시작
    }

    public static void randomWalk(Graph graph, String startNode, int steps) {
        Random random = new Random();
        String currentNode = startNode;

        for (int i = 0; i < steps; i++) {
            try {
                Thread.sleep(1000); // 1초 대기 (애니메이션 효과)
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            Node node = graph.getNode(currentNode);
            node.setAttribute("ui.style", "fill-color: red;");

            List<String> neighbors = new ArrayList<>();
            for (Edge edge : node.getEachEdge()) {
                String neighbor = edge.getNode0().getId().equals(currentNode) ? edge.getNode1().getId() : edge.getNode0().getId();
                neighbors.add(neighbor);
            }

            if (neighbors.isEmpty()) break; // 더 이상 이동할 수 없으면 멈춤
            currentNode = neighbors.get(random.nextInt(neighbors.size()));
        }
    }
}