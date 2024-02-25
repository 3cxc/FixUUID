package com.github.fixuuid.Packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.injector.GamePhase;
import com.comphenix.protocol.wrappers.WrappedGameProfile;

import static com.github.fixuuid.ConfigManager.*;
import static com.github.fixuuid.FixUUID.plugin;
import static com.github.fixuuid.FixUUID.pm;

/**
 * 数据包监听器
 */
public class LoginPacketListener {
    public void addListener(){
        pm.addPacketListener(new PacketAdapter(PacketAdapter.params()
                //设置开始
                .plugin(plugin)
                //监听来自客户端的数据包
                .clientSide()
                //设置本监听优先级为高
                .listenerPriority(ListenerPriority.HIGH)
                //监听登录数据包
                .gamePhase(GamePhase.LOGIN)
                //设置同步处理监听
                .optionSync()
                //不验证plugin.yml
                .options(ListenerOptions.SKIP_PLUGIN_VERIFIER)
                //监听登录数据包
                .types(PacketType.Login.Client.START)
                //设置结束
        ){
            @Override
            public void onPacketReceiving(PacketEvent event){ //当服务器收到客户端数据包
                //获取数据包及其类型
                PacketContainer packet = event.getPacket();
                if (event.getPacketType() == PacketType.Login.Client.START){
                    //获取登录数据包的玩家资料
                    WrappedGameProfile gameProfile = packet.getGameProfiles().getValues().get(0);
                    //玩家不在白名单，且白名单已开启就踢出
                    if (!Player_Whitelist.contains(gameProfile.getName()) & proxy_mode){
                        event.getPlayer().kickPlayer(KickMessage);
                    }
                }
            }
        });
    }
}
