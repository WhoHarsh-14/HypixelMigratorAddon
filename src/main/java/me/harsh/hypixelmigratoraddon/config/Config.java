package me.harsh.hypixelmigratoraddon.config;

import de.marcely.bedwars.libraries.com.cryptomorin.xseries.XMaterial;
import me.harsh.hypixelmigratoraddon.HypixelMigratorAddon;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;
import java.util.Optional;

public class Config {
    public static Boolean ONLINE_MODE;
    public static String HYPIXEL_API;
    public static String MIGRATE_ITEM_NAME;
    public static boolean MIGRATE_ITEM_IS_HEAD;
    public static String MIGRATE_HEAD_SKIN;
    public static Material MIGRATE_ITEM_ICON;
    public static List<String> MIGRATE_ITEM_LORE;
    public static String NOT_PREMIUM_USER;
    public static String MIGRATION_STARTED;
    public static String MIGRATION_FAILED;
    public static String MIGRATION_SUCCESS;
    public static String PREFIX;

    public static void load(){
        HypixelMigratorAddon.getPlugin().saveDefaultConfig();
        loadVars();
    }

    public static void reload(){
        HypixelMigratorAddon.getPlugin().reloadConfig();
        loadVars();
    }
    public static FileConfiguration get(){
        return HypixelMigratorAddon.getPlugin().getConfig();
    }

    private static void loadVars(){
        ONLINE_MODE = get().getBoolean("online-mode");
        HYPIXEL_API = get().getString("Hypixel-api");
        PREFIX = get().getString("Prefix");

        // Messages
        NOT_PREMIUM_USER = Config.get().getString("Messages.not-premium-player");
        MIGRATION_STARTED = Config.get().getString("Messages.migration-started");
        MIGRATION_FAILED = Config.get().getString("Messages.migration-failed");
        MIGRATION_SUCCESS = Config.get().getString("Messages.migration-success");


        // Item
        final String icon = "Migrate-item.Item-icon";
        final Optional<XMaterial> xMaterial = XMaterial.matchXMaterial(icon);
        xMaterial.ifPresent(material -> MIGRATE_ITEM_ICON = material.parseMaterial());
        MIGRATE_ITEM_IS_HEAD = get().getBoolean("Migrate-item.player-head");
        MIGRATE_HEAD_SKIN = get().getString("Migrate-item.player-head-skin");
        MIGRATE_ITEM_NAME = get().getString("Migrate-item.Item-name");
        MIGRATE_ITEM_LORE = get().getStringList("Migrate-item.Lore");
    }
}
