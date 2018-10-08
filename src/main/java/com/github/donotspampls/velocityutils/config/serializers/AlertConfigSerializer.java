 package com.github.donotspampls.velocityutils.config.serializers;

import com.github.donotspampls.velocityutils.config.AlertConfig;
import com.google.common.reflect.TypeToken;

import ninja.leaping.configurate.ConfigurationNode;

/**
 * Serializer for {@link AlertConfig}
 * 
 * @author satish
 */
public class AlertConfigSerializer extends AbstractCommandConfigSerializer<AlertConfig> {

	/**
	 * Node to represent prefix value
	 */
	private final String PREFIX_NODE = "prefix";
	
	/*
	 * (non-Javadoc)
	 * @see com.github.donotspampls.velocityutils.config.serializers.AbstractCommandConfigSerializer#deserializeCustomCommandConfig(com.google.common.reflect.TypeToken, ninja.leaping.configurate.ConfigurationNode, java.lang.String)
	 */
	@Override
	protected AlertConfig deserializeCustomCommandConfig(TypeToken<?> type, ConfigurationNode value,
			String noPermissionText) {
		String prefix = value.getNode(PREFIX_NODE).getString();
		return new AlertConfig(noPermissionText, prefix);
	}

	/*
	 * (non-Javadoc)
	 * @see com.github.donotspampls.velocityutils.config.serializers.AbstractCommandConfigSerializer#serializeCustomCommandConfig(com.github.donotspampls.velocityutils.config.AbstractCommandConfig, ninja.leaping.configurate.ConfigurationNode)
	 */
	@Override
	protected void serializeCustomCommandConfig(AlertConfig obj, ConfigurationNode value) {
		value.getNode(PREFIX_NODE).setValue(obj.getPrefix());
	}

}
