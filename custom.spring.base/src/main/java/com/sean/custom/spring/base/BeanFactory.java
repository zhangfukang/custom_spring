package com.sean.custom.spring.base;

import java.util.Map;

public interface BeanFactory {
	public Map<String, Object> parse(String file);
}
