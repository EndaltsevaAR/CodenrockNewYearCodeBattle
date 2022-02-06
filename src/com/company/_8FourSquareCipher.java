package com.company;
/*
Description:
Шифр четырех квадратов

Шифр четырех квадратов использует 4 квадратных матрицы, размером зависящих от количества букв в алфавите, 5х5 в английском языке.
Чтобы добиться подходящего размера «I» и «J» объединяются в одной клетке.
Алгоритм:
Из заданной строки нужно убрать пробелы и поделить буквы попарно:
IW AN TE DT OS AV ET HE WO RL D
Берем каждую пару букв, и находим и в левом верхнем первую букву пары и правом нижнем вторую
Далее для каждой пары букв надо получить такую пару, состоящую из букв из правого верхнего и левого нижнего квадратов, стоящие на пересечении
путей, при движении по вертикали и по горизонтали от начальных букв. Первая буква берется из правого верхнего квадрата, вторая буква из левого
нижнего.
Полученную пару букв помещают в шифр в конец кодируемой строки.
Повторяем алгоритм для всего сообщения. Если сообщение состоит из нечетного количества букв последнюю букву в кодируемом сообщении оставляем как есть.
Регистр кодируемого сообщения верхний во всех входных и выходных данных.

Входные данные: Вам будут даны квадратные матрицы для кодирования в последовательности: левый верхний квадрат, правый верхний, нижний левый,
нижний правый.
После в одну строку кодируемая фраза.
Кодируемая фраза состоит из не более чем 100 символов.
Для облегчения кодирования буква “J” отсутствует в правом верхнем и , левом нижнем квадратах.
Пример входных данных:

A,B,C,D,E

F,G,H,I/J,K

L,M,N,O,P

Q,R,S,T,U

V,W,X,Y,Z



C,R,I,P,T

O,G,A,F,B

D,E,H,K,L

M,N,Q,S,U

V,W,X,Y,Z



S,E,G,U,R

T,A,B,C,D

F,H,I,K,L

M,N,O,P,Q

V,W,X,Y,Z



A,B,C,D,E

F,G,H,I/J,K

L,M,N,O,P

Q,R,S,T,U

V,W,X,Y,Z

I WANTED TO SAVE THE WORLD
Пример выходных данных:
GYIFUUPPHPCVPQBGYHMHD
 */

import java.util.Scanner;

public class _8FourSquareCipher {
    public static void main(String[] args) throws java.lang.Exception {
        Scanner sc = new Scanner(System.in); //System.in is a standard input stream
        String[][] codingSquare = new String[20][5];
        String lineForCoding = "";
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        while (i < 21 && sc.hasNextLine()) {
            String line = sc.nextLine();// get the input
            if (line.isEmpty()) {
                continue;
            }
            if (i < 20) {
                codingSquare[i] = line.split(",");
                i++;
            } else {
                lineForCoding = line.replaceAll(" ", "");
                i++;
            }
        }
        for (int j = 0; j < lineForCoding.length(); j++) {
            if (j == lineForCoding.length() - 1) {
                stringBuilder.append(lineForCoding.charAt(j));
            } else {
                int firstX = 0, firstY = 0, secondX = 0, secondY = 0;
                for (int k = 0; k < 5; k++) {
                    for (int l = 0; l < 5; l++) {
                        if (codingSquare[k][l].contains(Character.toString(lineForCoding.charAt(j)))) {
                            firstX = l;
                            firstY = k;
                            break;
                        }
                        if (firstX != 0) break;
                    }
                }
                j++;
                for (int k = 15; k < 20; k++) {
                    for (int l = 0; l < 5; l++) {
                        if (codingSquare[k][l].contains(Character.toString(lineForCoding.charAt(j)))) {
                            secondX = l;
                            secondY = k - 15;
                            break;
                        }
                        if (secondX != 0) break;
                    }
                }
                stringBuilder.append(codingSquare[firstY + 5][secondX]).
                        append(codingSquare[10 + secondY][firstX]);

            }
        }
        System.out.println(stringBuilder);
    }
}
