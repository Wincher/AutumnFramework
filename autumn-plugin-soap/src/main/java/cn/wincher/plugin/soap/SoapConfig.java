package cn.wincher.plugin.soap;

import org.autumn.framework.helper.ConfigHelper;

/**
 * @author wincher
 * @since 2019-01-25
 * <p> cn.wincher.plugin.soap <p>
 */
public class SoapConfig {
  public static boolean isLog() {
    return ConfigHelper.getBoolean(SoapConstant.LOG);
  }
}
