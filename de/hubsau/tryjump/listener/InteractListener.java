package de.hubsau.tryjump.listener;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
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
	}

}
