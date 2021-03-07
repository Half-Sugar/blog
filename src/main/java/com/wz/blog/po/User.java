package com.wz.blog.po;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/***
 * 用户类
 */
@Entity
@Table(name = "t_user")
public class User {

    @Id
    @GeneratedValue
    /** 用户id */
    private Long id;

    /** 用户昵称 */
    private String nickName;

    /** 用户名 */
    private String userName;

    /** 密码 */
    private String password;

    /** 邮箱 */
    private String email;

    /** 头像 图片地址 */
    private String avatar;

    /** 用户类型 */
    private Integer type;

    /** 数据库的时间格式 */
    @Temporal(TemporalType.TIMESTAMP)
    /** 创建时间 */
    private Date createTime;

    /** 数据库的时间格式 */
    @Temporal(TemporalType.TIMESTAMP)
    /** 更新时间 */
    private Date updateTime;

    /** Blog对象 一对多关系 */
    @OneToMany(mappedBy = "user")
    private List<Blog> blogs = new ArrayList<>();

    /** 默认构造 */
    public User() {
    }

    /** get和set方法 */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    /** toString方法 */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                ", type=" + type +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
