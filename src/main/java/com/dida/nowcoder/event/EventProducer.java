package com.dida.nowcoder.event;

import com.alibaba.fastjson.JSONObject;
import com.dida.nowcoder.entity.Event;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class EventProducer {

    @Resource
    private KafkaTemplate kafkaTemplate;

    //处理事件
    public void fireEvent(Event event) {
        //将事件发布到指定的主题
        kafkaTemplate.send(event.getTopic(), JSONObject.toJSONString(event));
    }
}
