/**
 * 
 */
package com.sean.custom.spring.dom;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sean.custom.spring.base.BeanFactory;

/**
 * @author zfk_0
 *
 */
public class BeanDomFactory implements BeanFactory {
	public Map<String,Object> parse(String file)  {
		try {
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(file);
			NodeList nodeList = document.getElementsByTagName("bean");
			Map<String,Object> root = new HashMap<String, Object>();
			for(int i = 0;i < nodeList.getLength();i++) {
				Node node = nodeList.item(i);
				String id = node.getAttributes().getNamedItem("id").getNodeValue();
				root.put(id, createBeanObject(node,root));
			}
			return root;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private Object createBeanObject(Node node,Map<String,Object> root) throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchFieldException, SecurityException {
		String cls = node.getAttributes().getNamedItem("class").getNodeValue();
		Object object = Class.forName(cls).newInstance();
		for(int j = 0;j < node.getChildNodes().getLength();j++) {
			Node childNode = node.getChildNodes().item(j);
			if("ref".equals(childNode.getNodeName())) {
				String ref = childNode.getAttributes().getNamedItem("value").getNodeValue();
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
