package com.github.donotspampls.velocityutils.commands;

import com.github.donotspampls.velocityutils.VelocityUtils;
import com.velocitypowered.api.command.Command;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.ServerConnection;
import net.kyori.text.TextComponent;
import net.kyori.text.format.TextColor;
import net.kyori.text.serializer.ComponentSerializers;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Optional;

@SuppressWarnings("deprecation")
public class FindCommand implements Command {

    private final VelocityUtils plugin;
    private final ProxyServer server;

    public FindCommand(VelocityUtils plugin) {
        this.plugin = plugin;
        server = plugin.getServer();
    }

    @Override
    public void execute(@NonNull CommandSource source, String[] args) {
        if (source.hasPermission("velocityutils.find")) {
            if (args.length == 0) {
                source.sendMessage(TextComponent.of("Please follow this command by a user name", TextColor.RED));
            } else {
                Optional<Player> player = server.getPlayer(args[0]);
                if (!player.isPresent()) {
                    source.sendMessage(TextComponent.of("That user is not online", TextColor.RED));
                } else {
                    Player pl = player.get();
                    Optional<ServerConnection> server = pl.getCurrentServer();
                    server.ifPresent(srv -> source.sendMessage(ComponentSerializers.LEGACY.deserialize("&e" + pl.getUsername() + " &ais online in server &e" + srv.getServerInfo().getName(), '&')));
                }
            }
        } else {
            source.sendMessage(ComponentSerializers.LEGACY.deserialize(plugin.getConfig().getStringOption("find", "no_permission"), '&'));
        }
    }

}
