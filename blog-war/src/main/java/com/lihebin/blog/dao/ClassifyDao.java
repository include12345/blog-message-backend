package com.lihebin.blog.dao;

import com.lihebin.blog.bean.Article;
import com.lihebin.blog.bean.Articles;
import com.lihebin.blog.bean.Classify;
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
public class ClassifyDao {
    private final static Logger log = LoggerFactory.getLogger(ClassifyDao.class);


    @Resource(name = "blogJdbcTemplate")
    JdbcTemplate blogTemplate;

    private static final String SN = "sn";
    private static final String NAME = "name";

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;



    /**
     * 查询所有的标签
     * @return
     */
    public List<String> listClassify(){
        String sql = "select name from classify order by sn";
        try {
            return blogTemplate.queryForList(sql, String.class);
        } catch (Exception e) {
            log.error("getClassify:", e);
            return new ArrayList<>();
        }
    }


    /**
     * 根据编号查标签
     * @param sn
     * @return
     */
    public String getClassifyBySn(int sn){
        String sql = "select name from classify where sn = :sn";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(SN, sn);
        try {
            namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(blogTemplate);
            return namedParameterJdbcTemplate.queryForObject(sql, mapSqlParameterSource, String.class);
        } catch (Exception e) {
            log.error("getClassifyBySn: {}", sn, e);
            return null;
        }
    }


    /**
     * 根据标签查编号
     * @param name
     * @return
     */
    public Integer getClassifyByName(String name){
        String sql = "select sn from classify where name = :name";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(NAME, name);
        try {
            namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(blogTemplate);
            return namedParameterJdbcTemplate.queryForObject(sql, mapSqlParameterSource, Integer.class);
        } catch (Exception e) {
            log.error("getClassifyByName: {}", name, e);
            return 0;
        }
    }


    /**
     * 删除
     * @param sn
     * @return
     */
    public boolean deleteClassify(int sn){
        String sql = "delete from classify where sn = :sn";
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(blogTemplate);
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue(SN, sn);
        try {
            namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
            return true;
        } catch (Exception e) {
            log.error("deleteClassify: {}", sn, e);
            return false;
        }
    }

    /**
     * 创建
     * @param name
     * @return
     */
    public Integer createClassify(String name){
        int sn = getClassifyByName(name);
        if (sn != 0) {
            return sn;
        }
        String sql = "insert into article(name) value(:name)";

        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(blogTemplate);
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        String id = UUID.randomUUID().toString();
        mapSqlParameterSource.addValue(NAME, name);
        try {
            namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
        } catch (Exception e) {
            log.error("createClassify: {}, {}", name, e);
            return 0;
        }
        return getClassifyByName(name);
    }


}
