package db;

import edu.princeton.cs.algs4.In;
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
    public Table joinWith(Table anotherTable) {
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

        if(this.haveSameColumn(anotherTable)) {
            //Initialize a table whose rows have been filtered.
            Table FilterRowjoinTable = new Table("FilterRowJoinTable");
            FilterRowjoinTable.addFirstRow(newFirstRow);

            //Filter the specified rows.
            for(int i = 1; i < joinTable.rowSize; i++) {//i is the index of the row.
                for(int j = 0; j < joinTable.columnSize; j++) {          //CN is the duplicated Column Name.
                    for(int k = j + 1; k < joinTable.columnSize; k++) {       //cn is column names of jointable.
                        String jvalue = joinTable.getRows().get(i).row.get(j);//get the CN column's value in ith row.
                        String kvalue = joinTable.getRows().get(i).row.get(k);//get the cn column's value in ith row.
                        if(newColumnName[j]==newColumnName[k] && jvalue.equals(kvalue)) {
                            FilterRowjoinTable.addRow(joinTable.getRows().get(i));
                        }
                    }
                }
            }

            //Filter the specified columns.
            ArrayList<Integer> duplicatedNameIndex = findDuplicatedNameIndex(FilterRowjoinTable);

            //Change the values rows of the table.Integer index:duplicatedNameIndex
            for(Integer index:duplicatedNameIndex) {
                for(int i = 0; i < FilterRowjoinTable.rowSize; i++) {
                    FilterRowjoinTable.getRows().get(i).row.remove((int)index);
                    FilterRowjoinTable.getRows().get(i).rowSize -= 1;
                }
            }
            FilterRowjoinTable.columnSize -= duplicatedNameIndex.size();
            return FilterRowjoinTable;
        }else {
            return joinTable;
        }
    }

    /**Find the duplicated column names' index.*/
    public static ArrayList<Integer> findDuplicatedNameIndex(Table T) {
        ArrayList<Integer> duplicatedNameIndex = new ArrayList<Integer>();
        for(int i = 0; i < T.firstRow.row.size(); i++) {
            for(int j = i + 1; j < T.firstRow.row.size(); j++) {
                String columnNamei = T.firstRow.row.get(i);
                String columnNamej = T.firstRow.row.get(j);
                if(columnNamej.equals(columnNamei)){
                    duplicatedNameIndex.add(j);
                }
            }
        }
        return duplicatedNameIndex;
    }

    /**Combine two arrays of string*/
    private String[] combineStringArray(String[] str1, String[] str2){
        int strlen1 = str1.length;
        int strlen2 = str2.length;
        str1 = Arrays.copyOf(str1, strlen1 + strlen2);
        System.arraycopy(str2, 0, str1, strlen1, strlen2);
        return str1;
    }

    /**Collect the column names from two tables.
     * Return an array of string.*/
    private String[] collectColumnName(Table anotherTable) {
        List<String> columnName = new ArrayList<String>();
        for(String c:this.firstRow.row) {
            columnName.add(c);
        }
        for(String c:anotherTable.firstRow.row) {
            columnName.add(c);
        }
        return columnName.toArray(new String[columnName.size()]);

    }

    /**Determine whether two tables have the same column.*/
    private boolean haveSameColumn(Table anotherTable){
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
