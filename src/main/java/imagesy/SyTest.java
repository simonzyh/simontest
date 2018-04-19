package imagesy;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;

import com.alibaba.simpleimage.ImageFormat;
import com.alibaba.simpleimage.ImageRender;
import com.alibaba.simpleimage.ImageWrapper;
import com.alibaba.simpleimage.SimpleImageException;
import com.alibaba.simpleimage.render.CornerDrawTextItem;
import com.alibaba.simpleimage.render.DrawTextParameter;
import com.alibaba.simpleimage.render.DrawTextRender;
import com.alibaba.simpleimage.render.FixDrawTextItem;
import com.alibaba.simpleimage.render.FootnoteDrawTextItem;
import com.alibaba.simpleimage.render.ReadRender;
import com.alibaba.simpleimage.render.ScaleParameter;
import com.alibaba.simpleimage.render.ScaleRender;
import com.alibaba.simpleimage.render.WatermarkParameter;
import com.alibaba.simpleimage.render.WatermarkRender;
import com.alibaba.simpleimage.render.WriteParameter;
import com.alibaba.simpleimage.render.WriteRender;

import org.apache.commons.io.IOUtils;

/**
 * Created by yehua.zyh on 2018/3/30.
 */
public class SyTest {



    public static void main(String[] args) throws Exception {


        DrawTextParameter param = new DrawTextParameter();
        //param.addTextInfo(new FixDrawTextItem("仅限蚂蚁借呗业务合作使用"));
        param.addTextInfo(new CornerDrawTextItem("仅限蚂蚁借呗业务合作使用"));
        //param.addTextInfo(new FootnoteDrawTextItem("仅限蚂蚁借呗业务合作使用", "cheneychenc.alibaba.com.cn"));


        ImageRender readRender = new ReadRender(new FileInputStream(new File("/Users/yehua.zyh/test1.png")));

        DrawTextRender drawTextRender = new DrawTextRender(readRender, param);

        FileOutputStream outStream = new FileOutputStream(new File("/Users/yehua.zyh/dest2.jpg"));

        WriteParameter writeParam = new WriteParameter();
        writeParam.setDefaultQuality(0.5f);

        ImageRender writeRender = new WriteRender(drawTextRender, outStream, ImageFormat.JPEG, writeParam);
         writeRender.render();
         outStream.flush();
         outStream.close();
    }
}