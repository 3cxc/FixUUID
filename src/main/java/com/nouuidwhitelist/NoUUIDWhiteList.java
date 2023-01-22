package com.nouuidwhitelist;

import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class NoUUIDWhiteList extends JavaPlugin {

    public static Plugin config;

    public static List<String> Whitelist;

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        getLogger().info(ChatColor.GREEN+"插件已加载,作者:3cxc");
        config = NoUUIDWhiteList.getPlugin(NoUUIDWhiteList.class);
        Whitelist = config.getConfig().getStringList("Whitelist");
        CommandRegister();//注册命令
        getServer().getPluginManager().registerEvents(new Event(),this);//注册Event
    }

    public void CommandRegister(){//命令注册
        PluginCommand command = getCommand("whitelist");
        if (command != null){
            command.setExecutor(new Commands());
            command.setTabCompleter(new Commands());
        }
        if (command == null){//如果命令注册失败
            getLogger().info(ChatColor.RED+"[ERROR]命令注册失败，为了安全将禁用本插件！");
            config.getPluginLoader().disablePlugin(config);//为了方便直接用了config
        }
    }

    @Override
    public void onDisable(){
        config.getConfig().set("Whitelist",Whitelist);
        config.saveConfig();
        config.reloadConfig();
        getLogger().info("插件数据已保存,插件已关闭");
    }
}
