package com.hvduck.ezbroadcast.util;

import java.util.List;
import java.util.Map;
import org.bukkit.inventory.ItemStack;

public class ItemStackUtils
{
  public ItemStackUtils() {}
  
  private static List<String> replaceColors(List<String> list)
  {
    List<String> listTemp = new java.util.ArrayList();
    for (String s : list) {
      s = org.bukkit.ChatColor.translateAlternateColorCodes('&', s);
      listTemp.add(s);
    }
    return listTemp;
  }
  
  public static ItemStack load(Map<String, Object> keys) {
    try {
      ItemStack stack = null;
      String item = "";
      if (keys.containsKey("material")) {
        if ((keys.get("material") instanceof List)) {
          List<String> list = (List)keys.get("material");
          item = (String)list.get(keys.containsKey("index") ? ((Integer)keys.get("index")).intValue() : java.util.concurrent.ThreadLocalRandom.current().nextInt(list.size()));
        }
        else {
          item = keys.get("material").toString();
        }
      }
      if ((keys.containsKey("material")) && (keys.containsKey("amount"))) {
        stack = com.hvduck.ezbroadcast.Main.getInstance().getEss().getItemDb().get(item, Integer.parseInt(keys.get("amount").toString()));
      }
      else {
        stack = com.hvduck.ezbroadcast.Main.getInstance().getEss().getItemDb().get(item, 1);
      }
      org.bukkit.inventory.meta.ItemMeta meta = stack.getItemMeta();
      if (keys.containsKey("name")) {
        meta.setDisplayName(org.bukkit.ChatColor.translateAlternateColorCodes('&', keys.get("name").toString()));
      }
      if (keys.containsKey("playerhead")) {
        ((org.bukkit.inventory.meta.SkullMeta)meta).setOwner(keys.get("playerhead").toString());
      }
      if (keys.containsKey("lore")) {
        List<String> lore = replaceColors((List)keys.get("lore"));
        meta.setLore(lore);
      }
      if (keys.containsKey("enchants")) {
        List<String> enchants = (List)keys.get("enchants");
        for (String s : enchants) {
          String[] parts = s.split(":");
          if (EnchantUtils.argsToEnchant(parts[0]) != null)
          {

            if ((meta instanceof org.bukkit.inventory.meta.EnchantmentStorageMeta)) {
              ((org.bukkit.inventory.meta.EnchantmentStorageMeta)meta).addStoredEnchant(EnchantUtils.argsToEnchant(parts[0]), Integer.parseInt(parts[1]), true);
            }
            else
              meta.addEnchant(EnchantUtils.argsToEnchant(parts[0]), Integer.parseInt(parts[1]), true);
          }
        }
      }
      stack.setItemMeta(meta);
      if (keys.containsKey("enchanted")) {
        boolean enchanted = Boolean.valueOf(keys.get("enchanted").toString()).booleanValue();
        if (enchanted) {
          EnchantGlow.addGlow(stack);
        }
      }
      return stack;
    }
    catch (Exception ignore) {
      com.hvduck.ezbroadcast.Main.getInstance().getLogger().severe(org.bukkit.ChatColor.stripColor(ignore.getMessage())); }
    return null;
  }
  
  public static boolean isSimilar(ItemStack item, ItemStack compare)
  {
    if ((item == null) || (compare == null)) {
      return false;
    }
    if (item == compare) {
      return true;
    }
    if (item.getTypeId() != compare.getTypeId()) {
      return false;
    }
    if (item.getDurability() != compare.getDurability()) {
      return false;
    }
    if (item.hasItemMeta() != compare.hasItemMeta()) {
      return false;
    }
    if ((item.hasItemMeta()) && (item.getItemMeta().hasDisplayName())) {
      if (item.getItemMeta().hasDisplayName() != compare.getItemMeta().hasDisplayName()) {
        return false;
      }
      if (!item.getItemMeta().getDisplayName().equals(compare.getItemMeta().getDisplayName())) {
        return false;
      }
    }
    return true;
  }
}
