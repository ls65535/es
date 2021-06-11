package com.ls.config;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author ls
 * @date 2020/6/27 8:28
 */
@Configuration // 当前是一个java 配置类
@EnableTransactionManagement // 开启事务管理
@MapperScan(basePackages = "com.ls.dao") // mapper 扫描
public class MybatisPlusConfig {

  // 2.MybatisSqlSessionFactoryBean工厂
  @Bean
  public SqlSessionFactory mybatisSqlSessionFactoryBean(
      @Qualifier("dataSource") DataSource dataSource)
      throws Exception { // 使用依赖注入的形式注入过来
    MybatisSqlSessionFactoryBean factory = new MybatisSqlSessionFactoryBean();
    // 传入数据源
    factory.setDataSource(dataSource);
    PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    factory.setMapperLocations(resolver.getResources("classpath*:mappers/*.xml"));
    //设置驼峰配置
    factory.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
    return factory.getObject();
  }

  //4. 事务管理， 开启事务使用类注解
  @Bean
  public DataSourceTransactionManager dataSourceTransactionManager(
      @Qualifier("dataSource") DataSource dataSource) { // 数据源依赖注入
    DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
    transactionManager.setDataSource(dataSource);
    return transactionManager;
  }


}
