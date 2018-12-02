package com.lihebin.blog.bean;

import java.util.List;

/**
 * Created by lihebin on 2018/12/2.
 */
public class PageInfo {
    private Integer page;
    private Integer pageSize;
    private Long dateStart;
    private Long dateEnd;
    private List<OrderBy> orderBy;

    public PageInfo() {

    }

    public PageInfo(Integer page, Integer pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }

    public PageInfo(Integer page, Integer pageSize, Long dateStart, Long dateEnd) {
        this.page = page;
        this.pageSize = pageSize;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public PageInfo(Integer page, Integer pageSize, Long dateStart, Long dateEnd, List<OrderBy> orderBy) {
        this.page = page;
        this.pageSize = pageSize;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.orderBy = orderBy;
    }


    public List<OrderBy> getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(List<OrderBy> orderBy) {
        this.orderBy = orderBy;
    }

    @Override
    protected Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }


    }

    public Integer getPage() {
        return page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Long getDateStart() {
        return dateStart;
    }

    public Long getDateEnd() {
        return dateEnd;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setDateStart(Long dateStart) {
        this.dateStart = dateStart;
    }

    public void setDateEnd(Long dateEnd) {
        this.dateEnd = dateEnd;
    }
}
