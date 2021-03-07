package com.wz.blog.web.admin;

import com.wz.blog.po.Type;
import com.wz.blog.service.TypeService;
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
public class TypeController {

    @Autowired
    private TypeService typeService;

    /**
     * 分页的方法 处理
     * @param pageable
     * @return
     */
    @GetMapping("/types")
    public String types(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model){

        model.addAttribute("page",typeService.listType(pageable));

        return "admin/types";
    }

    /**
     * 添加分类
     * @return
     */
    @GetMapping("/types/input")
    public String input(Model  model){
        model.addAttribute("type",new Type());

        return "admin/types-input";
    }


    /**
     * 标签 新增
     * @param type
     * @param resule
     * @param attributes
     * @return
     */
    @PostMapping("/types")
    public String post(@Validated  Type type, BindingResult resule, RedirectAttributes attributes){
        Type type1 = typeService.getTypeByName(type.getName());
        /**
         * 判断重复
         */
        if(type1 != null){
            resule.rejectValue("name","nameError","不能添加重复的分类");
        }

        /**
         * 判断是否为空
         */
        if(resule.hasErrors()){
            return "admin/types-input";
        }

        Type t = typeService.saveType(type);

        if(t == null){
            //没有保存成功
            attributes.addFlashAttribute("message","新增失败");
        }else{
            //保存成功
            attributes.addFlashAttribute("message","新增成功");
        }
        return "redirect:/admin/types";
    }

    /**
     * 修改
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type",typeService.getType(id));
        return "admin/types-input";
    }

    /**
     * 更新
     * @param type
     * @param resule
     * @param attributes
     * @return
     */
    @PostMapping("/types/{id}")
    public String editpost(@Validated  Type type, BindingResult resule, @PathVariable Long id, RedirectAttributes attributes){
        Type type1 = typeService.getTypeByName(type.getName());
        /**
         * 判断重复
         */
        if(type1 != null){
            resule.rejectValue("name","nameError","不能添加重复的分类");
        }

        /**
         * 判断是否为空
         */
        if(resule.hasErrors()){
            return "admin/types-input";
        }

        Type t = typeService.updateType(id, type);

        if(t == null){
            //没有保存成功
            attributes.addFlashAttribute("message","更新失败");
        }else{
            //保存成功
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/types";
    }

    /**
     * 删除
     * @param id
     * @param attributes
     * @return
     */
    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";
    }
}
