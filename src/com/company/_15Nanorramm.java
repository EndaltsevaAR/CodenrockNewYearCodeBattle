package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class _15Nanorramm {
    private static final String fullCeil = "X";
    private static final String gapCeil = ".";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); //System.in is a standard input stream
        String line = "";
        int countSpaces = 0;
        boolean changeList = false;
        List<List<Integer>> horizLines = new ArrayList<>();
        List<List<Integer>> vertLines = new ArrayList<>();

        while (sc.hasNextLine()) {   //convert input data of clues to lists
            line = sc.nextLine();
            if (line == null) {
                continue;
            } else if (line.isEmpty()) {
                countSpaces++;
                continue;
            } else if (line.equals("end")) {
                break; //temp for breaking while loop
            } else {
                List<Integer> lineNumbers = Arrays.stream(line.split(",")).map(Integer::parseInt).collect(Collectors.toList());
                if (countSpaces > 1) {
                    changeList = true;
                }
                if (!changeList) {
                    horizLines.add(lineNumbers);
                } else {
                    vertLines.add(lineNumbers);
                }
                countSpaces = 0;
            }
        }

        String[][] picture = new String[vertLines.size()][horizLines.size()];   //creating empty array

        //     while (!isSolved(picture)) {
        picture = usingHorizonClues(picture, horizLines);       //pass through horizontal clues
        picture = usingVertClues(picture, vertLines);         //pass through vertical clues


        for (int i = 0; i < picture[0].length; i++) {  //test
            for (int j = 0; j < picture[0].length; j++) {
                System.out.print(picture[i][j] + " ");
            }
            System.out.println("");
        }
        //   }
    }

    private static String[][] usingHorizonClues(String[][] picture, List<List<Integer>> gorizLines) { //check columns
        for (int i = 0; i < picture[0].length; i++) {  //moving along the x
            String[] column = new String[picture.length];   //initialization of each column
            for (int j = 0; j < picture.length; j++) {
                column[j] = picture[j][i];
            }
            boolean isRow = false;
            picture = clueUtilization(gorizLines, column, isRow, picture, i); //the algorithm for checking by columns and by rows is identical, the algorithm is deduced into a separate method
        }
        return picture;
    }

    private static String[][] usingVertClues(String[][] picture, List<List<Integer>> vertLines) { //check rows
        for (int i = 0; i < picture.length; i++) {    //moving along the y
            String[] row = picture[i];                     //initialization of each row
            boolean isRow = true;
            picture = clueUtilization(vertLines, row, isRow, picture, i);   //the algorithm for checking by columns and by rows is identical, the algorithm is deduced into a separate method
        }
        return picture;
    }

    private static String[][] clueUtilization(List<List<Integer>> gorizLines, String[] line, boolean isRow, String[][] picture, int i) {
        List<Integer> clues = gorizLines.get(i);       //clues for this row or column
        int numberFromClue = findNumberFromClue(clues);  //result sum from clues

        int startEmpties = startEmpties(i, picture, line, isRow);         //start spaces
        int endEmpties = endEmpties(i, picture, line, isRow);             //end spaces

        if (numberFromClue > (line.length - startEmpties - endEmpties) / 2 && clues.size() == 1) {
            int start = line.length - numberFromClue;
            int end = numberFromClue - 1;
            for (int j = start; j <= end; j++) {
                picture = paintCeil(picture, j, i, isRow);
            }
        }
        return picture;
    }

    private static int findNumberFromClue(List<Integer> clues) {
        int numberFromClue = 0;
        if (clues.size() > 1) {
            for (Integer clue : clues) {
                numberFromClue += clue + 1;
            }
            numberFromClue--;
        } else {
            numberFromClue = clues.get(0);
        }
        return numberFromClue;
    }

    private static int startEmpties(int numberOfColumnOrRow, String[][] picture, String[] line, boolean isRow) {      // клетки с начала, которые не могут быть окрашены
        for (int j = 0; j < line.length; j++) {
            if (isRow) {
                if (picture[numberOfColumnOrRow][j] == null || picture[numberOfColumnOrRow][j].equals("X")) //if row is checked
                    return j;
            } else {
                if (picture[j][numberOfColumnOrRow] == null || picture[j][numberOfColumnOrRow].equals("X"))  //if column is checked
                    return j;
            }
        }
        return line.length;
    }

    private static int endEmpties(int numberOfLine, String[][] picture, String[] line, boolean isRow) {  // клетки с конца, которые не могут быть окрашены
        for (int j = line.length - 1; j >= 0; j--) {
            if (isRow) {
                if (picture[numberOfLine][j] == null || picture[numberOfLine][j].equals("X"))  //if row is checked
                    return line.length - j - 1;
            } else {
                if (picture[j][numberOfLine] == null || picture[j][numberOfLine].equals("X"))  //if column is checked
                    return line.length - j - 1;
            }
        }
        return line.length;
    }

    private static String[][] paintCeil(String[][] picture, int j, int i, boolean isRow) {
        if (isRow) {
            picture[i][j] = "X";           //if row is checked
        } else {
            picture[j][i] = "X";           //if column is checked
        }
        return picture;
    }

    private static boolean isSolved(String[][] picture) {   //проверка, решен ли кроссворд
        for (String[] strings : picture) {
            for (int j = 0; j < picture[0].length; j++) {
                if (strings[j] == null) return false;
            }
        }
        return true;
    }
}
