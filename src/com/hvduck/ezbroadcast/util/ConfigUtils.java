package com.hvduck.ezbroadcast.util;

import com.hvduck.ezbroadcast.Main;

public class ConfigUtils {
  public static ConfigUtils instance;
  
  public ConfigUtils() {}
  
  public static ConfigUtils getInstance() {
    if (instance == null) {
      instance = new ConfigUtils();
    }
    return instance;
  }
  
  public void playSound(org.bukkit.entity.Player p, String path) {
    float volume = (float)Main.getInstance().getConfig().getDouble(String.valueOf(path) + ".volume");
    float pitch = (float)Main.getInstance().getConfig().getDouble(String.valueOf(path) + ".pitch");
    p.playSound(p.getLocation(), Sounds.valueOf(Main.getInstance().getConfig().getString(String.valueOf(path) + ".name").toUpperCase()).bukkitSound(), volume, pitch);
  }
  
  public void playAnnouncementSound(org.bukkit.entity.Player p, String announcement) {
    String str = "Announcements." + announcement + ".Sound";
    if (!Main.getInstance().getConfig().getBoolean(String.valueOf(str) + ".enabled")) {
      return;
    }
    playSound(p, str);
  }
  
  public int getSlot(String path) {
    return Main.getInstance().getConfig().getInt(path);
  }
}
