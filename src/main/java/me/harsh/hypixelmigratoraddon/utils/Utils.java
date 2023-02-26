package me.harsh.hypixelmigratoraddon.utils;


import me.harsh.hypixelmigratoraddon.HypixelMigratorAddon;
import me.harsh.hypixelmigratoraddon.config.Config;
import me.harsh.hypixelmigratoraddon.manager.MigrateManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static MigrateManager getManager(){
        return new MigrateManager();
    }

    public static void tell(Player player, String message){
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public static void log(String message){
        HypixelMigratorAddon.getPlugin().getServer().getLogger().info(ChatColor.translateAlternateColorCodes('&', message));
    }

    public static ItemStack getMigrateItem(){
        ItemStack item = new ItemStack(Config.MIGRATE_ITEM_ICON);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(colorize(Config.MIGRATE_ITEM_NAME));
        meta.setLore(colorize(Config.MIGRATE_ITEM_LORE));
        item.setItemMeta(meta);
        item.setAmount(1);
        return item;
    }
    public static String colorize(String msg){
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
    public static List<String> colorize(List<String> msg){
        final List<String> colored = new ArrayList<>();
        for (String s : msg) {
            colored.add(ChatColor.translateAlternateColorCodes('&', s));
        }
        return colored;
    }
}
