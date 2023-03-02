package me.harsh.hypixelmigratoraddon;

import de.marcely.bedwars.api.BedwarsAddon;
import org.bukkit.plugin.Plugin;

public class HypixelMigratorPluginAddon extends BedwarsAddon {
    public HypixelMigratorPluginAddon(Plugin plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "HypixelMigratorAddon";
    }
}

