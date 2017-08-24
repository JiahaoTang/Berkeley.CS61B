/**
 * Created by Administrator on 2017/8/24.
 */
public class Board {
    public static final int SIZE = 3; // Tic-Tac-Toe Boards are always 3x3
    private Piece[][] pieces;
    private boolean isXTurn;

    @Override
    public boolean equals(Object o) {
        Board otherBoard = (Board) o;
        for(int i = 0;i < SIZE; i ++) {
            for (int j = 0;j < SIZE;j ++){
                if(pieces[i][j].equals(otherBoard.pieces[i][j])) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int[] Code = new int[3];
        for(int i = 0;i < SIZE; i ++) {
            Code[i] = 0;
            for (int j = 0;j < SIZE;j ++){
                Code[i] = Code[i] * 2 + pieces[i][j].hashCode();
            }
        }
        int codeNum = Code[0] * 100 + Code[1] * 10 + Code[2];
        return codeNum;
    }

    public static void main(String[] args) {
        Board board_9 = new Board();
        board_9.pieces = new Piece[SIZE][SIZE];
        Piece x = new Piece();
        x.setX();
        Piece o = new Piece();
        o.setO();

        board_9.pieces[0][0] = o;
        board_9.pieces[0][1] = o;
        board_9.pieces[0][2] = x;
        board_9.pieces[1][0] = o;
        board_9.pieces[1][1] = x;
        board_9.pieces[1][2] = o;
        board_9.pieces[2][0] = x;
        board_9.pieces[2][1] = o;
        board_9.pieces[2][2] = o;

        System.out.println(board_9.hashCode());
    }
}
