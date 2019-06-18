package com.hvduck.ezbroadcast.util;

import org.bukkit.inventory.ItemStack;

public class Util
{
  public Util() {}
  
  public static ItemStack createItem(org.bukkit.Material mat, int amt, int durability, String name, java.util.List<String> lore)
  {
    ItemStack item = new ItemStack(mat, amt);
    org.bukkit.inventory.meta.ItemMeta meta = item.getItemMeta();
    meta.setDisplayName(name);
    meta.setLore(lore);
    if (durability != 0) {
      item.setDurability((short)durability);
    }
    item.setItemMeta(meta);
    return item;
  }
  
  public static ItemStack createItem(org.bukkit.Material mat, int amt, String name, java.util.List<String> lore) {
    return createItem(mat, amt, 0, name, lore);
  }
  
  public static ItemStack createItem(org.bukkit.Material mat, int amt, int durability, String name, String... lore) {
    java.util.List<String> l = new java.util.ArrayList();
    for (String s : lore) {
      l.add(s);
    }
    return createItem(mat, amt, durability, name, l);
  }
  
  public static ItemStack createBorder(int color) {
    return createItem(org.bukkit.Material.STAINED_GLASS_PANE, 1, color, color("&c&l&1&c&k&r"), new String[0]);
  }
  
  public static String color(String s) {
    return org.bukkit.ChatColor.translateAlternateColorCodes('&', s);
  }
}
