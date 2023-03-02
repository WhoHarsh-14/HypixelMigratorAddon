package me.harsh.hypixelmigratoraddon;

import de.marcely.bedwars.api.BedwarsAPI;
import de.marcely.bedwars.api.BedwarsAddon;
import de.marcely.bedwars.api.command.SubCommand;
import me.harsh.hypixelmigratoraddon.commands.MigrateCommand;
import me.harsh.hypixelmigratoraddon.config.Config;
import me.harsh.hypixelmigratoraddon.listeners.ShopListener;
import me.harsh.hypixelmigratoraddon.manager.MigrateManager;
import me.harsh.hypixelmigratoraddon.utils.Utils;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class HypixelMigratorAddon extends JavaPlugin {

    private static HypixelMigratorAddon plugin;
    private static BedwarsAddon addon;

    @Override
    public void onEnable() {
        plugin = this;
        try {
            Config.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Utils.log("&a" + getPlugin().getName() + " has enabled!");
        registerEverything();
        new Metrics(this, 17834);
        final BedwarsAddon addon = new HypixelMigratorPluginAddon(this);
        BedwarsAPI.onReady(addon::register);
        HypixelMigratorAddon.addon = addon;
        final SubCommand cmd = addon.getCommandsRoot().addCommand("migrator");

        cmd.setAliases("migrator");
        cmd.setUsage("<player_name>");
        cmd.setOnlyForPlayers(true);
        cmd.setHandler(new MigrateCommand());
    }


    private void registerEverything(){
        this.getServer().getPluginManager().registerEvents(new ShopListener(), this);
        Utils.setManager(new MigrateManager());
    }

    public static HypixelMigratorAddon getPlugin() {
        return plugin;
    }

    public static BedwarsAddon getAddon() {
        return addon;
    }
}
