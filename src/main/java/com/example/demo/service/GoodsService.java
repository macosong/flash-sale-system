package com.example.demo.service;

import com.example.demo.dao.GoodsMapper;
import com.example.demo.domain.MiaoshaGoods;
import com.example.demo.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * GoodsService
 *
 * @author maco
 * @data 2019/10/28
 */
@Service
public class GoodsService {
    @Autowired
    GoodsMapper goodsMapper;

    public List<GoodsVo> listGoodsVo()  { return goodsMapper.listGoodsVo(); }

    public GoodsVo getCoodsVoByGoodsId(long goodsId)    { return goodsMapper.getGoodsVoByGoodsId(goodsId); }

    public boolean reduceStock(GoodsVo goodsVo){
        MiaoshaGoods goods = new MiaoshaGoods();
        goods.setGoodsId(goods.getId());
        int ret = goodsMapper.reduceStock(goods);
        return ret > 0;
    }
}
