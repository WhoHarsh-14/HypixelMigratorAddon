package me.harsh.hypixelmigratoraddon.config;

import me.harsh.hypixelmigratoraddon.HypixelMigratorAddon;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {
    public static String HYPIXEL_API;

    public static void load(){
        HYPIXEL_API = get().getString("Hypixel-api");
    }
    public static FileConfiguration get(){
        return HypixelMigratorAddon.getPlugin().getConfig();
    }
}
