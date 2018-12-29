package com.github.donotspampls.velocityutils;

import com.github.donotspampls.velocityutils.commands.AlertCommand;
import com.github.donotspampls.velocityutils.commands.FindCommand;
import com.github.donotspampls.velocityutils.commands.ListCommand;
import com.github.donotspampls.velocityutils.commands.SendCommand;
import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyReloadEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

import java.nio.file.Files;
import java.nio.file.Path;

@Plugin(id = "velocityutils", name = "VelocityUtils", version = "1.1.0",
        description = "Useful utility commands for Velocity",
        authors = "DoNotSpamPls")
public class VelocityUtils {

    private final ProxyServer server;
    private final Logger logger;
    private final Path dataDirectory;
    private ConfigManager config;

    @Inject
    public VelocityUtils(ProxyServer server, Logger logger, @DataDirectory Path userConfigDirectory) {
        this.server = server;
        this.logger = logger;
        this.dataDirectory = userConfigDirectory;
    }

    @Subscribe
    public void onProxyInitialize(ProxyInitializeEvent event) {
        logger.info("Enabling VelocityUtils version 1.1.0");

        try {
            if (!Files.exists(dataDirectory))
                Files.createDirectory(dataDirectory);

            config = new ConfigManager(dataDirectory, "config.yml");
        } catch (Exception e) {
            logger.error("Unable to load configuration!");
            logger.error(e.getMessage(), e);
        }

        // Register all the commands
        server.getCommandManager().register(new AlertCommand(this), "alert");
        server.getCommandManager().register(new FindCommand(this), "find");
        server.getCommandManager().register(new ListCommand(this), "glist");
        server.getCommandManager().register(new SendCommand(this), "send");
    }

    @Subscribe
    public void onReloadEvent(ProxyReloadEvent event) {
        config.reload();
    }

    public ConfigManager getConfigManager() {
        return config;
    }

    public ProxyServer getProxyServer() {
        return server;
    }
}
