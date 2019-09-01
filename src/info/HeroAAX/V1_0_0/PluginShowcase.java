package info.HeroAAX.V1_0_0;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import info.TobiBayer.munichpower.Commands.CommandPreProcess;
import info.TobiBayer.munichpower.saveload.SaveLoadSystem;
import net.md_5.bungee.api.ChatColor;

public class PluginShowcase extends JavaPlugin {

	private static PluginShowcase plugin;
	public static final List<String> blockedCommands = new ArrayList<>();
	public static boolean logging = true;

	@Override
	public void onEnable() {
		super.onEnable();
		plugin = getPlugin(PluginShowcase.class);
		blockedCommands.addAll(SaveLoadSystem.getBlockedCommands(
				plugin.getFile().getAbsolutePath().replace(File.separatorChar + "PluginShowcase.jar", "")));
		PluginManager pm = getServer().getPluginManager();
//		pm.addPermission(new Permission("pluginshowcase.commands"));
//		pm.addPermission(new Permission("pluginshowcase.modifyconfig"));
		pm.registerEvents(new CommandPreProcess(), this);
	}

	@Override
	public void onLoad() {
		super.onLoad();
	}

	@Override
	public void onDisable() {
		super.onDisable();
		SaveLoadSystem.saveCommands(blockedCommands);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!sender.hasPermission("pluginshowcase.modifyconfig"))
			return true;
		if (command.getName().equals("blockcommand") || command.getName().equalsIgnoreCase("bc")) {
			if (args.length > 0)
				switch (args[0].toLowerCase()) {
				case "add":
					if (args.length >= 2) {
						String cmd = args[1].replace("/", "").toLowerCase();
						if (!blockedCommands.contains(cmd)) {
							blockedCommands.add(cmd);
							sender.sendMessage(ChatColor.RED + "added command: " + args[1]);
						} else
							sender.sendMessage(ChatColor.RED + cmd + " is already blocked");
					}
					return true;
				case "list":
					String s = "";
					for (String f : blockedCommands) {
						s += f + ";";
					}
					sender.sendMessage(ChatColor.DARK_RED + "[Blocked Commands] " + ChatColor.YELLOW + s);
					return true;
				case "log":
					if (args.length > 1) {
						if (args[1].equalsIgnoreCase("true"))
							logging = true;
						else if (args[1].equalsIgnoreCase("false"))
							logging = false;
					} else {
						logging = !logging;
					}
					return true;
				case "info":
					return true;
				case "remove":
					if (args.length >= 2) {
						String cmd = args[1].replace("/", "").toLowerCase();
						if (blockedCommands.contains(cmd)) {
							blockedCommands.remove(cmd);
							sender.sendMessage(ChatColor.RED + "removed command: " + args[1]);
						} else
							sender.sendMessage(ChatColor.RED + cmd + " is already removed");
					}
					return true;
				case "save":
					SaveLoadSystem.saveCommands(blockedCommands);
					sender.sendMessage(ChatColor.RED + "saved config");
					return true;
				default:
					sender.sendMessage(ChatColor.WHITE + "usage: " + ChatColor.GRAY + "/bc add <command>");
					sender.sendMessage(ChatColor.GRAY + "/bc remove <command>");
					sender.sendMessage(ChatColor.GRAY + "/bc list");
					sender.sendMessage(ChatColor.GRAY + "/bc save");
					return true;
				}
		}
		return super.onCommand(sender, command, label, args);
	}
}
