package com.lihebin.blog.service;

import com.lihebin.blog.annotation.BackendAnnotation;
import com.lihebin.blog.bean.Article;
import com.lihebin.blog.bean.Articles;
import com.sun.istack.internal.NotNull;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * Created by lihebin on 2019/3/24.
 */
@BackendAnnotation
@Validated
public interface BlogService {

    /**
     * 根据编号查博客详细信息
     *
     * @param id
     * @return
     */
    Article getBlog(String id);

    /**
     * 分页查询博客列表
     * @param articles
     * @return
     */
    Articles listBlog(Articles articles);

    /**
     * 根据ID更新博客
     *
     * @param article
     * @return
     */
    Article updateBlog(Article article);

    /**
     * 创建博客
     *
     * @param article
     * @return
     */
    Article createBlog(Article article);

    /**
     * 删除博客
     * @param id
     */
    void deleteBlog(String id);

    /**
     * 标签
     *
     * @return
     */
    List<String> getClassify();
}
