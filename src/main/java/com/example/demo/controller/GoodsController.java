package com.example.demo.controller;

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
    public String detail(HttpServletRequest request, HttpServletResponse response, Model model) {
        long goodsId = Long.parseLong(request.getParameter("goodsId"));
        MiaoshaUser user = userService.getFromCookie(request);
        model.addAttribute("user", user);

        String html = redisService.get(GoodsKey.getGoodsDetail, "" + goodsId, String.class);

        if (!StringUtils.isEmpty(html)) {
            return html;
        }

        GoodsVo goods = goodsService.getCoodsVoByGoodsId(goodsId);
        model.addAttribute("goods", goods);

        long startTime = goods.getStartDate().getTime();
        long endTime = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();

        int miaoshaStatus = 0;
        int remainSeconds = 0;
        if (now < startTime) {
            remainSeconds = (int) ((startTime - now) / 1000);
        } else if (now > endTime) {
            miaoshaStatus = 2;
            remainSeconds = -1;
        }

        model.addAttribute("miaoshaStatus", miaoshaStatus);
        model.addAttribute("remainSeconds", remainSeconds);

        WebContext context = new WebContext(request, response,
                request.getServletContext(), request.getLocale(), model.asMap());
        html = viewResolver.getTemplateEngine().process("goods_detail", context);
        redisService.set(GoodsKey.getGoodsDetail, "" + goodsId, html);
        return html;
    }

}
