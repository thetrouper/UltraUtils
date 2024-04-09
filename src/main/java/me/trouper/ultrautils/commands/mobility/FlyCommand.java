package me.trouper.ultrautils.commands.mobility;

import io.github.itzispyder.pdk.commands.Args;
import io.github.itzispyder.pdk.commands.CommandRegistry;
import io.github.itzispyder.pdk.commands.CustomCommand;
import io.github.itzispyder.pdk.commands.Permission;
import io.github.itzispyder.pdk.commands.completions.CompletionBuilder;
import io.github.itzispyder.pdk.utils.ServerUtils;
import me.trouper.ultrautils.functions.Text;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@CommandRegistry(value = "fly", permission = @Permission("ultrautils.flight"),printStackTrace = true)
public class FlyCommand implements CustomCommand {
    @Override
    public void dispatchCommand(CommandSender sender, Command command, String label, Args args) {
        Player user = (Player) sender;
        Player target = Bukkit.getPlayer(args.get(1).toString());
        if (target == null && (sender instanceof Player)) target = (Player) sender;
        if (target == null) {
            sender.sendMessage(Text.prefix("Only players may execute that command"));
            return;
        }

        boolean current = target.getAllowFlight();
        if (user.equals(target)) {
            target.setAllowFlight(!current);
            sender.sendMessage(Text.prefix("Flight &a%s&7.".formatted(!current ? "&aenabled" : "&cdisabled")));
        } else if (sender.hasPermission("ultrautils.flight.others")) {
            target.setAllowFlight(!current);
            sender.sendMessage(Text.prefix("&a%s&7 the flight of &e%s&7.".formatted(!current ? "&aenabled" : "&cdisabled",target.getName())));
            target.sendMessage(Text.prefix("Your flight has been &a%s&7.".formatted(!current ? "&aenabled" : "&cdisabled")));
        } else {
            sender.sendMessage(Text.prefix("You lack the permission to set other player's flight status."));
        }

    }

    @Override
    public void dispatchCompletions(CompletionBuilder b, CommandSender sender) {
        List<String> players = new ArrayList<>();
        for (Player player : ServerUtils.players()) {
            players.add(player.getName());
        }
        b.then(b.arg(players));
    }
}
