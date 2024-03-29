package com.github.fixuuid.Whitelist;

import com.github.fixuuid.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static com.github.fixuuid.ConfigManager.Player_Whitelist;
import static com.github.fixuuid.FixUUID.plugin;

public class Command implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String s, String[] args) {
        if (command.getName().equalsIgnoreCase("whitelist")){
            //仅使用/whitelist时的提示
            if (args.length == 0){
                sender.sendMessage(ChatColor.GRAY+"/whitelist add|remove|reload|list");
            }

            if (args.length == 1){
                if (args[0].equalsIgnoreCase("reload")){//重载白名单
                    if (checkPlayerPermission(sender,"fixuuid.whitelist.reload",ChatColor.RED+"您没有权限执行此命令！"))return false;
                    //重载配置文件
                    plugin.reloadConfig();
                    new ConfigManager().loadConfig();
                    sender.sendMessage(ChatColor.WHITE+"已重新读取配置文件！");
                }
                if (args[0].equalsIgnoreCase("list")){//白名单玩家列表
                    if (checkPlayerPermission(sender,"fixuuid.whitelist.list",ChatColor.RED+"您没有权限执行此命令！"))return false;
                    if (Player_Whitelist.isEmpty()){
                        sender.sendMessage(ChatColor.WHITE+"白名单内没有玩家");
                        return false;
                    }
                    sender.sendMessage(ChatColor.WHITE+"白名单内共有"+Player_Whitelist.size()+"名玩家: "+Player_Whitelist);
                }
                if (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("remove")){
                    sender.sendMessage(ChatColor.RED+"请指定一个有效的玩家名！");
                }
            }

            if (args.length == 2){
                if (args[0].equalsIgnoreCase("add")){//添加一个玩家到白名单
                    if (checkPlayerPermission(sender,"fixuuid.whitelist.add",ChatColor.RED+"您没有权限执行此命令！"))return false;
                    if (Player_Whitelist.contains(args[1])){
                        sender.sendMessage(ChatColor.RED+"此玩家已在白名单内");
                    }else {
                        Player_Whitelist.add(args[1]);
                        ConfigManager.saveConfig("Whitelist.list",Player_Whitelist);
                        sender.sendMessage(ChatColor.WHITE+"已添加玩家"+args[1]+"到白名单内");
                    }
                }
                if (args[0].equalsIgnoreCase("remove")){//删除一个玩家的白名单
                    if (checkPlayerPermission(sender,"fixuuid.whitelist.remove",ChatColor.RED+"您没有权限执行此命令！"))return false;
                    if (!Player_Whitelist.contains(args[1])){
                        sender.sendMessage(ChatColor.RED+"此玩家不在白名单内");
                    }else {
                        Player_Whitelist.remove(args[1]);
                        ConfigManager.saveConfig("Whitelist.list",Player_Whitelist);
                        sender.sendMessage(ChatColor.WHITE+"已从白名单内删除玩家"+args[1]);
                    }
                }
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String s, String[] args) {
        //玩家权限检查
        if (checkPlayerPermission(sender,"fixuuid.admin",null))return null;
        if (args.length == 1){//返回命令帮助列表
            List<String> list = new ArrayList<>();
            list.add("on");
            list.add("off");
            list.add("add");
            list.add("remove");
            list.add("reload");
            list.add("list");
            return list;
        }
        if (args.length == 2){
            //尝试返回列表
            if (args[0].equalsIgnoreCase("add")){//如果子命令为add
                return getPlayerList();//返回在线玩家列表(原版如此)
            }else if (args[0].equalsIgnoreCase("remove")){
                return Player_Whitelist;
            }
        }
        return null;
    }

    /**
     * 返回在线的且没有白名单的玩家列表
     * @return 玩家列表
     */
    private static List<String> getPlayerList() {
        ArrayList<Player> OnlinePlayerList = new ArrayList<>(Bukkit.getOnlinePlayers());
        List<String> list = new ArrayList<>();
        for (Player player : OnlinePlayerList) {
            if (!Player_Whitelist.contains(player.getName())) {//检测玩家是否在白名单内，不在则把该玩家添加到要返回的List中
                list.add(player.getName());
            }
        }
        return list;
    }

    /**
     * 检测玩家是否有权限执行一个命令
     * <p>
     * 如果没有权限则发送一个无权限信息并返回true(可根据返回的boolean做出处理)
     * @return 如果该玩家没有权限则返回true
     */
    private static boolean checkPlayerPermission(CommandSender sender,String permission,String noPermissionMessage){
        if (sender instanceof Player){
            Player player = (Player) sender;
            if (!player.hasPermission(permission)){
                if (noPermissionMessage != null){//如果指定了无权限时发送的信息
                    player.sendMessage(noPermissionMessage);
                }
                return true;
            }
        }
        return false;
    }
}
