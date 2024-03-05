package com.dcx.spring6.bean.impl;

import com.dcx.spring6.annotation.Bean;
import com.dcx.spring6.annotation.DI;
import com.dcx.spring6.bean.ApplicationContext;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author dawn
 * @ @date 2024/3/4 18:42
 */
public class ApplicationContextImpl implements ApplicationContext {
    //map放bean对象
     private Map<Class,Object> beanFactory = new HashMap<>();
    private static String rootPath;
    @Override
    public Object getBean(Class clazz) {
        return beanFactory.get(clazz);
    }
    //设置扫描规则
    //创建有参数构造，传递包路径
    public  ApplicationContextImpl(String basePackage){
         //1 把路径点替换成斜杠 com.dcx  com\dcx
        //2 获取包的绝对路径

        String packagePath = basePackage.replaceAll("\\.", "\\\\");
        try {
            Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(packagePath);
            while(urls.hasMoreElements()){
                URL url = urls.nextElement();
                String path = URLDecoder.decode(url.getFile(), "utf-8");
                //字符串截取
                rootPath = path.substring(0, path.length() - packagePath.length());
                //3  扫描包
                loadBean(new File(path));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //DI
        loadDi();
    }

    //3  扫描包
    public  void loadBean(File file) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        //1当前是不是文件夹
        if (file.isDirectory()) {
        //2获取文件夹里面的内容
            File[] childrenFiles = file.listFiles();
        //3判断文件夹空直接返回
            if(childrenFiles == null || childrenFiles.length == 0) {
                return;
            }
        //4如果文件夹不是空，则遍历所有内容
            for (File child:childrenFiles) {
                //4.1遍历得到每个File对象，是文件夹，递归
                if(child.isDirectory()){
                    loadBean(child);
                }else{
                    //4.2不是文件夹文件
                    //4.2.1得到包路径和类名称部分
                    String pathWithClass = child.getAbsolutePath().substring(rootPath.length() - 1);
                    //4.2.2判断是不是.class
                    if(pathWithClass.contains(".class")){
                        //4.2.2.1是的话把\换成.，把class去掉    com.dcx.UserDao
                        String allName = pathWithClass.replaceAll("\\\\", ".")
                                .replace(".class", "");
                     
                        //4.2.3获取类class
                        Class<?> aClass = Class.forName(allName);
                        //4.2.4不是接口
                        if(!aClass.isInterface()){
                            //4.2.5哦按段类上面有没有注解bean，有就实例化
                            Bean annotation = aClass.getAnnotation(Bean.class);
                            if(annotation != null){
                                Object instance = aClass.getConstructor().newInstance();
                                //4.2.5将实例化放进map.如果类是实现了某个接口则以接口为key
                                if(aClass.getInterfaces().length > 0){
                                    beanFactory.put(aClass.getInterfaces()[0],instance);
                                }else{
                                    beanFactory.put(aClass,instance);
                                }
                            }
                        }

                        //map进factory
                    }
                }

            }

        }
    }
    //属性注入
    private void loadDi(){
        //1 实例化对象都在beanFactory的map集合中
        //遍历Map集合
        Set<Map.Entry<Class, Object>> entries = beanFactory.entrySet();
        for (Map.Entry<Class, Object> entry : entries) {
            Object obj = entry.getValue();
            Class<?> clazz = obj.getClass();
            Field[] declaredFields = clazz.getDeclaredFields();
            //3 遍历属性是否有@DI
            for (Field field:declaredFields) {
                DI di = field.getAnnotation(DI.class);
                if(di != null){
                    //如果有私有属性，设置可以设置
                    field.setAccessible(true);
                    try {
                        //获取属性类型后从map中拿取对象并设置
                        field.set(obj,beanFactory.get(field.getType()));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

        }
        //2 获取map集合的每个对象及其属性

        //3.1 有的话就在从map集合中拿取相应的对象
    }
}
