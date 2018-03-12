package de.hubsau.tryjump.countdown;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import de.hubsau.tryjump.JumpLeage;
import de.hubsau.tryjump.Var;
import de.hubsau.tryjump.api.ActionBarAPI;
import de.hubsau.tryjump.gamestate.GameState;
import de.hubsau.tryjump.gamestate.GameStateManager;
import de.hubsau.tryjump.util.Game;

public class Lobbycountdown extends CountDown {
	int task;
	int idle;
	int seconds = 20;
	boolean running = false, ideling = false;
	String prefix = JumpLeage.getInstance().getPrefix();

	@Override
	public void start() {

		running = true;

		task = Bukkit.getScheduler().scheduleSyncRepeatingTask(JumpLeage.getInstance(), new Runnable() {

			@Override
			public void run() {

				for (Player online : Bukkit.getOnlinePlayers()) {
					if (seconds != 1) {
						ActionBarAPI.sendActionBar(online, "§7Das Spiel beginnt in §b§l" + seconds + "§r§7 Sekunden");
					} else
						ActionBarAPI.sendActionBar(online, "§7Das Spiel beginnt in §b§l" + seconds + "§r§7 Sekunde");

				}

				switch (seconds) {

				case 60:
				case 30:
				case 15:
				case 5:
				case 4:
					Bukkit.broadcastMessage(prefix + "§7Das Spiel beginnt in §f" + seconds + "§7 Sekunden");
					for (Player online : Bukkit.getOnlinePlayers())
						online.playSound(online.getLocation(), Sound.ENDERMAN_TELEPORT, 3.0F, 0.5F);

					break;
				case 3:
				case 2:
					Bukkit.broadcastMessage(prefix + "§7Das Spiel beginnt in §f" + seconds + "§7 Sekunden");
					for (Player online : Bukkit.getOnlinePlayers())
						online.playSound(online.getLocation(), Sound.ITEM_BREAK, 3.0F, 0.5F);

					break;
				case 1:
					Bukkit.broadcastMessage(prefix + "§7Das Spiel beginnt in §f" + seconds + "§7 Sekunde");
					for (Player online : Bukkit.getOnlinePlayers())
						online.playSound(online.getLocation(), Sound.NOTE_PLING, 3.0F, 0.5F);

					break;
				case 0:
					GameStateManager.setGameState(GameState.INGAME_STATE);
					stop();

					break;
				default:
					break;
				}
				seconds--;

			}
		}, 0, 20);

	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	public int getSeconds() {
		return seconds;
	}

	@Override
	public void stop() {

		seconds = 30;
		Bukkit.getScheduler().cancelTask(task);
		running = false;
	}

	public void stopIdle() {

		Bukkit.getScheduler().cancelTask(idle);
		ideling = false;

	}

	public boolean isRunning() {
		return running;
	}

	public boolean isIdle() {

		return ideling;
	}

	public void waitForPlayers() {

		ideling = true;
		if (isRunning())
			stop();
		idle = Bukkit.getScheduler().scheduleSyncRepeatingTask(JumpLeage.getInstance(), new Runnable() {

			@Override
			public void run() {
				int waitngplayers = Game.MINPLAYERS() - Var.INGAME.size();
				for (Player online : Bukkit.getOnlinePlayers()) {

					if (waitngplayers > 1) {
						ActionBarAPI.sendActionBar(online, "§bZum Spielstart fehlen noch §6" + waitngplayers
								+ " §bvon maximal §e" + Game.MAXPLAYER() + "§b Spielern");
					} else
						ActionBarAPI.sendActionBar(online, "§bZum Spielstart fehlt noch §6" + "einer"
								+ " §bvon maximal §e" + Game.MAXPLAYER() + "§b Spielern");

				}

			}
		}, 0, 20);
	}

}
