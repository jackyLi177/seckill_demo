package com.jacky.seckill_demo.service;

import com.jacky.seckill_demo.mapper.ProductRecordMapper;
import com.jacky.seckill_demo.mapper.ProductStorageMapper;
import com.jacky.seckill_demo.model.ProductRecord;
import com.jacky.seckill_demo.model.ProductStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import java.util.concurrent.TimeUnit;

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
    private StringRedisTemplate redis;

    Random random = new Random();

    private final static Object lock = new Object();

    @Transactional
    public String buySth(String userId, String productId) {
        int num = random.nextInt(10) + 1;

        //获取锁
        Boolean lock = redis.opsForValue().setIfAbsent("skill::" + productId, userId, 3, TimeUnit.SECONDS);
        if (lock) {
            try {
                // 查库存
                ProductStorage storage = storageMapper.getByProductId(productId);
                Long oldAmount = storage.getAmount();
                if (num > oldAmount) {
                    return userId + " request " + num + " fail";
                }
                storage.setAmount(oldAmount - num);
                log.info(userId + " request " + num + " " + productId + " 当前 " + oldAmount + " success");
                // 减库存
                int res = storageMapper.updateByPrimaryKeySelective(storage);
                if (res > 0) {
                    ProductRecord productRecord = new ProductRecord();
                    productRecord.setProductId(productId);
                    productRecord.setUserId(userId);
                    productRecord.setNum(num);
                    recordMapper.insert(productRecord);
                    return userId + "request " + num + " success" + " 当前 " + oldAmount;
                } else {
                    return userId + "request " + num + " fail";
                }
            } finally {
                String currUser = redis.opsForValue().get("skill::" + productId);
                if (userId.equals(currUser)) {
                    redis.delete("skill" + productId);
                }
            }
        } else {
//            log.info(userId + "request lock fail");
            return userId + "request " + num + " fail";
        }
    }

    public void init() {
        recordMapper.truncateTable();
        storageMapper.initNum();
    }
}
