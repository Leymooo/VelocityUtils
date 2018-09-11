package com.github.donotspampls.velocityutils.commands;

import com.velocitypowered.api.command.Command;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.text.TextComponent;
import net.kyori.text.format.TextColor;
import net.kyori.text.serializer.ComponentSerializers;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Optional;

@SuppressWarnings({"deprecation", "OptionalGetWithoutIsPresent"})
public class FindCommand implements Command {

    private final ProxyServer server;
    public FindCommand(ProxyServer server) {
        this.server = server;
    }

    @Override
    public void execute(@NonNull CommandSource source, String[] args) {
        if (source.hasPermission("velocityutils.find")) {
            if (args.length == 0) {
                source.sendMessage(TextComponent.builder("Please follow this command by a user name").color(TextColor.RED).build());
            } else {
                Optional<Player> player = server.getPlayer(args[0]);
                if (!player.isPresent()) {
                    source.sendMessage(TextComponent.builder("That user is not online").color(TextColor.RED).build());
                } else {
                    Player prplayer = player.get();
                    String onlineserver = prplayer.getCurrentServer().get().getServerInfo().getName(); // we know it's present
                    prplayer.sendMessage(ComponentSerializers.LEGACY.deserialize("&e" + prplayer.getUsername() + " &ais online in server &e" + onlineserver, '&'));
                }
            }
        } else {
            source.sendMessage(TextComponent.builder("You do not have permission to execute this command!").color(TextColor.RED).build());
        }
    }

}
