package com.ls.config;

import com.github.pagehelper.PageHelper;
import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by ls on 2019/11/18.
 */
@Configuration
public class PageHelpPlusConfig {
    /**
     * 注册分页插件
     * @return
     */
    /**
     * 注册MyBatis分页插件PageHelper
     */
    private static String dialect = "dialect";
    private static String offsetAsPageNum = "offsetAsPageNum";
    private static String rowBoundsWithCount = "rowBoundsWithCount";
    private static String reasonable = "reasonable";
    private static String database = "mysql";
    private static String t = "true";

    @Bean
    public PageHelper pageHelper() {
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        p.setProperty(dialect, database);
        p.setProperty(offsetAsPageNum, t);
        p.setProperty(rowBoundsWithCount, t);
        p.setProperty(reasonable, t);
        pageHelper.setProperties(p);
        return pageHelper;
    }

}
