package me.trouper.ultrautils.commands;

import io.github.itzispyder.pdk.commands.Args;
import io.github.itzispyder.pdk.commands.CommandRegistry;
import io.github.itzispyder.pdk.commands.CustomCommand;
import io.github.itzispyder.pdk.commands.Permission;
import io.github.itzispyder.pdk.commands.completions.CompletionBuilder;
import me.trouper.ultrautils.UltraUtils;
import me.trouper.ultrautils.functions.ImageUtils;
import me.trouper.ultrautils.functions.Text;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.List;

@CommandRegistry(value = "ultrautils", permission = @Permission("ultrautils.admin"), printStackTrace = true)
public class UltraUtilsCommand implements CustomCommand {

    @Override
    public void dispatchCommand(CommandSender sender, Command command, String label,Args args) {
        Player p = (Player) sender;

        switch (args.get(0).toString()) {
            case "resetscoreboard" -> {
                p.sendMessage(Text.prefix("Starting vanilla scoreboard reset..."));
                ScoreboardManager manager = Bukkit.getScoreboardManager();
                if (manager == null) return;
                Scoreboard board = manager.getMainScoreboard();
                for (Objective objective : board.getObjectives()) {
                    p.sendMessage(Text.color("&7Removing objective &a%s&7.".formatted(objective.getName())));
                    objective.unregister();
                }
                for (Team team : board.getTeams()) {
                    p.sendMessage(Text.color("&7Removing team &a%s&7.".formatted(team.getName())));
                    team.unregister();
                }
                for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
                    p.sendMessage(Text.color("&7Removing entry &a%s&7.".formatted(offlinePlayer.getName())));
                    board.resetScores(offlinePlayer);
                }
                p.sendMessage(Text.prefix("Scoreboard reset complete!"));
            }
            case "image" -> {
                String url = args.get(1).toString();
                List<String> imageLines = ImageUtils.imageToList("https://crafatar.com/avatars/0e68b123-6df9-4201-a822-4ffae822d429?size=8");
                imageLines.set(2,imageLines.get(2) + " ");
                imageLines.set(3,imageLines.get(3) + " §bNice One");
                imageLines.set(4,imageLines.get(4) + " §fImage Printer");
                imageLines.set(5,imageLines.get(5) + " ");
                imageLines.set(7,imageLines.get(7) + " §8§m==========================");
                imageLines.set(0,imageLines.get(0) + " §8§m==========================");
                Bukkit.broadcastMessage(String.join("\n", imageLines));
            }
            case "toggle" -> {
                switch (args.get(1).toString()) {
                    case "debug" -> {
                        UltraUtils.config.debugMode = !UltraUtils.config.debugMode;
                        UltraUtils.config.save();
                    }
                }
            }
            default -> {
                sender.sendMessage(Component.text(Text.prefix(
                        "&bAuthor:&f obvWolf"
                )));
            }
        }
    }

    @Override
    public void dispatchCompletions(CompletionBuilder b, CommandSender sender) {
        b.then(b.arg("toggle")
                .then(b.arg("debug")))
                .then(b.arg("resetscoreboard"));
    }
}
