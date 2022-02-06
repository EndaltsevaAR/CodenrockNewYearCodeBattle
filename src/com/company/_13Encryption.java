package com.company;

/*
Description:
Шифрование

Зашифруйте сообщение с помощью перестановки букв внутри слов и шифра Цезаря
Дана строка, её надо разбить по словам. каждое слово зашифровать подстановкой, для каждой буквы в слове определить её порядок в алфавите.
и потом сдвинуть на это число с помощью шифра Цезаря.
Чтобы совершить перестановку, условно берем пустое поле, состоящее из n клеток, где n - количество букв в слове, добавляем в поле буквы
шифрованного слова по алгоритму:
Для вставки каждой следующей буквы отсчитываем две пустые клетки в поле, относительно незаполненных клеток, если при отсчете наткнусь на
тупиковую клетку поля, то меняем направление отсчета, и отсчитываем дальше по незаполненным клеткам.
Первая буква слова, идет на вторую позицию, вторую букву вставляем на две позиции дальше, относительно незаполненных позиции. Последняя
буква попадает, в оставшуюся пустой клетку.
То что получилось в поле и есть шифрованное слово.

Пример заполнения для слова из 3-x букв, цифра показывает номер буквы в слове по порядку:
123 → 213

Пример заполнения для слова из 4-х букв:
1234 → 3142

Пример перестановки для слова из 8 букв:
12345678  →  61825374

После чего каждое слово зашифруйте алгоритмом  шифрования Цезаря. С шагом равному номеру в алфавите первой буквы в слове получившемся в
результате перестановки.
Для кодирования шифром цезаря, каждая буква кодируемого слова заменяется на букву получившеюся в результате смещения на n заданных позиции
вправо от первоначальной буквы, относительно алфавита.
Если смещение зашифрованной буквы больше, чем количество символов в алфавите, смещение продолжается с нулевой позиции
Пример шифрования, при сдвиге равном 3-м.

Слово "something" после перестановки станет "nshoimteg" и мы букву "n" сдвигаем на его позицию - 14 и "n" станет "b" получается bgvcwahsu
Регистр мы сохраняем во время всех манипуляций

Входные данные: кодируемая строка

Пример входных данных:

When you delete something, you are making a choice to destroy it. To never see it again.

Пример выходных данных:

jBsm dnj jijjyq bgvcwahsu, dnj jsw ntphur b jhnmht di jvqwlkg nc. dI jfnww jxj nc boouw.
 */

import java.util.*;

public class _13Encryption {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); //System.in is a standard input stream

        while (sc.hasNextLine()) { // get the input
            String line = sc.nextLine();
            StringTokenizer stringTokenizer = new StringTokenizer(line, "[ :;!?,.-]", true); //allows to save delimiters
            StringBuilder stringBuilderForResult = new StringBuilder();
            while (stringTokenizer.hasMoreElements()) {
                String token = (String) stringTokenizer.nextElement();
                if (token == null || token.isEmpty()) {
                    break;
                } else if (Character.isAlphabetic(token.charAt(0))) {  //work with words
                    token = mixLetters(token);  //first iteration
                    token = shiftLetters(token);  //second iteration
                }
                stringBuilderForResult.append(token);
            }
            System.out.println(stringBuilderForResult);
            break;
        }
    }

    private static String mixLetters(String s) {
        char[] tempBoxes = new char[s.length()];
        int step = 1;
        int letterNumber = 0;
        while (letterNumber < s.length() - 1) {  //at this cycle we use letters from string without last, because last letter use last once  cell
            for (int j = 1; j < tempBoxes.length; j++) { //at the natural order
                if (tempBoxes[j] == 0) {
                    if (step > 1) {
                        step = 1;
                    } else {
                        tempBoxes[j] = s.charAt(letterNumber++);
                        step = 2;
                    }
                }
            }

            for (int j = tempBoxes.length - 2; j >= 0; j--) { //at the reverse order
                if (tempBoxes[j] == 0) {
                    if (step > 1) {
                        step = 1;
                    } else {
                        tempBoxes[j] = s.charAt(letterNumber++);
                        step = 2;
                    }
                }
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < tempBoxes.length; i++) {
            if (!Character.isLetterOrDigit(tempBoxes[i])) {
                tempBoxes[i] = s.charAt(s.length() - 1); //last letter of word
            }
            stringBuilder.append(tempBoxes[i]);
        }
        return stringBuilder.toString();
    }

    private static String shiftLetters(String s) {

        Map<Character, Character> encryptLetters = new HashMap<>();
        char firstLetter = s.charAt(0);
        int shiftPosition;

        if (Character.isLowerCase(firstLetter)) {
            shiftPosition = firstLetter - 'a' + 1;
        } else {
            shiftPosition = firstLetter - 'A' + 1;
        }
        int lettersBeforeEnd = 26 - shiftPosition;

        for (char i = 'a'; i <= 'z'; i++) {
            if (i - 'a' < lettersBeforeEnd) {
                encryptLetters.put(i, (char) (i + shiftPosition));
            } else {
                encryptLetters.put(i, (char) (i - lettersBeforeEnd));
            }
        }
        for (char i = 'A'; i <= 'Z'; i++) {
            if (i - 'A' < lettersBeforeEnd) {
                encryptLetters.put(i, (char) (i + shiftPosition));
            } else {
                encryptLetters.put(i, (char) (i - lettersBeforeEnd));
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            stringBuilder.append(encryptLetters.get(s.charAt(i)));
        }
        return stringBuilder.toString();
    }
}