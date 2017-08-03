/**
 * Created by Administrator on 2017/8/4.
 */
public class LinkListDequeTest {
    public static void main(String[] args){
        LinkedListDeque<Integer> test= new LinkedListDeque<Integer>();
        test.addLast(1);
        test.addLast(2);
        test.addLast(3);
        test.addLast(4);
        test.addLast(5);
        int x = test.get(1);//should be 2
        int y = test.get(4);//should be 5

    }
}
