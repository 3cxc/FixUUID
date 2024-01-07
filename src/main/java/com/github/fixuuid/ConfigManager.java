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
        Player_Whitelist = plugin.getConfig().getStringList("Whitelist.list");
        //读取语言文件
        File file = new File(plugin.getDataFolder(), "message" + ".yml");
        FileConfiguration messageConfig = YamlConfiguration.loadConfiguration(file);
        KickMessage = messageConfig.getString("Kick_Message");
    }

    /**
     * 更改并保存一个配置文件项
     * @param path 配置文件项
     * @param del 新值
     */
    public static void saveConfig(String path,List<String> del){
        plugin.getConfig().set(path,del);
        plugin.saveConfig();
    }
}
