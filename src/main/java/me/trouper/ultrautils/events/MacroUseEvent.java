package me.trouper.ultrautils.events;

import io.github.itzispyder.pdk.events.CustomListener;
import me.trouper.ultrautils.commands.admin.MacroToolCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

public class MacroUseEvent implements CustomListener {
    MacroToolCommand mtc = new MacroToolCommand();

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (!p.hasPermission("ultrautils.macrotool")) return;
        if (e.getItem() == null) return;
        switch (e.getAction()) {
            case RIGHT_CLICK_AIR,RIGHT_CLICK_BLOCK -> {
                List<String> commands = mtc.getMacros(e.getItem());
                for (String command : commands) {
                    p.performCommand(command);
                }
            }
        }
    }
}
