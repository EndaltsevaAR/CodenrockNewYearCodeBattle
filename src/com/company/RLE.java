package com.company;

/*
Description:
RLE

RLE - самый простой алгоритм сжатия. Его суть состоит в замене повторяющихся данных образцом, и количеством повтора образца.
Алгоритм подходит для сжатия данных, имеющих большое количество повторений.  При сжатии учитывайте регистр.
Напишите программу, которая реализует RLE для строк, состоящих из букв латинского алфавита, не имеющих пробелы.
Во входных данных только одна строка.
Например: DDDDFFFFHHHHk → 4D,4F,4H,1k
 */

import java.util.Scanner;

public class RLE {
    public static void main(String[] args) throws java.lang.Exception {
        Scanner sc = new Scanner(System.in); //System.in is a standard input stream

        while (sc.hasNextLine()) { // get the input
            // your code
            String line = sc.nextLine();
            StringBuilder result = new StringBuilder();
            String[] letters = line.split("");
            int count = 1;
            String letter = letters[0];
            for (int i = 1; i < letters.length; i++) {
                if (letters[i].equals(letter)) {
                    count++;
                } else {
                    result.append(count).append(letter).append(",");
                    count = 1;
                    letter = letters[i];
                }
            }
            if (count == 1) {
                result.append(count).append(letter).append(",");
            }
            System.out.println(result.deleteCharAt(result.lastIndexOf(",")));
        }
    }
}