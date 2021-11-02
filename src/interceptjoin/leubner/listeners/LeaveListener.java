package interceptjoin.leubner.listeners;

import java.sql.SQLException;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import interceptjoin.leubner.main.ConnectionManager;

public class LeaveListener implements Listener{
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		try {
			ConnectionManager.addNewEvent(player, "Leave"); //Simply add the "leave" action to Database
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
