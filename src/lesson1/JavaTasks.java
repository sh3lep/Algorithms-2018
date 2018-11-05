package lesson1;

import kotlin.NotImplementedError;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@SuppressWarnings("unused")
public class JavaTasks {
    /**
     * Сортировка времён
     * <p>
     * Простая
     * (Модифицированная задача с сайта acmp.ru)
     * <p>
     * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС,
     * каждый на отдельной строке. Пример:
     * <p>
     * 13:15:19
     * 07:26:57
     * 10:00:03
     * 19:56:14
     * 13:15:19
     * 00:40:31
     * <p>
     * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
     * сохраняя формат ЧЧ:ММ:СС. Одинаковые моменты времени выводить друг за другом. Пример:
     * <p>
     * 00:40:31
     * 07:26:57
     * 10:00:03
     * 13:15:19
     * 13:15:19
     * 19:56:14
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortTimes(String inputName, String outputName) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        List<Date> times = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(inputName))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Date date = sdf.parse(line);
                times.add(date);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
        Collections.sort(times);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputName))) {
            for (Date time : times) {
                writer.write(sdf.format(time));
                writer.newLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Трудоемкость T = O(n * log(n))
    // Ресурсоемкость R = O(n)

    /**
     * Сортировка адресов
     * <p>
     * Средняя
     * <p>
     * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
     * где они прописаны. Пример:
     * <p>
     * Петров Иван - Железнодорожная 3
     * Сидоров Петр - Садовая 5
     * Иванов Алексей - Железнодорожная 7
     * Сидорова Мария - Садовая 5
     * Иванов Михаил - Железнодорожная 7
     * <p>
     * Людей в городе может быть до миллиона.
     * <p>
     * Вывести записи в выходной файл outputName,
     * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
     * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
     * <p>
     * Железнодорожная 3 - Петров Иван
     * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
     * Садовая 5 - Сидоров Петр, Сидорова Мария
     * <p>
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortAddresses(String inputName, String outputName) {
        throw new NotImplementedError();
    }

    /**
     * Сортировка температур
     * <p>
     * Средняя
     * (Модифицированная задача с сайта acmp.ru)
     * <p>
     * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
     * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
     * Например:
     * <p>
     * 24.7
     * -12.6
     * 121.3
     * -98.4
     * 99.5
     * -12.6
     * 11.0
     * <p>
     * Количество строк в файле может достигать ста миллионов.
     * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
     * Повторяющиеся строки сохранить. Например:
     * <p>
     * -98.4
     * -12.6
     * -12.6
     * 11.0
     * 24.7
     * 99.5
     * 121.3
     */
    static public void sortTemperatures(String inputName, String outputName) {
        ArrayList<Double> list = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(inputName))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                double currentTemperature = Double.parseDouble(line);
                if (currentTemperature >= -237.0 || currentTemperature <= 500.0) {
                    list.add(currentTemperature);
                } else throw new NumberFormatException();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.sort(list);

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputName))) {
            for (Double temp : list) {
                bufferedWriter.write(temp.toString());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Трудоемкость T = O(n * log(n))
    // Ресурсоемкость R = O(n)

    /**
     * Сортировка последовательности
     * <p>
     * Средняя
     * (Задача взята с сайта acmp.ru)
     * <p>
     * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
     * <p>
     * 1
     * 2
     * 3
     * 2
     * 3
     * 1
     * 2
     * <p>
     * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
     * а если таких чисел несколько, то найти минимальное из них,
     * и после этого переместить все такие числа в конец заданной последовательности.
     * Порядок расположения остальных чисел должен остаться без изменения.
     * <p>
     * 1
     * 3
     * 3
     * 1
     * 2
     * 2
     * 2
     */
    static public void sortSequence(String inputName, String outputName) {
        Map<Integer, Integer> map = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        int repeatingNumber = 0;
        int frequency = 0;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(inputName))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Integer currentNum = Integer.parseInt(line);
                if (currentNum > 0) {
                    list.add(currentNum);
                } else throw new NumberFormatException();
                Integer currentFrequency = map.getOrDefault(currentNum, 0) + 1;
                if (currentFrequency > frequency || (frequency == currentFrequency && currentNum < repeatingNumber)) {
                    frequency = currentFrequency;
                    repeatingNumber = currentNum;
                }
                map.put(currentNum, currentFrequency);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputName))) {
            for (int number : list) {
                if (number != repeatingNumber) {
                    writer.write(Integer.toString(number));
                    writer.newLine();
                }
            }
            for (int i = 0; i < frequency; i++) {
                writer.write(Integer.toString(repeatingNumber));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Трудоемкость T = O(n)
    // Ресурсоемкость R = O(n)

    /**
     * Соединить два отсортированных массива в один
     * <p>
     * Простая
     * <p>
     * Задан отсортированный массив first и второй массив second,
     * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
     * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
     * <p>
     * first = [4 9 15 20 28]
     * second = [null null null null null 1 3 9 13 18 23]
     * <p>
     * Результат: second = [1 3 4 9 9 13 15 20 23 28]
     */
    static <T extends Comparable<T>> void mergeArrays(T[] first, T[] second) {
        throw new NotImplementedError();
    }
}
