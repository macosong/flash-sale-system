package com.example.demo.service;

import com.example.demo.dao.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * OrderService
 *
 * @author Qiang Song
 * @data 2019/11/17
 */
public class OrderService {
    @Autowired
    OrderMapper orderMapper;
}
