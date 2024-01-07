package com.github.fixuuid;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.github.fixuuid.Packets.LoginPacketListener;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import com.github.toolslib.network.HttpClient;

import java.util.ArrayList;

import static com.github.fixuuid.ConfigManager.KickMessage;
import static com.github.fixuuid.ConfigManager.Player_Whitelist;

final public class FixUUID extends JavaPlugin{
    public static JavaPlugin plugin;

    public static ProtocolManager pm;

    @Override
    public void onLoad(){
        //尝试生成主配置文件和语言文件
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        saveResource("message.yml", false);
        new ConfigManager().loadConfig();
    }


    @Override
    public void onEnable() {
        getLogger().info(ChatColor.GREEN + "插件已加载,作者:3cxc");
        plugin = FixUUID.getPlugin(FixUUID.class);
        //尝试获取ProtocolLib的API
        pm = ProtocolLibrary.getProtocolManager();
        //注册命令
        RegisterCommands.registerCommands();
        //注册监听器
        new LoginPacketListener().addListener();
        //异步执行，每300tick执行一次，每次执行检查一次在线玩家是否都有白名单
        //如果发现有在线的玩家没有白名单的，且KickOnlinePlayer为true，则踢出该玩家
        new BukkitRunnable() {
            @Override
            public void run() {
                ArrayList<Player> list = new ArrayList<>(getServer().getOnlinePlayers());
                for (Player player : list) {
                    if (!Player_Whitelist.contains(player.getName())) {
                        if (plugin.getConfig().getBoolean("KickOnlinePlayer", true)) {//只有时才踢出
                            player.kickPlayer(KickMessage);
                        }
                    }
                }
            }
        }.runTaskTimerAsynchronously(this, 50, 300);
    }

    @Override
    public void onDisable(){
        getConfig().set("Whitelist",Player_Whitelist);
        saveConfig();
        getLogger().info("插件数据已保存,插件已关闭");
    }
}