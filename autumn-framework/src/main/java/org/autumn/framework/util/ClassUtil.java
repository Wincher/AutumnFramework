package org.autumn.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * class util
 * @author wincher
 * @since 2018/9/2
 * <p> org.autumn.framework.util <p>
 */
public class ClassUtil {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtil.class);
  
  public static final String CLASS_ENDS = ".class";
  /**
   * get class loader
   * @return
   */
  public static ClassLoader getClassLoader() {
    return Thread.currentThread().getContextClassLoader();
  }
  
  /**
   * load class
   * @param className
   * @param isInitialized
   * @return
   */
  public static Class<?> loadClass(String className, boolean isInitialized) {
    Class<?> cls;
    try {
      cls = Class.forName(className, isInitialized, getClassLoader());
    } catch (ClassNotFoundException e) {
      LOGGER.error("load class failure", e);
      throw new RuntimeException(e);
    }
    return cls;
  }
  
  /**
   * load class
   * @param className
   * @return
   */
  public static Class<?> loadClass(String className) {
    Class<?> cls;
    try {
      cls = loadClass(className, false);
    } catch (Exception e) {
      LOGGER.error("load class failure", e);
      throw new RuntimeException(e);
    }
    return cls;
  }
  
  /**
   * get all  classes in package
   * @param packageName
   * @return
   */
  public static Set<Class<?>> getClassSet(String packageName) {
    Set<Class<?>> classSet = new HashSet<>();
    try {
      //获取包名下面所有路径
      Enumeration<URL> urls = getClassLoader().getResources(packageName.replaceAll(".", "/"));
      while (urls.hasMoreElements()) {
        URL url = urls.nextElement();
        if (null != url) {
          String protocol = url.getProtocol();
          //根据路径协议判断是file还是jar
          if ("file".equals(protocol)) {
            String packagePath = url.getPath().replaceAll("%20", "");
            addClass(classSet, packagePath, packageName);
          } else if ("jar".equals(protocol)) {
            JarURLConnection jarURLConnection = (JarURLConnection)url.openConnection();
            if (null != jarURLConnection) {
              JarFile jarFile = jarURLConnection.getJarFile();
              if (null != jarFile) {
                Enumeration<JarEntry> jarEntries = jarFile.entries();
                while (jarEntries.hasMoreElements()) {
                  JarEntry jarEntry = jarEntries.nextElement();
                  String jarEntryName = jarEntry.getName();
                  if (jarEntryName.endsWith(CLASS_ENDS)) {
                    String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replaceAll("/", ".");
                    doAddClass(classSet, className);
                  }
                }
              }
            }
          }
        }
      }
    } catch (IOException e) {
      LOGGER.error("get class set failure", e);
      throw new RuntimeException(e);
    }
    return classSet;
  }
  
  /**
   *
   * @param classSet
   * @param packagePath
   * @param packageName
   */
  private static void addClass(Set<Class<?>> classSet, String packagePath, String packageName) {
    //过滤出packagePath下文件夹和以.class结尾的文件
    File[] files = new File(packagePath).listFiles(file -> (file.isFile() && file.getName().endsWith(CLASS_ENDS) || file.isDirectory()));
    Arrays.stream(files).forEach(file -> {
      String fileName = file.getName();
      if (file.isFile()) {
        String className = fileName.substring(0, fileName.lastIndexOf("."));
        if (StringUtil.isNotEmpty(packageName)) {
          className = packageName + "." + className;
          doAddClass(classSet, className);
        } else {
          //如果是文件夹路径,构建子文件夹路径
          String subPackagePath = fileName;
          if (StringUtil.isNotEmpty(packagePath)) {
            subPackagePath = packagePath + "/" + subPackagePath;
          }
          String subPackageName = fileName;
          if (StringUtil.isNotEmpty(packageName)) {
            subPackageName = packageName + "." + subPackageName;
          }
          //递归
          addClass(classSet, subPackagePath, subPackageName);
        }
      }
    });
  }
  
  /**
   * 添加class到classSet
   * @param classSet
   * @param className
   */
  private static void doAddClass(Set<Class<?>> classSet, String className) {
    Class<?> cls = loadClass(className, false);
    classSet.add(cls);
  }
}
