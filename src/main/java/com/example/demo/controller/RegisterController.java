package com.example.demo.controller;

import com.example.demo.common.resultbean.MyResult;
import com.example.demo.service.MiaoShaUserService;
import com.example.demo.service.MiaoshaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

import static com.example.demo.common.enums.ResultStatus.*;

/**
 * RegisterController
 *
 * @author maco
 * @data 2019/10/28
 */
@Slf4j
@Controller
@RequestMapping("/user")
public class RegisterController {
    @Autowired
    private MiaoShaUserService miaoShaUserService;

    @Autowired
    private MiaoshaService miaoshaService;

    @RequestMapping("/do_register")
    public String registerIndex() { return "register"; }

    /**
     * 网站注册
     *
     * @param userName
     * @param password
     * @param verifyCode
     * @param salt
     * @param response
     * @return
     */
    @PostMapping("/register")
    @ResponseBody
    public MyResult<String> register(@RequestParam("username") String userName,
                                     @RequestParam("password") String password,
                                     @RequestParam("verifyCode") String verifyCode,
                                     @RequestParam("salt") String salt,
                                     HttpServletResponse response){
        MyResult<String> result = MyResult.build();

        //校验验证码
        boolean check = miaoshaService.checkVerifyCodeRegister(Integer.parseInt(verifyCode));
        if (!check){
            result.withError(CODE_FAIL.getCode(), CODE_FAIL.getMessage());
            return result;
        }

        //注册
        boolean registerInfo = miaoShaUserService.register(response,userName, password, salt);
        if (!registerInfo){
            result.withError(REGISTER_FAIL.getCode(), REGISTER_FAIL.getMessage());
            return result;
        }
        return result;
    }
}
