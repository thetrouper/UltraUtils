package me.trouper.ultrautils.data.config;

import io.github.itzispyder.pdk.utils.misc.JsonSerializable;

import java.io.File;

public class Config implements JsonSerializable<Config> {

    @Override
    public File getFile() {
        File file = new File("plugins/UltraUtils/main-config.json");
        file.getParentFile().mkdirs();
        return file;
    }

    public String prefix = "&9UltraCore> &7";
    public boolean debugMode = false;
    public Plugin plugin = new Plugin();

    public class Plugin {
        public String joinMessage = "&8[&2+&8] &a%s&7 has joined.";
        public String leaveMessage = "&8[&4-&8] &c%s&7 has left.";
    }
}
