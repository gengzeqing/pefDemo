package com.example.demo.utils;

import com.google.common.base.Throwables;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: tanxuhui
 * @create: 2019-05-31 18:17
 */
public class MapUtils {


    /**
     *  对传入的对象属性值存入map集合
     */
    public static Map<String,Object> object2Map(Object requestParameters) {

        Map<String, Object> map = new HashMap<>();
        try {
            // 获取f对象对应类中的所有属性域
            Field[] fields = requestParameters.getClass().getDeclaredFields();
            for (int i = 0, len = fields.length; i < len; i++) {
                String varName = fields[i].getName();
                // 获取原来的访问控制权限
                boolean accessFlag = fields[i].isAccessible();
                // 修改访问控制权限
                fields[i].setAccessible(true);
                Object o = fields[i].get(requestParameters);
                if (o != null) {
                    map.put(varName, o);
                }
                // 恢复访问控制权限
                fields[i].setAccessible(accessFlag);
            }
        } catch (IllegalAccessException  e) {
            Throwables.propagate(e);
        }
        return map;
    }


    /**
     *  对传入的对象进行数据清洗，将属性值为null和""的去掉，其他字段名和属性值存入map集合
     */
    public static Map<String,String> object2StringMap(Object requestParameters) {
        Map<String, String> map = new HashMap<>();
        try {
            // 获取f对象对应类中的所有属性域
            Field[] fields = requestParameters.getClass().getDeclaredFields();
            for (int i = 0, len = fields.length; i < len; i++) {
                // 获取原来的访问控制权限
                boolean accessFlag = fields[i].isAccessible();
                String varName = fields[i].getName();
                // 修改访问控制权限
                fields[i].setAccessible(true);
                Object o = fields[i].get(requestParameters);
                if (o != null && StrKit.isNotEmpty(o.toString().trim())) {
                    map.put(varName, o.toString().trim());
                }
                // 恢复访问控制权限
                fields[i].setAccessible(accessFlag);
            }
        } catch (IllegalAccessException e) {
            Throwables.propagate(e);
        }
        return map;
    }


}
