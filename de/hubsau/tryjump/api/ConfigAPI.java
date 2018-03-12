package de.hubsau.tryjump.api;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigAPI {
	public static void createCfgINT(Location loc, String path, File file, YamlConfiguration cfg) {
		cfg.set(path + ".World", loc.getWorld().getName());
		cfg.set(path + ".X", loc.getBlockX());
		cfg.set(path + ".Y", loc.getBlockY());
		cfg.set(path + ".Z", loc.getBlockZ());

		try {
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public static void createCfg(Location loc, String path, File file, YamlConfiguration cfg) {
		cfg.set(path + ".World", loc.getWorld().getName());
		cfg.set(path + ".X", loc.getX());
		cfg.set(path + ".Y", loc.getY());
		cfg.set(path + ".Z", loc.getZ());

		try {
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	public static void createCfgYP(Location loc, String path, File file, YamlConfiguration cfg) {
		cfg.set(path + ".World", loc.getWorld().getName());
		cfg.set(path + ".X", loc.getX());
		cfg.set(path + ".Y", loc.getY());
		cfg.set(path + ".Z", loc.getZ());
		cfg.set(path + ".Yaw", loc.getYaw());
		cfg.set(path + ".Pitch", loc.getPitch());
		try {
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static Location getCfg(String path, YamlConfiguration cfg) {
		World w = Bukkit.getWorld(cfg.getString(path + ".World"));
		int x = cfg.getInt(path + ".X");
		int y = cfg.getInt(path + ".Y");
		int z = cfg.getInt(path + ".Z");
		return new Location(w, x, y, z);

	}

	public static Location getCfgYP(String path, YamlConfiguration cfg) {
		World w = Bukkit.getWorld(cfg.getString(path + ".World"));
		double x = cfg.getDouble(path + ".X");
		double y = cfg.getDouble(path + ".Y");
		double z = cfg.getDouble(path + ".Z");
		float yaw = (float) cfg.getDouble(path + ".Yaw");
		float pitch = (float) cfg.getDouble(path + ".Pitch");
		return new Location(w, x, y, z, yaw, pitch);

	}
}
