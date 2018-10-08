package com.github.donotspampls.velocityutils.config;

import com.github.donotspampls.velocityutils.commands.AlertCommand;

/**
 * Config for {@link AlertCommand}
 * 
 * @author satish
 */
public class AlertConfig extends AbstractCommandConfig {
	
	private String prefix;

	public AlertConfig(String noPermissionText, String prefix) {
		super(noPermissionText);
		this.prefix = prefix;
	}

	/**
	 * @return the prefix
	 */
	public String getPrefix() {
		return prefix;
	}
	
}
