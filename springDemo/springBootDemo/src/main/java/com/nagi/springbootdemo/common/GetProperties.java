package com.nagi.springbootdemo.common;

import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.Properties;

public class GetProperties {
    public static void getProperties(String path) throws Exception {
        Properties properties = new Properties();
        properties.load(new FileInputStream(path));
        Enumeration<?> enumeration = properties.propertyNames();
        while (enumeration.hasMoreElements()) {
            String key = (String) enumeration.nextElement();
            String value = properties.getProperty(key);
            System.out.println(key + " : " + value);
        }
    }
}
