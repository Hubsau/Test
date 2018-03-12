package de.hubsau.tryjump.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import de.hubsau.tryjump.Var;
import de.hubsau.tryjump.gamestate.GameStateManager;
import de.hubsau.tryjump.gamestate.LobbyState;
import de.hubsau.tryjump.util.PlayerChatColor;

public class ChatListener implements Listener {

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {

		if (Var.INGAME.contains(event.getPlayer())) {

			if (GameStateManager.getGameState() instanceof LobbyState) {
				event.setFormat(PlayerChatColor.getColorName(event.getPlayer()) + "§8 » §7" + event.getMessage());

			} else
				event.setFormat("§e" + event.getPlayer().getName() + "§8 » §7" + event.getMessage());

		} else {
			if (Var.SPECTATOR.contains(event.getPlayer())) {
				event.setCancelled(true);
				for (Player spectator : Var.SPECTATOR) {

					spectator.sendMessage(
							"§7[§3Spectator§7] §8" + event.getPlayer().getName() + "§8 » §7" + event.getMessage());
				}
			}

		}

	}

}
