package com.wz.blog.web.admin;

import com.wz.blog.po.Tag;
import com.wz.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    /**
     * 分页的方法 处理
     * @param pageable
     * @return
     */
    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model){

        model.addAttribute("page",tagService.listTag(pageable));

        return "admin/tags";
    }

    /**
     * 添加分类
     * @return
     */
    @GetMapping("/tags/input")
    public String input(Model  model){
        model.addAttribute("tag",new Tag());

        return "admin/tags-input";
    }


    /**
     * 标签 新增
     * @param tag
     * @param resule
     * @param attributes
     * @return
     */
    @PostMapping("/tags")
    public String post(@Validated  Tag tag, BindingResult resule, RedirectAttributes attributes){
        Tag tag1 = tagService.getTagByName(tag.getName());
        /**
         * 判断重复
         */
        if(tag1 != null){
            resule.rejectValue("name","nameError","不能添加重复的分类");
        }

        /**
         * 判断是否为空
         */
        if(resule.hasErrors()){
            return "admin/tags-input";
        }

        Tag t = tagService.saveTag(tag);

        if(t == null){
            //没有保存成功
            attributes.addFlashAttribute("message","新增失败");
        }else{
            //保存成功
            attributes.addFlashAttribute("message","新增成功");
        }
        return "redirect:/admin/tags";
    }

    /**
     * 修改
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("tag",tagService.getTag(id));
        return "admin/tags-input";
    }

    /**
     * 更新
     * @param tag
     * @param resule
     * @param attributes
     * @return
     */
    @PostMapping("/tags/{id}")
    public String editpost(@Validated  Tag tag, BindingResult resule, @PathVariable Long id, RedirectAttributes attributes){
        Tag tag1 = tagService.getTagByName(tag.getName());
        /**
         * 判断重复
         */
        if(tag1 != null){
            resule.rejectValue("name","nameError","不能添加重复的分类");
        }

        /**
         * 判断是否为空
         */
        if(resule.hasErrors()){
            return "admin/tags-input";
        }

        Tag t = tagService.updateTag(id, tag);

        if(t == null){
            //没有保存成功
            attributes.addFlashAttribute("message","更新失败");
        }else{
            //保存成功
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/tags";
    }

    /**
     * 删除
     * @param id
     * @param attributes
     * @return
     */
    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/tags";
    }
}
