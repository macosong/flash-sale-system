package com.example.demo.service;

import com.example.demo.dao.MiaoShaMessageMapper;
import com.example.demo.domain.MiaoShaMessageInfo;
import com.example.demo.domain.MiaoShaMessageUser;
import com.example.demo.vo.MiaoShaMessageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * MiaoShaMessageService
 *
 * @author: songqiang
 * @date: 2019/12/17
 */
@Service
public class MiaoShaMessageService {
    @Autowired
    private MiaoShaMessageMapper messageMapper;

    public List<MiaoShaMessageInfo> getMessageUserList(Long userId, Integer status) {
        return messageMapper.listMiaoShaMessageByUserId(userId, status);
    }

    @Transactional(rollbackFor = Exception.class)
    public void insert(MiaoShaMessageVo miaoShaMessageVo) {
        MiaoShaMessageUser mu = new MiaoShaMessageUser();
        mu.setUserId(miaoShaMessageVo.getUserId());
        mu.setMessageId(miaoShaMessageVo.getMessageId());
        messageMapper.insertMiaoShaMessageUser(mu);

        MiaoShaMessageInfo miaoShaMessageInfo = new MiaoShaMessageInfo();
        miaoShaMessageInfo.setContent(miaoShaMessageVo.getContent());
        miaoShaMessageInfo.setStatus(miaoShaMessageVo.getStatus());
        miaoShaMessageInfo.setMessageType(miaoShaMessageVo.getMessageType());
        miaoShaMessageInfo.setSendType(miaoShaMessageVo.getSendType());
        miaoShaMessageInfo.setMessageId(miaoShaMessageVo.getMessageId());
        miaoShaMessageInfo.setCreateTime(new Date());
        miaoShaMessageInfo.setMessageHead(miaoShaMessageVo.getMessageHead());
        messageMapper.insertMiaoShaMessage(miaoShaMessageInfo);

    }
}
