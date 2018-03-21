package de.hubsau.tryjump.listener;

import de.hubsau.tryjump.gamestate.IngameState;
import de.hubsau.tryjump.util.DeathCounter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import de.hubsau.tryjump.JumpLeage;
import de.hubsau.tryjump.Var;
import de.hubsau.tryjump.gamestate.EndFightState;
import de.hubsau.tryjump.gamestate.GameState;
import de.hubsau.tryjump.gamestate.GameStateManager;
import de.hubsau.tryjump.mysql.MySQLStats;
import de.hubsau.tryjump.util.Game;
import de.hubsau.tryjump.util.UUIDFatcher;

public class DeathListener implements Listener {

	String prefix = JumpLeage.getInstance().getPrefix();
	int kills;

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		event.setDeathMessage(null);

		if (GameStateManager.getGameState() instanceof EndFightState) {
            EndFightState ingameState = (EndFightState)GameStateManager.getGameState();
			Player death = event.getEntity();

			if (event.getEntity() instanceof Player) {
				if (event.getEntity().getKiller() instanceof Player) {

					Player killer = death.getKiller();

					if (Var.INGAME.contains(killer) && Var.INGAME.contains(death)) {
						if (!Var.KILLS.containsKey(UUIDFatcher.getUUID(killer.getName()))) {
							Var.KILLS.put(UUIDFatcher.getUUID(killer.getName()), 1);

						} else {
							int kills = Var.KILLS.get(UUIDFatcher.getUUID(killer.getName()));
							kills++;
							Var.KILLS.put(UUIDFatcher.getUUID(killer.getName()), kills);

						}



						DeathCounter deathCounter = ingameState.getDeathCoutDown(death);
						deathCounter.addDeath(death, killer, prefix);
						respawnPlayer(death);

					
					}

				} else {
                    DeathCounter deathCounter = ingameState.getDeathCoutDown(death);
                    deathCounter.addDeath(death, prefix);
					respawnPlayer(death);

					

				}

			}
		}

	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {

		if (event.getPlayer().getKiller() instanceof Player) {
			Player killer = event.getPlayer().getKiller();
			event.getPlayer().teleport(killer);

		}

		Game.teleportPlayerToRandomPlayer(event.getPlayer());
	}

	private void respawnPlayer(Player player) {

		new BukkitRunnable() {

			@Override
			public void run() {

				player.spigot().respawn();

			}
		}.runTaskLater(JumpLeage.getInstance(), 1);

	}

}
