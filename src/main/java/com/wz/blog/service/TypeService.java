package com.wz.blog.service;


import com.wz.blog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService {
    /**
     * 新增
     * @param type
     * @return
     */
    Type saveType(Type type);

    /**
     * 查询
     * @parapm id
     * @return
     */
    Type getType(Long id);

    /**
     * 分页查询
     * @param pageable
     * @return
     */
    Page<Type> listType(Pageable pageable);

    /**
     * 修改
     * @param id
     * @param type
     * @return
     */
    Type updateType(Long id,Type type);

    /**
     * 删除
     * @param id
     */
    void deleteType(Long id);

    /**
     * 通过名称查找Type
     * @param name
     * @return
     */
    Type getTypeByName(String name);


    List<Type> listType();

    List<Type> listTypeTop(Integer size);
}
