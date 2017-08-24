import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/24.
 */
public class Fibonacci {
    public Map<Integer, Integer> map = new HashMap<Integer, Integer>();

    //fib(0) = 0, fib(1) = 1, fib(2) = 1, fib(3) = 2, ...
    public int fib(int n) {
        if(n == 0){
            map.put(0, 0);
            return 0;
        }else if(n == 1){
            map.put(1, 1);
            return 1;
        }else if(map.containsKey(n)) {
            return map.get(n);
        }else{
            Integer fib_n = fib(n - 2) + fib(n - 1);
            map.put(n, fib_n);
            return fib_n;
        }
    }

    public static void main(String[] args) {
        Fibonacci x = new Fibonacci();
        System.out.println(x.fib(5));
        System.out.println(x.fib(100));
        System.out.println(x.map);
    }
}
