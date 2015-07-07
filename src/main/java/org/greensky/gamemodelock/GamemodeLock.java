package org.greensky.gamemodelock;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

public final class GamemodeLock extends JavaPlugin implements Listener {
	@Override
	public void onEnable() {
		// TODO Insert logic to be performed when the plugin is enabled
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			if (!player.hasPermission("gamemodelock.bypass")) {
				switch (player.getGameMode()) {
				case CREATIVE:
					if (!player.hasPermission("gamemode.creative")) {
						if (player.hasPermission("gamemode.survival")) {
							player.setGameMode(GameMode.SURVIVAL);
						} else {
							player.setGameMode(GameMode.ADVENTURE);
						}
					}
					break;
				case SURVIVAL:
					if (!player.hasPermission("gamemode.survival")) {
						if (player.hasPermission("gamemode.creative")) {
							player.setGameMode(GameMode.CREATIVE);
						} else {
							player.setGameMode(GameMode.ADVENTURE);
						}
					}
					break;
				default:
				}
			}
		}
		getServer().getPluginManager().registerEvents(this, this);

		try {
			Metrics metrics = new Metrics(this);
			metrics.start();
		} catch (IOException e) {
			// Failed
			getLogger().info("enable metrics failed");
		}

	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerJoinEvent(PlayerJoinEvent event) {

		// Your code here...
		Player player = event.getPlayer();
		player.sendMessage("You have logged in");
		if (!player.hasPermission("gamemodelock.bypass")) {
			switch (player.getGameMode()) {
			case CREATIVE:
				if (!player.hasPermission("gamemode.creative")) {
					if (player.hasPermission("gamemode.survival")) {
						player.setGameMode(GameMode.SURVIVAL);
					} else {
						player.setGameMode(GameMode.ADVENTURE);
					}
				}
				break;
			case SURVIVAL:
				if (!player.hasPermission("gamemode.survival")) {
					if (player.hasPermission("gamemode.creative")) {
						player.setGameMode(GameMode.CREATIVE);
					} else {
						player.setGameMode(GameMode.ADVENTURE);
					}
				}
				break;
			default:
			}
		}

	}

	@EventHandler
	public void onPlayerGameModeChangeEvent(PlayerGameModeChangeEvent event) {

		Player player = event.getPlayer();
		player.sendMessage("You have changed game mode");
		if (!player.hasPermission("gamemodelock.bypass")) {
			switch (event.getNewGameMode()) {
			case CREATIVE:
				if (!player.hasPermission("gamemode.creative")) {
					event.setCancelled(true);
				}
				break;
			case SURVIVAL:
				if (!player.hasPermission("gamemode.survival")) {
					event.setCancelled(true);
				}
				break;
			default:
			}
		}

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (cmd.getName().equalsIgnoreCase("gamemodelock")) {
			for (Player player : Bukkit.getServer().getOnlinePlayers()) {
				if (!player.hasPermission("gamemodelock.bypass")) {
					switch (player.getGameMode()) {
					case CREATIVE:
						if (!player.hasPermission("gamemode.creative")) {
							if (player.hasPermission("gamemode.survival")) {
								player.setGameMode(GameMode.SURVIVAL);
							} else {
								player.setGameMode(GameMode.ADVENTURE);
							}
						}
						break;
					case SURVIVAL:
						if (!player.hasPermission("gamemode.survival")) {
							if (player.hasPermission("gamemode.creative")) {
								player.setGameMode(GameMode.CREATIVE);
							} else {
								player.setGameMode(GameMode.ADVENTURE);
							}
						}
						break;
					default:
					}
				}
			}
			sender.sendMessage("Game mode locked");
			return true;
		} // If this has happened the function will return true.
			// If this hasn't happened the value of false will be returned.
		return false;
	}
}
