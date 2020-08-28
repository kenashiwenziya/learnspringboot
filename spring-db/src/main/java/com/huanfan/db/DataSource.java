package com.huanfan.db;

import java.lang.annotation.*;

/**
 * @author lwf
 * @date 2020/8/24 13:46
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {

    /**
     * 数据源key值
     * @return
     */
    String value();

}
