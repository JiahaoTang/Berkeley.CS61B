/**
 * Created by Administrator on 2017/7/27.
 */
public class LinkedListDeque<type> {
    /*Create a class named LinkedlistItem which is the fundamental item in the LinkedListDeque.*/
    private class DoubleLLinkedlist {
        public DoubleLLinkedlist prev;
        public type Item;
        public DoubleLLinkedlist next;

        public DoubleLLinkedlist(type x, DoubleLLinkedlist nextDLList, DoubleLLinkedlist prevDLList){
            Item = x;
            next = nextDLList;
            prev = prevDLList;
        }
    }

    /*Create the inner size counter and sentinel node.*/
    private int size;
    private DoubleLLinkedlist sentFront;
    private DoubleLLinkedlist sentBack;
    private DoubleLLinkedlist getPointer;
    /*Create a empty LinkedListDeuqe*/
    public LinkedListDeque(){
        sentFront = new DoubleLLinkedlist(null, null, null);
        sentBack = new DoubleLLinkedlist(null, null, null);
        sentFront.next = sentBack;
        sentBack.prev = sentFront;
        sentFront.prev = sentBack;
        sentBack.next = sentFront;
        getPointer = sentFront;
        size = 0;
    }

    /*Create a LinkedListDeque within one item.*/
    public LinkedListDeque(type x){
        sentFront = new DoubleLLinkedlist(null, null, null);
        sentBack = new DoubleLLinkedlist(null, null, null);
        DoubleLLinkedlist item = new DoubleLLinkedlist(x, sentBack, sentFront);
        sentFront.prev = sentBack;
        sentFront.next = item;
        sentBack.next = sentFront;
        sentBack.prev = item;
        getPointer = sentFront;
        size = 1;
    }

    /*Create the addFirst method.*/
    public void addFirst(type x){
        DoubleLLinkedlist item = new DoubleLLinkedlist(x, null, null);
        item.next = sentFront.next;
        item.prev = sentFront;
        sentFront.next = item;
        item.next.prev = item;
        size += 1;
    }

    /*Create the addLast method*/
    public void addLast(type x){
        DoubleLLinkedlist item = new DoubleLLinkedlist(x, null, null);
        item.next = sentBack;
        item.prev = sentBack.prev;
        sentBack.prev = item;
        item.prev.next = item;
        size += 1;
    }

    /*Create the isEmpty method*/
    public boolean isEmpty(){
        if(size == 0){
            return true;
        }
        return  false;
    }

    /*Create the size method*/
    public int size(){
        return size;
    }

    /*Create the printDeque method*/
    public void printDeque(){
        DoubleLLinkedlist printItem;
        printItem = sentFront.next;
        for(int i = 0; i< size; i++) {
            System.out.print((String)printItem.Item + ' ');
            printItem = printItem.next;
        }
        System.out.println();
    }

    /*Create the removeFirst method*/
    public type removeFirst(){
        DoubleLLinkedlist removeItem;
        removeItem = sentFront.next;
        sentFront.next = removeItem.next;
        sentFront.next.prev = sentFront;
        size -= 1;
        return removeItem.Item;
    }

    /*Create the removeLast method.*/
    public type removeLast(){
        DoubleLLinkedlist removeItem;
        removeItem = sentBack.prev;
        sentBack.prev = removeItem.prev;
        removeItem.prev.next = sentBack;
        size -= 1;
        return removeItem.Item;
    }

    /*Create the get method.*/
    public type get(int index){
        for (int i = 0; i < index; i++){
            getPointer = getPointer.next;
        }
        return getPointer.Item;
    }

    /*Create the getRecursive method.*/
    public type getRecursive(int index){
        if(index == 0){
            return (type)getPointer.Item;
        }else{
            getPointer = getPointer.next;
            return getRecursive(index - 1);
        }
    }

}

/*Test Github function in IntelliJ*/
