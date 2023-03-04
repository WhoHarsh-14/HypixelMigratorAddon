package me.harsh.hypixelmigratoraddon.commands;

import de.marcely.bedwars.api.command.CommandHandler;
import de.marcely.bedwars.api.command.SubCommand;
import me.harsh.hypixelmigratoraddon.HypixelMigratorPlugin;
import me.harsh.hypixelmigratoraddon.config.Config;
import me.harsh.hypixelmigratoraddon.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class MigrateCommand implements CommandHandler {

    @Override
    public Plugin getPlugin() {
        return HypixelMigratorPlugin.getPlugin();
    }

    @Override
    public void onRegister(SubCommand subCommand) {

    }

    @Override
    public void onFire(CommandSender commandSender, String s, String[] strings) {
        final Player player = (Player) commandSender;
        if (strings.length == 1){
            if (strings[0].equals("reload")) {
                if (!player.hasPermission("migrator.admin")) return;
                Config.reload();
                Utils.tell(player, "&a&lReloaded successfully!");
            }else if (strings[0].equalsIgnoreCase("migrate")){
                if (!player.hasPermission("migrator.use")) return;
                Utils.getManager().addPlayerChat(player);
                Utils.tell(player, Config.CHAT_MIGRATE);
            }

        }else if (strings.length == 2){
            if (strings[0].equalsIgnoreCase("migrate")){
                if (!player.hasPermission("migrator.use")) return;
                final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(strings[1]);
                if (offlinePlayer == null) return;
                Utils.getManager().migrateFromChat(offlinePlayer, player);
            }
        }

    }

    @Override
    public List<String> onAutocomplete(CommandSender commandSender, String[] strings) {
        final List<String> toComplete = new ArrayList<>();

        switch (strings.length){
            case 1:
                toComplete.add("reload");
                toComplete.add("migrate");
                break;
            case 2:
                Bukkit.getOnlinePlayers().forEach(player -> toComplete.add(player.getDisplayName()));
                break;
        }

        return toComplete;
    }
}
