package me.harsh.hypixelmigratoraddon;

import me.harsh.hypixelmigratoraddon.commands.MigrateCommand;
import me.harsh.hypixelmigratoraddon.config.Config;
import me.harsh.hypixelmigratoraddon.listeners.PlayerListener;
import me.harsh.hypixelmigratoraddon.utils.Utils;
import org.bukkit.plugin.java.JavaPlugin;

public class HypixelMigratorAddon extends JavaPlugin {

    private static HypixelMigratorAddon plugin;
    @Override
    public void onEnable() {
        plugin = this;
        Config.load();
        Utils.log("&a" + getPlugin().getName() + " has enabled!");
        registerEverything();
    }


    private void registerEverything(){
        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        this.getCommand("migrate").setExecutor(new MigrateCommand());
    }

    public static HypixelMigratorAddon getPlugin() {
        return plugin;
    }
}
