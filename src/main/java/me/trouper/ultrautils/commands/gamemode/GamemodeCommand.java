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

import javax.annotation.Nullable;

@CommandRegistry(value = "gamemode", permission = @Permission("ultrautils.gamemode"),printStackTrace = true)
public class GamemodeCommand implements CustomCommand {

    @Override
    public void dispatchCommand(CommandSender sender, Command command, String label, Args args) {
        Player target = Bukkit.getPlayer(args.get(1).toString());
        if (target == null && (sender instanceof Player)) target = (Player) sender;
        if (target == null) {
            sender.sendMessage(Text.prefix("Only players may execute that command"));
            return;
        }
        target.sendMessage("Target: %s, Sender: %s, Command: %s, Label: %s, Args: %s".formatted(target,sender,command,label,args));
        switch (label) {
            case "gma" -> {
                setGameMode(sender,target,GameMode.ADVENTURE, "ultrautils.gamemode.adventure");
                return;
            }
            case "gmc" -> {
                setGameMode(sender,target,GameMode.CREATIVE,"ultrautils.gamemode.creative");
                return;
            }
            case "gms" -> {
                setGameMode(sender,target,GameMode.SURVIVAL,"ultrautils.gamemode.survival");
                return;
            }
            case "gmsp" -> {
                setGameMode(sender,target,GameMode.SPECTATOR,"ultrautils.gamemode.spectator");
                return;
            }
        }
        switch (args.get(0).toString()) {
            case "a","adventure","adv","gma" -> {
                setGameMode(sender,target,GameMode.ADVENTURE, "ultrautils.gamemode.adventure");
                return;
            }
            case "c","creative","cre","gmc" -> {
                setGameMode(sender,target,GameMode.CREATIVE,"ultrautils.gamemode.creative");
                return;
            }
            case "s","survival","sur","gms" -> {
                setGameMode(sender,target,GameMode.SURVIVAL,"ultrautils.gamemode.survival");
                return;
            }
            case "sp","spectator","spec","gmsp" -> {
                setGameMode(sender,target,GameMode.SPECTATOR,"ultrautils.gamemode.spectator");
                return;
            }
        }
    }

    @Override
    public void dispatchCompletions(CompletionBuilder b, CommandSender sender) {
        b.then(b.arg("adventure","creative","survival","spectator")
                .then(b.arg(Bukkit.getOnlinePlayers(),Player::getName))
        );
    }

    public static void setGameMode(CommandSender user, @Nullable Player target, GameMode gm, String permission) {
        if (!user.hasPermission(permission)) {
            user.sendMessage(Text.prefix("You lack the permission to swith gamemodes to %c%s&7."));
            return;
        }

        if (target == null && user instanceof Player) target = (Player) user;
        if (target == null) {
            user.sendMessage(Text.prefix("Only players can execute self-effecting commands."));
            return;
        }

        if (user.equals(target)) {
            target.setGameMode(gm);
            user.sendMessage(Text.prefix("Set your gamemode to &a%s&7.".formatted(target.getGameMode().toString().toLowerCase())));
        } else if (user.hasPermission("ultrautils.gamemode.others")) {
            target.setGameMode(gm);
            user.sendMessage(Text.prefix("Set the gamemode of &e%s&7 to &a%s&7.".formatted(target.getName(),target.getGameMode().toString().toLowerCase())));
            target.sendMessage(Text.prefix("Your gamemode has been set to &a%s&7.".formatted(target.getGameMode().toString().toLowerCase())));
        } else {
            user.sendMessage(Text.prefix("You lack the permission to set other player's gamemode."));
        }
     }
}
