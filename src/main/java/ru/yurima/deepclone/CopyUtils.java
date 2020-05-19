package ru.yurima.deepclone;

import java.lang.reflect.*;
import java.util.*;

public class CopyUtils {

    private static final Map<Object, Object> alreadyCopiedObjects = new HashMap<>();

    public static <T> T deepCopy(final T object){
        alreadyCopiedObjects.clear();
        return copyObject(object);
    }

    private static <T> T copyObject(final T object) {
        try {
            // Check on null
            if (object == null) return null;

            // Check if the object is already copied
            if (alreadyCopiedObjects.keySet().contains(object)) {
                return (T) alreadyCopiedObjects.get(object);
            }

            //Return primitive and immutable types
            if (object.getClass().isPrimitive() || object.getClass() == String.class || object instanceof Number) {
                return object;
            }

            // Handle Arrays
            if (object.getClass().isArray())    { return copyArray(object); }

            //Handle collections and maps
            if (object instanceof List)         { return copyList(object);  }
            if (object instanceof Queue)        { return copyQueue(object); }
            if (object instanceof Set)          { return copySet(object);   }
            if (object instanceof Map)          { return copyMap(object);   }

            // Handle objects
            T copy = createNewInstance(object);
            copyFields(object, copy);
            return copy;

        } catch (IllegalAccessException | InstantiationException | InvocationTargetException |
                NoSuchMethodException illegalAccessException) {
            illegalAccessException.printStackTrace();
        }

        return null;
    }

    private static <T> T copyQueue(T object) throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException {
        Queue orig = (Queue) object;
        Queue result = (Queue) object.getClass().getDeclaredConstructor().newInstance();
        alreadyCopiedObjects.put(object, result);
        for (Object o : orig) {
            result.add(CopyUtils.copyObject(o));
        }
        return (T) result;
    }

    private static <T> T copyMap(T object) throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException {
        Map orig = (Map) object;
        Map result = (Map) object.getClass().getDeclaredConstructor().newInstance();
        alreadyCopiedObjects.put(object, result);
        for (Object key : orig.keySet()) {
            Object value = orig.get(key);
            result.put(CopyUtils.copyObject(key), CopyUtils.copyObject(value));
        }
        return (T) result;
    }

    private static <T> T copySet(T object) throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException {
        Set orig = (Set) object;
        Set result = (Set) object.getClass().getDeclaredConstructor().newInstance();
        alreadyCopiedObjects.put(object, result);
        for (Object o : orig) {
            result.add(CopyUtils.copyObject(o));
        }
        return (T) result;
    }

    private static <T> T copyList(Object object) throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException {
        List orig = (List) object;
        List result = null;
        // special case for non-modified ArrayList
        if (object.getClass().getName().equals("java.util.Arrays$ArrayList")) {
            result = (List) Arrays.asList(orig.toArray());
            alreadyCopiedObjects.put(object, result);
            for (int i = 0; i < orig.size(); i++) {
                result.set(i, CopyUtils.copyObject(orig.get(i)));
            }
            return (T) result;
        }

        result = (List) object.getClass().getDeclaredConstructor().newInstance();
        alreadyCopiedObjects.put(object, result);
        for (Object o : orig) {
            result.add(CopyUtils.copyObject(o));
        }
        return (T) result;
    }

    private static <T> T copyArray(Object object) {
        Class type = object.getClass().getComponentType();
        Object result = Array.newInstance(type, Array.getLength(object));
        alreadyCopiedObjects.put(object, result);
        int length = Array.getLength(object);
        if (type.isPrimitive()) {
            System.arraycopy(object, 0, result, 0, length);
        } else {
            for (int i = 0; i < length; i++) {
                Array.set(result, i, CopyUtils.copyObject(Array.get(object, i)));
            }
        }
        return (T) result;
    }

    private static <T> void copyFields(final T  orig, T copy) throws IllegalAccessException {
        Field[] fields = orig.getClass().getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            f.set(copy, CopyUtils.copyObject(f.get(orig)));
        }
    }

    private static <T> T createNewInstance(T object)  {
        Constructor<T>[] constructors = (Constructor<T>[]) object.getClass().getDeclaredConstructors();
        T instance = null;
        for (int i = 0; i < constructors.length && instance == null; i++) {
            Constructor<T> constructor = constructors[i];
            List<Object> params = getParamsList(constructor);
            try {
                instance = constructor.newInstance(params.toArray());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        alreadyCopiedObjects.put(object, instance);
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
