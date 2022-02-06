package com.company;

/*
Description:
Бернард смотрит, что происходит в парке в разные моменты времени. Он хочет знать в каком состоянии находились машины в нужное ему время.
Они находятся в двух состояниях либо они играют GAME CONTINUES
Либо они находятся на починке GAME OVER
Вам будут даны интервалы времени, когда роботы находились на починке, и время, которое интересовало Бернарда.
Бернард смотрит данные за последний месяц. Поэтому он вводит число и время.
Например 2 -е число, 15 часов 13 минут:   2 15:13
Входные данные: Сначала идёт список роботов в виде: Имя | даты поломки через запятую в виду "DD HH:MM"
Потом идёт список времени, которое интересовало Бернарда в формате "DD HH:MM"
Кол-во роботов не превышает 10, кол-во дат в запросе не больше 10
Время ремонта робота включает в себя границы заданных промежутков времени
Добавлен идентификатор строки R - роботы, T - время
Пример входных данных:

R:Тедди| 4 18:12 - 6 19:32, 17 13:12 - 20 14:42
R:Долорес| 12 14:12 - 12 18:15
R:Мейв| 13 9:21 - 13 21:23, 14 7:23 - 15 12:12 , 17 18:00 - 19 23:22, 22 20:25 - 26 15:14
R:Питер| 8 9:05 - 10 4:55
R:Клементина| 15 4:00 - 16 14:43
T:8 14:21
T:17 19:17

Выходные данные: дата, состояния роботов
Пример выходных данных:

8 14:21
Тедди GAME CONTINUES
Долорес GAME CONTINUES
Мейв GAME CONTINUES
Питер GAME OVER
Клементина GAME CONTINUES
17 19:17
Тедди GAME OVER
Долорес GAME CONTINUES
Мейв GAME OVER
Питер GAME CONTINUES
Клементина GAME CONTINUES
 */

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class _6GameOver {

    public static void main(String[] args) throws java.lang.Exception {
        Scanner sc = new Scanner(System.in);
        List<String> names = new ArrayList<>();
        Map<String, List<Long>> unWorkingRobotsMap = new HashMap<>();
        List<String> reports = new ArrayList<>();//System.in is a standard input stream

        while (sc.hasNextLine()) { // get the input
            String line = sc.nextLine();
            if (line.isEmpty()) {
                break;
            }
            if (line.charAt(0) == 'R') {
                String name = line.substring(2, line.lastIndexOf("|"));
                names.add(name);
                List<Long> notWorkingTimes = new ArrayList<>();
                line = line.substring(line.lastIndexOf("|") + 1).trim();
                String[] diapazons = line.split(", ");
                for (String s : diapazons) {
                    String[] time = s.split(" - ");
                    long start = convertStringToLong(time[0]);
                    long end = convertStringToLong(time[1]);
                    notWorkingTimes.addAll(LongStream.range(start, end).boxed().collect(Collectors.toList()));
                }
                unWorkingRobotsMap.put(name, notWorkingTimes);
            } else if (line.charAt(0) == 'T') {
                reports.add(line.substring(2));
            }
        }
        for (String report : reports) {
            System.out.println(report);
            for (String name : names) {
                System.out.print(name + " ");
                if (unWorkingRobotsMap.get(name).contains(convertStringToLong(report))) {
                    System.out.println("GAME OVER");
                } else {
                    System.out.println("GAME CONTINUES");
                }
            }
        }
    }

    private static long convertStringToLong(String time) {
        List<Long> longWay = Arrays.stream(time.split("[ :]")).mapToLong(Long::parseLong).boxed().collect(Collectors.toList());
        return longWay.get(0) * 24 * 60 + longWay.get(1) * 60 + longWay.get(2);
    }
}