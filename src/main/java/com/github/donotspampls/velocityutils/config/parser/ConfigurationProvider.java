package com.github.donotspampls.velocityutils.config.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;

import com.github.donotspampls.velocityutils.config.Configuration;

/**
 * Provides user specified configuration, if any. If there is none, it'll
 * default to the configuration packaged with the tool
 * 
 * @author satish
 */
public class ConfigurationProvider {
	/**
	 * Provides the user specified configuration, if any. If there is none,
	 * it'll default to the configuration packaged with the tool
	 * 
	 * @param userDirectory
	 *            Path of the directory containing the file with the user
	 *            specified configuration
	 * @return the user specified configuration, if any. If there is none, it'll
	 *         default to the configuration packaged with the tool. As a side
	 *         effect, it'll copy the default configuration file to this
	 *         directory
	 * @throws Exception
	 */
	public static Configuration getConfiguration(File userDirectory) throws Exception {
		// no configured user directory is given
		if (userDirectory == null) {
			return getDefaultConfig();
		}

		File userConfigFile = new File(userDirectory, ConfigParser.CONFIG_FILE_NAME);

		// directory does not exist
		if (!userDirectory.exists()) {
			// directory created successfully
			if (userDirectory.mkdirs()) {
				copyDefaultConfig(userDirectory);
			} else {// directory creation failed
				return getDefaultConfig();
			}
		} else {// directory exists
			if (!userConfigFile.exists()) {// file does not exist
				copyDefaultConfig(userDirectory);
			}
		}

		// read from the user config file
		try (InputStream configStream = new FileInputStream(userConfigFile)) {
			return ConfigParser.parseConfig(configStream);
		}
	}

	/**
	 * Copy the default configuration to given directory
	 * 
	 * @param userDirectory
	 *            directory to copy the default config
	 * @throws IOException
	 */
	private static void copyDefaultConfig(File userDirectory) throws IOException {
		File userConfigFile = new File(userDirectory, ConfigParser.CONFIG_FILE_NAME);
		try (InputStream defaultConfigStream = ConfigParser.class
				.getResourceAsStream("/" + ConfigParser.CONFIG_FILE_NAME);
				OutputStream userOutStream = new FileOutputStream(userConfigFile)) {
			IOUtils.copy(defaultConfigStream, userOutStream);
		}
	}

	/**
	 * Return the default config packaged with the tool
	 * 
	 * @return the default config packaged with the tool
	 * @throws Exception
	 */
	private static Configuration getDefaultConfig() throws Exception {
		try (InputStream configStream = ConfigParser.class.getResourceAsStream("/" + ConfigParser.CONFIG_FILE_NAME)) {
			return ConfigParser.parseConfig(configStream);
		}
	}

}
