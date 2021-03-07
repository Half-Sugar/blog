package com.wz.blog.web.admin;

import com.wz.blog.po.User;
import com.wz.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * 登录web控制器
 */
@Controller
@RequestMapping("/admin")
public class LoginController {

    /***
     * service注入
     */
    @Autowired
    private UserService userService;

    /** 跳转登录页面 */
    @GetMapping
    public String loginPage(){
        return "admin/login";
    }

    /**
     * 登录页面提交时调用的方法，把用户和密码提交过来
     */
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password,
                        HttpSession session, RedirectAttributes attributes){

        User user = userService.checkUser(username, password);

        /**
         * 判断用户名 密码是否正确
         * 正确：登录成功
         * 错误：页面重定向
         */
        if(null != user){
            user.setPassword(null);
            session.setAttribute("user",user);
            return "admin/index";
        } else {
            attributes.addFlashAttribute("message","用户名或密码错误");
            return "redirect:/admin";
            //return "admin/index";
        }
    }

    /**
     * 注销用户
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
