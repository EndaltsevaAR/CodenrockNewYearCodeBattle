package com.company;

import java.util.*;
import java.util.stream.Collectors;

public class _15Nanorramm {
    private static final String FULL_CElL = "X   ";   //TEMP!!!
    private static final String GAP_CElL = ".   ";    //TEMP!!!
    private static String[][] picture;   //creating empty array
    private static final List<List<Integer>> numberCluesLinesForColumns = new ArrayList<>();     //horizontal clues for columns from input
    private static final List<List<Integer>> numberCluesLinesForRows = new ArrayList<>();        //vertical clues for columns from input
    private static final List<List<Boolean>> statusCluesLinesForColumns = new ArrayList<>();     //status from each clue: totally used for painting cells or not
    private static final List<List<Boolean>> statusCluesLinesForRows = new ArrayList<>();        //status from each clue: totally used for painting cells or not
    private static final List<Set<Integer>> fulledCellsByColumns = new ArrayList<>();            //numbers of full cells at each column
    private static final List<Set<Integer>> fulledCellsByRows = new ArrayList<>();               //numbers of full cells at each row
    private static final List<Set<Integer>> gapCellsByColumns = new ArrayList<>();             //numbers of empty cells at each column
    private static final List<Set<Integer>> gapCellsByRows = new ArrayList<>();                //numbers of empty cells at each row


    public static void main(String[] args) {
        inputFromUser();
        picture = new String[numberCluesLinesForRows.size()][numberCluesLinesForColumns.size()];
        initListsByEmptyCollections();   //initialization of fulledCellsByColumns, fulledCellsByRows, gapCellsByColumns, gapCellsByRows, statusCluesLinesForColumns, statusCluesLinesForRows by Empty Collections

        //     while (!isSolved(picture)) {
        //this is pre-checks of clues
        usingHorizontalClues();              //pass through horizontal clues
        usingVerticalClues();                  //pass through vertical clues
        checkFullUsedHorizontalClues();      //check for fully used hints from horizontal clues
        checkFullUsedVerticalClues();          //check for fully used hints from vertical clues

        checkUpAndDownBorders();
        //       checkVerticalBorders();

/*
Этапы проверок:
- проверить подсказки, проставить пробелы
- по списку
внести в проверку пустые подсказки
 */

        for (int i = 0; i < picture.length; i++) {  //test
            for (int j = 0; j < picture[0].length; j++) {
                System.out.print(picture[i][j] + " ");
            }
            System.out.println("");
        }
        //   }
    }


    private static boolean isSolved() {   //проверка, решен ли кроссворд
        for (String[] strings : picture) {
            for (int j = 0; j < picture[0].length; j++) {
                if (strings[j] == null) return false;
            }
        }
        return true;
    }

    private static void inputFromUser() {
        Scanner sc = new Scanner(System.in); //System.in is a standard input stream
        String line = "";
        int countSpaces = 0;
        boolean changeList = false;

        while (sc.hasNextLine()) {   //convert input data of clues to lists
            line = sc.nextLine();
            if (line == null) {
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
                    numberCluesLinesForColumns.add(lineNumbers);

                } else {
                    numberCluesLinesForRows.add(lineNumbers);
                }
                countSpaces = 0;
            }
        }

    }

    private static void initListsByEmptyCollections() {
        for (int i = 0; i < numberCluesLinesForColumns.size(); i++) { //initialization by empty set
            fulledCellsByColumns.add(new TreeSet<>());            //init fulledCellsByColumns
            gapCellsByColumns.add(new TreeSet<>());               //init gapCellsByColumns
            List<Boolean> statusesOfColumn = new ArrayList<>();   //init statusCluesLinesForColumns
            for (Integer cell : numberCluesLinesForColumns.get(i)) { //number of status equal number of clue at the column
                statusesOfColumn.add(false);
            }
            statusCluesLinesForColumns.add(statusesOfColumn);

        }

        for (int i = 0; i < numberCluesLinesForRows.size(); i++) {
            fulledCellsByRows.add(new TreeSet<>());               //init fulledCellsByRows
            gapCellsByRows.add(new TreeSet<>());                  //init gapCellsByRows
            List<Boolean> statusesOfRow = new ArrayList<>();   //init statusCluesLinesForColumns
            for (Integer cell : numberCluesLinesForRows.get(i)) {//number of status equal number of clue at the row
                statusesOfRow.add(false);
            }
            statusCluesLinesForRows.add(statusesOfRow);
        }
    }

    private static void usingHorizontalClues() { //check columns
        for (int i = 0; i < picture[0].length; i++) {  //moving along the x
            String[] column = getColumn(i);
            boolean isRow = false;
            clueUtilization(numberCluesLinesForColumns, column, isRow, i); //the algorithm for checking by columns and by rows is identical, the algorithm is deduced into a separate method
        }
    }

    private static void usingVerticalClues() { //check rows
        for (int i = 0; i < picture.length; i++) {    //moving along the y
            String[] row = picture[i];                     //initialization of each row
            boolean isRow = true;
            clueUtilization(numberCluesLinesForRows, row, isRow, i);   //the algorithm for checking by columns and by rows is identical, the algorithm is deduced into a separate method
        }
    }

    private static String[] getColumn(int i) {
        String[] column = new String[picture.length];   //initialization of each column
        for (int j = 0; j < picture.length; j++) {
            column[j] = picture[j][i];
        }
        return column;
    }

    private static void clueUtilization(List<List<Integer>> listsOfClues, String[] line, boolean isRow, int numberOfColumnOrRow) {
        List<Integer> clues = listsOfClues.get(numberOfColumnOrRow);       //clues for this row or column
        int numberFromClue = findNumberFromClue(clues, 0, clues.size() - 1);  //result sum from clues

        int startGaps = startGaps(numberOfColumnOrRow, isRow);         //start spaces
        int endGaps = endGaps(numberOfColumnOrRow, isRow);             //end spaces

        if (numberFromClue > (line.length - startGaps - endGaps) / 2 && clues.size() == 1) {   //just 1 clue to this column/row
            int start = line.length - numberFromClue - endGaps;  //usage in calculations of "empty" boundaries
            int end = numberFromClue + startGaps;                //usage in calculations of "empty" boundaries
            paintCell(numberOfColumnOrRow, isRow, start, end);

        } else if (clues.size() > 1) {                              //more than 1 clue to this column/row
            for (int j = 0; j < clues.size(); j++) {                //painting cells according to the clues
                int start = line.length - findNumberFromClue(clues, j, clues.size() - 1) - endGaps; //usage in calculations of "empty" boundaries
                int end = findNumberFromClue(clues, 0, j) + startGaps; //usage in calculations of "empty" boundaries
                paintCell(numberOfColumnOrRow, isRow, start, end);
            }

            if (numberFromClue == line.length - startGaps - endGaps) {  //empties between clues. This is possible only clues more than 1
                for (int j = 0; j < clues.size() - 1; j++) {        //it is no space after last clue
                    int index = findNumberFromClue(clues, 0, j) + startGaps;
                    if (isRow) {
                        picture[numberOfColumnOrRow][index] = GAP_CElL;                //put a gap in the solution picture
                        gapCellsByColumns.get(numberOfColumnOrRow).add(index);         //put at the gap's column's list index by row
                        gapCellsByRows.get(index).add(numberOfColumnOrRow);

                    } else {
                        picture[index][numberOfColumnOrRow] = GAP_CElL;
                        gapCellsByColumns.get(index).add(numberOfColumnOrRow);
                        gapCellsByRows.get(numberOfColumnOrRow).add(index);
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

    private static int startGaps(int numberOfColumnOrRow, boolean isRow) {      // клетки с начала, которые не могут быть окрашены
        Set<Integer> gaps;
        int countOfStartGaps = 0;
        if (isRow) {
            gaps = gapCellsByRows.get(numberOfColumnOrRow);    //for columns
        } else {
            gaps = gapCellsByColumns.get(numberOfColumnOrRow); //for rows
        }

        if (gaps == null || gaps.isEmpty())
            return 0;          //if this set is not used yet, it is no gaps at the start of the column/row
        while (gaps.contains(countOfStartGaps++))
            ;             //if gaps not contain number bigger by 1 from previous it means that next cell at the picture is full or not defined
        return --countOfStartGaps;                             //it is necessary to zero the last increment of countOfStartGaps
    }

    private static int endGaps(int numberOfColumnOrRow, boolean isRow) {  // клетки с конца, которые не могут быть окрашены
        Set<Integer> gaps;
        int countOfStartGaps = 0;
        int endOfLine;
        if (isRow) {
            gaps = gapCellsByRows.get(numberOfColumnOrRow);    //for columns
            endOfLine = picture[0].length - 1;                 //length starts from 1, not 0
        } else {
            gaps = gapCellsByColumns.get(numberOfColumnOrRow); //for rows
            endOfLine = picture.length - 1;                     //length starts from 1, not 0
        }
        if (gaps == null || gaps.isEmpty())
            return 0;           //if this set is not used yet, it is no gaps at the start of the column/row
        while (gaps.contains(endOfLine--)) {                    //checking from end to start
            countOfStartGaps++;
        }
        return countOfStartGaps;
    }

    private static void paintCell(int i, boolean isRow, int start, int end) {
        for (int j = start; j < end; j++) {
            if (isRow) {
                picture[i][j] = FULL_CElL;           //if row is checked
                fulledCellsByColumns.get(i).add(j); //put a full in the full's lists
                fulledCellsByRows.get(j).add(i);
            } else {
                picture[j][i] = FULL_CElL;           //if column is checked
                fulledCellsByColumns.get(j).add(i);
                fulledCellsByRows.get(i).add(j);
            }
        }
    }

    private static void checkFullUsedHorizontalClues() {
        for (int i = 0; i < picture[0].length; i++) {  //moving along the x
            String[] column = getColumn(i);
            boolean isRow = false;
            int sumOfFullCells = 0;
            for (Integer integer : fulledCellsByColumns.get(i)) {
                sumOfFullCells += integer;
            }
            algorithmOfCheckFullUsedClues(column, numberCluesLinesForColumns, i, isRow, sumOfFullCells);  //algorithm for checking horizontal and vertical clues is identical
        }
    }

    private static void checkFullUsedVerticalClues() {
        for (int i = 0; i < picture.length; i++) {  //moving along the x
            String[] row = picture[i];
            boolean isRow = true;
            int sumOfFullCells = 0;
            for (Integer integer : fulledCellsByRows.get(i)) {
                sumOfFullCells += integer;
            }
            algorithmOfCheckFullUsedClues(row, numberCluesLinesForRows, i, isRow, sumOfFullCells);  //algorithm for checking horizontal and vertical clues is identical
        }
    }

    private static void algorithmOfCheckFullUsedClues(String[] line, List<List<Integer>> lines, int i, boolean isRow, int paintedCells) {
        int expectedCells = findNumberFromClue(lines.get(i), 0, lines.get(i).size() - 1);
        if (lines.get(i).size() != 1) {
            expectedCells = expectedCells - lines.get(i).size() + 1;
        }
        if (paintedCells == expectedCells) {
            for (int j = 0; j < line.length; j++) {
                if (isRow && picture[i][j] == null) {
                    picture[i][j] = GAP_CElL;
                } else if (!isRow && picture[j][i] == null) {
                    picture[j][i] = GAP_CElL;
                }
            }
        }
    }


    private static void updateListsOfFullAndEmptyCells() {
        for (int i = 0; i < picture.length; i++) {  //moving along the x
            for (int j = 0; j < picture[0].length; j++) {
                if (picture[i][j] == null) {
                } else if (picture[i][j].equals(FULL_CElL)) {
                    fulledCellsByColumns.get(j).add(i);
                    fulledCellsByRows.get(i).add(j);
                } else {
                    gapCellsByColumns.get(j).add(i);
                    gapCellsByRows.get(i).add(j);
                }
            }
        }
    }

    private static void checkUpAndDownBorders() {

    }
}
