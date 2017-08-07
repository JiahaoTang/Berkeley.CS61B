package synthesizer;

/**
 * Created by Administrator on 2017/8/7.
 */
public abstract class AbstractBoundQueue<T> implements BoundedQueue<T>{
    protected int fillCount;
    protected int capacity;

    public int capacity(){
        return capacity;
    }

    public int fillCount(){
        return fillCount;
    }
}
