package com.github.donotspampls.velocityutils.config.parser;

import com.github.donotspampls.velocityutils.config.AlertConfig;
import com.github.donotspampls.velocityutils.config.Configuration;
import com.github.donotspampls.velocityutils.config.FindConfig;

/**
 * Default implementation of {@link Configuration}
 * 
 * @author satish
 */
public class ConfigurationImpl implements Configuration {
	/**
	 * Config for alert command
	 */
	private final AlertConfig alertConfig;
	/**
	 * Config for find command
	 */
	private final FindConfig findConfig;

	public ConfigurationImpl(AlertConfig alertConfig, FindConfig findConfig) {
		super();
		this.alertConfig = alertConfig;
		this.findConfig = findConfig;
	}

	/* (non-Javadoc)
	 * @see com.github.donotspampls.velocityutils.config.Configuration#getAlertConfig()
	 */
	@Override
	public AlertConfig getAlertConfig() {
		return alertConfig;
	}

	/* (non-Javadoc)
	 * @see com.github.donotspampls.velocityutils.config.Configuration#getFindConfig()
	 */
	@Override
	public FindConfig getFindConfig() {
		return findConfig;
	}
	
}
