package top.androider.fontscale;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectUtils {


    public static Object invokeMethod(Object instance, String methodName,
                                      Class<?>[] classes, Object[] arguments) throws SecurityException,
            NoSuchMethodException, IllegalArgumentException,
            IllegalAccessException, InvocationTargetException {
        Method method = instance.getClass().getDeclaredMethod(methodName,
                classes);
        method.setAccessible(true);
        return method.invoke(instance, arguments);
    }
}
