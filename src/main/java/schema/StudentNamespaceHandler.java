package schema;


import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

public class StudentNamespaceHandler extends NamespaceHandlerSupport {

    public void init() {
        System.out.println("StudentNamespaceHandler init");
        registerBeanDefinitionParser("student", new StudentBeanDefinitionParser());
    }

    class StudentBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
        protected Class getBeanClass(Element element) {
            return Student.class;
        }

        protected void doParse(Element element, BeanDefinitionBuilder bean) {
            String name = element.getAttribute("name");
            if (StringUtils.hasText(name)) {
                bean.addPropertyValue("name", name);
            }

            String age = element.getAttribute("age");
            if (StringUtils.hasText(age)) {
                bean.addPropertyValue("age", Integer.valueOf(age));
            }

            System.out.println(bean.getClass() + " == " + bean.toString());
        }


    }
}
