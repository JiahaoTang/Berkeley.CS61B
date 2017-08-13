/**
 * Created by Administrator on 2017/8/13.
 */
public class UnionFindTest {
    public static void main(String[] args) {
        UnionFindString UF = new UnionFindString(9);
        UF.addItems("a");
        UF.addItems("b");
        UF.addItems("c");
        UF.addItems("d");
        UF.addItems("e");
        UF.addItems("f");
        UF.addItems("g");
        UF.addItems("h");
        UF.addItems("i");

        UF.union("b", "d");
        UF.union("c", "e");
        UF.union("h", "e");
        UF.union("f", "a");
        UF.union("g", "i");
        UF.union("g", "b");
        System.out.println(UF.connected("d", "i"));
        System.out.println(UF.connected("a", "g"));
        System.out.println(UF.getComponentNum());
    }
}
