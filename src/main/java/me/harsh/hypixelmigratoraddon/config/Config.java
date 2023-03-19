package me.harsh.hypixelmigratoraddon.config;

import de.marcely.bedwars.tools.Helper;
import me.harsh.hypixelmigratoraddon.HypixelMigratorPlugin;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Config {
    public static Boolean ONLINE_MODE;
    public static String HYPIXEL_API;
    public static boolean CLOSE_GUI;
    public static int DELAY;
    public static String MIGRATE_ITEM_NAME;
    public static boolean MIGRATE_ITEM_IS_HEAD;
    public static String MIGRATE_HEAD_SKIN;
    public static ItemStack MIGRATE_ITEM_ICON;
    public static List<String> MIGRATE_ITEM_LORE;
    public static String NOT_PREMIUM_USER;
    public static String MIGRATION_STARTED;
    public static String MIGRATION_FAILED;
    public static String MIGRATION_FAILED_NO_PERM;
    public static String MIGRATION_SUCCESS;
    public static String MIGRATION_DELAYED;
    public static String PREFIX;
    public static String CHAT_MIGRATE;
    public static String QUICK_SHOP_ITEM;

    public static void load(){
        HypixelMigratorPlugin.getPlugin().saveDefaultConfig();
        loadVars();
    }

    public static void reload(){
        HypixelMigratorPlugin.getPlugin().reloadConfig();
        loadVars();
    }

    public static FileConfiguration get(){
        return HypixelMigratorPlugin.getPlugin().getConfig();
    }

    private static void loadVars(){
        ONLINE_MODE = get().getBoolean("online-mode");
        HYPIXEL_API = get().getString("Hypixel-api");
        DELAY = get().getInt("Delay");
        PREFIX = get().getString("Prefix");
        CLOSE_GUI = get().getBoolean("Close-shop-on-migrate");
        QUICK_SHOP_ITEM = get().getString("Quick-shop-item");

        // Messages
        NOT_PREMIUM_USER = Config.get().getString("Messages.not-premium-player");
        MIGRATION_STARTED = Config.get().getString("Messages.migration-started");
        MIGRATION_FAILED = Config.get().getString("Messages.migration-failed");
        MIGRATION_FAILED_NO_PERM = Config.get().getString("Messages.migration-failed-no-perms");
        MIGRATION_SUCCESS = Config.get().getString("Messages.migration-success");
        MIGRATION_DELAYED = Config.get().getString("Messages.migration-delay");
        CHAT_MIGRATE = Config.get().getString("Messages.chat-migrate");


        // Item
        MIGRATE_ITEM_ICON = loadItemStack(get().getString("Migrate-item.Item-icon"));
        MIGRATE_ITEM_IS_HEAD = get().getBoolean("Migrate-item.player-head");
        MIGRATE_HEAD_SKIN = get().getString("Migrate-item.player-head-skin");
        MIGRATE_ITEM_NAME = get().getString("Migrate-item.Item-name");
        MIGRATE_ITEM_LORE = get().getStringList("Migrate-item.Lore");
    }

    // Tries to parse the item using thw config string. Returns stone if it fails
    private static ItemStack loadItemStack(String configItem){
        final ItemStack is = configItem != null ? Helper.get().parseItemStack(configItem) : null;

        return is != null ? is : new ItemStack(Material.STONE);
    }
}
