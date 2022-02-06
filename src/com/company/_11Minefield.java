package com.company;

/*
Description:
Минное поле

Долорес с Уильямом попали в необычное минное поле. Поле прямоугольное, поделено на меньшие клетки, мины расположены в клетках в шахматном порядке.

В пустых клетках пустое пространство.
Мины могут ранить только Долорес, но ни Уильяма.
Мины взрываются от Уильяма, но не от Долорес.
Если на мину наступит Уильям, то эта мина взорвется и взорвутся все по диагонали от нее.
В первый момент все мины целы, после чего Долорес и Уильям делают шаг, каждый в заданную им сторону, при этом Долорес может ранить, если она
окажется в клетке со взываемой миной.

Изначально Уильям и Долорес стоят на пустом пространстве. Ваша задача найти ранит ли мина Долорес.
Входные данные: минное поле, вместе с расположением Долорес и Уйльяма
Шаги в одном из направлений: right, left, up, down. Сначала шаг Долорес, потом Уйльяма
Шаг делается только один
Обозначения:
0 - пустое пространство
X - изображены мины
D - местоположение Долорес
W - местоположение Уильяма
Минное поле размеров до 10 на 10

Пример входных данных:

X0X0X0X0X

0X0X0X0X0

XWX0X0X0X

0X0X0X0X0

X0X0X0X0X

0X0X0X0X0

X0X0X0X0X

0X0X0X0X0

X0X0XDX0X

right

left

Выходные данные: Yes, если ранило, No если нет.
 */

import java.util.*;
import java.util.stream.Collectors;

public class _11Minefield {
    public static void main(String[] args) throws java.lang.Exception {

        Scanner sc = new Scanner(System.in); //System.in is a standard input stream
        List<List<String>> wield = new ArrayList<>();
        List<String> moves = new ArrayList<>();
        boolean isAllDataisNotLoaded = true;
        while (sc.hasNextLine() && isAllDataisNotLoaded) {
            String line = sc.nextLine();// get the input
            if (line.isEmpty()) {
                continue;
            }
            if (Character.isLowerCase(line.charAt(0))) {
                moves.add(line);
            } else {
                wield.add(Arrays.stream(line.split("")).collect(Collectors.toList()));
            }
            if (wield.size() == wield.get(0).size() && moves.size() == 2) {
                isAllDataisNotLoaded = false;
            }
        }
        List<Integer> wCoordinates = findCoordinates("W", wield);
        List<Integer> dCoordinates = findCoordinates("D", wield);

        dCoordinates = findNewCoordinates(moves.get(0), dCoordinates, wield);
        wCoordinates = findNewCoordinates(moves.get(1), wCoordinates, wield);
        if (minningStep(dCoordinates, wCoordinates, wield)) return;
        System.out.println("No");

    }

    private static boolean minningStep(List<Integer> dCoordinates, List<Integer> wCoordinates, List<List<String>> wield) {
        boolean blow = false;
        wield = wBoomMove(wCoordinates, wield);
        String initDLocation = wield.get(dCoordinates.get(0)).get(dCoordinates.get(1));
        if (initDLocation.equals("Z")) {
            System.out.println("Yes");
            blow = true;
        }
        return blow;
    }

    private static List<Integer> findNewCoordinates(String move, List<Integer> wCoordinates, List<List<String>> wield) {
        switch (move) {
            case "right":
                wCoordinates.set(1, wCoordinates.get(1) + 1);
                break;
            case "left":
                wCoordinates.set(1, wCoordinates.get(1) - 1);
                break;
            case "up":
                wCoordinates.set(0, wCoordinates.get(0) - 1);
                break;
            case "down":
                wCoordinates.set(0, wCoordinates.get(0) + 1);
        }
        return wCoordinates;
    }

    private static List<Integer> findCoordinates(String letter, List<List<String>> wield) {
        List<Integer> coordinates = new ArrayList<>();
        for (int i = 0; i < wield.size(); i++) {
            for (int j = 0; j < wield.get(i).size(); j++) {
                if (wield.get(i).get(j).equals(letter)) {
                    coordinates.add(i);
                    coordinates.add(j);
                    return coordinates;
                }
            }
        }
        return coordinates;
    }

    private static List<List<String>> wBoomMove(List<Integer> wCoordinates, List<List<String>> wield) {
        List<Integer> tempCoordinates = new ArrayList<>(wCoordinates);
        if (!(wield.get(tempCoordinates.get(0)).get(tempCoordinates.get(1)).equals("X"))) {
            return wield;
        }
        while (tempCoordinates.get(0) >= 0 && tempCoordinates.get(1) >= 0) {
            wield.get(tempCoordinates.get(0)).set(tempCoordinates.get(1), "Z");
            tempCoordinates.set(0, tempCoordinates.get(0) - 1);
            tempCoordinates.set(1, tempCoordinates.get(1) - 1);
        }
        tempCoordinates = new ArrayList<>(wCoordinates);
        while (tempCoordinates.get(0) < wield.size() && tempCoordinates.get(1) < wield.get(tempCoordinates.get(0)).size()) {
            wield.get(tempCoordinates.get(0)).set(tempCoordinates.get(1), "Z");
            tempCoordinates.set(0, tempCoordinates.get(0) + 1);
            tempCoordinates.set(1, tempCoordinates.get(1) + 1);
        }
        tempCoordinates = new ArrayList<>(wCoordinates);
        while (tempCoordinates.get(0) < wield.size() && tempCoordinates.get(1) >= 0) {
            wield.get(tempCoordinates.get(0)).set(tempCoordinates.get(1), "Z");
            tempCoordinates.set(0, tempCoordinates.get(0) + 1);
            tempCoordinates.set(1, tempCoordinates.get(1) - 1);
        }
        tempCoordinates = new ArrayList<>(wCoordinates);
        while (tempCoordinates.get(0) >= 0 && tempCoordinates.get(1) < wield.get(tempCoordinates.get(0)).size()) {
            wield.get(tempCoordinates.get(0)).set(tempCoordinates.get(1), "Z");
            tempCoordinates.set(0, tempCoordinates.get(0) - 1);
            tempCoordinates.set(1, tempCoordinates.get(1) + 1);
        }
        return wield;
    }
}