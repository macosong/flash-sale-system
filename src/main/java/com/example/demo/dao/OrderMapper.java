package com.example.demo.dao;

import com.example.demo.domain.MiaoshaOrder;
import com.example.demo.domain.OrderInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * OrderMapper
 *
 * @author Qiang Song
 * @data 2019/11/17
 */

@Mapper
public interface OrderMapper {
    @Select("select * from miaosha_order where user_id=#{userNickName} and goods_id=#{goodsId}")
    MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(@Param("userNickName") long userNickName, @Param("goodsId") long goodsId);

    @Insert("insert into order_info(user_id, goods_id, goods_name, goods_count, goods_price, order_channel, status, create_date)values("
            + "#{userId}, #{goodsId}, #{goodsName}, #{goodsCount}, #{goodsPrice}, #{orderChannel},#{status},#{createDate} )")
    @SelectKey(keyColumn = "id", keyProperty = "id", resultType = long.class, before = false, statement = "select last_insert_id()")
    long insert(OrderInfo orderInfo);

    @Insert("insert into miaosha_order (user_id, goods_id, order_id)values(#{userId}, #{goodsId}, #{orderId})")
    int insertMiaoshaOrder(MiaoshaOrder miaoshaOrder);

    @Select("select * from order_info where id = #{orderId}")
   OrderInfo getOrderById(@Param("orderId") long orderId);

    @Select("select * from order_info where status=#{status} and create_Date<=#{createDate}")
    public List<OrderInfo> selectOrderStatusByCreateTime(@Param("status") Integer status, @Param("createDate") String createDate);

    @Select("update order_info set status=0 where id=#{id}")
    int closeOrderByOrderInfo();
}
