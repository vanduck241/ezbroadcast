package com.hvduck.ezbroadcast.util;

import com.hvduck.ezbroadcast.Main;

public class Message
{
  public Message() {}
  
  public static void sendMessage(org.bukkit.command.CommandSender p, MessagePart str) {
    if (str.getPart().isEmpty()) {
      return;
    }
    p.sendMessage(String.valueOf(Main.getInstance().getPrefix()) + str.getPart());
  }
  
  public static void sendMessage(org.bukkit.command.CommandSender p, String str) {
    MessagePart strPart = generate(str);
    if (strPart.getPart().isEmpty()) {
      return;
    }
    p.sendMessage(String.valueOf(Main.getInstance().getPrefix()) + strPart.getPart());
  }
  
  public static MessagePart generate(String str) {
    return new MessagePart(str);
  }
  
  public static class MessagePart
  {
    public String str;
    
    public MessagePart(String str) {
      this.str = (Main.getInstance().getMessages().isString(str.toUpperCase()) ? org.bukkit.ChatColor.translateAlternateColorCodes('&', Main.getInstance().getMessages().getString(str.toUpperCase())) : "");
    }
    
    public MessagePart replace(Object placeholder, Object key) {
      str = str.replace(placeholder.toString(), key.toString());
      return this;
    }
    
    public String getPart() {
      return str;
    }
  }
}
