package me.harsh.hypixelmigratoraddon.listeners;

import de.marcely.bedwars.api.arena.Arena;
import de.marcely.bedwars.api.event.ShopGUIPostProcessEvent;
import de.marcely.bedwars.api.event.player.SpectatorJoinArenaEvent;
import de.marcely.bedwars.api.game.spectator.SpectateReason;
import de.marcely.bedwars.tools.gui.GUIItem;
import de.marcely.bedwars.tools.gui.type.ChestGUI;
import me.harsh.hypixelmigratoraddon.utils.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class PlayerListener implements Listener {

    // Item slot 49
    @EventHandler
    public void onOpen(ShopGUIPostProcessEvent event) {
        if (event.getGUI() == null) return;
        if (event.getGUI() instanceof ChestGUI) {
            final ChestGUI gui = (ChestGUI) event.getGUI();
            final ItemStack item = Utils.getMigrateItem();
            gui.setItem(new GUIItem(item, (player, leftClick, b1) -> {
                Utils.getManager().migratePlayerLayout(player);
            }), 49);
        }
    }

}
