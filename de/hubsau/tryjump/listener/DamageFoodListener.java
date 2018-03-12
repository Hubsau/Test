package de.hubsau.tryjump.listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.inventory.ItemStack;

import de.hubsau.tryjump.JumpLeage;
import de.hubsau.tryjump.Var;
import de.hubsau.tryjump.gamestate.GameStateManager;
import de.hubsau.tryjump.gamestate.IngameState;

public class DamageFoodListener implements Listener {

	int failed;

	@EventHandler
	public void onDamgae(EntityDamageEvent event) {

		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if (Var.INGAME.contains(player)) {

				if (event.getCause().equals(DamageCause.VOID) || event.getCause().equals(DamageCause.FALL)) {

					if (Var.LASTCHECK.containsKey(player) && GameStateManager.getGameState() instanceof IngameState) {

						Location lastcheck = Var.LASTCHECK.get(player);
						player.teleport(lastcheck);
						event.setCancelled(true);
						IngameState ingamestate = (IngameState) GameStateManager.getGameState();

						failed = ingamestate.getProtectionCountdown().getMoveTimer(player).getFailed();
						System.out.println(failed);

						new BukkitRunnable() {

							@Override
							public void run() {

								switch (failed) {

								case 0:
									player.sendTitle("§4", "§4§lX §r§7§lX X");

									break;

								case 1:
									player.sendTitle("§4", "§4§lX §4§lX §r§7§lX");
									player.getInventory().addItem(new ItemStack(Material.WOOL, 1, (short) 5));

									break;
								case 2:
									player.sendTitle("§4", "§4§lX X X");
									player.getInventory().addItem(new ItemStack(Material.WOOL, 1, (short) 5));

									break;

								case 3:

									player.getInventory().addItem(new ItemStack(Material.WOOL, 1, (short) 5));

									break;

								}

								ingamestate.getProtectionCountdown().getMoveTimer(player).setFailed(failed + 1);

							}
						}.runTaskLater(JumpLeage.getInstance(), 5);

					} else {

						event.setCancelled(!Var.ATTACK);

					}
				}
				event.setCancelled(!Var.ATTACK);
			} else {
				event.setCancelled(true);
			}

		} else

			event.setCancelled(true);

	}

	@EventHandler
	public void onDamgae(EntityDamageByBlockEvent event) {

		event.setCancelled(!Var.ATTACK);

	}

	@EventHandler
	public void onDamgae(EntityDamageByEntityEvent event) {

		event.setCancelled(!Var.ATTACK);

	}

	@EventHandler
	public void onDamgae(FoodLevelChangeEvent event) {

		event.setCancelled(true);

	}

}
