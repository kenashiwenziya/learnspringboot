package com.huanfan.springdb;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringDbApplicationTests {


    //注入StringEncryptor
    @Autowired
    StringEncryptor encryptor;

    @Test
    public void encry() {
        //加密账号
        String username = encryptor.encrypt("xxx");
        System.out.println(username);
        //加密密码
        String password = encryptor.encrypt("xxx");
        System.out.println(password);
    }

    @Test
    void contextLoads() {
    }



}
