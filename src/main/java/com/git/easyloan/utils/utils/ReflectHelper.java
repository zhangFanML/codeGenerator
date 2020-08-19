package com.git.easyloan.utils.utils;

import java.lang.reflect.Field;

public class ReflectHelper {

    public ReflectHelper() {
    }

    public static Field getFieldByFieldName(Object obj, String fieldName) {
        Class superClass = obj.getClass();

        while(superClass != Object.class) {
            try {
                return superClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException var4) {
                superClass = superClass.getSuperclass();
            }
        }

        return null;
    }

    public static Object getValueByFieldName(Object obj, String fieldName) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field field = getFieldByFieldName(obj, fieldName);
        Object value = null;
        if (field != null) {
            if (field.isAccessible()) {
                value = field.get(obj);
            } else {
                field.setAccessible(true);
                value = field.get(obj);
                field.setAccessible(false);
            }
        }

        return value;
    }

    public static void setValueByFieldName(Object obj, String fieldName, Object value) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field field = obj.getClass().getDeclaredField(fieldName);
        if (field.isAccessible()) {
            field.set(obj, value);
        } else {
            field.setAccessible(true);
            field.set(obj, value);
            field.setAccessible(false);
        }

    }
}
