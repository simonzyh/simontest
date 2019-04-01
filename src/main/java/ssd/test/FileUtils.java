package ssd.test;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


/**
 * Created by yehua.zyh on 2018/3/19. ziputils
 */
public class FileUtils {

    /**
     * 根据多个输入的inputstream 打成zip文件压缩包
     *
     * @param inputStreamMap
     * @return
     */
    public static void zip(Map<String, InputStream> inputStreamMap) throws IOException {
        byte[] buf = new byte[1024];

        //打成的zip包输出到字节流
        OutputStream outputStream = new FileOutputStream(new File("/Users/yehua.zyh/test.zip"));
        ZipOutputStream zos = null;
        ZipEntry ze = null;
        zos = new ZipOutputStream(outputStream);
        zos.setLevel(0);

        for (Entry<String, InputStream> entry : inputStreamMap.entrySet()) {
            ze = new ZipEntry(entry.getKey());
            //byte[] data = readByte(entry.getValue());
            //ze.setSize(data.length);
            zos.putNextEntry(ze);
            int len;
            while ((len = entry.getValue().read(buf)) > 0) {
                zos.write(buf, 0, len);
            }
            // zos.write(data);
        }
        zos.flush();
        zos.finish();
        outputStream.flush();


        zos.close();
        outputStream.close();


    }

    public static void main(String[] args) throws Exception {
        InputStream i1 = new FileInputStream(new File("/Users/yehua.zyh/test1.png"));
        InputStream i2 = new FileInputStream(new File("/Users/yehua.zyh/test2.jpg"));
        Map<String, InputStream> map = new HashMap<>();
        map.put("1.jpg", i1);
        map.put("2.jpg", i2);
        zip(map);


    }

    /**
     * 从inpuerstream 中读取byte数组
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] readByte(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "GBK"));
        String content = null;
        while ((content = in.readLine()) != null) {
            byteArrayOutputStream.write(content.getBytes("GBK"));
        }
        inputStream.close();
        byteArrayOutputStream.flush();
        byte[] result = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();
        return result;
    }

}