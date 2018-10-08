package com.github.donotspampls.velocityutils.config;

/**
 * Common configuration for all commands
 * 
 * @author satish
 */
public abstract class AbstractCommandConfig {
	/**
	 * Text to be displayed when the user does not have permission to execute the command
	 */
	protected String noPermissionText;

	public AbstractCommandConfig(String noPermissionText) {
		super();
		this.noPermissionText = noPermissionText;
	}

	/**
	 * @return the noPermissionText
	 */
	public String getNoPermissionText() {
		return noPermissionText;
	}
	
}
