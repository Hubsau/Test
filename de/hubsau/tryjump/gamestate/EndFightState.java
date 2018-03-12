package de.hubsau.tryjump.gamestate;

import org.bukkit.entity.Player;

import de.hubsau.tryjump.Var;
import de.hubsau.tryjump.countdown.EndCountdown;

public class EndFightState extends GameState {
	EndCountdown endcountdown;

	@Override
	public void start() {

		endcountdown = new EndCountdown();

		Var.ATTACK = true;
		for (Player ingame : Var.INGAME) {

		}

	}

	@Override
	public void end() {

		Var.ATTACK = false;
		if (!endcountdown.isRunning()) {
			endcountdown.start();
		}

	}

	public EndCountdown getEndcountdown() {
		return endcountdown;
	}

}
