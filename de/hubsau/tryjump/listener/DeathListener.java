package de.hubsau.tryjump.listener;

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
						Var.KILLER.put(death, killer);

						Var.DIE.add(UUIDFatcher.getUUID(death.getName()));

						JumpLeage.getInstance().getScoreboardmanager().setScreboardDeatch(death);
						JumpLeage.getInstance().getScoreboardmanager().setScreboardDeatchMatch(killer);
						Bukkit.broadcastMessage(prefix + "§aDer Spieler §6" + death.getDisplayName() + " §awurde von §3"
								+ killer.getDisplayName() + " §agetötet!");
						Var.INGAME.remove(death);
						GameState.checkWinning();
						Var.SPECTATOR.add(death);
						for (Player ingame : Var.INGAME)
							ingame.hidePlayer(death);
						death.setPlayerListName("§7Specatator: §8" + death.getName());
						respawnPlayer(death);

					
					}

				} else {
					JumpLeage.getInstance().getScoreboardmanager().setScreboardDeatch(death);

					Bukkit.broadcastMessage(prefix + "§aDer Spieler §6" + death.getDisplayName() + " §aist gestorben!");

					Var.INGAME.remove(death);
					GameState.checkWinning();
					Var.SPECTATOR.add(death);
					Var.DIE.add(UUIDFatcher.getUUID(death.getName()));
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
