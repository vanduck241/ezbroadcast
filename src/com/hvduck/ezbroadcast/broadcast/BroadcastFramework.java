package com.hvduck.ezbroadcast.broadcast;

import com.hvduck.ezbroadcast.*;
import org.bukkit.*;
import org.bukkit.plugin.*;
import org.bukkit.entity.*;
import com.hvduck.ezbroadcast.util.*;

public class BroadcastFramework
{
    public static BroadcastFramework getInstance() {
        return Main.announcementFramework;
    }
    
    public void announce(final String announcement) {
        Bukkit.getScheduler().runTask((Plugin)Main.getInstance(), () -> Bukkit.getOnlinePlayers().forEach(p -> this.displayAnnouncement(p, announcement)));
    }
    
    public void displayAnnouncement(final Player player, final String announcement) {
        final Boolean centered = this.isAnnouncementCentered(announcement);
        Main.getInstance().getConfig().getStringList("Announcements." + announcement + ".lines").stream().map(line -> Main.getInstance().color(player, line)).forEachOrdered(line -> {
            if (centered) {
                ChatCenter.sendCenteredMessage(player, line);
            }
            else {
                player.sendMessage(line);
            }
            return;
        });
        ConfigUtils.getInstance().playAnnouncementSound(player, announcement);
    }
    
    public Integer getAnnouncementOrder(final Player player, final String announcement) {
        return Main.getInstance().getConfig().getInt("Announcements." + announcement + ".order");
    }
    
    public boolean isAnnouncementCentered(final String announcement) {
        return Main.getInstance().getConfig().getBoolean("Announcements." + announcement + ".centered");
    }
}
