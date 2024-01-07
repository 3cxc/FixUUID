package com.github.fixuuid.Packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.injector.GamePhase;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;

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
                .serverSide()
                //设置本监听优先级为高
                .listenerPriority(ListenerPriority.HIGH)
                //监听登录数据包
                .gamePhase(GamePhase.LOGIN)
                //设置异步处理监听
                .optionAsync()
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
                PacketType type = event.getPacketType();
                //检查是否为客户端发送的登录开始数据包
                if (type.isClient() && type == PacketType.Login.Client.START){
                    //获取登录数据包的玩家资料
                    WrappedGameProfile gameProfile = packet.getGameProfiles().getValues().get(0);
                    //踢出
                    event.setCancelled(true);
                    PacketContainer disconnectEvent = PacketContainer.fromPacket(PacketType.Login.Server.DISCONNECT);
                    WrappedChatComponent chatComponent = WrappedChatComponent.fromText("测试\n还是测试");
                    disconnectEvent.getChatComponents().getValues().set(0,chatComponent);
                    event.schedule(new ScheduledPacket(disconnectEvent,event.getPlayer(),true));
                }
            }
        });
    }
}
