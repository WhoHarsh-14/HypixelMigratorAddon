package me.harsh.hypixelmigratoraddon.commands;

import me.harsh.hypixelmigratoraddon.config.Config;
import me.harsh.hypixelmigratoraddon.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MigrateCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            final Player player = (Player) sender;
            if (args.length == 1){
                if (args[0].equals("reload")) {
                    if (!player.hasPermission("migrator.admin")) return false;
                    Config.reload();
                    Utils.tell(player, "&a&lReloaded successfully!");
                }else if (args[0].equalsIgnoreCase("migrate")){
                    if (!player.hasPermission("migrator.use")) return false;
                    Utils.getManager().addPlayerChat(player);
                    Utils.tell(player, Config.CHAT_MIGRATE);
                }

            }else if (args.length == 2){
                if (args[0].equalsIgnoreCase("migrate")){
                    if (!player.hasPermission("migrator.use")) return false;
                    final OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
                    if (offlinePlayer == null) return false;
                    Utils.getManager().migrateFromChat(offlinePlayer, player);
                }
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        final List<String> toComplete = new ArrayList<>();

        switch (args.length){
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
