package parkingmanage.vo;

import lombok.Data;

@Data
public class Response<T> implements java.io.Serializable {

    public Boolean success;

    public String code;

    public String msg;

    public T data;


    public static <T> Response succ(T t) {
        Response response = new Response();
        response.setData(t);
        response.setSuccess(Boolean.TRUE);
        return response;
    }

    public static Response fail(String code, String msg) {
        Response response = new Response();
        response.setSuccess(Boolean.FALSE);
        response.setCode(code);
        response.setMsg(msg);
        return response;
    }

}
