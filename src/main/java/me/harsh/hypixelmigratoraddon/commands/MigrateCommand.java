package me.harsh.hypixelmigratoraddon.commands;

import me.harsh.hypixelmigratoraddon.HypixelMigratorAddon;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MigrateCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            if (args.length == 1){
                if (args[0].equals("reload")) {
                    HypixelMigratorAddon.getPlugin().reloadConfig();
                }
            }
        }
        return true;
    }
}
