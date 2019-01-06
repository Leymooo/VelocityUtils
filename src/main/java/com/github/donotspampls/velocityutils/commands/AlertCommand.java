package com.github.donotspampls.velocityutils.commands;

import com.github.donotspampls.velocityutils.config.ConfigManager;
import com.github.donotspampls.velocityutils.VelocityUtils;

import com.velocitypowered.api.command.Command;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.ProxyServer;

import net.kyori.text.TextComponent;
import net.kyori.text.serializer.ComponentSerializers;

import javax.annotation.Nonnull;

@SuppressWarnings("deprecation")
public class AlertCommand implements Command {

    private final ProxyServer server;
    private final ConfigManager config;

    public AlertCommand(VelocityUtils plugin) {
        this.server = plugin.getProxyServer();
        this.config = plugin.getConfigManager();
    }

    @Override
    public void execute(@Nonnull CommandSource source, @Nonnull String[] args) {
        if (args.length == 0) {
            source.sendMessage(ComponentSerializers.LEGACY.deserialize(config.getString("alert", "no_message"), '&'));
        } else {
            String message = String.join(" ", args);
            TextComponent component = ComponentSerializers.LEGACY.deserialize(config.getString("alert", "prefix") + message, '&');
            server.broadcast(component);
        }
    }

    @Override
    public boolean hasPermission(CommandSource source, String[] args) {
        return config.getBoolean("alert", "enabled") && source.hasPermission(config.getString("alert", "permission"));
    }

}
