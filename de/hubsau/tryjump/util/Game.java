package de.hubsau.tryjump.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import de.dytanic.cloudnet.api.CloudAPI;
import de.dytanic.cloudnet.lib.server.info.ServerInfo;
import de.hubsau.tryjump.JumpLeage;
import de.hubsau.tryjump.Var;
import de.hubsau.tryjump.api.ConfigAPI;
import de.hubsau.tryjump.mysql.MySQLStats;
import net.minecraft.server.v1_8_R3.PacketPlayOutCamera;

public class Game {

	public static Inventory inventory;

	public static int MAXPLAYER() {
		
		return Var.CONFIGCFG.getInt("maxplayers");
	}

	public static int MINPLAYERS() {
		return Var.CONFIGCFG.getInt("minplayers");
	}

	public static void setLobbyItems(Player player) {
		
		player.getInventory().clear();
		player.setGameMode(GameMode.ADVENTURE);
		player.setHealth(20);
		player.setFoodLevel(20);
		player.getInventory().clear();

		Location mainspawn = ConfigAPI.getCfg("spawn.hauptspawn", Var.LOCATIONSCFG);
		player.teleport(mainspawn);
		player.setDisplayName("§7"+player.getName());
	}

	public static void setJumpAndRun(Player player) {
		player.setGameMode(GameMode.SURVIVAL);
		player.getInventory().clear();

		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
		SkullMeta meta = (SkullMeta) skull.getItemMeta();
		meta.setOwner("MHF_ArrowLeft");
		meta.setDisplayName("§b§lZurück zum checkpoint");
		skull.setItemMeta(meta);
		player.getInventory().setItem(4, skull);
		player.setLevel(0);
		player.setDisplayName("§7"+player.getName());


	}

	public static void teleportPlayerToRandomPlayer(Player player) {
		Random random = new Random();
		if (!Var.INGAME.isEmpty()) {

			Player target = Var.INGAME.get(random.nextInt(Var.INGAME.size()));
			player.teleport(target);

		} else {
			int randomlocation = random.nextInt(5);
			Location loc = ConfigAPI.getCfgYP("spawn.endfight." + randomlocation, Var.LOCATIONSCFG);
			player.teleport(loc);

		}

	}

	public static void setCamera(Player player, Player target) {

		if (Var.SPECTATOR.contains(player)) {

			PacketPlayOutCamera packet = new PacketPlayOutCamera();
			packet.a = target.getEntityId();
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);

		}
	}

	public int getListOfLobbyServer() {
		List<Integer> list = new ArrayList<>();
		int amount = 0;
		for (ServerInfo serverInfo : CloudAPI.getInstance().getServers("Lobby")) {
			amount++;

		}

		return amount;
	}

	public static void sendToLobbyServer(Player player) {

		Random random = new Random();

		int amount = new Game().getListOfLobbyServer();

		System.out.println(amount);
		System.out.println("Lobby-" + random.nextInt(amount + 1));
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Connect");
		out.writeUTF("Lobby-" + random.nextInt(amount + 1));
		player.sendPluginMessage(JumpLeage.getInstance(), "BungeeCord", out.toByteArray());

	}

	public static void removeCamera(Player player) {

		setCamera(player, player);
	}

	public static void setStatsToMySql() {
		for (UUID hasKilledSomeoneUUID : Var.KILLS.keySet()) {

			MySQLStats.addKills(hasKilledSomeoneUUID, Var.KILLS.get(hasKilledSomeoneUUID));

			Var.KILLS.remove(hasKilledSomeoneUUID);

		}

		for (UUID uuiDie : Var.DIE) {
			MySQLStats.addDeaths(uuiDie, 1);
			MySQLStats.addLoses(uuiDie);
			Var.DIE.remove(uuiDie);

		}
	}

	public static void setStatsToMySql(Player player) {

		if (Var.KILLS.containsKey(UUIDFatcher.getUUID(player.getName()))) {

			MySQLStats.addKills(UUIDFatcher.getUUID(player.getName()),
					Var.KILLS.get(UUIDFatcher.getUUID(player.getName())));

			Var.KILLS.remove(UUIDFatcher.getUUID(player.getName()));
		}

		if (Var.DIE.contains(UUIDFatcher.getUUID(player.getName()))) {
			MySQLStats.addDeaths(UUIDFatcher.getUUID(player.getName()), 1);
			MySQLStats.addLoses(UUIDFatcher.getUUID(player.getName()));

			Var.DIE.remove(UUIDFatcher.getUUID(player.getName()));
		}

	}

	public static void crateSpecatorInv() {

		inventory = Bukkit.createInventory(null, 9 * 6, "§eTeleporter");

	}

	public static Inventory getInventory() {
		return inventory;
	}

}
