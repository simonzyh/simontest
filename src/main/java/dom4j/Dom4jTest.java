package dom4j;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.List;

public class Dom4jTest {

    public static void main(String[] args) {

        try {
            URL uri = new URL("http://localhost:1082/weixinmp/images/ABT-20335387.xml.c7489eadfe9f4f7495243d38dc82a113");

            //解析
            SAXReader reader = new SAXReader(false);
            Document doc = reader.read(uri);
            Element root = doc.getRootElement();
            List<Element> childs1 = root.element("TopicBody").elements();
            List<Element> childs = childs1.get(0).elements();
            for (Element element : childs) {
                System.out.println(element.getName());

            }

            childs.sort((o1, o2) -> {
                if ("ConceptName".equals(o1.getName())) {
                    return -1;
                }
                return 1;
            });

            for (Element element : childs) {
                System.out.println(element.getName());

            }

            System.out.println(urlConcat("http://localhost:1081/weixinmp/", "test.jsp"));
            System.out.println(urlConcat("http://localhost:1081/weixinmp/", "/test.jsp"));
            System.out.println(urlConcat("http://localhost:1081/weixinmp", "test.jsp"));
            System.out.println(urlConcat("http://localhost:1081/weixinmp", "/test.jsp"));
            String userSn = String.format("%20s", 123).replaceAll(" ", "0").substring(16);

            System.out.println(userSn);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    public static String urlConcat(String url, String url1) {
        if (url.endsWith("/")) {
            if (url1.startsWith("/")) {
                return url + url1.substring(1);
            } else {
                return url + url1;
            }
        } else {
            if (url1.startsWith("/")) {
                return url + url1;
            } else {
                return url + "/" + url1;
            }
        }
    }

    private static String parseSectionHead(Element xml) {
        return "<h2><font style='vertical-align: inherit;'>" + xml.getTextTrim() + "</font></h2>";
    }

    private static String parseHtml(Element xml) {
        return xml.getTextTrim();
    }

    private static String parsePopupMedia(Element xml) {
        return parseImg(xml.element("Image"));
    }

    //<img src="/-/media/kcms/gbs/patient-consumer/images/2013/11/15/17/38/ds00078_-my00501_im01586_hdg7_fibroidlocationthu_jpg.jpg"
// alt="纤维瘤位置">
    private static String parseImg(Element xml) {
        StringBuffer imghtml = new StringBuffer();
        xml.addAttribute("width", xml.attributeValue("Width"));
        xml.addAttribute("height", xml.attributeValue("Height"));
        xml.addAttribute("alt", xml.attributeValue("Alt"));
        imghtml.append("<p>");
        xml.addAttribute("src", "https://www.mayoclinic.org/-/media/kcms/gbs/patient-consumer/images/2013/11/15/17/38/ds00078_-my00501_im01586_hdg7_fibroidlocationthu_jpg.jpg");
        imghtml.append(xml.asXML().replaceAll("Image", "img"));
        imghtml.append("</p>");
        imghtml.append("<p>").append(xml.attributeValue("Title")).append("</p>");
        return imghtml.toString();
    }

    private void handel(Element e, StringBuffer sb) {
        List<Element> child = e.elements();

        if (child != null && child.size() > 0) {

        }
        String name = e.getName();

    }
}
