package helpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class ReflectionHelper {
    private static final Logger logger = LogManager.getLogger(ReflectionHelper.class.getName());

    public static Object createInstance(String className) {
        Object obj = null;
        try {
            obj = Class.forName(className).getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException |
                InstantiationException | IllegalAccessException | InvocationTargetException e) {
            logger.error(e.getMessage());
        }
        return obj;
    }

    public static void setFieldValue(Object object, String fieldName, String fieldValue) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);

            if (field.getType().equals(String.class)) {
                field.set(object, fieldValue);
            } else if (field.getType().equals(int.class)) {
                field.set(object, Integer.decode(fieldValue));
            } else if (field.getType().equals(float.class)) {
                field.set(object, Float.valueOf(fieldValue));
            } else if (field.getType().equals(double.class)) {
                field.set(object, Double.valueOf(fieldValue));
            } else if (field.getType().equals(boolean.class)) {
                field.set(object, Boolean.valueOf(fieldValue));
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            logger.error(e.getMessage());
        }
    }
}
