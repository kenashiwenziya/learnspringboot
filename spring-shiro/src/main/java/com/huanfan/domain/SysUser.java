package com.huanfan.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lwf
 * @date 2020/8/29 15:47
 */
@Data
public class SysUser implements Serializable {
    private Integer id;

    private String userName;

    private String passWord;

    /**
     * 是否启用
     */
    private Integer userEnable;
}
