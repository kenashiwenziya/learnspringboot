package com.huanfan.service;

import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author lwf
 * @date 2020/8/29 15:50
 * 没写实现类，为了不报错，@Service直接标在接口
 */
@Service
public interface SysRoleService {
    Set<String> findRoleNameByUserId(int userId);
}
