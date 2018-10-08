package com.github.donotspampls.velocityutils.config.serializers;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import com.github.donotspampls.velocityutils.config.AbstractCommandConfig;
import com.google.common.reflect.TypeToken;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;

/**
 * Common serialization code for Command config
 * 
 * @author satish
 */
public abstract class AbstractCommandConfigSerializer<T extends AbstractCommandConfig> implements TypeSerializer<T> {

	/**
	 * Node name to store the noPermissionText value
	 */
	private final String NO_PERMISSION_TEXT_NODE = "nopermission";
	
	/*
	 * (non-Javadoc)
	 * @see ninja.leaping.configurate.objectmapping.serialize.TypeSerializer#deserialize(com.google.common.reflect.TypeToken, ninja.leaping.configurate.ConfigurationNode)
	 */
	@Override
	public @Nullable T deserialize(@NonNull TypeToken<?> type, @NonNull ConfigurationNode value)
			throws ObjectMappingException {
		
		String noPermissionText = value.getNode(NO_PERMISSION_TEXT_NODE).getString();
		return deserializeCustomCommandConfig(type, value, noPermissionText);
	}

	/**
	 * Deserialize the other part of the configuration
	 * 
	 * @param type				Type of the object to be deserialized
	 * @param value				Node to deserialize the data from
	 * @param noPermissionText	NoPermission text already read from the node
	 * @return
	 */
	protected abstract T deserializeCustomCommandConfig(TypeToken<?> type, ConfigurationNode value, String noPermissionText);
	
	/*
	 * (non-Javadoc)
	 * @see ninja.leaping.configurate.objectmapping.serialize.TypeSerializer#serialize(com.google.common.reflect.TypeToken, java.lang.Object, ninja.leaping.configurate.ConfigurationNode)
	 */
	@Override
	public void serialize(@NonNull TypeToken<?> type, @Nullable T obj, @NonNull ConfigurationNode value)
			throws ObjectMappingException {
		value.getNode(NO_PERMISSION_TEXT_NODE).setValue(obj == null ? null : obj.getNoPermissionText());
		
		serializeCustomCommandConfig(obj, value);
	}

	/**
	 * Serialize other part of the configuration
	 * 
	 * @param obj	Object to be serialized
	 * @param value	Node to write to
	 */
	protected abstract void serializeCustomCommandConfig(T obj, ConfigurationNode value);
	
}
