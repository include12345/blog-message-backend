package com.lihebin.blog.bean;

/**
 * Created by lihebin on 2019/3/24.
 */
public class Article extends Base {

    private String title;

    private Integer classify;

    private String classifyName;

    private String content;

    private String createAuthorId;

    private String updateAuthorId;

    private String create_author_id;

    private String update_author_id;

    private String createAuthor;

    private String updateAuthor;

    private String timeStart;

    private String timeEnd;

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

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

    public String getCreateAuthorId() {
        return createAuthorId;
    }

    public void setCreateAuthorId(String createAuthorId) {
        this.createAuthorId = createAuthorId;
    }

    public String getUpdateAuthorId() {
        return updateAuthorId;
    }

    public void setUpdateAuthorId(String updateAuthorId) {
        this.updateAuthorId = updateAuthorId;
    }

    public String getCreate_author_id() {
        return create_author_id;
    }

    public void setCreate_author_id(String create_author_id) {
        this.create_author_id = create_author_id;
    }

    public String getUpdate_author_id() {
        return update_author_id;
    }

    public void setUpdate_author_id(String update_author_id) {
        this.update_author_id = update_author_id;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }
}
