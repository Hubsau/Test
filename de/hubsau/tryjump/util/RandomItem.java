package de.hubsau.tryjump.util;
//Class createt by Hubsau
//
//
//20:57 2018 13.03.2018
//Wochentag : Dienstag


import de.hubsau.tryjump.Var;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Random;

public class RandomItem {

    int max;
    int min;
    public Inventory inventory;



    public RandomItem(int max, int min, int inventoryId, Player player) {

        this.max = max;
        this.min = min;


        inventory = Bukkit.createInventory(null, 9 * 3, "§7Enderchest");


        Random random = new Random();
        ArrayList<ItemStack> contenst = (ArrayList<ItemStack>) Var.CONFIGCFG.get("inventory." + inventoryId);
        ItemStack[] contenstsarray = contenst.toArray(new ItemStack[contenst.size()]);
        int amount = random.nextInt(max - min + 1) + min;
        for (int i = 0; i < amount; i++) {
            if (i <= contenstsarray.length) {
                if (contenstsarray[i + 1] != null) {
                    int itemAmount= random.nextInt(2);
                    addItemToInvenotory(inventory, contenstsarray[i + 1], itemAmount+1);
                }
            }
        }
        for (ItemStack items : inventory.getContents()) {
            if(items != null) {
                if (items.getType().equals(Material.AIR)) {
                    new RandomItem(max, min, inventoryId, player);
                }
            }
            player.openInventory(inventory);
        }
    }
    public void addItemToInvenotory(Inventory inventory, ItemStack item, int itemAmount){
        item.setAmount(itemAmount);
        inventory.addItem(item);
    }
    public Inventory getInventory() {
        return inventory;
    }
}
