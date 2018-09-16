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
import java.util.Optional;

@SuppressWarnings({"deprecation", "OptionalGetWithoutIsPresent"})
public class SendCommand implements Command {

    private final ProxyServer server;
    public SendCommand(ProxyServer server) {
        this.server = server;
    }

    @Override
    public void execute(@NonNull CommandSource source, String[] args) {
        if (source.hasPermission("velocityutils.send")) {
            if (args.length != 2) {
                source.sendMessage(TextComponent.builder("Not enough arguments. Usage: /send <player|current|all> <target>").color(TextColor.RED).build());
            } else if (args[0].equals("all")) {
                Optional<RegisteredServer> sendserver = server.getServerInfo(args[1]);
                Collection<Player> players = server.getAllPlayers();
                for (Player p : players) {
                    if (sendserver.isPresent()) {
                        RegisteredServer prserver = sendserver.get();
                        p.createConnectionRequest(prserver).connect();
                        p.sendMessage(ComponentSerializers.LEGACY.deserialize("&aYou were summoned to &e" + prserver.getServerInfo().getName() + " &aby an administrator.", '&'));
                    } else {
                        source.sendMessage(TextComponent.builder("The server you've chosen does not exist!").color(TextColor.RED).build());
                    }
                }
            } else if (args[0].equals("current")) {
                Player player = (Player) source;
                Optional<RegisteredServer> sendserver = server.getServerInfo(args[1]);
                Collection<Player> players = player.getCurrentServer().get().getServer().getPlayersConnected();
                for (Player p : players) {
                    if (sendserver.isPresent()) {
                        RegisteredServer prserver = sendserver.get();
                        p.createConnectionRequest(prserver).connect();
                        p.sendMessage(ComponentSerializers.LEGACY.deserialize("&aYou were summoned to &e" + prserver.getServerInfo().getName() + " &aby an administrator.", '&'));
                    } else {
                        source.sendMessage(TextComponent.builder("The server you've chosen does not exist!").color(TextColor.RED).build());
                    }
                }
            } else {
                Optional<RegisteredServer> sendserver = server.getServerInfo(args[1]);
                Optional<Player> player = server.getPlayer(args[0]);
                if (player.isPresent() && sendserver.isPresent()) {
                    RegisteredServer prserver = sendserver.get();
                    Player prplayer = player.get();
                    prplayer.createConnectionRequest(sendserver.get()).connect();
                    prplayer.sendMessage(ComponentSerializers.LEGACY.deserialize("&aYou were summoned to &e" + prserver.getServerInfo().getName() + " &aby an administrator.", '&'));
                } else {
                    source.sendMessage(TextComponent.builder("Either the player or the server you've chosen does not exist!").color(TextColor.RED).build());
                }
            }
        } else {
            source.sendMessage(TextComponent.builder("You do not have permission to execute this command!").color(TextColor.RED).build());
        }
    }

}
