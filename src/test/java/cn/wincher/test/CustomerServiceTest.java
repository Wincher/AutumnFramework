package cn.wincher.test;

import cn.wincher.helper.DatabaseHelper;
import cn.wincher.model.Customer;
import cn.wincher.service.CustomerService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huwq
 * @since 2018/8/23
 * <p> cn.wincher.test <p>
 */
public class CustomerServiceTest {
  
  private final CustomerService customerService;
  
  public CustomerServiceTest() {
    customerService = new CustomerService();
  }
  
  @Before
  public void init() throws IOException {
    DatabaseHelper.excuteSqlFile("sql/customer_init.sql");
  }
  
  @Test
  public void getCustomerListTest() throws Exception {
    List<Customer> customerList = customerService.getCustomerList("");
    Assert.assertEquals(2, customerList.size());
  }
  
  @Test
  public void getCustomerTest() throws Exception {
    long id = 1;
    Customer customer = customerService.getCustomer(id);
    Assert.assertNotNull(customer);
  }
  
  @Test
  public void createCustomerTest() throws Exception {
    Map<String, Object> fieldMap = new HashMap<>();
    fieldMap.put("name", "henry");
    fieldMap.put("contact", "messi");
    fieldMap.put("telephone", "17098768766");
    boolean result = customerService.createCustomer(fieldMap);
    Assert.assertTrue(result);
  }
  
  @Test
  public void updateCustomerTest() throws Exception {
    long id = 5;
    Map<String, Object> fieldMap = new HashMap<>();
    fieldMap.put("contact", "messi");
    boolean result = customerService.updateCustomer(id, fieldMap);
    Assert.assertTrue(result);
  }
  
  @Test
  public void deleteCustomerTest() throws Exception {
    long id = 5;
    boolean result = customerService.deleteCustomer(id);
    Assert.assertTrue(result);
  }
  
}
