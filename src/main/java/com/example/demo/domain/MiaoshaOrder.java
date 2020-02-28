package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * MiaoshaOrder
 *
 * @author Qiang Song
 * @data 2019/11/17
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MiaoshaOrder {
    private Long id;
    private Long userId;
    private Long orderId;
    private Long goodsId;

    @Override
    public String toString() {
        return "MiaoshaOrder{" +
                "id=" + id +
                ", userId=" + userId +
                ", orderId=" + orderId +
                ", goodsId=" + goodsId +
                '}';
    }
}
