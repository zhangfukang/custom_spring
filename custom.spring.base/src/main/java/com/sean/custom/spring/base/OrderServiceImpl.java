/**
 * 
 */
package com.sean.custom.spring.base;

import com.sean.custom.spring.annotation.Autowire;
import com.sean.custom.spring.annotation.Component;

/**
 * @author zfk_0
 *
 */
@Component(value = "orderService")
public class OrderServiceImpl implements OrderService {
	@Autowire
	private CustomerService customerService;
	
	public String createOrder(Order order) {
		return customerService.getCustomer();
	}
}
