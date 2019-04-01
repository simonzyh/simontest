package ssd.netty4.model;


/**
 * 消息头
 */
public class GjMessageHead implements java.io.Serializable {
    /**
     * 状态 1成功。0 失败表示出现业务未捕获的异常
     * 一般用在响应消息中
     */
    int status;
    /**
     * 消息id 单一chennel 唯一 用于request 和response 的对应
     *
     * @必传
     */
    private String messageId;
    /**
     * key 用于签名， 确定企业身份
     */
    private String appKey;
    /**
     * 签名
     */
    private String sign;
    /**
     * 请求类型用于表示该 消息是 request 还是response
     * 0 请求  1 响应
     */
    private int messageType;

    /**
     * 请求业务类型
     * 约定  class.method
     */
    private String requestMethod;

    /**
     * 请求日志id 用于日志记录
     */
    private String traceId;

    /**
     * 客户端ip
     */
    private String ip;
    /**
     * 客户端版本
     */
    private String clientVersion;

    @Override
    public String toString() {
        return "GjMessageHead{" +
                "messageId='" + messageId + '\'' +
                ", status=" + status +
                ", appKey='" + appKey + '\'' +
                ", sign='" + sign + '\'' +
                ", messageType=" + messageType +
                ", requestMethod='" + requestMethod + '\'' +
                ", traceId='" + traceId + '\'' +
                ", ip='" + ip + '\'' +
                ", clientVersion='" + clientVersion + '\'' +
                '}';
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }
}
