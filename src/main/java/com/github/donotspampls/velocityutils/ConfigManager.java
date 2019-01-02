package com.github.donotspampls.velocityutils;

import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.yaml.YAMLConfigurationLoader;
import org.yaml.snakeyaml.DumperOptions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class ConfigManager {

    private ConfigurationNode parent;
    private File configFile;

    ConfigManager(Path dataDirectory, String configName) {
        try {
            configFile = new File(dataDirectory.toFile(), configName);
            ConfigurationLoader<ConfigurationNode> config = YAMLConfigurationLoader.builder().setFlowStyle(DumperOptions.FlowStyle.BLOCK).setFile(configFile).build();
            this.parent = config.load();

            addAlertValues();
            addFindValues();
            addListValues();
            addSendValues();

            config.save(parent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * Beware: large methods ahead (object mappers are even more stupid than this)
     */
    private void addAlertValues() {
        addNode("alert", "enabled", true);
        addNode("alert", "permission", "velocityutils.alert");
        addNode("alert", "prefix", "&8[&4Alert&8] &r");
        addNode("alert", "no_message", "&4You must supply a message.");
    }

    private void addFindValues() {
        addNode("find", "enabled", true);
        addNode("find", "permission", "velocityutils.find");
        addNode("find", "no_username", "&4Please follow this command by a user name");
        addNode("find", "user_offline", "&4That user is not online");
        addNode("find", "response", "&e{0} &ais online in server &e{1}");
    }

    private void addListValues() {
        addNode("list", "enabled", true);
        addNode("list", "permission", "velocityutils.list");
        addNode("list", "response", "&a[{0}] &e({1}): {2}");
        addNode("list", "total_players", "&fTotal players online: {0}");
    }

    private void addSendValues() {
        addNode("send", "enabled", true);
        addNode("send", "permission", "velocityutils.send");
        addNode("send", "usage", "&4Not enough arguments. Usage: /send <player|current|all> <target>");
        addNode("send", "summoned", "&aYou were summoned to &e{0} &aby an administrator.");
        addNode("send", "no_server", "&4The server you've chosen does not exist!");
        addNode("send", "not_player", "&4You are not a player!");
        addNode("send", "no_player", "&4The player you've chosen does not exist!");
    }

    void reload() {
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
    
    private void addNode(String section, String key, Object value) {
        if (parent.getNode(section, key).isVirtual()) {
            parent.getNode(section, key).setValue(value);
        }
    }
}
