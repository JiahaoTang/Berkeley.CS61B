package lab8;

import com.sun.org.apache.bcel.internal.generic.LSUB;
import edu.princeton.cs.algs4.BST;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Administrator on 2017/8/24.
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    //root of BST
    public Node root;

    //Number of Node in BSTMap;
    public int size = 0;

    //The fundamental node of binary search tree.
    private class Node{
        K key;
        V value;
        Node Lsubtree;
        Node Rsubtree;
        int size;//Number of Node in all subtrees.
        //Constructor of Node
        Node(K key, V value, int size){
            this.key = key;
            this.value = value;
            this.size = size;
        }
    }
    //Constructor of BSTMap.
    public BSTMap(){
    }

    @Override
    //Remove all items from BSTmaps
    public void clear() {
        root = null;
    }

    @Override
    /* Returns true if this map contains a mapping for the specified key. */
    public boolean containsKey(K key){
        return get(key) != null;
    }

    @Override
    /*Return the value of given key*/
    public V get(K key) {
        return get(root, key);
    }

    private V get(Node x, K key) {
        if(key == null) throw new IllegalArgumentException("called get() with a null key");
        if(x == null) return null;
        int cmp = key.compareTo(x.key);
        if(cmp < 0){
            return get(x.Lsubtree, key);
        }else if(cmp > 0){
            return get(x.Rsubtree, key);
        }else{
            return x.value;
        }
    }

    @Override
    public int size(){
        return size(root);
    }

    public int size(Node x){
        if(x == null) return 0;
        else return x.size;
    }

    @Override
    public void put(K key, V value){
        if(key == null) throw new IllegalArgumentException("callinput() with a null key");
        //if(value == null)
        root = put(root, key, value);
    }

    private Node put(Node x, K key, V value){
        if(x == null) return new Node(key, value, 1);
        int cmp = key.compareTo(x.key);
        if(cmp < 0){
            x.Lsubtree = put(x.Lsubtree, key, value);
        }else if(cmp > 0){
            x.Rsubtree = put(x.Rsubtree, key, value);
        }else{
            x.value = value;
        }
        int sizeL = size(x.Lsubtree);
        int sizeR = size(x.Rsubtree);
        x.size = 1 + sizeL + sizeR;
        return x;
    }

    /* Returns a Set view of the keys contained in this map. */
    public Set<K> keySet(){
        //return keySet(root);
        Set<K> ks = new HashSet<K>();
        if(root == null){
        }else{
            addkey(ks, root);
        }
        return ks;
    }

    private void addkey(Set<K> ks, Node r) {
        if(r != null){
            ks.add(r.key);
            addkey(ks, r.Lsubtree);
            addkey(ks, r.Rsubtree);
        }else{
            return;
        }
    }

    /* Removes the mapping for the specified key from this map if present.
     * Not required for Lab 8. If you don't implement this, throw an
     * UnsupportedOperationException. */

    private Node minNode(Node r){
        if(r.Lsubtree == null) {
            return r ;
        }else {
            return minNode(r.Lsubtree);
        }
    }

    private Node deleteMin(Node r) {
        if(r.Lsubtree == null) return r.Rsubtree;
        r.Lsubtree = deleteMin(r.Lsubtree);
        r.size = size(r.Lsubtree) + size(r.Rsubtree) + 1;
        return r;
    }

    private Node remove(Node r, K key) {
        int cmp = key.compareTo(r.key);
        if(cmp < 0){
            r.Lsubtree = remove(r.Lsubtree, key);
        }else if(cmp > 0){
            r.Rsubtree = remove(r.Rsubtree, key);
        }else{
            if(r.Lsubtree == null) return r.Rsubtree;
            if(r.Rsubtree == null) return r.Lsubtree;
            Node t = r;
            r.key = minNode(t.Rsubtree).key;
            r.value = minNode(t.Rsubtree).value;
            r.Rsubtree = deleteMin(t.Rsubtree);
            r.Lsubtree = t.Lsubtree;
        }
        r.size = size(r.Lsubtree) + size(r.Rsubtree) + 1;
        return r;
    }

    @Override
    public V remove(K key){
        if(!containsKey(key)) throw new IllegalArgumentException("Cannot find the specified key.");
        V returnValue = get(key);
        root = remove(root, key);
        return returnValue;
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 8. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        if(get(key) == value){
            return remove(key);
        }else{
            throw new IllegalArgumentException("Cannot find the specified key.");
        }
    }

    @Override
    public Iterator<K> iterator() {
        //return new BSTMapIter();
        throw new UnsupportedOperationException();
    }

    /*
    private class BSTMapIter implements Iterator<K>{
        public BSTMapIter(){
            return root;
        }
    }
    */
}
