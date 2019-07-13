package develop.marat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MyArrayListMain {
    public static void main(String... args){
        MyArrayList<Integer> firstList = new MyArrayList<Integer>();
        for(int i=0; i< 10; i++){
            firstList.add(i);
        }

        System.out.println("firstList");
        System.out.println(firstList);

        Collections.addAll(firstList, 20, 21, 22, 23, 24, 25);
        System.out.println("firstList after Collections.addAll");
        System.out.println(firstList);

        MyArrayList<Integer> secondList = new MyArrayList<Integer>(20);
        secondList.addAll(Collections.nCopies(20, 0));
        System.out.println("secondList");
        System.out.println(secondList);

        Collections.copy(secondList, firstList);
        System.out.println("secondList after Collections.copy");
        System.out.println(secondList);

        Collections.shuffle(secondList);
        System.out.println("secondList after Collections.shuffle");
        System.out.println(secondList);

        Collections.sort(secondList);
        System.out.println("secondList after Collections.sort");
        System.out.println(secondList);
        secondList.clear();
        System.out.println("secondList after clear");
        System.out.println(secondList);
        System.out.println("secondList size after clear");
        System.out.println(secondList.size());

        ArrayList<Integer> checkArrayList = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4));
        System.out.println("Check ArrayList");
        System.out.println(checkArrayList);
        System.out.println(checkArrayList.subList(0, 2));

    }

}
