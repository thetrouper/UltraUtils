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

@CommandRegistry(value = "speed", permission = @Permission("ultrautils.speed"),printStackTrace = true)
public class SpeedCommand implements CustomCommand {
    @Override
    public void dispatchCommand(CommandSender sender, Command command, Args args) {
        String type = args.get(1).toString();
        float speed = args.get(0).toFloat();
        Player user = (Player) sender;
        Player target = Bukkit.getPlayer(args.get(2).toString());
        if (target == null && (sender instanceof Player)) target = (Player) sender;
        if (target == null) {
            sender.sendMessage(Text.prefix("Only players may execute that command"));
            return;
        }

        switch (type) {
            case "walk" -> {
                setWalkSpeed(sender,user,target,speed);
            }
            case "flight" -> {
                setFlySpeed(sender,user,target,speed);
            }
            default -> {
                if (target.isFlying()) {
                    setFlySpeed(sender,user,target,speed);
                    return;
                }
                setWalkSpeed(sender,user,target,speed);
            }
        }
    }

    @Override
    public void dispatchCompletions(CompletionBuilder b, CommandSender sender) {
        List<String> players = new ArrayList<>();
        for (Player player : ServerUtils.players()) {
            players.add(player.getName());
        }

        b.then(b.arg("[<int>]")
                .then(b.arg("walk","flight")
                        .then(b.arg(players))
                )
        );
    }
    public static void setFlySpeed(CommandSender sender, Player user, Player target, float speed) {
        if (!user.hasPermission("ultrautils.speed.flight")) {
            sender.sendMessage("You lack the permission to set walk speed.");
            return;
        }
        if (user.equals(target)) {
            target.setFlySpeed(speed);
            user.sendMessage(Text.prefix("Set your flight speed to &a%s&7.".formatted(speed)));
        } else if (user.hasPermission("ultrautils.speed.fly.others")) {
            target.setFlySpeed(speed);
            user.sendMessage(Text.prefix("Set the flight speed of &e%s&7 to &a%s&7.".formatted(target.getName(),speed)));
            target.sendMessage(Text.prefix("Your flight speed has been set to &a%s&7.".formatted(speed)));
        } else {
            user.sendMessage(Text.prefix("You lack the permission to set other player's flight speed."));
        }
    }

    public static void setWalkSpeed(CommandSender sender, Player user, Player target, float speed) {
        if (!user.hasPermission("ultrautils.speed.walk")) {
            sender.sendMessage("You lack the permission to set walk speed.");
            return;
        }
        if (user.equals(target)) {
            target.setWalkSpeed(speed);
            user.sendMessage(Text.prefix("Set your walk speed to &a%s&7.".formatted(speed)));
        } else if (user.hasPermission("ultrautils.speed.walk.others")) {
            target.setWalkSpeed(speed);
            user.sendMessage(Text.prefix("Set the walk speed of &e%s&7 to &a%s&7.".formatted(target.getName(),speed)));
            target.sendMessage(Text.prefix("Your walk speed has been set to &a%s&7.".formatted(speed)));
        } else {
            user.sendMessage(Text.prefix("You lack the permission to set other player's walk speed."));
        }
    }
}
