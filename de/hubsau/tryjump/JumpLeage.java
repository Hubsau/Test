package de.hubsau.tryjump;

import java.awt.List;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.hubsau.tryjump.command.SetupCommand;
import de.hubsau.tryjump.command.StartCommand;
import de.hubsau.tryjump.gamestate.GameState;
import de.hubsau.tryjump.gamestate.GameStateManager;
import de.hubsau.tryjump.listener.BuildListener;
import de.hubsau.tryjump.listener.ChatListener;
import de.hubsau.tryjump.listener.DamageFoodListener;
import de.hubsau.tryjump.listener.DeathListener;
import de.hubsau.tryjump.listener.InteractListener;
import de.hubsau.tryjump.listener.JoinListener;
import de.hubsau.tryjump.listener.MoveTimer;
import de.hubsau.tryjump.listener.OtherListener;
import de.hubsau.tryjump.listener.QuitListener;
import de.hubsau.tryjump.listener.SpectatorListener;
import de.hubsau.tryjump.mysql.MySQL;
import de.hubsau.tryjump.mysql.MySQLStats;
import de.hubsau.tryjump.util.Game;
import net.md_5.bungee.api.ChatColor;

public class JumpLeage extends JavaPlugin {

	static JumpLeage instance;
	public String PREFIX;
	private ScoreboardManager scoreboardmanager;

	// public GameStateManager gameStateManager;

	@Override
	public void onEnable() {
		instance = this;
		setDefaultToConfig();

		new GameStateManager();

		scoreboardmanager = new ScoreboardManager();

		this.getCommand("setup").setExecutor(new SetupCommand());
		this.getCommand("start").setExecutor(new StartCommand());

		setCommandExecuter(this);
		registerListeners(this);

		new MySQL();

		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		Bukkit.getConsoleSender().sendMessage("§aDas Plugin wurde erfolgreich aktiviert");
		GameStateManager.setGameState(GameState.LOBBY_STATE);
		System.out.println(GameStateManager.getGameState());

		Game.crateSpecatorInv();

	}

	@Override
	public void onLoad() {
		Bukkit.getConsoleSender().sendMessage("TRYJUMP " + "§eDas Plugin wird geladen..");

	}

	private void setCommandExecuter(JumpLeage instance) {

		instance.getCommand("setup").setExecutor(new SetupCommand());
		instance.getCommand("start").setExecutor(new StartCommand());

	}

	private void registerListeners(JumpLeage instance) {
		Bukkit.getPluginManager().registerEvents(new DamageFoodListener(), this);
		Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
		Bukkit.getPluginManager().registerEvents(new QuitListener(), this);
		Bukkit.getPluginManager().registerEvents(new InteractListener(), this);
		Bukkit.getPluginManager().registerEvents(new DeathListener(), this);
		Bukkit.getPluginManager().registerEvents(new OtherListener(), this);
		Bukkit.getPluginManager().registerEvents(new BuildListener(), this);
		Bukkit.getPluginManager().registerEvents(new SpectatorListener(), this);
		Bukkit.getPluginManager().registerEvents(new ChatListener(), this);

	}

	@Override
	public void onDisable() {

	}

	public static JumpLeage getInstance() {
		return instance;
	}

	public ScoreboardManager getScoreboardmanager() {
		return scoreboardmanager;
	}

	private void setDefaultToConfig() {

		/* The Data/Config file */

		File configfile = new File(this.getDataFolder(), "config.yml");
		YamlConfiguration configcfg = YamlConfiguration.loadConfiguration(configfile);

		File locationsfile = new File(this.getDataFolder(), "locations.yml");
		YamlConfiguration locationscfg = YamlConfiguration.loadConfiguration(locationsfile);

		File mysqlfile = new File(this.getDataFolder(), "mysql.yml");
		YamlConfiguration mysql = YamlConfiguration.loadConfiguration(mysqlfile);
		if (!configfile.exists()) {
			try {

				configfile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		if (!locationsfile.exists()) {
			try {
				locationsfile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (!mysqlfile.exists()) {
			try {
				mysqlfile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		configcfg.options().header("Hier werden alle wichtige Daten gespeichert");
		configcfg.addDefault("prefix", "&4&lTRY&eJUMP ");
		configcfg.addDefault("maxplayers", 10);
		configcfg.addDefault("side", "");

		configcfg.addDefault("minplayers", 2);
		configcfg.addDefault("amount", 0);

		configcfg.options().copyDefaults(true);
		configcfg.options().copyHeader(true);

		/* The Locations file */
		locationscfg.options().header("Hier werden alle wichtige Locations gespeichert");
		locationscfg.options().copyHeader(true);

		mysql.addDefault("Host", "localhost");
		mysql.addDefault("Username", "username");
		mysql.addDefault("Password", "password");
		mysql.addDefault("Database", "database");
		mysql.addDefault("Port", Integer.valueOf(3306));
		mysql.options().copyDefaults(true);

		try {
			configcfg.save(configfile);
			locationscfg.save(locationsfile);
			mysql.save(mysqlfile);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public String getPrefix() {
		File configfile = new File(JumpLeage.getInstance().getDataFolder(), "config.yml");
		YamlConfiguration configcfg = YamlConfiguration.loadConfiguration(configfile);

		PREFIX = ChatColor.translateAlternateColorCodes('&', configcfg.getString("prefix"));

		return PREFIX + "§8 ▌ ";

	}

	public String getPrefix2() {
		File configfile = new File(JumpLeage.getInstance().getDataFolder(), "config.yml");
		YamlConfiguration configcfg = YamlConfiguration.loadConfiguration(configfile);

		PREFIX = ChatColor.translateAlternateColorCodes('&', configcfg.getString("prefix"));

		return PREFIX + "§8 ";

	}

}
