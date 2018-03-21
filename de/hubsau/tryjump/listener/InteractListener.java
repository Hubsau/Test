package de.hubsau.tryjump.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import de.hubsau.tryjump.util.RandomItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import de.hubsau.tryjump.JumpLeage;
import de.hubsau.tryjump.Var;
import de.hubsau.tryjump.api.ItemBuilder;
import de.hubsau.tryjump.command.SetupCommand;
import de.hubsau.tryjump.gamestate.GameStateManager;
import de.hubsau.tryjump.gamestate.IngameState;

public class InteractListener implements Listener {
	int i1 = 1;
	int i2 = 1;

	public static List<Player> delay = new ArrayList<>();
	public static List<Location> enderchests = new ArrayList<>();

	public RandomItem randomitem;

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {

		Player player = event.getPlayer();
		ItemStack skull = new ItemStack(Material.SKULL_ITEM);

		if (player.getItemInHand().getType().equals(Material.SKULL_ITEM)) {
			if (event.getAction().equals(Action.RIGHT_CLICK_AIR)
					|| event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

				if (Var.LASTCHECK.containsKey(player) && GameStateManager.getGameState() instanceof IngameState) {

					Location lastcheck = Var.LASTCHECK.get(player);

					if (!delay.contains(player)) {
						player.teleport(lastcheck);
						delay.add(player);
						new BukkitRunnable() {

							@Override
							public void run() {

								delay.remove(player);

							}
						}.runTaskLaterAsynchronously(JumpLeage.getInstance(), 25);

					} else {
						player.sendMessage(JumpLeage.getInstance().getPrefix() + "§cBitte warte einen Augeblick");
					}
					event.setCancelled(true);
					return;
				} else
					event.setCancelled(true);

			}

		} else {
			if (player.getItemInHand().equals(

					new ItemBuilder(Material.STICK).enchantment(Enchantment.KNOCKBACK).name("§2Spawns Item").build())) {

				if (Var.INSETUP.containsKey(player)) {
					String setup = Var.INSETUP.get(player);

					if (event.getAction().equals(Action.RIGHT_CLICK_AIR)
							|| event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

						if (setup.equals("end")) {

							try {
								SetupCommand.setEndSpawns(i1, player);
							} catch (Exception e) {
								e.printStackTrace();
							}
							i1++;

						} else if (setup.equals("spawns")) {
							try {
								SetupCommand.setSpawns(i2, player);

							} catch (Exception e) {
								e.printStackTrace();
							}
							i2++;

						}
					} else {
						if (event.getAction().equals(Action.LEFT_CLICK_AIR)
								|| event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
							if (setup.equals("end")) {
								if (i1 >= 1) {
									i1--;
								} else {

								}

							} else {
								if (setup.equals("spawns")) {
									if (i2 >= 1) {
										i2--;

									}

								}

							}

						}

					}
				}

			}
		}
		if (Var.SPECTATOR.contains(player)) {
			event.setCancelled(true);

		}

		if (GameStateManager.getGameState() instanceof IngameState) {

			if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && event.getClickedBlock().getType().equals(Material.ENDER_CHEST)) {
			if (!enderchests.contains(event.getClickedBlock().getLocation())) {
				
				event.setCancelled(true);

				Random random = new Random();

				int ivnd = random.nextInt(4);
                System.out.println(ivnd);

                randomitem=  new RandomItem(20,10,ivnd, player);


				enderchests.add(event.getClickedBlock().getLocation());


				
				
			}else {
				event.setCancelled(true);



				player.openInventory(randomitem.getInventory());
			}

			}
		}
		
		
	}

	public int getRandom() {

		Double random = 0.0;

		random = Math.random() * 10;
		while (random >= 2) {
			random = Math.random() * 10;

		}
		return random.intValue();

	}

}
