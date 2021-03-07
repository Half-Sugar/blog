package com.wz.blog.web;


import com.wz.blog.po.Comment;
import com.wz.blog.po.User;
import com.wz.blog.service.BlogService;
import com.wz.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    /** 图片 */
//    @Value("${comment.avatar}")
//    private String avatar;

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model){

        model.addAttribute("comments", commentService.listCommentByBlogId(blogId));

        return "blog :: commentList";
    }


    /***
     * 接收提交的信息
     * @param comment
     * @return
     */
    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session){

        Long blogId = comment.getBlog().getId();

        comment.setBlog(blogService.getBlog(blogId));

        User user = (User) session.getAttribute("user");

        if(user != null){
            /** 管理员头像 */
            comment.setAvatar(user.getAvatar());

            /** 是否为管理员 */
            comment.setAdminComment(true);

            /** 管理员昵称 */
//            comment.setNickName(user.getNickName());
        }else{
            /** 访客头像 */
            //comment.setAvatar(avatar);
        }

        commentService.saveComment(comment);

        return "redirect:/comments/" + blogId;
    }
}
