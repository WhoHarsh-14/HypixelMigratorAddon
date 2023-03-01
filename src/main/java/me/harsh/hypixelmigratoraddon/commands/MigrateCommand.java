package me.harsh.hypixelmigratoraddon.commands;

import me.harsh.hypixelmigratoraddon.HypixelMigratorAddon;

import me.harsh.hypixelmigratoraddon.config.Config;
import me.harsh.hypixelmigratoraddon.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MigrateCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            final Player player = (Player) sender;
            if (args.length == 1){
                if (args[0].equals("reload")) {
                    Config.reload();
                    Utils.tell(player, "&a&lReloaded successfully!");
                }
            }
        }
        return true;
    }
}
