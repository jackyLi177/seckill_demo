package com.jacky.seckill_demo.controller;

import com.jacky.seckill_demo.service.ProductRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description
 * @Author liyj
 * @Date 2020/12/10 3:42 下午
 */
@RestController
public class SecKillController {

    @Autowired
    private ProductRecordService recordService;

    public static AtomicInteger userSalt = new AtomicInteger(0);

    @GetMapping("/buySth")
    public String buySth(/*@RequestParam String userId,*/@RequestParam String productId) throws SQLException {
        return recordService.buySth(UUID.randomUUID().toString(),productId);
    }

    @GetMapping("/hello")
    public String hello(){
        System.out.println("hello");
        return "hello";
    }

    @GetMapping("/init")
    public String init(){
        SecKillController.userSalt.set(1);
        recordService.init();
        System.err.println("------------->" + SecKillController.userSalt);;
        return "init success";
    }

}
