package me.harsh.hypixelmigratoraddon.utils;


import de.marcely.bedwars.api.GameAPI;
import de.marcely.bedwars.api.game.shop.ShopItem;
import de.marcely.bedwars.api.game.shop.ShopPage;
import de.marcely.bedwars.libraries.com.cryptomorin.xseries.XMaterial;
import me.harsh.hypixelmigratoraddon.HypixelMigratorAddon;
import me.harsh.hypixelmigratoraddon.config.Config;
import me.harsh.hypixelmigratoraddon.manager.MigrateManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Utils {
    private static MigrateManager manager;

    public static MigrateManager getManager(){
        return manager;
    }

    public static void tell(Player player, String message){
        player.sendMessage(colorize(Config.PREFIX + " " + message));
    }

    public static void log(String message){
        HypixelMigratorAddon.getPlugin().getServer().getLogger().info(ChatColor.translateAlternateColorCodes('&', message));
    }

    public static ItemStack getMigrateItem(){
        Optional<XMaterial> xMaterial = XMaterial.matchXMaterial(Config.MIGRATE_ITEM_ICON);
        if (!xMaterial.isPresent()) return null;
        final ItemStack item = xMaterial.get().parseItem();
        if (item == null) return null;
        final ItemMeta migrateItemMeta = item.getItemMeta();
        migrateItemMeta.setDisplayName(colorize(Config.MIGRATE_ITEM_NAME));
        migrateItemMeta.setLore(colorize(Config.MIGRATE_ITEM_LORE));
        item.setItemMeta(migrateItemMeta);
        item.setAmount(1);
        if (Config.MIGRATE_ITEM_IS_HEAD) {
            final SkullMeta skull = (SkullMeta) item.getItemMeta();
            String version = Bukkit.getBukkitVersion().split("-")[0];
            if (version.contains("1.8")) {
                skull.setOwner(Config.MIGRATE_HEAD_SKIN);
                item.setItemMeta(skull);
                return item;
            }
            skull.setOwningPlayer(Bukkit.getOfflinePlayer(Config.MIGRATE_HEAD_SKIN));
            item.setItemMeta(skull);
            return item;
        }
        return item;
    }
    public static String colorize(String msg){
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
    public static List<String> colorize(List<String> msg){
        final List<String> colored = new ArrayList<>();
        for (String s : msg) {
            colored.add(colorize(s));
        }
        return colored;
    }

    public static ShopItem[] getAllShopItems(String[] rawItems){
        final ShopItem[] items = new ShopItem[rawItems.length];

        for (int i=0; i<rawItems.length; i++) {
            final ItemManager manager = ItemManager.matchItem(rawItems[i]);
            if (manager == null)
                continue;
            final ShopItem item = getShopItem(manager.getItem());
            if (item == null) {
                items[i] = null;
                continue;
            }
            items[i] = item;
        }

        return items;
    }

    public static ShopItem getShopItem(ItemStack icon){
        for (ShopPage shopPage : GameAPI.get().getShopPages()) {
            if (shopPage == null) return null;
            for (ShopItem item : shopPage.getItems()) {
                if (item.getIcon().getType() == icon.getType()){
                    return item;
                }
                if (checkBow(icon, item) != null)
                    return checkBow(icon, item);

                if (checkPotion(icon, item, PotionEffectType.INVISIBILITY) != null)
                    return checkPotion(icon, item, PotionEffectType.INVISIBILITY);

                if (checkPotion(icon, item, PotionEffectType.JUMP) != null)
                    return checkPotion(icon, item, PotionEffectType.JUMP);

                if (checkPotion(icon, item, PotionEffectType.SPEED) != null)
                    return checkPotion(icon, item, PotionEffectType.SPEED);
            }
        }
        return null;
    }

    public static void setManager(MigrateManager manager) {
        Utils.manager = manager;
    }

    private static ShopItem checkBow(ItemStack icon, ShopItem item){
        if (icon.getItemMeta().hasEnchant(Enchantment.ARROW_KNOCKBACK))
            if (item.getIcon().containsEnchantment(Enchantment.ARROW_KNOCKBACK))
                return item;

        return null;
    }
    private static ShopItem checkPotion(ItemStack icon, ShopItem item, PotionEffectType type) {
        if (icon.getType() != Material.POTION) return null;
        final PotionMeta meta = (PotionMeta) icon.getItemMeta();
        if (meta.hasCustomEffect(type)) {
            if (item.getIcon().getType() != Material.POTION) return null;
            final PotionMeta potionMeta = (PotionMeta) item.getIcon().getItemMeta();
            if (potionMeta.hasCustomEffect(type))
                return item;
        }
        return null;
    }

    public static ItemStack getPotion(String potion){
        final ItemStack itemStack = new ItemStack(Material.POTION);
        final PotionMeta meta = (PotionMeta) itemStack.getItemMeta();
        switch (potion){
            case "invis":
                meta.addCustomEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999999, 2), true);
                return itemStack;
            case "jump":
                meta.addCustomEffect(new PotionEffect(PotionEffectType.JUMP, 999999999, 5), true);
                return itemStack;
            case "speed":
                meta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 999999999, 2), true);
                return itemStack;
            default:
                itemStack.setItemMeta(meta);
                return itemStack;
        }
    }

    public static ItemStack getBow(boolean power, boolean punch){
        final ItemStack itemStack = new ItemStack(Material.BOW);
        final ItemMeta meta = itemStack.getItemMeta();
        if (power)
            meta.addEnchant(Enchantment.ARROW_DAMAGE, 1, false);
        if (punch)
            meta.addEnchant(Enchantment.ARROW_KNOCKBACK, 1, false);
        itemStack.setItemMeta(meta);

        return itemStack;
    }

}
