package imagesy;

import java.awt.*;
import java.io.FileInputStream;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * 加载字体
 *
 * @author chaopu.ccp 2016年9月20日 上午11:41:50
 */
public class FontLoadSingleton {


    Font font = null;
    private static final int FONT_SIZE = 12;
    private static final String FONT_PATH = "/mwsleyrl.TTF";

    private static final FontLoadSingleton INSTANCE = new FontLoadSingleton();

    private FontLoadSingleton() {
        try {

            Resource resource = new ClassPathResource(FONT_PATH);
            // 创建字体
            FileInputStream fontStream = new FileInputStream(resource.getFile());
            Font dynamicFont = Font.createFont(Font.TRUETYPE_FONT, fontStream);
            font = dynamicFont.deriveFont(Font.PLAIN, FONT_SIZE);
            fontStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            // 有异常则降级，只显示工号
            font = new Font(null, Font.PLAIN, FONT_SIZE);
        }

    }

    public static FontLoadSingleton getInstance() {
        return INSTANCE;
    }

    public Font getFont() {
        return font;
    }

    public static void main(String[] args){
        System.out.println(getInstance().getFont());
    }
}