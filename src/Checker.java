import java.util.HashSet;

/**
 * Contains methods that check that sudoku rules are met when numbers are added into puzzle
 * Arrays are put into HashSets to check for duplicates in each row,column,and box.
 * @author Alex Luongo 
 * @version 1.0 9/24/16
 */
public class Checker {	
	
    /**
     * Checks the input column to see if Sudoku rules are met
     * @param (col)column to be checked
     * @return does column meet rules(true/false)
     */
    public boolean checkColumn(int[] col){
        HashSet<Integer> set = new HashSet<Integer>();
        for(int i = 0; i < col.length;i++){
            if(!set.add(col[i])  && col[i] > 0){//Duplicate?
                return false;
            }
        }
        return true;
    }

    /**
     * Checks the input row to see if Sudoku rules are met
     * @param (row)row to be checked
     * @return does row meet rules(true/false)
     */
    public boolean checkRow(int[]row){
        HashSet<Integer> set = new HashSet<Integer>();
        for(int i = 0; i < row.length;i++){
            if(!set.add(row[i]) && row[i] > 0){//Duplicate?
                return false;
            }
        }
        return true;
    }


    /**
     * Checks the input box to make sure Sudoku rules are met
     * @param board, width and height of a box
     * @return does box meet rules(true/false)
     */
    public boolean checkBox(int[][] board, int col, int row, int rSize, int cSize){
        HashSet<Integer> set = new HashSet<Integer>();

        int rBox = row/rSize;
        int cBox = col/cSize;
        for (int r=rSize*rBox; r < rSize*rBox+rSize; r++){
            for (int c=cSize*cBox; c < cSize*cBox+cSize; c++){
                if(board[r][c] > 0 && !set.add(board[r][c])){
                    return false;
                } 
            }
        }
        return true;
    }			
}
