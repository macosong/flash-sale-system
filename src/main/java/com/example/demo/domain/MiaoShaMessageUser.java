package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * MiaoShaMessageUser
 * 消息中心用户存储关系表
 *
 * @author: songqiang
 * @date: 2019/12/17
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MiaoShaMessageUser implements Serializable {
    private Long id;

    private Long userId;

    private Long messageId;

    private String goodsId;

    private Date orderId;
}
