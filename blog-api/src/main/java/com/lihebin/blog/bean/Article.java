package com.lihebin.blog.bean;

/**
 * Created by lihebin on 2019/3/24.
 */
public class Article extends Base {

    private String title;

    private Integer classify;

    private String content;

    private Integer createAuthorId;

    private Integer updateAuthorId;

    private String createAuthor;

    private String updateAuthor;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getClassify() {
        return classify;
    }

    public void setClassify(Integer classify) {
        this.classify = classify;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getCreateAuthorId() {
        return createAuthorId;
    }

    public void setCreateAuthorId(Integer createAuthorId) {
        this.createAuthorId = createAuthorId;
    }

    public Integer getUpdateAuthorId() {
        return updateAuthorId;
    }

    public void setUpdateAuthorId(Integer updateAuthorId) {
        this.updateAuthorId = updateAuthorId;
    }

    public String getCreateAuthor() {
        return createAuthor;
    }

    public void setCreateAuthor(String createAuthor) {
        this.createAuthor = createAuthor;
    }

    public String getUpdateAuthor() {
        return updateAuthor;
    }

    public void setUpdateAuthor(String updateAuthor) {
        this.updateAuthor = updateAuthor;
    }
}
