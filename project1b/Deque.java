/**
 * Created by Administrator on 2017/8/3.
 */
public interface Deque<type> {
    public void addFirst(type x);
    public void addLast(type x);
    public boolean isEmpty();
    public int size();
    public void printDeque();
    public type removeFirst();
    public type removeLast();
    public type get(int index);
}
