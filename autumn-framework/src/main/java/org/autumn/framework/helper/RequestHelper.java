package org.autumn.framework.helper;

import org.autumn.framework.bean.FormParam;
import org.autumn.framework.bean.Param;
import org.autumn.framework.util.ArrayUtil;
import org.autumn.framework.util.CodecUtil;
import org.autumn.framework.util.StreamUtil;
import org.autumn.framework.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

/**
 * @author wincher
 * @since 2019/1/9
 * <p> org.autumn.framework.helper <p>
 */
public class RequestHelper {
  /**
   * create request object
   */
  public static Param createParam(HttpServletRequest request) throws IOException {
    List<FormParam> formParamList = new ArrayList<>();
    formParamList.addAll(parseParametersNames(request));
    formParamList.addAll(parseInputStream(request));
    return new Param(formParamList, null);
  }
  
  private static List<FormParam> parseInputStream(HttpServletRequest request) throws IOException {
    List<FormParam> formParamList = new ArrayList<>();
    String body = CodecUtil.decodeUrl(StreamUtil.getString(request.getInputStream()));
    if (StringUtil.isNotEmpty(body)) {
      String[] kvs = StringUtil.splitString(body, "&");
      if (ArrayUtil.isNotEmpty(kvs)) {
        for (String kv : kvs) {
          String[] array = StringUtil.splitString(kv, "=");
          if (ArrayUtil.isNotEmpty(array) && 2 == array.length) {
            formParamList.add(new FormParam(array[0], array[1]));
          }
        }
      }
    }
    return formParamList;
  }
  
  private static List<FormParam> parseParametersNames(HttpServletRequest request) {
    List<FormParam> formParamList = new ArrayList<>();
    Enumeration<String> parameterNames = request.getParameterNames();
    while (parameterNames.hasMoreElements()) {
      String fieldName = parameterNames.nextElement();
      String[] fieldValues = request.getParameterValues(fieldName);
      if (ArrayUtil.isNotEmpty(fieldValues)) {
        Object fieldValue;
        if (1==fieldValues.length) {
          fieldValue = fieldValues[0];
        } else {
          StringBuilder sb = new StringBuilder("");
          for (int i = 0; i<fieldValues.length; i++) {
            sb.append(fieldValues[i]);
            if (i != fieldValues.length -1) {
              sb.append(StringUtil.SEPARATOR);
            }
          }
          fieldValue = sb.toString();
        }
        formParamList.add(new FormParam(fieldName, fieldValue));
      }
    }
    return formParamList;
  }
}
