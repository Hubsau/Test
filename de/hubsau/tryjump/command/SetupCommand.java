package de.hubsau.tryjump.command;

import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.hubsau.tryjump.JumpLeage;
import de.hubsau.tryjump.Var;
import de.hubsau.tryjump.api.ConfigAPI;
import de.hubsau.tryjump.api.ItemBuilder;
import de.hubsau.tryjump.util.Game;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;

public class SetupCommand implements CommandExecutor {
	private String prefix;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		prefix = JumpLeage.getInstance().getPrefix();

		Player player = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("setup") && player.hasPermission("tryjump.setup")) {

			if (args.length == 0) {
				sendHelpMessage(player);
				return true;
			}
			if (args.length >= 1) {
				switch (args[0].toLowerCase()) {

				case "setspawn":
					try {
						setSpawns(Integer.parseInt(args[1]), player);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					break;

				case "setcheck":
					if (args[2].equalsIgnoreCase("leicht") || args[2].equalsIgnoreCase("mittel")
							|| args[2].equalsIgnoreCase("schwer")) {
						try {
							int id = Integer.parseInt(args[1]);
							Var.LOCATIONSCFG.set("checks." + id + ".difficulty", args[2].toLowerCase());
							Var.LOCATIONSCFG.set("checks." + id + ".name", args[3].toLowerCase());

							ConfigAPI.createCfgINT(player.getLocation(), "checks." + id, Var.LOCATIONSFILE,
									Var.LOCATIONSCFG);
							player.sendMessage(prefix + "§aDu hast den Checkpoint mit der ID §7:§b" + id
									+ "§a gesetzt! §7(" + args[2].toLowerCase() + ") (" + args[3].toUpperCase() + ")");

						} catch (Exception e) {
							player.sendMessage(prefix + "§cBitte gib eine gülte Zahl an!");

						}
					} else
						player.sendMessage("§cSyntax: §a/setup setcheck <id> <leicht|mittel|schwer> <name>");

					break;

				case "side":
					char[] side = args[1].toCharArray();
					Character s = side[0];
					if (s.toString().equalsIgnoreCase("z") || s.toString().equalsIgnoreCase("x")) {
						Var.CONFIGCFG.set("side", s.toString().toLowerCase());
						player.sendMessage(prefix + "§aDu hast die Seite zu der §b" + s.toString().toUpperCase()
								+ "§a Koordinate gesetzt");

					}
					try {
						Var.CONFIGCFG.save(Var.CONFIGFILE);
					} catch (IOException e) {
						e.printStackTrace();
					}

					break;
				case "setmax":
					try {
						int id = Integer.parseInt(args[1]);
						Var.CONFIGCFG.set("maxplayer", id);
						player.sendMessage(prefix + "§aDu hast die Maximalen Spieler zu §6" + id + "§a gesetzt");
						Var.CONFIGCFG.save(Var.CONFIGFILE);

					} catch (Exception e) {
						player.sendMessage(prefix + "§cBitte gib eine gülte Zahl an!");

					}

					break;
				case "setmin":

					try {
						int id = Integer.parseInt(args[1]);
						Var.CONFIGCFG.set("minplayers", id);
						player.sendMessage(prefix + "§aDu hast die Minimalen Spieler zu §6" + id + "§a gesetzt");
						Var.CONFIGCFG.save(Var.CONFIGFILE);

					} catch (Exception e) {
						player.sendMessage(prefix + "§cBitte gib eine gülte Zahl an!");
					}

					break;
				case "setamount":
					try {
						int amount = Integer.parseInt(args[1]);
						Var.CONFIGCFG.set("amount", amount);
						player.sendMessage(
								prefix + "§aDu hast die Menge von schematicas zu §6" + amount + "§a gesetzt");
						Var.CONFIGCFG.save(Var.CONFIGFILE);

					} catch (Exception e) {
						player.sendMessage(prefix + "§cBitte gib eine gülte Zahl an!");
					}

					break;
				case "giveitem":
					Var.INSETUP.put(player, "null");
					TextComponent message1 = new net.md_5.bungee.api.chat.TextComponent(
							prefix + "§aWas möchtest du einstellen?");
					TextComponent message2 = new net.md_5.bungee.api.chat.TextComponent(
							prefix + "§6[§eStart Spawns§6] ");
					TextComponent message3 = new net.md_5.bungee.api.chat.TextComponent("§boder ");
					TextComponent message4 = new net.md_5.bungee.api.chat.TextComponent("§2[§aEndfight Spawns§2]");
					player.spigot().sendMessage(message1);
					message2.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/setup spawntype Spawns"));
					message2.addExtra(message3);
					message2.addExtra(message4);
					message4.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/setup spawntype end"));
					player.spigot().sendMessage(message2);
					player.getInventory().setItem(0, new ItemBuilder(Material.STICK).enchantment(Enchantment.KNOCKBACK)
							.name("§2Spawns Item").build());
					player.sendMessage(prefix + "§2Du hast jetz das Spawns Item");

					break;
				case "mainspawn":

					ConfigAPI.createCfg(player.getLocation(), "spawn.hauptspawn", Var.LOCATIONSFILE, Var.LOCATIONSCFG);
					player.sendMessage(prefix + "§aDu hast den Hauptspawn gesetzt!");

					break;

				case "endfightspawn":
					try {
						setEndSpawns(Integer.parseInt(args[1]), player);

					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				case "spawntype":
					if (args[1].equalsIgnoreCase("Spawns")) {
						if (Var.INSETUP.containsKey(player)) {
							Var.INSETUP.put(player, "spawns");
							player.sendMessage(prefix + "§aDu stellt jetz die Spawn locations ein");

						}

					} else if (args[1].equalsIgnoreCase("end")) {
						if (Var.INSETUP.containsKey(player)) {
							Var.INSETUP.put(player, "end");
							player.sendMessage(prefix + "§aDu stellt jetz die Endfight locations ein");
						}
					}

					break;
				case "setholo":

					ConfigAPI.createCfg(player.getLocation(), "hologram.spawn", Var.LOCATIONSFILE, Var.LOCATIONSCFG);
					player.sendMessage(prefix + "§aDu hast den Spawn des stats Holograms gesetzt!");

					break;
				case "hub":
					
					Game.sendToLobbyServer(player);

					break;
				case "setchest":
					int selten =0;
					
					
					try {
					selten = Integer.parseInt(args[1]);

					}catch (Exception e) {
					}
					
					
					Var.CONFIGCFG.set("inventory."+selten, player.getInventory().getContents());
					try {
						Var.CONFIGCFG.save(Var.CONFIGFILE);
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
					
//				case "getchest":
//					
//					ArrayList<ItemStack> contenst = (ArrayList<ItemStack>) Var.CONFIGCFG.get("inventory.1");
//					
//					ItemStack[] contenstsarray = contenst.toArray(new ItemStack[contenst.size()]);
//					
//					
////					ItemStack[] inv = Var.CONFIGCFG.get("inventory.1");
//					
//					
//					player.getInventory().setContents(contenstsarray);
//					
//					
//					
//					break;
				default:
					sendHelpMessage(player);
					break;

				}
				return true;

			}
		}
		return false;
	}

	private void sendHelpMessage(Player player) {

		player.sendMessage("§3███████████████████████████████████████");
		player.sendMessage("§3█§e °-° §e SETUP HILFE °-°");
		player.sendMessage("§3█§b/setup §7: §8Hauptcommand (Siehe diese Hilfenachricht)");
		player.sendMessage("§3█§b/setup setspawn <Zahl> §7: §8Setze die Verschiedenen Spawns");
		player.sendMessage("§3█§b/setup setcheck <Zahl> §7: §8Setze die Checkpoints");
		player.sendMessage("§3█§b/setup side <Char> §7: §8Setze die X oder Z...");
		player.sendMessage("§3█§b/setup mainspawn §7: §8Setze den Spawn");
		player.sendMessage("§3█§b/setup endfightspawn <Zahl> §7: §8Setze Spawns für das Endfight");
		player.sendMessage("§3█§b/setup setmax <Zahl> §7: §8Setze die Maximalen Spieler zum Start");
		player.sendMessage("§3█§b/setup setmin <Zahl> §7: §8Setze die Minimalen Spieler zum Start");
		player.sendMessage("§3█§b/setup setAmout <Zahl> §7: §8Setze die anzahl");
		player.sendMessage("§3█§b/setup setHolo §7: §8Setze das stats Hologram");
		player.sendMessage("§3█§8der gespeicherten Schematicas in den Worldedit verzeichniss");
		player.sendMessage("§3█§b/setup giveitem: §8Gebe dir das Item um schneller Spawns ");
		player.sendMessage("§3███████████████████████████████████████");

		// player.sendMessage(prefix + "§aWie speichere ich passen zum plugin die
		// Schematicas?:");
		// player.sendMessage(prefix
		// + "§71. §eMakiere die erste Jump'n Run Insel oder den ersten Bereich auf der
		// Map mit Worldedit");
		// player.sendMessage(prefix
		// + "§72. §eStelle dich zum Startpunkt und schaue geradeaus jetzt Kopiere mit
		// //copy und erstelle mit '/schematic save 1' eine Schematic von der ersten
		// Insel");
		// player.sendMessage(prefix
		// + "§73. §eMache diesen Vorgang bis zu beim Ziel ankommen bist stelle dich
		// immer auf den Jackpoint, wähle den nächsten Bereich aus und Koppierst"
		// + "ihn indem du dich auf den Jackpoint stellst und nach vorne schaust.
		// Dannach speichere mit '/schematic save <und eine Zahl die sich immer um eins
		// erhöht>'");
		// player.sendMessage(prefix
		// + "§74. §eNun bist du fast fertig du musst nur noch die Anzahl der
		// gespeicherten Schematics mit §b/setup setamount <anzahl> setzen!");

	}

	public static void setEndSpawns(int id, Player player) throws Exception {

		try {

			if (id == 1) {
				ConfigAPI.createCfgYP(player.getLocation(), "spawn.endfight." + id, Var.LOCATIONSFILE,
						Var.LOCATIONSCFG);
			} else {
				ConfigAPI.createCfgYP(player.getLocation(), "spawn.endfight." + id, Var.LOCATIONSFILE,
						Var.LOCATIONSCFG);

			}

			player.sendMessage(JumpLeage.getInstance().getPrefix() + "§aDu hast den EnSpawn mit der ID §7:§b" + id
					+ "§a gesetzt!");

		} catch (Exception e) {
			player.sendMessage(JumpLeage.getInstance().getPrefix() + "§cBitte gib eine gülte Zahl an!");
		}

	}

	public static void setSpawns(int id, Player player) throws Exception {

		try {
			if (id == 1) {
				ConfigAPI.createCfgYP(player.getLocation(), "spawn." + id, Var.LOCATIONSFILE, Var.LOCATIONSCFG);
			} else {
				ConfigAPI.createCfg(player.getLocation(), "spawn." + id, Var.LOCATIONSFILE, Var.LOCATIONSCFG);

			}

			player.sendMessage(
					JumpLeage.getInstance().getPrefix() + "§aDu hast den Spawn mit der ID §7:§b" + id + "§a gesetzt!");

		} catch (Exception e) {
			player.sendMessage(JumpLeage.getInstance().getPrefix() + "§cBitte gib eine gülte Zahl an!");
		}

	}

}
