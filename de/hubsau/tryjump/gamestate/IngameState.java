package de.hubsau.tryjump.gamestate;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.hubsau.tryjump.JumpLeage;
import de.hubsau.tryjump.Var;
import de.hubsau.tryjump.api.ConfigAPI;
import de.hubsau.tryjump.countdown.Gamecoutdown;
import de.hubsau.tryjump.countdown.ProtectionCountdown;
import de.hubsau.tryjump.countdown.WinnerCountdown;
import de.hubsau.tryjump.util.Game;

public class IngameState extends GameState {
	ProtectionCountdown protectionCountdown;
	WinnerCountdown winnerCountdown;
	private Gamecoutdown gamecoutdown;

	@Override
	public void start() {
		protectionCountdown = new ProtectionCountdown();
		protectionCountdown.start();
		winnerCountdown = new WinnerCountdown();
		gamecoutdown = new Gamecoutdown();
		gamecoutdown.start();
		Var.ATTACK = false;
	}

	@Override
	public void end() {
		for (int i = 1; i < Var.INGAME.size() + 1; i++) {
			Location loc = ConfigAPI.getCfgYP("spawn.endfight." + i, Var.LOCATIONSCFG);
			Player player = Var.INGAME.get(i - 1);
			player.teleport(loc);
			player.getInventory().clear();
		}
		for (Player specator : Var.SPECTATOR) {
			Game.teleportPlayerToRandomPlayer(specator);
		}
		Bukkit.broadcastMessage(JumpLeage.getInstance().getPrefix() + "§eDer letzte überlebende gewinnt das Endfight");
		for (Player ingame : Bukkit.getOnlinePlayers())
			JumpLeage.getInstance().getScoreboardmanager().setScreboardDeatchMatch(ingame);
	}

	public Gamecoutdown getGamecoutdown() {
		return gamecoutdown;
	}

	public ProtectionCountdown getProtectionCountdown() {
		return protectionCountdown;
	}

	public WinnerCountdown getWinnerCountdown() {
		return winnerCountdown;
	}
}