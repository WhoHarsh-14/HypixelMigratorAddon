package me.harsh.hypixelmigratoraddon;

import de.marcely.bedwars.api.BedwarsAddon;
import org.bukkit.plugin.Plugin;

public class HypixelMigratorAddon extends BedwarsAddon {
    public HypixelMigratorAddon(Plugin plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "HypixelMigratorAddon";
    }
}

