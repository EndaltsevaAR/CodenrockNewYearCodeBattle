package com.company;

/*
Description:
Зишифруйте сообщение меняя буквы на их порядковый номер в алфавите. Пробелы при этом не учитывать. Строки будут даны без знаков препинания,
только с пробелами. Регистр не учитывать.
Входные данные: шифруемая строка, длиной до 1000 символов, на латинице
Пример входных данных:
MR Robot
Выходные данные: через запятую порядковый номер букв в алфавите
Пример выходных данных:
13,18,18,15,2,15,20
 */

import java.util.Scanner;

public class Alphabet {

    public static void main(String[] args) throws java.lang.Exception {
        Scanner sc = new Scanner(System.in); //System.in is a standard input stream
        String response = "";
        while (sc.hasNextLine()) { // get the input
            String line = sc.nextLine();
            response += line;
            StringBuilder stringBuilder = new StringBuilder();
            for (String s : response.split("")) {
                char c = s.toLowerCase().charAt(0);
                if (Character.isAlphabetic(c)) {
                    stringBuilder.append((int) c - 96).append(",");
                }
            }
            System.out.println(stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(",")));
            response = "";
        }
        sc.close();
        System.out.println(response);
    }
}