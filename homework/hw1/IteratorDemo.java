
import java.util.List;
import java.util.Iterator;

/**
 * Created by Administrator on 2017/8/7.
 */
public class IteratorDemo {
    public static void main(String[] args) {
        synthesizer.ArrayRingBuffer<Integer> friends = new synthesizer.ArrayRingBuffer<Integer>(10);
        friends.enqueue(5);
        friends.enqueue(23);
        friends.enqueue(42);
        friends.enqueue(54);
        friends.enqueue(75);

        friends.enqueue(88);
        friends.enqueue(96);
        friends.enqueue(41);
        friends.enqueue(74);
        friends.enqueue(36);

        for (int x : friends) {
            System.out.println(x);
        }

        Iterator<Integer> friendViewer = friends.iterator();
        while (friendViewer.hasNext()) {
            System.out.println(friendViewer.next());
        }
    }
}
