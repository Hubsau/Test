package de.hubsau.tryjump.mysql;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import de.hubsau.tryjump.JumpLeage;

public class MySQL {

	static String host = "";
	static String username = "";
	static String password = "";
	static String database = "";
	static int port;
	static Connection connection;

	public MySQL() {

		File mysqlfile = new File(JumpLeage.getInstance().getDataFolder(), "mysql.yml");
		YamlConfiguration cfg = YamlConfiguration.loadConfiguration(mysqlfile);
		this.host = cfg.getString("Host");
		this.username = cfg.getString("Username");
		this.password = cfg.getString("Password");
		this.database = cfg.getString("Database");
		this.port = cfg.getInt("Port");
		connect();
	}

	public static void connect() {
		if (!isConnected()) {
			try {
				connection = DriverManager.getConnection(
						"jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true", username,
						password);
				Bukkit.getConsoleSender()
						.sendMessage(JumpLeage.getInstance().getPrefix() + "Verbindung erfolgreich aufgebaut!");

				MySQLStats.createTable();

			} catch (SQLException var2) {
				Bukkit.getConsoleSender().sendMessage(JumpLeage.getInstance().getPrefix()
						+ "Verbindung erfolgreich geschlossen! Reason: NO CONNECTION!");
			}
		}
	}

	public static void disconnect() {
		if (isConnected()) {
			try {
				connection.close();
				Bukkit.getConsoleSender()
						.sendMessage(JumpLeage.getInstance().getPrefix() + "Verbindung erfolgreich geschlossen!");
			} catch (SQLException localSQLException) {
			}
		}
	}

	public static boolean isConnected() {
		return connection != null;
	}

	public static Connection getConnection() {
		return connection;
	}

	public static void update(String qry) {
		try {
			PreparedStatement ps = connection.prepareStatement(qry);
			ps.executeUpdate();
			ps.close();
		} catch (SQLException localSQLException) {
		}
	}

	public ResultSet getResult(String qry) {
		try {
			PreparedStatement var3 = connection.prepareStatement(qry);
			return var3.executeQuery();
		} catch (SQLException var31) {
		}
		return null;
	}

	public static PreparedStatement getStatement(String sql) {
		if (isConnected()) {
			PreparedStatement ps;
			try {
				ps = connection.prepareStatement(sql);
				return ps;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
