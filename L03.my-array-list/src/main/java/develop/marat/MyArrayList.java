package develop.marat;


import java.util.*;

public class MyArrayList<T> implements List<T> {

    private static final Object[] EMPTY_ELEMENTDATA = {};
    private static final int DEFAULT_CAPACITY = 10;
    private static final double INCREASE_CAPACITY_RATE = 0.3;

    private Object[] elementData;
    private int size = 0;

    public MyArrayList(){
        elementData = EMPTY_ELEMENTDATA;
    }

    public MyArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            this.elementData = EMPTY_ELEMENTDATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
    }

    private MyArrayList(MyArrayList<T> originalMyArrayList, int fromIndex, int toIndex){
        this.elementData = new Object[ originalMyArrayList.elementData.length ];
        size = toIndex - fromIndex;
        System.arraycopy(originalMyArrayList.elementData, fromIndex, this.elementData,0, size);
    }



    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    public Iterator<T> iterator() {
        return new Itr();
    }

    public Object[] toArray() {
        return Arrays.copyOf(elementData, size);
    }

    @SuppressWarnings("unchecked")
    public <T1> T1[] toArray(T1[] a) {
        if (a.length < size){
            // Make a new array of a's runtime type, but my contents:
            return (T1[]) Arrays.copyOf(elementData, size, a.getClass());
        }

        System.arraycopy(elementData, 0, a, 0, size);
        if (a.length > size){
            a[size] = null;
        }

        return a;
    }

    public boolean add(T t) {
        if (size == elementData.length){
            elementData = grow(elementData);
        }

        elementData[size] = t;
        size++;
        return true;
    }


    private Object[] grow(final Object[] originalData) {
        return Arrays.copyOf(originalData, newCapacity());
    }

    private int newCapacity() {
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + (int)(oldCapacity * INCREASE_CAPACITY_RATE);
        return Math.max(DEFAULT_CAPACITY, newCapacity);
    }

    public boolean remove(Object o) {
        final Object[] es = elementData;
        int findIndex = indexOf(o);

        if(findIndex >= 0){
            fastRemove(es, findIndex);
            return true;
        }
        else{
            return false;
        }
    }

    private void fastRemove(Object[] es, int i) {
        final int newSize;
        if ((newSize = size - 1) > i){
            System.arraycopy(es, i + 1, es, i, newSize - i);
        }

        es[size = newSize] = null;
    }

    public boolean containsAll(Collection<?> c) {
        for (Object e : c){
            if (!contains(e)){
                return false;
            }
        }
        return true;
    }

    public boolean addAll(Collection<? extends T> c) {
        boolean modified = false;
        for (T e : c){
            if (add(e)){
                modified = true;
            }
        }

        return modified;
    }

    public boolean addAll(int index, Collection<? extends T> c) {
        rangeCheckForAdd(index);
        boolean modified = false;
        for (T e : c) {
            add(index++, e);
            modified = true;
        }
        return modified;
    }

    public boolean removeAll(Collection<?> c) {
        return batchRemove(c, false);
    }

    public boolean retainAll(Collection<?> c) {
        return batchRemove(c, true);
    }

    private boolean batchRemove(Collection<?> c, boolean complement) {
        Objects.requireNonNull(c);

        //Save the indices of elements that remain in the resulting array
        final Object[] es = elementData;
        int[] saveElementsIndex = new int[size];
        int saveElementsArraySize = 0;
        for(int i = 0; i < size; i++){
            if(c.contains(es[i]) == complement){
                saveElementsIndex[ saveElementsArraySize++ ] = i;
            }
        }

        //Copy the relevant elements into the original array of elements.
        for(int i = 0; i < saveElementsArraySize; i++){
            int saveElementIndex = saveElementsIndex[i];
            Object saveElement = es[saveElementIndex];
            es[i] = saveElement;
        }

        for(int i = saveElementsArraySize; i<size; i++){
            es[i] = null;
        }
        size = saveElementsArraySize;
        return true;
    }

    public void clear() {
        final Object[] es = elementData;
        for (int i = 0; i < size; i++){
            es[i] = null;
        }
        size = 0;
    }

    public T get(int index) {
        Objects.checkIndex(index, size);
        return elementData(index);
    }

    @SuppressWarnings("unchecked")
    T elementData(int index) {
        return (T) elementData[index];
    }

    public T set(int index, T element) {
        Objects.checkIndex(index, size);
        T oldValue = elementData(index);
        elementData[index] = element;
        return oldValue;
    }

    public void add(int index, T element) {
        rangeCheckForAdd(index);
        final int s;
        if ((s = size) == elementData.length){
            elementData = grow(elementData);
        }
        System.arraycopy(elementData, index, elementData,index + 1, s - index);
        elementData[index] = element;
        size = s + 1;
    }

    private void rangeCheckForAdd(int index) {
        if (index > size || index < 0){
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    private String outOfBoundsMsg(int index) {
        return "Index: "+index+", Size: "+size;
    }

    public T remove(int index) {
        Objects.checkIndex(index, size);
        T oldValue = elementData(index);
        fastRemove(elementData, index);
        return oldValue;
    }

    public int indexOf(Object o) {
        final Object[] es = elementData;
        final int size = this.size;
        final int start = 0;

        if (o == null) {
            for (int i = start; i < size; i++) {
                if (es[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = start; i < size; i++) {
                if (o.equals(es[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    public int lastIndexOf(Object o) {
        Object[] es = elementData;
        final int size = this.size;
        final int start = 0;

        if (o == null) {
            for (int i = size - 1; i >= start; i--) {
                if (es[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = size - 1; i >= start; i--) {
                if (o.equals(es[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    public List<T> subList(int fromIndex, int toIndex) {
        subListRangeCheck(fromIndex, toIndex, size);
        return new MyArrayList<T>(this, fromIndex, toIndex);
    }

    static void subListRangeCheck(int fromIndex, int toIndex, int size) {
        if (fromIndex < 0){
            throw new IndexOutOfBoundsException("fromIndex = " + fromIndex);
        }

        if (toIndex > size){
            throw new IndexOutOfBoundsException("toIndex = " + toIndex);
        }

        if (fromIndex > toIndex){
            throw new IllegalArgumentException("fromIndex(" + fromIndex + ") > toIndex(" + toIndex + ")");
        }
    }

    public ListIterator<T> listIterator() {
        return new ListItr(0);
    }

    public ListIterator<T> listIterator(int index) {
        rangeCheckForAdd(index);
        return new ListItr(index);
    }

    private class Itr implements Iterator<T> {
        int cursor;       // index of next element to return
        int lastRet = -1; // index of last element returned; -1 if no such

        // prevent creating a synthetic constructor
        Itr() {}

        public boolean hasNext() {
            return cursor != size;
        }

        @SuppressWarnings("unchecked")
        public T next() {
            int i = cursor;
            if (i >= size){
                throw new NoSuchElementException();
            }

            Object[] elementData = MyArrayList.this.elementData;
            if (i >= elementData.length){
                throw new ConcurrentModificationException();
            }
            cursor = i + 1;
            return (T) elementData[lastRet = i];
        }

        public void remove() {
            if (lastRet < 0){
                throw new IllegalStateException();
            }

            try {
                MyArrayList.this.remove(lastRet);
                cursor = lastRet;
                lastRet = -1;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }

    private class ListItr extends Itr implements ListIterator<T> {
        ListItr(int index) {
            super();
            cursor = index;
        }

        public boolean hasPrevious() {
            return cursor != 0;
        }

        public int nextIndex() {
            return cursor;
        }

        public int previousIndex() {
            return cursor - 1;
        }

        @SuppressWarnings("unchecked")
        public T previous() {
            int i = cursor - 1;
            if (i < 0){
                throw new NoSuchElementException();
            }

            Object[] elementData = MyArrayList.this.elementData;
            if (i >= elementData.length){
                throw new ConcurrentModificationException();
            }
            cursor = i;
            return (T) elementData[lastRet = i];
        }

        public void set(T e) {
            if (lastRet < 0){
                throw new IllegalStateException();
            }

            try {
                MyArrayList.this.set(lastRet, e);
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }

        public void add(T e) {
            try {
                int i = cursor;
                MyArrayList.this.add(i, e);
                cursor = i + 1;
                lastRet = -1;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
        }
    }

    public String toString() {
        Iterator<T> it = iterator();
        if (! it.hasNext())
            return "[]";

        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (;;) {
            T e = it.next();
            sb.append(e == this ? "(this Collection)" : e);
            if (! it.hasNext())
                return sb.append(']').toString();
            sb.append(',').append(' ');
        }
    }
}
