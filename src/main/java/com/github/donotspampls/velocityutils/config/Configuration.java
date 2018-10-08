package com.github.donotspampls.velocityutils.config;

/**
 * Represents all the available config
 * 
 * @author satish
 */
public interface Configuration {

	/**
	 * Return Config for Alert command
	 * @return Config for alert command
	 */
	AlertConfig getAlertConfig();
	
	/**
	 * Return config for find command
	 * @return config for find command
	 */
	FindConfig getFindConfig();
	
}
