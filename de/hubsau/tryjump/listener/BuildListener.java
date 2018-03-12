package de.hubsau.tryjump.listener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BuildListener implements Listener {

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {

		event.setCancelled(true);

	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {

		event.setCancelled(!event.getBlockPlaced().getType().equals(Material.WOOL) && !(event.getBlockPlaced().getData() == (short) 5) );
		
		;
	}

}
