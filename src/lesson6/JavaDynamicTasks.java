package lesson6;

import kotlin.NotImplementedError;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Collections.reverse;

@SuppressWarnings("unused")
public class JavaDynamicTasks {
    /**
     * Наибольшая общая подпоследовательность.
     * Средняя
     * <p>
     * Дано две строки, например "nematode knowledge" и "empty bottle".
     * Найти их самую длинную общую подпоследовательность -- в примере это "emt ole".
     * Подпоследовательность отличается от подстроки тем, что её символы не обязаны идти подряд
     * (но по-прежнему должны быть расположены в исходной строке в том же порядке).
     * Если общей подпоследовательности нет, вернуть пустую строку.
     * При сравнении подстрок, регистр символов *имеет* значение.
     */
    public static String longestCommonSubSequence(String first, String second) {
        throw new NotImplementedError();
    }

    /**
     * Наибольшая возрастающая подпоследовательность
     * Средняя
     * <p>
     * Дан список целых чисел, например, [2 8 5 9 12 6].
     * Найти в нём самую длинную возрастающую подпоследовательность.
     * Элементы подпоследовательности не обязаны идти подряд,
     * но должны быть расположены в исходном списке в том же порядке.
     * Если самых длинных возрастающих подпоследовательностей несколько (как в примере),
     * то вернуть ту, в которой числа расположены раньше (приоритет имеют первые числа).
     * В примере ответами являются 2, 8, 9, 12 или 2, 5, 9, 12 -- выбираем первую из них.
     */
    // Трудоемкость T = O(n^2)
    // Ресурсоемкость R = О(n)
    public static List<Integer> longestIncreasingSubSequence(List<Integer> list) {

        int size = list.size();

        if ((size == 1 || size == 0) || (size == 2 && list.get(0) < list.get(1))) {
            return list;
        }

        int[] indexes = new int[size], lengthArray = new int[size];
        for (int i = 0; i < size; i++) {
            lengthArray[i] = 1;
            indexes[i] = -1;
            for (int j = 0; j < i; j++) {
                if (list.get(j) < list.get(i) && lengthArray[j] + 1 > lengthArray[i]) {
                    lengthArray[i] = lengthArray[j] + 1;
                    indexes[i] = j;
                }
            }
        }

        int position = 0;
        int length = lengthArray[0];
        for (int i = 0; i < list.size(); i++) {
            if (lengthArray[i] > length) {
                position = i;
                length = lengthArray[i];
            }
        }

        List<Integer> result = new ArrayList<>();
        while (position != -1) {
            result.add(list.get(position));
            position = indexes[position];
        }

        reverse(result);

        return result;
    }

    /**
     * Самый короткий маршрут на прямоугольном поле.
     * Сложная
     * <p>
     * В файле с именем inputName задано прямоугольное поле:
     * <p>
     * 0 2 3 2 4 1
     * 1 5 3 4 6 2
     * 2 6 2 5 1 3
     * 1 4 3 2 6 2
     * 4 2 3 1 5 0
     * <p>
     * Можно совершать шаги длиной в одну клетку вправо, вниз или по диагонали вправо-вниз.
     * В каждой клетке записано некоторое натуральное число или нуль.
     * Необходимо попасть из верхней левой клетки в правую нижнюю.
     * Вес маршрута вычисляется как сумма чисел со всех посещенных клеток.
     * Необходимо найти маршрут с минимальным весом и вернуть этот минимальный вес.
     * <p>
     * Здесь ответ 2 + 3 + 4 + 1 + 2 = 12
     */
    // Трудоемкость T = O(m * n)
    // Ресурсоемкость R = O(m * n)
    public static int shortestPathOnField(String inputName) throws IOException {
        List<String> lines = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(inputName))) {
            String line;
            Pattern pattern = Pattern.compile("[[a-zA-Z]-~#@*+%{}<>\\[\\]|\"_^]");

            while ((line = bufferedReader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    return -1; // не бросаем исключение, чтобы проходили тесты
                }
                lines.add(line.replaceAll(" ", ""));
            }
        }

        if (lines.size() == 0) {
            return -1;
        }

        int width = lines.get(0).length(), height = lines.size();
        int[][] field = new int[height][width];
        field[0][0] = Character.getNumericValue(lines.get(0).charAt(0));

        for (int j = 1; j < width; j++) {
            field[0][j] = Character.getNumericValue(lines.get(0).charAt(j)) + field[0][j - 1];
        }
        for (int i = 1; i < height; i++) {
            field[i][0] = Character.getNumericValue(lines.get(i).charAt(0)) + field[i - 1][0];
        }

        for (int i = 1; i < width; i++) {
            for (int j = 1; j < height; j++) {
                int minPreviousSum = Math.min(Math.min(field[j - 1][i], field[j][i - 1]), field[j -1][i -1]);
                field[j][i] = Character.getNumericValue(lines.get(j).charAt(i)) + minPreviousSum;
            }
        }
        return field[height - 1][width - 1];
    }
    // Задачу "Максимальное независимое множество вершин в графе без циклов"
    // смотрите в уроке 5
}

