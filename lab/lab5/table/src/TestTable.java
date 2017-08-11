/**
 * Created by Administrator on 2017/8/8.
 */
public class TestTable {
    public static void main(String[] args){
        Table Student = new Table("Name", "Sex", "Graduate School", "Major");
        Row tangjiahao = new Row("Tang Jiahao", "Male", "BIT", "EE");
        Row tianjiaqi = new Row("Tian Jiaqi", "Male", "Xian Oil Univ", "Oil Transportation");
        Row zhangxuan = new Row("Zhang Xuan", "Female", "BIT", "Spanish");
        Student.addRow(tangjiahao);
        Student.addRow(tianjiaqi);
        Student.addRow(zhangxuan);
        /*
        Student.printTable();
        Student.changeInfo(tianjiaqi, "Major", "Communication");
        System.out.println("After change operation.");
        Student.printTable();
        */
        Row specifiedPerson = Student.selectPerson(zhangxuan);
        specifiedPerson.printRow();
        if(Student.isExist("Tang Jiahao")) {
            System.out.println("Exist.");
        }else{
            System.out.println("NOT Exist.");
        }
    }

}
