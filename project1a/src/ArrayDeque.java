/**
 * Created by Administrator on 2017/7/27.
 */
public class ArrayDeque<type> {
    private type[] items;
    private int size;

    /*Create two pointers*/
    int nextLast = 1;
    int nextFirst = 0;
    int max_size;
    int rfactor = 2;

    /*Create a empty Alist.*/
    @SuppressWarnings("unchecked")
    public ArrayDeque(){
        max_size = 8;
        items = (type[]) new Object[max_size];
        size = 0;
    }

    /*When the Alist is full, we need to extend the max_size of the list.
    * This function is used to resize the list.*/
    @SuppressWarnings("unchecked")
    public void resize(int capacity){
        type[] newArray = (type[]) new Object[capacity];
        System.arraycopy(items, 0, newArray, 0, size);
        items = newArray;
        max_size = capacity;
    }

    /*Add a item at the first position.*/
    public void addFirst(type x){
        if(size == items.length){
            resize(size * rfactor);
        }
        items[nextFirst] = x;
        if(nextFirst == 0){
            nextFirst = max_size;
        }else{
            nextFirst -= 1;
        }
        size += 1;
    }

    /*Add a item at the last position.*/
    public void addLast(type x){
        if(size == items.length){
            resize(size * rfactor);
        }
        items[nextLast] = x;
        if(nextLast == max_size){
            nextLast = 0;
        }else{
            nextLast += 1;
        }
        size += 1;
    }

    /*Determined a Alist is Empty or not*/
    public boolean isEmpty(){
        if(size == 0) {
            return true;
        }else{
            return false;
        }
    }

    /*Return the size of the Alist.*/
    public int size(){
        return size;
    }

    /*Print out the deque on the screen.*/
    public void printDeque(){
        int printStart = nextFirst;
        if(printStart == max_size){
            printStart  = 0;
        }
        for(int i = printStart ; i < nextLast; i++){
            System.out.print((String)items[i] + " ");
        }
        System.out.println();
    }

    /*Remove first item.*/
    public type removeFirst(){
        if(nextFirst == max_size){
            nextFirst = 0;
        }else{
            nextFirst += 1;
        }
        size -= 1;
        return items[nextFirst];
    }

    /*Remove last item.*/
    public type removeLast(){
        if(nextLast == 0){
            nextLast = max_size;
        }else{
            nextLast -= 1;
        }
        size -= 1;
        return  items[nextLast];
    }

    /*Get nth item*/
    public type get(int index){
        return items[index];
    }
}
