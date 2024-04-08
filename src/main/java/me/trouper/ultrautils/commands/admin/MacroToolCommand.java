package me.trouper.ultrautils.commands.admin;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.itzispyder.pdk.commands.Args;
import io.github.itzispyder.pdk.commands.CommandRegistry;
import io.github.itzispyder.pdk.commands.CustomCommand;
import io.github.itzispyder.pdk.commands.Permission;
import io.github.itzispyder.pdk.commands.completions.CompletionBuilder;
import me.trouper.ultrautils.UltraUtils;
import me.trouper.ultrautils.functions.Text;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

@CommandRegistry(value = "macrotool",permission = @Permission("ultrautils.macrotool"),playersOnly = true,printStackTrace = true)
public class MacroToolCommand implements CustomCommand {
    @Override
    public void dispatchCommand(CommandSender sender, Command cmd, Args args) {
        Player p = (Player) sender;
        String command = args.getAll(1).toString();
        ItemStack item = p.getInventory().getItemInMainHand();
        if (item.isEmpty()) {
            p.sendMessage(Text.prefix("You must hold out an item!"));
            return;
        }
        switch (args.get(0).toString()) {
            case "add" -> {
                p.sendMessage(Text.prefix("Adding the macro &e%s&7 to your item.".formatted(command)));
                addMacro(item,command);
            }
            case  "remove" -> {
                p.sendMessage(Text.prefix("Removing the macro &e%s&7 from your item.".formatted(command)));
                removeMacro(item,command);
            }
            case "clear" -> {
                p.sendMessage(Text.prefix("Cleared the macros on your item."));
                clearMacros(item);
            }
            case "get" -> {
                p.sendMessage(Text.prefix("Macros: &b%s".formatted(getMacros(item).toString())));
            }
        }


    }

    @Override
    public void dispatchCompletions(CompletionBuilder b, CommandSender sender) {
        b.then(b.arg("add","clear","get"))
                .then(b.arg("remove")
                        .then(b.arg(getMacros(((Player) sender).getInventory().getItemInMainHand()))));
    }

    private static final NamespacedKey MACROS_KEY = new NamespacedKey(UltraUtils.getInstance(), "macros");

    public void setMacros(ItemStack item, List<String> commands) {
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();

        JsonObject obj = new JsonObject();
        JsonArray macroList = new JsonArray();

        for (String macro : commands) {
            macroList.add(macro);
        }

        obj.add("macro-commands", macroList);
        data.set(MACROS_KEY,PersistentDataType.STRING, obj.toString());
        item.setItemMeta(meta);
    }

    public void addMacro(ItemStack item, String command) {
        List<String> currentCommands = getMacros(item);
        currentCommands.add(command);
        setMacros(item,currentCommands);
    }

    public void removeMacro(ItemStack item, String command) {
        List<String> currentCommands = getMacros(item);
        if (!currentCommands.remove(command)) return;
        setMacros(item,currentCommands);
    }

    public void clearMacros(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        data.remove(MACROS_KEY);
        item.setItemMeta(meta);
    }

    public List<String> getMacros(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        List<String> commands = new ArrayList<>();

        String json = data.get(MACROS_KEY, PersistentDataType.STRING);

        if (json == null) return new ArrayList<>();

        JsonObject loaded = JsonParser.parseString(json).getAsJsonObject();

        for (JsonElement element : loaded.get("macro-commands").getAsJsonArray()) {
            commands.add(element.getAsString());
        }

        return commands;
    }



}
