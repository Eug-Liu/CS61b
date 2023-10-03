public class ArrayDeque<T>{

    private T items[];
    private int size;
    private int nextFirst;
    private int nextLast;
    private int length;

    /** Creates an empty list. */
    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 7;
        nextLast = 0;
        length = 8;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int size(){
        return size;
    }

    private int minusOne(int index) {
        if (index == 0) {
            return length - 1;
        }
        return index - 1;
    }

    private int plusOne(int index) {
        index += 1;
        if (index == length) {
            return 0;
        }
        return index;
    }

    public void grow(){

        T[] a =(T[]) new Object[2 * length];
        int first = plusOne(nextFirst);
        int last = minusOne(nextLast);
        if(first > last){
            System.arraycopy(items, first, a, 0, length - first);
            System.arraycopy(items, 0, a, length - first, last + 1);
        } else{
            System.arraycopy(items, first, a, 0, size);
        }
        
        length *= 2;
        items = a;
        nextFirst = length - 1;
        nextLast = size;

    }

    public void shirnk(){

        T[] a =(T[]) new Object[length / 2];
        int first = plusOne(nextFirst);
        int last = minusOne(nextLast);
        if(first > last){
            System.arraycopy(items, first, a, 0, length - first);
            System.arraycopy(items, 0, a, length - first, last + 1);
        } else{
            System.arraycopy(items, first, a, 0, size);
        }
        
        length /= 2;
        items = a;
        nextFirst = length - 1;
        nextLast = size;
    }

    /** Inserts X into the back of the list. */
    public void addLast(T x) {
        if(size == length)
            grow();
        items[nextLast] = x;
        nextLast = plusOne(nextLast);
        size++;
    }

    public void addFirst(T x){
        if(size == length)
            grow();
        items[nextFirst] = x;
        nextFirst = minusOne(nextFirst);
        size++;
    }

    /** Deletes item from back of the list and
      * returns deleted item. */
    public T removeLast() {
        if(size == 0)
            return null;
        int last = minusOne(nextLast);
        T x = items[last];
        nextLast = last;
        size--;
        if(size * 4 < length)
            shirnk();
        return x;
    }

    public T removeFirst(){
        if(size == 0)
            return null;
        int first = plusOne(nextFirst);
        T x = items[first];
        nextFirst = first;
        size--;
        if(size * 4 < length)
            shirnk();
        return x;
    }

    public T get(int index){
        if(index >= size)
            return null;
        int first = plusOne(nextFirst);
        int last = minusOne(nextLast);
        if(first < last)
            return items[first + index];
        else{
            if(index <= length - first - 1)
                return items[first + index];
            else return items[index - length + first];
        }
    }

    public void printDeque(){
        int first = plusOne(nextFirst);
        int index = first;
        for(int rest = size; rest > 0; rest--){
            System.out.print(items[index] + " ");
            index = plusOne(index);
        }       
        System.out.println("");
    }
} 