package com.github.donotspampls.velocityutils.commands;

import com.velocitypowered.api.command.Command;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.text.TextComponent;
import net.kyori.text.format.TextColor;
import net.kyori.text.serializer.ComponentSerializers;

import javax.annotation.Nonnull;

public class AlertCommand implements Command {

    private final ProxyServer server;

    public AlertCommand(ProxyServer server) {
        this.server = server;
    }

    @Override
    public void execute(@Nonnull CommandSource source, @Nonnull String[] args) {
        if (source.hasPermission("velocityutils.alert")) {
            if (args.length == 0) {
                source.sendMessage(TextComponent.of("You must supply a message.", TextColor.RED));
            } else {
                String message = String.join(" ", args);
                TextComponent component = ComponentSerializers.LEGACY.deserialize("&8[&4Alert&8] &r" + message, '&');
                server.broadcast(component);
            }
        } else {
            source.sendMessage(TextComponent.of("You do not have permission to execute this command!", TextColor.RED));
        }
    }

}
