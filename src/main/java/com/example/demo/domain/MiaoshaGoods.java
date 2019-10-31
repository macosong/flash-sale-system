package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * MiaoshaGoods
 *
 * @author maco
 * @data 2019/10/28
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MiaoshaGoods {
    private Long id;
    private Long goodsId;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
}
