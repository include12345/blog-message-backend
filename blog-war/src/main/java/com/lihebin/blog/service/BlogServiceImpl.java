package com.lihebin.blog.service;

import com.lihebin.blog.bean.Article;
import com.lihebin.blog.bean.Articles;
import com.lihebin.blog.bean.PageInfo;
import com.lihebin.blog.dao.ArticleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lihebin on 2019/3/24.
 */
@Service
public class BlogServiceImpl implements BlogService{

    @Autowired
    private ArticleDao articleDao;

    @Override
    public Article getBlog(String id) {
        return articleDao.getArticle(id);
    }

    @Override
    public Articles listBlog(Articles articles) {
        PageInfo pageInfo = new PageInfo(articles.getPage(), articles.getPageSize(), articles.getDateStart(), articles.getDateEnd(), articles.getOrderBy());
        Article article = new Article();
        return articleDao.listArticle(article, pageInfo);
    }

    @Override
    public Article updateBlog(Article article) {
        if(!articleDao.updateArticle(article)) {
            return new Article();
        }
        return articleDao.getArticle(article.getId());

    }

    @Override
    public Article createBlog(Article article) {
        return articleDao.createArticle(article);
    }

    @Override
    public void deleteBlog(String id) {
        articleDao.deleteArticle(id);
    }
}
