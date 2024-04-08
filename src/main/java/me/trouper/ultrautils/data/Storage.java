package me.trouper.ultrautils.data.config;

import io.github.itzispyder.pdk.utils.misc.JsonSerializable;
import me.trouper.ultrautils.data.IpInfo;

import java.io.File;
import java.util.*;

public class Storage implements JsonSerializable<Storage> {

    @Override
    public File getFile() {
        File file = new File("plugins/UltraUtils/storage.json");
        file.getParentFile().mkdirs();
        return file;
    }

    public List<UUID> whitelist = new ArrayList<>();
    public Map<String, IpInfo> ipInfoLog = new HashMap<>();
}
