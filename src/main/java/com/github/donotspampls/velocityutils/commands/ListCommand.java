package com.github.donotspampls.velocityutils.commands;

import com.velocitypowered.api.command.Command;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.ServerInfo;
import net.kyori.text.TextComponent;
import net.kyori.text.format.TextColor;
import net.kyori.text.serializer.ComponentSerializers;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("deprecation")
public class ListCommand implements Command {

    private final ProxyServer server;
    public ListCommand(ProxyServer server) {
        this.server = server;
    }

    @Override
    public void execute(@NonNull CommandSource source, String[] args) {
        if (source.hasPermission("velocityutils.send")) {
            for (ServerInfo sinfo : server.getAllServers()) {
                List<String> players = new ArrayList<>();
                //for (Player player : sinfo.getPlayers()) players.add(player.getUsername());
                //todo: waiting for the ability to get a server's players and player count
                players.sort(String.CASE_INSENSITIVE_ORDER);

                source.sendMessage(ComponentSerializers.LEGACY.deserialize("&a[" + sinfo.getName() + "] &e(PLAYER_COUNT_HERE): &r" + players, '&'));
            }
            source.sendMessage(TextComponent.of("Total players online:" + server.getPlayerCount()));
        } else {
            source.sendMessage(TextComponent.builder("You do not have permission to execute this command!").color(TextColor.RED).build());
        }
    }
}
