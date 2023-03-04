package me.harsh.hypixelmigratoraddon.manager;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.marcely.bedwars.api.GameAPI;
import de.marcely.bedwars.api.game.shop.ShopItem;
import de.marcely.bedwars.api.player.PlayerDataAPI;
import me.harsh.hypixelmigratoraddon.HypixelMigratorPlugin;
import me.harsh.hypixelmigratoraddon.config.Config;
import me.harsh.hypixelmigratoraddon.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class MigrateManager {

    private final List<Player> migrateCoolDown = new ArrayList<>();
    private final List<UUID> migrateChat = new ArrayList<>();

    public void migratePlayerLayout(Player player) {
        if (Config.ONLINE_MODE) {
            applyPlayerMigratedLayout(player, player.getUniqueId(), true);
        } else {
            getUuidFromWeb(player, uuid -> {
                if (uuid == null) {
                    Utils.tell(player, Config.NOT_PREMIUM_USER);
                } else {
                    applyPlayerMigratedLayout(player, uuid, true);
                }
            });
        }
    }

    public void migrateFromChat(OfflinePlayer p, Player player) {
        getUuidFromWeb(p, uuid -> {
            if (uuid == null) {
                Utils.tell(player, Config.NOT_PREMIUM_USER);
            } else {
                applyPlayerMigratedLayout(player, uuid, false);
            }
        });
    }

    public boolean isCooldownOver(Player player) {
        return !migrateCoolDown.contains(player);
    }

    public void addMigrateCoolDown(Player player){
        migrateCoolDown.add(player);
        Bukkit.getScheduler().runTaskLater(HypixelMigratorPlugin.getPlugin(), () -> migrateCoolDown.remove(player), 20L * Config.DELAY);
    }

    private void applyPlayerMigratedLayout(Player player, UUID uuid, boolean refreshGUI) {
        Utils.tell(player, Config.MIGRATION_STARTED);
        getHypixelTemplate(uuid, response -> {
            if (response == null) {
                Utils.tell(player, Config.NOT_PREMIUM_USER);
                return;
            }

            if (response.get("player").isJsonNull()) {
                Utils.tell(player, Config.MIGRATION_FAILED);
                return;
            }
            final String layout = response.getAsJsonObject("player").getAsJsonObject("stats").getAsJsonObject("Bedwars").get("favourites_2").getAsString();
            String[] items = layout.split(",");
            ShopItem[] shopItems = Utils.getAllShopItems(items);
            PlayerDataAPI.get().getProperties(player, playerProperties -> {
                playerProperties.setShopHypixelV2QuickBuyItems(shopItems);
                Utils.tell(player, Config.MIGRATION_SUCCESS);

                if(refreshGUI)
                    GameAPI.get().openShop(player); // re-open quick buy
            });
        });
    }

    private void getHypixelTemplate(UUID uuid, Consumer<JsonObject> response) {
        Bukkit.getScheduler().runTaskAsynchronously(HypixelMigratorPlugin.getPlugin(), () -> {
            try {
                final URL hypixelApi = new URL(String.format("https://api.hypixel.net/player?key=%s&uuid=%s", Config.HYPIXEL_API, uuid.toString()));
                final HttpURLConnection httpURLConnection = (HttpURLConnection)hypixelApi.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setRequestProperty("Content-Type", "application/json");
                if (httpURLConnection.getResponseCode() == 200) {
                    final InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
                    final JsonObject json = (new JsonParser()).parse(inputStreamReader).getAsJsonObject();
                    response.accept(json);
                } else
                    response.accept(null);
                Utils.log("response code:- " + httpURLConnection.getResponseCode());
                httpURLConnection.disconnect();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    private void getUuidFromWeb(OfflinePlayer player, Consumer<UUID> callback) {
        Bukkit.getScheduler().runTaskAsynchronously(HypixelMigratorPlugin.getPlugin(), () -> {
            try {
                URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + player.getName());
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json");
                if (connection.getResponseCode() == 200) {
                    InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                    JsonObject json = (new JsonParser()).parse(reader).getAsJsonObject();
                    String uuid = json.get("id").getAsString().replaceAll("(.{8})(.{4})(.{4})(.{4})(.+)", "$1-$2-$3-$4-$5");
                    callback.accept(UUID.fromString(uuid));
                } else {
                    callback.accept(null);
                }
                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public boolean isInChatList(Player player){
        return migrateChat.contains(player.getUniqueId());
    }

    public void addPlayerChat(Player player){
        if (isInChatList(player))
            return;

        if (isCooldownOver(player))
            return;

        migrateChat.add(player.getUniqueId());
        addMigrateCoolDown(player);
    }

    public void removePlayerChat(Player player){
        if (!isInChatList(player))
            return;

        if (!isCooldownOver(player))
            return;

        migrateChat.remove(player.getUniqueId());
    }
}
