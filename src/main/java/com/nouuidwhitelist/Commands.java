package com.nouuidwhitelist;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.nouuidwhitelist.NoUUIDWhiteList.*;

public class Commands implements CommandExecutor, TabExecutor{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("whitelist")){//命令
            if (args.length == 0){
                if (sender instanceof Player){
                    Player player = (Player) sender;
                    if (player.hasPermission("whitelist.help")){
                        player.sendMessage(ChatColor.GRAY+"/whitelist on|off|add|remove|reload|list");
                    }else {
                        player.sendMessage(ChatColor.RED+"您没有权限运行任何子命令.");
                    }
                }else {
                    System.out.println(ChatColor.GRAY+"whitelist on|off|add|remove|reload|list");
                }
            }
            if (args.length == 1){
                if (args[0].equalsIgnoreCase("reload")){//reload指令
                    if (sender instanceof Player){
                        Player player = (Player) sender;
                        if (player.hasPermission("whitelist.reload")){//检查权限
                            config.getConfig().set("Whitelist",Whitelist);
                            config.saveConfig();
                            config.reloadConfig();
                            File messgae = new File(NoUUIDWhiteList.getPlugin(NoUUIDWhiteList.class).getDataFolder(),"message"+".yml");
                            MessageConfig = YamlConfiguration.loadConfiguration(messgae);//读取语言文件
                            KickMessage = MessageConfig.getString("Kick_Message");
                            player.sendMessage(ChatColor.WHITE+"已重新读取白名单");
                        }else {
                            player.sendMessage(ChatColor.RED+"您没有权限执行此命令！");
                        }
                    }else {//控制台
                        config.getConfig().set("Whitelist",Whitelist);
                        config.saveConfig();
                        config.reloadConfig();
                        File messgae = new File(NoUUIDWhiteList.getPlugin(NoUUIDWhiteList.class).getDataFolder(),"message"+".yml");
                        MessageConfig = YamlConfiguration.loadConfiguration(messgae);//读取语言文件
                        KickMessage = MessageConfig.getString("Kick_Message");
                        System.out.println(ChatColor.WHITE+"已重新读取白名单");
                    }
                }
                if (args[0].equalsIgnoreCase("on")){//开启白名单
                    if (sender instanceof Player){
                        Player player = (Player) sender;
                        if (player.hasPermission("whitelist.on")){//检查权限
                            if (config.getConfig().getBoolean("Enable",true)){
                                player.sendMessage(ChatColor.RED+"白名单原已开启");
                            }else {
                                config.getConfig().set("Enable",true);
                                config.saveConfig();
                                config.reloadConfig();
                                player.sendMessage(ChatColor.WHITE+"白名单已开启");
                            }
                        }else {
                            player.sendMessage(ChatColor.RED+"您没有权限执行此命令！");
                        }
                    }else {
                        if (config.getConfig().getBoolean("Enable",true)){
                            System.out.println(ChatColor.RED+"白名单原已开启");
                        }else {
                            config.getConfig().set("Enable",true);
                            config.saveConfig();
                            config.reloadConfig();
                            System.out.println(ChatColor.WHITE+"白名单已开启");
                        }
                    }
                }
                if (args[0].equalsIgnoreCase("off")){
                    if (sender instanceof Player){
                        Player player = (Player) sender;
                        if (player.hasPermission("whitelist.off")){//检查权限
                            if (config.getConfig().getBoolean("Enable",true)){
                                config.getConfig().set("Enable",false);
                                config.saveConfig();
                                config.reloadConfig();
                                player.sendMessage(ChatColor.WHITE+"白名单已关闭");
                            }else {
                                player.sendMessage(ChatColor.RED+"白名单原已关闭");
                            }
                        }else {
                            player.sendMessage(ChatColor.RED+"您没有权限执行此命令！");
                        }
                    }else {
                        if (config.getConfig().getBoolean("Enable",true)){
                            config.getConfig().set("Enable",false);
                            config.saveConfig();
                            config.reloadConfig();
                            System.out.println(ChatColor.WHITE+"白名单已关闭");
                        }else {
                            System.out.println(ChatColor.RED+"白名单原已关闭");
                        }
                    }
                }
                if (args[0].equalsIgnoreCase("list")){
                    if (sender instanceof Player){
                        Player player = (Player) sender;
                        if (player.hasPermission("whitelist.list")){
                            if (Whitelist.size() == 0){
                                player.sendMessage(ChatColor.WHITE+"白名单内没有玩家");
                            }else {
                                player.sendMessage(ChatColor.WHITE+"白名单内共有"+Whitelist.size()+"名玩家: "+Whitelist);
                            }
                        }else {
                            player.sendMessage(ChatColor.RED+"您没有权限执行此命令！");
                        }
                    }else {
                        if (Whitelist.size() == 0){
                            System.out.println(ChatColor.WHITE+"白名单内没有玩家");
                        }else {
                            System.out.println(ChatColor.WHITE+"白名单内共有"+Whitelist.size()+"名玩家: "+Whitelist);
                        }
                    }
                }
                if (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("remove")){
                    if (sender instanceof Player){
                        Player player = (Player) sender;
                        player.sendMessage(ChatColor.RED+"输入的参数有误！");
                    }else {
                        System.out.println(ChatColor.RED+"输入的参数有误！");
                    }
                }
            }
            if (args.length == 2){
                if (args[0].equalsIgnoreCase("add")){
                    if (sender instanceof Player){
                        Player player = (Player) sender;
                        if (player.hasPermission("whitelist.add")){
                            if (Whitelist.contains(args[1])){
                                player.sendMessage(ChatColor.RED+"此玩家已在白名单内");
                            }else {
                                Whitelist.add(args[1]);
                                config.getConfig().set("Whitelist",Whitelist);
                                config.saveConfig();
                                config.reloadConfig();
                                player.sendMessage(ChatColor.WHITE+"已添加玩家"+args[1]+"到白名单内");
                            }
                        }else {
                            player.sendMessage(ChatColor.RED+"您没有权限执行此命令！");
                        }
                    }else {
                        if (Whitelist.contains(args[1])){
                            System.out.println(ChatColor.RED+"此玩家已在白名单内");
                        }else {
                            Whitelist.add(args[1]);
                            config.getConfig().set("Whitelist",Whitelist);
                            config.saveConfig();
                            config.reloadConfig();
                            System.out.println(ChatColor.WHITE+"已添加玩家"+args[1]+"到白名单内");
                        }
                    }
                }
                if (args[0].equalsIgnoreCase("remove")){
                    if (sender instanceof Player){
                        Player player = (Player) sender;
                        if (player.hasPermission("whitelist.remove")){
                            if (!Whitelist.contains(args[1])){
                                player.sendMessage(ChatColor.RED+"此玩家不在白名单内");
                            }else {
                                Whitelist.remove(args[1]);
                                config.getConfig().set("Whitelist",Whitelist);
                                config.saveConfig();
                                config.reloadConfig();
                                player.sendMessage(ChatColor.WHITE+"已从白名单内删除玩家"+args[1]);
                            }
                        }else {
                            player.sendMessage(ChatColor.RED+"您没有权限执行此命令！");
                        }
                    }else {
                        if (!Whitelist.contains(args[1])){
                            System.out.println(ChatColor.RED+"此玩家不在白名单内");
                        }else {
                            Whitelist.remove(args[1]);
                            config.getConfig().set("Whitelist",Whitelist);
                            config.saveConfig();
                            config.reloadConfig();
                            System.out.println(ChatColor.WHITE+"已从白名单内删除玩家"+args[1]);
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {//TAB补全
        if (args.length == 1){
            List<String> list = new ArrayList<>();
            list.add("on");
            list.add("off");
            list.add("add");
            list.add("remove");
            list.add("reload");
            list.add("list");
            if (sender instanceof Player){//是否为玩家(因为控制台不受权限的限制)
                Player player = (Player) sender;
                if (player.hasPermission("whitelist.help")){//有权限才返回可用子命令
                    return list;
                }
            }else {//当为控制台时
                return list;
            }
        }
        if (args.length == 2){
            if (sender instanceof Player){
                Player player = (Player) sender;
                if (player.hasPermission("whitelist.help")){
                    if (args[0].equalsIgnoreCase("add")){//如果子命令为add
                        ArrayList<Player> OnlinePlayerList = new ArrayList<>(Bukkit.getOnlinePlayers());
                        List<String> list = new ArrayList<>();
                        for (int i = 0 ; i < OnlinePlayerList.size() ; i++){
                            if (!Whitelist.contains(OnlinePlayerList.get(i).getName())){//没有白名单且在线的玩家才会加入到TAB补全列表(原版如此)
                                list.add(OnlinePlayerList.get(i).getName());
                            }
                        }
                        return list;//返回在线玩家列表(原版如此)
                    }else if (args[0].equalsIgnoreCase("remove")){
                        return Whitelist;
                    }
                }
            }else {
                if (args[0].equalsIgnoreCase("add")){//如果子命令为add
                    ArrayList<Player> OnlinePlayerList = new ArrayList<>(Bukkit.getOnlinePlayers());
                    List<String> list = new ArrayList<>();
                    for (int i = 0 ; i < OnlinePlayerList.size() ; i++){
                        if (!Whitelist.contains(OnlinePlayerList.get(i).getName())){//没有白名单且在线的玩家才会加入到TAB补全列表(原版如此)
                            list.add(OnlinePlayerList.get(i).getName());
                        }
                    }
                    return list;//返回在线玩家列表(原版如此)
                }else if (args[0].equalsIgnoreCase("remove")){
                    return Whitelist;
                }
            }
        }
        return null;
    }
}
