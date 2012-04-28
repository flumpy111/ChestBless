package info.bytecraft.chestbless;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;

import org.bukkit.plugin.java.JavaPlugin;

public class ChestBless extends JavaPlugin{
	
	@Override
	public void onEnable(){
		setupDatabase();
		getServer().getPluginManager().registerEvents(new CListener(this), this);
		getCommand("bless").setExecutor(new CCommand(this));
	}
	
	private void setupDatabase(){
		try{
			this.getDatabase().find(Database.class).findRowCount();
		}catch(PersistenceException e){
			installDDL();
		}
	}
	
	@Override
	public List<Class<?>> getDatabaseClasses(){
		List<Class<?>> list = new ArrayList<Class<?>>();
		list.add(Database.class);
		return list;
	}
}
