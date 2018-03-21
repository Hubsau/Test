package de.hubsau.tryjump.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import de.hubsau.tryjump.JumpLeage;
import de.hubsau.tryjump.Var;
import de.hubsau.tryjump.api.ConfigAPI;
import de.hubsau.tryjump.api.ItemBuilder;
import de.hubsau.tryjump.gamestate.GameStateManager;
import de.hubsau.tryjump.gamestate.IngameState;
import de.hubsau.tryjump.gamestate.LobbyState;
import de.hubsau.tryjump.mysql.MySQLStats;
import de.hubsau.tryjump.util.Game;
import de.hubsau.tryjump.util.Hologram;
import de.hubsau.tryjump.util.PlayerChatColor;
import de.hubsau.tryjump.util.UUIDFatcher;

public class JoinListener implements Listener {

	String prefix = JumpLeage.getInstance().getPrefix();

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {

		Player player = event.getPlayer();

		if (!MySQLStats.isRegistered(UUIDFatcher.getUUID(player.getName()))) {
			MySQLStats.register(player);

		}
		
		
		
		// MySQLStats.addDeaths(player.getUniqueId(), 10);
		// }

		player.setPlayerListName(PlayerChatColor.getColorName(player));
		player.setDisplayName(player.getPlayerListName());
		event.setJoinMessage(null);

		if (GameStateManager.getGameState() instanceof LobbyState) {

			LobbyState lobbyState = (LobbyState) GameStateManager.getGameState();

			double kd;

			try {

				kd = MySQLStats.getKills(UUIDFatcher.getUUID(player.getName()))
						/ MySQLStats.getDeaths(UUIDFatcher.getUUID(player.getName()));

			} catch (Exception e) {

				kd = 0.0;

			}

			int kills = MySQLStats.getKills(UUIDFatcher.getUUID(player.getName()));
			int deaths = MySQLStats.getDeaths(UUIDFatcher.getUUID(player.getName()));
			int winns = MySQLStats.getWinns(UUIDFatcher.getUUID(player.getName()));
			int loses = MySQLStats.getLoses(UUIDFatcher.getUUID(player.getName()));

			Hologram statsholo = new Hologram(new String[] { "§7-=§6Deine JumpLeage Stats§7=-",
					"§7Spieler: " + player.getName(), "§7KD: " + Math.round(kd * 100) / 100.0, "§7Kills: " + kills,
					"§7Deaths: " + deaths, "§7Gewonnene Spiele: " + winns, "§7Verlorene Spiele: " + loses }

					, ConfigAPI.getCfg("hologram.spawn", Var.LOCATIONSCFG));
			statsholo.showPlayer(player);

			Game.setLobbyItems(player);

			Var.INGAME.add(player);

			Bukkit.broadcastMessage("§2[§a+§2] §6Der Spieler §b" + PlayerChatColor.getColorName(player)
					+ " §6hat den Server betreten" + " §8[§f" + Var.INGAME.size() + "§7/§f" + Game.MAXPLAYER() + "§8]");

			if (Var.INGAME.size() >= Game.MINPLAYERS()) {
				if (!lobbyState.getCountdown().isRunning()) {
					lobbyState.getCountdown().stopIdle();
					lobbyState.getCountdown().start();

				}

			}
			if (Var.INGAME.size() < Game.MINPLAYERS()) {
				if (!lobbyState.getCountdown().isIdle()) {
					lobbyState.getCountdown().waitForPlayers();

				}

			}
			for (Player ingame : Var.INGAME)
				JumpLeage.getInstance().getScoreboardmanager().setScoreboardLobby(ingame);

		} else if (GameStateManager.getGameState() instanceof IngameState) {

			Var.SPECTATOR.add(player);
			player.getInventory().clear();
			player.setAllowFlight(true);
			player.setFlying(true);
			player.setPlayerListName("§7[§8Spec§7] §7" + player.getName());
			player.getInventory().setItem(4,
					new ItemBuilder(Material.WATCH).name("§3Teleporter §7(Rechtsklick)").build());

			for (Player specator : Var.SPECTATOR)
				JumpLeage.getInstance().getScoreboardmanager().setScoreboardSpectator(specator);

			Game.teleportPlayerToRandomPlayer(player);
			for (Player ingame : Var.INGAME) {
				ingame.hidePlayer(player);
			}

		}

	}

}
