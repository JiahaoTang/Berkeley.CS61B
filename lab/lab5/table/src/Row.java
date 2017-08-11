import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/8.
 */
public class Row {

    ArrayList<String> row;
    int rowSize;
    /*Constuctor.*/
    public Row(String... rowMember) {
        rowSize = rowMember.length;
        row = new ArrayList<String>(rowSize);

        for(int i = 0; i < rowSize; i++) {
            row.add(rowMember[i]);
        }
    }

    public void printRow() {
        for(int i =0; i < rowSize - 1; i++) {
            System.out.print(row.get(i) + "|");
        }
        System.out.println(row.get(rowSize - 1));
    }
}
