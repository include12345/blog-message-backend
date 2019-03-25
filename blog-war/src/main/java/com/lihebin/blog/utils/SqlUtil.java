package com.lihebin.blog.utils;

import com.google.common.collect.Maps;
import com.lihebin.blog.bean.DaoConstants;
import com.lihebin.blog.bean.OrderBy;
import com.lihebin.blog.bean.PageInfo;
import org.apache.commons.collections.MapUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by lihebin on 2018/12/2.
 */
public class SqlUtil {

    /**
     * sql拼接
     * @param
     * @return
     */
//    public static List<Map<String, Object>> appendCriteriaParams(JdbcTemplate jdbcTemplate, String tableName, Map<String,Object> params, List<String> paramsKeys) {
//        String sql = String.format("select * from %s where 1 = 1", tableName);
//        Map<String, Object> newParams = Maps.filterKeys(params, paramsKeys::contains);
//        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
//        for (String key : newParams.keySet()) {
//            if (newParams.get(key).equals("NULL")) {
//                sql = String.format("%s and %s is null", sql, key);
//            }
//            sql = String.format("%s and %s:%s", sql, key, key);
//            parameterSource.addValue(key, newParams.get(key));
//        }
//        return jdbcTemplate.queryForList(sql, parameterSource);
//    }


    public static String sqlFormat(String sql, String key) {
        return String.format("%s, %s =:%s", sql, key, key);
    }


    public static String sqlAndFormat(String sql, String key) {
        return String.format("%s and %s =:%s", sql, key, key);
    }

    /**
     * sql拼接
     * @param params
     * @return
     */
    public static String appendCriteriaParams(String tableName, Map<String, Object> params, List<String> paramsKeys) {
        String sql = String.format("select * from %s where 1 = 1", tableName);
        Map<String, Object> newParams = Maps.filterKeys(params, paramsKeys::contains);
            for (String key : newParams.keySet()) {
                if (MapUtils.getObject(newParams, key) == null) {
                    continue;
                }
                if (newParams.get(key).equals("NULL")) {
                    sql = String.format("%s and %s is null", sql, key);
                    continue;
                }
                sql = String.format("%s and %s = :%s", sql, key, key);
            }
        return sql;
    }



    /**
     * 查询条件-时间.
     *
     * @param sql
     * @param pageInfo
     */
    public static String appendCriteriaDateInfo(String sql, PageInfo pageInfo) {
        if (pageInfo.getDateStart() != null) {
            sql = String.format("%s and ctime >=%d", sql, pageInfo.getDateStart());
        }
        if (pageInfo.getDateEnd() != null) {
            sql = String.format("%s and ctime <%d", sql, pageInfo.getDateEnd());
        }
        if (pageInfo.getOrderBy() == null || pageInfo.getOrderBy().size() == 0) {

            pageInfo.setOrderBy(Arrays.asList(
                    new OrderBy(DaoConstants.CTIME, OrderBy.OrderType.DESC)
            ));
        }
        return sql;
    }

    /**
     * 分页排序-如果没有设置排序，则默认创建时间倒叙.
     *
     * @param pageInfo
     */
    public static String appendPageInfoCtimeDesc(String sql, PageInfo pageInfo) {
        if (pageInfo.getOrderBy() == null || pageInfo.getOrderBy().size() == 0) {
            pageInfo.setOrderBy(Arrays.asList(
                    new OrderBy(DaoConstants.CTIME, OrderBy.OrderType.DESC)
            ));
            if (pageInfo.getPage() == null || pageInfo.getPageSize() == null) {
                pageInfo.setPage(1);
                pageInfo.setPageSize(100);
            }
        }
        return String.format("%s order by %s %s limit %d,%d", sql, pageInfo.getOrderBy().get(0).getField(), pageInfo.getOrderBy().get(0).getOrder(), (pageInfo.getPage() - 1) * pageInfo.getPageSize(), pageInfo.getPageSize());
    }


}
