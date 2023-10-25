package dev.loapu.vanishbridge.paper;

import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class VanishBridge extends JavaPlugin implements Listener
{
	private enum SubChannel
	{
		VANISHED,
		UNVANISHED
	}
	private final String IDENTIFIER = "vanishbridge:main";
	private BukkitTask scheduler;
	private final List<Player> vanishedPlayers = new ArrayList<>();
	private final List<Player> unvanishedPlayers = new ArrayList<>();
	@Override
	public void onEnable()
	{
		getServer().getMessenger().registerOutgoingPluginChannel(this, IDENTIFIER);
		scheduler = startScheduler();
		getServer().getPluginManager().registerEvents(this, this);
		getLogger().info("VanishBridge has been initialized!");
	}
	
	@Override
	public void onDisable()
	{
		scheduler.cancel();
		getServer().getMessenger().unregisterOutgoingPluginChannel(this, IDENTIFIER);
		getLogger().info("VanishBridge has been disabled!");
	}
	
	private BukkitTask startScheduler()
	{
		return getServer().getScheduler().runTaskTimer(this, () ->
		{
			var players = getServer().getOnlinePlayers();
			players.forEach(player ->
			{
				if (isVanished(player))
				{
					modifyVanishedPlayers(player, SubChannel.VANISHED);
				}
				else
				{
					modifyVanishedPlayers(player, SubChannel.UNVANISHED);
				}
			});
		}, 0L, 20L * 5L);
	}
	
	private boolean isVanished(Player player)
	{
		for (MetadataValue meta : player.getMetadata("vanished"))
		{
			if (meta.asBoolean()) return true;
		}
		return false;
	}
	
	private void modifyVanishedPlayers(Player player, SubChannel subChannel)
	{
		var anythingChanged = false;
		if (subChannel.equals(SubChannel.VANISHED))
		{
			anythingChanged = unvanishedPlayers.remove(player);
			if (anythingChanged) vanishedPlayers.add(player);
		}
		else
		{
			anythingChanged = vanishedPlayers.remove(player);
			if (anythingChanged) unvanishedPlayers.add(player);
		}
		if (!vanishedPlayers.contains(player) && !unvanishedPlayers.contains(player))
		{
			anythingChanged = true;
			if (isVanished(player)) vanishedPlayers.add(player);
			else unvanishedPlayers.add(player);
		}
		if (anythingChanged)
		{
			sendPluginMessage(player, subChannel);
		}
	}
	
	private void sendPluginMessage(Player player, SubChannel subChannel)
	{
		var uuid = player.getUniqueId().toString();
		var name = player.getName();
		var out = ByteStreams.newDataOutput();
		out.writeUTF(subChannel.name().toLowerCase());
		out.writeUTF(uuid);
		out.writeUTF(name);
		player.sendPluginMessage(this, IDENTIFIER, out.toByteArray());
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerQuit(PlayerQuitEvent event)
	{
		var player = event.getPlayer();
		unvanishedPlayers.remove(player);
		vanishedPlayers.remove(player);
	}
}
