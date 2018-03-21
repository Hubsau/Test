package de.hubsau.tryjump.countdown;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.hubsau.tryjump.JumpLeage;
import de.hubsau.tryjump.Var;
import de.hubsau.tryjump.gamestate.GameState;
import de.hubsau.tryjump.gamestate.GameStateManager;

public class WinnerCountdown extends CountDown {

	private boolean isRunning;
	private int seconds = 30;
	private final String prefix = JumpLeage.getInstance().getPrefix();

	@Override
	public void start() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(JumpLeage.getInstance(), new Runnable() {

			@Override
			public void run() {
				switch (seconds) {
				case 30:
					Bukkit.broadcastMessage(prefix + "§aIn 30 Sekunden beginnt das Endfight, bereitet euch vor!");
					break;

				case 20:
				case 10:
				case 5:
				case 3:
				case 2:
					Bukkit.broadcastMessage(prefix + "§cAchtung die Kämpfe beginnen in §6" + seconds + "§c Sekunden");

					break;
				case 1:
					Bukkit.broadcastMessage(prefix + "§cAchtung die Kämpfe beginnen in §6" + "einer" + "§c Sekunden");
					break;
				case 0:
				default:
					break;
				}

				if (seconds == 0) {
					Var.ATTACK = true;
					stop();
					GameStateManager.setGameState(GameState.FIGHT_STATE);
					for (Player player : Var.INGAME) {
						player.setNoDamageTicks(20 * 5);
					}

				}
				seconds--;

			}
		}, 0, 20);

	}

	@Override
	public void stop() {

	}

	public boolean isRunning() {
		return isRunning;
	}

}
