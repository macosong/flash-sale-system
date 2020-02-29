package com.example.demo.controller;

import com.example.demo.common.resultbean.MyResult;
import com.example.demo.domain.MiaoshaUser;
import com.example.demo.redis.GoodsKey;
import com.example.demo.redis.RedisService;
import com.example.demo.service.GoodsService;
import com.example.demo.service.MiaoShaUserService;
import com.example.demo.vo.GoodsDetailVo;
import com.example.demo.vo.GoodsVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.context.webflux.SpringWebFluxContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * GoodsController
 *
 * @author maco
 * @data 2019/10/28
 */
@Slf4j
@Controller
@RequestMapping("/goods")
public class GoodsController extends BaseController {

    @Autowired
    private MiaoShaUserService userService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    ThymeleafViewResolver viewResolver;

    @Autowired
    ApplicationContext applicationContext;

    @RequestMapping(value = "/to_list", produces = "text/html")
    @ResponseBody
    public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("user", userService.getFromCookie(request));
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList", goodsList);
        return render(request, response, model, "goods_list", GoodsKey.getGoodsList, "");
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET, produces = "text/html")
    @ResponseBody
    public String detail(HttpServletRequest request, HttpServletResponse response, Model model, MiaoshaUser user) {
        long goodsId = Long.parseLong(request.getParameter("goodsId"));
        model.addAttribute("user", user);

        String html = redisService.get(GoodsKey.getGoodsDetail, "" + goodsId, String.class);

        if (!StringUtils.isEmpty(html)) {
            return html;
        }

        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods", goods);

        long startTime = goods.getStartDate().getTime();
        long endTime = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();

        //TODO:修改参数测试，初始化都是0
        int miaoshaStatus = 1;
        int remainSeconds = 5;
//        if (now < startTime) {
//            remainSeconds = (int) ((startTime - now) / 1000);
//        } else if (now > endTime) {
//            miaoshaStatus = 2;
//            remainSeconds = -1;
//        }

        model.addAttribute("miaoshaStatus", miaoshaStatus);
        model.addAttribute("remainSeconds", remainSeconds);

        WebContext context = new WebContext(request, response,
                request.getServletContext(), request.getLocale(), model.asMap());
        html = viewResolver.getTemplateEngine().process("goods_detail", context);
        redisService.set(GoodsKey.getGoodsDetail, "" + goodsId, html);
        return html;
    }

    @RequestMapping(value="/detail/{goodsId}")
    @ResponseBody
    public MyResult<GoodsDetailVo> detail(HttpServletRequest request, HttpServletResponse response, Model model, MiaoshaUser user,
                                          @PathVariable("goodsId")long goodsId) {
        MyResult<GoodsDetailVo> result = MyResult.build();
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();
        //TODO:修改参数测试，初始化都是0
        int miaoshaStatus = 1;
        int remainSeconds = 5;
//        if(now < startAt ) {//秒杀还没开始，倒计时
//            miaoshaStatus = 0;
//            remainSeconds = (int)((startAt - now )/1000);
//        }else  if(now > endAt){//秒杀已经结束
//            miaoshaStatus = 2;
//            remainSeconds = -1;
//        }else {//秒杀进行中
//            miaoshaStatus = 1;
//            remainSeconds = 0;
//        }
        GoodsDetailVo vo = new GoodsDetailVo();
        vo.setGoods(goods);
        vo.setUser(user);
        vo.setRemainSeconds(remainSeconds);
        vo.setMiaoshaStatus(miaoshaStatus);
        result.setData(vo);
        return result;
    }

}
