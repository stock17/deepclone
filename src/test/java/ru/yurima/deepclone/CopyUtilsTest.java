package ru.yurima.deepclone;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class CopyUtilsTest {

    @Test
    public void deepCopy() {
        Man man = new Man("John", 20, Arrays.asList("Martian"));
        Man copy = CopyUtils.deepCopy(man);
        System.out.printf("Original Name = %s, age = %d, books = %s\n", man.getName(), man.getAge(),
                man.getFavoriteBooks().toString());
        System.out.printf("Copy Name = %s, age = %d, books = %s\n", copy.getName(), copy.getAge(),
                copy.getFavoriteBooks()!= null ? copy.getFavoriteBooks().toString() : "none");
        assert(man != copy);

    }

    @Test
    public void PromitiveCopy(){
        int a = 65535;
        int b = CopyUtils.deepCopy(a);
        assert (a == b);

        double c = 0.12345;
        double d = CopyUtils.deepCopy(c);
        assert (c == d);
    }

    @Test
    public void NumberCopy(){
        Integer a = 65535;
        Integer b = CopyUtils.deepCopy(a);
        assert (a.equals(b));
        assert (a != b);
    }

    @Test
    public void testPrimitiveCopyArray(){
        int[] arr = new int[]{1,2,3,4,5};
        int[] copy =  CopyUtils.deepCopy(arr);
        Arrays.stream(arr).forEach(System.out::print);
        Arrays.stream(copy).forEach(System.out::print);
        assert (Arrays.equals(arr,copy));
        assert (arr != copy);
    }

    @Test
    public void testObjectCopyArray(){
        String[] arr = new String[]{"1","2","3","4","5"};
        String[] copy =  CopyUtils.deepCopy(arr);
        Arrays.stream(arr).forEach(System.out::print);
        Arrays.stream(copy).forEach(System.out::print);
        assert (Arrays.equals(arr,copy));
        assert (arr != copy);

        Integer[] arr1 = new Integer[]{1,2,3,4,5};
        Integer[] copy1 =  CopyUtils.deepCopy(arr1);

        Arrays.stream(arr).forEach(System.out::print);
        Arrays.stream(copy).forEach(System.out::print);
        assert (Arrays.equals(arr1,copy1));
        assert (arr1 != copy1);
    }

    @Test
    public void testListCopy(){
        List<Integer> ints = new ArrayList<>(); ints.add(65535);
        List<Integer> copy = CopyUtils.deepCopy(ints);

        assert(ints != copy);
        assert (ints.get(0) != copy.get(0));
        assert(ints.get(0).equals(copy.get(0)));

    }
}