package me.trouper.ultrautils.commands.admin;

import io.github.itzispyder.pdk.commands.Args;
import io.github.itzispyder.pdk.commands.CommandRegistry;
import io.github.itzispyder.pdk.commands.CustomCommand;
import io.github.itzispyder.pdk.commands.Permission;
import io.github.itzispyder.pdk.commands.completions.CompletionBuilder;
import me.trouper.ultrautils.UltraUtils;
import me.trouper.ultrautils.functions.Text;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
@CommandRegistry(value = "broadcast", permission = @Permission("ultrautils.broadcast"))
public class BroadcastCommand implements CustomCommand {

    @Override
    public void dispatchCommand(CommandSender commandSender, Command command, String label, Args args) {
        Bukkit.getServer().broadcast(Component.text(Text.color(UltraUtils.config.broadcastPrefix + args.getAll().toString())));
    }

    @Override
    public void dispatchCompletions(CompletionBuilder completionBuilder, CommandSender sender) {

    }
}
