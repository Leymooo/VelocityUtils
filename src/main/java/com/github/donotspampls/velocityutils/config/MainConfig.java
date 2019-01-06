package com.github.donotspampls.velocityutils.config;

import java.nio.file.Path;

public class MainConfig extends ConfigManager {

    public MainConfig(Path dataDirectory) {
        super(dataDirectory, "config.yml");

        addAlertValues();
        addFindValues();
        addListValues();
        addSendValues();
        addSaveServerValues();
        super.save();
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

    private void addSaveServerValues() {
        addNode("save-last-server", "enabled", false);
    }
}
