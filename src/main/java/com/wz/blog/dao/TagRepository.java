package com.wz.blog.dao;

import com.wz.blog.po.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface TagRepository extends JpaRepository<Tag, Long> {
    /***
     * 查找
     */
    Tag findByName(String name);


    /**
     * 排序
     * @param pageable
     * @return
     */
    @Query("select t from Tag t")

    /**
     *
     * @param pageable
     * @return: java.util.List<com.wz.blog.po.Tag>
     * @author: 蚊子
     * @date: 2020/12/9
     * @version: V1.0
     * @Description:
     *
     */
    List<Tag> findTop(Pageable pageable);
}
