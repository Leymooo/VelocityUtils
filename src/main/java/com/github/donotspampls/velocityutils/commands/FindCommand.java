package com.github.donotspampls.velocityutils.commands;

import com.github.donotspampls.velocityutils.config.ConfigManager;
import com.github.donotspampls.velocityutils.VelocityUtils;

import com.velocitypowered.api.command.Command;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.ServerConnection;

import net.kyori.text.serializer.ComponentSerializers;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Optional;

@SuppressWarnings("deprecation")
public class FindCommand implements Command {

    private final ProxyServer server;
    private final ConfigManager config;

    public FindCommand(VelocityUtils plugin) {
        this.server = plugin.getProxyServer();
        this.config = plugin.getConfigManager();
    }

    @Override
    public void execute(@NonNull CommandSource source, @NonNull String[] args) {
        if (args.length == 0) {
            source.sendMessage(ComponentSerializers.LEGACY.deserialize(config.getString("find", "no_username"), '&'));
        } else {
            Optional<Player> player = server.getPlayer(args[0]);
            if (!player.isPresent()) {
                source.sendMessage(ComponentSerializers.LEGACY.deserialize(config.getString("alert", "user_offline"), '&'));
            } else {
                Player p = player.get();
                Optional<ServerConnection> server = p.getCurrentServer();
                server.ifPresent(srv -> source.sendMessage(ComponentSerializers.LEGACY.deserialize(
                        config.getString("find", "response")
                                .replace("{0}", p.getUsername())
                                .replace("{1}", srv.getServerInfo().getName()), '&')
                ));
            }
        }
    }

    @Override
    public boolean hasPermission(CommandSource source, String[] args) {
        return config.getBoolean("find", "enabled") && source.hasPermission(config.getString("find", "permission"));
    }

}
