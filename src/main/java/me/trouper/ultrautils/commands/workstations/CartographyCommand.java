package me.trouper.ultrautils.commands.workstations;

import io.github.itzispyder.pdk.commands.Args;
import io.github.itzispyder.pdk.commands.CommandRegistry;
import io.github.itzispyder.pdk.commands.CustomCommand;
import io.github.itzispyder.pdk.commands.Permission;
import io.github.itzispyder.pdk.commands.completions.CompletionBuilder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandRegistry(value = "cartography", permission = @Permission("ultrautils.cartography"),playersOnly = true)
public class CartographyCommand implements CustomCommand {
    @Override
    public void dispatchCommand(CommandSender sender, Command command, Args args) {
        Player p = (Player) sender;
        p.openCartographyTable(p.getLocation(),true);
    }

    @Override
    public void dispatchCompletions(CompletionBuilder b, CommandSender sender) {

    }
}
