package ssd.protobuf;

import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SerializableModel implements Serializable {
    private static final long serialVersionUID = -2140242550063332020L;

    @Protobuf(fieldType = FieldType.INT32, required = false, order = 1)
    private Integer id;
    @Protobuf(fieldType = FieldType.STRING, required = false, order = 2)
    private String name;


    @Protobuf(fieldType = FieldType.OBJECT, required = false, order = 3)
    private SerializableModel parent;

    @Protobuf(fieldType = FieldType.OBJECT, required = false, order = 4)
    private List<SerializableModel> chlids;

}