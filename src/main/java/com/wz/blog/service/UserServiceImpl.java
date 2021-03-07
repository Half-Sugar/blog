package com.wz.blog.service;

import com.wz.blog.dao.UserRepository;
import com.wz.blog.po.User;
import com.wz.blog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***
 * 从数据库获取user
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * 注入
     */
    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password){
//        User user = userRepository.findByUserNameAndPassword(username, /*MD5Utils.code(password)*/password);
//        return user;
        return userRepository.findByUserNameAndPassword(username, MD5Utils.code(password));
    }
}
