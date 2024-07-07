package RandomWalk;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomWalkVisualization3 {

    public static void main(String[] args) {
        System.setProperty("org.graphstream.ui", "swing");

        // 그래프 초기화 및 노드, 엣지 추가
        Graph graph = initializeGraph(10);

        // 그래프 시각화 스타일 설정
        for (Node node : graph) {
            node.addAttribute("ui.label", node.getId());
        }

        graph.display();

        // 랜덤 워크 수행 및 시각화
        performRandomWalk(graph, "0", 10);  // 0번 노드에서 시작
    }

    /**
     * 그래프를 초기화하고 노드와 엣지를 추가합니다.
     *
     * @param numNodes 그래프에 추가할 노드 수
     * @return 초기화된 그래프 객체
     */
    public static Graph initializeGraph(int numNodes) {
        Graph graph = new SingleGraph("Random Walk");

        // 노드 추가
        for (int i = 0; i < numNodes; i++) {
            graph.addNode(String.valueOf(i));
        }

        // 트리 엣지 추가
        for (int i = 0; i < numNodes; i++) {
            if (2 * i + 1 < numNodes) graph.addEdge(i + "" + (2 * i + 1), i + "", (2 * i + 1) + "");
            if (2 * i + 2 < numNodes) graph.addEdge(i + "" + (2 * i + 2), i + "", (2 * i + 2) + "");
        }

        return graph;
    }

    /**
     * 그래프에서 랜덤 워크를 수행합니다.
     *
     * @param graph 그래프 객체
     * @param startNode 시작 노드 ID
     * @param steps 랜덤 워크의 스텝 수
     */
    public static void performRandomWalk(Graph graph, String startNode, int steps) {
        Random random = new Random();
        String currentNode = startNode;

        List<String> walkSequence = new ArrayList<>();
        walkSequence.add(currentNode);

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
            walkSequence.add(currentNode);

            // 이동 경로 시각화: 이전 노드와 현재 노드 사이의 엣지를 강조
            if (i > 0) {
                String previousNode = walkSequence.get(i - 1);
                Edge edge = graph.getEdge(previousNode + currentNode);
                if (edge == null) {
                    edge = graph.getEdge(currentNode + previousNode);
                }
                if (edge != null) {
                    edge.setAttribute("ui.style", "fill-color: blue; size: 3px;");
                }
            }
        }

        // 전체 이동 순서 출력
        System.out.println("Random Walk Sequence: " + walkSequence);
    }
}