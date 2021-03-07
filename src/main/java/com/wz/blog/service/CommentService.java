package com.wz.blog.service;

import com.wz.blog.po.Comment;

import java.util.List;

public interface CommentService  {
    /**
     * 获取评论链表
     */
    List<Comment> listCommentByBlogId(Long blogId);

    /**
     * 保存
     */
    Comment saveComment(Comment comment);

}
