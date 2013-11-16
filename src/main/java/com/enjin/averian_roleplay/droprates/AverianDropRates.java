package com.enjin.averian_roleplay.droprates;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class AverianDropRates extends JavaPlugin implements Listener {
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		saveDefaultConfig();
	}
	
	@Override
	public void saveDefaultConfig() {
		File configFile = new File(getDataFolder(), "config.yml");
		if (!configFile.exists()) {
			getConfig().set("LOG", Arrays.asList(new ItemStack[] {new ItemStack(Material.WOOD, 2), new ItemStack(Material.STICK, 4)}));
			getConfig().set("STONE", Arrays.asList(new ItemStack[] {new ItemStack(Material.COBBLESTONE, 2), new ItemStack(Material.STONE, 1)}));
			saveConfig();
		}
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onBlockBreak(BlockBreakEvent event) {
		if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
			if (getConfig().contains(event.getBlock().getType().toString())) {
				List<ItemStack> possibleItems = new ArrayList<ItemStack>();
				for (Object item : getConfig().getList(event.getBlock().getType().toString())) {
					if (item instanceof ItemStack) {
						possibleItems.add((ItemStack) item);
					}
				}
				event.setCancelled(true);
				event.getBlock().setType(Material.AIR);
				Random random = new Random();
				event.getBlock().getWorld().dropItem(event.getBlock().getLocation().add(0.5D, 0.5D, 0.5D), possibleItems.get(random.nextInt(possibleItems.size())));
			}
		}
	}

}
