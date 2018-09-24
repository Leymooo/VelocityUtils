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

import java.util.Collection;
import net.kyori.text.Component;

public class SendCommand implements Command {

    private final ProxyServer server;

    public SendCommand(ProxyServer server) {
        this.server = server;
    }

    @Override
    public void execute(@NonNull CommandSource source, String[] args) {
        if (source.hasPermission("velocityutils.send")) {
            if (args.length != 2) {
                source.sendMessage(TextComponent.of("Not enough arguments. Usage: /send <player|current|all> <target>", TextColor.RED));
                return;
            }

            RegisteredServer target = server.getServer(args[1]).orElse(null);
            if (target == null) {
                source.sendMessage(TextComponent.of("The server you've chosen does not exist!", TextColor.RED));
                return;
            }

            Component summoned = ComponentSerializers.LEGACY.deserialize("&aYou were summoned to &e" + target.getServerInfo().getName() + " &aby an administrator.", '&');

            switch (args[0].toLowerCase()) {
                case "all":
                    server.getAllPlayers().forEach(p -> p.createConnectionRequest(target).connect());
                    server.broadcast(summoned);
                    break;
                case "current":
                    if (!(source instanceof Player)) {
                        source.sendMessage(TextComponent.of("You are not player!", TextColor.RED));
                        break;
                    }
                    Player player = (Player) source;
                    Collection<Player> players = player.getCurrentServer().get().getServer().getPlayersConnected();
                    for (Player p : players) {
                        p.createConnectionRequest(target).connect();
                        p.sendMessage(summoned);
                    }
                    break;
                case "player":
                    player = server.getPlayer(args[0]).orElse(null);
                    if (player != null) {
                        player.createConnectionRequest(target).connect();
                        player.sendMessage(summoned);
                    } else {
                        source.sendMessage(TextComponent.of("Either the player you've chosen does not exist!", TextColor.RED));
                    }
                    break;
                default:
                    source.sendMessage(TextComponent.of("Usage: /send <player|current|all> <target>", TextColor.RED));
                    break;
            }
        } else {
            source.sendMessage(TextComponent.of("You do not have permission to execute this command!", TextColor.RED));
        }
    }

}
