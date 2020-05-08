package ru.yurima.deepclone;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CopyUtils {
    public static <T> T deepCopy(final T object) {
        try {
            T copy = createNewInstance(object);
            return copy;

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static <T> T createNewInstance(T object) throws IllegalAccessException,
            InvocationTargetException, InstantiationException {
        Constructor<T> constructor = (Constructor<T>)
                object.getClass().getDeclaredConstructors()[0];
        List<Object> params = getParamsList(constructor);
        T instance = constructor.newInstance(params.toArray());
        return instance;
    }

    private static List<Object>  getParamsList(Constructor constructor) {
        List<Object> params = new ArrayList<>();
        for (Class type : constructor.getParameterTypes()) {
            if (type.isPrimitive()) {
                if (type == boolean.class) {
                    params.add(false);
                } else {
                    params.add(0);
                }
            } else {
                params.add(null);
            }
        }
        return params;
    }

}
