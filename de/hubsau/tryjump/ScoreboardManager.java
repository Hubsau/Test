package de.hubsau.tryjump;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Team;

import de.hubsau.tryjump.api.ConfigAPI;
import de.hubsau.tryjump.gamestate.GameState;
import de.hubsau.tryjump.gamestate.GameStateManager;
import de.hubsau.tryjump.gamestate.IngameState;
import de.hubsau.tryjump.util.Game;
import de.hubsau.tryjump.util.UUIDFatcher;
import net.minecraft.server.v1_8_R3.IScoreboardCriteria;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardDisplayObjective;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardObjective;
import net.minecraft.server.v1_8_R3.PacketPlayOutScoreboardScore;
import net.minecraft.server.v1_8_R3.Scoreboard;
import net.minecraft.server.v1_8_R3.ScoreboardObjective;
import net.minecraft.server.v1_8_R3.ScoreboardScore;

public class ScoreboardManager {
	ScoreboardObjective obj;
	PacketPlayOutScoreboardScore scoreboardscorepoints;
	PacketPlayOutScoreboardScore scoreboardScorekiller;

	ScoreboardScore punkte;
	ScoreboardScore killerscore;

	private Location startcheck;
	private Location endcheck;

	int firstditance;

	public void sendPacket(Player player, Packet<?> packet) {

		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);

	}

	public void setScoreboardLobby(Player player) {

		// §b§llPLORAX.NET
		// §r§3§oTRYJUMP

		Scoreboard board = new Scoreboard();
		obj = board.registerObjective("§6PLORAX.§eNET", IScoreboardCriteria.b);
		obj.setDisplayName("§r     §6§lPLORAX.§e§lNET§r     ");

		PacketPlayOutScoreboardObjective remove = new PacketPlayOutScoreboardObjective(obj, 1);
		PacketPlayOutScoreboardObjective create = new PacketPlayOutScoreboardObjective(obj, 0);
		PacketPlayOutScoreboardDisplayObjective display = new PacketPlayOutScoreboardDisplayObjective(1, obj);

		ScoreboardScore score1 = new ScoreboardScore(board, obj, "§3§lOnline: §7§l" + Var.INGAME.size());

		ScoreboardScore online;
		if (Game.MINPLAYERS() - Var.INGAME.size() >= 0) {
			online = new ScoreboardScore(board, obj, "§3§lWarten auf: §7§l" + (Game.MINPLAYERS() - Var.INGAME.size()));
		} else
			online = new ScoreboardScore(board, obj, "§3§lWarten auf: §7§l" + 0);

		PacketPlayOutScoreboardScore scoreboardscore1 = new PacketPlayOutScoreboardScore(score1);
		PacketPlayOutScoreboardScore scorebaordonlinePlayers = new PacketPlayOutScoreboardScore(online);

		sendPacket(player, remove);

		sendPacket(player, create);
		sendPacket(player, display);

		sendPacket(player, scoreboardscore1);
		sendPacket(player, scorebaordonlinePlayers);

	}

	public void startIngameSheduler(Player player) {

		org.bukkit.scoreboard.Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective obj = board.registerNewObjective("scores", "dummy");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		
//		IngameState ingame = (IngameState)GameStateManager.getGameState();
		
		obj.setDisplayName("§r     §6§lPLORAX.§e§lNET§r     ");
		
		
		obj.getScore("§a").setScore(200);
		obj.getScore("§6§lFortrschritt in Prozent").setScore(199);
		obj.getScore("§a ").setScore(198);
//		obj.getScore("§aDas Spiel Dauert noch").setScore(198);
		
//		obj.getScore(" §aMinuten").setScore(197);
//		obj.getScore("§a   ").setScore(196);
//		Team team = board.registerNewTeam("time");
//		
//		team.addEntry(" §aMinuten");
//		team.setSuffix("§3§l"+ingame.getGamecoutdown().getSeconds());
//
//		new BukkitRunnable(
//				) {
//			
//			@Override
//			public void run() {
//				
//				team.addEntry(" §aMinuten");
//				team.setSuffix("§3§l"+ingame.getGamecoutdown().getSeconds());
//
//			}
//		}.runTaskLater(JumpLeage.getInstance(), 20);

		


		int amount = Var.CONFIGCFG.getInt("amount") + 1;
		Location firstcheck = ConfigAPI.getCfg("checks." + 1, Var.LOCATIONSCFG);
		Location lastcheck = ConfigAPI.getCfg("checks." + amount, Var.LOCATIONSCFG);

		for (int i = 0; i < Var.INGAME.size(); i++) {
			if (Var.INGAME.get(i).equals(player)) {
				obj.getScore("§7§n§l" + Var.INGAME.get(i).getName()).setScore(0);

			}
			if (!Var.INGAME.get(i).equals(player)) {
				

				obj.getScore("§7" + Var.INGAME.get(i).getName()).setScore(0);
			}

		}
		if (Var.CONFIGCFG.getString("side").equals("z")) {

			startcheck = new Location(firstcheck.getWorld(), player.getLocation().getX(), firstcheck.getY(),
					lastcheck.getZ());
			endcheck = new Location(lastcheck.getWorld(), player.getLocation().getX(), lastcheck.getY(),
					lastcheck.getZ());
			Var.STARTCHECK.put(player, startcheck);
			Var.ENDCHECK.put(player, endcheck);

		} else if (Var.CONFIGCFG.getString("side").equals("x")) {

			startcheck = new Location(firstcheck.getWorld(), firstcheck.getX(), firstcheck.getY(),
					player.getLocation().getZ());
			endcheck = new Location(lastcheck.getWorld(), lastcheck.getX(), lastcheck.getY(),
					player.getLocation().getZ());
			Var.STARTCHECK.put(player, startcheck);
			Var.ENDCHECK.put(player, endcheck);

		}

		player.setScoreboard(board);
		System.out.println("Endcheck" + player.getName() + "X:" + Var.ENDCHECK.get(player).getBlockX() + "y:"
				+ Var.ENDCHECK.get(player).getBlockY() + "z:" + Var.ENDCHECK.get(player).getBlockZ());

		// Location startchek = Var.STARTCHECK.get(i);
		Location endcheckk = Var.ENDCHECK.get(player);
		int distance = (int) player.getLocation().distance(endcheckk);
		Var.DISCANCE.put(player, distance);

		new BukkitRunnable() {

			@Override
			public void run() {

				// Location startchek = Var.STARTCHECK.get(i);

				for (int i = 0; i < Var.INGAME.size(); i++) {

					Player playerc = Var.INGAME.get(i);

					Location endcheckk = Var.ENDCHECK.get(playerc);
					int distance = (int) playerc.getLocation().distance(endcheckk);

					int procent = 100 - (distance * 100 / Var.DISCANCE.get(playerc));

					if (procent < 0) {
						procent = 0;
					}

					if (Var.INGAME.get(i).equals(player)) {
						if (procent >= 0) {
							obj.getScore("§7§n§l" + Var.INGAME.get(i).getName()).setScore(procent);

						}

						if (!Var.INGAME.get(i).equals(player)) {

							obj.getScore("§7" + Var.INGAME.get(i).getName()).setScore(procent);

						}

					}
				}

			}
		}.runTaskTimer(JumpLeage.getInstance(), 1, 10);
	}

	public void setScreboardDeatchMatch(Player player) {

		// §b§llPLORAX.NET
		// §r§3§oTRYJUMP

		Scoreboard board = new Scoreboard();
		obj = board.registerObjective("§7PLORAX.§8NET", IScoreboardCriteria.b);
		obj.setDisplayName("§r     §6§lPLORAX.§e§lNET§r     ");

		PacketPlayOutScoreboardObjective remove = new PacketPlayOutScoreboardObjective(obj, 1);
		PacketPlayOutScoreboardObjective create = new PacketPlayOutScoreboardObjective(obj, 0);
		PacketPlayOutScoreboardDisplayObjective display = new PacketPlayOutScoreboardDisplayObjective(1, obj);

		if (Var.KILLS.containsKey(UUIDFatcher.getUUID(player.getName()))) {

			int kills = Var.KILLS.get(UUIDFatcher.getUUID(player.getName()));

			killerscore = new ScoreboardScore(board, obj, "§8§l➥ §3" + kills);
		} else
			killerscore = new ScoreboardScore(board, obj, "§8§l➥  §3" + 0);

		ScoreboardScore score2 = new ScoreboardScore(board, obj, "§7§lDeine Kills:");

		PacketPlayOutScoreboardScore scoreboardscore2 = new PacketPlayOutScoreboardScore(score2);
		scoreboardScorekiller = new PacketPlayOutScoreboardScore(killerscore);

		sendPacket(player, remove);
		sendPacket(player, create);
		sendPacket(player, display);

		sendPacket(player, scoreboardscore2);
		sendPacket(player, scoreboardScorekiller);

	}

	public void setScreboardDeatch(Player player) {

		Scoreboard board = new Scoreboard();
		obj = board.registerObjective("§7PLORAX.§8NET", IScoreboardCriteria.b);
		obj.setDisplayName("§r     §6§lPLORAX.§e§lNET§r     ");

		PacketPlayOutScoreboardObjective remove = new PacketPlayOutScoreboardObjective(obj, 1);
		PacketPlayOutScoreboardObjective create = new PacketPlayOutScoreboardObjective(obj, 0);
		PacketPlayOutScoreboardDisplayObjective display = new PacketPlayOutScoreboardDisplayObjective(1, obj);

		ScoreboardScore score1 = new ScoreboardScore(board, obj, "§7§lDeine Kills:");

		int kills;

		if (Var.KILLS.containsKey(UUIDFatcher.getUUID(player.getName()))) {

			kills = Var.KILLS.get(UUIDFatcher.getUUID(player.getName()));
		} else
			kills = 0;

		ScoreboardScore killerscore = new ScoreboardScore(board, obj, "§8➥  §3" + kills);

		ScoreboardScore score4 = new ScoreboardScore(board, obj, "§7§lDu wurdest getötet von:");

		String killername;

		if (Var.KILLER.containsKey(player)) {
			Player killer = Var.KILLER.get(player);
			killername = killer.getDisplayName();

		} else
			killername = "NIEMANDEN";

		ScoreboardScore killer = new ScoreboardScore(board, obj, "§8➥  §3" + killername);

		score1.setScore(4);
		killerscore.setScore(3);
		score4.setScore(2);
		killer.setScore(1);

		PacketPlayOutScoreboardScore scoreboardscore1 = new PacketPlayOutScoreboardScore(score1);
		PacketPlayOutScoreboardScore scoreboardScorekills = new PacketPlayOutScoreboardScore(killerscore);
		PacketPlayOutScoreboardScore scoreboardscore3 = new PacketPlayOutScoreboardScore(score4);
		PacketPlayOutScoreboardScore scoreboardscorekiller = new PacketPlayOutScoreboardScore(killer);

		sendPacket(player, remove);
		sendPacket(player, create);
		sendPacket(player, display);
		sendPacket(player, scoreboardscore1); // Dein Kills
		sendPacket(player, scoreboardScorekills); // kills
		sendPacket(player, scoreboardscore3); // Du wurdest getötet von:
		sendPacket(player, scoreboardscorekiller); // killer

	}

	public void setScoreboardSpectator(Player player) {

		Scoreboard board = new Scoreboard();
		obj = board.registerObjective("§7PLORAX.§8NET", IScoreboardCriteria.b);
		obj.setDisplayName("§r     §6§lPLORAX.§e§lNET§r     ");

		PacketPlayOutScoreboardObjective remove = new PacketPlayOutScoreboardObjective(obj, 1);
		PacketPlayOutScoreboardObjective create = new PacketPlayOutScoreboardObjective(obj, 0);
		PacketPlayOutScoreboardDisplayObjective display = new PacketPlayOutScoreboardDisplayObjective(1, obj);

		ScoreboardScore score1 = new ScoreboardScore(board, obj, "§7§lIngame: §3§l" + Var.INGAME.size());

		ScoreboardScore deathscore = new ScoreboardScore(board, obj, "§7§lSpectator: §3§l" + Var.SPECTATOR.size());
		PacketPlayOutScoreboardScore scoreboardscore1 = new PacketPlayOutScoreboardScore(score1);
		PacketPlayOutScoreboardScore scorebaordDeathPlayers = new PacketPlayOutScoreboardScore(deathscore);

		sendPacket(player, remove);

		sendPacket(player, create);
		sendPacket(player, display);

		sendPacket(player, scoreboardscore1);
		sendPacket(player, scorebaordDeathPlayers);

	}

}
