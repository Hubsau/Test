package de.hubsau.tryjump.listener;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import de.hubsau.tryjump.JumpLeage;
import de.hubsau.tryjump.Var;
import de.hubsau.tryjump.api.ItemBuilder;
import de.hubsau.tryjump.util.Game;

public class SpectatorListener implements Listener {

	List<Player> incamera = Var.INKAMERA;

	@EventHandler
	public void onInteractAtEntity(PlayerInteractAtEntityEvent event) {

		if (Var.SPECTATOR.contains(event.getPlayer()) && !incamera.contains(event.getPlayer())) {

			if (event.getRightClicked() instanceof Player && Var.INGAME.contains(event.getRightClicked())) {
				Game.setCamera(event.getPlayer(), (Player) event.getRightClicked());
				incamera.add(event.getPlayer());

				event.getPlayer().sendMessage(JumpLeage.getInstance().getPrefix()
						+ "§aDu bist jetz in der Spielersicht von §6" + event.getRightClicked().getName());
				event.getPlayer().sendMessage(JumpLeage.getInstance().getPrefix() + "§3Verlassen mit §eSHIFT");
			}
		}
	}

	@EventHandler
	public void onInteractAtEntity(PlayerToggleSneakEvent event) {

		if (Var.SPECTATOR.contains(event.getPlayer()) && incamera.contains(event.getPlayer())) {

			Game.removeCamera(event.getPlayer());
			incamera.remove(event.getPlayer());
			event.getPlayer().sendMessage(JumpLeage.getInstance().getPrefix() + "§aDu hast die Spielersicht verlassen");

		}
	}

	@EventHandler
	public void onPlayerTeleport(PlayerInteractEvent event) {

		if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

			if (Var.SPECTATOR.contains(event.getPlayer())) {
				if (event.getPlayer().getItemInHand()
						.equals(new ItemBuilder(Material.WATCH).name("§3Teleporter §7(Rechtsklick)").build())) {

					event.getPlayer().openInventory(Game.getInventory());

					ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
					SkullMeta im = (SkullMeta) skull.getItemMeta();
					for (int i = 0; i < Var.INGAME.size(); i++) {

						im.setOwner(Var.INGAME.get(i).getName());
						im.setDisplayName("§7" + Var.INGAME.get(i).getName());
						skull.setItemMeta(im);
					}

					new BukkitRunnable() {

						@Override
						public void run() {

							for (int i = 0; i < Var.INGAME.size(); i++) {

								Game.getInventory().setItem(i, skull);

							}

						}
					}.runTaskLater(JumpLeage.getInstance(), 10);

				}

			}

		}
	}

	@EventHandler
	public void onInventoryKlick(InventoryClickEvent event) {

		Player klicker = (Player) event.getWhoClicked();

		if (event.getInventory().getName().equals("§eTeleporter")) {

			ItemStack klicked = event.getCurrentItem();
			SkullMeta meta = (SkullMeta) klicked.getItemMeta();

			Player target = Bukkit.getPlayer(meta.getOwner());
			if (target != null) {
				if (Var.INGAME.contains(target)) {

					klicker.teleport(target);

				} else {
					klicker.sendMessage(
							JumpLeage.getInstance().getPrefix() + "§3" + meta.getOwner() + " §cist bereits gestorben!");
				}

			} else {
				klicker.sendMessage(
						JumpLeage.getInstance().getPrefix() + "§3" + meta.getOwner() + " §cist nicht mehr online!");
			}

		}

	}

}
