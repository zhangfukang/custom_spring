/**
 * 
 */
package com.sean.custom.spring.annotation;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import com.sean.custom.spring.base.BeanFactory;

/**
 * @author zfk_0
 *
 */
public class Scan implements BeanFactory {
	public Map<String, Object> parse(String file) {
		Map<String, Object> root = new HashMap<String, Object>();
		try {
			String scanPackage = "com.sean.custom.spring.base";
			Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(scanPackage.replace(".", "/"));
			while(urls.hasMoreElements()) {
				URL url = urls.nextElement();
				for(String fileName : new File(url.getPath()).list()) {
					fileName = fileName.replace(".class", "");
					Class cls = Class.forName(scanPackage + "." + fileName);
					Component component = (Component) cls.getAnnotation(Component.class) ;
					if(component != null) {
						String serviceName = component.value();
						Object obj = cls.newInstance();
						root.put(serviceName, obj);
						for(Field field : cls.getDeclaredFields()) {
							if(field.getAnnotation(Autowire.class) != null) {
								field.setAccessible(true);
								field.set(obj, root.get(field.getName()));
							}
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return root;
	}
	
}
