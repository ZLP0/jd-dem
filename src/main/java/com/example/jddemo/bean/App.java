package com.example.jddemo.bean;

import com.example.jddemo.copy.Person;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/**
 *        <dependency>
 *             <groupId>commons-beanutils</groupId>
 *             <artifactId>commons-beanutils</artifactId>
 *             <version>1.9.4</version>
 *         </dependency>
 *
 *         Map属性 转到Bean对象
 */
public class App {

    public static void main(String[] args) {

        Person person = new Person();
        HashMap<String, Object> map = new HashMap<>();

        map.put("name","张三");
        try {
            BeanUtils.populate(person,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.println(person);
    }
}
