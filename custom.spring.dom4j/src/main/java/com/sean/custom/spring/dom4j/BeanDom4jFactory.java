/**
 * 
 */
package com.sean.custom.spring.dom4j;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.sean.custom.spring.base.BeanFactory;

/**
 * @author zfk_0
 *
 */
public class BeanDom4jFactory implements BeanFactory {
	public Map<String,Object> parse(String file)  {
		try {
			Map<String,Object> root = new HashMap<String, Object>();
			SAXReader saxBuilder = new SAXReader();
			Document document = saxBuilder.read(new File(file));
			Element rootElement = document.getRootElement();
			for(Element bean : rootElement.elements()) {
				String id = bean.attributeValue("id");
				root.put(id, createBeanObject(bean,root));
			}
			return root;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private Object createBeanObject(Element element,Map<String,Object> root) throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchFieldException, SecurityException {
		String cls = element.attributeValue("class");
		Object object = Class.forName(cls).newInstance();
		Iterator<Element> refElements = element.elementIterator("ref");
		while(refElements.hasNext()) {
			Element refElement = refElements.next();
			String ref = refElement.attributeValue("value");
			for(Field field : object.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				if(ref.equals(field.getName())) {
					field.set(object, root.get(ref));
				}
			}
		}
		return object;
	}

}
