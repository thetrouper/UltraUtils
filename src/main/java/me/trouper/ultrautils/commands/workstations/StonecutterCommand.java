package me.trouper.ultrautils.commands.workstations;

import io.github.itzispyder.pdk.commands.Args;
import io.github.itzispyder.pdk.commands.CommandRegistry;
import io.github.itzispyder.pdk.commands.CustomCommand;
import io.github.itzispyder.pdk.commands.Permission;
import io.github.itzispyder.pdk.commands.completions.CompletionBuilder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandRegistry(value = "stonecutter", permission = @Permission("ultrautils.stonecut"),playersOnly = true)
public class StonecutterCommand implements CustomCommand {
    @Override
    public void dispatchCommand(CommandSender sender, Command command, String label, Args args) {
        Player p = (Player) sender;
        p.openStonecutter(p.getLocation(),true);
    }

    @Override
    public void dispatchCompletions(CompletionBuilder b, CommandSender sender) {

    }
}
