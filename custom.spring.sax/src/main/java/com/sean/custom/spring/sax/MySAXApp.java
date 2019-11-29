package com.sean.custom.spring.sax;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class MySAXApp extends DefaultHandler {
	private Map<String, Object> root = new HashMap<String, Object>();
	
	private String preTag;
	
	public void startElement (String uri, String name,String qName, Attributes atts) {
		if("bean".equals(qName)) {
			preTag = atts.getValue("id");
			String className = atts.getValue("class");
			try {
				Object object = Class.forName(className).newInstance();
				root.put(preTag, object);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if("ref".equals(qName)) {
			String refValue = atts.getValue("value");
			Object object = root.get(preTag);
			for(Field field : object.getClass().getDeclaredFields()) {
				if(refValue.equals(field.getName())) {
					field.setAccessible(true);
					try {
						field.set(object, root.get(refValue));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public Map<String, Object> getRoot() {
		return root;
	}
}
