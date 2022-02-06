package com.company;

/*
Description:
Имя Фамилия

Эллиот хочет получить все имена и фамилии. Для этого он находит все пары слов, которые идут друг за другом, и начинаются с заглавной буквы,
причем далее в слове могут быть только русские буквы. Напишите программу способную это сделать.
Если между двумя подходящими словами стоит любой знак кроме пробела, то эту пару слов не считать Именем Фамилией.
Входные данные представлены на русском языке и исключают возможность появления трех и более слов, удовлетворяющих условию поиска, подряд.
Текст длиной не более 2000 символов
Могут встречаться повторяющиеся пробелы, в таком случае подходящая пара остается Именем и Фамилией. Имя и фамилию ищите на русском языке.
Пример входных данных:
“В качестве выкупа fsociety вынуждает Скотта Ноулза надеть маску fsociety и публично сжечь 5,9 миллиона долларов полученных от взлома. Анджела
Мосс продолжает подниматься по карьерной лестнице в E Corp, по-видимому, довольная своей новой корпоративной позицией, и, похоже, отказывается от
иска. Джоанна получает подарок на пороге - музыкальную шкатулку со спрятанным под ней телефоном, но пропускает звонок. Эллиот обнаруживает, что
действовал под влиянием Мистера Робота, когда думал, что спит. Человек по имени Брок убивает Гидеона, который ранее угрожал сообщить о
подозрительном поведении Эллиота в Олсейф ФБР и агенту Доминик ДиПьерро. Эллиот просыпается от диссоциативного состояния, разговаривая по телефону,
его приветствует на другом конце провода Тайрелл.”

Пример выходных данных: “Скотта Ноулза, Анджела Мосс, Мистера Робота, Олсейф ФБР, Доминик ДиПьерро”
 */

import java.util.Scanner;
import java.util.*;

public class _5Name {
    public static void main(String[] args) throws java.lang.Exception {
        Scanner sc = new Scanner(System.in); //System.in is a standard input stream
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            line = line.replaceAll("[\\s]+", " ").trim();
            StringBuilder stringBuilder = new StringBuilder();
            StringTokenizer stringTokenizer = new StringTokenizer(line, "[ \\t\\n\\r\\f:;!?,.-]", true);
            String firstWord = "";
            boolean circle = false;
            while (stringTokenizer.hasMoreElements()) {
                if (firstWord.length() == 0 || !circle) {
                    firstWord = (String) stringTokenizer.nextElement();
                }
                circle = false;
                if (Character.isUpperCase(firstWord.charAt(0)) && isRussian(firstWord)) { //check first word
                    String delimetr = (String) stringTokenizer.nextElement();
                    if (delimetr != null && delimetr.charAt(0) == ' ') {   // check delimetr for space
                        String secondWord = (String) stringTokenizer.nextElement();
                        if (secondWord != null) {
                            if (Character.isUpperCase(secondWord.charAt(0)) && isRussian(secondWord)) { //check second word
                                stringBuilder.append(firstWord).append(" ").append(secondWord).append(", ");
                            } else {
                                firstWord = secondWord;
                                circle = true;
                            }
                        }
                    }
                }
            }
            System.out.println(stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(",")).toString().trim());
        }
    }

    private static boolean isRussian(String word) {
        for (int i = 0; i < word.length(); i++) {
            if (!Character.UnicodeBlock.of(word.charAt(i)).equals(Character.UnicodeBlock.CYRILLIC)) {
                return false;
            }
        }
        return true;
    }
}