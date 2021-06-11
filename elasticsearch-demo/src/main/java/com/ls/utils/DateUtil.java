package com.ls.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ls
 * @date 2020/6/14 8:00
 */
public class DateUtil {
  public static Date string2Date(String strDate) {
    SimpleDateFormat fmt = new SimpleDateFormat();
    Date date = null;
    try {
      if (strDate != null && !strDate.equals("")) {
        String dash = "-";
        String slash = "/";
        int dateStringLength = 8;
        if (strDate.indexOf(dash) != -1) {
          fmt.applyPattern("yyyy-MM-dd");
        } else if (strDate.indexOf(slash) != -1) {
          fmt.applyPattern("yyyy/MM/dd");
        } else if (strDate.length() == dateStringLength) {
          fmt.applyPattern("yyyyMMdd");
        }
        date = fmt.parse(strDate);

      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return date;
  }

}
