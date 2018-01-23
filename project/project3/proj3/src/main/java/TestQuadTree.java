public class TestQuadTree {
    quadTree qt;

    private class quadTree{
        String index;
        quadTree firstChild;
        quadTree secondChild;
        quadTree thirdChild;
        quadTree forthChild;

        quadTree(String index){
            this.index = index;
            this.firstChild = null;
            this.secondChild = null;
            this.thirdChild = null;
            this.forthChild = null;
        }
    }
    /** imgRoot is the name of the directory containing the images.
     *  You may not actually need this for your class. */
    public TestQuadTree(String imgRoot) {
        // YOUR CODE HERE
        qt = new quadTree("root");
        qt.firstChild = construct("1", "");
        qt.secondChild = construct("2", "");
        qt.thirdChild= construct("3", "");
        qt.forthChild = construct("4", "");
    }

    public quadTree construct(String num, String index){
        if(num.length() == 2){
            return null;
        }else{
            quadTree res = new quadTree(num + index);
            res.firstChild = construct(res.index, "1");
            res.secondChild = construct(res.index, "2");
            res.thirdChild = construct(res.index, "3");
            res.forthChild = construct(res.index, "4");
            return res;
        }
    }

    public static void main(String[] args){
        TestQuadTree tree = new TestQuadTree("img/");
        System.out.println("hello world");
    }
}
