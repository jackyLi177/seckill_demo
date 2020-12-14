package com.jacky.seckill_demo.mapper;

import com.jacky.seckill_demo.model.ProductStorage;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductStorageMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ProductStorage record);

    int insertSelective(ProductStorage record);

    ProductStorage selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductStorage record);

    int updateByPrimaryKey(ProductStorage record);

    ProductStorage getByProductId(String productId);

    int initNum();
}