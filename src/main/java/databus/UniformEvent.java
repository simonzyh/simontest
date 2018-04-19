package databus;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yehua.zyh on 2018/4/9.
 */
public class UniformEvent {

    private String topic;

    private String eventCode;

    private Object eventPayload;

    private Map properties=new HashMap();

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public Object getEventPayload() {
        return eventPayload;
    }

    public void setEventPayload(Object eventPayload) {
        this.eventPayload = eventPayload;
    }

    public Map getProperties() {
        return properties;
    }

    public void setProperties(Map properties) {
        this.properties = properties;
    }
}