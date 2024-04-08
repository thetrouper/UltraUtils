package me.trouper.ultrautils.events;

import io.github.itzispyder.pdk.events.CustomListener;
import me.trouper.ultrautils.UltraUtils;
import me.trouper.ultrautils.functions.Text;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinLeaveEvent implements CustomListener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (UltraUtils.config.plugin.joinMessage.isBlank()) {
            e.joinMessage(null);
            return;
        }
        e.joinMessage(Component.text(Text.color(UltraUtils.config.plugin.joinMessage.formatted(e.getPlayer().getName()))));
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        if (UltraUtils.config.plugin.leaveMessage.isBlank()) {
            e.quitMessage(null);
            return;
        }
        e.quitMessage(Component.text(Text.color(UltraUtils.config.plugin.leaveMessage.formatted(e.getPlayer().getName()))));
    }
}
