package com.sean.custom.spring.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

import com.sean.custom.spring.base.BeanFactory;
import com.sean.custom.spring.base.OrderService;
import com.sean.custom.spring.dom.BeanDomFactory;
import com.sean.custom.spring.dom4j.BeanDom4jFactory;
import com.sean.custom.spring.jdom.BeanJDomFactory;
import com.sean.custom.spring.sax.BeanSaxFactory;

public class TestBeanFactory {
	private BeanFactory beanFactory;

	@Test
	public void testBeanSaxFactory() {
		beanFactory = new BeanSaxFactory();
		testBeanFactory();
	}
	
	@Test
	public void testBeanDomFactory() {
		beanFactory = new BeanDomFactory();
		testBeanFactory();
	}
	
	@Test
	public void testBeanJDomFactory() {
		beanFactory = new BeanJDomFactory();
		testBeanFactory();
	}
	
	@Test
	public void testBeanDom4jFactory() {
		beanFactory = new BeanDom4jFactory();
		testBeanFactory();
	}
	
	private void testBeanFactory() {
		Map<String, Object> mapObject = beanFactory.parse("spring.xml");
		assertNotNull(mapObject.get("customerService"));
		assertNotNull(mapObject.get("orderService"));
		assertTrue("com.sean.custom.spring.base.CustomerServiceImpl".equals(mapObject.get("customerService").getClass().getName()));
		assertTrue("com.sean.custom.spring.base.OrderServiceImpl".equals(mapObject.get("orderService").getClass().getName()));
		OrderService orderService = (OrderService)mapObject.get("orderService");
		assertTrue("customer".equals(orderService.createOrder(null)));
	}

}
