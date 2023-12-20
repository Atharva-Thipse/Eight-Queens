import java.util.*;
class Board{
    // Create 2-D Array of 0s and 1s where,
    // 0 represents queen is not present
    // 1 represents queen is present
    int chessb[][]=new int[8][8];

    // Summary: checks diagonal from top-left to bottom-right
    // Returns: True or False
    //  True: if queen is not present in diagonal
    //  False: if queen is present in diagonal
    private boolean checkDiagonal1(int row,int col){
        int min= Math.min(row, col);
        int startr=row-min;
        int startc=col-min;
        for(;startc < 7 && startr < 7; startr++, startc++){
            if (startr==row) // checking either of startr or startc is enough
                continue;
                
            if (chessb[startr][startc]==1)
                return false;
        }
        return true;
    }


    // Summary: checks diagonal from bottom-left to top right
    // Returns: True or False
    //  True: if queen is not present in diagonal
    //  False: if queen is present in diagonal
    private boolean checkDiagonal2(int row,int col){
        int startc;
        int startr;
        if (col > 7 - row){
            startr=7;
            startc=col+row-7;
        }
        else{
            startr=col+row;
            startc=0;
        }
        for (;startr >= 0 && startc <= 7;startr--,startc++){
            if (startr==row) // checking either of startr or startc is enough
                continue;
            
            if (chessb[startr][startc]==1)
                return false;
        }
        return true;
    }
    
    // Summary: checks a column
    // Returns: True or False
    //  True: if queen is not present in column
    //  False: if queen is present in column
    private boolean checkCol(int row,int col){
        int startc=col;
        for(int startr=0;startr<8;startr++){
            if(startr==row)
                continue;
            if (chessb[startr][startc]==1)
                return false;
        }
        return true;
    }

    private void RemoveQueen (int row, int col) {
        chessb[row][col]=0;
    }

    private void PlaceQueen (int row, int col) {
        chessb[row][col]=1;
    }

    // Check if the queen is already placed in the row.
    // If not, start searching from beginning of the row
    // If yes, the search should start after that position
    // Also, Queen's current position should be cleared
    public boolean placeInRow(int row){
        // Get Queen's current position in the row
        int currentPos=getQueenPos(row);

        // If the Queen was placed in the row, clear it
        if(currentPos != -1)
            RemoveQueen (row, currentPos);

        // Start searching for column after the current one
        for(int col=currentPos+1; col<8; col++){
            // For each column check if the Queen can be placed in that col
            if(checkDiagonal1(row, col) && checkDiagonal2(row, col) && checkCol(row, col)){
                PlaceQueen (row, col);
                System.out.printf("Row : %d, Col %d\n", row, col);
                return true;
            }
        }

        System.out.printf("Could not place Queen in Row %d \n",row);
        return false;
    }

    // Find Queen in a Row
    private int getQueenPos(int row){
        for (int col=0;col<8;col++){
            if (chessb[row][col]==1)
                return col;
        }
        return -1;
    }
    
    public void drawBoard(){
        // Draws Chess Board
        for(int row=0;row<8;row++){
            System.out.printf("Row: %d \tCol: %d\n", row, getQueenPos(row));
        }
        for (int row=0; row<8; row++){
            for (int col=0; col<8; col++){
                if ((row + col)%2==0)
                    System.out.print ("#######");
                else
                    System.out.print ("       ");
            }
            System.out.print ("\n");
            for (int col=0; col<8; col++){
                if (chessb[row][col] == 1){
                    if ((row + col)%2==0)
                        System.out.print("## Q ##");
                    else 
                        System.out.print("   Q   ");
                }
                else
                    if ((row + col)%2==0)
                        System.out.print("#######");
                    else 
                        System.out.print("       ");
            }
            System.out.print ("\n");
            for (int col=0; col<8; col++){
                if ((row + col)%2==0)
                    System.out.print ("#######");
                else
                    System.out.print ("       ");
            }
            System.out.print ("\n");
        }
    }
}

// Place 8 Queens in such a way that they can't attack each other
public class EightQueens {
    // Take valid input for column from user to place queen in 1st row
    private static int readPositionInFirstRow(){
        //Ask user to place Queen in 1st row
        Scanner input=new Scanner(System.in);
        System.out.print("Enter column number to place Queen in 1st row (0-7): ");

        // Loop while the user inputs a valid number (0..7)
        boolean invalidInput;
        int col;
        do{
            col=input.nextInt();
            invalidInput = (col < 0 || col > 7);
        } while (invalidInput);

        return col;
    }
    public static void main(String args[]){
        
        //Create board
        Board board = new Board();

        // Places Queen in 1st row and given column
        int col = readPositionInFirstRow();
        board.chessb[0][col]=1;
        
        // For each queen, Qi, place the queen in ith row from 2nd row onwards
        for (int queen=1;queen<8;queen++){
            boolean placed = board.placeInRow(queen);
            while (!placed){
                queen--;
                System.out.printf("Repositioning Queen at Row %d\n",queen);
                placed=board.placeInRow(queen);
            }
        }

        // Draws board
        board.drawBoard();
    }
}