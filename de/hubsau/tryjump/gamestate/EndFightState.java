package de.hubsau.tryjump.gamestate;

import de.hubsau.tryjump.util.DeathCounter;
import org.bukkit.entity.Player;

import de.hubsau.tryjump.Var;
import de.hubsau.tryjump.countdown.EndCountdown;

import java.util.HashMap;
import java.util.Map;

public class EndFightState extends GameState {
	EndCountdown endcountdown;
    Map<Player, DeathCounter> deathcouter = new HashMap<>();
    DeathCounter deathCounter;


    @Override
	public void start() {


        for(Player ingame: Var.INGAME) {
            deathCounter = new DeathCounter(ingame);
            deathCounter.start();
            deathcouter.put(ingame, deathCounter);
        }

		endcountdown = new EndCountdown();

		Var.ATTACK = true;
		for (Player ingame : Var.INGAME) {



		}

	}
    public DeathCounter getDeathCoutDown(Player player){

        return  deathcouter.get(player);

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
