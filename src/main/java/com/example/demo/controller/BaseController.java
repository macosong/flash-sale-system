package com.example.demo.controller;

import com.example.demo.redis.KeyPrefix;
import com.example.demo.redis.RedisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * BaseController
 *
 * @author maco
 * @data 2019/10/28
 */
@Controller
public class BaseController {
    @Value("#{'true'}")
    private boolean pageCacheEnable;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    RedisService redisService;

    public String render(HttpServletRequest request, HttpServletResponse response, Model model, String tplName, KeyPrefix prefix, String key){
        if (!pageCacheEnable){
            return tplName;
        }

        String html = redisService.get(prefix, key, String.class);
        if (!StringUtils.isEmpty(html)){
            out(response, html);
            return null;
        }

        //手动渲染
        WebContext ctx = new WebContext(request, response,
                request.getServletContext(),request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process(tplName, ctx);
        redisService.set(prefix, key ,html);
        out(response, html);
        return null;
    }

    public static void out(HttpServletResponse response, String html){
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        try{
            OutputStream out = response.getOutputStream();
            out.write(html.getBytes("UTF-8"));
            out.flush();
            out.close();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

}
