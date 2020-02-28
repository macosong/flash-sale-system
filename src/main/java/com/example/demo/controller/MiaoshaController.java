package com.example.demo.controller;

import com.example.demo.Access.AccessLimit;
import com.example.demo.common.enums.ResultStatus;
import com.example.demo.common.resultbean.MyResult;
import com.example.demo.domain.MiaoshaOrder;
import com.example.demo.domain.MiaoshaUser;
import com.example.demo.rabbitmq.MQSender;
import com.example.demo.rabbitmq.MiaoshaMessage;
import com.example.demo.redis.GoodsKey;
import com.example.demo.redis.RedisService;
import com.example.demo.service.GoodsService;
import com.example.demo.service.MiaoShaUserService;
import com.example.demo.service.MiaoshaService;
import com.example.demo.service.OrderService;
import com.example.demo.vo.GoodsVo;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.*;

import static com.example.demo.common.enums.ResultStatus.*;

/**
 * MiaoshaController
 *
 * @author maco
 * @data 2019/10/28
 */
@Slf4j
@Controller
@RequestMapping("/miaosha")
public class MiaoshaController implements InitializingBean {
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private MiaoShaUserService userService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private MiaoshaService miaoshaService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MQSender mqSender;

    private HashMap<Long, Boolean> localOverMap = new HashMap<>();

    /**
     * 秒杀的业务方法
     *
     * @param model
     * @param path
     * @param goodsId
     * @param request
     * @return
     */
    @AccessLimit(seconds = 5, maxCount = 5, needLogin = true)
    @RequestMapping(value = "/{path}/do_miaosha", method = RequestMethod.POST)
    @ResponseBody
    public MyResult<Integer> flashBuy(Model model,
                                      @PathVariable("path") String path,
                                      @RequestParam("goodsId") long goodsId,
                                      HttpServletRequest request) {
        MyResult<Integer> result = MyResult.build();
        MiaoshaUser user = userService.getFromCookie(request);

        if (user == null) {
            result.withError(SESSION_ERROR.getCode(), SESSION_ERROR.getMessage());
            return result;
        }

        //验证path，限流
        boolean check = miaoshaService.checkPath(user, goodsId, path);
        if (!check) {
            result.withError(REQUEST_ILLEGAL.getCode(), REQUEST_ILLEGAL.getMessage());
            return result;
        }

        //检测当前用户是否已经秒杀到，避免重复订单
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(Long.valueOf(user.getNickname()), goodsId);
        if (order != null) {
            result.withError(REPEATE_MIAOSHA.getCode(), REPEATE_MIAOSHA.getMessage());
            return result;
        }

        //内存标记这个商品的秒杀活动是否已经结束
        boolean over = localOverMap.get(goodsId);
        if (over) {
            result.withError(MIAO_SHA_OVER.getCode(), MIAO_SHA_OVER.getMessage());
            return result;
        }

        //redis库存预减小
        Long stock = redisService.decr(GoodsKey.getMiaoshaGoodsStock, "" + goodsId);
        if (stock < 0) {
            localOverMap.put(goodsId, true);
            result.withError(MIAO_SHA_OVER.getCode(), MIAO_SHA_OVER.getMessage());
            return result;
        }

        MiaoshaMessage mm = new MiaoshaMessage();
        mm.setGoodsId(goodsId);
        mm.setUser(user);
        mqSender.sendMiaoshaMessage(mm);
        return result;
    }


    /**
     * 注册时验证码的逻辑
     *
     * @param response
     * @return
     */
    @GetMapping("/verifyCodeRegister")
    @ResponseBody
    public MyResult<String> getMiaoshaVerifyCode(HttpServletResponse response) {
        MyResult<String> result = MyResult.build();
        try {
            BufferedImage image = miaoshaService.createVerifyCodeRegister();
            OutputStream out = response.getOutputStream();
            ImageIO.write(image, "JPEG", out);
            out.flush();
            out.close();
            return result;
        } catch (Exception e) {
            log.error("生成验证码错误———注册：{}", e);
            result.withError(MIAOSHA_FAIL.getCode(), MIAOSHA_FAIL.getMessage());
            return result;
        }
    }


    /**
     * 系统初始化
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        if (goodsList == null) {
            return;
        }

        for (GoodsVo goods : goodsList) {
            redisService.set(GoodsKey.getMiaoshaGoodsStock, "" + goods.getId(), goods.getStockCount());
            localOverMap.put(goods.getId(), false);
        }


    }
}
