package me.harsh.hypixelmigratoraddon.commands;

import me.harsh.hypixelmigratoraddon.config.Config;
import me.harsh.hypixelmigratoraddon.utils.Utils;
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
                }
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        final List<String> toComplete = new ArrayList<>();

        if (args.length == 1)
            toComplete.add("reload");

        return toComplete;
    }
}
