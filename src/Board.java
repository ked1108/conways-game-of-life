import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Board {
    int size;
    boolean[][] board;

    public Board(int n){
        this.board = new boolean[n][n];
        this.size = n;
//        randomise(n/3, (2*n)/3, n/3, (2*n)/3);
        randomise();
    }

    public Board(boolean[][] board){
        this.board = board;
        this.size = board.length;
    }

     private void display(){
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                if(board[i][j]){
                    System.out.print("* ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }

    public void updateState(){
        boolean temp[][] = new boolean[this.size][this.size];
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                int surrounding = checkSurroundingCells(i, j);
                // RULE 1: IF LESS THAN TWO NEIGHBOURS THEN THE CELL DIES
                if(board[i][j] && surrounding< 2)  temp[i][j] = false;

                // RULE 2: IF MORE THAN 3 NEIGHBOURS THEN THE CELL DIES
                else if(board[i][j] && surrounding > 3) temp[i][j] = false;

                // RULE 3: IF 2 OR 3 NEIGHBOURS THEN THE CELL LIVES ON
                else if(board[i][j] && (surrounding == 2 || surrounding == 3)) temp[i][j] = true;

                // RULE 4: IF CELL IS DEAD AND HAS 3 NEIGHBOUR IT REVIVES
                else if(!board[i][j] && surrounding == 3) temp[i][j] = true;
            }
        }
        for(int i = 0; i < this.size; i++){
            System.arraycopy(temp[i], 0, board[i], 0, this.size);
        }
    }

    private void randomise(){
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = (int) (Math.random() * 10 + 1) % 2 == 0;
            }
        }
    }

    public void randomise(int r1, int r2, int c1, int c2){
        for (int i = r1; i < r2; i++) {
            for (int j = c1; j < c2; j++) {
                board[i][j] = (int) (Math.random() * 10 ) % 2 == 0;
            }
        }
    }

    public void run(){
//        Scanner in = new Scanner(System.in);
        for(int i = 0; i < 10; i++) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            display();
            updateState();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            for(int j = 0; j < this.size; j++) System.out.print("-");
            System.out.println();
        }
    }


    private int checkSurroundingCells(int i, int j){
        int count = 0;
        for(int r = i - 1; r <= i + 1; r++){
            for(int c = j - 1; c <= j + 1; c++){
                if(r < 0 || c < 0 || r >= size || c >= size){
                    continue;
                } else {
                    count += (board[r][c]) ? 1 : 0;
                }
            }
        }

        return count;
    }

    public static void main(String[] args) {

        int board[][] = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        };
        boolean[][] b = new boolean[11][11];
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                b[i][j] = board[i][j] == 1;
            }
        }
        Board bd = new Board(20);
        bd.run();
    }
}
