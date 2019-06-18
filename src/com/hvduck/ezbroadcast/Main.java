package com.hvduck.ezbroadcast;

import com.earth2me.essentials.Essentials;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import me.clip.placeholderapi.PlaceholderAPI;
import com.hvduck.ezbroadcast.broadcast.BroadcastFramework;
import com.hvduck.ezbroadcast.commands.BroadcastCommand;
import com.hvduck.ezbroadcast.util.EnchantGlow;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class Main
  extends JavaPlugin
{
  private Essentials ess;
  private int announcementIndex;
  private BukkitTask announcer;
  private Iterator<String> announcements;
  private File messages;
  private FileConfiguration messagesConfig;
  public static BroadcastFramework announcementFramework;
  private static Boolean placeholdersEnabled;
  private long announcementPeriod;
  
  public Main()
  {
    this.announcementIndex = 1;
  }
  
  public void onEnable()
  {
    setup();
    this.announcements = getAnnouncements();
    this.announcementPeriod = getInstance().getConfig().getLong("Options.AnnouncementInterval");
    announcementFramework = new BroadcastFramework();
    this.ess = ((Essentials)Bukkit.getPluginManager().getPlugin("Essentials"));
    PluginManager pm = Bukkit.getPluginManager();
    placeholdersEnabled = Boolean.valueOf(pm.isPluginEnabled("PlaceholderAPI"));
    getCommand("ezbc").setExecutor(new BroadcastCommand());
    scheduleAnnouncements();
  }
  
  private void setup()
  {
    try
    {
      saveDefaultConfig();
      this.messages = new File(getDataFolder(), "messages.yml");
      if (!this.messages.exists())
      {
        this.messages.createNewFile();
        saveResource("messages.yml", true);
      }
      this.messagesConfig = YamlConfiguration.loadConfiguration(this.messages);
    }
    catch (Exception localException) {}
  }
  
  public void onLoad()
  {
    EnchantGlow.getGlow();
  }
  
  public Essentials getEss()
  {
    return this.ess;
  }
  
  public FileConfiguration getMessages()
  {
    return this.messagesConfig;
  }
  
  private Iterator<String> getAnnouncements()
  {
    return new ArrayList(getInstance().getConfig().getConfigurationSection("Announcements").getKeys(false)).iterator();
  }
  
  private void scheduleAnnouncements()
  {
    this.announcer = new BukkitRunnable()
    {
      public void run()
      {
        Main.this.announcements = (Main.this.announcements.hasNext() ? Main.this.announcements : Main.this.getAnnouncements());
        if (!Main.this.announcements.hasNext()) {
          return;
        }
        BroadcastFramework.getInstance().announce((String)Main.this.announcements.next());
      }
    }.runTaskTimer(getInstance(), 0L, this.announcementPeriod);
  }
  
  public void saveMessages()
  {
    try
    {
      this.messagesConfig.save(this.messages);
    }
    catch (Exception localException) {}
  }
  
  public void reloadMessages()
  {
    try
    {
      this.messagesConfig.load(this.messages);
      this.messagesConfig.save(this.messages);
    }
    catch (Exception localException) {}
  }
  
  public String getPrefix()
  {
    return String.valueOf(ChatColor.translateAlternateColorCodes('&', this.messagesConfig.getString("Prefix"))) + " ";
  }
  
  public String color(Player p, String input)
  {
    return placeholdersEnabled.booleanValue() ? ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(p, input)) : ChatColor.translateAlternateColorCodes('&', input);
  }
  
  public static Main getInstance()
  {
    return (Main)JavaPlugin.getPlugin(Main.class);
  }
  
  public void reloadPlugin()
  {
    reloadConfig();
    reloadMessages();
    this.announcements = getAnnouncements();
  }
}
