package cn.wincher.service;

import cn.wincher.helper.DatabaseHelper;
import cn.wincher.model.Customer;
import org.autumn.framework.annotation.Transaction;
import org.autumn.framework.bean.FileParam;
import org.autumn.framework.helper.UploadHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * 提供客户数据服务
 * @author wincher
 * @since 2018/8/23
 * <p> cn.wincher.service <p>
 */
public class CustomerService {
  
  private static Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);
  
  
  /**
   * 获取客户列表
   * @return
   */
  public List<Customer> getCustomerList() {
    String sql = "select * from customer";
    return DatabaseHelper.queryEntityList(Customer.class, sql);
  }
  
  /**
   * 获取客户
   * @param id
   * @return
   */
  public Customer getCustomer(long id) {
    // todo:
    return null;
  }
  
  /**
   * 创建客户
   * @param fieldMap
   * @return
   */
  @Transaction
  public boolean createCustomer(Map<String, Object> fieldMap, FileParam fileParam) {
    boolean result = DatabaseHelper.insertEntity(Customer.class, fieldMap);
    if (result) {
      UploadHelper.uploadFile("/tmp/upload", fileParam);
    }
    return result;
  }
  
  /**
   * 更新客户
   * @param fieldMap
   * @return
   */
  public boolean updateCustomer(long id, Map<String, Object> fieldMap) {
    // todo:
    return false;
  }
  
  /**
   * 删除客户
   * @param id
   * @return
   */
  public boolean deleteCustomer(long id) {
    // todo:
    return false;
  }
  
}
