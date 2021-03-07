package com.wz.blog.po;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 标签类
 */
@Entity
@Table(name = "t_tag")
public class Tag {

    @Id
    @GeneratedValue
    /** 标签id */
    private Long id;

    /** 标签名 */
    private String name;

    /** 博客类集合 多对多关系 */
    @ManyToMany(mappedBy = "tags")
    private List<Blog> blogs = new ArrayList<>();

    /** 默认构造 */
    public Tag() {
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
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
