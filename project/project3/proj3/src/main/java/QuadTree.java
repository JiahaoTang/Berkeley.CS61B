public class QuadTree {
    QTreeNode node;
    QuadTree firstChild;
    QuadTree secondChild;
    QuadTree thirdChild;
    QuadTree forthChild;

    public QuadTree(QTreeNode node){
        this.node = node;
        this.firstChild = null;
        this.secondChild = null;
        this.thirdChild = null;
        this.forthChild = null;
    }
}
