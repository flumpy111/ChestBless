package info.bytecraft.chestbless;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class CListener implements Listener {
	private static ChestBless plugin;

	public CListener(ChestBless instance) {
		plugin = instance;
	}

	@EventHandler
	public void onBless(PlayerInteractEvent event) {
		if (event.getAction() == Action.LEFT_CLICK_AIR
				|| event.getAction() == Action.RIGHT_CLICK_AIR)
			return;
		Player player = event.getPlayer();
		ItemStack item = player.getItemInHand();
		if (item.getType() != Material.BONE)
			return;
		if (CCommand.players.get(player) == null)
			return;
		if (!player.hasPermission("bytecraft.bless"))
			return;
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK
				|| event.getAction() == Action.LEFT_CLICK_BLOCK) {
			Block block = event.getClickedBlock();
			if (block.getType() == Material.CHEST
					|| block.getType() == Material.DISPENSER
					|| block.getType() == Material.WORKBENCH
					|| block.getType() == Material.SIGN_POST
					|| block.getType() == Material.ENCHANTMENT_TABLE
					|| block.getType() == Material.WALL_SIGN
					|| block.getType() == Material.STONE_BUTTON
					|| block.getType() == Material.TRAP_DOOR
					|| block.getType() == Material.WOODEN_DOOR
					|| block.getType() == Material.IRON_DOOR_BLOCK
					|| block.getType() == Material.JUKEBOX
					|| block.getType() == Material.FURNACE
					|| block.getType() == Material.LEVER) {
				Location loc = block.getLocation();
				String x = String.valueOf(loc.getBlockX());
				String y = String.valueOf(loc.getBlockY());
				String z = String.valueOf(loc.getBlockZ());
				String world = loc.getWorld().getName();
				Database db = plugin.getDatabase().find(Database.class).where()
						.ieq("x", x).ieq("y", y).ieq("z", z)
						.ieq("worldName", world).findUnique();
				if (db == null) {
					db = new Database();
					db.setLocation(block.getLocation());
					db.setPlayer(CCommand.players.get(player));
					plugin.getDatabase().save(db);
					player.sendMessage(ChatColor.AQUA + "Blessed a block to "
							+ CCommand.players.get(player).getDisplayName());
					CCommand.players.get(player).sendMessage(
							ChatColor.AQUA
									+ "A chest has been blessed in your name");
					event.setCancelled(true);
					// CCommand.players.remove(player);
				} else {
					return;
				}
			}
		}
	}

	@EventHandler
	public void onClick(PlayerInteractEvent event) {
		if (event.isCancelled())
			return;
		if (event.getAction() == Action.LEFT_CLICK_AIR
				|| event.getAction() == Action.RIGHT_CLICK_AIR)
			return;
		Player player = event.getPlayer();
		Block block = event.getClickedBlock();
		Location loc = block.getLocation();
		String x = String.valueOf(loc.getBlockX());
		String y = String.valueOf(loc.getBlockY());
		String z = String.valueOf(loc.getBlockZ());
		String world = loc.getWorld().getName();
		if (event.getAction() == Action.LEFT_CLICK_BLOCK
				|| event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Database db = plugin.getDatabase().find(Database.class).where()
					.ieq("x", x).ieq("y", y).ieq("z", z)
					.ieq("worldName", world).findUnique();
			if (db == null)
				return;
			if (!player.getName().equalsIgnoreCase(db.getPlayerName())) {
				if (!player.hasPermission("bytecraft.bless.override")) {
					if (Bukkit.getPlayer(db.getPlayerName()) == null) {
						player.sendMessage(ChatColor.RED + "Blessed to "
								+ db.getPlayerName());
						event.setCancelled(true);
					} else {
						player.sendMessage(ChatColor.RED + "Blessed to "
								+ db.getPlayer().getDisplayName());
						event.setCancelled(true);
					}
				} else {
					if (Bukkit.getPlayer(db.getPlayerName()) == null) {
						player.sendMessage(ChatColor.AQUA + "Blessed to "
								+ db.getPlayerName());
						event.setCancelled(false);
					} else {
						player.sendMessage(ChatColor.AQUA + "Blessed to "
								+ db.getPlayer().getDisplayName());
						event.setCancelled(false);
					}
				}
			} else {
				player.sendMessage(ChatColor.AQUA + "Blessed to you");
			}
		}

	}

	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		Block block = event.getBlock();
		Location loc = block.getLocation();
		String x = String.valueOf(loc.getBlockX());
		String y = String.valueOf(loc.getBlockY());
		String z = String.valueOf(loc.getBlockZ());
		String world = loc.getWorld().getName();
		Database db = plugin.getDatabase().find(Database.class).where()
				.ieq("x", x).ieq("y", y).ieq("z", z).ieq("worldName", world)
				.findUnique();
		if (db == null)
			return;
		if (!player.getName().equalsIgnoreCase(db.getPlayerName())) {
			if (!player.hasPermission("bytecraft.admin")) {
				player.sendMessage(ChatColor.RED + "Blessed to "
						+ db.getPlayerName());
				event.setCancelled(true);
			} else {
				player.sendMessage(ChatColor.AQUA + "Blessed to "
						+ db.getPlayerName());
			}
		} else {
			player.sendMessage(ChatColor.AQUA + "Blessed to you");
		}
	}

	@EventHandler
	public void onCheck(PlayerInteractEvent event) {
		if (event.isCancelled())
			return;
		if (event.getAction() == Action.LEFT_CLICK_AIR
				|| event.getAction() == Action.RIGHT_CLICK_AIR)
			return;
		ChatColor green = ChatColor.DARK_GREEN;
		ChatColor white = ChatColor.WHITE;
		Player player = event.getPlayer();
		Block block = event.getClickedBlock();
		Location loc = block.getLocation();
		String x = String.valueOf(loc.getBlockX());
		String y = String.valueOf(loc.getBlockY());
		String z = String.valueOf(loc.getBlockZ());
		String world = loc.getWorld().getName();
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK
				&& player.getItemInHand().getType() == Material.BOOK) {
			Database db = plugin.getDatabase().find(Database.class).where()
					.ieq("x", x).ieq("y", y).ieq("z", z)
					.ieq("worldName", world).findUnique();
			player.sendMessage(green + "World: " + white + world);
			player.sendMessage(green + "Biome: " + white + block.getBiome());
			player.sendMessage(green + "X: " + white + x);
			player.sendMessage(green + "Y: " + white + y);
			player.sendMessage(green + "Z: " + white + z);
			if (db != null) {
				player.sendMessage(green + "BlessId: " + white + db.getId());
				player.sendMessage(green + "Blessed to: " + white
						+ db.getPlayerName());
			}
			event.setCancelled(true);
		}
	}
}
