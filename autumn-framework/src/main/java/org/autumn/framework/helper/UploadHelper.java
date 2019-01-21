package org.autumn.framework.helper;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.autumn.framework.bean.FileParam;
import org.autumn.framework.bean.FormParam;
import org.autumn.framework.bean.Param;
import org.autumn.framework.util.CollectionUtil;
import org.autumn.framework.util.FileUtil;
import org.autumn.framework.util.StreamUtil;
import org.autumn.framework.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author wincher
 * @since 2019/1/9
 * <p> org.autumn.framework.helper <p>
 */
public class UploadHelper {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(UploadHelper.class);
  
  /**
   * uploadfile object provided by apache commons fileupload
   */
  private static ServletFileUpload servletFileUpload;
  
  /**
   * init
   */
  public static void init(ServletContext servletContext) {
    File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
    servletFileUpload = new ServletFileUpload(new DiskFileItemFactory(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD, repository));
    int uploadLimit = ConfigHelper.getAppUploadLimit();
    if (0 < uploadLimit) {
      servletFileUpload.setFileSizeMax(uploadLimit * 1024 * 1024);
    }
  }
  
  /**
   *
   */
  public static boolean isMultipart(HttpServletRequest request) {
    return ServletFileUpload.isMultipartContent(request);
  }
  
  /**
   * create request Object
   */
  public static Param createParam(HttpServletRequest request) throws IOException {
    List<FormParam> formParamList = new ArrayList<>();
    List<FileParam> fileParamList = new ArrayList<>();
    try {
      Map<String, List<FileItem>> fileItemListMap = servletFileUpload.parseParameterMap(request);
      if (CollectionUtil.isNotEmpty(fileItemListMap)) {
        for (Map.Entry<String, List<FileItem>> fileItemListEntry:fileItemListMap.entrySet()) {
          String fileName = fileItemListEntry.getKey();
          List<FileItem> fileItemList = fileItemListEntry.getValue();
          if (CollectionUtil.isNotEmpty(fileItemList)) {
            for (FileItem fileItem : fileItemList) {
              if (fileItem.isFormField()) {
                  String fieldValue = fileItem.getString("UTF-8");
                  formParamList.add(new FormParam(fileName, fieldValue));
              } else {
                String fieldName = FileUtil.getRealFileName(new String(fileItem.getName().getBytes(), "UTF-8"));
                if (StringUtil.isNotEmpty(fileName)) {
                  long fileSize = fileItem.getSize();
                  String contentType = fileItem.getContentType();
                  InputStream inputStream = fileItem.getInputStream();
                  fileParamList.add(new FileParam(fieldName, fileName, fileSize, contentType, inputStream));
                }
              }
            }
          }
        }
      }
    } catch (FileUploadException e) {
      LOGGER.error("create param failure", e);
      throw new RuntimeException(e);
    }
    return new Param(formParamList, fileParamList);
  }
  
  /**
   * upload file
   */
  public static void uploadFile(String basePath, FileParam fileParam) {
    if (Objects.nonNull(fileParam)) {
      try {
        String filePath = basePath + fileParam.getFileName();
        FileUtil.createFile(filePath);
        InputStream inputStream = new BufferedInputStream(fileParam.getInputStream());
        OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath));
        StreamUtil.copyStream(inputStream, outputStream);
      } catch (FileNotFoundException e) {
        LOGGER.error("upload file failure", e);
        throw new RuntimeException(e);
      }
    }
  }
  
  /**
   * batch upload file
   */
  public static void uploadFile(String basePath, List<FileParam> fileParamList) {
    try {
      if (CollectionUtil.isNotEmpty(fileParamList)) {
        for (FileParam fileParam:fileParamList) {
          uploadFile(basePath, fileParam);
        }
      }
    } catch (Exception e) {
      LOGGER.error("upload file failure", e);
      throw new RuntimeException(e);
    }
  }
}
