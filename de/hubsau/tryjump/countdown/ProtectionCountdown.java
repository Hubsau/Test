package de.hubsau.tryjump.countdown;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import de.hubsau.tryjump.JumpLeage;
import de.hubsau.tryjump.Var;
import de.hubsau.tryjump.api.ConfigAPI;
import de.hubsau.tryjump.listener.MoveTimer;
import de.hubsau.tryjump.util.Game;

public class ProtectionCountdown extends CountDown {
	int task;
	int seconds = 5;
	boolean running = false;
	String prefix = JumpLeage.getInstance().getPrefix();
	public MoveTimer moveTimer;
	public  Map<Player, MoveTimer> timer = new HashMap<>();


	@Override
	public void start() {
		running = true;
		Var.canMove = false;
		


		task = Bukkit.getScheduler().scheduleSyncRepeatingTask(JumpLeage.getInstance(), new Runnable() {

			@Override
			public void run() {

				for (int i = 1; i < Var.INGAME.size() + 1; i++) {

					Location loc = ConfigAPI.getCfgYP("spawn." + i, Var.LOCATIONSCFG);

					Player player = Var.INGAME.get(i - 1);
					Location newloc = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(),
							player.getLocation().getYaw(), player.getLocation().getPitch());
					player.teleport(newloc);
					JumpLeage.getInstance().getScoreboardmanager().startIngameSheduler(player);
				


				}

				switch (seconds) {
				case 5:
					for (Player online : Bukkit.getOnlinePlayers()) {
						online.sendTitle("§4" + seconds, "");
					}

					break;
				case 4:
					for (Player online : Bukkit.getOnlinePlayers()) {
						online.sendTitle("§c" + seconds, "");
					}

					break;
				case 3:
					for (Player online : Bukkit.getOnlinePlayers()) {
						online.sendTitle("§6" + seconds, "");
					}

					break;
				case 2:
					for (Player online : Bukkit.getOnlinePlayers()) {
						online.sendTitle("§2" + seconds, "");
					}

					break;
				case 1:
					for (Player online : Bukkit.getOnlinePlayers()) {
						online.sendTitle("§a" + seconds, "");
					}

					break;

				default:
					break;
				}

				if (seconds == 0) {
					for (int i = 1; i < Var.INGAME.size() + 1; i++) {

						Location loc = ConfigAPI.getCfgYP("spawn." + i, Var.LOCATIONSCFG);

						Player player = Var.INGAME.get(i - 1);
						Location newloc = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(),
								loc.getPitch());
						player.teleport(newloc);

					}

					stop();

					for (Player ingame : Var.INGAME) {
						Game.setJumpAndRun(ingame);
					}

				}

				seconds--;

			}
		}, 0, 20);

	}

	@Override
	public void stop() {

		seconds = 5;
		Var.canMove = true;

		Bukkit.getScheduler().cancelTask(task);
		for (Player ingame : Var.INGAME) {
			moveTimer = new MoveTimer(ingame);
			moveTimer.runTaskTimer(JumpLeage.getInstance(), 4, 4);
			this.timer.put(ingame, moveTimer);
		}

		running = false;
	}
	
	public Map<Player, MoveTimer> getTimer() {
		return timer;
	}
	public MoveTimer getMoveTimer(Player player) {
	
		if(this.timer.containsKey(player)) {
			return this.timer.get(player);
		}
		return null;
	}
	



	public boolean isRunning() {
		return running;
	}

}
