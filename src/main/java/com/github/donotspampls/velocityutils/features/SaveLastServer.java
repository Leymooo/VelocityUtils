package com.github.donotspampls.velocityutils.features;

import com.github.donotspampls.velocityutils.config.ConfigManager;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import com.velocitypowered.api.event.player.ServerPreConnectEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class SaveLastServer {

    private final ProxyServer server;
    private final ConfigManager lastServers;
    private final Set<Player> players;
    private boolean enabled = false;

    public SaveLastServer(ProxyServer server, ConfigManager lastServers) {
        this.server = server;
        this.lastServers = lastServers;
        this.players = new HashSet<>();
    }

    @Subscribe
    public void onServerLogin(PostLoginEvent ev) {
        if (enabled) {
            this.players.add(ev.getPlayer());
        }
    }

    @Subscribe
    public void onServerPreConnect(ServerPreConnectEvent ev) {
        if (enabled) {
            Player player = ev.getPlayer();
            if (players.remove(player) && ev.getResult().isAllowed()) {
                String lastServer = lastServers.getString(player.getUsername());
                if (lastServer != null) {
                    Optional<RegisteredServer> toConnect = server.getServer(lastServer);
                    toConnect.ifPresent(srv -> ev.setResult(ServerPreConnectEvent.ServerResult.allowed(srv)));
                }
            }
        }
    }

    @Subscribe
    public void onDisconnect(DisconnectEvent ev) {
        if (enabled) {
            players.remove(ev.getPlayer());
            ev.getPlayer().getCurrentServer().ifPresent(srv -> lastServers.set(srv.getServerInfo().getName(), ev.getPlayer().getUsername()));
        }
    }

    @Subscribe
    public void onShutDown(ProxyShutdownEvent ev) {
        lastServers.save();
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled != null && enabled;
    }
}
