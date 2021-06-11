package com.ls.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Druid连接池配置
 */
@Configuration
public class DruidConfig {

  @Value("${spring.datasource.url}")
  private String url;
  @Value("${spring.datasource.username}")
  private String username;
  @Value("${spring.datasource.password}")
  private String password;

  @Bean(destroyMethod = "close", initMethod = "init")
  public DruidDataSource dataSource(Environment environment) throws SQLException {
    DruidDataSource druidDataSource = new DruidDataSource();
    druidDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
    druidDataSource.setUrl(url);
    druidDataSource.setUsername(username);
    druidDataSource.setPassword(password);
    /** 配置初始化大小、最小、最大*/
    druidDataSource.setInitialSize(5);
    druidDataSource.setMinIdle(3);
    druidDataSource.setMaxActive(20);
    /**配置获取连接等待超时的时间,单位是毫秒*/
    druidDataSource.setMaxWait(60000);
    druidDataSource.setValidationQuery("select 1 from dual");
    druidDataSource.setTestWhileIdle(true);
    druidDataSource.setTimeBetweenEvictionRunsMillis(60000);
    druidDataSource.setMinEvictableIdleTimeMillis(300000);
    druidDataSource.setFilters("stat");
    return druidDataSource;
  }

  @Bean
  public ServletRegistrationBean statViewServlet() {
    StatViewServlet statViewServlet = new StatViewServlet();
    ServletRegistrationBean registrationBean = new ServletRegistrationBean(statViewServlet,
        "/druid/*");
    Map<String, String> map = new HashMap();
    map.put("loginUsername", "root");
    map.put("loginPassword", "root");
    map.put("resetEnable", "true");
    registrationBean.setInitParameters(map);
    return registrationBean;
  }

}