/**
 * 
 */
package com.sean.custom.spring.base;

/**
 * @author zfk_0
 *
 */
public class OrderServiceImpl implements OrderService {
	private CustomerService customerService;
	
	public String createOrder(Order order) {
		return customerService.getCustomer();
	}
}
