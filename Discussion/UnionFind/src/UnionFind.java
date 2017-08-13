/**
 * Created by Administrator on 2017/8/13.
 * This Algorithm is named Union Find.
 */
public class UnionFind implements UnionFindAPI{
    private int[] id;
    private int count;

    public UnionFind(int capacity) {
        id = new int[capacity];
        for(int i = 0; i < capacity; i ++) {
            id[i] = i;
        }
        count = capacity;
    }

    @Override
    public int getComponentNum() {
        return count;
    }

    @Override
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    @Override
    public int find(int p) {
        return id[p];
    }

    @Override
    public void union(int p, int q){
        int IDp = find(p);
        int IDq = find(q);
        if(IDp == IDq) {
            return;
        }else {
            for(int i = 0; i < id.length; i++) {
                if(find(i) == IDq){
                    id[i] = IDp;
                }
            }
        }
        count--;
    }
}
