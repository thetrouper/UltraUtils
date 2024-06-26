package me.trouper.ultrautils.commands.workstations;

import io.github.itzispyder.pdk.commands.Args;
import io.github.itzispyder.pdk.commands.CommandRegistry;
import io.github.itzispyder.pdk.commands.CustomCommand;
import io.github.itzispyder.pdk.commands.Permission;
import io.github.itzispyder.pdk.commands.completions.CompletionBuilder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandRegistry(value = "smith", permission = @Permission("ultrautils.smith"),playersOnly = true)
public class SmithingCommand implements CustomCommand {
    @Override
    public void dispatchCommand(CommandSender sender, Command command, String label, Args args) {
        Player p = (Player) sender;
        p.openSmithingTable(p.getLocation(),true);
    }

    @Override
    public void dispatchCompletions(CompletionBuilder b, CommandSender sender) {

    }
}
