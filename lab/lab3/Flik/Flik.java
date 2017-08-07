import org.junit.Test;
import static org.junit.Assert.*;

/** An Integer tester created by Flik Enterprises. */
public class Flik {
    public static boolean isSameNumber(Integer a, Integer b) {
        return a == b;
    }

    @Test
    public void Fliktest(){
        Integer a = 129;
        Integer b = 129;
        assertTrue(isSameNumber(a, b));
    }
}

