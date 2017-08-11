
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/8.
 */
public class Table {
    ArrayList<Row> tableRows;
    Row firstRow;
    public int columnSize;
    public int tableSize = 0;

    /*Create the first row of Table.*/
    public Table(String... columnName) {
        columnSize = columnName.length;
        tableRows = new ArrayList<Row>();
        firstRow = new Row(columnName);
        tableRows.add(firstRow);
        tableSize += 1;
    }

    /*Add a new row into the table.*/
    public void addRow(Row inputRow) {
        if(inputRow.rowSize != columnSize){
            System.out.println("The row which you will add is not correspond with the table.");
        }else{
            tableSize += 1;
            tableRows.add(inputRow);
        }
    }

    /*Find the index of the row whose name is personName.*/
    public int findPersonIndex(String personName) {
        for(int i =0; i < tableSize; i ++) {
            if(tableRows.get(i).row.get(0) == personName){
                return i;
            }
        }
        return -1;
    }

    /*Delete the person provided.*/
    public void deleteRow(Row deletePerson) {
        String deleteName = deletePerson.row.get(0);
        int deleteIndex = findPersonIndex(deleteName);

        if(deleteIndex != -1) {
            tableRows.remove(deleteIndex);
            tableSize -= 1;
        }else{
            System.out.println(deleteName + " is not exist");
        }
    }

    /*Change some information of specified person.*/
    public void changeInfo(Row changePerson, String Item, String changeInfo) {
        int changeColumnIndex = firstRow.row.indexOf(Item);
        String changePersonName = changePerson.row.get(0);
        int changeRowIndex = findPersonIndex(changePersonName);
        tableRows.get(changeRowIndex).row.set(changeColumnIndex, changeInfo);
    }

    /*Print out the table.*/
    public void printTable() {
        for(int i = 0; i < tableSize; i++) {
            tableRows.get(i).printRow();
        }
    }

    /*Return the selected person*/
    public Row selectPerson(Row selectPerson) {
        String personName = selectPerson.row.get(0);
        int selectPersonIndex = findPersonIndex(personName);
        return this.tableRows.get(selectPersonIndex);
    }

    /*Return a boolean value to tell the existance of person.*/
    public boolean isExist(String selectPerson) {
        //String personName = selectPerson.row.get(0);
        return findPersonIndex(selectPerson) >= 0;
    }
}
