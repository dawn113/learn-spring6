package com.dcx.spring6.bean.impl;

import com.dcx.spring6.annotation.Bean;
import com.dcx.spring6.bean.ApplicationContext;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

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
}
