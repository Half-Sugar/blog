package com.wz.blog.vo;

public class BlogQuery {
    /**
     * 查询的标题
     */
    private String title;

    /**
     * 查询的id
     */
    private Long typeId;

    /**
     *  是否推荐
     */
    private boolean recommend;

    /**
     * 构造函数
     */
    public BlogQuery() {
    }

    /**
     * get和set方法
     * @return
     */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }
}
