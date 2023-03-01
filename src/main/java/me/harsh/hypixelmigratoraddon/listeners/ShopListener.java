package me.harsh.hypixelmigratoraddon.listeners;

import de.marcely.bedwars.api.event.ShopGUIPostProcessEvent;
import de.marcely.bedwars.tools.gui.GUIItem;
import de.marcely.bedwars.tools.gui.type.ChestGUI;
import me.harsh.hypixelmigratoraddon.utils.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class ShopListener implements Listener {

    @EventHandler
    public void onOpen(ShopGUIPostProcessEvent event) {
        if (event.getGUI() == null) return;
        if (event.getGUI() instanceof ChestGUI) {
            final ChestGUI gui = (ChestGUI) event.getGUI();
            if (gui == null) return;
            final ItemStack item = Utils.getMigrateItem();
            if (item == null){
                Utils.log("[!] Config values for item are not set properly! migrate system wont work");
                return;
            }
            gui.setItem(new GUIItem(item, (player, leftClick, b1) -> {
                Utils.getManager().migratePlayerLayout(player);
            }), 49);
            event.getGUI().closeAll();
        }
    }


}
