package com.example.demo.controller;

import com.example.demo.common.enums.ResultStatus;
import com.example.demo.common.resultbean.MyResult;
import com.example.demo.redis.GoodsKey;
import com.example.demo.redis.RedisService;
import com.example.demo.service.GoodsService;
import com.example.demo.service.MiaoshaService;
import com.example.demo.vo.GoodsVo;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
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
    private RedisService redisService;

    @Autowired
    private MiaoshaService miaoshaService;

    private HashMap<Long, Boolean> localOverMap = new HashMap<>();

    @GetMapping("/verifyCodeRegister")
    @ResponseBody
    public MyResult<String> getMiaoshaVerifyCode(HttpServletResponse response){
        MyResult<String> result = MyResult.build();
        try{
            BufferedImage image = miaoshaService.createVerifyCodeRegister();
            OutputStream out = response.getOutputStream();
            ImageIO.write(image, "JPEG", out);
            out.flush();
            out.close();
            return result;
        }catch (Exception e){
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
        if (goodsList == null){
            return;
        }

        for (GoodsVo goods : goodsList){
            redisService.set(GoodsKey.getMiaoshaGoodsStock, "" + goods.getId(), goods.getStockCount());
            localOverMap.put(goods.getId(), false);
        }


    }
}
