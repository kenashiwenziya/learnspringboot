package com.huanfan.service;

import com.huanfan.domain.SysUser;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author lwf
 * @date 2020/8/29 15:51
 * 没写实现类，为了不报错，@Service直接标在接口
 */
@Service
public interface UserService {

    SysUser getUser(SysUser user);

    /**
     * 获取用户权限
     *
     * @param userId userId
     * @return 用户权限
     */
    Set<String> findPermissionsByUserId(int userId);
}
