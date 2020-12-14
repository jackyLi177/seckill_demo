package com.jacky.seckill_demo.model;

import java.io.Serializable;

/**
 * @author 
 */
public class ProductRecord implements Serializable {
    private Long id;

    /**
     * 商品id
     */
    private String productId;

    /**
     * 买主id
     */
    private String userId;

    private Integer num;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}