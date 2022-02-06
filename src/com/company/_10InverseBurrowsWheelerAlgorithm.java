package com.company;

/*
Description:
Напишите программу, которая делает обратное преобразование Алгоритма Барроуза-Уилера
Алгоритм заключается в разбиении входной строки (строка преобразованная алгоритмом ) на символы,после чего образуется столбец (на рисунке добавление 1)
Лексикографически(по алфавиту) сортируют образованные строки. После к полученному добавляют слева символы из добавления 1. Снова сортируют, и т.д.
до тех пор, пока количество символов в полученных строках не станет равным количеству символов во входной строке.
При столбцах, где есть добавление добавляем символы из первого столбца.
В конце образуется столбец, где искомая строка это строка c символом конца строки. Символ конца строки обозначается |.

Лексикографическая сортировка производится в соответствии с таблицей символов Unicode.
Длина входной строки до 100 символов
Пример входных данных: |_deipPPier
Пример выходных данных: Pied_Piper|
 */

import java.util.*;
import java.util.stream.Collectors;

public class _10InverseBurrowsWheelerAlgorithm
{
    public static void main (String[] args) throws java.lang.Exception
    {
        Scanner sc= new Scanner(System.in); //System.in is a standard input stream

        while(sc.hasNextLine()){ // get the input
            String line= sc.nextLine();
            List<String> initLines = Arrays.stream(line.split("")).collect(Collectors.toList());
            List<String> resLines = new ArrayList<>(initLines);
            for (int i = 1; i < line.length(); i++) {

                Collections.sort(resLines);

                for (int j = 0; j <resLines.size() ; j++) {
                    resLines.set(j, initLines.get(j) + resLines.get(j));
                }


            }
            for (String resiltString :resLines) {
                if (resiltString.charAt(resiltString.length() - 1) == '|' && resiltString.length() == initLines.size()) {
                    System.out.println(resiltString);
                    return;
                }
            }
        }
    }
}
