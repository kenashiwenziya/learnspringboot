package com.huanfan.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lwf
 * @date 2020/8/29 15:49
 */
@Data
public class SysRole implements Serializable {
    private Integer id;

    private String roleDesc;
}
