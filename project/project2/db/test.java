package db;

import edu.princeton.cs.algs4.Out;

public class test {
    public static void main(String[] args) {
        Table t1 = new Table("t1");
        t1.addFirstRow(new Row("x int","y int"));
        t1.addRow(new Row("2","5"));
        t1.addRow(new Row("8","3"));
        t1.addRow(new Row("13","7"));
        Table t4 = new Table("t4");
        t4.addFirstRow(new Row("x int","z int"));
        t4.addRow(new Row("2","4"));
        t4.addRow(new Row("8","9"));
        t4.addRow(new Row("10","0"));
        /*
        * x int, y int, z int
        * 2,5,4
        * 8,3,9*/
        Table tx = t1.joinWith(t4, "tx");
        tx.printTable();

    }
}
