package me.trouper.ultrautils;

import io.github.itzispyder.pdk.PDK;
import io.github.itzispyder.pdk.utils.misc.JsonSerializable;
import me.trouper.ultrautils.commands.EnderChestCommand;
import me.trouper.ultrautils.commands.UltraUtilsCommand;
import me.trouper.ultrautils.commands.admin.BroadcastCommand;
import me.trouper.ultrautils.commands.admin.MacroToolCommand;
import me.trouper.ultrautils.commands.admin.WorldCommand;
import me.trouper.ultrautils.commands.gamemode.*;
import me.trouper.ultrautils.commands.mobility.FlyCommand;
import me.trouper.ultrautils.commands.mobility.SpeedCommand;
import me.trouper.ultrautils.commands.workstations.*;
import me.trouper.ultrautils.data.config.Config;
import me.trouper.ultrautils.data.config.Storage;
import me.trouper.ultrautils.events.JoinLeaveEvent;
import me.trouper.ultrautils.events.MacroUseEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;

public final class UltraUtils extends JavaPlugin {

    private static UltraUtils instance;
    private static final File configFile = new File("plugins/UltraUtils/main-config.json");
    private static final File storagefile = new File("plugins/UltraUtils/storage.json");
    public static Config config = JsonSerializable.load(configFile, Config.class, new Config());
    public static Storage storage = JsonSerializable.load(storagefile, Storage.class, new Storage());
    public static final Logger log = Bukkit.getLogger();

    /**
     * Plugin startup logic
     */
    @Override
    public void onEnable() {

        log.info("\n]======------ Pre-load started ------======[");
        PDK.init(this);
        instance = this;

        loadConfig();

        startup();
    }

    public void startup() {
        log.info("\n]======----- Loading UltraUtils-----======[");

        // Plugin startup logic
        log.info("Starting Up! (%s)...".formatted(getDescription().getVersion()));


        new UltraUtilsCommand().register();
        new GamemodeCommand().register();
//        new GMACommand().register();
//        new GMCCommand().register();
//        new GMSPCommand().register();
//        new GMSCommand().register();
        new AnvilCommand().register();
        new CartographyCommand().register();
        new CraftingCommand().register();
        new GrindstoneCommand().register();
        new LoomCommand().register();
        new SmithingCommand().register();
        new StonecutterCommand().register();
        new FlyCommand().register();
        new SpeedCommand().register();
        new WorldCommand().register();
        new BroadcastCommand().register();
        new MacroToolCommand().register();
        new EnderChestCommand().register();

        // Events
        new JoinLeaveEvent().register();
        new MacroUseEvent().register();

        log.info("""
                Finished, UltraUtils has loaded.
                """);
    }

    public void loadConfig() {
        log.info("Loading Config...");

        // Init
        config = JsonSerializable.load(configFile, Config.class,new Config());
        storage = JsonSerializable.load(storagefile, Storage.class,new Storage());

        // Save
        config.save();
        storage.save();
    }


    /**
     * Plugin shutdown logic
     */
    @Override
    public void onDisable() {
        // Plugin shutdown logic
        log.info("UltraUtils has disabled! (%s)".formatted(getDescription().getVersion()));
    }

    public static UltraUtils getInstance() {
        return instance;
    }

}
