package com.lihebin.blog.service;

import com.lihebin.blog.bean.Article;
import com.lihebin.blog.bean.Articles;
import com.lihebin.blog.bean.PageInfo;
import com.lihebin.blog.bean.PreNext;
import com.lihebin.blog.dao.ArticleDao;
import com.lihebin.blog.dao.ClassifyDao;
import com.lihebin.blog.dao.UserDao;
import com.lihebin.blog.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lihebin on 2019/3/24.
 */
@Service
public class BlogServiceImpl implements BlogService{

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private ClassifyDao classifyDao;

    @Autowired
    private UserDao userDao;

    @Override
    public Article getBlog(String id) {
        return articleResponse(articleDao.getArticle(id));
    }

    @Override
    public Articles listBlog(Articles articles) {
        PageInfo pageInfo = new PageInfo(articles.getPage(), articles.getPageSize(), articles.getDateStart(), articles.getDateEnd(), articles.getOrderBy());
        Article article = new Article();
        Articles articlesOut = articleDao.listArticle(article, pageInfo);
        List<Article> articleList = new ArrayList<>();
        articlesOut.getArticleList().forEach(articleOut -> {
            articleList.add(articleResponse(articleOut));
        });
        articlesOut.setArticleList(articleList);
        return articlesOut;
    }

    @Override
    public Article updateBlog(Article article) {
        if (null != article.getClassifyName()) {
            int classifySn = classifyDao.getClassifyByName(article.getClassifyName());
            article.setClassify(classifySn);
        }
        if(!articleDao.updateArticle(article)) {
            return new Article();
        }
        return articleResponse(articleDao.getArticle(article.getId()));

    }

    @Override
    public Article createBlog(Article article) {
        if (null != article.getClassifyName()) {
            int classifySn = classifyDao.getClassifyByName(article.getClassifyName());
            article.setClassify(classifySn);
        }
        return articleResponse(articleDao.createArticle(article));
    }

    @Override
    public void deleteBlog(String id) {
        articleDao.deleteArticle(id);
    }

    @Override
    public List<String> getClassify() {
        return classifyDao.listClassify();
    }

    @Override
    public PreNext getPreNextBlog(String id) {
        Article preArticle = articleDao.getPreArticle(id);
        Article nextArticle = articleDao.getNextArticle(id);
        PreNext preNext = new PreNext();
        preNext.setPreId(preArticle.getId());
        preNext.setPreTitle(preArticle.getTitle());
        preNext.setNextId(nextArticle.getId());
        preNext.setNextTitle(nextArticle.getTitle());
        return preNext;
    }


    private Article articleResponse(Article article) {
        if (article == null) {
            return new Article();
        }
        Article articleOut = new Article();
        articleOut.setId(article.getId());
        articleOut.setTitle(article.getTitle());
        articleOut.setClassifyName(classifyDao.getClassifyBySn(article.getClassify()));
        articleOut.setContent(article.getContent());
        articleOut.setCreateAuthor(userDao.queryUserByUsername(article.getCreate_author_id()).getName());
        articleOut.setUpdateAuthor(userDao.queryUserByUsername(article.getUpdate_author_id()).getName());
        articleOut.setTimeStart(DateTimeUtil.dateFormat(article.getCtime()));
        articleOut.setTimeEnd(DateTimeUtil.dateFormat(article.getMtime()));
        return articleOut;
    }
}
