package org.autumn.framework.bean;

import org.autumn.framework.util.CastUtil;
import org.autumn.framework.util.CollectionUtil;
import org.autumn.framework.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wincher
 * @since 2018/9/6
 * <p> org.autumn.framework.bean <p>
 */
public class Param {
  
  private List<FormParam> formParamList;
  private List<FileParam> fileParamList;
  
  public Param(List<FormParam> formParamList, List<FileParam> fileParamList) {
    this.formParamList = formParamList;
    this.fileParamList = fileParamList;
  }
  
  /**
   * get request field param map
   * @return
   */
  public Map<String, Object> getFieldMap() {
    Map<String, Object> fieldMap = new HashMap<>();
    if (CollectionUtil.isNotEmpty(formParamList)) {
      formParamList.stream().forEach(formParam -> {
        String fieldName = formParam.getFieldName();
        Object fieldValue = formParam.getFieldValue();
        if (fieldMap.containsKey(fieldName)) {
          fieldValue = fieldMap.get(fieldName) + StringUtil.SEPARATOR + fieldValue;
        }
        fieldMap.put(fieldName, fieldValue);
      });
    }
    return fieldMap;
  }
  
  /**
   * get upload file map
   * @return
   */
  public Map<String, List<FileParam>> getFileMap() {
    Map<String, List<FileParam>> fileMap = new HashMap<>();
    if (CollectionUtil.isNotEmpty(fileParamList)) {
      fileParamList.stream().forEach(fileParam -> {
        String fieldName = fileParam.getFieldName();
        List<FileParam> fileParamList;
        if (fileMap.containsKey(fieldName)) {
          fileParamList = fileMap.get(fieldName);
        } else {
          fileParamList = new ArrayList<>();
        }
        fileParamList.add(fileParam);
        fileMap.put(fieldName, fileParamList);
      });
    }
    return fileMap;
  }
  
  /**
   * get all upload file
   */
  public List<FileParam> getFileList(String fieldName) {
    return getFileMap().get(fieldName);
  }
  
  /**
   * get only one upload file
   */
  public  FileParam getFile(String fieldName) {
    List<FileParam> fileList = getFileList(fieldName);
    if (CollectionUtil.isNotEmpty(fileList) && 1 == fileList.size()) {
      return fileList.get(0);
    }
    return null;
  }
  
  /**
   *
   */
  public boolean isEmpty() {
    return CollectionUtil.isEmpty(formParamList) && CollectionUtil.isNotEmpty(fileParamList);
  }
  
  /**
   *
   */
  public String getString(String name) {
    return CastUtil.castString(getFieldMap().get(name));
  }
  
  /**
   *
   */
  public Double getDouble(String name) {
    return CastUtil.castDouble(getFieldMap().get(name));
  }
  
  /**
   *
   */
  public long getLong(String name) {
    return CastUtil.castLong(getFieldMap().get(name));
  }
  
  /**
   *
   */
  public int getInt(String name) {
    return CastUtil.castInt(getFieldMap().get(name));
  }
  
  /**
   *
   */
  public boolean getBoolean(String name) {
    return CastUtil.castBoolean(getFieldMap().get(name));
  }
}
