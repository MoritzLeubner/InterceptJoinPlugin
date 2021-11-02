package interceptjoin.leubner.main;

import interceptjoin.leubner.listeners.JoinListener;
import interceptjoin.leubner.listeners.LeaveListener;
import interceptjoin.leubner.commands.GetConnectionsCommand;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{
	
	private static Main plugin;
	
	public static MySQL mysql;
	
	public static boolean error;
	
	public void onEnable() {
                loadMySQL(); //Connect to MySQL
		plugin = this; //set Main Class
		getCommand("getconnections").setExecutor(new GetConnectionsCommand());
	
		
		PluginManager pluginManager = getServer().getPluginManager();
		pluginManager.registerEvents(new LeaveListener(), this);  //get Plugin Manager and register Events 
		pluginManager.registerEvents(new JoinListener(), this);
		System.out.println("InterceptJoin-Plugin loaded successfull");
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			
			@Override
			public void run() {
				if(MySQL.check()) {
					System.out.println("[InterceptJoin] Found Error in MySQL"); //This construct updates the MySQL class, otherwise the plugin crashes on the player join listener
					loadMySQL();
				}
			}
		}, 0, 20 * 10); //This script run every 10 seconds
	}
	public static void loadMySQL() {
		mysql = new MySQL("localhost", 3306, "<database>", "<username>", "<password>"); //For the MySQL Structure, see mysql.txt
	}                   //<host>    <port>
	public static Main getPlugin() {
		return plugin; //Return Main Class
	}
}
