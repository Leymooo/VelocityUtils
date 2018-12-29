package com.github.donotspampls.velocityutils.commands;

import com.github.donotspampls.velocityutils.ConfigManager;
import com.github.donotspampls.velocityutils.VelocityUtils;

import com.velocitypowered.api.command.Command;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;

import net.kyori.text.Component;
import net.kyori.text.serializer.ComponentSerializers;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collection;

@SuppressWarnings("deprecation")
public class SendCommand implements Command {

    private final ProxyServer server;
    private final ConfigManager config;

    public SendCommand(VelocityUtils plugin) {
        this.server = plugin.getProxyServer();
        this.config = plugin.getConfigManager();
    }

    @Override
    public void execute(@NonNull CommandSource source, @NonNull String[] args) {
        if (source.hasPermission(config.getString("send", "permission")) && config.getBoolean("send", "enabled")) {
            if (args.length != 2) {
                source.sendMessage(ComponentSerializers.LEGACY.deserialize(config.getString("send", "usage"), '&'));
                return;
            }

            RegisteredServer target = server.getServer(args[1]).orElse(null);
            if (target == null) {
                source.sendMessage(ComponentSerializers.LEGACY.deserialize(config.getString("send", "no_server"), '&'));
                return;
            }

            Component summoned = ComponentSerializers.LEGACY.deserialize(config.getString("send", "no_server").replace("{0}", target.getServerInfo().getName()), '&');

            switch (args[0].toLowerCase()) {
                case "all":
                    server.getAllPlayers().forEach(p -> p.createConnectionRequest(target).connect());
                    server.broadcast(summoned);
                    break;
                case "current":
                    if (!(source instanceof Player)) {
                        source.sendMessage(ComponentSerializers.LEGACY.deserialize(config.getString("send", "not_player"), '&'));
                        break;
                    }
                    Player player = (Player) source;
                    Collection<Player> players = player.getCurrentServer().get().getServer().getPlayersConnected(); // we know the server exists
                    for (Player p : players) {
                        p.createConnectionRequest(target).connect();
                        p.sendMessage(summoned);
                    }
                    break;
                default:
                    player = server.getPlayer(args[0]).orElse(null);
                    if (player != null) {
                        player.createConnectionRequest(target).connect();
                        player.sendMessage(summoned);
                    } else {
                        source.sendMessage(ComponentSerializers.LEGACY.deserialize(config.getString("send", "no_player"), '&'));
                    }
                    break;
            }
        }
    }

}
