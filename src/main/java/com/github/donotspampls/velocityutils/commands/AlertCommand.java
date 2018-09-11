package com.github.donotspampls.velocityutils.commands;

import com.velocitypowered.api.command.Command;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.util.LegacyChatColorUtils;
import net.kyori.text.TextComponent;
import net.kyori.text.format.TextColor;
import net.kyori.text.serializer.ComponentSerializers;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.annotation.Nonnull;
import java.util.Collection;

@SuppressWarnings("deprecation")
public class AlertCommand implements Command {

    private final ProxyServer server;
    public AlertCommand(ProxyServer server) {
        this.server = server;
    }

    public void execute(@Nonnull CommandSource source, @Nonnull String[] args) {
        if (source.hasPermission("velocityutils.alert")) {
            if (args.length == 0) {
                source.sendMessage(TextComponent.builder("You must supply a message.").color(TextColor.RED).build());
            } else {
                // Generate the message
                StringBuilder builder = new StringBuilder();
                builder.append(LegacyChatColorUtils.translate('&', "&8[&4Alert&8] &r"));
                for ( String s : args ) {
                    builder.append(LegacyChatColorUtils.translate( '&', s ));
                    builder.append(" ");
                }
                @NonNull TextComponent message = ComponentSerializers.LEGACY.deserialize(builder.substring( 0, builder.length() - 1 ));

                // Get all players and send the message
                Collection<Player> players = server.getAllPlayers();
                for (Player p : players) {
                    p.sendMessage(message);
                }
            }
        } else {
            source.sendMessage(TextComponent.builder("You do not have permission to execute this command!").color(TextColor.RED).build());
        }
    }

}
