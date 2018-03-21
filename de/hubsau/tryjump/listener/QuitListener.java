package de.hubsau.tryjump.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import de.hubsau.tryjump.JumpLeage;
import de.hubsau.tryjump.Var;
import de.hubsau.tryjump.gamestate.GameState;
import de.hubsau.tryjump.gamestate.GameStateManager;
import de.hubsau.tryjump.gamestate.IngameState;
import de.hubsau.tryjump.gamestate.LobbyState;
import de.hubsau.tryjump.util.Game;
import de.hubsau.tryjump.util.PlayerChatColor;

public class QuitListener implements Listener {

	String prefix = JumpLeage.getInstance().getPrefix();

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {

		Player player = event.getPlayer();

		event.setQuitMessage(null);
		if (Var.SPECTATOR.contains(player)) {

			
			Var.SPECTATOR.remove(player);

		}
		if (Var.INGAME.contains(player)) {
			Var.INGAME.remove(player);

			if (GameStateManager.getGameState() instanceof LobbyState) {

				LobbyState ls = (LobbyState) GameStateManager.getGameState();

				Bukkit.broadcastMessage("§4[§c-§4] §eDer Spieler §b" + PlayerChatColor.getColorName(player)
						+ " §ehat den Server verlassen" + " §c[§6" + Var.INGAME.size() + " §c/§6" + Game.MAXPLAYER()
						+ "§c]");
				for (Player ingame : Var.INGAME)
					JumpLeage.getInstance().getScoreboardmanager().setScoreboardLobby(ingame);

				if (Var.INGAME.size() < Game.MINPLAYERS()) {
					if (ls.getCountdown().isRunning()) {
						ls.getCountdown().stop();
						ls.getCountdown().waitForPlayers();

					}
				}

			} else if (GameStateManager.getGameState() instanceof IngameState) {
				Var.INGAME.remove(player);
				Bukkit.broadcastMessage(prefix + "§eDer Spieler §b" + PlayerChatColor.getColorName(player)
						+ " §ehat das Spiel verlassen. §aEs Spielen noch §6" + Var.INGAME.size() + " §aSpieler");
				GameState.checkWinning();
			} else {
				GameState.checkWinning();

			}
		}

		Game.setStatsToMySql(player);

	}

}
