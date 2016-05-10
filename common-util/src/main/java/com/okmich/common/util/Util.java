/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.okmich.common.util;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author m-enudi
 */
public class Util {

    /**
     * loops through an entire hierarchy of a class object in search of all
     * field.
     *
     * @param Class the class object to search
     * @return Field[] - an array of Field objects
     */
    public static Field[] getInstanceVariables(Class cls) {
        List accum = new LinkedList();
        while (cls != null) {
            Field[] fields = cls.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                accum.add(fields[i]);
            }
            cls = cls.getSuperclass();
        }
        Field[] retvalue = new Field[accum.size()];
        return (Field[]) accum.toArray(retvalue);
    }

    /**
     * loops through an entire hierarchy of class object in search of a
     * particular field. returns null if the search is unsuccessful, else
     * returns the Field object.
     *
     * @param Class the class object to search
     * @param String - the field name
     * @return Field or null if the search is unsuccessful
     */
    public static Field getInstanceVariable(Class cls, String fieldName) {
        Field returnField = null;
        while (cls != null) {
            try {
                returnField = cls.getDeclaredField(fieldName);
                if (returnField != null) {
                    break;
                }
            } catch (NoSuchFieldException ex) {
            }
            cls = cls.getSuperclass();
        }
        return returnField;
    }

    /**
     * returns the value of an object's field.
     *
     * @param object
     * @param fieldName
     * @return Object
     * @throws java.lang.Exception like IllegalArgumentException or
     * IllegalAccessException
     */
    public static Object getFieldProperty(Object object, String fieldName) throws Exception {
        Object value = null;
        try {
            Field field = getInstanceVariable(object.getClass(), fieldName);
            field.setAccessible(true);
            value = field.get(object);
        } catch (IllegalArgumentException ex) {
            throw ex;
        } catch (IllegalAccessException ex) {
            throw ex;
        }
        return value;
    }

    /**
     * setting the member variable for a class t and returning the generic type.
     *
     * @param t - an instance of type T
     * @param fieldName
     * @param propValue
     * @return T
     * @throws java.lang.Exception if error occurs like IllegalArgumentException
     * or IllegalAccessException
     */
    public static <T> T setFieldProperty(T t, String fieldName, Object propValue) throws Exception {
        try {
            Field field = null;
            field = getInstanceVariable(t.getClass(), fieldName);
            field.setAccessible(true);
            if (field.getType().equals(String.class)) {
                field.set(t, propValue.toString());
            } else if (field.getType().equals(BigDecimal.class)) {
                field.set(t, new BigDecimal(propValue.toString()));
            } else if (field.getType().equals(Short.class)) {
                Short i = ((Number) Double.valueOf(propValue.toString())).shortValue();
                field.set(t, i);
            } else if (field.getType().equals(Integer.class) || field.getType().equals(int.class)) {
                int i = ((Number) Double.valueOf(propValue.toString())).intValue();
                field.set(t, i);
            } else {
                field.set(t, propValue);
            }
        } catch (IllegalArgumentException ex) {
            throw ex;
        } catch (IllegalAccessException ex) {
            throw ex;
        }
        return t;
    }
}
