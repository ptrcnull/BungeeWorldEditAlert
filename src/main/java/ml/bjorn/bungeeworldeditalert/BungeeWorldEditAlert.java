package ml.bjorn.bungeeworldeditalert;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.connection.Server;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

import java.util.Objects;
import java.util.Optional;

public final class BungeeWorldEditAlert extends Plugin implements Listener {

    @Override
    public void onEnable() {
        getProxy().getPluginManager().registerListener(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onChatEvent (ChatEvent event) {
        if (!(event.getSender() instanceof ProxiedPlayer) || !(event.getReceiver() instanceof Server)) {
            return;
        }
        ProxiedPlayer sender = (ProxiedPlayer) event.getSender();
        Server server = (Server) event.getReceiver();

        if (event.isCommand() && event.getMessage().startsWith("//")) {
            BaseComponent[] message = new ComponentBuilder("Warning!").color(ChatColor.RED)
                .append(sender.getDisplayName()).color(ChatColor.YELLOW)
                .append(" used WorldEdit on a server ").color(ChatColor.RED)
                .append(server.getInfo().getName()).color(ChatColor.AQUA)
                .append(": ").color(ChatColor.RED)
                .append(event.getMessage()).color(ChatColor.YELLOW)
                .create();

            for (ProxiedPlayer player : getProxy().getPlayers()) {
                if (player.hasPermission("worldeditalert.notify") && !Objects.equals(player, sender)) {
                    player.sendMessage(message);
                }
            }
        }
    }
}
