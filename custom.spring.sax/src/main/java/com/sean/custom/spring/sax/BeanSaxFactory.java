/**
 * 
 */
package com.sean.custom.spring.sax;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.sean.custom.spring.base.BeanFactory;

/**
 * @author zfk_0
 *
 */
public class BeanSaxFactory implements BeanFactory {
	public Map<String,Object> parse(String file)  {
		try {
			Map<String,Object> root = new HashMap<String, Object>();
			XMLReader xr = XMLReaderFactory.createXMLReader();
			MySAXApp handler = new MySAXApp();
			xr.setContentHandler(handler);
			xr.setErrorHandler(handler);
		    FileReader r = new FileReader(file);
		    xr.parse(new InputSource(r));
			return handler.getRoot();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		BeanFactory beanFactory = new BeanSaxFactory();
		beanFactory.parse("spring.xml");
	}
}
