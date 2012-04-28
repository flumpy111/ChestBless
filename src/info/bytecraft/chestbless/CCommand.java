package info.bytecraft.chestbless;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CCommand implements CommandExecutor{
	public static ChestBless plugin;
	public CCommand(ChestBless instance){
		plugin = instance;
	}
	
	public static HashMap<Player,Player> players = new HashMap<Player,Player>();
	
	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("bless") && args.length == 1){
			if(cs instanceof Player){
				Player player = (Player)cs;
				if(player.hasPermission("bytecraft.bless")){
					Player target = Bukkit.getPlayer(args[0]);
					if(target != null){
						player.sendMessage(ChatColor.AQUA + "Preparing to bless a block for " + target.getDisplayName());
						players.put(player, target);
						return true;
					}else{
						return true;
					}
				}
			}
		}else if(cmd.getName().equalsIgnoreCase("bless") && args.length == 0){
			if(cs instanceof Player){
				Player player = (Player)cs;
				if(player.hasPermission("bytecraft.bless")){
					if(players.containsKey(player)){
						players.remove(player);
						return true;
					}else{
						return true;
					}
				}
				}
		}
		return false;
	}

}
