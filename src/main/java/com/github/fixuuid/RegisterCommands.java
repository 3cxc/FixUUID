package com.github.fixuuid;

import com.github.fixuuid.Whitelist.WhitelistCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;

import static com.github.fixuuid.FixUUID.plugin;

public class RegisterCommands {
    public static void registerCommands(){
        //尝试注册命令
        if (plugin.getConfig().getBoolean("Whitelist.proxy_mode")){
            whitelistCommand();
        }
    }

    /**
     * 注册/whitelist命令
     */
    private static void whitelistCommand(){
        PluginCommand command = plugin.getCommand("whitelist");
        if (command != null){
            command.setExecutor(new WhitelistCommand());
            command.setTabCompleter(new WhitelistCommand());
        }else {
            plugin.getLogger().warning(ChatColor.RED+"命令注册失败，将禁用本插件！");
            plugin.getPluginLoader().disablePlugin(plugin);
        }
    }
}
