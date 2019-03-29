package com.lihebin.blog.dao;

import com.lihebin.blog.bean.Article;
import com.lihebin.blog.bean.Articles;
import com.lihebin.blog.bean.PageInfo;
import com.lihebin.blog.utils.SqlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by lihebin on 2019/3/24.
 */
@Repository
public class ArticleDao {
    private final static Logger log = LoggerFactory.getLogger(ArticleDao.class);


    @Resource(name = "blogJdbcTemplate")
    JdbcTemplate blogTemplate;

    private static final String ID = "id";
    private static final String TITLE = "title";
    private static final String CLASSIFY = "classify";
    private static final String CONTENT = "content";
    private static final String CREATE_AUTHOR_ID = "create_author_id";
    private static final String UPDATE_AUTHOR_ID = "update_author_id";
    private static final String CTIME = "ctime";
    private static final String MTIME = "mtime";
    private static final String DELETED = "deleted";

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    /**
     * 分页获取博客列表
     * @param article
     * @param pageInfo
     * @return
     */
    public Articles listArticle(Article article, PageInfo pageInfo){
        String countSql = "select count(1) from article where deleted = 0";
        String sql = "select * from article where deleted = 0";
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(blogTemplate);
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();

        if (null != article.getId()) {
            countSql = SqlUtil.sqlAndFormat(countSql, ID);
            sql = SqlUtil.sqlAndFormat(sql, ID);
            mapSqlParameterSource.addValue(ID, article.getId());
        }

        if (null != article.getTitle()) {
            sql = SqlUtil.sqlAndFormat(sql, TITLE);
            countSql = SqlUtil.sqlAndFormat(countSql, TITLE);
            mapSqlParameterSource.addValue(TITLE, article.getTitle());
        }
        if (null != article.getClassify()) {
            sql = SqlUtil.sqlAndFormat(sql, CLASSIFY);
            countSql = SqlUtil.sqlAndFormat(countSql, CLASSIFY);
            mapSqlParameterSource.addValue(CLASSIFY, article.getClassify());
        }
        if (null != article.getCreateAuthorId()) {
            sql = SqlUtil.sqlAndFormat(sql, CREATE_AUTHOR_ID);
            countSql = SqlUtil.sqlAndFormat(countSql, CREATE_AUTHOR_ID);
            mapSqlParameterSource.addValue(CREATE_AUTHOR_ID, article.getCreateAuthorId());
        }
        if (null != article.getUpdateAuthorId()) {
            sql = SqlUtil.sqlAndFormat(sql, UPDATE_AUTHOR_ID);
            countSql = SqlUtil.sqlAndFormat(countSql, UPDATE_AUTHOR_ID);
            mapSqlParameterSource.addValue(UPDATE_AUTHOR_ID, article.getUpdateAuthorId());
        }
        Articles articles = new Articles();
        //查询数量
        try {
            int count = namedParameterJdbcTemplate.queryForObject(countSql, mapSqlParameterSource, Integer.class);
            articles.setArticleCount(count);
        } catch (Exception e) {
            log.error("listArticle count: {}", article, e);
            return new Articles();
        }
        sql = SqlUtil.appendPageInfoCtimeDesc(sql, pageInfo);
        try {
            RowMapper<Article> rowMapper = new BeanPropertyRowMapper<>(Article.class);
            List<Article> articlesList = namedParameterJdbcTemplate.query(sql, mapSqlParameterSource, rowMapper);
            articles.setArticleList(articlesList);
        } catch (Exception e) {
            log.error("listArticle list: {},{}", article, pageInfo, e);
            return new Articles();
        }
        return articles;
    }



    /**
     * 查询
     * @param id
     * @return
     */
    public Article getArticle(String id){
        String sql = "select * from article where id =:id";
        return getArticleHandle(sql, id);
    }

    /**
     * 查询上一条数据
     * @param id
     * @return
     */
    public Article getPreArticle(String id){
        String sql = "select * from article where ctime > (select ctime from article where id = :id) and deleted = 0 order by ctime desc limit 1";
        return getArticleHandle(sql, id);
    }


    /**
     * 查询下一条数据
     * @param id
     * @return
     */
    public Article getNextArticle(String id){
        String sql = "select * from article where ctime < (select ctime from article where id = :id) and deleted = 0 order by ctime desc limit 1";
        return getArticleHandle(sql, id);
    }

    private Article getArticleHandle(String sql, String id) {
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(blogTemplate);
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(ID, id);
        try {
            RowMapper<Article> rowMapper = new BeanPropertyRowMapper<>(Article.class);
            return namedParameterJdbcTemplate.queryForObject(sql, mapSqlParameterSource, rowMapper);
        } catch (Exception e) {
            log.error("getArticle: {}, {}, {}", sql, id, e);
            return new Article();
        }
    }



    /**
     * 删除
     * @param id
     * @return
     */
    public boolean deleteArticle(String id){
        String sql = "update article set deleted = 1 where  id = :id";
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(blogTemplate);
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(ID, id);
        try {
            namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
            return true;
        } catch (Exception e) {
            log.error("deleteArticle: {}, {}", id, e);
            return false;
        }
    }

    /**
     * 创建
     * @param article
     * @return
     */
    public Article createArticle(Article article){
        String sql = "insert into article(id, title, classify, content, create_author_id, update_author_id, ctime, mtime, version) " +
                "value(:id, :title, :classify, :content, :create_author_id, :update_author_id, :ctime, :mtime, 1)";


        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(blogTemplate);
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        String id = UUID.randomUUID().toString();
        mapSqlParameterSource.addValue(ID, id);
        mapSqlParameterSource.addValue(TITLE, article.getTitle());
        mapSqlParameterSource.addValue(CLASSIFY, article.getClassify());
        mapSqlParameterSource.addValue(CONTENT, article.getContent());
        mapSqlParameterSource.addValue(CREATE_AUTHOR_ID, article.getCreateAuthorId());
        mapSqlParameterSource.addValue(UPDATE_AUTHOR_ID, article.getUpdateAuthorId());
        mapSqlParameterSource.addValue(CTIME, System.currentTimeMillis());
        mapSqlParameterSource.addValue(MTIME, System.currentTimeMillis());
        try {
            namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);

        } catch (Exception e) {
            log.error("createArticle: {}, {}", article, e);
            return new Article();
        }
        return getArticle(id);
    }


    /**
     * 修改
     * @param article
     * @return
     */
    public boolean updateArticle(Article article){
        String sql = "update article set version = version + 1";

        sql = SqlUtil.sqlFormat(sql, MTIME);
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(MTIME, System.currentTimeMillis());
        int num = 0;
        if (null != article.getDeleted()) {
            sql = SqlUtil.sqlFormat(sql, DELETED);
            mapSqlParameterSource.addValue(DELETED, article.getDeleted());
            num ++;
        }

        if (null != article.getTitle()) {
            sql = SqlUtil.sqlFormat(sql, TITLE);
            mapSqlParameterSource.addValue(TITLE, article.getTitle());
            num ++;
        }
        if (null != article.getClassify()) {
            sql = SqlUtil.sqlFormat(sql, CLASSIFY);
            mapSqlParameterSource.addValue(CLASSIFY, article.getClassify());
            num ++;
        }
        if (null != article.getContent()) {
            sql = SqlUtil.sqlFormat(sql, CONTENT);
            mapSqlParameterSource.addValue(CONTENT, article.getContent());
            num ++;
        }
        if (null != article.getUpdateAuthorId()) {
            sql = SqlUtil.sqlFormat(sql, UPDATE_AUTHOR_ID);
            mapSqlParameterSource.addValue(UPDATE_AUTHOR_ID, article.getUpdateAuthorId());
            num ++;
        }
        if (num == 0) {
            log.info("updateArticle: {}, {}", article, "no update");
            return false;
        }
        sql = String.format("%s where %s = :%s", sql, ID, ID);
        mapSqlParameterSource.addValue(ID, article.getId());
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(blogTemplate);
        try {
            namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
            return true;
        } catch (Exception e) {
            log.error("updateArticle: {}, {}", article, e);
            return false;
        }
    }


}
