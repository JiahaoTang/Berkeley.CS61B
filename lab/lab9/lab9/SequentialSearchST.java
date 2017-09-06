package lab9;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by Administrator on 2017/9/2.
 */
public class SequentialSearchST<Key, Value> {
    private Node first;
    private int n;

    private class Node{
        Key key;
        Value value;
        Node next;

        public Node(Key k, Value v){
            key = k;
            value = v;
            next = null;
        }
    }

    public SequentialSearchST(){
        n = 0;
        first = null;
    }

    public void clear(){
        first = null;
        n = 0;
    }

    public int size(){
        return n;
    }

    public boolean containsKey(Key k){
        Node p1 = first;

        while(p1 != null){
            if(p1.key.equals(k)) return true;
            else p1 = p1.next;
        }
        return false;
    }

    public void put(Key k, Value v){
        if(containsKey(k)){
            Node p1 = first;
            while(true){
                if(p1.key.equals(k)) {
                    p1.value = v;
                    return;
                }
            }
        }else{
            Node newFirst = new Node(k, v);
            newFirst.next = first;
            first = newFirst;
            n += 1;
        }
    }

    public Value remove(Key k){
        if(containsKey(k)){
            Node p1 = first.next;
            Node p2 = first;
            if(p2.key == k) {
                first = p1;
                n =- 1;
                return p2.value;
            }else{
                while(true){
                    if(p1.key == k){
                        p2.next = p1.next;
                        n =- 1;
                        return p1.value;
                    }else{
                        p1 = p1.next;
                        p2 = p2.next;
                    }
                }
            }
        }else{
            return null;
        }
    }

    public Value get(Key k){
        if(containsKey(k)){
            Node p = first;
            while(true){
                if(p.key.equals(k)) return p.value;
                else p = p.next;
            }
        }else{
            return null;
        }
    }

    public Key getKey(int index) {
        Node pointer = first;
        int i = 0;
        while( i < index){
            pointer = pointer.next;
            i++;
        }
        return pointer.key;
    }

    public Queue<Key> keys(){
        Queue<Key> q = new Queue();
        for(Node i = first;i != null;i = i.next){
            q.enqueue(i.key);
        }
        return q;
    }

    public static void main(String[] args) {
        SequentialSearchST<String, String> st = new SequentialSearchST<String, String>();

        // insert some key-value pairs
        st.put("www.cs.princeton.edu",   "128.112.136.11");
        st.put("www.cs.princeton.edu",   "128.112.136.35");
        st.put("www.princeton.edu",      "128.112.130.211");
        st.put("www.math.princeton.edu", "128.112.18.11");
        st.put("www.yale.edu",           "130.132.51.8");
        st.put("www.amazon.com",         "207.171.163.90");
        st.put("www.simpsons.com",       "209.123.16.34");
        st.put("www.stanford.edu",       "171.67.16.120");
        st.put("www.google.com",         "64.233.161.99");
        st.put("www.ibm.com",            "129.42.16.99");
        st.put("www.apple.com",          "17.254.0.91");
        st.put("www.slashdot.com",       "66.35.250.150");
        st.put("www.whitehouse.gov",     "204.153.49.136");
        st.put("www.espn.com",           "199.181.132.250");
        st.put("www.snopes.com",         "66.165.133.65");
        st.put("www.movies.com",         "199.181.132.250");
        st.put("www.cnn.com",            "64.236.16.20");
        st.put("www.iitb.ac.in",         "202.68.145.210");

        // search for IP addresses given URL
        StdOut.println("size = " + st.size());
        StdOut.println(st.get("www.cs.princeton.edu"));
        StdOut.println(st.get("www.amazon.com"));
        StdOut.println(st.get("www.amazon.edu"));
        StdOut.println();

        // test out the iterator
        for (String s : st.keys())
            StdOut.println(s);


        // print out all key-value pairs
        for (String s : st.keys())
            StdOut.println(s + " " + st.get(s));
        StdOut.println();

        StdOut.println("Deleting");
        StdOut.println(st.remove("www.princeton.edu"));
        StdOut.println(st.remove("www.princeton.edu"));
    }

}
