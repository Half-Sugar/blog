package com.wz.blog.po;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;



/**
 * 分类 类
 */
@Entity
@Table(name = "t_type")
public class Type {

    @Id
    @GeneratedValue
    /** 分类id */
    private Long id;

    /** 分类名字 */
    @NotNull
    private String name;

    /** Blog对象集合 一对多关系 */
    @OneToMany(mappedBy = "type")
    private List<Blog> blogs = new ArrayList<>();

    /** 默认构造函数 */
    public Type() {
    }

    /** get和set方法 */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
