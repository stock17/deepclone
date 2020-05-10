package ru.yurima.deepclone;

import org.junit.Test;

import java.util.Arrays;

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
    }
}