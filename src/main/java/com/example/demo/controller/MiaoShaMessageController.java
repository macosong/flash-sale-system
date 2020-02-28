package com.example.demo.controller;

import com.example.demo.common.enums.MessageStatus;
import com.example.demo.common.enums.ResultStatus;
import com.example.demo.common.resultbean.MyResult;
import com.example.demo.domain.MiaoShaMessageInfo;
import com.example.demo.rabbitmq.MQSender;
import com.example.demo.service.MiaoShaMessageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * MiaoShaMessageController
 *
 * @author: songqiang
 * @date: 2019/12/18
 */
@Controller
@RequestMapping("/message")
public class MiaoShaMessageController {
    @Autowired
    private MiaoShaMessageService messageService;

    @Autowired
    private MQSender mqSender;

    @RequestMapping(value = "/list", produces = "text/html")
    public String list(@RequestParam(value = "userid", required = true) String userId, Model model) {
        MyResult result = MyResult.build();
        if (StringUtils.isBlank(userId)) {
            result.withError(ResultStatus.USER_NOT_EXIST);
        }
        List<MiaoShaMessageInfo> miaoShaMessageInfos = messageService.getMessageUserList(Long.valueOf(userId), null);
        model.addAttribute("message", miaoShaMessageInfos);

        return "message_list";
    }

    @RequestMapping(value = "getNewMessage", produces = "text/html")
    public String getNewMessage(@RequestParam(value = "userid", required = true) String userId, Model model) {
        if (StringUtils.isBlank(userId)) {
            return "0";
        }
        List<MiaoShaMessageInfo> miaoShaMessageInfos = messageService.getMessageUserList(Long.valueOf(userId), MessageStatus.ZORE);
        if (miaoShaMessageInfos.isEmpty()) {
            return "0";
        } else {
            return "1";
        }
    }
}
