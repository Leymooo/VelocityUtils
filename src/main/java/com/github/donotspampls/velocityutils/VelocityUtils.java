package com.github.donotspampls.velocityutils;

import java.io.File;
import java.nio.file.Path;

import org.slf4j.Logger;

import com.github.donotspampls.velocityutils.commands.AlertCommand;
import com.github.donotspampls.velocityutils.commands.FindCommand;
import com.github.donotspampls.velocityutils.commands.ListCommand;
import com.github.donotspampls.velocityutils.commands.SendCommand;
import com.github.donotspampls.velocityutils.config.Configuration;
import com.github.donotspampls.velocityutils.config.parser.ConfigurationProvider;
import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;

@Plugin(id = "velocityutils", name = "VelocityUtils", version = "1.0.0",
        description = "Useful utility commands for Velocity",
        authors = "DoNotSpamPls")
public class VelocityUtils {

    private final ProxyServer server;
    private final Logger logger;
    private final File userConfigDirectory;

    @Inject
    public VelocityUtils(ProxyServer server, Logger logger, @DataDirectory Path userConfigDirectory) {
        this.server = server;
        this.logger = logger;
        this.userConfigDirectory = new File(userConfigDirectory.toFile(),  "velocityutils");
    }

    @Subscribe
    public void onProxyInitialize(ProxyInitializeEvent event) {
        logger.info("Enabling VelocityUtils version 1.0.1");

        @SuppressWarnings("unused")
		Configuration configuration = null;
        try {
        	configuration = ConfigurationProvider.getConfiguration(userConfigDirectory);
        } catch(Exception e) {
        	logger.error("Error while trying to read configuration");
        	logger.error(e.getMessage(), e);
        }
        
        // Register all the commands
        server.getCommandManager().register(new AlertCommand(server), "alert");
        server.getCommandManager().register(new FindCommand(server), "find");
        server.getCommandManager().register(new ListCommand(server), "glist");
        server.getCommandManager().register(new SendCommand(server), "send");
    }

}
