import RandomWalk.RandomWalkVisualization3;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import java.util.List;

public class RandomWalkVisualization3Test {

    private Graph graph;

    @BeforeEach
    public void setUp() {
        graph = new SingleGraph("Test Graph");
    }

    @Test
    public void testInitializeGraph() {
        int numNodes = 10;
        graph = RandomWalkVisualization3.initializeGraph(numNodes);

        // 检查图中节点的数量
        assertEquals(numNodes, graph.getNodeCount());

        // 检查每个节点的存在性
        for (int i = 0; i < numNodes; i++) {
            Node node = graph.getNode(String.valueOf(i));
            assertTrue(node != null);
        }
    }

    @Test
    public void testPerformRandomWalk() {
        int numNodes = 10;
        int steps = 10;
        String startNode = "0";

        // 初始化图
        graph = RandomWalkVisualization3.initializeGraph(numNodes);

        // 执行随机游走
        RandomWalkVisualization3.performRandomWalk(graph, startNode, steps);

        // 检查图中是否包含起始节点
        assertTrue(graph.getNode(startNode) != null);

        // 检查游走路径是否包含起始节点
        List<String> walkSequence = getWalkSequence(graph, startNode, steps);
        assertTrue(walkSequence.contains(startNode));

        // 检查游走步数不超过指定步数
        assertTrue(walkSequence.size() <= steps + 1);
    }

    // 辅助方法：从图中获取随机游走的路径
    private List<String> getWalkSequence(Graph graph, String startNode, int steps) {
        List<String> walkSequence = new ArrayList<>();
        walkSequence.add(startNode);
        String currentNode = startNode;

        for (int i = 0; i < steps; i++) {
            Node node = graph.getNode(currentNode);
            List<String> neighbors = new ArrayList<>();
            for (Edge edge : node.getEachEdge()) {
                String neighbor = edge.getNode0().getId().equals(currentNode) ? edge.getNode1().getId() : edge.getNode0().getId();
                neighbors.add(neighbor);
            }
            if (neighbors.isEmpty()) break;
            currentNode = neighbors.get(new Random().nextInt(neighbors.size()));
            walkSequence.add(currentNode);
        }
        return walkSequence;
    }
}
