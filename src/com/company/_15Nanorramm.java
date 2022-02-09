package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class _15Nanorramm {
    private static final String FULL_CElL = "X   ";   //TEMP!!!
    private static final String GAP_CElL = ".   ";    //TEMP!!!

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); //System.in is a standard input stream
        String line = "";
        int countSpaces = 0;
        boolean changeList = false;
        List<List<Integer>> horizLines = new ArrayList<>();      //horizontal clues for columns
        List<List<Integer>> vertLines = new ArrayList<>();       //vertical clues for rows

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
        usingHorizonClues(picture, horizLines);       //pass through horizontal clues
        usingVertClues(picture, vertLines);         //pass through vertical clues
/*
Этапы проверок:
- проверить подсказки, проставить пробелы
- по списку

 */


        for (int i = 0; i < picture[0].length; i++) {  //test
            for (int j = 0; j < picture[0].length; j++) {
                System.out.print(picture[i][j] + " ");
            }
            System.out.println("");
        }
        //   }
    }

    private static void usingHorizonClues(String[][] picture, List<List<Integer>> horizLines) { //check columns
        for (int i = 0; i < picture[0].length; i++) {  //moving along the x
            String[] column = new String[picture.length];   //initialization of each column
            for (int j = 0; j < picture.length; j++) {
                column[j] = picture[j][i];
            }
            boolean isRow = false;
            clueUtilization(horizLines, column, isRow, picture, i); //the algorithm for checking by columns and by rows is identical, the algorithm is deduced into a separate method
        }
    }

    private static void usingVertClues(String[][] picture, List<List<Integer>> vertLines) { //check rows
        for (int i = 0; i < picture.length; i++) {    //moving along the y
            String[] row = picture[i];                     //initialization of each row
            boolean isRow = true;
            clueUtilization(vertLines, row, isRow, picture, i);   //the algorithm for checking by columns and by rows is identical, the algorithm is deduced into a separate method
        }
    }

    private static void clueUtilization(List<List<Integer>> lines, String[] line, boolean isRow, String[][] picture, int i) {
        List<Integer> clues = lines.get(i);       //clues for this row or column
        int numberFromClue = findNumberFromClue(clues, 0, clues.size()-1);  //result sum from clues

        int startEmpties = startEmpties(i, picture, line, isRow);         //start spaces
        int endEmpties = endEmpties(i, picture, line, isRow);             //end spaces

        if (numberFromClue > (line.length - startEmpties - endEmpties) / 2 && clues.size() == 1) {   //just 1 clue to this column/row
            int start = line.length - numberFromClue - endEmpties;  //usage in calculations of "empty" boundaries
            int end = numberFromClue + startEmpties;                //usage in calculations of "empty" boundaries
            paintCell(picture, i, isRow, start, end);

        } else if (clues.size() > 1) {                              //more than 1 clue to this column/row
            for (int j = 0; j < clues.size(); j++) {                //painting cells according to the clues
                int start = line.length - findNumberFromClue(clues, j, clues.size()-1) - endEmpties; //usage in calculations of "empty" boundaries
                int end = findNumberFromClue(clues, 0, j) + startEmpties; //usage in calculations of "empty" boundaries
                paintCell(picture, i, isRow, start, end);
            }

            if (numberFromClue == line.length - startEmpties - endEmpties) {  //empties between clues. This is possible only clues more than 1
                for (int j = 0; j < clues.size() - 1; j++) {        //it is no space after last clue
                    int index = findNumberFromClue(clues, 0, j) + startEmpties;
                    if (isRow) {
                        picture[i][index] = GAP_CElL;
                    } else {
                        picture[index][i] = GAP_CElL;
                    }
                }
            }
        }
    }

    private static int findNumberFromClue(List<Integer> clues, int start, int end) {
        int numberFromClue = 0;
        if (clues.size() > 1) {
            for (int i = start; i <= end; i++) {
                numberFromClue += clues.get(i) + 1;
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
                if (picture[numberOfColumnOrRow][j] == null || picture[numberOfColumnOrRow][j].equals(FULL_CElL)) //if row is checked
                    return j;
            } else {
                if (picture[j][numberOfColumnOrRow] == null || picture[j][numberOfColumnOrRow].equals(FULL_CElL))  //if column is checked
                    return j;
            }
        }
        return line.length;
    }

    private static int endEmpties(int numberOfLine, String[][] picture, String[] line, boolean isRow) {  // клетки с конца, которые не могут быть окрашены
        for (int j = line.length - 1; j >= 0; j--) {
            if (isRow) {
                if (picture[numberOfLine][j] == null || picture[numberOfLine][j].equals(FULL_CElL))  //if row is checked
                    return line.length - j - 1;
            } else {
                if (picture[j][numberOfLine] == null || picture[j][numberOfLine].equals(FULL_CElL))  //if column is checked
                    return line.length - j - 1;
            }
        }
        return line.length;
    }

    private static void paintCell(String[][] picture, int i, boolean isRow, int start, int end) {
        for (int j = start; j < end; j++) {
            if (isRow) {
                picture[i][j] = FULL_CElL;           //if row is checked
            } else {
                picture[j][i] = FULL_CElL;           //if column is checked
            }
        }

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
