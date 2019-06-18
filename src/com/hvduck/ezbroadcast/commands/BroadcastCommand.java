package com.hvduck.ezbroadcast.commands;

import org.bukkit.command.*;
import com.hvduck.ezbroadcast.*;
import java.util.function.*;
import com.hvduck.ezbroadcast.broadcast.*;
import org.bukkit.entity.*;
import com.hvduck.ezbroadcast.util.*;

public class BroadcastCommand implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (!cmd.getName().equalsIgnoreCase("ezbc")) {
            return false;
        }
        if (!sender.hasPermission("ezbc.admin")) {
            Message.sendMessage(sender, "NO-PERMISSION");
        }
        switch (args.length) {
            case 1: {
                if (args[0].equalsIgnoreCase("help")) {
                    return this.displayHelp(sender);
                }
                if (!args[0].equalsIgnoreCase("reload")) {
                    return this.invalidCommand(sender);
                }
                Main.getInstance().reloadPlugin();
                Message.sendMessage(sender, "CONFIG-RELOADED");
                return true;
            }
            case 2: {
                final String lowerCase2;
                final String lowerCase = lowerCase2 = args[0].toLowerCase();
                switch (lowerCase2) {
                    case "display": {
                        final String name = args[1];
                        if (Main.getInstance().getConfig().getConfigurationSection("Announcements").getKeys(false).stream().noneMatch(name::equals)) {
                            Message.sendMessage(sender, "INVALID-ARGS");
                            return false;
                        }
                        BroadcastFramework.getInstance().announce(name);
                        return true;
                    }
                    case "preview": {
                        if (!(sender instanceof Player)) {
                            Message.sendMessage(sender, "PREVIEW-CONSOLE");
                            return false;
                        }
                        final String announcementName = args[1];
                        if (Main.getInstance().getConfig().getConfigurationSection("Announcements").getKeys(false).stream().noneMatch(announcementName::equals)) {
                            Message.sendMessage(sender, "INVALID-ARGS");
                            return false;
                        }
                        BroadcastFramework.getInstance().displayAnnouncement((Player)sender, announcementName);
                        return true;
                    }
                    default: {
                        return this.invalidCommand(sender);
                    }
                }
            }
            default: {
                return this.displayHelp(sender);
            }
        }
    }
    
    private boolean displayHelp(final CommandSender sender) {
        Main.getInstance().getConfig().getStringList("Options.Help").forEach(line -> sender.sendMessage(Util.color(line)));
        return true;
    }
    
    private boolean invalidCommand(final CommandSender sender) {
        Message.sendMessage(sender, "INVALID-CMD");
        return false;
    }
}
