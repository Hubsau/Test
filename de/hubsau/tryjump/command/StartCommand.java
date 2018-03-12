package de.hubsau.tryjump.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.hubsau.tryjump.JumpLeage;
import de.hubsau.tryjump.gamestate.GameStateManager;
import de.hubsau.tryjump.gamestate.LobbyState;

public class StartCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {

		Player player = (Player) sender;
		String prefix = JumpLeage.getInstance().getPrefix();

		if (cmd.getName().equalsIgnoreCase("start") && player.hasPermission("tryjump.forcestart")) {

			if (GameStateManager.getGameState() instanceof LobbyState) {

				LobbyState lobbyState = (LobbyState) GameStateManager.getGameState();
				int seconds = lobbyState.getCountdown().getSeconds();
				if (seconds >= 5) {
					if (lobbyState.getCountdown().isRunning()) {
						lobbyState.getCountdown().setSeconds(5);
						player.sendMessage(prefix + "§2Du hast den Lobbycountdown beschläunigt");
					} else {
						player.sendMessage(prefix
								+ "§2Du hast die Zählzeit des Lobbycountowns für diese Runde auf 5 Sekunden gesetzt");
						lobbyState.getCountdown().setSeconds(5);

					}
				} else
					player.sendMessage(
							prefix + "§cDer Countdown kann nur beschläunigt werden wenn er über 5 Sekunden ist");

			} else {
				player.sendMessage(prefix
						+ "§cDas Spiel hat bereits begonnen du kannst deswegen den Countdown auch nicht mehr beschläunigen");
			}

		}

		return false;
	}

}
