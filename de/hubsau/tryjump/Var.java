package de.hubsau.tryjump;

import java.io.File;

import java.util.*;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import de.hubsau.tryjump.listener.MoveTimer;

public class Var {
	
	

	public static File CONFIGFILE = new File(JumpLeage.getInstance().getDataFolder(), "config.yml");
	public static YamlConfiguration CONFIGCFG = YamlConfiguration.loadConfiguration(CONFIGFILE);

	public static File LOCATIONSFILE = new File(JumpLeage.getInstance().getDataFolder(), "locations.yml");
	public static YamlConfiguration LOCATIONSCFG = YamlConfiguration.loadConfiguration(LOCATIONSFILE);

	public static ArrayList<Player> INGAME = new ArrayList<>();
	public static ArrayList<Player> SPECTATOR = new ArrayList<>();
	public static ArrayList<UUID> DIE = new ArrayList<>(); //Wenn der Spieler das Ziel erreich hat!

	
	public static ArrayList<Player> SUCESSED = new ArrayList<>(); //Wenn der Spieler das Ziel erreich hat!
	public static Map<Player, String> INSETUP = new HashMap<>(); //Wenn der Spieler Locations mit dem item setzen will

	public static ArrayList<Block> CHECKEDBLOCKS = new ArrayList<>(); //Um abfragen zu k§nnen ob der Block unter den Spieler bereits ein Checkpoint war
	
	public static Map<Player, Location> LASTCHECK = new HashMap<>(); //Alle bl§cke
	
	
	public static Map<Player, Location> STARTCHECK = new HashMap<>(); //First check +
	public static Map<Player, Location> ENDCHECK = new HashMap<>(); //First check 


	public static Map<UUID, Integer> KILLS = new HashMap<>();
	public static Map<Player, Player> KILLER = new HashMap<>();
	public static Map<Player, Integer> DISCANCE = new HashMap<>();

	
	public static List<Player> INKAMERA = new ArrayList<>();




	public static boolean canMove = true;
	public static boolean ATTACK = false;

	// public static HashMap<Player, Integer> POINTS = new HashMap<>();

}
