package com.nouuidwhitelist;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import static com.nouuidwhitelist.NoUUIDWhiteList.*;

public class Event implements Listener {//处理玩家登录数据

    @EventHandler
    public void PlayerLoginEvent(AsyncPlayerPreLoginEvent Aevent){
        boolean bl = false;
        for (int i = 0 ; i < Whitelist.size() ; i++){
            if (Whitelist.get(i).equals(Aevent.getName())){
                bl = true;
            }
        }
        if (!bl){//检查是否在白名单内
            if (config.getConfig().getBoolean("Enable",true)){//如果不在白名单内且白名单已开启
                Aevent.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST,KickMessage);
            }
        }
    }
}
