package com.github.donotspampls.velocityutils;

import com.github.donotspampls.velocityutils.commands.AlertCommand;
import com.github.donotspampls.velocityutils.commands.FindCommand;
import com.github.donotspampls.velocityutils.commands.ListCommand;
import com.github.donotspampls.velocityutils.commands.SendCommand;
import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

@Plugin(id = "velocityutils", name = "VelocityUtils", version = "1.0.0",
        description = "Useful utility commands for Velocity",
        authors = "DoNotSpamPls")
public class VelocityUtils {

    private final ProxyServer server;
    private final Logger logger;

    @Inject
    public VelocityUtils(ProxyServer server, Logger logger) {
        this.server = server;
        this.logger = logger;
    }

    @Subscribe
    public void onProxyInitialize(ProxyInitializeEvent event) {
        logger.info("Enabling VelocityUtils version 1.0.0");

        // Register all the commands
        server.getCommandManager().register(new AlertCommand(server), "alert");
        server.getCommandManager().register(new FindCommand(server), "find");
        server.getCommandManager().register(new ListCommand(server), "glist");
        server.getCommandManager().register(new SendCommand(server), "send");
    }

}
