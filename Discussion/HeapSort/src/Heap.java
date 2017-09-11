/**
 * Created by Administrator on 2017/9/10.
 */
public class Heap {
    int[] array;

    public Heap(int[] array){
        this.array = array;
    }

    private int maxIndex(){
        return array.length - 1;
    }

    public void maxHeaplify(int endIndex){
        for(int i = endIndex; i >= 2; i--){
            if(array[i/2] < array[i]){
                swap(i/2, i);
            }
        }

    }

    public void minHeaplify(int startIndex){
        for(int i = startIndex; i >= 2; i--){
            if(array[i/2] > array[i]) {
                swap(i/2, i);
            }
        }
    }

    public void ascendingHeapSort(){
        for(int i = maxIndex(); i >= 2; i--){
            maxHeaplify(i);
            swap(1, i);
        }
    }

    public void descendingHeapSort(){
        for(int i = maxIndex(); i >= 2; i--){
            minHeaplify(i);
            swap(1, i);
        }
    }

    private void swap(int formalIndex, int latterIndex){
        int temp = array[formalIndex];
        array[formalIndex] = array[latterIndex];
        array[latterIndex] = temp;
    }

    public static void main(String[] args){
        int[] list = new int[]{99, 7, 44, 345, 6756, 21, 2, 88};
        Heap test = new Heap(list);
        //test.maxHeaplify(test.maxIndex());
        test.ascendingHeapSort();
        test.descendingHeapSort();
    }
}
