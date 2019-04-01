package ssd.imagesy;

import org.apache.commons.lang.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by yehua.zyh on 2018/4/17.
 */
public class Sy2Test {
    public static void main(String[] args) throws IOException {
        int width = 250, height = 120;
        InputStream inputStream = new FileInputStream(new File("/Users/yehua.zyh/workspace/qwe.jpg"));
        Image image = ImageIO.read(inputStream);

        BufferedImage bufferImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = bufferImage.createGraphics();
        graphics.drawImage(image, 0, 0, null);
        graphics.dispose();
        graphics = bufferImage.createGraphics();
        // 设置背景透明
        bufferImage = graphics.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
        graphics.dispose();
        graphics = bufferImage.createGraphics();

        // 设置字体
        Font font = FontLoadSingleton.getInstance().getFont();
        graphics.setFont(font);

        // 背景颜色
        Color background = new Color(250, 255, 255);
        graphics.setBackground(background);

        // 图片内容
        String message = "仅限蚂蚁借呗业务合作使用";
        message = StringUtils.left(message, 18); // 防止过长，只取18个字符
        // 内容颜色
        Color color = new Color(234, 234, 234);
        graphics.setPaint(color);

        // 计算内容出现位置
        FontMetrics fontMetrics = graphics.getFontMetrics();
        int stringWidth = fontMetrics.stringWidth(message);
        int startX = (width - stringWidth) / 2;
        int startY = height / 2 + stringWidth / 4; // 1/4处

        AffineTransform atf = new AffineTransform();
        atf.rotate(-30 * Math.PI / 180, startX, startY); // -30倾斜角度
        graphics.setTransform(atf);
        graphics.drawString(message, startX, startY);
        graphics.dispose();

        FileOutputStream fileOutputStream = new FileOutputStream(new File("/Users/yehua.zyh/workspace/qwe1.jpg"));
        ImageIO.write(bufferImage, "png", fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
    }

}