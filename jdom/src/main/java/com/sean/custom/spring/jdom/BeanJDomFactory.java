/**
 * 
 */
package com.sean.custom.spring.jdom;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import com.sean.custom.spring.base.BeanFactory;

/**
 * @author zfk_0
 *
 */
public class BeanJDomFactory implements BeanFactory {
	public Map<String,Object> parse(String file)  {
		try {
			Map<String,Object> root = new HashMap<String, Object>();
			SAXBuilder saxBuilder = new SAXBuilder();
			InputStream in = new FileInputStream(new File(file));
			Document document = saxBuilder.build(in);
			Element element = document.getRootElement();
			for(Element bean : element.getChildren()) {
				String id = bean.getAttributeValue("id");
				root.put(id, createBeanObject(bean,root));
			}

			return root;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private Object createBeanObject(Element element,Map<String,Object> root) throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchFieldException, SecurityException {
		String cls = element.getAttributeValue("class");
		Object object = Class.forName(cls).newInstance();
		for(Element child :element.getChildren()) {
			if("ref".equals(child.getName())) {
				String ref = child.getAttributeValue("value");
				for(Field field : object.getClass().getDeclaredFields()) {
					field.setAccessible(true);
					if(ref.equals(field.getName())) {
						field.set(object, root.get(ref));
					}
				}
			}
		}
		return object;
	}

}
