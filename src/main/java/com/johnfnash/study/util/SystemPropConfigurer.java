package com.johnfnash.study.util;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class SystemPropConfigurer extends PropertyPlaceholderConfigurer {
	private static final String ENCODE_RULES = "johnfnash";
	
	@Override
	protected String convertProperty(String propertyName, String propertyValue) {
		// 将数据库账号密码进行解密
		if(Constants.JDBC_PASSWORD.equals(propertyName) || 
				Constants.JDBC_USERNAME.equals(propertyName)) {
			return SymmetricEncoder.AESDncode(ENCODE_RULES, propertyValue);
		} else {
			return super.convertProperty(propertyName, propertyValue);
		}		
	}

}
