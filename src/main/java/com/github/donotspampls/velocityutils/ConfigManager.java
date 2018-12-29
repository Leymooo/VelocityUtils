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
        if (parent.getNode("alert", "enabled").isVirtual()) {
            parent.getNode("alert", "enabled").setValue(true);
        }
        if (parent.getNode("alert", "permission").isVirtual()) {
            parent.getNode("alert", "permission").setValue("velocityutils.alert");
        }
        if (parent.getNode("alert", "prefix").isVirtual()) {
            parent.getNode("alert", "prefix").setValue("&8[&4Alert&8] &r");
        }
        if (parent.getNode("alert", "no_message").isVirtual()) {
            parent.getNode("alert", "no_message").setValue("&4You must supply a message.");
        }
    }

    private void addFindValues() {
        if (parent.getNode("find", "enabled").isVirtual()) {
            parent.getNode("find", "enabled").setValue(true);
        }
        if (parent.getNode("find", "permission").isVirtual()) {
            parent.getNode("find", "permission").setValue("velocityutils.find");
        }
        if (parent.getNode("find", "no_username").isVirtual()) {
            parent.getNode("find", "no_username").setValue("&4Please follow this command by a user name");
        }
        if (parent.getNode("find", "user_offline").isVirtual()) {
            parent.getNode("find", "user_offline").setValue("&4That user is not online");
        }
        if (parent.getNode("find", "response").isVirtual()) {
            parent.getNode("find", "response").setValue("&e{0} &ais online in server &e{1}");
        }
    }

    private void addListValues() {
        if (parent.getNode("list", "enabled").isVirtual()) {
            parent.getNode("list", "enabled").setValue(true);
        }
        if (parent.getNode("list", "permission").isVirtual()) {
            parent.getNode("list", "permission").setValue("velocityutils.list");
        }
        if (parent.getNode("list", "response").isVirtual()) {
            parent.getNode("list", "response").setValue("&a[{0}] &e({1}): {2}");
        }
        if (parent.getNode("list", "total_players").isVirtual()) {
            parent.getNode("list", "total_players").setValue("&fTotal players online: {0}");
        }
    }

    private void addSendValues() {
        if (parent.getNode("send", "enabled").isVirtual()) {
            parent.getNode("send", "enabled").setValue(true);
        }
        if (parent.getNode("send", "permission").isVirtual()) {
            parent.getNode("send", "permission").setValue("velocityutils.send");
        }
        if (parent.getNode("send", "usage").isVirtual()) {
            parent.getNode("send", "usage").setValue("&4Not enough arguments. Usage: /send <player|current|all> <target>");
        }
        if (parent.getNode("send", "summoned").isVirtual()) {
            parent.getNode("send", "summoned").setValue("&aYou were summoned to &e{0} &aby an administrator.");
        }
        if (parent.getNode("send", "no_server").isVirtual()) {
            parent.getNode("send", "no_server").setValue("&4The server you've chosen does not exist!");
        }
        if (parent.getNode("send", "not_player").isVirtual()) {
            parent.getNode("send", "not_player").setValue("&4You are not a player!");
        }
        if (parent.getNode("send", "no_player").isVirtual()) {
            parent.getNode("send", "no_player").setValue("&4The player you've chosen does not exist!");
        }
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
}
