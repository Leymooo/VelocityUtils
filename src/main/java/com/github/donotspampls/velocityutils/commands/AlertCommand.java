package com.github.donotspampls.velocityutils.commands;

import com.github.donotspampls.velocityutils.VelocityUtils;
import com.velocitypowered.api.command.Command;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.text.TextComponent;
import net.kyori.text.format.TextColor;
import net.kyori.text.serializer.ComponentSerializers;

import javax.annotation.Nonnull;

@SuppressWarnings("deprecation")
public class AlertCommand implements Command {

    private final VelocityUtils plugin;
    private final ProxyServer server;

    public AlertCommand(VelocityUtils plugin) {
        this.plugin = plugin;
        server = plugin.getServer();
    }

    @Override
    public void execute(@Nonnull CommandSource source, @Nonnull String[] args) {
        if (source.hasPermission("velocityutils.alert")) {
            if (args.length == 0) {
                source.sendMessage(TextComponent.of("You must supply a message.", TextColor.RED));
            } else {
                String message = String.join(" ", args);
                TextComponent component = ComponentSerializers.LEGACY.deserialize(plugin.getConfig().getStringOption("alert", "prefix") + message, '&');
                server.broadcast(component);
            }
        } else {
            source.sendMessage(ComponentSerializers.LEGACY.deserialize(plugin.getConfig().getStringOption("alert", "no_permission"), '&'));
        }
    }

}
