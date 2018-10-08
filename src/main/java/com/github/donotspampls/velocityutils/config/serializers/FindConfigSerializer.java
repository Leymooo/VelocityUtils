package com.github.donotspampls.velocityutils.config.serializers;

import com.github.donotspampls.velocityutils.config.FindConfig;
import com.google.common.reflect.TypeToken;

import ninja.leaping.configurate.ConfigurationNode;

/**
 * Serializer for {@link FindConfig}
 * 
 * @author satish
 */
public class FindConfigSerializer extends AbstractCommandConfigSerializer<FindConfig> {

	/*
	 * (non-Javadoc)
	 * @see com.github.donotspampls.velocityutils.config.serializers.AbstractCommandConfigSerializer#deserializeCustomCommandConfig(com.google.common.reflect.TypeToken, ninja.leaping.configurate.ConfigurationNode, java.lang.String)
	 */
	@Override
	protected FindConfig deserializeCustomCommandConfig(TypeToken<?> type, ConfigurationNode value,
			String noPermissionText) {
		return new FindConfig(noPermissionText);
	}

	/*
	 * (non-Javadoc)
	 * @see com.github.donotspampls.velocityutils.config.serializers.AbstractCommandConfigSerializer#serializeCustomCommandConfig(com.github.donotspampls.velocityutils.config.AbstractCommandConfig, ninja.leaping.configurate.ConfigurationNode)
	 */
	@Override
	protected void serializeCustomCommandConfig(FindConfig obj, ConfigurationNode value) {
	}

}
