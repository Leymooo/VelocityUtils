package com.github.donotspampls.velocityutils.commands;

import com.velocitypowered.api.command.Command;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import net.kyori.text.TextComponent;
import net.kyori.text.format.TextColor;
import net.kyori.text.serializer.ComponentSerializers;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("deprecation")
public class ListCommand implements Command {

    private final ProxyServer server;

    public ListCommand(ProxyServer server) {
        this.server = server;
    }

    @Override
    public void execute(@NonNull CommandSource source, String[] args) {
        if (source.hasPermission("velocityutils.list")) {
            for (RegisteredServer srv : server.getAllServers()) {
                List<String> players = srv.getPlayersConnected().stream().map(Player::getUsername).sorted().collect(Collectors.toList());
                String playersString = String.join(", ", players);
                source.sendMessage(ComponentSerializers.LEGACY.deserialize("&a[" + srv.getServerInfo().getName() + "] &e(" + players.size() + "): &r" + playersString, '&'));
            }
            source.sendMessage(TextComponent.of("Total players online: " + server.getPlayerCount()));
        } else {
            source.sendMessage(TextComponent.of("You do not have permission to execute this command!", TextColor.RED));
        }
    }
}
