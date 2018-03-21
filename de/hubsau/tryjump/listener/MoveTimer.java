package de.hubsau.tryjump.listener;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

import de.hubsau.tryjump.JumpLeage;
import de.hubsau.tryjump.Var;
import de.hubsau.tryjump.api.ActionBarAPI;
import de.hubsau.tryjump.api.ConfigAPI;
import de.hubsau.tryjump.countdown.WinnerCountdown;
import de.hubsau.tryjump.gamestate.GameStateManager;
import de.hubsau.tryjump.gamestate.IngameState;
import de.hubsau.tryjump.util.PlayerChatColor;
import de.hubsau.tryjump.util.Schematic;

public class MoveTimer extends BukkitRunnable {

	WinnerCountdown winnercountdown;
	static Map<Player, Integer> lastcheck = new HashMap<>();

	public Player player;

	int module;
	int failed;

	public MoveTimer(Player player) {

		this.player = player;
		module = 0;
		failed = 0;

	}

	@Override
	public void run() {

		Location by = player.getLocation();
		Block under = player.getLocation().subtract(0, 1, 0).getBlock();
		int amount = Var.CONFIGCFG.getInt("amount");

		if (GameStateManager.getGameState() instanceof IngameState) {

			if (Var.INGAME.contains(player) && !Var.SUCESSED.contains(player)) {

				if (lastcheck.containsKey(player)) {
					int lastcheckid = lastcheck.get(player);

					String modulename = Var.LOCATIONSCFG.getString("checks." + lastcheckid + ".name");
					String moduledifficulty = Var.LOCATIONSCFG.getString("checks." + lastcheckid + ".difficulty");

					ActionBarAPI.sendActionBar(player, "§bName§7:§e§l " + modulename.toUpperCase()
							+ " §bSchwierigkeit§7:§e§l " + moduledifficulty.toUpperCase());

				}

				if (by.getBlock().getType().equals(Material.GOLD_PLATE)) {
					if (under.getType().equals(Material.GOLD_BLOCK)) {

						for (int i = 1; i < amount + 2; i++) {
							Location check = ConfigAPI.getCfg("checks." + i, Var.LOCATIONSCFG);

							//
							if (Var.CONFIGCFG.getString("side").equalsIgnoreCase("z")) {
								if (player.getLocation().getBlock().getY() == check.getBlock().getY()) {
									
									check(by, i);

								} 
							}else {
								if (Var.CONFIGCFG.getString("side").equalsIgnoreCase("x")) {

									if (player.getLocation().getBlock().getX() == check.getBlock().getX()) {

										check(by, i);
									}

								}
							}

								
							
						}
					}
					

							// } else {
							// if (Var.CONFIGCFG.getString("side").equalsIgnoreCase("x")) {
							//
							// if (player.getLocation().getBlock().getX() == check.getBlock().getX()) {
							//
							// if (!Var.CHECKEDBLOCKS.contains(player.getLocation().getBlock())) {
							//
							// if (module > 0)
							// player.sendMessage(
							// JumpLeage.getInstance().getPrefix() + "§6Checkpoint erreicht!");
							// module++;
							// failed = 0;
							//
							// try {
							// Var.LASTCHECK.put(player, by);
							//
							// if (!Var.CHECKEDBLOCKS.contains(player.getLocation().getBlock())) {
							// player.playSound(player.getLocation(), Sound.LEVEL_UP, 10, 10);
							//
							// Var.CHECKEDBLOCKS.add(by.getBlock());
							//
							// new Schematic().loadSchematica(i, player);
							// }
							// } catch (Exception e1) {
							//
							// if (!Var.SUCESSED.contains(player)) {
							// player.playSound(player.getLocation(), Sound.FIREWORK_LAUNCH, 3.0F,
							// 0.5F);
							// player.sendTitle("§6Du bist am Ziel angekommen", "§3");
							// Bukkit.broadcastMessage("");
							// Bukkit.broadcastMessage("");
							// Bukkit.broadcastMessage(JumpLeage.getInstance().getPrefix()
							//
							// + " §aDer Spieler " + PlayerChatColor.getColorName(player)
							// + " §ahat das Ziel errreicht!");
							// Bukkit.broadcastMessage("");
							// Bukkit.broadcastMessage("");
							//
							// Firework firework = player.getWorld().spawn(player.getLocation(),
							// Firework.class);
							//
							// FireworkMeta meta = firework.getFireworkMeta();
							// meta.addEffect(FireworkEffect.builder().with(Type.BALL)
							// .withColor(Color.GREEN).withFade(Color.RED).build());
							//
							// firework.setFireworkMeta(meta);
							//
							// Var.SUCESSED.add(player);
							// IngameState ingameState = (IngameState) GameStateManager
							// .getGameState();
							// if (!ingameState.getWinnerCountdown().isRunning()) {
							// ingameState.getWinnerCountdown().start();
							// }
							//
							// }
							// }
							// }
							// }
							//
							// }
							//
							// }

					
					return;

				}

			}

		}

	}
	private void check(Location by, int i) {
		if (!Var.CHECKEDBLOCKS.contains(player.getLocation().getBlock())) {
			if (module > 0)
				player.sendMessage(
						JumpLeage.getInstance().getPrefix() + "§6Checkpoint erreicht!");
			module++;
			failed = 0;

			try {
				Var.LASTCHECK.put(player, by);
				lastcheck.put(player, i);

				if (!Var.CHECKEDBLOCKS.contains(player.getLocation().getBlock())) {
					player.playSound(player.getLocation(), Sound.LEVEL_UP, 10, 10);

					Var.CHECKEDBLOCKS.add(by.getBlock());

					new Schematic().loadSchematica(i, player);
				}

			} catch (Exception e1) {

				if (!Var.SUCESSED.contains(player)) {
					// player.sendMessage("§6Du bist am Ziel angekommen");
					player.playSound(player.getLocation(), Sound.FIREWORK_LAUNCH, 3.0F, 0.5F);
					player.sendTitle("§6Du bist am Ziel angekommen", "§3");
					Bukkit.broadcastMessage("");
					Bukkit.broadcastMessage("");
					Bukkit.broadcastMessage(JumpLeage.getInstance().getPrefix()
							+ " §aDer Spieler " + PlayerChatColor.getColorName(player)
							+ " §ahat das Ziel errreicht!");
					Bukkit.broadcastMessage("");
					Bukkit.broadcastMessage("");

					Firework firework = player.getWorld().spawn(player.getLocation(),
							Firework.class);

					FireworkMeta meta = firework.getFireworkMeta();
					meta.addEffect(FireworkEffect.builder().with(Type.BALL)
							.withColor(Color.GREEN).withFade(Color.RED).build());

					firework.setFireworkMeta(meta);

					Var.SUCESSED.add(player);
					IngameState ingameState = (IngameState) GameStateManager.getGameState();
					if (!ingameState.getWinnerCountdown().isRunning()) {
						ingameState.getWinnerCountdown().start();
					}

				}
			}
		}
	}

	public int getFailed() {
		return failed;
	}

	public void setFailed(int failed) {
		this.failed = failed;
	}

}