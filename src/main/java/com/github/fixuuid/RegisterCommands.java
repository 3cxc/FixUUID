package com.github.fixuuid;

import com.github.fixuuid.Whitelist.Command;
import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;

import static com.github.fixuuid.FixUUID.plugin;

public class RegisterCommands {
    public static void registerCommands(){
        //尝试注册/whitelist命令
        whitelistCommand();
    }

    private static void whitelistCommand(){
        PluginCommand command = plugin.getCommand("whitelist");
        if (command != null){
            command.setExecutor(new Command());
            command.setTabCompleter(new Command());
        }else {
            plugin.getLogger().warning(ChatColor.RED+"命令注册失败，为了安全将禁用本插件！");
            plugin.getPluginLoader().disablePlugin(plugin);
        }
    }
}
