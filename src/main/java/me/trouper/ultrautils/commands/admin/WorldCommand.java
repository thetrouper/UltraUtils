package me.trouper.ultrautils.commands.admin;

import io.github.itzispyder.pdk.commands.Args;
import io.github.itzispyder.pdk.commands.CommandRegistry;
import io.github.itzispyder.pdk.commands.CustomCommand;
import io.github.itzispyder.pdk.commands.Permission;
import io.github.itzispyder.pdk.commands.completions.CompletionBuilder;
import io.github.itzispyder.pdk.utils.ServerUtils;
import me.trouper.ultrautils.functions.Text;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@CommandRegistry(value = "world",permission = @Permission("ultrautils.world"),printStackTrace = true)
public class WorldCommand implements CustomCommand {

    @Override
    public void dispatchCommand(CommandSender sender, Command command, Args args) {
        String worldName = args.get(0).toString();
        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            sender.sendMessage(Text.prefix("That world does not exist!"));
            return;
        }
        Player target = Bukkit.getPlayer(args.get(1).toString());
        if (target == null && !(sender instanceof Player)) {
            sender.sendMessage(Text.prefix("You must be a player, or specify a target user to execute this command!"));
            return;
        }
        if (target == null) target = (Player) sender;
        Location where = world.getSpawnLocation();
        if (where == null) where = new Location(world,0,64,0);


        if (target.equals((Player) sender)) {
            target.teleport(where);
            sender.sendMessage(Text.prefix("Successfully moved you to &e%s&7.".formatted(world.getName())));

        } else if (sender.hasPermission("ultrautils.world.others")) {
            target.teleport(where);
            target.sendMessage(Text.prefix("You have been moved to &a%s&7.".formatted(world.getName())));
            sender.sendMessage(Text.prefix("Moved &a%s&7 to &e%s&7.".formatted(target.getName(),world.getName())));
        } else {
            sender.sendMessage(Text.prefix("You do not have permission to move other players."));
        }

    }

    @Override
    public void dispatchCompletions(CompletionBuilder b, CommandSender sender) {
        List<String> worlds = new ArrayList<>();
        for (World world : Bukkit.getWorlds()) {
            worlds.add(world.getName());
        }
        List<String> players = new ArrayList<>();
        for (Player player : ServerUtils.players()) {
            players.add(player.getName());
        }
        b.then(b.arg(worlds)
                .then(b.arg(players)));
    }
}
