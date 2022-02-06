package com.company;

/*
Description:
Взлом двери

Дарлин взламывает тумбочку в квартире Эллиота.
Замочна скважина устроена из барьеров, которые не позволяют сдвинуть замок, эти барьеры соответствуют прорезям на ключе.
Когда ключ поворачивается, он поворачивает то пространство где нет барьеров.
Дарлин замерила расстояние от входа в замочную скважину, до начала каждого барьера, они равны целым числам.
Чтобы взломать замок, достаточно вставить в каждый промежуток между барьерами,а так же до первого барьера и после последнего палочки диаметром 1 и
повернуть их одновременно.
Расстояние между барьерами во входных данных не может быть меньше чем 1. Каждый барьер толщиной 1.
Барьеров может быть от 1 до 3.
Ваша задача вывести модель самодельного ключа Дарлин. Где каждая палочка будет надета на основу, равную длине замка, каждая палочка высотой 3,
в каждом промежутке между барьерами каждая палочка будет ближе к правой стороне.
Для примера модель ключа:

X0X00X0X
X0X00X0X
X0X00X0X
XXXXXXXX

Входные данные: расстояния от начала замочной скважины, до каждого барьера, и общая длина замочной скважины
Пример выходных данных:

1,3,6,8

Выходные данные: нарисованная модель ключа Дарлин. Где X - ключ, 0 - пустое пространство.
Пример выходных данных:

X0X00X0X
X0X00X0X
X0X00X0X
XXXXXXXX

 */

import java.util.Scanner;
import java.util.*;

public class _3BreakingTheDoor {
    public static void main(String[] args) throws java.lang.Exception {
        Scanner sc = new Scanner(System.in); //System.in is a standard input stream
        while (sc.hasNextLine()) { // get the input
            String line = sc.nextLine();
            String[] inputs = line.split(",");
            char[] firstRow = new char[Integer.parseInt(inputs[inputs.length - 1])];
            for (int i = 0; i < inputs.length - 1; i++) {
                firstRow[Integer.parseInt(inputs[i]) - 1] = 'X';
            }
            for (int i = 0; i < firstRow.length; i++) {
                if (firstRow[i] != 'X') {
                    firstRow[i] = '0';
                }
            }
            firstRow[firstRow.length - 1] = 'X';
            char[] lastRow = new char[firstRow.length];
            Arrays.fill(lastRow, 'X');
            for (int i = 0; i < 3; i++) {
                System.out.println(firstRow);
            }
            System.out.println(lastRow);
        }
    }
}