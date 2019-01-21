package netty4.model;


/**
 * netty消息body
 */
public class GjMessage implements java.io.Serializable {

    private GjMessageHead head;


    //json 字符串 不是jsonobject 拿到数据后根据请求类型直接jsonparse成object
    private String body;

    public GjMessageHead getHead() {
        return head;
    }

    public void setHead(GjMessageHead head) {
        this.head = head;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
