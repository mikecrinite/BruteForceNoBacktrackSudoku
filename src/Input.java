import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * The input class defines how the program interprets appropriate files
 * The first two line of the file are read to interpret the amount of spaces in
 * one box on the grid
 * The rest of the lines define the input of the grid, itself.
 * These values are to be kept in an array of arrays, where each subarray corresponds
 * to a column in the sudoku grid
 * @author Jacob Caggese
 * @version 9/24/2016
 *
 */
public class Input {

    private File file;
    private int w; // the number of columns in a box
    private int h; // the number of rows in a box
    private int[][] grid; // the 2-D array that house the sudoku grid

    /**
     * Constructor for Input class.
     * Reads a file and creates a 2-D array to house sudoku data
     * 
     * @param f The file to read
     */
    public Input(File f) {
        file = f;
        try {
            //inputFile();
            readFile();
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * inputFile is designed to read a file location from the command line
     * if the user inputs a file that does not exist or cannot be read, the
     * method asks for another file.
     * @throws IOException
     */
    private void inputFile() throws IOException{
        boolean readable;
        do {
            BufferedReader in = new BufferedReader(new InputStreamReader (System.in));
            System.out.println("Input file location:");
            file = new File(in.readLine());
            readable = file.canRead();
            if (!readable) {
                System.out.println("File is not readable.");
            }
            in.close();
        } while (!readable);
    }

    /**
     * Reads the data in a file and transfers it into a 2D array
     * @throws IOException
     */
    private void readFile() throws IOException {
        Scanner in = new Scanner(file);
        boolean flag = false;
        do { //check for comment lines
            String val = in.next();
            if (val.equals("c")) {
                in.nextLine(); 
                flag = true;
            }
            else {
                w = Integer.parseInt(val);
                flag = false;
            }
        }while (flag);
        do { //check for comment lines
            String val = in.next();
            if (val.equals("c")) {
                in.nextLine(); 
                flag = true;
            }
            else {
                h = Integer.parseInt(val);
                flag = false;
            }
        }while (flag);
        int boxSize = w * h;
        grid = new int[boxSize][boxSize];
        for (int i = 0; i < boxSize; i++) { // count rows
            do { // skip all comment sections
                String firstVal = in.next(); // first value of row
                if (firstVal.equals("c")) { 
                    in.nextLine();
                    flag = true;
                } else {
                    flag = false;
                    grid[i][0] = Integer.parseInt(firstVal.trim());
                }
            }while (flag);
            for (int j = 1; j < boxSize; j++) { // count columns
                grid[i][j] = in.nextInt();
            }
        }
        in.close();
    }

    public void test() {
        int s = w*h;
        for (int i = 0; i < s; i++) {
            for (int j = 0; j < s; j++) {
                System.out.print(grid[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    /**
     * @return w
     */
    public int getW() {
        return w;
    }

    /**
     * @return h
     */
    public int getH() {
        return h;
    } 

    /**
     * @return grid
     */
    public int[][] getGrid() {		
        return grid; 
    }
}