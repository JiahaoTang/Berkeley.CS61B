package db;

import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/8/11.
 */
public class Column {
    ArrayList<String> column;
    int columnSize;
    /*Constuctor.*/
    public Column(String... rowMember) {
        columnSize = rowMember.length;
        column = new ArrayList<String>(columnSize);

        for(int i = 0; i < columnSize; i++) {
            column.add(rowMember[i]);
        }
    }

    public void printColumn() {
        for(int i =0; i < columnSize; i++) {
            System.out.println(column.get(i));
        }
    }
}
