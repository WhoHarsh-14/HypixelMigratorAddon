package me.harsh.hypixelmigratoraddon;

import me.harsh.hypixelmigratoraddon.config.Config;
import me.harsh.hypixelmigratoraddon.utils.Utils;
import org.bukkit.plugin.java.JavaPlugin;

public class HypixelMigratorAddon extends JavaPlugin {

    private static HypixelMigratorAddon plugin;
    @Override
    public void onEnable() {
        plugin = this;
        saveDefaultConfig();
        Utils.log("&a" + getPlugin().getName() + " has enabled!");
        registerListeners();
        Config.load();
    }


    private void registerListeners(){
    }
    public static HypixelMigratorAddon getPlugin() {
        return plugin;
    }
}
