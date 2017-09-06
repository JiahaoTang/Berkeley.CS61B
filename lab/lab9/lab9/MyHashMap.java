package lab9;


import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 2017/9/2.
 */
public class MyHashMap<Key, Value> {
    private static final int INIT_CAPACITY = 4;
    private static final double DEF_LOADFACTOR = 0.75;

    SequentialSearchST<Key, Value>[] sq;
    public int blockNum;
    public int pairSize;
    public double loadFactor;

    public MyHashMap(){
        this(INIT_CAPACITY, DEF_LOADFACTOR);
    }

    public MyHashMap(int blockNum){
        this(blockNum, DEF_LOADFACTOR);
    }

    public MyHashMap(int blockNum, double loadFactor){
        this.blockNum = blockNum;
        this.pairSize = 0;
        this.loadFactor = loadFactor;
        sq = (SequentialSearchST<Key, Value>[])new SequentialSearchST[blockNum];
        for(int i = 0; i < blockNum; i++) {
            sq[i] = new SequentialSearchST<Key, Value>();
        }
    }

    public void clear() {
        for(int i = 0; i < blockNum; i++){
            sq[i].clear();
        }
        pairSize = 0;
    }

    private void resize(int x){

    }

    public int hash(Key k) {
        return (k.hashCode() & 0x7fffffff) % blockNum;
    }

    public int size() {
        return pairSize;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean containsKey(Key k) {
        if(k == null) throw new IllegalArgumentException("The key occur an error.");
        return get(k) != null;
    }

    public Value get(Key k) {
        if(k == null) throw new IllegalArgumentException("The key occur an error.");
        int index = hash(k);
        return sq[index].get(k);
    }

    public void put(Key k, Value v) {
        if(k == null) throw new IllegalArgumentException("The key occur an error.");

        if(v == null){
            remove(k);
            return;
        }

        if(pairSize >= loadFactor * blockNum) resize(2 * blockNum);

        int index = hash(k);
        if(!sq[index].containsKey(k)) pairSize++;
        sq[index].put(k, v);
    }

    public Value remove(Key k){
        if(k == null) throw new IllegalArgumentException("The key occur an error.");
        int index = hash(k);
        if(sq[index].containsKey(k)) pairSize--;
        Value removeItem = sq[index].get(k);
        sq[index].remove(k);
        return removeItem;
    }

    public Set<Key> keySet() {
        Set<Key> keysSet = new HashSet<Key>();
        for(SequentialSearchST s:sq){
            for(int i = 0; i < s.size(); i++){
                keysSet.add((Key)(s.getKey(i)));
            }
        }
        return keysSet;
    }

}
