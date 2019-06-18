package com.hvduck.ezbroadcast.util;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class EnchantGlow extends org.bukkit.enchantments.EnchantmentWrapper
{
  private static Enchantment glow;
  
  public EnchantGlow(int id)
  {
    super(id);
  }
  
  public boolean canEnchantItem(ItemStack item) {
    return true;
  }
  
  public boolean conflictsWith(Enchantment other) {
    return false;
  }
  
  public org.bukkit.enchantments.EnchantmentTarget getItemTarget() {
    return null;
  }
  
  public int getMaxLevel() {
    return 10;
  }
  
  public String getName() {
    return "Glow";
  }
  
  public int getStartLevel() {
    return 1;
  }
  
  public static Enchantment getGlow() {
    try {
      if (glow != null) {
        return glow;
      }
      java.lang.reflect.Field f = Enchantment.class.getDeclaredField("acceptingNew");
      f.setAccessible(true);
      f.set(null, Boolean.valueOf(true));
      Enchantment.registerEnchantment(EnchantGlow.glow = new EnchantGlow(255));
      return glow;
    }
    catch (Exception e) {}
    return null;
  }
  
  public static void addGlow(ItemStack item)
  {
    try {
      Enchantment glow = getGlow();
      item.addEnchantment(glow, 1);
    }
    catch (Exception localException) {}
  }
}
