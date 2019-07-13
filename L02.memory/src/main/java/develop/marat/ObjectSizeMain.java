package develop.marat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class ObjectSizeMain {

    public static void main(String... args)
            throws InterruptedException
    {
        int minSizeContainer = 10;
        int middleSizeContainer = 100;
        int maxSizeContainer = 1000;

        //empty string
        ObjectSize stringEmptyObjectSize = new ObjectSize(new StringFactory());
        System.out.println("Empty String object = " + stringEmptyObjectSize.calculateAndGetSize());

        //empty containers
        ObjectSize arrayListEmptyObjectSize = new ObjectSize(new ContainerFactory(ArrayList.class));
        System.out.println("Empty ArrayList object = " + arrayListEmptyObjectSize.calculateAndGetSize());
        ObjectSize linkedListEmptyObjectSize = new ObjectSize(new ContainerFactory(LinkedList.class));
        System.out.println("Empty LinkedList object = " + linkedListEmptyObjectSize.calculateAndGetSize());
        ObjectSize hashSetEmptyObjectSize = new ObjectSize(new ContainerFactory(HashSet.class));
        System.out.println("Empty HashSet object = " + hashSetEmptyObjectSize.calculateAndGetSize());

        //String of different sizes
        ObjectSize minStringObjectSize = new ObjectSize(new StringFactory(new char[minSizeContainer]));
        System.out.println("String object ("+minSizeContainer+") = " + minStringObjectSize.calculateAndGetSize());
        ObjectSize middleStringObjectSize = new ObjectSize(new StringFactory(new char[middleSizeContainer]));
        System.out.println("String object ("+middleSizeContainer+") = " + middleStringObjectSize.calculateAndGetSize());
        ObjectSize maxStringObjectSize = new ObjectSize(new StringFactory(new char[maxSizeContainer]));
        System.out.println("String object ("+maxSizeContainer+") = " + maxStringObjectSize.calculateAndGetSize());

        //ArrayList of different sizes
        ObjectSize minArrayListObjectSize = new ObjectSize(new ContainerFactory(ArrayList.class, minSizeContainer));
        System.out.println("ArrayList object ("+minSizeContainer+") = " + minArrayListObjectSize.calculateAndGetSize());
        ObjectSize middleArrayListObjectSize = new ObjectSize(new ContainerFactory(ArrayList.class, middleSizeContainer));
        System.out.println("ArrayList object ("+middleSizeContainer+") = " + middleArrayListObjectSize.calculateAndGetSize());
        ObjectSize maxArrayListObjectSize = new ObjectSize(new ContainerFactory(ArrayList.class, maxSizeContainer));
        System.out.println("ArrayList object ("+maxSizeContainer+") = " + maxArrayListObjectSize.calculateAndGetSize());

        //HashSet of different sizes
        ObjectSize minHashSetObjectSize = new ObjectSize(new ContainerFactory(HashSet.class, minSizeContainer));
        System.out.println("HashSet object ("+minSizeContainer+") = " + minHashSetObjectSize.calculateAndGetSize());
        ObjectSize middleHashSetObjectSize= new ObjectSize(new ContainerFactory(HashSet.class, middleSizeContainer));
        System.out.println("HashSet object ("+middleSizeContainer+") = " + middleHashSetObjectSize.calculateAndGetSize());
        ObjectSize maxHashSetObjectSize = new ObjectSize(new ContainerFactory(HashSet.class, maxSizeContainer));
        System.out.println("HashSet object ("+maxSizeContainer+") = " + maxHashSetObjectSize.calculateAndGetSize());
    }

}
