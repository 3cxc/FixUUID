package com.github.fixuuid;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

import static com.github.fixuuid.FixUUID.plugin;

public class ConfigManager {

    public static List<String> Player_Whitelist;

    public static String KickMessage;

    /**
     * 加载配置文件
     */
    public void loadConfig(){
        Player_Whitelist = plugin.getConfig().getStringList("Whitelist");
        //读取语言文件
        File file = new File(plugin.getDataFolder(), "message" + ".yml");
        FileConfiguration messageConfig = YamlConfiguration.loadConfiguration(file);
        KickMessage = messageConfig.getString("Kick_Message");
    }
}
