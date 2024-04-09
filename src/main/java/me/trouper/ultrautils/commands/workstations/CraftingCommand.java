package me.trouper.ultrautils.commands.workstations;

import io.github.itzispyder.pdk.commands.Args;
import io.github.itzispyder.pdk.commands.CommandRegistry;
import io.github.itzispyder.pdk.commands.CustomCommand;
import io.github.itzispyder.pdk.commands.Permission;
import io.github.itzispyder.pdk.commands.completions.CompletionBuilder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandRegistry(value = "craft", permission = @Permission("ultrautils.craft"),playersOnly = true)
public class CraftingCommand implements CustomCommand {
    @Override
    public void dispatchCommand(CommandSender sender, Command command, String label, Args args) {
        Player p = (Player) sender;
        p.openWorkbench(p.getLocation(),true);
    }

    @Override
    public void dispatchCompletions(CompletionBuilder b, CommandSender sender) {

    }
}
