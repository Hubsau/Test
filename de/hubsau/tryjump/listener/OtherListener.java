package de.hubsau.tryjump.listener;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import de.hubsau.tryjump.JumpLeage;
import de.hubsau.tryjump.gamestate.GameStateManager;
import de.hubsau.tryjump.gamestate.IngameState;
import de.hubsau.tryjump.gamestate.LobbyState;

public class OtherListener implements Listener {

	@EventHandler
	public void onDrop(PlayerDropItemEvent event) {

		if (GameStateManager.getGameState() instanceof LobbyState
				|| GameStateManager.getGameState() instanceof IngameState) {

			event.setCancelled(event.getItemDrop().getItemStack().getType().equals(Material.SKULL_ITEM));

		}
	}

	@EventHandler
	public void onPickUpItem(PlayerPickupItemEvent event) {

		// if (GameStateManager.getGameState() instanceof LobbyState
		// || GameStateManager.getGameState() instanceof IngameState) {
		// event.setCancelled(true);
		//
		// }
	}

	@EventHandler
	public void onInventoryKlick(InventoryClickEvent event) {
		
		System.out.println(event.getSlot());

		if (GameStateManager.getGameState() instanceof LobbyState
				|| GameStateManager.getGameState() instanceof IngameState) {

			if (event.getInventory().getType().equals(InventoryType.CHEST)) {

				if (event.getCurrentItem().getType().equals(Material.SKULL_ITEM)) {
					if (!event.getInventory().getName().equals("§eTeleporter")) {
						event.getWhoClicked().sendMessage(JumpLeage.getInstance().getPrefix()
								+ "§cDu darft dieses Item nur in deiner Hotbar versetzen");
						event.setCancelled(true);

					}
				}
			}
			if(event.getSlot() > 8) {
				if (!event.getInventory().getName().equals("§eTeleporter")) {
					if (event.getCurrentItem().getType().equals(Material.SKULL_ITEM)) {

					event.getWhoClicked().sendMessage(JumpLeage.getInstance().getPrefix()
							+ "§cDu darft dieses Item nur in deiner Hotbar versetzen");
					event.setCancelled(true);
					}
				}
			
			}
		}
		
		
			

//			}
//				else {
//				if(!event.get ) {
//
//					if (event.getCurrentItem().getType().equals(Material.SKULL_ITEM)) {
//
//						if (!event.getInventory().getName().equals("§eTeleporter")) {
//							event.setCancelled(true);
//
//					event.getWhoClicked().sendMessage(JumpLeage.getInstance().getPrefix()
//							+ "§cDu darft dieses Item nur in deiner Hotbar versetzen");
//						}
//					}
//					
//				}
//
//			}

		}

		// event.setCancelled(event.getCurrentItem().getType().equals(Material.SKULL_ITEM));

		// Player klicked = (Player) event.getWhoClicked();
		// if (!klicked.getGameMode().equals(GameMode.CREATIVE)) {
		// event.setCancelled(true);
		// }

		// if (Var.SPECTATOR.contains((Player) event.getWhoClicked())) {
		// event.setCancelled(true);
		// }
	

	@EventHandler
	public void onWeahterChange(WeatherChangeEvent event) {
		event.setCancelled(true);

	}

	@EventHandler
	public void onWeahterChange(EntitySpawnEvent event) {

		event.setCancelled(!(event.getEntity() instanceof Player) && !(event.getEntity() instanceof Item));

	}

}
