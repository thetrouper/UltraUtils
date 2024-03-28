package me.trouper.ultrautils.commands;

import io.github.itzispyder.pdk.commands.Args;
import io.github.itzispyder.pdk.commands.CommandRegistry;
import io.github.itzispyder.pdk.commands.CustomCommand;
import io.github.itzispyder.pdk.commands.Permission;
import io.github.itzispyder.pdk.commands.completions.CompletionBuilder;
import me.trouper.ultrautils.UltraUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandRegistry(value = "ultrautils", permission = @Permission("ultrautils.admin"), printStackTrace = true)
public class UltraUtilsCommand implements CustomCommand {

    @Override
    public void dispatchCommand(CommandSender sender, Args args) {
        Player p = (Player) sender;

        switch (args.get(0).toString()) {
            case "toggle" -> {
                switch (args.get(1).toString()) {
                    case "debug" -> {
                        UltraUtils.config.debugMode = !UltraUtils.config.debugMode;
                        UltraUtils.config.save();
                    }
                }
            }
        }
    }

    @Override
    public void dispatchCompletions(CompletionBuilder b) {
        b.then(b.arg("toggle")
                .then(b.arg("debug"))
        );
    }
}
