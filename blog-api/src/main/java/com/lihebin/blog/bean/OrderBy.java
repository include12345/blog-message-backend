package com.lihebin.blog.bean;

/**
 * Created by lihebin on 2018/12/2.
 */
public class OrderBy {
    private String field;
    private OrderType order;

    public OrderBy() {
    }

    public OrderBy(String field, OrderType order) {
        this.field = field;
        this.order = order;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public OrderType getOrder() {
        return order;
    }

    public void setOrder(OrderType order) {
        this.order = order;
    }

    public static enum OrderType {
        ASC(1), DESC(-1);
        private int value;

        OrderType(int order) {
            value = order;
        }

        public int getValue() {
            return value;
        }
    }
}
