package com.damaohongtu.orderquery.util;

import groovy.lang.GroovyClassLoader;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Method;

/**
 * @author: 大袤宏图
 * Description: 分库分表工具类
 */

public class ShardingUtil {

    /**
     * 通过Groovy动态代码获取分库信息
     * @param script 分库分表规则Groovy代码
     * @param serialNo 流水号
     * @return 分库Index
     * @throws Exception
     */
    public static int parseDbIndex(String script, String serialNo) {
        int res = 0;
        if(StringUtils.isBlank(script)){
            return res;
        }
        try {
            Class clazz = new GroovyClassLoader().parseClass(script);
            Method run = clazz.getMethod("parseDbIndex", String.class);
            Object object = clazz.newInstance();
            SpringUtil.autowireBean(object);
            res = (int) run.invoke(object, serialNo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    /**
     * 通过Groovy动态代码获取分库信息
     * @param script
     * @param serialNo
     * @return
     * @throws Exception
     */
    public static String parseTablePostfix(String script, String serialNo) {
        String res = StringUtils.EMPTY;
        if(StringUtils.isBlank(script)){
            return res;
        }
        try {
            Class clazz = new GroovyClassLoader().parseClass(script);
            Method run = clazz.getMethod("parseTablePostfix", String.class);
            Object object = clazz.newInstance();
            SpringUtil.autowireBean(object);
            res = (String) run.invoke(object, serialNo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return res;
    }


}
