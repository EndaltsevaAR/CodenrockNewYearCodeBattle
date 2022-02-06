package com.company;

/*
Description:
Напишите программу преобразования Барроуза-Уилера, которое используется перед применением алгоритма сжатия RLE, для большей его эффективности.
Описание алгоритма преобразования Барроуза-Уилера:
Составляется список всех циклических сдвигов входной строки.
Производится лексикографическая (в алфавитном порядке) сортировка списка.
В качестве выходной строки выбирается последние буквы отсортированных строк.
Лексикографическая сортировка происходит в соответствии с кодировкой Unicode
Длина входной строки до 100 символов
Пример входных данных: Pied_Piper
Пример выходных данных: r_deipPPie
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class _9BurrowsWheelerAlgorithm {
    public static void main(String[] args) throws java.lang.Exception {
        Scanner sc = new Scanner(System.in); //System.in is a standard input stream

        while (sc.hasNextLine()) {
            String line = sc.nextLine();// get the input
            List<String> firstIter = new ArrayList<>();
            for (int i = 0; i < line.length(); i++) {   //first iteration
                String l = line.substring(i) + line.substring(0, i);
                firstIter.add(l);
            }
            Collections.sort(firstIter);    //second iteration
            StringBuilder stringBuilder = new StringBuilder();
            for (String s : firstIter) {
                stringBuilder.append(s.charAt(s.length() - 1));
            }
            System.out.println(stringBuilder);
        }
    }
}
