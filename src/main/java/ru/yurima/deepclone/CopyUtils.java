package ru.yurima.deepclone;

import java.lang.reflect.InvocationTargetException;

public class CopyUtils {
    public static <T> T deepCopy(T object) {
        try {
            return (T) object.getClass().getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
