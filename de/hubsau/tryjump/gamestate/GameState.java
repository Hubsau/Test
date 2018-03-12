package de.hubsau.tryjump.gamestate;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.hubsau.tryjump.JumpLeage;
import de.hubsau.tryjump.Var;
import de.hubsau.tryjump.countdown.EndCountdown;
import de.hubsau.tryjump.mysql.MySQLStats;
import de.hubsau.tryjump.util.PlayerChatColor;
import de.hubsau.tryjump.util.UUIDFatcher;

public abstract class GameState {
	public static final int LOBBY_STATE = 0, INGAME_STATE = 1, FIGHT_STATE = 2, END_STATE = 3;

	public abstract void start();

	public abstract void end();

	public static void checkWinning() {
		String prefix = JumpLeage.getInstance().getPrefix();

		// if (GameStateManager.getGameState() instanceof EndFightState
		// || GameStateManager.getGameState() instanceof IngameState) {

		if (GameStateManager.getGameState() instanceof EndFightState) {

			if (Var.INGAME.size() <= 1) {
				if (!Var.INGAME.isEmpty() && Var.INGAME.get(0).isOnline()) {

					Player player = Var.INGAME.get(0);
					Bukkit.broadcastMessage("");
					Bukkit.broadcastMessage(prefix + "§2Der Spieler §e" + PlayerChatColor.getColorName(player)
							+ " §2hat das Spiel gewonnen");
					Bukkit.broadcastMessage("");
					player.sendTitle("§k12§r§2Du hast das Spiel gewonnen", "");

					MySQLStats.addWin(UUIDFatcher.getUUID(player.getName()));

					
					GameStateManager.getGameState().end();
					
					
				} else {
					GameStateManager.getGameState().end();

				}
			} else {
				if (Var.INGAME.size() < 0) {

					GameStateManager.getGameState().end();

				}

			}

		} else if (GameStateManager.getGameState() instanceof IngameState) {

			if (Var.INGAME.size() <= 1) {

				try {

					if (!Var.INGAME.isEmpty() && Var.INGAME.get(0).isOnline()) {
						Player player = Var.INGAME.get(0);

						player.sendMessage(
								prefix + "§aDa du der letze Spielenede Spieler bist stopt der Server in 10 Sekunden");
						EndCountdown coutdown = new EndCountdown();
						coutdown.setSeconds(10);
						coutdown.start();

					} else {
						EndCountdown coutdown = new EndCountdown();
						coutdown.setSeconds(3);
						coutdown.start();
					}
				} catch (Exception e) {
					Bukkit.getServer().reload();
				}

			}

		}

	}
}
