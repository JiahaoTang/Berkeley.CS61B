package synthesizer;

import java.util.Iterator;

/**
 * Created by Administrator on 2017/8/6.
 */
public interface BoundedQueue<T> extends Iterable<T> {
    public abstract int capacity();
    public abstract int fillCount();
    public abstract void enqueue(T x);
    public abstract T dequeue();
    public abstract T peek();
    public abstract Iterator<T> iterator();

    default boolean isEmpty(){
        return fillCount() == 0;
    }

    default boolean isFull(){
        return capacity() == fillCount();
    }

}
