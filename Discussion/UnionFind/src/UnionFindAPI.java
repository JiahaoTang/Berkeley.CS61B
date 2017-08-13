/**
 * Created by Administrator on 2017/8/13.
 * The interface of Union find class.
 */
public interface UnionFindAPI {
    boolean connected(int p, int q);
    void union(int p, int q);
    int find(int p);
    int getComponentNum();
}
