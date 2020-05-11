package ru.yurima.deepclone;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class CopyUtilsTest {

    private Man man;

    @Before
    public void init(){
        man = new Man("John", 20, Arrays.asList("Martian", "Back to Future"));
        man.addFriend(new Man("Bill", 30, Arrays.asList("Stranger in a Strange Land", "Invisible Man")));
        // I'm a friend of mine (add recursive reference)
        man.addFriend(man);
    }

    @Test
    public void deepCopy() {

        Man copy = CopyUtils.deepCopy(man);
        System.out.printf("Original Name = %s, age = %d, books = %s\n", man.getName(), man.getAge(),
                man.getFavoriteBooks().toString());
        System.out.printf("Copy Name = %s, age = %d, books = %s\n", copy.getName(), copy.getAge(),
                copy.getFavoriteBooks()!= null ? copy.getFavoriteBooks().toString() : "none");
        assert(man != copy);

        assert(man.getName().equals(copy.getName()));
        copy.setName("Nikas");
        assert(!man.getName().equals(copy.getName()));

        assert(man.getAge() == copy.getAge());
        copy.setAge(30);
        assert(man.getAge() != copy.getAge());

        assert(man.getFavoriteBooks() != copy.getFavoriteBooks());
        assert(man.getFavoriteBooks().get(0).equals(copy.getFavoriteBooks().get(0)));
        copy.getFavoriteBooks().set(0, "Terra Nova");
        assert(!man.getFavoriteBooks().get(0).equals(copy.getFavoriteBooks().get(0)));

        assert (man != copy);
        assert (man == man.getFriends().get(1));
        assert (copy == copy.getFriends().get(1));

    }

    @Test
    public void PrimitiveCopy(){
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
        assert(ints.equals(copy));
        copy.set(0, 65536);
        assert(!ints.get(0).equals(copy.get(0)));

        List<String> strings = new ArrayList<>( Arrays.asList("Foo", "Bar"));
        List<String> copy1 = CopyUtils.deepCopy(strings);
        assert(strings != copy1);
        assert(strings.get(0).equals(copy1.get(0)));

        List<String> strings2 =  Arrays.asList("Foo", "Bar");
        List<String> copy2 = CopyUtils.deepCopy(strings2);
        assert(strings2 != copy2);
        assert(strings2.get(0).equals(copy2.get(0)));
    }

    @Test
    public void testSetCopy(){
        Set<Integer> set = new HashSet<>(); set.add(65535);
        Set<Integer> copy = CopyUtils.deepCopy(set);
        assert(set != copy);
        assert (set.equals(copy));

        Set<Man> set1 = new HashSet<>(); set1.add(man);
        Set<Man> copy1 = CopyUtils.deepCopy(set1);
        assert(set1.contains(man));
        assert(!copy1.contains(man));
    }

    @Test
    public void testMapCopy(){
        Map<Integer, Man> map = new HashMap<>(); map.put(1, man);
        Map<Integer, Man> copy = CopyUtils.deepCopy(map);
        assert(map != copy);
        assert (map.get(1)!= copy.get(1));
        assert (map.get(1).getName().equals(copy.get(1).getName()));
    }

    @Test
    public void testQueueCopy(){
        Queue<String> queue = new PriorityQueue<>(); queue.add("one"); queue.add("two");
        Queue<String> copy = CopyUtils.deepCopy(queue);
        assert(queue != copy);
        assert (Objects.equals(queue.peek(), copy.peek()));
    }
}