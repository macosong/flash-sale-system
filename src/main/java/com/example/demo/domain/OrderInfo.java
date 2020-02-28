package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * OrderInfo
 *
 * @author Qiang Song
 * @data 2019/11/17
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderInfo {
    private Long id;
    private Long userId;
    private Long goodsId;
    private Long deliveryAddrId;
    private String goodsName;
    private Integer goodsCount;
    private Double goodsPrice;
    private Integer orderChannel;
    private Integer status;
    private Date createDate;
    private Date payDate;

    @Override
    public String toString() {
        return "OrderInfo{" +
                "id=" + id +
                ", userId=" + userId +
                ", goodsId=" + goodsId +
                ", deliveryAddrId=" + deliveryAddrId +
                ", goodsName='" + goodsName + '\'' +
                ", goodsCount=" + goodsCount +
                ", goodsPrice=" + goodsPrice +
                ", orderChannel=" + orderChannel +
                ", status=" + status +
                ", createDate=" + createDate +
                ", payDate=" + payDate +
                '}';
    }
}
