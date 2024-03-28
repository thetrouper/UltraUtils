package me.trouper.ultrautils.commands.gamemode;

import functions.Text;
import io.github.itzispyder.pdk.commands.Args;
import io.github.itzispyder.pdk.commands.CommandRegistry;
import io.github.itzispyder.pdk.commands.CustomCommand;
import io.github.itzispyder.pdk.commands.Permission;
import io.github.itzispyder.pdk.commands.completions.CompletionBuilder;
import io.github.itzispyder.pdk.utils.ServerUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@CommandRegistry(value = "gmc",permission = @Permission("ultrautils.gamemode.creative"))
public class GMCCommand implements CustomCommand {

    @Override
    public void dispatchCommand(CommandSender commandSender, Args args) {
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
    public void dispatchCompletions(CompletionBuilder b) {
        List<String> players = new ArrayList<>();
        for (Player player : ServerUtils.players()) {
            players.add(player.getName());
        }
        b.then(b.arg(players));
    }
}
