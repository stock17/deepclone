package ru.yurima.deepclone;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class CopyUtils {
    public static <T> T deepCopy(final T object) {

        try {
            T copy = createNewInstance(object);
            copyFields(object, copy);
            return copy;

        } catch (IllegalAccessException illegalAccessException) {
            illegalAccessException.printStackTrace();
        } catch (InstantiationException instantiationException) {
            instantiationException.printStackTrace();
        } catch (InvocationTargetException invocationTargetException) {
            invocationTargetException.printStackTrace();
        }

        return null;
    }

    private static <T> void copyFields(final T  orig, T copy) throws IllegalAccessException {
        Field[] fields = orig.getClass().getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            if (f.getType().isPrimitive() || f.getType() == String.class) {
                Object value = f.get(orig);
                f.set(copy, value);
            } else {
                //TODO for objects
                Object value = f.get(orig);
                f.set(copy, value);
            }
        }
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
