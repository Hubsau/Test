package de.hubsau.tryjump.util;
/*Class erstellt von Hubsau


20:12 2018 18.03.2018
Wochentag : Sonntag


*/


import de.hubsau.tryjump.JumpLeage;
import de.hubsau.tryjump.Var;
import de.hubsau.tryjump.api.ActionBarAPI;
import de.hubsau.tryjump.gamestate.GameState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


public class DeathCounter  {
    Player player;



    int deaths;
    public DeathCounter(Player player){

this.player = player;
        deaths = 0;




    }

    public void addDeath(Player death, Player killer, String prefix){

        deaths++;
        if(deaths == 3){
            Var.KILLER.put(death, killer);
            Var.DIE.add(UUIDFatcher.getUUID(death.getName()));
            JumpLeage.getInstance().getScoreboardmanager().setScreboardDeatch(death);
            Var.INGAME.remove(death);
            GameState.checkWinning();
            Var.SPECTATOR.add(death);
            for (Player ingame : Var.INGAME)
                ingame.hidePlayer(death);
            death.setPlayerListName("§7Specatator: §8" + death.getName());
        }else{
            JumpLeage.getInstance().getScoreboardmanager().setScreboardDeatchMatch(killer);
            Bukkit.broadcastMessage(prefix + "§aDer Spieler §6" + death.getDisplayName() + " §awurde von §3"
                + killer.getDisplayName() + " §agetötet! §c"+getDeaths()+ "§4❤ §7/ §c3");

        }
    }

    public void addDeath(Player death, String prefix){

        deaths++;
        if(deaths == 3){
            Var.INGAME.remove(death);
            GameState.checkWinning();
            Var.SPECTATOR.add(death);
            Var.DIE.add(UUIDFatcher.getUUID(death.getName()));
            Bukkit.broadcastMessage(prefix + "§aDer Spieler §6" + death.getDisplayName() + " §aist gestorben! §c"+getDeaths()+ "§4❤ §7/ §c3");
            JumpLeage.getInstance().getScoreboardmanager().setScreboardDeatch(death);


        }else{
            Bukkit.broadcastMessage(prefix + "§aDer Spieler §6" + death.getDisplayName() + " §aist gestorben! §c"+getDeaths()+ "§4❤ §7/ §c3");
            JumpLeage.getInstance().getScoreboardmanager().setScreboardDeatchMatch(death);

        }

    }



    public void start(){

        new BukkitRunnable() {

            @Override
            public void run() {


                if(!Var.DIE.contains(UUIDFatcher.getUUID(player.getName()))) {
                    ActionBarAPI.sendActionBar(player, "§3Deine Leben : §c"+getDeaths()+ "§4❤ §7/ §c3");
                }else{
                    ActionBarAPI.sendActionBar(player, "§4$LDu bist gerstorben! §aEs leben noch §7"+ Var.INGAME.size()+ "§a Spieler");
                }




            }
        }.runTaskTimer(JumpLeage.getInstance(), 1, 10);

    }



    public int getDeaths() {
        return deaths;
    }
}
