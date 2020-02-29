package com.example.demo.vo;

import com.example.demo.domain.OrderInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author: songqiang
 * @date: 2020/2/29
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderDetailVo {
    private GoodsVo goods;
    private OrderInfo order;

}
