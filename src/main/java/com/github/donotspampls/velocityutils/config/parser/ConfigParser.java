package com.github.donotspampls.velocityutils.config.parser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Callable;

import com.github.donotspampls.velocityutils.config.AlertConfig;
import com.github.donotspampls.velocityutils.config.Configuration;
import com.github.donotspampls.velocityutils.config.FindConfig;
import com.github.donotspampls.velocityutils.config.serializers.AlertConfigSerializer;
import com.github.donotspampls.velocityutils.config.serializers.FindConfigSerializer;
import com.google.common.reflect.TypeToken;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializerCollection;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers;
import ninja.leaping.configurate.yaml.YAMLConfigurationLoader;

/**
 * Parses the {@link Configuration} and returns
 * 
 * @author satish
 */
public class ConfigParser {

	/**
	 * Parse the config file and return the configuration
	 * 
	 * @return {@link Configuration}
	 * @throws Exception
	 */
	public static Configuration parseConfig() throws Exception {
		TypeSerializerCollection serializers = TypeSerializers.getDefaultSerializers().newChild();
		serializers.registerType(TypeToken.of(AlertConfig.class), new AlertConfigSerializer());
		serializers.registerType(TypeToken.of(FindConfig.class), new FindConfigSerializer());
		
		ConfigurationOptions configOptions = ConfigurationOptions.defaults();
		configOptions = configOptions.setSerializers(serializers);
		
		try (InputStream configStream = ConfigParser.class.getResourceAsStream("/config.yaml")) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(configStream, StandardCharsets.UTF_8.name()));
			Callable<BufferedReader> readerCallable = new Callable<BufferedReader>() {
				/*
				 * (non-Javadoc)
				 * @see java.util.concurrent.Callable#call()
				 */
				@Override
				public BufferedReader call() throws Exception {
					return reader;
				}
			};
			
			YAMLConfigurationLoader loader = YAMLConfigurationLoader.builder().setSource(readerCallable).build();
			ConfigurationNode node = loader.load(configOptions);
			
			ConfigurationNode alertConfigNode = node.getNode("alert");
			AlertConfig alertConfig = alertConfigNode.getValue(TypeToken.of(AlertConfig.class));
			
			ConfigurationNode findConfigNode = node.getNode("find");
			FindConfig findConfig = findConfigNode.getValue(TypeToken.of(FindConfig.class));
			
			return new ConfigurationImpl(alertConfig, findConfig);
		}
	}

	public static void main(String[] args) throws Exception {
		Configuration configuration = parseConfig();
		AlertConfig alertConfig = configuration.getAlertConfig();
		FindConfig findConfig = configuration.getFindConfig();
		
		System.out.println(alertConfig.getNoPermissionText());
		System.out.println(alertConfig.getPrefix());
		
		System.out.println(findConfig.getNoPermissionText());
	}
	
}
