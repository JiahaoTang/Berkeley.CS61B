package db;

import db.Row;
import db.Table;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Administrator on 2017/8/8.
 */
public class TestTable {
    //@Test
    public static void main(String[] args){
        Table Student = new Table("Students");
        Row firstRow = new Row("Name", "Sex", "Graduate School", "Major");
        Row tangjiahao = new Row("Tang Jiahao", "Male", "BIT", "EE");
        Row tianjiaqi = new Row("Tian Jiaqi", "Male", "Xian Oil Univ", "Oil Transportation");
        Row zhangxuan = new Row("Zhang Xuan", "Female", "BIT", "Spanish");
        Student.addFirstRow(firstRow);
        Student.addRow(tangjiahao);
        Student.addRow(tianjiaqi);
        Student.addRow(zhangxuan);

        Column expected = new Column("Name", "Tang Jiahao");
        //expected.printColumn();
        //Row actual = Student.getRows().get(1);
        //actual.printRow();
    }

}
