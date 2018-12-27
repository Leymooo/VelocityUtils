package com.github.donotspampls.velocityutils;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.yaml.YAMLConfigurationLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class Config {

    private File configFile;
    private ConfigurationNode parent;

    public Config(Path configDirectory, String configName) {

        if (!configDirectory.toFile().exists()) configDirectory.toFile().mkdir();
        try {
            this.configFile = new File(configDirectory.toFile(), configName);
            ConfigurationLoader<ConfigurationNode> config = YAMLConfigurationLoader.builder().setFile(configFile).build();
            this.parent = config.load();
            values();
            config.save(parent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void values() {
        if (parent.getNode("find", "no_permission").isVirtual()) {
            parent.getNode("find", "no_permission").setValue("&4You do not have permission to execute this command!");
        }
        if (parent.getNode("alert", "no_permission").isVirtual()) {
            parent.getNode("alert", "no_permission").setValue("&4You do not have permission to execute this command!");
        }
        if (parent.getNode("alert", "prefix").isVirtual()) {
            parent.getNode("alert", "prefix").setValue("&8[&4Alert&8] &r");
        }
    }

    public String getStringOption(Object... nodes) {
        return parent.getNode(nodes).getString();
    }
}
