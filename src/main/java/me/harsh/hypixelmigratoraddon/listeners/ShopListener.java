package me.harsh.hypixelmigratoraddon.listeners;

import de.marcely.bedwars.api.GameAPI;
import de.marcely.bedwars.api.arena.Arena;
import de.marcely.bedwars.api.event.ShopGUIPostProcessEvent;
import de.marcely.bedwars.tools.gui.GUIItem;
import de.marcely.bedwars.tools.gui.type.ChestGUI;
import me.harsh.hypixelmigratoraddon.config.Config;
import me.harsh.hypixelmigratoraddon.manager.MigrateManager;
import me.harsh.hypixelmigratoraddon.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

public class ShopListener implements Listener {

    @EventHandler
    public void onOpen(ShopGUIPostProcessEvent event) {
        if (event.getGUI() == null) return;
        if (event.getPage() != null) return;
        if (event.getGUI() instanceof ChestGUI) {
            final ChestGUI gui = (ChestGUI) event.getGUI();
            if (gui == null) return;
            final ItemStack item = Utils.getMigrateItem();
            if (item == null){
                Utils.log("[!] Config values for item are not set properly! migrate system wont work");
                return;
            }
            final MigrateManager manager = Utils.getManager();
            gui.setItem(new GUIItem(item, (player, leftClick, b1) -> {
                if (!player.hasPermission("migrator.use")){
                    Utils.tell(player, Config.MIGRATION_FAILED_NO_PERM);
                    event.getGUI().closeAll();
                    return;
                }
                if (manager.isCooldownOver(player))
                    manager.removeMigrateCoolDown(player);

                if (!manager.isCooldownOver(player)) {
                    Utils.tell(player, Config.MIGRATION_DELAYED);
                    return;
                }
                manager.migratePlayerLayout(player);
                manager.addMigrateCoolDown(player);
            }), 49);
            event.getGUI().closeAll();
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        final Player player = event.getPlayer();
        final Arena arena = GameAPI.get().getArenaByPlayer(player);
        if (arena == null){
            Utils.tell(player, "&cNot in arena");
            return;
        }
        final MigrateManager manager = Utils.getManager();

        if (manager.isInChatList(player)){
            final String playerName = event.getMessage().split(" ")[0];
            if (playerName == null) return;
            final OfflinePlayer p = Bukkit.getOfflinePlayer(playerName);
            if (p == null) return;
            manager.migrateFromChat(p, player);
            manager.removePlayerChat(player);
        }
    }


}
