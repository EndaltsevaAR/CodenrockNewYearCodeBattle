package com.company;

/*
Description:
Литорея

Литорея - метод шифрования русского алфавита, где Е и Ё объединяются. Остальные 32 символа разделяют на 2 группы по 16 штук, внутри группы
производится зеркальная замена букв, что ближе к концу по местоположению в группе на те, что ближе к началу и наоборот.
Например: 1-я буква заменяется на 16-ю, а - п, 3-я буква заменяется на 14-ю в-н
Учитывайте регистр
Реализуйте данный метод шифрования.
Входные данные: строка до 100 символов, на русском языке
Пример входных данных:  Мир это опасное место
Выходные данные: зашифрованная строка
Пример выходных данных: Гзя тэб бапювбк гкюэб
 */

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class _7Litorrhea {
    public static void main(String[] args) throws java.lang.Exception {
        Scanner sc = new Scanner(System.in); //System.in is a standard input stream

        String[] abs = "АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдежзийклмнопрстуфхцчшщъыьэюя".split("");
        String[] reversAbs = new String[abs.length];
        Map<String, String> codingMap = new HashMap<>(); //key - abs, value - revers letters
        for (int i = 0; i < abs.length / 16; i++) {
            for (int j = 0; j < 16; j++) {
                reversAbs[i * 16 + 15 - j] = abs[i * 16 + j];
                codingMap.put(abs[i * 16 + 15 - j], reversAbs[i * 16 + 15 - j]);
            }
        }

        while (sc.hasNextLine()) { // get the input
            String line = sc.nextLine();
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < line.length(); i++) {
                if (Character.isAlphabetic(line.charAt(i))) {
                    stringBuilder.append(codingMap.get(Character.toString(line.charAt(i))));
                } else {
                    stringBuilder.append(line.charAt(i));
                }
            }
            System.out.println(stringBuilder);
        }
    }
}
