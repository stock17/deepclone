package ru.yurima.deepclone;

import org.junit.Test;

import java.util.Arrays;

public class CopyUtilsTest {

    @Test
    public void deepCopy() {
        Man man = new Man("John", 20, Arrays.asList("Martian"));
        Man copy = CopyUtils.deepCopy(man);
        assert(man != copy);

    }
}