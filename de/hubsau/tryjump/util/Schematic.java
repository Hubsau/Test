package de.hubsau.tryjump.util;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;

public class Schematic {

	public Schematic() {
	}

	public void loadSchematica(int id, Player player) throws Exception {

		Location location = player.getLocation();
		World world = player.getWorld();
		Vector vector = new Vector(location.getX(), location.getY(), location.getZ());
		File file = new File("plugins/WorldEdit/schematics/" + id + ".schematic");

		EditSession es = new EditSession(new BukkitWorld(world), 999999999);

		CuboidClipboard cc = CuboidClipboard.loadSchematic(file);

		cc.paste(es, vector, false);

	}

	public void loadSchematica(String path, Vector location) throws Exception {

		Vector vector = location;
		
		File file = new File("plugins/WorldEdit/schematics/" + path + ".schematic");

		EditSession es = new EditSession(new BukkitWorld(Bukkit.getWorld("world")), 999999999);

		CuboidClipboard cc = CuboidClipboard.loadSchematic(file);

		cc.paste(es, vector, false);

	}

}
