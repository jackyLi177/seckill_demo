package com.jacky.seckill_demo.service;

import com.jacky.seckill_demo.controller.SecKillController;
import com.jacky.seckill_demo.mapper.ProductRecordMapper;
import com.jacky.seckill_demo.mapper.ProductStorageMapper;
import com.jacky.seckill_demo.model.ProductRecord;
import com.jacky.seckill_demo.model.ProductStorage;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.transaction.Transaction;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Random;

/**
 * @Description
 * @Author liyj
 * @Date 2020/12/10 3:49 下午
 */
@Slf4j
@Service
public class ProductRecordService {

    @Autowired
    private ProductRecordMapper recordMapper;
    @Autowired
    private ProductStorageMapper storageMapper;
    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;
    @Autowired
    private TransactionDefinition transactionDefinition;

    Random random = new Random();

    private final static Object lock = new Object();

    //    @Transactional
    public synchronized String buySth(String userId, String productId) {
        int num = random.nextInt(10) + 1;
        // 查库存
        ProductStorage storage = storageMapper.getByProductId(productId);
        Long oldAmount = storage.getAmount();
        if (num > oldAmount) {
            log.info(userId + " request " + num + " fail");
            return userId + " request " + num + " fail";
        }
        //获取事务状态对象，开启事务
        TransactionStatus transaction = dataSourceTransactionManager.getTransaction(transactionDefinition);
        try {
            storage.setAmount(oldAmount - num);
            log.info(userId + " request " + num + " " + productId + " 当前 " + oldAmount);
            // 减库存
            int res = storageMapper.updateByPrimaryKeySelective(storage);
            if (res > 0) {
                ProductRecord productRecord = new ProductRecord();
                productRecord.setProductId(productId);
                productRecord.setUserId(userId);
                productRecord.setNum(num);
                recordMapper.insert(productRecord);
                //事务提交
                dataSourceTransactionManager.commit(transaction);
                return userId + "request " + num + " success" + " 当前 " + oldAmount;
            } else {
                //事务回滚
                dataSourceTransactionManager.rollback(transaction);
                return userId + "request " + num + " fail";
            }
        } catch (Exception e) {
            dataSourceTransactionManager.rollback(transaction);
        } finally {
            return userId + "request " + num + " fail";
        }
    }

    public void init() {
        recordMapper.truncateTable();
        storageMapper.initNum();
    }
}
