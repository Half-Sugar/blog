package com.wz.blog.service;


import com.wz.blog.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {
    /**
     * 新增
     * @param tag
     * @return
     */
    Tag saveTag(Tag tag);

    /**
     * 查询
     * @parapm id
     * @return
     */
    Tag getTag(Long id);

    /**
     * 分页查询
     * @param pageable
     * @return
     */
    Page<Tag> listTag(Pageable pageable);

    /**
     * 修改
     * @param id
     * @param tag
     * @return
     */
    Tag updateTag(Long id,Tag tag);

    /**
     * 删除
     * @param id
     */
    void deleteTag(Long id);

    /**
     * 通过名称查找Type
     * @param name
     * @return
     */
    Tag getTagByName(String name);


    List<Tag> listTag();

    List<Tag> listTag(String ids);

    List<Tag> listTatTop(Integer size);
}
