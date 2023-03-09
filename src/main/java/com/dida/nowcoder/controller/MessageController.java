package com.dida.nowcoder.controller;

import com.dida.nowcoder.entity.Message;
import com.dida.nowcoder.entity.Page;
import com.dida.nowcoder.entity.User;
import com.dida.nowcoder.service.MessageService;
import com.dida.nowcoder.service.UserService;
import com.dida.nowcoder.utils.CommunityUtil;
import com.dida.nowcoder.utils.HostHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

@Controller
public class MessageController {

    @Resource
    private MessageService messageService;

    @Resource
    private HostHolder hostHolder;

    @Resource
    private UserService userService;

    /**
     * 通过conversationId获取到目标id
     * @param conversationId
     * @return
     */
    private User getLetterTarget(String conversationId) {
        String[] ids = conversationId.split("_");
        int id1 = Integer.parseInt(ids[0]);
        int id2 = Integer.parseInt(ids[0]);

        if (hostHolder.getUser().getId() == id1) {
            return userService.getUserById(id2);
        } else {
            return userService.getUserById(id1);
        }
    }

    /**
     * 获取未读消息列表
     *
     * @param letterList 消息列表
     * @return 未读消息列表
     */
    private List<Integer> getLetterIds(List<Message> letterList) {
        List<Integer> ids = new ArrayList<>();

        if (letterList != null) {
            for (Message message : letterList) {
                if (hostHolder.getUser().getId() == message.getToId() && message.getStatus() == 0) {
                    ids.add(message.getId());
                }
            }
        }
        return ids;
    }

    //消息列表
    @GetMapping("/letter/list")
    public String getLetterList(Model model, Page page) {
        User user = hostHolder.getUser();

        //分页信息
        page.setLimit(5);
        page.setPath("/letter/list");
        page.setRows(messageService.getConversationCount(user.getId()));

        //会话列表
        List<Message> conversationList = messageService.getConversations(user.getId(), page.getOffset(), page.getLimit());
        List<Map<String, Object>> conversations = new ArrayList<>();

        if (conversationList != null) {
            for (Message message : conversationList) {
                Map<String, Object> map = new HashMap<>();

                map.put("conversation", message);
                map.put("letterCount", messageService.getLetterCount(message.getConversationId()));
                map.put("unreadCount", messageService.getLetterUnreadCount(user.getId(), message.getConversationId()));

                int targetId = user.getId() == message.getFromId() ? message.getToId() : message.getFromId();
                map.put("target", userService.getUserById(targetId));

                conversations.add(map);
            }
        }
        model.addAttribute("conversations", conversations);

        //查询未读消息数量
        int letterUnreadCount = messageService.getLetterUnreadCount(user.getId(), null);
        model.addAttribute("letterUnreadCount", letterUnreadCount);

        return "/site/letter";
    }

    //消息详情
    @GetMapping("/letter/detail/{conversationId}")
    public String getLetterDetail(@PathVariable("conversationId") String conversationId, Model model, Page page) {
        //分页信息
        page.setLimit(5);
        page.setPath("/letter/detail/" + conversationId);
        page.setRows(messageService.getLetterCount(conversationId));

        //私信列表
        List<Message> letterList = messageService.getLetters(conversationId, page.getOffset(), page.getLimit());
        List<Map<String, Object>> letters = new ArrayList<>();

        if (letterList != null) {
            for (Message message : letterList) {
                Map<String, Object> map = new HashMap<>();

                map.put("letter", message);
                map.put("fromUser", userService.getUserById(message.getFromId()));

                letters.add(map);
            }
        }
        model.addAttribute("letters", letters);

        //私信目标
        model.addAttribute("target", getLetterTarget(conversationId));

        //将未读消息设为已读
        List<Integer> ids = getLetterIds(letterList);
        if (!ids.isEmpty()) {
            messageService.readMessage(ids);
        }

        return "/site/letter-detail";
    }

    /**
     * 发送私信
     *
     * @param toName 接收人用户名
     * @param content 私信内容
     * @return 返回刷新页面
     */
    @PostMapping("/letter/send")
    @ResponseBody
    public String sendLetter(String toName, String content) {
        User target = userService.getUserByName(toName);

        //判断用户是否为空
        if (target == null) {
            return CommunityUtil.getJSONString(1, "目标用户不存在");
        }

        Message message = new Message();

        //封装message对象
        message.setFromId(hostHolder.getUser().getId());
        message.setToId(target.getId());
        if (message.getFromId() < message.getToId()) {
            message.setConversationId(message.getFromId() + "_" + message.getToId());
        } else {
            message.setConversationId(message.getToId() + "_" + message.getFromId());
        }
        message.setContent(content);
        message.setCreateTime(new Date());

        //调用业务层业务并返回
        messageService.addMessage(message);
        return CommunityUtil.getJSONString(0);
    }
}
