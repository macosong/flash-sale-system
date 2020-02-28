package com.example.demo.service;

import com.example.demo.dao.OrderMapper;
import com.example.demo.domain.MiaoshaOrder;
import com.example.demo.domain.MiaoshaUser;
import com.example.demo.domain.OrderInfo;
import com.example.demo.redis.OrderKey;
import com.example.demo.redis.RedisService;
import com.example.demo.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * OrderService
 *
 * @author Qiang Song
 * @data 2019/11/17
 */
@Service
public class OrderService {
    @Autowired
    OrderMapper orderMapper;

    @Autowired
    private RedisService redisService;

    public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(Long userId, long goodsId) {
        return redisService.get(OrderKey.getMiaosaOrderByUidGid, "" + userId + "_" + goodsId, MiaoshaOrder.class);
    }

    public OrderInfo getOrderById(long orderId) {
        return orderMapper.getOrderById(orderId);
    }

    @Transactional
    public OrderInfo createOrder(MiaoshaUser user, GoodsVo goods) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setGoodsPrice(goods.getMiaoshaPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setUserId(Long.valueOf(user.getNickname()));
        orderMapper.insert(orderInfo);

        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        miaoshaOrder.setGoodsId(goods.getId());
        miaoshaOrder.setOrderId(orderInfo.getId());
        miaoshaOrder.setUserId(Long.valueOf(user.getNickname()));
        orderMapper.insertMiaoshaOrder(miaoshaOrder);
        redisService.set(OrderKey.getMiaosaOrderByUidGid, "" + user.getNickname() + "_" + goods.getId(), miaoshaOrder);
        return orderInfo;
    }


}
