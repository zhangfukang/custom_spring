/**
 * 
 */
package com.sean.custom.spring.base;

import com.sean.custom.spring.annotation.Component;

/**
 * @author zfk_0
 *
 */
@Component(value = "customerService")
public class CustomerServiceImpl implements CustomerService {
	public String getCustomer() {
		return "customer";
	}
}
