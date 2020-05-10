package ru.yurima.deepclone;

import java.lang.reflect.*;
import java.util.*;

public class CopyUtils {
    public static <T> T deepCopy(final T object) {
        try {
            if (object.getClass().isPrimitive() || object.getClass() == String.class) {
                return object;
            }

            if (object instanceof Number)
                return (T) object.getClass().getDeclaredConstructor(String.class).newInstance(object.toString());

            if (object.getClass().isArray()) {
                return (T) copyArray(object);
            }

            if (object instanceof List) {
                List orig = (List) object;
                List result = null;
                // special case for non-modified ArrayList
                if (object.getClass().getName().equals("java.util.Arrays$ArrayList")) {
                    return (T) Arrays.asList(orig.toArray());
                }

                result = (List) object.getClass().getDeclaredConstructor().newInstance();

                for (Object o : orig) {
                    result.add(CopyUtils.deepCopy(o));
                }
                return (T) result;
            }

            if (object instanceof Set) {
                Set orig = (Set) object;
                Set result = (Set) object.getClass().getDeclaredConstructor().newInstance();
                for (Object o : orig) {
                    result.add(CopyUtils.deepCopy(o));
                }
                return (T) result;
            }

            if (object instanceof Map) {
                Map orig = (Map) object;
                Map result = (Map) object.getClass().getDeclaredConstructor().newInstance();
                for (Object key : orig.keySet()) {
                    Object value = orig.get(key);
                    result.put(CopyUtils.deepCopy(key), CopyUtils.deepCopy(value));
                }
                return (T) result;
            }




            T copy = createNewInstance(object);
            copyFields(object, copy);
            return copy;

        } catch (IllegalAccessException illegalAccessException) {
            illegalAccessException.printStackTrace();
        } catch (InstantiationException instantiationException) {
            instantiationException.printStackTrace();
        } catch (InvocationTargetException invocationTargetException) {
            invocationTargetException.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static Object copyArray(Object object) {
        Class type = object.getClass().getComponentType();
        Object result = Array.newInstance(type, Array.getLength(object));
        int length = Array.getLength(object);
        if (type.isPrimitive()) {
            System.arraycopy(object, 0, result, 0, length);
        } else {
            for (int i = 0; i < length; i++) {
                    Array.set(result, i, CopyUtils.deepCopy(Array.get(object, i)));
            }
        }

        return result;
    }

    private static <T> void copyFields(final T  orig, T copy) throws IllegalAccessException {
        Field[] fields = orig.getClass().getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            f.set(copy, CopyUtils.deepCopy(f.get(orig)));
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
