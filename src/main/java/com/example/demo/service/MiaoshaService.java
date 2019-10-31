package com.example.demo.service;

import com.example.demo.redis.MiaoshaKey;
import com.example.demo.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * MiaoshaService
 *
 * @author maco
 * @data 2019/10/28
 */
@Service
public class MiaoshaService {
    @Autowired
    private RedisService redisService;

    /**
     * 用户注册页面初始化时生成验证码
     *
     * @return
     */
    public BufferedImage createVerifyCodeRegister(){
        int width = 80;
        int height = 32;
        //create the image
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        // set the background color
        g.setColor(new Color(0xDCDCDC));
        g.fillRect(0, 0, width, height);
        // draw the border
        g.setColor(Color.black);
        g.drawRect(0, 0, width - 1, height - 1);
        // create a random instance to generate the codes
        Random rdm = new Random();
        // make some confusion
        for (int i = 0; i < 50; i++) {
            int x = rdm.nextInt(width);
            int y = rdm.nextInt(height);
            g.drawOval(x, y, 0, 0);
        }
        // generate a random code
        String verifyCode = generateVerifyCode(rdm);
        g.setColor(new Color(0, 100, 0));
        g.setFont(new Font("Candara", Font.BOLD, 24));
        g.drawString(verifyCode, 8, 24);
        g.dispose();
        int rnd = calc(verifyCode);
        redisService.set(MiaoshaKey.getMiaoshaVerifyCodeRegister, "register", rnd);
        return image;
    }

    private static int calc(String exp) {
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("JavaScript");
            Integer catch1 = (Integer)engine.eval(exp);
            return catch1.intValue();
        }catch(Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static char[] ops = new char[] {'+', '-', '*'};
    /**
     * + - *
     * */
    private String generateVerifyCode(Random rdm) {
        int num1 = rdm.nextInt(10);
        int num2 = rdm.nextInt(10);
        int num3 = rdm.nextInt(10);
        char op1 = ops[rdm.nextInt(3)];
        char op2 = ops[rdm.nextInt(3)];
        String exp = ""+ num1 + op1 + num2 + op2 + num3;
        return exp;
    }

    /**
     * 注册时检查验证码
     *
     * @param verifyCode
     * @return
     */
    public boolean checkVerifyCodeRegister(int verifyCode){
        String registerKey = "register";
        Integer codeOld = redisService.get(MiaoshaKey.getMiaoshaVerifyCodeRegister, registerKey, Integer.class);
        if (codeOld == null || codeOld - verifyCode != 0){
            return false;
        }

        redisService.delete(MiaoshaKey.getMiaoshaVerifyCode, registerKey);
        return true;

    }
}
