package me.harsh.hypixelmigratoraddon.config;

import me.harsh.hypixelmigratoraddon.HypixelMigratorAddon;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class Config {
    public static Boolean ONLINE_MODE;
    public static String HYPIXEL_API;
    public static String MIGRATE_ITEM_NAME;
    public static Material MIGRATE_ITEM_ICON;
    public static List<String> MIGRATE_ITEM_LORE;
    public static String NOT_PREMIUM_USER;
    public static String MIGRATION_STARTED;
    public static String MIGRATION_FAILED;
    public static String MIGRATION_SUCCESS;

    public static void load(){
        HypixelMigratorAddon.getPlugin().saveDefaultConfig();
        ONLINE_MODE = get().getBoolean("online-mode");
        HYPIXEL_API = get().getString("Hypixel-api");

        // Messages
        NOT_PREMIUM_USER = Config.get().getString("Messages.not-premium-player");
        MIGRATION_STARTED = Config.get().getString("Messages.migration-started");
        MIGRATION_FAILED = Config.get().getString("Messages.migration-failed");
        MIGRATION_SUCCESS = Config.get().getString("Messages.migration-success");


        // Item
        MIGRATE_ITEM_ICON = Material.valueOf(get().getString("Migrate-item.Item-icon"));
        MIGRATE_ITEM_NAME = get().getString("Migrate-item.Item-name");
        MIGRATE_ITEM_LORE = get().getStringList("Migrate-item.Lore");
    }
    public static FileConfiguration get(){
        return HypixelMigratorAddon.getPlugin().getConfig();
    }
}
