package com.test.jacoco;

import covergae.CMethodsParser;
import java.lang.reflect.*;

/**
 * @description:
 * @author: charlyne
 * @time: 2019/6/20 8:34 PM
 */
public class TestClass {
    public static void modify(Object cMethodsParser, String fieldName, Object newFieldValue) throws Exception {
        Field field = cMethodsParser.getClass().getDeclaredField(fieldName);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true); //Field 的 modifiers 是私有的
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

        if(!field.isAccessible()) {
            field.setAccessible(true);
        }

        field.set(cMethodsParser, newFieldValue);
    }
    public static void main(String[] args) throws Exception {
        Class clazz=Class.forName("covergae.CMethodsParser");
        CMethodsParser cMethodsParser=(CMethodsParser)clazz.newInstance();
       TestClass.modify(cMethodsParser,"strfield",new String("hhhhh"));
        Field[] fields=clazz.getDeclaredFields();
        for(Field field:fields){
            field.setAccessible(true);
           System.out.println(field.get(cMethodsParser));
            System.out.println();
        }






    }
}