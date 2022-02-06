package com.company;

/*
Description:
Инвесторы

Ричард и Эрлих идут на переговоры с k инвесторами. У них два метода ведения переговоров, либо вести себя по-хамски, тем самым не дать прогнуть себя,
либо вести себя прилично. У них только n дней, для того, чтобы представить свой проект, всем инвесторам.
Они понимают, что если вести себя по-хамски, то инвесторы не будут посылать их на повторные переговоры, но успех при этом будет сомнителен.
Если же они будут себя вести прилично, то инвесторы будут наглеть и устраивать больше встреч.
Они изначально составляют план, сколько времени они готовы тратить на каждого инвестора. Если они готовы рискнуть, то они будут вести себя
максимально неприлично, на этого инвестора планируют немного дней. Если же инвестор стоит того, и они готовы тратить время, чтобы представлять
проект снова и снова, то они будут терпеливы, и планируют больше дней. Так как у них ограничено время, то они могут просто не пойти на встречу.
Ричард для каждого инвестора составил функцию зависимости вероятности успеха ведения переговоров от количества дней, потраченных на инвестора.
Функции неубывающие.  Смотрите таблицу. Таблица составлена для 5 дней, и 4 инвесторов.

 	0 день	1 день	2 день	3 день	4 день	5 день
1 инвестор	0	1	2	3	4	6
2 инвестор	0	2	3	3	4	6
3 инвестор	0	3	4	5	5	5
4 инвестор	0	6	7	7	7	9

Ричард считает успешной стратегию, при которой сумма вероятностей успехов максимальна.

Для данных из таблицы выше успешная стратегия следующая:

 	Количество дней	Вероятность успеха
1 инвестор	0	0
2 инвестор	1	2
3 инвестор	2	4
4 инвестор	2	7
При этом максимальная суммарная вероятность равна 13.
Ваша задача найти максимальную суммарную вероятность, для входных данных.
Задача подразумевает решение методом динамического программирования.
Входные данные: k, n, функции успеха
Пример входных данных:

4

5

1,2,3,4,6

2,3,3,4,6

3,4,5,5,5

6,7,7,7,9
end         //for breaking while loop

k ≤ 20, n ≤ 20
Выходные данные: максимальная суммарная вероятность
 */

import java.util.*;
import java.util.stream.Collectors;

public class _14Investors {
    private static List<List<Integer>> table = new ArrayList<>();
    private static int kInvestors = 0;
    private static int nDays = 0;

    public static void main(String[] args) throws java.lang.Exception {
        Scanner sc = new Scanner(System.in); //System.in is a standard input stream
        String line = "";

        while (sc.hasNextLine()) {
            line = sc.nextLine();
            if (line == null || line.isEmpty()) continue;
            if (line.equals("end")) break; //temp
            if (kInvestors == 0) {
                kInvestors = Integer.parseInt(line);
            } else if (nDays == 0) {
                nDays = Integer.parseInt(line);
            } else {
                List<Integer> eachInvestors = Arrays.stream(line.split(",")).map(Integer::parseInt).collect(Collectors.toList());
                if (eachInvestors.size() == nDays + 1 && eachInvestors.get(0).equals(0)) {
                    List<Integer> temp = new ArrayList<>();
                    for (int i = 1; i < eachInvestors.size(); i++) {
                        temp.add(eachInvestors.get(i));
                    }
                    table.add(temp);
                } else {
                    table.add(eachInvestors);
                }
            }
        }
        if (kInvestors == 0 || nDays == 0) {
            System.out.println(0);
            return;
        }
        List<Map<Integer, Integer>> initMaxInvestors = stepForFirstDay();  //список возможных максимальных изначальных позиций. В мапе ключ: номер инвестора, значение номер дня

        for (int i = 1; i < nDays; i++) {
            initMaxInvestors = stepForNextDays(initMaxInvestors);
        }

        int maxProbability = 0;
        for (Map<Integer, Integer> versionOfInvest : initMaxInvestors) {
            int currentMax = maxFromMap(versionOfInvest);
            if (currentMax > maxProbability) {
                maxProbability = currentMax;
            }
        }
        System.out.println(maxProbability);
    }

    private static int maxFromMap(Map<Integer, Integer> versionOfInvest) {
        int currentMax = 0;
        for (Map.Entry<Integer, Integer> pair : versionOfInvest.entrySet()) {
            currentMax += table.get(pair.getKey()).get(pair.getValue());
        }
        return currentMax;
    }

    private static List<Map<Integer, Integer>> stepForFirstDay() {
        List<Map<Integer, Integer>> maxFirstDayVars = new ArrayList<>();

        List<Integer> firstDayInvestors = new ArrayList<>();
        for (List<Integer> list : table) {
            firstDayInvestors.add(list.get(0));
        }
        int max = firstDayInvestors.stream().max(Comparator.naturalOrder()).get();


        for (int i = 0; i < firstDayInvestors.size(); i++) {
            if (firstDayInvestors.get(i).equals(max)) {
                Map<Integer, Integer> firstInvestor = new HashMap<>();
                firstInvestor.put(i, 0);
                maxFirstDayVars.add(firstInvestor);
            }
        }
        return maxFirstDayVars;
    }

    private static List<Map<Integer, Integer>> stepForNextDays(List<Map<Integer, Integer>> initMaxInvestors) {
        List<Map<Integer, Integer>> resultMap = new ArrayList<>();
        for (Map<Integer, Integer> currentFoundDays : initMaxInvestors) {
            int currentMax = 0;
            int currentDays = 0;
            for (Map.Entry<Integer, Integer> pair : currentFoundDays.entrySet()) {
                currentMax += table.get(pair.getKey()).get(pair.getValue());
                currentDays += pair.getValue() + 1;
            }
            List<Integer> dayInvestor = new ArrayList<>();
            for (int i = 0; i < table.size(); i++) {
                if (currentFoundDays.containsKey(i)) {
                    dayInvestor.add(table.get(i).get(currentFoundDays.get(i) + 1) - table.get(i).get(currentFoundDays.get(i)));
                } else {
                    dayInvestor.add(table.get(i).get(0));
                }
            }
            int max = dayInvestor.stream().max(Comparator.naturalOrder()).get();


            if (currentDays < nDays) {
                for (int i = 0; i < table.size(); i++) {
                    if (table.get(i).get(currentDays) > currentMax + max) {
                        Map<Integer, Integer> extra = new HashMap<>();
                        extra.put(i, currentDays);
                        resultMap.add(extra);
                        return resultMap;
                    }
                }
            }


            for (int i = 0; i < dayInvestor.size(); i++) {
                if (dayInvestor.get(i) == max) {
                    Map<Integer, Integer> tempCopy = new HashMap<>(currentFoundDays);
                    if (tempCopy.containsKey(i)) {
                        tempCopy.put(i, tempCopy.get(i) + 1);
                    } else {
                        tempCopy.put(i, 0);
                    }
                    resultMap.add(tempCopy);
                }
            }

        }
        return resultMap;
    }
}