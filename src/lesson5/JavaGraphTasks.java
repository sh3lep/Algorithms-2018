package lesson5;

import kotlin.NotImplementedError;

import java.util.*;

@SuppressWarnings("unused")
public class JavaGraphTasks {
    /**
     * Эйлеров цикл.
     * Средняя
     * <p>
     * Дан граф (получатель). Найти по нему любой Эйлеров цикл.
     * Если в графе нет Эйлеровых циклов, вернуть пустой список.
     * Соседние дуги в списке-результате должны быть инцидентны друг другу,
     * а первая дуга в списке инцидентна последней.
     * Длина списка, если он не пуст, должна быть равна количеству дуг в графе.
     * Веса дуг никак не учитываются.
     * <p>
     * Пример:
     * <p>
     * G -- H
     * |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     * <p>
     * Вариант ответа: A, E, J, K, D, C, H, G, B, C, I, F, B, A
     * <p>
     * Справка: Эйлеров цикл -- это цикл, проходящий через все рёбра
     * связного графа ровно по одному разу
     */
    public static List<Graph.Edge> findEulerLoop(Graph graph) {
        throw new NotImplementedError();
    }

    /**
     * Минимальное остовное дерево.
     * Средняя
     * <p>
     * Дан граф (получатель). Найти по нему минимальное остовное дерево.
     * Если есть несколько минимальных остовных деревьев с одинаковым числом дуг,
     * вернуть любое из них. Веса дуг не учитывать.
     * <p>
     * Пример:
     * <p>
     * G -- H
     * |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     * <p>
     * Ответ:
     * <p>
     * G    H
     * |    |
     * A -- B -- C -- D
     * |    |    |
     * E    F    I
     * |
     * J ------------ K
     */
    public static Graph minimumSpanningTree(Graph graph) {
        throw new NotImplementedError();
    }

    /**
     * Максимальное независимое множество вершин в графе без циклов.
     * Сложная
     * <p>
     * Дан граф без циклов (получатель), например
     * <p>
     * G -- H -- J
     * |
     * A -- B -- D
     * |         |
     * C -- F    I
     * |
     * E
     * <p>
     * Найти в нём самое большое независимое множество вершин и вернуть его.
     * Никакая пара вершин в независимом множестве не должна быть связана ребром.
     * <p>
     * Если самых больших множеств несколько, приоритет имеет то из них,
     * в котором вершины расположены раньше во множестве this.vertices (начиная с первых).
     * <p>
     * В данном случае ответ (A, E, F, D, G, J)
     * <p>
     * Эта задача может быть зачтена за пятый и шестой урок одновременно
     */
    // Трудоемкость T = O(n)
    // Ресурсоемкость R = O(n)
    public static Set<Graph.Vertex> largestIndependentVertexSet(Graph graph) {

        Set<Graph.Vertex> result = new HashSet<>();
        Set<Graph.Vertex> invertedResult = new HashSet<>();
        Set<Graph.Edge> edges = graph.getEdges();

        for (Graph.Edge edge : edges) {
            Graph.Vertex begin = edge.getBegin();
            Graph.Vertex end = edge.getEnd();
            if (result.isEmpty() || !invertedResult.contains(begin)) {
                result.add(begin);
                invertedResult.add(end);
            }
            if (result.contains(begin)) {
                invertedResult.add(end);
            } else result.add(end);
        }

        if (result.size() > invertedResult.size())
            return result;
        else return invertedResult;
    }

    /**
     * Наидлиннейший простой путь.
     * Сложная
     * <p>
     * Дан граф (получатель). Найти в нём простой путь, включающий максимальное количество рёбер.
     * Простым считается путь, вершины в котором не повторяются.
     * Если таких путей несколько, вернуть любой из них.
     * <p>
     * Пример:
     * <p>
     * G -- H
     * |    |
     * A -- B -- C -- D
     * |    |    |    |
     * E    F -- I    |
     * |              |
     * J ------------ K
     * <p>
     * Ответ: A, E, J, K, D, C, H, G, B, F, I
     */
    // Трудоемкость T = O(v)
    // Ресурсоемкость R = O(v)
    public static Path longestSimplePath(Graph graph) {

        Set<Graph.Vertex> vertices = graph.getVertices();
        Graph.Vertex first = vertices.iterator().next();
        Path longestPath = new Path(first);
        Deque<Path> deque = new ArrayDeque<>();

        for (Graph.Vertex vertex : vertices) {
            deque.add(new Path(vertex));
        }

        while (!deque.isEmpty()) {
            Path curPath = deque.pop();
            List<Graph.Vertex> listOfVertices = curPath.getVertices();
            if (curPath.getLength() > longestPath.getLength()) {
                longestPath = curPath;
                if (listOfVertices.size() > vertices.size()) break;
            }
            Set<Graph.Vertex> neighbors = graph.getNeighbors(listOfVertices.get(listOfVertices.size() - 1));
            for (Graph.Vertex neighbor : neighbors) {
                if (!curPath.contains(neighbor)) {
                    deque.addLast(new Path(curPath, graph, neighbor));
                }
            }
        }

        return longestPath;
    }
}
