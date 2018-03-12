package de.hubsau.tryjump.countdown;

import org.bukkit.Bukkit;

import de.hubsau.tryjump.JumpLeage;
import de.hubsau.tryjump.gamestate.GameStateManager;

public class Gamecoutdown extends CountDown {

	int task;
	int seconds = 1800;
	boolean running = false;

	@Override
	public void start() {

		running = true;

		task = Bukkit.getScheduler().scheduleSyncRepeatingTask(JumpLeage.getInstance(), new Runnable() {

			@Override
			public void run() {

				if (seconds == 1200) {

					Bukkit.broadcastMessage(
							JumpLeage.getInstance().getPrefix() + "§cDas Spiel endet in §620§c Minuten");

				}

				if (seconds == 600) {

					Bukkit.broadcastMessage(
							JumpLeage.getInstance().getPrefix() + "§cDas Spiel endet in §610§c Minuten");

				}

				if (seconds == 300) {

					Bukkit.broadcastMessage(JumpLeage.getInstance().getPrefix() + "§cDas Spiel endet in §65§c Minuten");

				}
				if (seconds == 240) {

					Bukkit.broadcastMessage(JumpLeage.getInstance().getPrefix() + "§cDas Spiel endet in §64§c Minuten");

				}
				if (seconds == 180) {

					Bukkit.broadcastMessage(JumpLeage.getInstance().getPrefix() + "§cDas Spiel endet in §63§c Minuten");

				}
				if (seconds == 120) {

					Bukkit.broadcastMessage(JumpLeage.getInstance().getPrefix() + "§cDas Spiel endet in §62§c Minuten");

				}
				if (seconds == 60) {

					Bukkit.broadcastMessage(
							JumpLeage.getInstance().getPrefix() + "§cDas Spiel endet in §6einer§c Minute");

				}

				if (seconds == 0) {

					stop();
					Bukkit.broadcastMessage(JumpLeage.getInstance().getPrefix() + "§cDas Spiel endet jetzt!");
					GameStateManager.getGameState().end();

				}

				seconds--;

			}
		}, 0, 20);

	}

	public int getSeconds() {
		return seconds;
	}

	@Override
	public void stop() {

		running = false;

		Bukkit.getScheduler().cancelTask(task);

	}

}
