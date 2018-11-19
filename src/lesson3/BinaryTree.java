package lesson3;

import kotlin.NotImplementedError;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

// Attention: comparable supported but comparator is not
@SuppressWarnings("WeakerAccess")
public class BinaryTree<T extends Comparable<T>> extends AbstractSet<T> implements CheckableSortedSet<T> {

    private static class Node<T> {
        T value;

        Node<T> left = null;

        Node<T> right = null;

        Node(T value) {
            this.value = value;
        }
    }

    private Node<T> root = null;

    private int size = 0;

    @Override
    public boolean add(T t) {
        Node<T> closest = find(t);
        int comparison = closest == null ? -1 : t.compareTo(closest.value);
        if (comparison == 0) {
            return false;
        }
        Node<T> newNode = new Node<>(t);
        if (closest == null) {
            root = newNode;
        } else if (comparison < 0) {
            assert closest.left == null;
            closest.left = newNode;
        } else {
            assert closest.right == null;
            closest.right = newNode;
        }
        size++;
        return true;
    }

    public boolean checkInvariant() {
        return root == null || checkInvariant(root);
    }

    private boolean checkInvariant(Node<T> node) {
        Node<T> left = node.left;
        if (left != null && (left.value.compareTo(node.value) >= 0 || !checkInvariant(left))) return false;
        Node<T> right = node.right;
        return right == null || right.value.compareTo(node.value) > 0 && checkInvariant(right);
    }

    /**
     * Удаление элемента в дереве
     * Средняя
     */
    // Трудоемкость - O(h)
    // Ресурсоемкость - O(h)
    // h - высота бинарного дерева
    // Задание выполнено на основе алгоритма, представленного по ссылке ниже:
    // https://neerc.ifmo.ru/wiki/index.php?title=Дерево_поиска,_наивная_реализация
    @Override
    public boolean remove(Object o) {
        T t = (T) o;
        Node<T> result = delete(root, t);
        size--;
        return (result != null);
    }

    private Node<T> delete(Node<T> root, T t) {
        int comparison = t.compareTo(root.value);

        if (comparison < 0) {
            root.left = delete(root.left, t);
        } else if (comparison > 0) {
            root.right = delete(root.right, t);
        } else if (root.right != null) {
            root.value = minimum(root.right).value;
            root.right = delete(root.right, root.value);
        } else if (root.left != null) {
            root.value = maximum(root.left).value;
            root.left = delete(root.left, root.value);
        } else {
            root = null;
        }

        return root;
    }

    private Node<T> minimum(Node<T> t) {
        if (t.left == null) {
            return t;
        }
        return minimum(t.left);
    }

    private Node<T> maximum(Node<T> t) {
        if (t.right == null) {
            return t;
        }
        return maximum(t.right);
    }

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T t = (T) o;
        Node<T> closest = find(t);
        return closest != null && t.compareTo(closest.value) == 0;
    }

    private Node<T> find(T value) {
        if (root == null) return null;
        return find(root, value);
    }

    private Node<T> find(Node<T> start, T value) {
        int comparison = value.compareTo(start.value);
        if (comparison == 0) {
            return start;
        } else if (comparison < 0) {
            if (start.left == null) return start;
            return find(start.left, value);
        } else {
            if (start.right == null) return start;
            return find(start.right, value);
        }
    }

    public class BinaryTreeIterator implements Iterator<T> {
        private Node<T> current;
        private LinkedList<Node<T>> list;

        private BinaryTreeIterator() {
            list = new LinkedList<>();
            current = root;
            while (current != null) {
                list.addFirst(current);
                current = current.left;
            }
        }

        /**
         * Поиск следующего элемента
         * Средняя
         */
        //Трудоёмкость Т = O(N)
        //Ресурсоёмкость R = O(1)
        private Node<T> findNext() {
            return list.pop();
        }

        @Override
        public boolean hasNext() {
            return !list.isEmpty();
        }

        @Override
        public T next() {
            Node<T> node;
            current = findNext();
            node = current;
            if (current == null) throw new NoSuchElementException();
            if (node.right != null) {
                node = node.right;
                while (node != null) {
                    list.addFirst(node);
                    node = node.left;
                }
            }
            return current.value;
        }

        /**
         * Удаление следующего элемента
         * Сложная
         */
        // Трудоемкость - O(h)
        // Ресурсоемкость - O(h)
        // h - высота бинарного дерева
        @Override
        public void remove() {
            root = delete(root, current.value);
            size--;
        }
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new BinaryTreeIterator();
    }

    @Override
    public int size() {
        return size;
    }


    @Nullable
    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    /**
     * Для этой задачи нет тестов (есть только заготовка subSetTest), но её тоже можно решить и их написать
     * Очень сложная
     */
    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        // TODO
        throw new NotImplementedError();
    }

    /**
     * Найти множество всех элементов меньше заданного
     * Сложная
     */
    // Трудоемкость T = O(n)
    // Ресурсоемкость R = O(n)
    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        SortedSet<T> sortedSet = new TreeSet<>();
        newHeadSet(toElement, sortedSet, root);
        return sortedSet;
    }

    private void newHeadSet(T toElement, SortedSet<T> sortedSet, Node<T> node) {
        int comparison = node.value.compareTo(toElement);

        if (comparison < 0) {
            sortedSet.add(node.value);
            if (node.left != null) {
                newSet(sortedSet, node.left);
            }
            if (node.right != null) {
                newHeadSet(toElement, sortedSet, node.right);
            }
        } else if (node.left != null) {
            if (comparison == 0) {
                newSet(sortedSet, node.left);
            } else {
                newHeadSet(toElement, sortedSet, node.left);
            }
        }
    }

    private void newSet(SortedSet<T> sortedSet, Node<T> node) {
        sortedSet.add(node.value);

        if (node.left != null) {
            newSet(sortedSet, node.left);
        }
        if (node.right != null) {
            newSet(sortedSet, node.right);
        }
    }

    /**
     * Найти множество всех элементов больше или равных заданного
     * Сложная
     */
    // Трудоемкость T = O(n)
    // Ресурсоемкость R = O(n)
    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        SortedSet<T> sortedSet = new TreeSet<>();
        newTailSet(fromElement, sortedSet, root);
        return sortedSet;
    }

    private void newTailSet(T fromElement, SortedSet<T> sortedSet, Node<T> node) {
        int comparison = node.value.compareTo(fromElement);

        if (comparison >= 0) {
            sortedSet.add(node.value);
            if (node.right != null) {
                newSet(sortedSet, node.right);
            }
            if (node.left != null) {
                newTailSet(fromElement, sortedSet, node.left);
            }
        } else if (node.right != null) {
            newTailSet(fromElement, sortedSet, node.right);
        }
    }

    @Override
    public T first() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current.value;
    }

    @Override
    public T last() {
        if (root == null) throw new NoSuchElementException();
        Node<T> current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current.value;
    }
}
