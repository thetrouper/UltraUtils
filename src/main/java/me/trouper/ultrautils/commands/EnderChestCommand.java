package me.trouper.ultrautils.commands;

import io.github.itzispyder.pdk.commands.Args;
import io.github.itzispyder.pdk.commands.CommandRegistry;
import io.github.itzispyder.pdk.commands.CustomCommand;
import io.github.itzispyder.pdk.commands.Permission;
import io.github.itzispyder.pdk.commands.completions.CompletionBuilder;
import me.trouper.ultrautils.functions.Text;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandRegistry(value = "enderchest",permission = @Permission("ultrautils.enderchest"),playersOnly = true)
public class EnderChestCommand implements CustomCommand {
    @Override
    public void dispatchCommand(CommandSender sender, Command command, String label, Args args) {
        Player user = (Player) sender;
        Player target = Bukkit.getPlayer(args.get(0).toString());
        if (target == null) target = user;
        if (user.equals(target)) {
            user.openInventory(user.getEnderChest());
        } else if (user.hasPermission("ultrautils.enderchest.others")) {
            user.openInventory(target.getEnderChest());
        } else {
            user.sendMessage(Text.prefix("You lack the permission to see other player's enderchest."));
        }
    }

    @Override
    public void dispatchCompletions(CompletionBuilder b, CommandSender sender) {

    }
}
