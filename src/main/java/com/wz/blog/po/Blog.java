package com.wz.blog.po;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 博客类
 */
@Entity
@Table(name = "t_blog")
public class Blog {

    @Id
    @GeneratedValue
    /** id */
    private Long id;

    /** 博客标题 */
    private String title;

    /** 博客内容 */
    //@Column(columnDefinition = "MEDIUMTEXT")
    @Basic(fetch = FetchType.LAZY)
    @Lob
    private String content;

    /** 图片 */
    private String firstPicture;

    /** 标记 */
    private String flag;

    /** 浏览次数 */
    private Integer views;

    /** 赞赏是否开启 */
    private boolean appreciation;

    /** 转载声明是否开启 */
    private boolean shareStatement;

    /** 评论是否开启 */
    private boolean commentabled;

    /** 是否发布 */
    private boolean published;

    /** 是否推荐 */
    private boolean recommend;

    /** 博客描述 */
    private String description;

    /** 数据库的时间格式 */
    @Temporal(TemporalType.TIMESTAMP)
    /** 创建时间 */
    private Date createTime;

    /** 数据库的时间格式 */
    @Temporal(TemporalType.TIMESTAMP)
    /** 更新时间 */
    private Date updateTime;

    /** Type对象 多对一关系 */
    @ManyToOne
    private Type type;

    /** 标签对象 多对多关系 */
    @ManyToMany(cascade = {CascadeType.PERSIST})//级联关系
    private List<Tag> tags = new ArrayList<>();

    /** user对象 多对一关系  */
    @ManyToOne
    private User user;

    /** Comment对象 一对多关系 */
    @OneToMany(mappedBy = "blog")
    private List<Comment> comments = new ArrayList<>();

    @Transient
    private String tagIds;

    /** 默认构造函数 */
    public Blog() {
    }




    /** get和set方法 */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public boolean isAppreciation() {
        return appreciation;
    }

    public void setAppreciation(boolean appreciation) {
        this.appreciation = appreciation;
    }

    public boolean isShareStatement() {
        return shareStatement;
    }

    public void setShareStatement(boolean shareStatement) {
        this.shareStatement = shareStatement;
    }

    public boolean isCommentabled() {
        return commentabled;
    }

    public void setCommentabled(boolean commentabled) {
        this.commentabled = commentabled;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getTagIds() {
        return tagIds;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void init(){
        this.tagIds = tagsToIds(this.getTags());
    }

    /**
     * 将数组转换为字符串  转换后：1，2，3
     * @param tags
     * @return
     */
    private String tagsToIds(List<Tag> tags){
        if(!tags.isEmpty()){
            StringBuilder ids = new StringBuilder();

            /** 开关 不然末尾加逗号 */
            boolean flag = false;
            for(Tag tag : tags){
                if(flag){
                    ids.append(",");
                }else{
                    flag = true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        }

        return tagIds;
    }


    /** toString方法 */
    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", firstPicture='" + firstPicture + '\'' +
                ", flag='" + flag + '\'' +
                ", views=" + views +
                ", appreciation=" + appreciation +
                ", shareStatement=" + shareStatement +
                ", commentabled=" + commentabled +
                ", published=" + published +
                ", recommend=" + recommend +
                ", description='" + description + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", type=" + type +
                ", tags=" + tags +
                ", user=" + user +
                ", comments=" + comments +
                ", tagIds='" + tagIds + '\'' +
                '}';
    }
}
