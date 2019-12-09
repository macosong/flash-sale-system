package com.example.demo.service;

import com.example.demo.dao.MiaoShaUserMapper;
import com.example.demo.domain.MiaoshaUser;
import com.example.demo.exception.GlobalException;
import com.example.demo.redis.MiaoShaUserKey;
import com.example.demo.redis.RedisService;
import com.example.demo.utils.MD5Utils;
import com.example.demo.utils.UUIDUtil;
import com.example.demo.vo.LoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Date;

import static com.example.demo.common.enums.ResultStatus.*;


/**
 * MiaoShaUserService
 *
 * @author maco
 * @data 2019/10/24
 */
@Slf4j
@Service
public class MiaoShaUserService {
    private static final String COOKIE_NAME_TOKEN = "token";

    @Autowired
    private MiaoShaUserMapper miaoShaUserMapper;

    @Autowired
    private RedisService redisService;

    public MiaoshaUser getFromCookie(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String nickName = "";
        for (Cookie cookie : cookies){
            if ("nickname".equals(cookie.getName())){
                nickName = cookie.getValue();
                break;
            }
        }
        if (nickName.equals("")){
            throw new GlobalException(USER_NOT_EXIST);
        }
        return getByNickName(nickName);
    }

    /**
     * 通过昵称从redis中获取MiaoshaUser对象
     *
     * @param nickName
     * @return
     */
    public MiaoshaUser getByNickName(String nickName){
        //从redis通过nickname获取MiaoshaUser对象
        MiaoshaUser user = redisService.get(MiaoShaUserKey.getByNichName,"" + nickName, MiaoshaUser.class);
        if (user != null){
            return user;
        }
        //缓存未命中，取数据库
        user = miaoShaUserMapper.getByNickname(nickName);
        if (user != null){
            redisService.set(MiaoShaUserKey.getByNichName, "" + nickName, user);
        }
        return user;
    }


    /**
     * 处理用户登录
     *
     * @param response
     * @param loginVo
     * @return
     */
    public boolean login(HttpServletResponse response, LoginVo loginVo){
        if (loginVo == null){
            throw new GlobalException(SYSTEM_ERROR);
        }

        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        MiaoshaUser user = getByNickName(mobile);
        if (user == null){
            throw new GlobalException(MOBILE_NOT_EXIST);
        }
        String dbPass = user.getPassword();
        String saltDb = user.getSalt();
        String calcPass = MD5Utils.formPassToDBPass(password, saltDb);
        if (!calcPass.equals(dbPass)){
            throw new GlobalException(PASSWORD_ERROR);
        }
        String token = UUIDUtil.uuid();
        addCookie(response, token, user);
        return true;
    }

    /**
     * 处理新用户注册
     *
     * @param userName
     * @param passWord
     * @param salt
     * @return
     */
    public boolean register(HttpServletResponse response, String userName, String passWord, String salt){
        MiaoshaUser miaoShaUser = new MiaoshaUser();
        miaoShaUser.setNickname(userName);
        String dbPassword = MD5Utils.formPassToDBPass(passWord, salt);
        miaoShaUser.setPassword(dbPassword);
        miaoShaUser.setRegisterDate(new Date());
        miaoShaUser.setSalt(salt);
        try{
            miaoShaUserMapper.insertMiaoShaUser(miaoShaUser);
            MiaoshaUser user = miaoShaUserMapper.getByNickname(miaoShaUser.getNickname());
            if (user == null){
                return false;
            }
            //生成cookie
            String token = UUIDUtil.uuid();
            addCookie(response, token ,user);
        }catch (Exception e){
            log.error("注册失败", e);
            return false;
        }
        return true;
    }

    /**
     * 设置cookie
     *
     * @param response
     * @param token
     * @param user
     */
    private void addCookie(HttpServletResponse response, String token, MiaoshaUser user){
        redisService.set(MiaoShaUserKey.token, token, user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(MiaoShaUserKey.token.expireSeconds());
        cookie.setPath("/");
        Cookie userCookie = new Cookie("nickname", String.valueOf(user.getNickname()));
        userCookie.setMaxAge(MiaoShaUserKey.token.expireSeconds());
        userCookie.setPath("/");
        response.addCookie(cookie);
        response.addCookie(userCookie);
    }
}
