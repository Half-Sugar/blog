package com.wz.blog.service;

import com.wz.blog.NotFoundException;
import com.wz.blog.dao.TagRepository;
import com.wz.blog.po.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;


    /**
     * 保存
     * @param tag
     * @return
     */
    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    /**
     * 根据id获取tag
     * @param id
     * @return
     */
    @Transactional
    @Override
    public Tag getTag(Long id) {
        return tagRepository.getOne(id);
    }

    /**
     * 获取tag列表
     * @param pageable
     * @return
     */
    @Transactional
    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    /**
     * 编辑
     * @param id
     * @param tag
     * @return
     */
    @Transactional
    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag t = tagRepository.getOne(id);
        if(t == null){
            throw  new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(tag, t);

        return tagRepository.save(t);
    }

    /**
     * 删除
     * @param id
     */
    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }


    /**
     * 获取标签名
     * @param name
     * @return
     */
    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }


    /**
     * 标签列表
     * @return
     */
    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }


    /**
     * 标签列表
     * @param ids
     * @return
     */
    @Override
    public List<Tag> listTag(String ids) {
        return tagRepository.findAllById(convertToList(ids));
    }


    @Override
    public List<Tag> listTatTop(Integer size) {
        /** 排序 */
        Sort sort = Sort.by(Sort.Direction.DESC,"blogs.size");
        Pageable pageable = PageRequest.of(0, size, sort);
        return tagRepository.findTop(pageable);
    }

    /**
     * 将字符串转换为数组
     * @param ids
     * @return
     */
    private List<Long> convertToList(String ids){
        List<Long> list = new ArrayList<>();
        if(!"".equals(ids) && ids != null){
            /** 以逗号分割字符串 */
            String[] idarray = ids.split(",");

            for(int i = 0; i < idarray.length; ++i){
                list.add(new Long(idarray[i]));
            }
        }

        return list;
    }



}
