package com.example.demo.controller;

import com.example.demo.common.resultbean.MyResult;
import com.example.demo.domain.MiaoshaUser;
import com.example.demo.domain.OrderInfo;
import com.example.demo.redis.RedisService;
import com.example.demo.service.GoodsService;
import com.example.demo.service.MiaoShaUserService;
import com.example.demo.service.OrderService;
import com.example.demo.vo.GoodsVo;
import com.example.demo.vo.OrderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.common.enums.ResultStatus.ORDER_NOT_EXIST;
import static com.example.demo.common.enums.ResultStatus.SESSION_ERROR;

/**
 * @author: songqiang
 * @date: 2020/2/29
 */
@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    MiaoShaUserService userService;

    @Autowired
    RedisService redisService;

    /**
     * 获取订单详情
     *
     * @param user
     * @param orderId
     * @param model
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ResponseBody
    public MyResult<OrderDetailVo> orderInfo(MiaoshaUser user,
                                             @RequestParam("orderId") long orderId,
                                             Model model){
        MyResult<OrderDetailVo> result = MyResult.build();
        if (user == null) {
            result.withError(SESSION_ERROR.getCode(), SESSION_ERROR.getMessage());
            return result;
        }

        OrderInfo orderInfo = orderService.getOrderById(orderId);
        if (orderInfo == null){
            result.withError(ORDER_NOT_EXIST.getCode(), ORDER_NOT_EXIST.getMessage());
            return result;
        }

        long goodsId = orderInfo.getGoodsId();
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        OrderDetailVo  detailVo = new OrderDetailVo();
        detailVo.setOrder(orderInfo);
        detailVo.setGoods(goodsVo);
        result.setData(detailVo);
        return result;
    }

}
