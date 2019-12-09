package com.example.demo.controller;

import com.example.demo.common.resultbean.MyResult;
import com.example.demo.redis.redismanager.RedisLua;
import com.example.demo.service.MiaoShaUserService;
import com.example.demo.vo.LoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static com.example.demo.common.Constants.COUNTLOGIN;

/**
 * LoginController class
 *
 * @author maco
 * @data 2019/10/23
 */
@Slf4j
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private MiaoShaUserService userService;

    @RequestMapping("/to_login")
    public String tologin(LoginVo loginVo, Model model){
        log.info(loginVo.toString());
        RedisLua.visitorCount(COUNTLOGIN);
        String count = RedisLua.getVistorCount(COUNTLOGIN).toString();
        log.info("访问网站的次数为:{}", count);
        model.addAttribute("count", count);
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public MyResult<Boolean> doLogin(HttpServletResponse response, @Valid LoginVo loginVo){
        MyResult<Boolean> result = MyResult.build();
        userService.login(response, loginVo);
        return result;
    }

}
