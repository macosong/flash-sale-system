package com.example.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * MiaoShaMessageVo
 *
 * @author: songqiang
 * @date: 2019/12/17
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MiaoShaMessageVo implements Serializable {
    private static final long serialVersionUID = -1341750239648941486L;

    private Integer id;

    private Long userId;

    private String goodId;

    private Date orderId;

    private Long messageId;

    private String content;

    private Date createTime;

    private Integer status;

    private Date overTime;

    private Integer messageType;

    private Integer sendType;

    private String goodName;

    private BigDecimal price;

    private String messageHead;

    @Override
    public String toString() {
        return "MiaoShaMessageVo{" +
                "id=" + id +
                ", userId=" + userId +
                ", goodId='" + goodId + '\'' +
                ", orderId=" + orderId +
                ", messageId=" + messageId +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", status=" + status +
                ", overTime=" + overTime +
                ", messageType=" + messageType +
                ", sendType=" + sendType +
                ", goodName='" + goodName + '\'' +
                ", price=" + price +
                ", messageHead='" + messageHead + '\'' +
                '}';
    }
}
