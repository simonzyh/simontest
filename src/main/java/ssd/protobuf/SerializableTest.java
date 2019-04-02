package ssd.protobuf;

import com.alibaba.fastjson.JSON;
import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import com.google.common.collect.Lists;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;
import java.util.UUID;

public class SerializableTest {
    static Random random = new Random();

    public static void main(String[] args) throws Exception {
        SerializableModel serializableModel = gen();
        serializableModel.setParent(gen());
        serializableModel.setChlids(Lists.newArrayList(gen(), gen(), gen(), gen(), gen(), gen(), gen(), gen(), gen(), gen()));

        JavaSerialize(serializableModel);
        jsonSerialize(serializableModel);
        portbuffSerialize(serializableModel);
        System.out.println("111111111111111");


        JavaSerialize(serializableModel);
        jsonSerialize(serializableModel);
        portbuffSerialize(serializableModel);
        System.out.println("2222222222222222");


        JavaSerialize(serializableModel);
        jsonSerialize(serializableModel);
        portbuffSerialize(serializableModel);
        System.out.println("3333333333333333");


        JavaSerialize(serializableModel);
        jsonSerialize(serializableModel);
        portbuffSerialize(serializableModel);

    }

    public static void JavaSerialize(SerializableModel serializableModel) throws Exception {
        Long lstart = System.currentTimeMillis();
        int size = 0;
        for (int i = 0; i < 100000; i++) {
            //序列化
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream obj = new ObjectOutputStream(out);
            obj.writeObject(serializableModel);

            byte[] data = out.toByteArray();
            size += data.length;
            //反序列化
            ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new java.io.ByteArrayInputStream(data)));
            SerializableModel model = (SerializableModel) ois.readObject();
        }
        System.out.println("JavaSerialize time=" + (System.currentTimeMillis() - lstart) + " size=" + size / 1024);

    }

    public static void jsonSerialize(SerializableModel serializableModel) throws Exception {
        Long lstart = System.currentTimeMillis();
        int size = 0;
        for (int i = 0; i < 100000; i++) {
            //序列化
            //序列化
            String data = JSON.toJSONString(serializableModel);

            //反序列化
            SerializableModel model = JSON.parseObject(data, SerializableModel.class);
            size += data.getBytes().length;

        }
        System.out.println("jsonSerialize time=" + (System.currentTimeMillis() - lstart) + " size=" + size / 1024);

    }

    public static void portbuffSerialize(SerializableModel serializableModel) throws Exception {
        Codec<SerializableModel> studentClassCodec = ProtobufProxy.create(SerializableModel.class, false);
        Long lstart = System.currentTimeMillis();
        int size = 0;
        for (int i = 0; i < 100000; i++) {
            //序列化
            //序列化
            byte[] data = studentClassCodec.encode(serializableModel);
            //反序列化
            SerializableModel model = studentClassCodec.decode(data);

            size += data.length;

        }
        System.out.println("portbuffSerialize time=" + (System.currentTimeMillis() - lstart) + " size=" + size / 1024);

    }


    static SerializableModel gen() {
        SerializableModel model = new SerializableModel();
        model.setId(random.nextInt());
        model.setName(UUID.randomUUID().toString());
        return model;

    }


}
