package com.example.demo.rabbitmq;

import com.example.demo.domain.MiaoshaUser;
import lombok.Getter;
import lombok.Setter;

/**
 * MiaoshaMessage
 *
 * @author: songqiang
 * @date: 2019/12/16
 */
@Setter
@Getter
public class MiaoshaMessage {
    private MiaoshaUser user;
    private long goodsId;
}
