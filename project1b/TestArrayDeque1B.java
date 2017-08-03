import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Created by Administrator on 2017/8/3.
 */
public class TestArrayDeque1B {
    @Test
    public void TestArrayDeque1B(){
            StudentArrayDeque<Integer> a = new StudentArrayDeque<Integer>();
            ArrayDequeSolution<Integer> b = new ArrayDequeSolution<Integer>();
        for(int i = 0; i < 10; i++){
            Double NumberBetweenZeroAndOne = StdRandom.uniform();

            if(NumberBetweenZeroAndOne > 0.5){
                a.addFirst(i);
                b.addFirst(i);
            }else{
                a.addLast(i);
                b.addLast(i);
            }
        }

        for(int i = 0; i < 10; i++){
            Double NumberBetweenZeroAndOne = StdRandom.uniform();

            if(NumberBetweenZeroAndOne > 0.5){
                a.removeLast();
                b.removeLast();
            }else{
                a.removeFirst();
                b.removeFirst();
            }
        }

        for(int i = 0; i < b.size(); i++){
            assertEquals("This is bad: StudentArrayDeque item " + a.get(i) +
                    "is not equel with the ArrayDequeSolution number "+ b.get(i),
                    a.get(i), b.get(i));

        }
    }
}
