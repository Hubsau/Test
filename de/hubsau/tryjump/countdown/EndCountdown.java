package de.hubsau.tryjump.countdown;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.hubsau.tryjump.JumpLeage;
import de.hubsau.tryjump.util.Game;

public class EndCountdown extends CountDown {

	private boolean isRunning;
	private int seconds = 21;
	private int task;

	@Override
	public void start() {

		task = Bukkit.getScheduler().scheduleSyncRepeatingTask(JumpLeage.getInstance(), new Runnable() {

			@Override
			public void run() {

				switch (seconds) {
				case 21:
				case 16:
				case 11:
				case 6:
				case 5:
				case 4:
				case 3:
					Bukkit.broadcastMessage(JumpLeage.getInstance().getPrefix() + "§cDer Server statet in §b"
							+ (seconds - 1) + " §c Sekunden neu!");

					break;
				case 2:
					Bukkit.broadcastMessage(JumpLeage.getInstance().getPrefix() + "§cDer Server statet in §b"
							+ (seconds - 1) + " §cSekunde neu!");

					Game.setStatsToMySql();
					for (Player online : Bukkit.getOnlinePlayers())

						Game.sendToLobbyServer(online);


				default:
					break;
				}

				if (seconds == 0)
					Bukkit.getServer().shutdown();

				seconds--;

			}
		}, 0, 20);

	}

	@Override
	public void stop() {

		Bukkit.getScheduler().cancelTask(task);
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}
}
