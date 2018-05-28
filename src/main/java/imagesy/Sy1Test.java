package imagesy;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by yehua.zyh on 2018/3/30.
 */
public class Sy1Test {

    public static void main(String[] args) throws Exception {
        System.out.println(new ClassPathResource("mwsleyrl.TTF").getFile());
        System.out.println(Sy1Test.class.getResource("/mwsleyrl.TTF").getPath());

        InputStream inputStream = new FileInputStream(new File("/Users/yehua.zyh/workspace/qwe.jpg"));
        Image image = ImageIO.read(inputStream);

        int width = image.getWidth(null);
        int height = image.getHeight(null);
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D graphics = bufferedImage.createGraphics();
        graphics.setBackground(Color.WHITE);
        graphics.drawImage(image, 0, 0, null);

        // 设置字体
        Font font = FontLoadSingleton.getInstance().getFont().deriveFont(20, 25);
        graphics.setFont(font);

        // 背景颜色
        Color background = new Color(250, 255, 255);
        graphics.setBackground(background);

        // 图片内容
        String message = "图片内容";
        message = StringUtils.left(message, 18); // 防止过长，只取18个字符
        // 内容颜色
        Color color = new Color(234, 234, 234);
        graphics.setPaint(color);
        for (int i = 0; i < 3; i++) {
            // 计算内容出现位置
            FontMetrics fontMetrics = graphics.getFontMetrics();
            int stringWidth = fontMetrics.stringWidth(message);
            int startX = (width - stringWidth) / 3;
            int startY = height / 2 + stringWidth / 4; // 1/4处
            startX += 100 * i;
            startY += 50 * i;
            AffineTransform atf = new AffineTransform();
            atf.rotate(-30 * Math.PI / 180, startX, startY); // -30倾斜角度
            graphics.setTransform(atf);
            graphics.drawString(message, startX, startY);
        }

       /* // 将第一步读入的模板图片绘制到新生成的图片上


        // 设置绘制文字属性
        // 文字颜色
        graphics.setColor(Color.black);
        // 消除锯齿
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 字体
        Font font = FontManager.getFont("founderblack"); // 使用默认字体
        font = font.deriveFont(20, 30);
        graphics.setFont(font);
        // 绘制文字内容
        String markMsg = "仅限蚂蚁借呗业务合作使用";
        AttributedString ats = new AttributedString(markMsg);
        ats.addAttribute(TextAttribute.FONT, font, 0, markMsg.length());
        graphics.drawString(ats.getIterator(), width / 3, height / 2);*/

        FileOutputStream fileOutputStream = new FileOutputStream(new File("/Users/yehua.zyh/workspace/qwe1.jpg"));

        boolean writeResult = ImageIO.write(bufferedImage, "png", fileOutputStream);

        System.out.println(writeResult);

    }

    private static String getStringWithWidth(Graphics2D g, String msg, int begin, int width) {
        FontMetrics fm = g.getFontMetrics();
        String temp = "";
        int stringWidth;
        for (int i = begin + 1; i <= msg.length(); i++) {
            temp = msg.substring(begin, i);
            stringWidth = fm.stringWidth(temp);
            if (stringWidth == width) {
                return temp;
            } else if (stringWidth > width) {
                return msg.substring(begin, i - 1);
            }
        }

        return temp;
    }
}