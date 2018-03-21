package de.hubsau.tryjump.util;

import org.bukkit.entity.Player;

import ru.tehkode.permissions.bukkit.PermissionsEx;

public class PlayerChatColor {

	public static String getColorName(Player player) {

		if (PermissionsEx.getUser(player).inGroup("CoOwner") || PermissionsEx.getUser(player).inGroup("Admin")) {

			return "§4" + player.getName();

		}
		if (PermissionsEx.getUser(player).inGroup("SrDev") || PermissionsEx.getUser(player).inGroup("Dev")
				|| PermissionsEx.getUser(player).inGroup("TestDev")) {

			return "§b" + player.getName();

		}
		if (PermissionsEx.getUser(player).inGroup("Content")) {

			return "§3" + player.getName();



		}

		if (PermissionsEx.getUser(player).inGroup("Designer")) {

			return "§8" + player.getName();

		}

		if (PermissionsEx.getUser(player).inGroup("Partner")) {

			return "§e" + player.getName();

		}

		if (PermissionsEx.getUser(player).inGroup("YT")) {

			return "§d" + player.getName();

		}
		if (PermissionsEx.getUser(player).inGroup("Premiump")) {

			return "§6" + player.getName();

		}
		if (PermissionsEx.getUser(player).inGroup("Vetaran")) {

			return "§2" + player.getName();

		}

		if (PermissionsEx.getUser(player).inGroup("Elite")) {

			return "§1" + player.getName();

		}

		if (PermissionsEx.getUser(player).inGroup("StanSp")) {

			return "§8" + player.getName();

		}

		if (PermissionsEx.getUser(player).inGroup("Mod") || PermissionsEx.getUser(player).inGroup("SrMod")
				|| PermissionsEx.getUser(player).inGroup("TestMod")) {
			return "§c" + player.getName();

		}

		if (PermissionsEx.getUser(player).inGroup("SrBuild") || PermissionsEx.getUser(player).inGroup("Build")
				|| PermissionsEx.getUser(player).inGroup("TestBuild")) {

			return "§a" + player.getName();

		}

		if (PermissionsEx.getUser(player).inGroup("Sup") || PermissionsEx.getUser(player).inGroup("TestSup")) {

			return "§9" + player.getName();

		} else
			return "§7" + player.getName();

	}

}
