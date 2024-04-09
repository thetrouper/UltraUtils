package me.trouper.ultrautils.commands.gamemode;

import io.github.itzispyder.pdk.commands.Args;
import io.github.itzispyder.pdk.commands.CommandRegistry;
import io.github.itzispyder.pdk.commands.CustomCommand;
import io.github.itzispyder.pdk.commands.Permission;
import io.github.itzispyder.pdk.commands.completions.CompletionBuilder;
import me.trouper.ultrautils.functions.Text;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandRegistry(value = "gmc",permission = @Permission("ultrautils.gamemode.creative"),printStackTrace = true)
public class GMCCommand implements CustomCommand {

    @Override
    public void dispatchCommand(CommandSender commandSender, Command command, String label, Args args) {
        Player target = Bukkit.getPlayer(args.get(0).toString());
        if (target == null && (commandSender instanceof Player)) target = (Player) commandSender;
        if (target == null) {
            commandSender.sendMessage(Text.prefix("Only players may execute that command"));
            return;
        }
        if (!commandSender.hasPermission("ultrautils.gamemode")) {
            commandSender.sendMessage(Text.prefix("You do not have the root permission to change gamemode."));
            return;
        }
        GamemodeCommand.setGameMode(commandSender,target,GameMode.CREATIVE,"ultrautils.gamemode.creative");}

    @Override
    public void dispatchCompletions(CompletionBuilder b, CommandSender sender) {
        b.arg(Bukkit.getOnlinePlayers(),Player::getName);
    }
}
