package info.TobiBayer.munichpower.Commands;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import info.HeroAAX.V1_0_0.PluginShowcase;

public class CommandPreProcess implements Listener {

	@EventHandler(ignoreCancelled=false, priority = EventPriority.HIGHEST)
	public void onCommand(PlayerCommandPreprocessEvent e) {
		if (!e.getPlayer().hasPermission("pluginshowcase.commands")) {
			String[] msg = e.getMessage().split(" ");
			if (msg.length > 0) {
				if (PluginShowcase.blockedCommands.contains(msg[0].replace("/", ""))) {
					e.setCancelled(true);
					return;
				}
			}
		}
	}
}
