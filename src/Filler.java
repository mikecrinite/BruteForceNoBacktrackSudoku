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

    Checker checker = new Checker();



    /**
     * Driver method.
     * Utilizes test methods to test a default puzzle for solutions.
     *
     * @param args Default main method arguments
     */
    public static void main(String args[]) {

        final long startTime = System.currentTimeMillis();//Take start time

        Filler filler = new Filler(Filler.testPuzzle(), 2, 3);
        filler.solve();
        filler.printAllRows();

        final long endTime = System.currentTimeMillis();  //Take end time
        System.out.println("Execution time: " + (endTime - startTime) + " ms");
    }

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

    public void solve() {
        //First, find every zero in the puzzle and store true in its place in a 2D array of booleans.

        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[i].length; j++) {
                if (puzzle[i][j] == 0) {
                    empties[i][j] = true;
                    //Second, fill every zero with a one
                    puzzle[i][j] = 1;
                }
            }
        }

        boolean solved = false;
        while (!solved) {
            //Third, increment the first empty if possible, if not increment the next empty
            outerloop:
            for (int i = 0; i < puzzle.length; i++) {         //for each row
                for (int j = 0; j < puzzle[i].length; j++) {  //for each column
                    if (empties[i][j]) {                     //if the space was an empty
                        if (canIncrement(puzzle[i][j])) {    //If you can increment it, do so
                           puzzle[i][j]++;
                        } else {                             //If not, start it over at 1, increment the next one
                            increment();
                        }

                        //Check if it worked or not
                        if(!isSolved()){
                            break outerloop;                   //Didn't work, try again.
                        }else{
                            solved = true;                     //Did work, exit while loop
                            break outerloop;
                        }
                    }
                }
            }


            //Check if that row/column/box is valid
            //If not, keep incrementing until you can no longer increment
            //increment the next empty to the left, and reset this one
        }


    }

    private boolean isSolved(){
        boolean rows;
        boolean cols;
        boolean boxs;

        for(int i = 0; i < puzzle.length; i++){
            if(!checker.checkRow(puzzle[i])){
                return false;
            }
        }

        for(int j = 0; j < puzzle.length; j++){
            if(!checker.checkRow(columnToArray(puzzle, j))){
                return false;
            }
        }

        for(int row = 0; row < puzzle.length; row+=rSize){
            for(int col = 0; col < puzzle[row].length; col+=cSize){
                if(!checker.checkBox(puzzle, col, row, rSize, cSize)){
                    return false;
                }
            }
        }

        return true;
    }

    private boolean canIncrement(int val){
        if(val < width){
            return true;
        }else{
            return false;
        }
    }

    private boolean increment(){
        //Find the first that CAN be incremented, increment it
        for(int i = 0; i < puzzle.length; i++){
            for(int j = 0; j < puzzle[i].length; j++){
                if(empties[i][j]){
                    if(canIncrement(puzzle[i][j])){
                        puzzle[i][j]++;
                        return true;
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
     * Creates a smaller fake puzzle
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