package com.wz.blog.service;

import com.wz.blog.po.User;

/**
 * 接口
 */
public interface UserService {
    User checkUser(String username, String password);
}
