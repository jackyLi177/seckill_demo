package com.jacky.seckill_demo.mapper;

import com.jacky.seckill_demo.model.ProductRecord;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProductRecordMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ProductRecord record);

    int insertSelective(ProductRecord record);

    ProductRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProductRecord record);

    int updateByPrimaryKey(ProductRecord record);

    int truncateTable();
}