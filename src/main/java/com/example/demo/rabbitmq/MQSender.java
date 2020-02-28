package com.example.demo.rabbitmq;

import com.example.demo.redis.RedisService;
import com.example.demo.vo.MiaoShaMessageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * MQSender
 *
 * @author: songqiang
 * @date: 2019/12/16
 */
@Slf4j
@Service
public class MQSender {
    @Autowired
    AmqpTemplate amqpTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 秒杀消息
     *
     * @param mm
     */
    public void sendMiaoshaMessage(MiaoshaMessage mm) {
        String msg = RedisService.beanToString(mm);
        amqpTemplate.convertAndSend(MQConfig.MIAOSHA_QUEUE, msg);
    }

    /**
     * 站内信
     *
     * @param mm
     */
    public void sendMessage(MiaoshaMessage mm) {
        log.info("send message:{}", "1111");
        rabbitTemplate.convertAndSend(MQConfig.EXCHANGE_TOPIC, "miaosha_*", "1111");
    }

    /**
     * 注册站内信
     *
     * @param miaoShaMessageVo
     */
    public void sendRegisterMessage(MiaoShaMessageVo miaoShaMessageVo) {
        String msg = RedisService.beanToString(miaoShaMessageVo);
        log.info("send register message:{}", msg);
        rabbitTemplate.convertAndSend(MQConfig.MIAOSHATEST, "miaosha_*", msg);
    }
}
