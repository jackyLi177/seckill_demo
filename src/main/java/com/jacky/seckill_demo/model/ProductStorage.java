package com.jacky.seckill_demo.model;

import java.io.Serializable;

/**
 * @author 
 */
public class ProductStorage implements Serializable {
    private Long id;

    /**
     * 商品ID
     */
    private String productId;

    /**
     * 库存数
     */
    private Long amount;

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

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}