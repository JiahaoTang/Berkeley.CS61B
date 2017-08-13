import java.util.ArrayList;

/**
 * The Union Find Algorithm
 * Created by Administrator on 2017/8/13.
 */
public class UnionFindString{
    private ArrayList<String> items;
    private int[] id;
    private int count;

    /*Constructor.*/
    public UnionFindString(int capacity) {
        items = new ArrayList<String>(capacity);
        //Initialize id group.
        id = new int[capacity];
        for(int i = 0; i < capacity; i++) {
            id[i] = i;
        }

        count = capacity;
    }

    public void addItems(String item) {
        items.add(item);
    }

    public int find(String item) {
        int itemindex = items.indexOf(item);
        return id[itemindex];
    }

    public int getComponentNum() {
        return count;
    }

    public boolean connected(String item1, String item2){
        return find(item1) == find(item2);
    }

    public void union(String item1, String item2) {
        int item1index = items.indexOf(item1);
        int item2index = items.indexOf(item2);
        if(item1index == item2index) return;
        for(int i = 0; i < id.length; i++) {
            if(find(items.get(i)) == item2index) {
                id[i] = item1index;
            }
        }
        count--;
    }
}
