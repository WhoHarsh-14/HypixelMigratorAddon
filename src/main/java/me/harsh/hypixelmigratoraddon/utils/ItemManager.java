package me.harsh.hypixelmigratoraddon.utils;

import de.marcely.bedwars.api.GameAPI;
import de.marcely.bedwars.api.game.shop.ShopItem;
import de.marcely.bedwars.api.game.shop.ShopPage;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;

public enum ItemManager {
    WOOL("wool"),
    CLAY( "hardened_clay"),
    GLASS("blast-proof_glass"),
    END_STONE( "end_stone"),
    LADDER("ladder"),
    WOOD("oak_wood_planks"),
    OBSIDIAN("obsidian"),
    STONE_SWORD("stone_sword"),
    IRON_SWORD("iron_sword"),
    DIAMOND_SWORD("diamond_sword"),
    KNOCKBACK_STICK("stick_(knockback_i)"),
    CHAINMAIL_ARMOR("chainmail_boots"),
    IRON_ARMOR("iron_boots"),
    DIAMOND_ARMOR("diamond_boots"),
    SHEARS("shears"),
    WOODEN_PICKAXE("wooden_pickaxe"),
    WOODEN_AXE("wooden_axe"),
    ARROW("arrow"),
    BOW_ONE("bow"),
    BOW_TWO("bow_(power_i)"),
    BOW_THREE("bow_(power_i__punch_i)"),
    SPEED_POTION("speed_ii_potion_(45_seconds)"),
    JUMP_POTION("jump_v_potion_(45_seconds)"),
    INVISIBILITY_POTION("invisibility_potion_(30_seconds)"),
    GOLDEN_APPLE("golden_apple"),
    BEDBUG("bedbug"),
    DREAM_DEFENDER("dream_defender"),
    FIREBALL("fireball"),
    TNT("tnt"),
    ENDER_PEARL("ender_pearl"),
    WATER_BUCKET("water_bucket"),
    BRIDGE_EGG("bridge_egg"),
    MAGIC_MILK("magic_milk"),
    SPONGE("sponge"),
    POPUP_TOWER("compact_pop-up_tower");

    ItemManager(String id) {
        this.materialName = id;
    }

    private final String materialName;
    public String getMaterialName() {
        return this.materialName;
    }

    public Material getMaterial() {
        return Material.matchMaterial(materialName);
    }

    public static ItemManager findItem(String item) {
        return Arrays.<ItemManager>stream(values()).filter(layoutItem -> layoutItem.getMaterialName().equals(item)).findAny().orElse(null);
    }

    public static ShopItem[] getAllShopItems(String[] items){
        final ArrayList<ShopItem> shopItems = new ArrayList<>();
        for (String s : items) {
            final ItemManager manager = ItemManager.findItem(s);
            final ShopItem item = getShopItem(manager.getMaterial());
            if (item == null) return null;
            shopItems.add(item);
        }
        final ShopItem[] item = new ShopItem[shopItems.size()];
        return shopItems.toArray(item);
    }

    public static ShopItem getShopItem(Material icon){
        for (ShopPage shopPage : GameAPI.get().getShopPages()) {
            if (shopPage == null) return null;
            for (ShopItem item : shopPage.getItems()) {
                if (item.getIcon().getType() == icon){
                    return item;
                }
            }
        }
        return null;
    }
}
