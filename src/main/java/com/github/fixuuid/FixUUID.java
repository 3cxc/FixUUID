package com.github.fixuuid;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.github.fixuuid.Packets.LoginPacketListener;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.ArrayList;

import static com.github.fixuuid.ConfigManager.*;

final public class FixUUID extends JavaPlugin{
    public static JavaPlugin plugin;

    //ProtocolLib API访问
    public static ProtocolManager pm;

    //无权限提示
    public static String NoPermissionMessage = ChatColor.RED+"您没有权限执行此命令";

    @Override
    public void onLoad(){
        //尝试生成主配置文件和语言文件，如果已经存在则直接加载
        plugin = FixUUID.getPlugin(FixUUID.class);
        if (!new File(plugin.getDataFolder(), "config.yml").exists()){
            getConfig().options().copyDefaults();
            saveDefaultConfig();
        }
        if (!new File(plugin.getDataFolder(), "message.yml").exists()){
            saveResource("message.yml", false);
        }
        //加载配置文件
        new ConfigManager().loadConfig();
    }


    @Override
    public void onEnable() {
        getLogger().info(ChatColor.GREEN + "插件已加载,作者:3cxc");
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
                        if (proxy_mode) {
                            player.kickPlayer(KickMessage);
                        }
                    }
                }
            }
        }.runTaskTimerAsynchronously(this, 50, 300);
    }

    @Override
    public void onDisable(){
        saveConfig();
        getLogger().info(ChatColor.GREEN+"插件数据已保存,插件已关闭");
    }
}