package com.github.donotspampls.velocityutils.config;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.yaml.YAMLConfigurationLoader;
import org.yaml.snakeyaml.DumperOptions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public abstract class ConfigManager {

    private ConfigurationLoader<ConfigurationNode> config;
    private ConfigurationNode parent;
    private File configFile;

    ConfigManager(Path dataDirectory, String configName) {
        configFile = new File(dataDirectory.toFile(), configName);
        config = YAMLConfigurationLoader.builder().setFlowStyle(DumperOptions.FlowStyle.BLOCK).setFile(configFile).build();

        try {
            this.parent = config.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            config.save(parent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reload() {
        try {
            this.parent = YAMLConfigurationLoader.builder().setFile(configFile).build().load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getString(Object... nodes) {
        return parent.getNode(nodes).getString();
    }

    public Boolean getBoolean(Object... nodes) {
        return parent.getNode(nodes).getBoolean();
    }

    public void set(Object value, Object... nodes) {
        parent.getNode(nodes).setValue(value);
    }

    public void addNode(String section, String key, Object value) {
        if (parent.getNode(section, key).isVirtual()) {
            parent.getNode(section, key).setValue(value);
        }
    }

    public static ConfigManager create(Path path, String fileName) {
        return new ConfigManager(path, fileName) {
        };
    }
}
