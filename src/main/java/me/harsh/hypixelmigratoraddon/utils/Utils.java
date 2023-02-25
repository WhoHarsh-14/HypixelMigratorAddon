package me.harsh.hypixelmigratoraddon.utils;


import me.harsh.hypixelmigratoraddon.HypixelMigratorAddon;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Utils {

    public static void tell(Player player, String message){
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public static void log(String message){
        HypixelMigratorAddon.getPlugin().getServer().getLogger().info(ChatColor.translateAlternateColorCodes('&', message));
    }
}
