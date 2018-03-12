package de.hubsau.tryjump.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

import org.bukkit.entity.Player;

import de.hubsau.tryjump.util.UUIDFatcher;

public class MySQLStats {

	public static void createTable() {

		try {
			PreparedStatement ps = MySQL.getStatement(
					"CREATE TABLE IF NOT EXISTS JumpLeage (UUID VARCHAR(100), Kills VARCHAR(100), Deaths VARCHAR(100), Winns VARCHAR(100), Lose VARCHAR(100))");

			ps.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static boolean isRegistered(UUID uuid) {
		try {
			PreparedStatement ps = MySQL.getStatement("SELECT * FROM JumpLeage WHERE UUID= ?");
			ps.setString(1, uuid.toString());
			ResultSet rs = ps.executeQuery();
			boolean user = rs.next();
			rs.close();
			rs.close();
			return user;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public static void register(Player p) {
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement(
					"INSERT INTO JumpLeage (UUID, Kills, Deaths, Winns, Lose) VALUES (?, ?, ?, ?, ?)");
			ps.setString(1, UUIDFatcher.getUUID(p.getName()).toString());
			ps.setInt(2, 0);

			ps.setInt(3, 0);
			ps.setInt(4, 0);
			ps.setInt(5, 0);

			ps.executeUpdate();
			ps.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static int getKills(UUID uuid) {
		try {
			PreparedStatement ps = MySQL.getStatement("SELECT * FROM JumpLeage WHERE UUID= ?");
			ps.setString(1, uuid.toString());
			ResultSet rs = ps.executeQuery();
			rs.next();
			int coins = rs.getInt("Kills");
			rs.close();
			ps.close();
			return coins;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return -1;
	}

	public static void setKills(Player p, int kills) {
		try {
			PreparedStatement ps = MySQL.getConnection()
					.prepareStatement("UPDATE JumpLeage SET Kills= ? WHERE UUID= ?");
			ps.setInt(1, kills);
			ps.setString(2, p.getUniqueId().toString());
			ps.executeUpdate();
			ps.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void addKills(UUID uuid, int kills) {
		try {
			PreparedStatement ps = MySQL.getConnection()
					.prepareStatement("UPDATE JumpLeage SET Kills= ? WHERE UUID= ?");
			ps.setInt(1, getKills(uuid) + kills);
			ps.setString(2, uuid.toString());
			ps.executeUpdate();
			ps.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void addWin(UUID uuid) {
		try {
			PreparedStatement ps = MySQL.getConnection()
					.prepareStatement("UPDATE JumpLeage SET Winns= ? WHERE UUID= ?");
			ps.setInt(1, getWinns(uuid) + 1);
			ps.setString(2, uuid.toString());
			ps.executeUpdate();
			ps.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void removeWin(UUID uuid) {
		try {
			PreparedStatement ps = MySQL.getConnection()
					.prepareStatement("UPDATE JumpLeage SET Winns= ? WHERE UUID= ?");
			ps.setInt(1, getWinns(uuid) - 1);
			ps.setString(2, uuid.toString());
			ps.executeUpdate();
			ps.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static int getWinns(UUID uuid) {
		try {
			PreparedStatement ps = MySQL.getStatement("SELECT * FROM JumpLeage WHERE UUID= ?");
			ps.setString(1, uuid.toString());
			ResultSet rs = ps.executeQuery();
			rs.next();
			int winns = rs.getInt("Winns");
			rs.close();
			ps.close();
			return winns;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return -1;
	}

	public static void addLoses(UUID uuid) {
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE JumpLeage SET Lose= ? WHERE UUID= ?");
			ps.setInt(1, getLoses(uuid) + 1);
			ps.setString(2, uuid.toString());

			System.out.println("loses");
			ps.executeUpdate();
			ps.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// public static void removeLoses(UUID uuid) {
	// try {
	// PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE
	// JumpLeage SET Lose= ? WHERE UUID= ?");
	// ps.setInt(1, getWinns(uuid) - 1);
	// ps.setString(2, uuid.toString());
	// ps.executeUpdate();
	// ps.close();
	// } catch (Exception ex) {
	// ex.printStackTrace();
	// }
	// }

	public static int getLoses(UUID uuid) {
		try {
			PreparedStatement ps = MySQL.getStatement("SELECT * FROM JumpLeage WHERE UUID= ?");
			ps.setString(1, uuid.toString());
			ResultSet rs = ps.executeQuery();
			rs.next();
			int loses = rs.getInt("Lose");
			rs.close();
			ps.close();
			return loses;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return -1;
	}

	public static void removeKills(UUID uuid, int kills) {
		try {
			PreparedStatement ps = MySQL.getConnection()
					.prepareStatement("UPDATE JumpLeage SET Kills= ? WHERE UUID= ?");
			ps.setInt(1, getKills(uuid) - kills);
			ps.setString(2, uuid.toString());
			ps.executeUpdate();
			ps.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/* Deaths */

	public static int getDeaths(UUID uuid) {
		try {
			PreparedStatement ps = MySQL.getStatement("SELECT * FROM JumpLeage WHERE UUID= ?");
			ps.setString(1, uuid.toString());
			ResultSet rs = ps.executeQuery();
			rs.next();
			int coins = rs.getInt("Deaths");
			rs.close();
			ps.close();
			return coins;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return -1;
	}

	public static void setDeaths(Player p, int Deaths) {
		try {
			PreparedStatement ps = MySQL.getConnection()
					.prepareStatement("UPDATE JumpLeage SET Deaths= ? WHERE UUID= ?");
			ps.setInt(1, Deaths);
			ps.setString(2, p.getUniqueId().toString());
			ps.executeUpdate();
			ps.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void addDeaths(UUID uuid, int Deaths) {
		try {
			PreparedStatement ps = MySQL.getConnection()
					.prepareStatement("UPDATE JumpLeage SET Deaths= ? WHERE UUID= ?");
			ps.setInt(1, getDeaths(uuid) + Deaths);
			ps.setString(2, uuid.toString());
			ps.executeUpdate();
			ps.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void removeDeaths(UUID uuid, int Deaths) {
		try {
			PreparedStatement ps = MySQL.getConnection()
					.prepareStatement("UPDATE JumpLeage SET Deaths= ? WHERE UUID= ?");
			ps.setInt(1, getDeaths(uuid) - Deaths);
			ps.setString(2, uuid.toString());
			ps.executeUpdate();
			ps.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// public Integer getRankingFromUUID(UUID uuid) {
	// boolean done = false;
	// int n = 0;
	// try {
	// PreparedStatement var8 = MySQL.getConnection()
	// .prepareStatement("SELECT UUID FROM JumpLeage WHERE Kills = ? ORDER BY kills
	// DESC");
	// var8.setString(1, uuid.toString());
	// ResultSet rs = var8.executeQuery();
	// while ((rs.next()) && (!done)) {
	// n++;
	// if (rs.getString("UUID").equalsIgnoreCase(uuid.toString())) {
	// done = true;
	// }
	// }
	// rs.close();
	// var8.close();
	// } catch (Exception var81) {
	// var81.printStackTrace();
	// }
	// return Integer.valueOf(n);
	// }

	// public String getNameRankbyID(UUID uuid) {
	// try {
	// int var7 = 0;
	// PreparedStatement ps = MySQL.getConnection()
	// .prepareStatement("SELECT * FROM JumpLeage WHERE UUID = ? ORDER BY kills
	// DESC");
	// ps.setString(1, uuid.toString());
	// ResultSet rs = ps.executeQuery();
	// while (rs.next()) {
	// var7++;
	// if (var7 == place) {
	// return rs.getString("NAME");
	// }
	// }
	// rs.close();
	// ps.close();
	// } catch (Exception var71) {
	// var71.printStackTrace();
	// }
	// return null;
	// }
	//
	// public String getUUIDRankbyID(String table, String type, int place) {
	// try {
	// int var7 = 0;
	// PreparedStatement ps = MySQL.getConnection()
	// .prepareStatement("SELECT * FROM " + table.toLowerCase() + " WHERE TYPE = ?
	// ORDER BY VALUE DESC");
	// ps.setString(1, type.toUpperCase());
	// ResultSet rs = ps.executeQuery();
	// while (rs.next()) {
	// var7++;
	// if (var7 == place) {
	// return rs.getString("UUID");
	// }
	// }
	// rs.close();
	// ps.close();
	// } catch (Exception var71) {
	// var71.printStackTrace();
	// }
	// return null;
	// }

}
