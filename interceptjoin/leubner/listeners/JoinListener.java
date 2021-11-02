package interceptjoin.leubner.listeners;

import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import interceptjoin.leubner.main.ConnectionManager;
import interceptjoin.leubner.main.Main;

public class JoinListener implements Listener {
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) { //The onPlayerJoin Event
		Player player = event.getPlayer();
		Bukkit.getScheduler().runTaskAsynchronously(Main.getPlugin(), new Runnable() { //Run Task 

			@Override
			public void run() {
				try {
					ConnectionManager.addNewEvent(player, "Join"); //Add the new event to Database
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
		});
	}
}
