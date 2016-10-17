import java.util.ArrayList;

/**
 * This class will attempt to fill a Sudoku puzzle with a solution.
 * 
 * Utilizing a recursive call to the fillBoard() method, it will try every
 * possible solution in each square. If at any point,it expends all possible 
 * values of any individual square, it will return false all the way down.
 * 
 * If it reaches the end of the board, having filled all previous spaces, it
 * will replace the field puzzle with the solved puzzle, which can then be
 * printed by calling the printAllRows() method.
 * 
 * @author Michael Crinite
 * @version 09.24.2016
 */
public class Filler {
    int[][] puzzle;             // The inputted puzzle
    boolean[][] empties;
    int width;                  // The amount of columns in the puzzle
    int height;                 // The amount of rows in the puzzle    
    int cSize;                  // The amount of columns in one block
    int rSize;                  // The amount of rows in one block

    int lastX;                  // Location of the final empty space (row)
    int lastY;                  // Location of the final empty space (column)

    Checker checker = new Checker();

    /**
     * Constructor for type SudokuFiller
     * Creates a puzzle board from a passed-in argument of type 2D array
     *
     * @param sudoku Two-dimensional array read in from a file
     */
    public Filler(int[][] sudoku, int h, int w) {
        puzzle = sudoku;
        width = sudoku[0].length; //This assumes equal length for every row
        height = sudoku.length;
        rSize = h;
        cSize = w;

        empties = new boolean[width][height]; //Initializes to false

    }

    public boolean solve() {
        //First, find every zero in the puzzle and store true in its place in a 2D array of booleans.

        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[i].length; j++) {
                if (puzzle[i][j] == 0) {
                    empties[i][j] = true;
                    lastX = i;
                    lastY = j;
                    //Second, fill every zero with a one
                    puzzle[i][j] = 1;
                }
            }
        }

        boolean solved = false;
        while (!solved) { //Technically an infinite loop, but the returns should eventually break the loop
            //Third, increment the first empty if possible, if not increment the next empty
            increment();

            //Check if it solved the entire puzzle or not
            if(isSolved()){
                System.out.println("Solution: ");
                return true;
            }else if(puzzle[lastX][lastY] == width){
                return false;
            }
        }
        return true;
    }

    /**
     * Iterates through each row, checking for validity.
     * If all are valid, then iterates through every column, checking for validity.
     * If all are valid, then iterates through every box, checking for validity.
     *
     * @return True if no row, column, or box is invalid. False if any is invalid.
     */
    private boolean isSolved(){
        //Check rows
        for(int i = 0; i < puzzle.length; i++){
            if(!checker.checkRow(puzzle[i])){
                return false;
            }
        }

        //Check columns
        for(int j = 0; j < puzzle.length; j++){
            if(!checker.checkRow(columnToArray(puzzle, j))){
                return false;
            }
        }

        //Check boxes
        for(int row = 0; row < puzzle.length; row = (row + rSize)){
            for(int col = 0; col < puzzle[row].length; col = (col + cSize)){
                if(!checker.checkBox(puzzle, col, row, rSize, cSize)){
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Checks whether a space can be incremented.
     * If the value of the space is less than the max possible value, then it can be incremented.
     * The max possible value is the same as the width, and as such, width is used in lieu of max.
     *
     * @param val Check whether this value can be incremented
     * @return True if it can be incremented, false if it cannot be incremented.
     */
    private boolean canIncrement(int val){
        if(val < width){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Finds the first possible empty space that contains a value that can be incremented.
     * If the space can be incremented, it increments the space.
     * If the space cannot be incremented, and it is the final empty space in the puzzle, this method returns
     * false.
     * If the space cannot be incremented, and it is not the final empty space in the puzzle, it resets the space to 1.
     *
     * @return True if the space is successfully incremented. False if no space can be incremented.
     */
    private boolean increment(){
        for(int i = 0; i < puzzle.length; i++){
            for(int j = 0; j < puzzle[i].length; j++){
                if(empties[i][j]){
                    if(canIncrement(puzzle[i][j])){
                        puzzle[i][j]++;
                        return true;
                    }else if(i == lastX && j == lastY) {
                        return false;
                    }else{
                        puzzle[i][j] = 1;
                    }

                }
            }
        }
        return false;
    }
    

    
    /**
     * Converts a single column in an array to a new array
     * 
     * @param col Column to create an array from
     * @return The new array, composed of the column contents from top to bottom
     */
    private int[] columnToArray(int[][] array, int col){
        int[] column = new int[height];
        for(int row = 0; row < height; row++){
            column[row] = array[row][col];
        }
        
        return column;
    }
    
    /**
     * Prints a single row to console
     * 
     * @param row The index of the row which is to be printed.
     */
    private void printRow(int row){
        for(int i : puzzle[row]){
            System.out.print(i + " ");
        }
    }
    
    /**
     * Prints the entire puzzle to the console by row using printRow
     */
    public void printAllRows(){
        for(int i = 0; i < height; i++){
            printRow(i);
            System.out.println();
        }
    }
    
    /**
     * Creates a new puzzle with blanks
     * @return A solvable 6x6 puzzle 
     */
    private static int[][] testPuzzle(){
        int[][] multi = new int[][]{
            { 1, 0, 3, 4, 0, 0 },
            { 4, 0, 6, 0, 0, 3 },
            { 2, 0, 1, 0, 6, 0 },
            { 5, 0, 4, 2, 0, 0 },
            { 3, 0, 2, 0, 4, 0 },
            { 6, 0, 5, 0, 0, 2 }};
        return multi;
    }
    
    /**
     * Creates a smaller test puzzle
     * @return A solvable 4v4 puzzle
     */
    private static int[][] testPuzzle2(){
        int[][] multi = new int[][]{
            {1, 2, 0, 0},
            {0, 4, 0, 0},
            {0, 0, 1, 0},
            {0, 0, 3, 2}};
        return multi;
    }
    
    /**
     * Creates the smallest puzzle
     * @return A solvable 2x2 puzzle
     */
     private static int[][] testPuzzle3(){
        int[][] multi = new int[][]{
            {1, 0},
            {2, 0}};
        return multi;
    }
     
    /**
     * Creates a new puzzle with blanks
     * @return A solvable 6x6 puzzle 
     */
    private static int[][] testPuzzle4(){
        int[][] multi = new int[][]{
            { 0, 0, 0, 1, 0, 5, 0, 6, 8 },
            { 0, 0, 0, 0, 0, 0, 7, 0, 1 },
            { 9, 0, 1, 0, 0, 0, 0, 3, 0 },
            { 0, 0, 7, 0, 2, 6, 0, 0, 0 },
            { 5, 0, 0, 0, 0, 0, 0, 0, 3 },
            { 0, 0, 0, 8, 7, 0, 4, 0, 0 },
            { 0, 3, 0, 0, 0, 0, 8, 0, 5 },
            { 1, 0, 5, 0, 0, 0, 0, 0, 0 },
            { 7, 9, 0, 4, 0, 1, 0, 0, 0 }};
        return multi;
    }
}