package com.github.donotspampls.velocityutils.commands;

import com.github.donotspampls.velocityutils.config.ConfigManager;
import com.github.donotspampls.velocityutils.VelocityUtils;

import com.velocitypowered.api.command.Command;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;

import net.kyori.text.serializer.ComponentSerializers;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("deprecation")
public class ListCommand implements Command {

    private final ProxyServer server;
    private final ConfigManager config;

    public ListCommand(VelocityUtils plugin) {
        this.server = plugin.getProxyServer();
        this.config = plugin.getConfigManager();
    }

    @Override
    public void execute(@NonNull CommandSource source, @NonNull String[] args) {
        for (RegisteredServer srv : server.getAllServers()) {
            List<String> players = srv.getPlayersConnected().stream().map(Player::getUsername).sorted().collect(Collectors.toList());
            String playersString = String.join(", ", players);
            source.sendMessage(ComponentSerializers.LEGACY.deserialize(config.getString("list", "response")
                    .replace("{0}", srv.getServerInfo().getName())
                    .replace("{1}", String.valueOf(players.size()))
                    .replace("{2}", playersString),
                    '&'));
        }
        source.sendMessage(ComponentSerializers.LEGACY.deserialize(
                config.getString("list", "total_players")
                        .replace("{0}", String.valueOf(server.getPlayerCount())), '&'));
    }

    @Override
    public boolean hasPermission(CommandSource source, String[] args) {
        return config.getBoolean("list", "enabled") && source.hasPermission(config.getString("list", "permission"));
    }

}
