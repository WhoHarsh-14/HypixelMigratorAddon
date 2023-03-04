package me.harsh.hypixelmigratoraddon;

import de.marcely.bedwars.api.BedwarsAddon;
import de.marcely.bedwars.api.command.SubCommand;
import me.harsh.hypixelmigratoraddon.commands.MigrateCommand;
import me.harsh.hypixelmigratoraddon.config.Config;
import me.harsh.hypixelmigratoraddon.listeners.ShopListener;
import me.harsh.hypixelmigratoraddon.manager.MigrateManager;
import me.harsh.hypixelmigratoraddon.utils.Utils;
import org.bukkit.plugin.java.JavaPlugin;

public class HypixelMigratorPlugin extends JavaPlugin {

    private static HypixelMigratorPlugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
        Config.load();
        Utils.log("&a" + getPlugin().getName() + " has enabled!");
        registerEverything();
        new Metrics(this, 17834);
        BedwarsAddon addon = new HypixelMigratorAddon(this);
        addon.register();
        if (!addon.isRegistered()) return;
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

    public static HypixelMigratorPlugin getPlugin() {
        return plugin;
    }
}
