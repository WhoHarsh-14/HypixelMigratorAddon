package me.harsh.hypixelmigratoraddon.utils;


import de.marcely.bedwars.api.GameAPI;
import de.marcely.bedwars.api.game.shop.ShopItem;
import de.marcely.bedwars.api.game.shop.ShopPage;
import me.harsh.hypixelmigratoraddon.HypixelMigratorAddon;
import me.harsh.hypixelmigratoraddon.config.Config;
import me.harsh.hypixelmigratoraddon.manager.MigrateManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    private static MigrateManager manager;

    public static MigrateManager getManager(){
        return manager;
    }

    public static void tell(Player player, String message){
        player.sendMessage(colorize(Config.PREFIX + " " + message));
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
        if (Config.MIGRATE_ITEM_IS_HEAD) {
            final SkullMeta itemMeta = (SkullMeta) item.getItemMeta();
            itemMeta.setOwner(Config.MIGRATE_HEAD_SKIN);
            item.setItemMeta(meta);
            return item;
        }
        return item;
    }
    public static String colorize(String msg){
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
    public static List<String> colorize(List<String> msg){
        final List<String> colored = new ArrayList<>();
        for (String s : msg) {
            colored.add(colorize(s));
        }
        return colored;
    }

    public static ShopItem[] getAllShopItems(String[] rawItems){
        final ShopItem[] items = new ShopItem[rawItems.length];

        for (int i=0; i<rawItems.length; i++) {
            final ItemManager manager = ItemManager.matchItem(rawItems[i]);
            if (manager == null)
                continue;
            final ShopItem item = getShopItem(manager.getMaterial());
            if (item == null) {
                items[i] = null;
                continue;
            }
            items[i] = item;
        }

        return items;
    }

    public static ShopItem getShopItem(Material icon){
        for (ShopPage shopPage : GameAPI.get().getShopPages()) {
            if (shopPage == null) return null;
            for (ShopItem item : shopPage.getItems()) {
                if (item.getIcon().getType() == icon){
                    return item;
                }
            }
        }
        return null;
    }

    public static void setManager(MigrateManager manager) {
        Utils.manager = manager;
    }

    private static void notNull(Object o, String name) {
        if (o == null) {
            throw new NullPointerException(name + " should not be null!");
        }
    }
}
