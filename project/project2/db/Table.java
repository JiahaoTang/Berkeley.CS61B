package db;

import edu.princeton.cs.algs4.SET;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/8/8.
 */
public class Table {
    String Name;
    ArrayList<Row> tableRows;
    Row firstRow;
    public int columnSize;
    public int rowSize;

    public Table(String name) {
        Name = name;
        rowSize = 0;
        tableRows = new ArrayList<Row>();
    }

    /**Get the columns from table.*/
    public ArrayList<Column> getColumns() {
        ArrayList<Column> tableColumns = new ArrayList<Column>();
        for(int i = 0; i < columnSize; i++) {
            Column newColumn = new Column();
            for(int j = 0; j < rowSize; j++) {
                newColumn.column.add(tableRows.get(j).row.get(i));
                newColumn.columnSize += 1;
            }
            tableColumns.add(newColumn);
        }
        return tableColumns;
    }

    /**Get the rows from table.*/
    public ArrayList<Row> getRows() {
        return tableRows;
    }

    /**Add the first row into the table.*/
    public void addFirstRow(Row firstRow) {
        rowSize = 1;
        this.firstRow = firstRow;
        tableRows.add(firstRow);
        columnSize = firstRow.rowSize;
    }
    /**Add a new row into the table.*/
    public void addRow(Row inputRow) {
        if(inputRow.rowSize != columnSize){
            System.out.println("The row which you will add is not correspond with the table.");
        }else{
            rowSize += 1;
            tableRows.add(inputRow);
        }
    }

    /**Print out the table.*/
    public void printTable() {
        for(int i = 0; i < rowSize; i++) {
            tableRows.get(i).printRow();
        }
    }

    /**Join this table with another table.*/
    public Table joinWith(Table anotherTable, String joinTableName) {
        Table joinTable;
        String[] newColumnName = collectColumnName(anotherTable);
        joinTable = new Table("joinTableName");
        Row newFirstRow = new Row(newColumnName);
        joinTable.addFirstRow(newFirstRow);
        for(int i = 1; i < this.getRows().size(); i++){
            for(int j = 1; j < anotherTable.getRows().size(); j++){
                String[] newRow = combineStringArray(this.getRows().get(i).row.toArray(new String[this.getRows().get(i).row.size()]),
                        anotherTable.getRows().get(j).row.toArray(new String[anotherTable.getRows().get(j).row.size()]));
                Row r = new Row(newRow);
                joinTable.addRow(r);
            }
        }
        if(this.haveSameColumn(anotherTable)){

            return anotherTable;
        }else{
            return joinTable;
        }
    }

    /**Combine two arrays of string*/
    public String[] combineStringArray(String[] str1, String[] str2){
        int strlen1 = str1.length;
        int strlen2 = str2.length;
        str1 = Arrays.copyOf(str1, strlen1 + strlen2);
        System.arraycopy(str2, 0, str1, strlen1, strlen2);
        return str1;
    }

    /**Collect the column names from two tables.
     * Return an array of string.*/
    public String[] collectColumnName(Table anotherTable) {
        List<String> columnName = new ArrayList<String>();
        for(Column c:this.getColumns()) {
            columnName.add(c.column.get(0));
        }
        for(Column c:anotherTable.getColumns()) {
            columnName.add(c.column.get(0));
        }
        return columnName.toArray(new String[columnName.size()]);

    }

    /**Determine whether two tables have the same column.*/
    public boolean haveSameColumn(Table anotherTable){
        int columnNumber = this.getColumns().size() + anotherTable.getColumns().size();

        SET<String> columnName = new SET<String>();
        for(Column c:this.getColumns()) {
            columnName.add(c.column.get(0));
        }
        for(Column c:anotherTable.getColumns()) {
            columnName.add(c.column.get(0));
        }
        return columnNumber > columnName.size();
    }

}
