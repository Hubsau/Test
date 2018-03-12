package de.hubsau.tryjump.gamestate;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.hubsau.tryjump.JumpLeage;
import de.hubsau.tryjump.Var;
import de.hubsau.tryjump.api.ConfigAPI;
import de.hubsau.tryjump.countdown.Gamecoutdown;
import de.hubsau.tryjump.countdown.Lobbycountdown;

public class LobbyState extends GameState {

	private Lobbycountdown countdown;

	String prefix = JumpLeage.getInstance().getPrefix();

	@Override
	public void start() {

		Var.ATTACK = false;

		countdown = new Lobbycountdown();

	}

	@Override
	public void end() {

		Location locyp = ConfigAPI.getCfgYP("spawn." + 1, Var.LOCATIONSCFG);

		for (int i = 1; i < Var.INGAME.size() + 1; i++) {

			Location loc = ConfigAPI.getCfgYP("spawn." + i, Var.LOCATIONSCFG);

			Location newloc = new Location(loc.getWorld(), loc.getX(), loc.getY(), loc.getZ(), locyp.getYaw(),
					locyp.getPitch());

			Player player = Var.INGAME.get(i - 1);
			player.teleport(newloc);

		}
	}



	public Lobbycountdown getCountdown() {
		return countdown;
	}
}
