package db;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;
import javafx.scene.control.Tab;

import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.util.StringJoiner;


public class Database {

    public ArrayList<Table> tableList;

    /**constructor.*/
    public Database() {
        tableList = new ArrayList<Table>();
    }

    public String transact(String query) {
        eval(query);
        return "Finished your operation.";
    }

    /**The parse of input command line.
     * Don't Change ANYTHING BELOW.*/
    // Various common constructs, simplifies parsing.
    private static final String REST  = "\\s*(.*)\\s*",
            COMMA = "\\s*,\\s*",
            AND   = "\\s+and\\s+";

    // Stage 1 syntax, contains the command name.
    private static final Pattern CREATE_CMD = Pattern.compile("create table " + REST),
                                 LOAD_CMD   = Pattern.compile("load " + REST),
                                 STORE_CMD  = Pattern.compile("store " + REST),
                                 DROP_CMD   = Pattern.compile("drop table " + REST),
                                 INSERT_CMD = Pattern.compile("insert into " + REST),
                                 PRINT_CMD  = Pattern.compile("print " + REST),
                                 SELECT_CMD = Pattern.compile("select " + REST);

    // Stage 2 syntax, contains the clauses of commands.
    private static final Pattern CREATE_NEW  = Pattern.compile("(\\S+)\\s+\\(\\s*(\\S+\\s+\\S+\\s*" +
                                                "(?:,\\s*\\S+\\s+\\S+\\s*)*)\\)"),
                                 SELECT_CLS  = Pattern.compile("([^,]+?(?:,[^,]+?)*)\\s+from\\s+" +
                                                "(\\S+\\s*(?:,\\s*\\S+\\s*)*)(?:\\s+where\\s+" +
                                                "([\\w\\s+\\-*/'<>=!.]+?(?:\\s+and\\s+" +
                                                "[\\w\\s+\\-*/'<>=!.]+?)*))?"),
                                 CREATE_SEL  = Pattern.compile("(\\S+)\\s+as select\\s+" +
                                                SELECT_CLS.pattern()),
                                 INSERT_CLS  = Pattern.compile("(\\S+)\\s+values\\s+(.+?" +
                                                "\\s*(?:,\\s*.+?\\s*)*)");

    /**The methods that can evaluate the query from users.*/
    public void eval(String query) {
        Matcher m;

        if ((m = CREATE_CMD.matcher(query)).matches()) { //Query and CREATE_CMD have the same word "create table ".
            createTable(m.group(1));                      //Call the createTable method.
        } else if ((m = LOAD_CMD.matcher(query)).matches()) {
            loadTable(m.group(1));
        } else if ((m = STORE_CMD.matcher(query)).matches()) {
            storeTable(m.group(1));
        } else if ((m = DROP_CMD.matcher(query)).matches()) {
            dropTable(m.group(1));
        } else if ((m = INSERT_CMD.matcher(query)).matches()) {
            insertRow(m.group(1));
        } else if ((m = PRINT_CMD.matcher(query)).matches()) {
            printTable(m.group(1));
        } else if ((m = SELECT_CMD.matcher(query)).matches()) {
            select(m.group(1));
        } else {
            System.err.printf("Malformed query: %s\n", query);
        }
    }
    //Finished
    public void createTable(String expr) {
        Matcher m;
        if ((m = CREATE_NEW.matcher(expr)).matches()) {                 //Match the command line which is to create a new table.
            createNewTable(m.group(1), m.group(2).split(COMMA));
        } else if ((m = CREATE_SEL.matcher(expr)).matches()) {         //Match the command line which is to create a table from exist table
            createSelectedTable(m.group(1), m.group(2), m.group(3), m.group(4));
        } else {                                                        //Error expression.
            System.err.printf("Malformed create: %s\n", expr);
        }
    }
    //Finished
    public void createNewTable(String name, String[] cols) {
        StringJoiner joiner = new StringJoiner(", ");
        for (int i = 0; i < cols.length-1; i++) {
            joiner.add(cols[i]);
        }

        //Create a new table.
        Table newtable = new Table(name);
        //Put the cols into the new table's first line;
        Row firstrow = new Row(cols);
        newtable.addFirstRow(firstrow);
        //Add the new table into the database.
        tableList.add(newtable);
        System.out.println(tableList.size());

        //Tell the users what they have done.
        String colSentence = joiner.toString() + " and " + cols[cols.length-1];
        System.out.printf("You are trying to create a table named %s with the columns %s\n", name, colSentence);
    }

    //select <column expr0>,<column expr1>,... from <table0>,<table1>,... where <cond0> and <cond1> and ...
    private void createSelectedTable(String name, String exprs, String tables, String conds) {
        System.out.printf("You are trying to create a table named %s by selecting these expressions:" +
                " '%s' from the join of these tables: '%s', filtered by these conditions: '%s'\n", name, exprs, tables, conds);
        String[] exprsArray = exprs.split(",");
        String[] tablesNames = tables.split(",");
        String[] condsArray = conds.split(" and ");
        //Merge the tables.
        ArrayList<Table> tablesArray = collectTablesArray(tablesNames);
        Table unSelectedTable = new Table("unSelectedTable");
        for(Table t:tablesArray) {
            unSelectedTable.joinWith(t);
        }
        //Selected columns.
        //Table SelectedTables =
    }

    //Used in createSelectedTable.
    private ArrayList<Table> collectTablesArray(String[] tableNameString) {
        ArrayList<Table> TableArray = new ArrayList<Table>();
        for(int i = 0; i < tableList.size(); i ++) {
            for(String name:tableNameString){
                if(tableList.get(i).Name == name) {
                    TableArray.add(tableList.get(i));
                }
            }
        }
        return TableArray;
    }

    //Finished
    public void loadTable(String name) {
        System.out.printf("You are trying to load the table named %s\n", name);

        In file = new In("C:\\Users\\Administrator\\Desktop\\CS61B\\project\\project2\\examples\\"+name+".tbl");
        Table newTable = new Table(name);
        newTable.Name = name;
        newTable.addFirstRow(new Row(file.readLine()));
        String nextLine = file.readLine();
        while(nextLine != null) {
            newTable.addRow(new Row(nextLine));
            nextLine = file.readLine();
        }
        tableList.add(newTable);
        System.out.println(tableList.size());
    }
    //Finished
    public void storeTable(String name) {

        System.out.printf("You are trying to store the table named %s\n", name);

        Out file = new Out("C:\\Users\\Administrator\\Desktop\\CS61B\\project\\project2\\examples\\"+name+".tbl");

        Table newTable;
        for(int i = 0; i < tableList.size(); i++) {
            //Find the table whose name is name string.
            if(tableList.get(i).Name.equals(name)) {
                //Assign specified table to newTable.
                newTable = tableList.get(i);
                //Put every row into file.
                for(int j = 0; j < newTable.rowSize;j++) {
                    //Combine every element in every row with comma.
                    StringJoiner joiner = new StringJoiner(", ");
                    for (int k = 0; k < newTable.getRows().get(j).rowSize; k++) {
                        joiner.add(newTable.getRows().get(j).row.get(k));
                    }
                    file.println(joiner.toString());
                }
                file.close();
                break;
            }
        }
    }
    //Finished
    public void dropTable(String name) {
        System.out.printf("You are trying to drop the table named %s\n", name);
        //Find the table in tableList and remove it according to it name and index.
        for(int i = 0; i < tableList.size(); i++) {
            if(tableList.get(i).Name.equals(name)) {
                tableList.remove(i);
                System.out.println(tableList.size());
                break;
            }
        }
    }
    //Finished
    public void insertRow(String expr) {
        Matcher m = INSERT_CLS.matcher(expr);
        if (!m.matches()) {
            System.err.printf("Malformed insert: %s\n", expr);
            return;
        }

        //Group(2) is values and group(1) is table name.
        System.out.printf("You are trying to insert the row \"%s\" into the table %s\n", m.group(2), m.group(1));

        //Find the table in tableList
        for(int i = 0; i < tableList.size(); i++) {
            if(tableList.get(i).Name.equals(m.group(1))) {
                //Convert the group(2) into a string group without ",".
                Row insertRow = new Row(m.group(2).split(","));
                //Insert the row into the table
                tableList.get(i).addRow(insertRow);
                //Print out the number of tables in tableList. Should no change.
                System.out.println(tableList.size());
                break;
            }
        }
    }
    //Finished
    public void printTable(String name) {
        System.out.printf("You are trying to print the table named %s\n", name);
        boolean printCheck = false;
        for(int i = 0; i < tableList.size(); i++) {
            if(tableList.get(i).Name.equals(name)) {
                tableList.get(i).printTable();
                printCheck = true;
                break;
            }
        }
        if (!printCheck) {
            System.out.println("There is no table named " + name);
        }

    }

    private static void select(String expr) {
        Matcher m = SELECT_CLS.matcher(expr);
        if (!m.matches()) {
            System.err.printf("Malformed select: %s\n", expr);
            return;
        }

        select(m.group(1), m.group(2), m.group(3));
    }

    private static void select(String exprs, String tables, String conds) {
        System.out.printf("You are trying to select these expressions:" +
                " '%s' from the join of these tables: '%s', filtered by these conditions: '%s'\n", exprs, tables, conds);
    }
}
