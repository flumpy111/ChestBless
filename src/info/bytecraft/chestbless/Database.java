package info.bytecraft.chestbless;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.avaje.ebean.validation.NotNull;

@Entity()
@Table(name="chest_bless")
public class Database {
	
	@Id
	private int id;
	
	@NotNull
	private String playerName;
	
	@NotNull
	private int x;
	
	@NotNull
	private int y;
	
	@NotNull
	private int z;
	
	@NotNull
	private String worldName;
	
	public int getId(){
		return this.id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public String getPlayerName(){
		return this.playerName;
	}
	
	public void setPlayerName(String name){
		this.playerName = name;
	}
	
	public String getWorldName(){
		return this.worldName;
	}
	
	public void setWorldName(String world){
		this.worldName = world;
	}
	
	public World getWorld(){
		return Bukkit.getWorld(worldName);
	}
	
	public void setWorld(World world){
		this.worldName = world.getName();
	}
	
	public Player getPlayer(){
		return Bukkit.getPlayer(playerName);
	}
	
	public void setPlayer(Player player){
		this.playerName = player.getName();
	}
	
	public int getX(){
		return this.x;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public int getY(){
		return this.y;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public int getZ(){
		return this.z;
	}
	
	public void setZ(int z){
		this.z = z;
	}
	
	public Location getLocation(){
		return new Location(getWorld(),x,y,z);
	}
	
	public void setLocation(Location loc){
		this.setWorld(loc.getWorld());
		this.setX(loc.getBlockX());
		this.setY(loc.getBlockY());
		this.setZ(loc.getBlockZ());
	}
}
