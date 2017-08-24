/**
 * Created by Administrator on 2017/8/24.
 */
public class Piece {
    private String type; // Will be either "X" or "O".

    public void setX() {
        type = "X";
    }

    public void setO() {
        type = "O";
    }

    @Override
    public boolean equals(Object o) {
        Piece otherPiece = (Piece) o;
        return this.type.equals(otherPiece.type);
    }

    @Override
    public int hashCode() {
        if(type == "X") {
            return 1;
        }else if(type == "O") {
            return 0;
        }
        return -1;
    }
}
